package ps230829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작시간: 
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 2xn 직사각형을 1x2, 2x1, 2x2 타일로 채우는 방법의 수 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * dp
 * f(n) = f(n-1) + f(n-2)*2
 * 		  세로붙이기    가로두개 또는 2x2붙이기
 * f(1)=1
 * f(2)=3
 * 
 * 3->5
 * 4->11
 * 5->21
 * 6->43
 * 7->85
 * 8->171
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_11727_2xn타일링2_이현영 {
	static int n;
	static long f[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		f = new long[1001];
		f[1]=1;
		f[2]=3;
		
		System.out.println(rectangle(n));
	}
	
	
	public static long rectangle(int number) {
		if(f[number]==0) {
			f[number] = rectangle(number-1)+2*rectangle(number-2);
		}
		return f[number]%10007;
	}
}
