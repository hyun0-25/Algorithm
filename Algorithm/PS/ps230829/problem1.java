package ps230829;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 시작시간: 11:34
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 아파트를 각 층별로 파란색 또는 노란색 페인트로 칠함
 * 규칙
 * 1. 노란색은 인접한 두 층에 연속하여 사용 가능
 * 2. 파란색은 인접한 두 층에 연속하여 사용 불가능
 * 층의 아파트를 칠 할 수 있는 방법의 수를 f(n)
 * f(1)=2. f(2)=3
 * f(8)=?
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * f(1) = 2
 * f(2) = 3
 * 
 * f(8) = 55
 * 
 * 
 * 노랑, 파랑 개수 저장
 * f(n+1) = f(n) + f(n-1)
 *  노 파        n+1노      n+1파
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class problem1 {
	static int f[] = new int [100];
	public static void main(String[] args) {
		f[1] = 2;
		f[2] = 3;
		
		System.out.println(floor(8));
		
	}
	public static int floor(int num) {
		if(f[num]==0) {
			f[num] = floor(num-1) + floor(num-2);
		}
		return f[num];
	}
}
