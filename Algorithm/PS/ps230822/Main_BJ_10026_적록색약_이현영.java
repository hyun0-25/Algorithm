package ps230822;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작시간: 13:09
 * 종료시간: 14:03 ~ 14:19
 * 
 * 
 * 문제 해석
 * 적록색약 - R(빨강),G(초록) 구분 못함
 * NxN인 그리드의 각 칸에 R,G,B중 하나를 색칠
 * 구역은 같은 색으로 이루어져 있음
 * 같은 색상이 상하좌우로 인접해있으면 두 글자는 같은 구역
 * 
 * 적록색약이 아닌 사람이 봤을 때의 구역의 수와 적록색약인 사람이 봤을 때의 구역의 수를 구하여라
 * 
 * 입력
 * 첫째줄: N
 * 둘째줄~N개줄: 그림
 * 
 * 출력
 * 적록색약이 아닌 사람이 봤을 때의 구역의 수와 적록색약인 사람이 봤을 때의 구역의 수
 * 
 * 문제해결 프로세스 (DFS)
 * char [] : colorRGB, colorRB
 * boolean[]: isvisited
 * 1. (0,0)부터 dfs로 사방탐색하며 방문처리(isvisited)
 * 2. 시작 문자에 대해서 사방탐색이 완료되었다면 cnt++
 * 3. 1,2번을 (n,n)까지 방문하지 않은 인덱스에 대해 실행
 * 4. colorRGB, colorRB에 대한 각 결과를 출력
 * 
 * 
 * 제한조건
 * N<=100
 * 
 * 
 * 시간복잡도
 * O(N*N*2)
 * 
 * 
 */

public class Main_BJ_10026_적록색약_이현영 {
	static char colorRGB[][] , origin;
	static boolean isVisited[][];
	static int N, cnt1, cnt2;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		colorRGB = new char[N][N];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < N; j++) {
				colorRGB[i][j] = str.charAt(j);
			}
		}
		
		isVisited= new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(!isVisited[i][j]) {
					//시작 색깔
					origin = colorRGB[i][j];
					dfs(i,j);
					cnt1++;
				}
			}
		}
		isVisited= new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(!isVisited[i][j]) {
					//시작 색깔
					origin = colorRGB[i][j];
					dfs(i,j);
					cnt2++;
				}
			}
		}

		System.out.printf("%d %d", cnt1, cnt2);
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	
	public static void dfs(int x, int y) {
		if(x<0 || x>=N || y<0 || y>=N) return;
		if(colorRGB[x][y]!=origin) return;
		if(isVisited[x][y]) return;
		
		isVisited[x][y]=true;
		//2번째 적록색맹DFS를 위해서 G->R로 통일
		if(colorRGB[x][y]=='G') colorRGB[x][y]='R';
		for (int k = 0; k < 4; k++) {
			int nx = x+dx[k];
			int ny = y+dy[k];
			dfs(nx,ny);
		}
	}

}
