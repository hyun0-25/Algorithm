package ps230829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 시작시간: 14:57
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 정수 X에 사용할 수 있는 연산 3가지
 * 1. X가 3으로 나누어 떨어지면, 3으로 나눔
 * 2. X가 2로 나누어 떨어지면, 2로 나눔
 * 3. 1을 뺀다
 * 
 * 정수 N이 주어졌을 때, 3가지 연산을 적절히 사용해서 1을 만들려고할때, 연산 횟수의 최솟값
 * 
 * 
 * 입력
 * N
 * 
 * 출력
 * 1을 만들려고할때, 연산 횟수의 최솟값
 * 
 * 문제해결 프로세스
 * f(n)가 3로 나누어지면 f(n)=f(n/3)+1
 * 		 2로 나누어지면 f(n)=f(n/2)+1
 * 		 2,3로 안나눠짐 f(n)=f(n-1)+1
 * 
 * 
 * 제한조건
 *  1<=N<=10^6
 * 
 * 시간복잡도
 * O(n)
 * 
 * 
 */

public class Main_BJ_1463_1로만들기_이현영 {
	static int f[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		f = new int [1_000_001];
		f[1] = 0;
		f[2] = 1;
		f[3] = 1;
		
		System.out.println(divide(N));
	}
	
	public static int divide(int num) {
		if(num==1 || num==2 || num==3) return f[num];
		
		if(f[num]==0) {
			int div3 = divide(num/3);
			int div2 = divide(num/2);
			int minus1 = divide(num-1);
			if(num%3==0) {
				if(num%2==0) {
					int min = Math.min(div3+1, div2+1);
					min = Math.min(min, minus1+1);
					f[num] = min;
				}
				else {
					int min = Math.min(div3+1, minus1+1);
					f[num] = min;
				}

			}
			else if(num%2==0) {
				int min = Math.min(div2+1, minus1+1);
				f[num] = min;
			}
			else {
				f[num] = minus1 + 1;
			}
		}
		return f[num];
	}
}
