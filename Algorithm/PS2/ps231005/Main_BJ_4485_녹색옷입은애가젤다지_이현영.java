package ps231005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN 크기의 동굴
 * 링크는 (0,0)에 위치
 * 링크는 (N-1,N-1)까지 이동
 * 각 칸마다 도둑루피가 있는데, 이 칸을 지나면 해당 도둑루피의 크기만큼 소지금을 잃음
 * 잃는 금액을 최소로 하여 동굴 건너편까지 이동
 * 한번에 상하좌우 인접한 곳으로 1칸씩 이동 가능
 * 
 * 
 * 입력
 * 0 입력받으면 종료
 * 
 * 출력
 * 링크가 이동하는데 드는 최소 금액
 * 
 * 문제해결 프로세스 (BFS+PQ)
 * 1. 미방문한 정점 체크
 * 2. x,y,가중치값 저장(PQ)
 * 3. 큐에서 꺼내며 1,2반복
 * 
 * 
 * 제한조건
 * N<=125
 * N=0이면 종료
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_4485_녹색옷입은애가젤다지_이현영 {
	static int N, map[][], result;
	static boolean isvisited[][];
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static class Move implements Comparable<Move>{
		int x,y,cost;

		public Move(int x, int y, int cost) {
			super();
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Move o) {
			return this.cost - o.cost;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int test_case = 1;
		while(true) {
			result = 0;
			N = Integer.parseInt(br.readLine());
			if(N==0) {
				System.out.println(sb);
				System.exit(0);
			}
			
			map = new int[N][N];
			isvisited = new boolean[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			bfs();
			
			sb.append("Problem ").append(test_case++).append(": ").append(result).append('\n');
		}
	}
	
	public static void bfs() {
		PriorityQueue<Move> q = new PriorityQueue<>();
		q.offer(new Move(0, 0, map[0][0]));
		isvisited[0][0] = true;
		
		//PQ에 의해 가장 작은 가중치부터 빼냄
		while(!q.isEmpty()) {
			Move m = q.poll();
			
			if(m.x==N-1 && m.y==N-1) {
				result = m.cost;
				return;
			}
			for (int i = 0; i < 4; i++) {
				int nx = m.x + dx[i];
				int ny = m.y + dy[i];
				if(!rangecheck(nx,ny) || isvisited[nx][ny]) continue;
				
				q.offer(new Move(nx, ny, map[nx][ny]+m.cost));
				isvisited[nx][ny] = true;
			}
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}
