package ps231012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:34
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN크기의 도시
 * 홈방범 서비스는 마름모 모양의 영역에서만 제공
 * 서비스 영역의 크기 K
 * 운영비용 = K*K + (K-1)*(K-1)
 * K=1 - 1
 * K=2 - 5
 * K=3 - 13
 * K=4 - 25
 * 도시를 벗어난 영역에 서비스를 제공해도 운영 비용은 동일
 * 집은 M의 비용을 지불할 수 있음
 * 손해를 보지 않으면서 가장 많은 집들에 제공하는 서비스영역을 찾고,
 * 서비스를 제공 받는 집들의 수를 출력
 * 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 더이상 이익이 나오는 (i,j)가 없으면 K탐색 종료
 * 
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Solution_2117_홈방범서비스_이현영 {
	static int N,M,result,map[][];
	static int maxK;
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st ;
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			result = 0;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			//홀수면
			if(N%2==1) maxK = N;
			//짝수면
			else maxK = N+1;
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					house(i,j);
				}
			}
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	public static void house(int x, int y) {
		boolean isvisited[][] = new boolean[N][N];
		Queue<Pair> q = new ArrayDeque<>();
		int cnt = 0;
		q.offer(new Pair(x,y));
		isvisited[x][y] = true;
		
		int depth = 1;
		while(!q.isEmpty()) {
			int size = q.size();
			
			if(depth>maxK) return;
			
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				int xx = p.x;
				int yy = p.y;
				
				if(map[xx][yy]==1) {
					cnt++;
				}
				for (int k = 0; k < 4; k++) {
					int nx = xx+dx[k];
					int ny = yy+dy[k];
					if(!rangecheck(nx,ny) || isvisited[nx][ny]) continue;
					//else
					
					q.offer(new Pair(nx,ny));
					isvisited[nx][ny] = true;
				}
			}
			//운영 비용 계산
			int cost = depth*depth+(depth-1)*(depth-1);
			//이익
			if(cnt*M - cost >= 0) {
				result = Math.max(result, cnt);
			}
			depth++;
		}
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}
