package ps230817;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:07
 * 종료시간:
 * 
 * 
 * 문제 해석
 * RxC 표 모양 보드
 * 보드의 각 칸에는 대문자 알파벳이 하나씩 적혀있음 (1,1)에는 말
 * 말은 상하좌우로 인접한 4칸 중의 1칸으로 이동
 * 새로 이동한 칸에 적혀 있는 알파벳은 지금까지 지나온 모든 칸에 적혀있는 알파벳과 달라야함
 * 즉, 같은 알파벳이 적힌 칸을 2번 지날 수 없다
 * 좌측 상단에서 시작해서, 말이 최대한 몇 칸을 지날 수 있는지 구하여라
 * 말이 지나는 칸은 시작인(1,1)도 포함
 * 
 * 입력
 * 첫째줄: R,C
 * 둘째줄~R개줄: 대문자 알파벳들
 * 
 * 출력
 * 말이 지날 수 있는 최대의 칸 수
 * 
 * 문제해결 프로세스 (DFS)
 * Alpa = new boolean [26];
 * int c = '대문자 알파벳'-65; 
 * 
 * dfs(int x, int y, int count);
 * 1. (1,1)에서 출발해서 해당 위치의 알파벳의 Alpa[i]값
 * 	1-1. 이미 true면 그 방향 못감
 * 		return;
 *  1-2. false면 그 방향 갈 수 있음
 *  	dfs(nx,ny,count+1);
 *  	Alpa[i] = true;
 * 2. count의 최댓값 구하기
 * 
 * 
 * 제한조건
 * R,C<=20
 * 
 * 
 * 시간복잡도
 * O(R*C
 * 
 * 
 */
public class Main_BJ_1987_알파벳_이현영 {
	static int R,C, max;
	static int map[][];
	static boolean isVisited [][];
	static boolean Alpa [] = new boolean[26];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		isVisited = new boolean[R][C];
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				map[i][j]=str.charAt(j)-65;
			}
		}
		dfs(0,0,1);
		System.out.println(max);
		
	}
	/* dfs(int x, int y, int count);
	 * 1. (1,1)에서 출발해서 해당 위치의 알파벳의 Alpa[i]값
	 * 	1-1. 이미 true 또는 isVisited면 그 방향 못감
	 * 		return;
	 *  1-2. false면 그 방향 갈 수 있음
	 *  	dfs(nx,ny,count+1);
	 *  	Alpa[i] = true;
	 * 2. count의 최댓값 구하기
	 */
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static int dfs(int x, int y, int count) {
		int idx = map[x][y];
		if(Alpa[idx] || isVisited[x][y]) {
			max = Math.max(max, count-1);
			return count-1;
		}
		
		else if(!Alpa[idx] && !isVisited[x][y]) {
			Alpa[idx]=true;
			isVisited[x][y]=true;
		}
		
		for (int i = 0; i < 4; i++) {
			int nx = x+dx[i];
			int ny = y+dy[i];
			//맵 밖이거나 이미 방문했거나 이미 갔던 곳이면 continue;
			if(nx<0 || nx>=R || ny<0 || ny>=C) continue;
			//else
			dfs(nx,ny,count+1);
		}
		Alpa[idx]=false;
		isVisited[x][y]=false;
		return count; 
	}

}
