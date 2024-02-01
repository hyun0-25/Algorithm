package ps231004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:06
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 모든 섬을 다리로 연결
 * NxM 크기의 이차원 격자, 땅 또는 바다
 * 다리는 	바다에만 건설 가능, 다리 길이는 격자에서 차지하는 칸의 수
 * 다리를 연결해서 모든 섬을 연결
 * 다리의 길이는 2 이상, 가로 또는 세로 방향의 일직선
 * 섬A,B를 연결하는 다리가 섬C와 인접한 바다를 지나는 경우 C가 A,B와 연결된것이 아님
 * 다리가 교차하는 경우도 가능, 교차하는 다리의 길이를 계산할 떄는 각 칸이 각 다리의 길이에 모두 포함
 * 
 * 
 * 입력
 * 첫째줄 : N,M
 * N개줄 : 지도 정보 0:바다,1:땅
 * 
 * 출력
 * 모든 섬을 연결하는 다리 길이의 최솟값
 * 불가능하면 -1
 * 
 * 
 * 문제해결 프로세스
 * 0. 섬 생성 -> bfs
 * 1. 각 섬이 이을 수 있는 섬 정보와 다리 길이(최소2) 저장, 섬개수-1까지
 * 	1-1. 섬의 각 칸이 자신과 같은 번호 만나거나 벽이면 break;
 * 	1-2. 섬이 다른 섬을 만나고 다리 길이가 2이상이면 다리길이 정보갱신(최솟값으로)
 * 2. 부분집합으로 다리 선택
 * 	2-1. 최솟값보다 크면 가지치기
 * 3. 선택한 다리들의 요소가 섬의 개수와 동일하면 최솟값 갱신
 * 4. 최솟값 출력
 * 
 * 
 * 
 * 제한조건
 * N,M<=10
 * 2<=섬개수<=6
 * 
 * 
 * 시간복잡도
 * 만들수 있는 다리 수 최대 6C2=15, 부분집합->2^15 + 모든섬 선택했는지t/f
 * 
 * 
 */

public class Main_BJ_17472_다리만들기2_이현영 {
	static int N,M,islandnumber;
	static int min = Integer.MAX_VALUE;
	static int parents[];
	
	static char board[][];
	static boolean isvisited[][];
	
	static List<Bridge> list = new ArrayList<>();
	static class Bridge implements Comparable<Bridge>{
		char first,second;
		int length;
		public Bridge(char first, char second, int length) {
			super();
			this.first = first;
			this.second = second;
			this.length = length;
		}
		@Override
		public String toString() {
			return "Bridge [first=" + first + ", second=" + second + ", length=" + length + "]";
		}
		@Override
		public int compareTo(Bridge o) {
			return this.length-o.length;
		}
		
	}
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
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new char[N][M];
		isvisited = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = st.nextToken().charAt(0);
			}
		}
		
		
		char alpa = 'A';
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(board[i][j]=='1' && !isvisited[i][j]) makeisland(i,j,alpa++);
			}
		}
		
		isvisited = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(board[i][j]!='0' && !isvisited[i][j]) makebridge(i,j,board[i][j]);
			}
		}
		if(list.size()==0) {
			System.out.println(-1);
			return;
		}
		
		//else
		//섬개수 = 노드수
		//list크기 = 간선수
		islandnumber = alpa-65;
		Collections.sort(list);
		//make, find, union
		make();
		//MST시작 : 비용이 작은 간선 순으로 꺼내서 union=true(합집합 성공)면 비용에 더함
		//기저조건 : (연결한 간선수)=(총 간선 수-1)
		int cost = 0;
		int count = 0;
		for(Bridge bg : list) {
			//합집합 성공
			if(union(bg.first-65, bg.second-65)) {
				cost += bg.length;
				count++;
				if(count==islandnumber-1) break;
			}
		}
		//섬을 모두 선택하지 않은 경우
		if(count!=islandnumber-1) {
			System.out.println(-1);
			return;
		}
		
		System.out.println(cost);
	}
	public static void make() {
		parents = new int[islandnumber];
		for (int i = 0; i < islandnumber; i++) {
			parents[i] = i;
		}
	}
	public static int find(int a) {
		if(parents[a]==a) return a;
		return parents[a] = find(parents[a]);
	}
	//부모 다르면 합집합 성공 true
	//부모 같으면 싸이클발생 실패 false
	public static boolean union(int a, int b) {
		int aroot = find(a);
		int broot = find(b);
		//부모같음
		if(aroot==broot) return false;
		//합집합 실행
		parents[broot] = aroot;
		return true;
	}
	/* 문제해결 프로세스 -> 틀림
	 * 0. 섬 생성 -> bfs
	 * 1. 각 섬이 이을 수 있는 섬 정보와 다리 길이(최소2) 저장, 섬개수-1까지
	 * 	1-1. 섬의 각 칸이 자신과 같은 번호 만나거나 벽이면 break;
	 * 	1-2. 섬이 다른 섬을 만나고 다리 길이가 2이상이면 다리길이 정보갱신(최솟값으로)
	 * 2. 부분집합으로 다리 선택
	 * 	2-1. 최솟값보다 크면 가지치기
	 * 3. 선택한 다리들의 요소가 섬의 개수와 동일하면 최솟값 갱신
	 * 4. 최솟값 출력
	 * 
	 * 문제해결 프로세스 -> MST
	 * 	크루스칼
	 */
	
	
	public static void makebridge(int xx, int yy, char island) {
		isvisited[xx][yy] = true;
		for (int i = 0; i < 4; i++) {
			int nx = xx;
			int ny = yy;
			int cnt = 0;
			while(true) {
				nx += dx[i];
				ny += dy[i];
				//1-1. 섬의 각 칸이 자신과 같은 번호 만나거나 벽이면 break;
				if(!rangecheck(nx,ny) || board[nx][ny]==island) break;
				else if (board[nx][ny]=='0') cnt++;
				else if (board[nx][ny]!=island) break;
			}
			//1-2. 섬이 다른 섬을 만나고 다리 길이가 2이상이면 다리길이 정보갱신(최솟값으로)
			boolean exist = false;
			if(cnt>=2 && rangecheck(nx,ny)) {
				for(Bridge bg : list) {
					if((bg.first==board[xx][yy] && bg.second==board[nx][ny]) || (bg.second==board[xx][yy] && bg.first==board[nx][ny])) {
						bg.length = Math.min(bg.length, cnt);
						exist = true;
					}
				}
				if(!exist) {
					list.add(new Bridge(board[xx][yy], board[nx][ny], cnt));
				}
			}
		}
	}
	
	public static void makeisland(int xx, int yy, char island) {
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(xx, yy));
		isvisited[xx][yy] = true;
		board[xx][yy] = island;
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(!rangecheck(nx,ny) || isvisited[nx][ny] || board[nx][ny]=='0') continue;
				//else
				q.offer(new Pair(nx, ny));
				isvisited[nx][ny] = true;
				board[nx][ny] = island;
			}
			
		}
	}
	
	public static boolean rangecheck(int ox, int oy) {
		if(ox<0 || ox>=N || oy<0 || oy>=M) return false;
		return true;
	}

}
