package ps230829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작시간: 14:10
 * 종료시간: 14:20
 * 
 * 
 * 문제 해석
 * 2xn 크기의 직사각형을 1x2, 2x1 타일로 채우는 방법의 수를 구하여라
 * 
 * 
 * 입력
 * 첫째줄 : n
 * 
 * 출력
 * 2xn크기의 직사각형을 채우는 방법의 수를 10,007로 나눈 나머지
 * 
 * 문제해결 프로세스
 * dp
 * f(n) = f(n-1) + f(n-2)
		세로붙이기     가로두개붙이기
 * f(1)=1;
 * f(2)=2;
 * ...
 * 3 ->3
 * 4 ->5
 * 5 ->8
 * 6 ->13
 * 
 * 제한조건
 * n<=1000
 * 
 * 
 * 시간복잡도
 * O(n)
 * 
 * 
 */

public class Main_BJ_11726_2xn타일링_이현영 {
	static int n;
	static long f[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		f = new long[1001];
		f[1]=1;
		f[2]=2;
		
		System.out.println(rectangle(n));
		
	}
	
	
	public static long rectangle(int number) {
		if(f[number]==0) {
			f[number] = rectangle(number-1)+rectangle(number-2);
		}
		return f[number];
	}
}
