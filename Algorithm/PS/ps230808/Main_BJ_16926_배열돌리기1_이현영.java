package ps230808;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 16:32
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * NxM 배열 -> 배열을 R번 회전 시킨 결과
 * 
 * 구해야 하는 것
 * 배열을 R번 회전 시킨 결과
 * 
 * 문제 입력
 * 첫번째줄 : 배열의 크기 N,M, 수행 회전 수 R
 * 
 * 제한 요소
 * N,M<=300
 * R<=1000
 * 
 *
 * 생각나는 풀이
 * (dx와 dy를 이용)
 * 1. 가장 바깥 부분부터 중심부로 탐색
 * 2. (1,1)에서 시작 -> 하,우,상,좌 순서대로 한칸씩 이동
 * 	(경계밖을 만나거나 다른 숫자로 차있으면 방향 전환)
 * 3. 더 이상 갈 곳이 없으면 (2,2)에서 2번 반복
 * ...
 * 4. 회전 깊이는 최대 d = (N과 M의 최솟값)/2
 *    (i,i) = (0,0) ~ (d-1,d-1)까지
 * 
 * 시간복잡도 
 * 
 *
 * 구현해야 하는 기능
 *
 */

public class Main_BJ_16926_배열돌리기1_이현영 {
	static int N,M,R, map[][], result[][], idx;
	//하,우,상,좌 순서
	static int[] dx = {1,0,-1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j]=Integer.parseInt(st.nextToken());
			}
		}
		idx = (Math.min(N, M))/2;
		
		for (int i = 0; i < R; i++) {
			result = new int[N][M];
			map = rotate(map);
		}
		
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append(map[i][j]).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
	public static int[][] rotate(int[][] A) {
		int k=0, cnt=0;
		int nx=0, ny=0;
		while(cnt<idx) {
			nx += dx[k];
			ny += dy[k];
			//경계밖을 만나거나 다른 숫자로 차있으면 방향 전환
			if(nx<0 || nx>=N || ny<0 || ny>=M || result[nx][ny]!=0) {
				nx -= dx[k];
				ny -= dy[k];
				k++;
				if(k==4){
					//회전 깊이
					cnt++;
					//방향 초기화
					k%=4;
					nx=cnt;
					ny=cnt;
				}
				continue;
			}
			
			//A배열 이동한 값 -> result에 저장
			result[nx][ny] = A[nx-dx[k]][ny-dy[k]];
		}
		return result;
	}
}