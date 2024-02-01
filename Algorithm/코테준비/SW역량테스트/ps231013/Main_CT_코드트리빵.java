package SW역량테스트.ps231013;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 17:10
 * 종료시간: 
 * 
 * 
 * 문제 해석
 * 빵을 구하고자 하는 m명의 사람이 있을때
 * 1번사람->1분
 * 2번사람->2분
 * m번사람->m분에 각자의 베이스캠프에서 출발하여 편의점으로 이동
 * 사람들은 출발 시간이 되기 전까지 격자밖에 위치
 * 각자 목표로하는 편의점은 모두 다름
 * 이동규칙=3가지(총1분진행)
 * 1. 모든 사람이 가고 싶은 편의점 방향을 향해서 1칸 이동
 * 	최단거리로 이동, 최단거리가 여러가지면, 상좌우하 순으로 움직임
 * 	최단거리 = 편의점에 도달하기까지 거쳐야하는 칸의 수가 최소
 * 2. 편의점에 도착하면 해당 편의점에서 정지,
 * 	다른 사람들은 해당 편의점이 있는 칸을 지나갈 수 없음
 * 	격자에 있는 사람들이 모두 이동한 뒤에 해당 칸을 지나갈 수 없어짐
 * 3. 현재 시간이 t분이고 t<=m이면, 
 * 	t번 사람은 자신이 가고 싶은 편의점과 가장 가까이 있는(최단거리) 베이스 캠프로감
 *	가장 가까운 베이스캠프가 여러 가지면, 행이 작은 베이스캠프->행이 같으면 열이 작은 베이스캠프로
 *	(t번 사람이 베이스 캠프로 이동하는데에는 시간이 전혀 소요되지x)
 * 
 * 	다른 사람들은 해당 베이스 캠프가 있는 칸을 지날수없음
 * 	t번 사람이 편의점을 향해 움직이기 시작했더라도, 해당 베이스 캠프는 앞으로 절대 지날 수 없음
 * 	격자에 있는 모든 사람들이 모두 이동한 뒤에 해당 칸을 지나갈 수 없어짐
 * 
 * 이미 사람들이 도착한 편의점이나, 출발한적있는 베이스캠프는 절대 지날 수 없음
 * 몇분후 모두 편의점에 도착하는지 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 베이스 캠프 선택, bfs
 * 2. 상좌우하 중 어느 방향으로 출발한건지 저장,
 * 		최단거리에 도달하는 방향 출력,
 * 		그 방향으로 1칸 이동
 * 3. 자신의 편의점에 도착하면 list에서 제거 + 편의점위치 isvisited=true처리
 * 4. 다음사람 출발
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_CT_코드트리빵 {
	static int n,m,map[][],out;
	static boolean isvisited[][];
	static List<Pair> storelist = new ArrayList<>();
	static List<Pair> list = new ArrayList<>();
	static boolean outlist[];
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static class Move {
		int x,y,dir;
		public Move(int x, int y, int dir) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		isvisited = new boolean[n][n];
		outlist = new boolean[m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]==1) map[i][j]=-1;
			}
		}
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			storelist.add(new Pair(x,y));
			map[x][y] = i+1;
		}
		int time = 0;
		while(out != m) {
			time++;
			start(time);
		}
		System.out.println(time);
		
	}
	/* 문제해결 프로세스
	 * 1. 베이스 캠프 선택, bfs
	 * 2. 상좌우하 중 어느 방향으로 출발한건지 저장,
	 * 		최단거리에 도달하는 방향 출력,
	 * 		그 방향으로 1칸 이동
	 * 3. 자신의 편의점에 도착하면 list에서 제거 + 편의점위치 isvisited=true처리
	 * 4. 다음사람 출발
	 */
	static int dx[] = {-1,0,0,1};
	static int dy[] = {0,-1,1,0};
	public static void start(int t) {
		//담겨있는 사람 모두 이동
		for (int i = 0; i < list.size(); i++) {
			if(outlist[i]) continue;
			//이동 위치
			Pair m = move(i+1, list.get(i).x, list.get(i).y);
			list.set(i, new Pair(m.x, m.y));
		}
		
		for (int i = 0; i < list.size(); i++) {
			if(outlist[i]) {
				continue;
			}
			int x = list.get(i).x;
			int y = list.get(i).y;
			if(x==storelist.get(i).x && y==storelist.get(i).y) {
				isvisited[x][y] = true;
				outlist[i] = true;
				out++;
			}
		}
		if(t<=m) {
			Pair bc = basecamp(t);
			isvisited[bc.x][bc.y] = true;
			list.add(new Pair(bc.x, bc.y));
		}
		
	}
	public static Pair move(int tt, int sx, int sy) {
		boolean basevisited[][] = new boolean[n][n];
		Queue<Move> q = new ArrayDeque<>();
		q.offer(new Move(sx, sy,-1));
		basevisited[sx][sy] = true;
		int depth = 1;
		while(!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Move p = q.poll();
				if(map[p.x][p.y]==tt) {
					return new Pair(sx+dx[p.dir], sy+dy[p.dir]);
				}
				for (int k = 0; k < 4; k++) {
					int nx = p.x + dx[k];
					int ny = p.y + dy[k];
					if(!rangecheck(nx,ny) || basevisited[nx][ny] || isvisited[nx][ny]) continue;
					if(p.dir==-1) q.offer(new Move(nx, ny, k));
					//else
					else q.offer(new Move(nx, ny, p.dir));
					basevisited[nx][ny]=true;
				}
			}
			depth++;
		}
		return null;
	}
	
	public static Pair basecamp(int tt) {
		boolean storevisited[][] = new boolean[n][n];
		Pair store = storelist.get(tt-1);
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(store.x, store.y));
		storevisited[store.x][store.y]=true;
		int destx = 15;
		int desty = 15;
		int depth = 1;
		boolean destination = false;
		while(!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				//베이스캠프 발견
				if(map[p.x][p.y]==-1) {
					destination = true;
					if(p.x < destx) {
						destx=p.x;
						desty=p.y;
					}
					else if(p.x == destx) {
						if(p.y<desty) {
							destx=p.x;
							desty=p.y;
						}
					}
				}
				for (int k = 0; k < 4; k++) {
					int nx = p.x + dx[k];
					int ny = p.y + dy[k];
					if(!rangecheck(nx,ny) || storevisited[nx][ny] || isvisited[nx][ny]) continue;
					q.offer(new Pair(nx, ny));
					storevisited[nx][ny]=true;
 				}
			}
			if(destination) 
				return new Pair(destx, desty);
			depth++;
		}
		return null;
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<n && ry>=0 && ry<n;
	}
}
