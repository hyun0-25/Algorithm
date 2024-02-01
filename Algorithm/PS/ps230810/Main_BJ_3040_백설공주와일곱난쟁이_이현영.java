package ps230810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 시작시간: 14:31
 * 종료시간: 14:35
 * 
 * 
 * 문제 해석
 * 각 난쟁이가 쓰고 다니는 모자에 100보다 작은 양의 정수 적혀있음
 * 일곱 난쟁이의 모자에 쓰여 있는 숫자의 합이 100
 * 아홉 난쟁이의 모자에 쓰여 있는 수가 주어졌을 때, 일곱 난쟁이를 찾는 프로그램
 * 
 * 
 * 입력
 * 9개의 줄: 1~99의 자연수
 * 모든 숫자는 서로 다름
 * 답이 유일한 경우만 입력으로 주어짐
 * 
 * 출력
 * 일곱 난쟁이가 쓴 모자에 쓰여있는 수를 한 줄에 하나씩 출력
 * 
 * 문제해결 프로세스 (조합)
 * 1. 조합으로 9C7의 경우 모두 구합
 * 2. 합이 100이 되는 경우 출력
 * ------------------------
 * 뺄 것 9C2로도 가능!
 * -------------------
 * 조합 구현시 for문 2개로하는게 재귀보다 나을듯
 * 
 * 
 * 제한조건
 * 9개의 숫자는 1~100
 * 
 * 
 * 시간복잡도
 * O(9C7* )
 * 
 * 
 */

public class Main_BJ_3040_백설공주와일곱난쟁이_이현영 {
	static int total, result, hat[], deletehat[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		hat = new int[9];
		for (int i = 0; i < 9; i++) {
			hat[i] = Integer.parseInt(br.readLine());
			total += hat[i];
		}
		result=total;
		deletehat = new int[2];
		comb(0,0);
		
		
	}
	//제거할 모자 수 2개
	public static void comb(int cnt, int start) {
		if(cnt==2) {
			for(int x: deletehat) {
				result-=x;
			}
			if(result==100) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < 9; i++) {
					if(hat[i]!=deletehat[0] && hat[i]!=deletehat[1]) {
						sb.append(hat[i]).append('\n');
					}
				}
				System.out.println(sb);
				System.exit(0);
			}
			result=total;
			return;
		}
		for (int i = start; i < 9; i++) {
			deletehat[cnt] = hat[i];
			comb(cnt+1, i+1);
		}
		
		
	}
	
}
