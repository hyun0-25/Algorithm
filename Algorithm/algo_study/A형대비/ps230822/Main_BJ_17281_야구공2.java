package A형대비.ps230822;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간: 4:33 ~
 * 
 * 
 * 문제 해석
 * 야구는 9명으로 이루어진 2팀이 공격과 수비를 번갈아하는 게임
 * 하나의 이닝은 공격과 수비로 이루어져있음 (총 N이닝 게임)
 * 한 이닝에 3아웃 -> 이닝 종료, 공격/수비 바꿈
 * 경기 시작하기 전까지 타순 정함, 경기중에는 변경 불가
 * 9번 타자까지 공침 -> 3아웃 발생 X 이닝 끝나지 않고 -> 1번 타자가 다시 타석에 섬
 * 타순은 이닝이 변경되어도 순서를 유지 
 * 예) 2이닝에 6번 타자가 마지막 타자, 3이닝 7번 타자부터 타석
 * 
 * 공격 : 투수가 던진 공을 타석의 타자가 침
 * 공격팀 선수가 1,2,3루를 거쳐서 홈에 도착하면 1점 득점
 * 			홈에 도착하지 못하고 1,2,3루 중 하나에 머물수 있음
 * 			(루에 있는 선수를 주자라고 함, 이니 시작시에는 주자X)
 * 타자가 공을 쳐서 얻을 수 있는 결과 : 1(안타),2(루타),3(루타),홈런(4),아웃(주자 진루X, 공격팀의 아웃 1증가)
 *
 * 감독이 타순을 정함, 총 9명 선수, 1~9번까지 번호
 * 1번선수->4번타자로 이미 결정, 1~3,5~9정해야함
 * 가장 많은 득점을 하는 타순을 찾고, 그때의 득점을 구해보자
 * 
 *  
 * 입력
 * 첫째줄: 이닝 수 N
 * 둘째줄~N개줄: 각 선수가 각 이닝에서 얻는 결과 (1~N이닝)
 * 
 * 각 이닝에는 아웃을 기록하는 타자가 적어도 1명 존재
 * 
 * 출력
 * 얻을 수 있는 최대 점수
 * 
 * 문제해결 프로세스 -> 완탐
 * 1. (9-1)! 순열로 순서 정하기
 * 2. 각 경우마다 이닝의 점수 계산
 * 3. 최댓값 출력
 * 
 * 
 * 
 * 제한조건
 * N<=50
 * 선수의 수 : 9명
 * 
 * 시간복잡도
 * O(8!*50*9*3) = 5440만
 * 
 * 
 */

public class Main_BJ_17281_야구공2 {
	static int N, player[][], sel[], result, max, first;
	static boolean isSelected[];
//	static ArrayList<Integer> base;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		player = new int[9][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				player[j][i] = Integer.parseInt(st.nextToken());
			}
		}
		isSelected = new boolean[9];
		sel = new int[9];
		//1번선수 4번타자 고정
		sel = new int[9];
		isSelected[0] = true;
		permutation(0);
		System.out.println(max);
	}
	public static void permutation(int cnt) {
		if(cnt==9) {
			result=0;
			game(sel);
			max = Math.max(max, result);
			return;
		}
		if(cnt==3) {
			//1번선수 4번타자 고정
			permutation(cnt+1);
			return;
		}
		for (int i = 1; i < 9; i++) {
			if(isSelected[i]) continue;
			sel[cnt] = i;
			isSelected[i]=true;
			permutation(cnt+1);
			isSelected[i]=false;
		}
	}

	//타자들 배열
	static boolean base[] = new boolean[4];
	public static void game(int[] play) {
		first=0;
		
		//총 이닝
		for (int i = 0; i < N; i++) {
			//베이스에 있는 타자들
			Arrays.fill(base, false);
			baseball(first, play, i, 0);
		}
	}
	public static void baseball(int f, int[] play, int round, int out) {
		int cnt_out=out;
		
		for (int i = f; i < 9; i++) {
			//아웃이면
			int idx = play[i];
			if(player[idx][round]==0) {
				cnt_out++;
			}
			//3아웃이면 다음 이닝으로
			if(cnt_out==3) {
				//아웃 당한 다음 타자부터 다음 이닝 시작
				first = i+1;
				if(first==9) first=0;
				return;
			}
			if(player[idx][round]!=0) point(player[idx][round], base);
		}
		baseball(0, play, round, cnt_out);
	}
	
	
	public static void point(int idx, boolean[] b) {
		for (int i = 3; i >= 1; i--) {
			if(b[i]) {
				if(i+idx>=4) {
					result++;
					b[i]=false;
				}
				else {
					b[i]=false;
					b[i+idx]=true;
				}
			}
		}
		if(idx!=4) b[idx]=true;
		else result++;
	}
}