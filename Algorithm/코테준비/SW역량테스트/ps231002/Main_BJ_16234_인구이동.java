package SW역량테스트.ps231002;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 8:10
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN크기의 땅, 각각 땅에는 나라가 하나씩 존재
 * (r,c)에 있는 나라에는 A[r][c]명 살고 있음
 * 인접한 나라 사이에는 국경선이 존재
 * - 인구이동
 * 	국경선을 공유하는 두 나라의 인구 차이가 L명이상, R명 이하면, 두 나라가 공유하는 국경선을 오늘 하루동안 엶
 * 	열 수 있는 국경선을 모두 열면 인구이동 시작
 * 	연합을 이루고 있는 각 칸의 인구수는 (연합 인구수)/(연합 이루는 칸의 수) -> 소수점 버림
 * 	연합을 해체하고 모든 국경선 닫음
 * 각 나라의 인구수가 주어졌을 때, 인구 이동이 며칠 동안 발생하는지
 * 
 * 입력
 * 첫째줄 : N 맵크기, L최소, R최대
 * N개줄 : 각 나라의 인구수 A[r][c]
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스 bfs
 * 1. isvisited[][]생성, (0,0)부터 (n-1,n-1)까지
 * 	아직 방문x -> bfs시작, arraylist에 넣기
 * 2. bfs
 * 	2-1. 아직 방문x
 * 		인구차이 가능하면 큐에 넣음 -> 총 인구수+= , arraylist에 넣기
 * 		인구차이 불가능하면 continue
 * 	2-2. 이미 방문 continue
 * 3. 방문 끝나면 -> 총인구수/arraylist의 크기
 * 	arraylist안에 들어있는 노드들에 대입
 * 4. bfs방문 횟수가 n*n과 동일하면 종료
 * 
 * 
 * 
 * 
 * 제한조건
 * N<=50
 * L,R<=100
 * A[][]<=100
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_16234_인구이동 {
	static int N,L,R, map[][];
	static boolean[][] isvisited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int result=0;
		while(true) {
			int cnt = 0;
			isvisited = new boolean[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(!isvisited[i][j]) {
						bfs(i,j);
						cnt++;
					}
				}
			}
			if(cnt==N*N) break;
			result++;
		}
		System.out.println(result);
	}
	
	/* 문제해결 프로세스 bfs
	 * 1. isvisited[][]생성, (0,0)부터 (n-1,n-1)까지
	 * 	아직 방문x -> bfs시작, arraylist에 넣기
	 * 2. bfs
	 * 	2-1. 아직 방문x
	 * 		인구차이 가능하면 큐에 넣음 -> 총 인구수+= , arraylist에 넣기
	 * 		인구차이 불가능하면 continue
	 * 	2-2. 이미 방문 continue
	 * 3. 방문 끝나면 -> 총인구수/arraylist의 크기
	 * 	arraylist안에 들어있는 노드들에 대입
	 * 4. bfs방문 횟수가 n*n과 동일하면 종료
	 */
	static class Pair{
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	
	public static void bfs(int x, int y) {
		int total = 0;
		List<Pair> list = new ArrayList<>();
		Queue<Pair> q = new ArrayDeque<>();
		
		Pair np = new Pair(x,y);
		q.offer(np);
		list.add(np);
		isvisited[x][y] = true;
		total += map[x][y];
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(nx<0 || nx>=N || ny<0 || ny>=N || isvisited[nx][ny]) continue;
				
				int minus = Math.abs(map[nx][ny]-map[p.x][p.y]);
				if(minus > R || minus < L) continue;
				
				//else
				np = new Pair(nx,ny);
				q.offer(np);
				list.add(np);
				isvisited[nx][ny] = true;
				total += map[nx][ny];
			}
		}
		
		if(list.size()==1) return;
		
		int num = total/list.size();
		for (int i = 0; i < list.size(); i++) {
			int mx = list.get(i).x;
			int my = list.get(i).y;
			map[mx][my] = num;
		}
	}
}
