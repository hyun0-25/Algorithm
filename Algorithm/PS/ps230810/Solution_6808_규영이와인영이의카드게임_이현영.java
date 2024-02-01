package ps230810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:41
 * 종료시간: 14:48
 * 
 * 
 * 문제 해석
 * 1~18까지의 수가 적힌 18장의 카드로 게임
 * 한 라운드에는 한 장씩 카드를 낸 다음 2명의 사람이 낸 카드에 적힌 수를 비교해서 점수를 계산
 * 높은 수가 적힌 카드를 낸 사람 : 두 카드에 적힌 수의 합만큼 점수
 * 낮은 수가 적힌 카드를 낸 사람 : 점수 X
 * 9번의 라운드 진행, 총점이 더 높은 사람이 승자
 * 				총점이 같으면 무승부
 * 규영이가 받은 9장의 카드에 적힌 수 주어짐
 * 규영이가 내는 카드의 순서를 고정하면 인영이가 어떻게 카드를 내는지에 따라 9!가지 순서에 따라 승패 결정
 * 규영이가 이기는 경우와 지는 경우가 총 몇가지 인지
 * 
 * 
 * 입력
 * 첫번째줄: 9개의 정수
 *       (규영이의 카드 내는 고정 순서)
 * 
 * 
 * 출력
 * 인영이가 카드를 내는 9!가지의 경우에 대해 
 * 규영이가 게임을 이기는 경우의 수와 게임을 지는 경우의 수를 출력
 * 
 * 
 * 문제해결 프로세스 (9!)
 * 1. 규영이가 낸 카드를 제외한 숫자들로 인영이가 가지고 있는 카드 9개를 알아냄
 * 2. 9P9의 순열로 인영이가 카드 내는 경우의 수를 모두 구함
 * 3. 각 경우의 수에 대해서 규영이가 이겼는지, 졌는지, 무승부였는지 판단
 * 
 * 
 * 제한조건
 * 각 정수는 1~18까지의 정수
 * 같은 정수는 없음
 * 
 * 
 * 시간복잡도
 * O(9!*9)
 * 
 */

public class Solution_6808_규영이와인영이의카드게임_이현영 {
	static int T, allcard[], card1[], card2[], pcard2[];
	static int win,lose;
	static boolean isSelected[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		T = Integer.parseInt(br.readLine());
//		int factorial = 1*2*3*4*5*6*7*8*9;
		for(int test_case = 1; test_case <= T; test_case++) {
			allcard = new int [18];
			card1 = new int [9];
			card2 = new int [9];
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 9; i++) {
				card1[i] = Integer.parseInt(st.nextToken());
				allcard[2*i] = 2*i+1;
				allcard[2*i+1] = 2*i+2;
			}
			int idx = 0;
			for(int x: allcard) {
				//1~18중 card1에 들어있는지 확인
				//들어있으면 continue;
				//아니면 card2에 넣음
				boolean flag = false;
				for (int i = 0; i < 9; i++) {
					if(x==card1[i]) flag = true;
				}
				if(!flag) {
					card2[idx++] = x;
				}
			}
			isSelected = new boolean[9];
			//9P9로 고른 순열들
			pcard2 = new int[9];
			//이긴 횟수, 진 횟수 초기화
			win=0;
			lose=0;
			Permutation(0);
			sb.append('#').append(test_case).append(' ')
						.append(win).append(' ')
						.append(lose).append(' ').append('\n');
		}
		System.out.println(sb);
	}
	
	public static void Permutation(int cnt) {
		if(cnt==9) {
			CompareCard(pcard2);
			return;
		}
		for (int i = 0; i < 9; i++) {
			if(isSelected[i]) continue;
			pcard2[cnt] = card2[i];
			isSelected[i] = true;
			Permutation(cnt+1);
			isSelected[i] = false;
		}
	}
	
	public static void CompareCard(int[] card) {
		int p1=0, p2=0;
		for (int i = 0; i < 9; i++) {
			if(card1[i]>card[i]) p1+=card1[i]+card[i];
			else if (card1[i]<card[i]) p2+=card1[i]+card[i];
		}
		//승패 확인
		if(p1>p2) win++;
		else if (p1<p2) lose++;
	}
}
