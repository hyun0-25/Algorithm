package ps230814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 17:15
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN인 행렬 A
 * A의 B제곱을 구하여라
 * A^B의 각 원소를 1000으로 나눈 나머지를 출력
 * 
 * 
 * 입력
 * 첫째줄: 행렬의 크기 N, B
 * 둘째줄~N개의 줄: 행렬의 각 원소
 * 
 * 
 * 출력
 * N개의 줄에 걸쳐 행렬 A를 B제곱한 결과를 출력
 * 
 * 문제해결 프로세스
 * 1. 행렬의 곱을 구해주는 matrix함수 구현
 * 2. 
 * 
 * 
 * 제한조건
 * 2<=N<=5
 * 1<=B<=100,000,000,000 = 10^11
 * 0<=원소의 값<=1000
 * 
 * 시간복잡도
 * 
 * 
 */
public class BJ_10830 {
	static long matrix[][];
	static int N;
	static long B;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());
		matrix = new long [N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		matrix = exp(matrix, B);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(matrix[i][j]%1000).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
	public static long[][] matrixPow(long [][] A, long[][] B) {
		long[][] result = new long [N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				result[i][j] = matrixIdxSum(i,j,A, B);
			}
		}
		return result;
	}
	
	public static long matrixIdxSum(int x, int y, long[][] A, long[][] B) {
		long sum = 0;
		for (int i = 0; i < N; i++) {
			sum += A[x][i]*B[i][y];
		}
		return sum%1000;
	}
	
	public static long[][] exp(long[][] x, long n) {
		if(n==1) return matrix;
		long [][] y = exp(x, n/2);
		return (n%2==0) ? matrixPow(y,y): matrixPow(matrixPow(y,y), matrix);
	}
}
