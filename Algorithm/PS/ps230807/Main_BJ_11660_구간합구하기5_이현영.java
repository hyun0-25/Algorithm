package ps230807;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 16:22
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * NxN 크기의 표에서 (x1,y1)~(x2,y2)까지의 합
 *
 * 구해야 하는 것
 * 
 * 문제 입력
 * 첫째줄 : N -> 표의 크기 / 합을 구하는 횟수 -> M
 * 둘째줄~N개의 줄 : N개의 줄에는 표에 채워져 있는 수
 * 다음 M개의 줄 : x1,y1, x2,y2
 * 
 * 제한 요소
 * 1<=N<=1024, 1<=M<=100,000
 * 표에 채워져 있는 수 : 1<=x1,y1,x2,y2<=1000
 * 
 * 생각나는 풀이 1 (시간초과)
 * for i=x2-x1
 * 	for j=y2-y1
 * 	  sum+=map[i][j]
 * 시간복잡도 => O(1000*1000*M) = O(10^11) 불가능
 * 
 * 생각나는 풀이 2 (이것도 가능)
 * 행 기준 구간합?
 * 행 구간합 계산 : O(N*M) = O(1000*100,000) = O(10^8)
 * ------------
 * 인덱스 위치 i,j기준 누적합
 * O(N*N)
 * 
 * 구현해야 하는 기능
 *
 */

public class Main_BJ_11660_구간합구하기5_이현영 {
	static int N,M, map[][], sum[][];
	static int x1,y1,x2,y2;
	
	
//	public static int cal_sum(int x, int y) {
//		int result=0;
//		for (int i = 0; i <= x; i++) {
//			for (int j = 0; j <= y; j++) {
//				result+=map[i][j];
//			}
//		}
//		return result;
//	}
	
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		//map
		map = new int[N+1][N+1];
		for (int i =1; i < N+1; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j =1; j < N+1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//i,j 기준 누적합
		sum = new int[N+1][N+1];
		for (int i = 1; i < N+1; i++) {
			for (int j = 1; j < N+1; j++) {
				map[i][j]=map[i][j]+map[i-1][j]+map[i][j-1]-map[i-1][j-1];
			}
		}
		
		
		//계산
		StringBuilder sb = new StringBuilder();
		int result = 0;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			x1 = Integer.parseInt(st.nextToken());
			y1 = Integer.parseInt(st.nextToken());
			x2 = Integer.parseInt(st.nextToken());
			y2 = Integer.parseInt(st.nextToken());
			result = map[x2][y2]-map[x2][y1-1]-map[x1-1][y2]+map[x1-1][y1-1];
			sb.append(result).append('\n');
		}
		System.out.println(sb);
	}

}
