package A형대비.ps230824;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:41
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 새집은 NxN의 격자판
 * 각 칸은 (r,c), 빈칸 또는 벽
 * 파이프는 3가지 방향, 2칸차지 -> 좌우, 상하, 좌상우하(대각선) 
 * 
 * 파이프를 밀어서 이동, 벽은 이동불가, 빈칸만 차지 가능
 * 파이프를 미는 방향 3가지 -> 우, 우하, 하
 * 파이프는 밀면서 회전 가능 -> 45도만 회전 (한번에 90도 불가능)
 * 미는 방향 -> 우, 우하, 하
 * 
 * 가장 처음 파이프는 (1,1),(1,2) 차지, 방향은 가로
 * 파이프의 한쪽 끝을 (N,N)로 이동시키는 방법의 개수
 * 
 * 입력
 * 첫째줄: 집의 크기 N
 * 둘째줄~N개줄: 집의 상태
 * 빈칸0,벽1
 * 
 * 출력
 * 파이프의 한쪽 끝을 (N,N)로 이동시키는 방법의 개수
 * 
 * 문제해결 프로세스 (dp)
 * 점화식
 * dp[i][j] = 
 * 
 * 
 * 문제해결 프로세스 (dfs)
 * 1. 파이프 3가지- 좌우 - 우,대각선 
 * 				 상하 - 대각선,하
 * 				 대각선 - 우,대각선,하
 * 2. dfs이동해서 (N,N)에 도착하면 result++;
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_17070_파이프옮기기1_이현영_백트래킹 {
	static int N, map[][], result;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st ;
		
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//(0,1) 우 방향으로 시작
		dfs(0,1,0);
		System.out.println(result);
	}
	
	/* 문제해결 프로세스 (dfs)
	 * 1. 파이프 3가지- 좌우 - 우,대각선 
	 * 				 상하 - 대각선,하
	 * 				 대각선 - 우,대각선,하
	 * 2. dfs이동해서 (N,N)에 도착하면 result++;
	 */
	
	//우, 대각선, 하 순서
	static int dx[] = {0,1,1};
	static int dy[] = {1,1,0};
	
	public static void dfs(int x, int y, int dir) {
		
		//범위 밖으로 나감
		if(x<0 || x>=N || y<0 || y>=N) {
			return;
		}
		
		//벽 긁음
		//우 - 긁: 우
		if(dir==0 && map[x][y]==1) {
			return;
		}
		//대각선 - 긁: 우,대각선,하
		else if(dir==1 && (map[x][y]==1 || map[x-1][y]==1 || map[x][y-1]==1)){
			return;
		}
		//하 - 긁: 하
		else if(dir==2 && map[x][y]==1) {
			return;
		}
		
		//정상적으로 도착한 경우
		if(x==N-1 && y==N-1) {
			result++;
			return;
		}
		
		int nx=0, ny=0;
		//좌우 - 우,대각선 
		if(dir == 0) {
			//우
			nx = x+dx[0];
			ny = y+dy[0];
			dfs(nx,ny,0);
			//대각선
			nx = x+dx[1];
			ny = y+dy[1];
			dfs(nx,ny,1);
		}
		//대각선 - 우,대각선,하
		else if(dir == 1) {
			//우
			nx = x+dx[0];
			ny = y+dy[0];
			dfs(nx,ny,0);
			//대각선
			nx = x+dx[1];
			ny = y+dy[1];
			dfs(nx,ny,1);
			//하
			nx = x+dx[2];
			ny = y+dy[2];
			dfs(nx,ny,2);
		}
		//상하 - 대각선,하
		else if(dir == 2) {
			//대각선
			nx = x+dx[1];
			ny = y+dy[1];
			dfs(nx,ny,1);
			//하
			nx = x+dx[2];
			ny = y+dy[2];
			dfs(nx,ny,2);
		}
	}

}
