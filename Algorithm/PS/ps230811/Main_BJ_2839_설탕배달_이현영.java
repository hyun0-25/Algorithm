package ps230811;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작시간: 13:39
 * 종료시간: 13:45
 * 
 * 
 * 문제 해석
 * 설탕을 정확하게 N킬로그램 배달
 * 봉지는 3kg봉지와 5kg봉지
 * 최대한 적은 봉지를 들고가려함
 * 18kg = 3*6 (x)
 *      = 5*3 + 3*1 (o)
 * 설탕을 정확하게 N킬로그램 배달해야 할 때, 봉지 몇 개를 가져가면 되는지 구하여라
 * 
 * 입력
 * 첫째 줄: N
 * 
 * 
 * 출력
 * 배달하는 봉지의 최소 개수 
 * 정확하게 N킬로그램을 만들 수 없으면 -1 출력
 * 
 * 문제해결 프로세스 (그리디)
 * 1. 설탕을 5kg 봉지에 최대한 담기
 * 2. 남은 설탕 3kg 봉지로 담기
 * 3. N킬로그램과 같지 않으면 5kg봉지 하나 제거하고 3kg으로 담을 수 있는지 확인
 * 4-1. 3번 반복
 * 4-2. 5kg봉지 0이 되고, 3kg으로 모두 담아도 N과 같지 않으면 -1출력
 * 
 * 
 * 제한조건
 * 3<=N<=5000
 * 
 * 
 * 시간복잡도
 * N = 5000/5 = 최대1000봉지
 * 최악 -> 1000번 가능한 횟수 확인
 * O(N/5)
 * 
 */

public class Main_BJ_2839_설탕배달_이현영 {
	static int N, n5, n3;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		/**
		 * 문제해결 프로세스 (그리디)
		 * 1. 설탕을 5kg 봉지에 최대한 담기
		 * 2. 남은 설탕 3kg 봉지로 담기
		 * 3. N킬로그램과 같지 않으면 5kg봉지 하나 제거하고 3kg으로 담을 수 있는지 확인
		 * 4-1. 3번 반복
		 * 4-2. 5kg봉지 0이 되고, 3kg으로 모두 담아도 N과 같지 않으면 -1출력 
		 */
		
		//설탕을 5kg 봉지에 최대한 담기
		n5 += N/5;
		//남은 설탕
		N %= 5;
		//남은 설탕 3kg 봉지로 담기
		n3 += N/3;
		N %= 3;
		if(N==0) {
			System.out.println(n5+n3);
			return;
		}
		else if(N==1) {
			//나머지 1이면 +5
			//5kg봉지가 1개라도 있으면 +5가능
			if(n5>0) {
				n5--;
				n3+=2;
				System.out.println(n5+n3);
				return;
			}
			else {
				System.out.println(-1);
				return;
			}
		}
		else if(N==2) {
			//나머지 2이면 +5+5
			//5kg봉지가 2개라도 있으면 +5+5가능
			if(n5>1) {
				n5-=2;
				n3+=4;
				System.out.println(n5+n3);
				return;
			}
			else {
				System.out.println(-1);
				return;
			}
		}
		
	}
}
