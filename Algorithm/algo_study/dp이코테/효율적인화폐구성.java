package dp이코테;

import java.util.Scanner;

/**
 * 문제 해석
 * N가지 종류의 화폐
 * 화폐들을 최소한으로 이용해서 가치의 합이 M원이 되도록
 * M원을 만들기 위한 최소한의 화폐 개수를 출력
 * 
 * 입력
 * 첫째줄: 화폐종류 개수 N, 화폐 합 M
 * 둘째줄부터 N줄: 화폐의 가치 N개
 * 
 * 출력
 * 최소 화폐 개수
 * 불가능한 경우 -1
 * 
 */



public class 효율적인화폐구성 {
	static int N,M;
	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		int [] dollar = new int[M+1];
		for (int i = 1; i < M+1; i++) {
			dollar[i]=10001;
		}
		int d;
		for (int i = 0; i < N; i++) {
			d = sc.nextInt();
			for (int j = d; j <= M; j+=d) {
				if(dollar[j-d]!=10001) {
					dollar[j] = Math.min(dollar[j], dollar[j-d]+1); 
				}
			}
		}
		if(dollar[M]==10001) System.out.println(-1);
		else System.out.println(dollar[M]);
	}

}
