package ps231004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import javax.print.DocFlavor.STRING;

/**
 * 시작시간: 16:50
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 파손된 도로는 트럭이나 탱크와 같은 차량들이 지나갈수x
 * 출발지S에서 도착지G까지 가기 위한 도로 복구 작업
 * 도로가 파여진 깊이에 비례해서 복구 시간은 증가
 * 출발지에서 도착지까지 가는 경로 중 복구 시간이 가장 짧은 경로에 대한 총 복구 시간
 * 
 * 
 * 입력
 * 첫째줄 : 맵크기 N
 * N개줄 : 맵정보 NxN
 * 
 * 출력
 * (0,0)에서 (N-1,N-1)까지 가는 경로 중에 복구 작업에 드는 시간이 가장 작은 경로의 복구 시간을 출력
 * 
 * 
 * 문제해결 프로세스 (다익스트라-> bfs+PQ)
 * 최소 비용..
 * 각 좌표까지 오는데 드는 최소 비용 저장
 * 큐에서 새로운 좌표가 들어왔을 때, 상하좌우 좌표중 가장 작은 값을 더한것을 저장
 * 
 * 
 * 제한조건
 * N<=100
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Solution_1249_보급로_이현영 {
	static int N, map[][],result;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static boolean isvisited[][];
	static class Road implements Comparable<Road>{
		int x, y, cost;
		public Road(int x, int y, int cost) {
			super();
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		@Override
		public int compareTo(Road o) {
			return this.cost - o.cost;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			map = new int [N][N];
			isvisited = new boolean[N][N];
			result = 0;
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					map[i][j] = str.charAt(j)-'0';
				}
			}
			bfs();
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	public static void bfs() {
		PriorityQueue<Road> q = new PriorityQueue<>();
		q.offer(new Road(0,0,0));
		isvisited[0][0] = true;
		
		while(!q.isEmpty()) {
			Road r = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = r.x + dx[i];
				int ny = r.y + dy[i];
				if(!rangecheck(nx,ny) || isvisited[nx][ny]) continue;
				//else
				if(nx==N-1 && ny==N-1) {
					result = map[nx][ny]+r.cost;
					return;
				}
				q.offer(new Road(nx, ny, map[nx][ny]+r.cost));
				isvisited[nx][ny] = true;
			}
		}
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}
}
