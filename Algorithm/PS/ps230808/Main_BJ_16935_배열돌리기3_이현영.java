package ps230808;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 13:54
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * NxM 배열이 있을 때, 연산 R번 적용
 * 6가지의 연산
 * 1. 상하 반전 (for문 i로만 돌아도 괜찮을듯)
 * 2. 좌우 반전 
 * 3. 오른쪽으로 90도 회전 (회전하면 MxN으로 바뀜)
 * 4. 왼쪽으로 90도 회전 (회전하면 MxN으로 바뀜)
 * 5,6번 4등분을 해서 델타 배열로 만든다. 5번은 시계방향, 6번은 반시계방향
 * 5. 1/4등분 => 1번->2번, 2번->3번, 3번->4번, 4번->1번
 * 6. 1/4등분 => 1번->4번, 4번->3번, 3번->2번, 2번->1번
 *
 * 구해야 하는 것
 * for 
 * 
 * 
 * 
 * 
 * 문제 입력
 * 첫째줄: 배열의 크기 N,M, 연산의 수 R
 * 둘째줄부터~ N개의줄 : 배열의 숫자들
 * 마지막줄: 연산 횟수 A
 *
 * 제한 요소
 * N,M <=100
 * R<=1000
 *
 * 생각나는 풀이
 * for i=0; i<N; i++
 * 	for j=0; j<M; j++
 * 	  map[i][j]
 * 
 * 1. 열은 동일, 행을 위아래 반전 (i->N-i-1)
 * for i=0; i<N; i++
 * 	for j=0; j<M; j++
 *   new[N-i-1][j]=map[i][j]
 * 2. 행은 동일, 열을 좌우 반전 (j->M-j-1)
 * for i=0; i<N; i++
 * 	for j=0; j<M; j++
 *   new[i][M-j-1]=map[i][j]
 * 3. 행 (i->열 M-i-1) 열 (j->행j)
 *  new[j][M-i-1]=map[i][j]
 * 4. 행 (i->열 i) 열 (j->행N-j-1)
 *  new[N-j-1][i]=map[i][j]
 *  
 * 5.
 * 
 * 1번 그룹 (행 동일, 열+=M/2)
 * for i=0; i<N/2; i++
 * 	for j=0; j<M/2; j++
 * 	 new[i][j+M/2]=map[i][j]
 * 
 * 2번 그룹 (행+=N/2 , 열)
 * for i=N/2; i<N; i++
 * 	for j=M/2; j<M; j++
 * 	 new[i][j+N/2]=map[i][j]
 * 
 * 시간복잡도
 * A 연산횟수 * 
 * 1000 *100*100
 * 
 * 
 * 구현해야 하는 기능
 *
 *
 */

public class Main_BJ_16935_배열돌리기3_이현영 {
	static int N,M,R, map[][], result[][], fun_num[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int [N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		int idx=0;
		fun_num = new int[R];
		for (int i = 0; i < R; i++) {
			fun_num[i]=Integer.parseInt(st.nextToken());
		}
		
		
		for (int i = 0; i < R; i++) {
			map = fun(fun_num[i], map);
		}
		
		//결과 출력
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append(map[i][j]).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
	
	
	
	public static int[][] func1(int[][] m) {
		result = new int [N][M];
		 for (int i=0; i<N; i++) {
			 for (int j=0; j<M; j++) {
				 result[N-i-1][j]=m[i][j];
			 }
		 }
		 return result;
	}
	
	public static int[][] func2(int[][] m) {
		result = new int [N][M];
		 for (int i=0; i<N; i++) {
			 for (int j=0; j<M; j++)
				 result[i][M-j-1]=m[i][j];
		 }
		 return result;
	}
	
	public static int[][] func3(int[][] m) {
		result = new int [M][N];
		for (int i=0; i<N; i++) {
			for (int j=0; j<M; j++) {
				result[j][N-i-1]=m[i][j];
			}
		}
		int tmp = N;
		N=M;
		M=tmp;
		return result;
	}
	public static int[][] func4(int[][] result) {
		result = new int [M][N];
		for (int i=0; i<N; i++) {
			for (int j=0; j<M; j++) {
				result[M-j-1][i]=map[i][j];
			}
		}
		int tmp = N;
		N=M;
		M=tmp;
		return result;
	}
	
	public static int[][] func5(int[][] m) {
		result = new int [N][M];
		 for (int i=0; i<N/2; i++) {
			 for (int j=0; j<M/2; j++)
				 result[i][j+M/2]=m[i][j];
		 }
		 for (int i=0; i<N/2; i++) {
			 for (int j=M/2; j<M; j++)
				 result[i+N/2][j]=m[i][j];
		 }
		 for (int i=N/2; i<N; i++) {
			 for (int j=M/2; j<M; j++)
				 result[i][j-M/2]=m[i][j];
		 }
		 for (int i=N/2; i<N; i++) {
			 for (int j=0; j<M/2; j++)
				 result[i-N/2][j]=m[i][j];
		 }
		 return result;
	}
	
	public static int[][] func6(int[][] m) {
		result = new int [N][M];
		 for (int i=0; i<N/2; i++) {
			 for (int j=0; j<M/2; j++)
				 result[i+N/2][j]=m[i][j];
		 }
		 for (int i=0; i<N/2; i++) {
			 for (int j=M/2; j<M; j++)
				 result[i][j-M/2]=m[i][j];
		 }
		 for (int i=N/2; i<N; i++) {
			 for (int j=M/2; j<M; j++)
				 result[i-N/2][j]=m[i][j];
		 }
		 for (int i=N/2; i<N; i++) {
			 for (int j=0; j<M/2; j++)
				 result[i][j+M/2]=m[i][j];
		 }
		 return result;
	}
	
	
	
	public static int[][] fun(int fun_num, int[][] m) {
		switch(fun_num) {
		case 1:
			return func1(m);
		case 2:
			return func2(m);
		case 3:
			return func3(m);
		case 4:
			return func4(m);
		case 5:
			return func5(m);
		case 6:
			return func6(m);
		}
		return m;
	}
}
