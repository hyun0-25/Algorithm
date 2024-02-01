package dp이코테;

import java.util.Scanner;

/**
 * 문제 해석
 * 정수 X가 주어졌을 때,
 * 1. X가 5로 나누어 떨어지면 5로 나눔
 * 2. X가 3로 나누어 떨어지면 3로 나눔
 * 3. X가 2로 나누어 떨어지면 2로 나눔
 * 4. X에서 1을 뺌
 * 
 * 정수 X가 주어졌을 때, 연산 4개를 적절히 사용해서 값을 1로 만듦
 * 연산을 사용하는 횟수의 최솟값을 출력
 * 
 * 26 -> 25 -> 5 -> 1
 * 
 * 점화식
 * a(i) = min(a(i-1),a(i/2),a(i/3),a(i/5))+1
 * 단, 1을 빼는 연산을 제외하고는 해당 수로 나누어 떨어질 때에 한해 점화식을 적용
 * 
 * 
 */


public class 숫자1로만들기 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		System.out.println(DP(n));
	}
	public static int DP(int n) {
		int dp[] = new int [n+1];
		for (int i = 2; i <= n; i++) {
			dp[i] = dp[i-1]+1;
			if (i%5==0) {
				dp[i] = Math.min(dp[i/5]+1, dp[i]);
			}
			else if (i%3==0) {
				dp[i] = Math.min(dp[i/3]+1, dp[i]);
			}
			else if (i%2==0) {
				dp[i] = Math.min(dp[i/2]+1, dp[i]);
			}
			else {
				dp[i] = Math.min(dp[i-1]+1, dp[i]);
			}
		}
		return dp[n];
	}
}
