package ps230823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 16:00
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 무선 충전 할 때 최적의 BC을 선택하는 알고리즘을 개발
 * 매초마다 특정 BC의 충전 범위에 안에 들어오면 해당 BC에 접속
 * 접속한 BC의 성능 P만큼 배터리 충전 가능
 * 		한 BC에 2명의 사용자가 동시에 접속하면, 접속한 사용자 수 만큼 충전 양 1/2씩 분배
 * 모든 사용자가 충전한 양의 합의 최댓값 
 * 
 * 서로 다른 1개 BC 위치 -> 사람1 - 그 지역 최대 BC 전부 +
 * 					    사람2 - 동일
 * 
 * 같은 1개 BC 2명 위치 -사람1,사람2 1/2씩 +(결국 전부 한번 더해주는것과 동일)
 * 
 * 
 * 1개 BC: 1명, (2,3,4..)개 BC: 1명 -> 사람1 - 무조건 그 BC선택
 * 					    	 	      사람2 - 사람1이 고른것 제외하고 가장 큰것 선택
 * 
 * (2,3,4..)개  BC 겹치는 위치 2명 -> 사람1 - 가장 큰 BC선택
 * 					    	       사람2 - 사람1이 고른것 제외하고 가장 큰것 선택
 * 
 * 			근데 가장큰 BC가 동일하면 2번째꺼 비교해서 더 작은 사람이 가장 큰 BC, 더 큰 사람이 2번째 선
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. bfs로 각 BC 영역 색칠
 * 2. 사람1,2 움직이면서 점수 더함
 * 3. 결과 출력
 * 
 * 
 * 
 * 제한조건
 * 가로10,세로10
 * A는(1,1), B는(10,10)에서 출발
 * 초기 위치부터 충전 가능
 * 
 * 시간복잡도
 * 
 * 
 */

public class Soltuion_5644_무선충전_이현영 {
	static int M,A,result;
	
	static int dx[] = {0,0,1,0,-1};
	static int dy[] = {0,-1,0,1,0};
	static int move1[], move2[];
	
	static ArrayList<BC> map[][];
	
	static class BC implements Comparable<BC>  {
		int num, P;

		public BC(int num, int P) {
			super();
			this.num = num;
			this.P = P;
		}

		@Override
		public int compareTo(BC o) {
			return o.P-this.P;
		}
	}
	
	static class Pair {
		int x,y,c,p,depth;
		public Pair(int x, int y, int c, int p, int depth) {
			super();
			this.x = x;
			this.y = y;
			this.c = c;
			this.p = p;
			this.depth = depth;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st ;
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			
			move1 = new int[M+1];
			move2 = new int[M+1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= M; i++) {
				move1[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= M; i++) {
				move2[i] = Integer.parseInt(st.nextToken());
			}
			
			map = new ArrayList[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					map[i][j] = new ArrayList<>();
				}
			}
			
			for (int i = 0; i < A; i++) {
				st = new StringTokenizer(br.readLine());
				int idx_x = Integer.parseInt(st.nextToken())-1;
				int idx_y = Integer.parseInt(st.nextToken())-1;
				int idx_c = Integer.parseInt(st.nextToken());
				int idx_p = Integer.parseInt(st.nextToken());
				bfs(idx_x, idx_y, idx_c, idx_p, i+1);
			}
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					Collections.sort(map[i][j]);
				}
			}
			
			result = 0;
			int a_x=0, a_y=0;
			int b_x=9, b_y=9;
			//0~M초까지
			for (int i = 0; i <= M; i++) {
				int k1 = move1[i];
				int k2 = move2[i];
				a_x += dx[k1];
				a_y += dy[k1];
				b_x += dx[k2];
				b_y += dy[k2];
				if(a_x==0 && a_y==0 && b_x==9 && b_y==9) {
					battery(a_x,a_y,b_x,b_y);
					continue;
				}
				battery(a_x,a_y,b_x,b_y);
			}
			sb.append('#').append(test_case).append(' ');
			sb.append(result).append('\n');
		}
		System.out.println(sb);
	}
	//bfs로 BC영역 색칠하는 함수
	public static void bfs(int sx, int sy, int sc, int sp, int n) {
		boolean isVisited [][] = new boolean[10][10];
		Queue<Pair> q = new ArrayDeque<>();
		
		int depth=0;
		//시작 칸 넣기
		q.offer(new Pair(sx,sy,sc,sp,depth));
		map[sx][sy].add(new BC(n, sp));
		isVisited[sx][sy] = true;
		
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 1; i <= 4; i++) {
				int nx = p.x+dx[i];
				int ny = p.y+dy[i];
				
				//맵 밖으로 나가는 경우
				if(nx<0 || nx>=10 || ny<0 || ny>=10) continue;
				//그 영역에 동일한 BC가 이미 설치되어 있는 경우
				if(isVisited[nx][ny]) continue;
				//거리가 c 초과인 경우
				if(p.depth>=sc) continue;
				
				q.offer(new Pair(nx,ny,sc,sp, p.depth+1));
				map[nx][ny].add(new BC(n, sp));
				isVisited[nx][ny] = true;
			}
		}
	}
	
	/* 0번
	 * 아무도 BC에 없음 -> return;
	 * 1명만 BC에 있음 -> 그 사람만 가장큰 BC더하고 return;
	 * 
	 * 1번
	 * 같은 1개 BC 2명 위치 -사람1,사람2 1/2씩 +(결국 전부 한번 더해주는것과 동일)
	 * 
	 * 2번
	 * 서로 다른 1개 BC 위치 -> 사람1 - 그 지역 최대 BC 전부 +
	 * 					    사람2 - 동일
	 * 
	 * 3번
	 * 1개 BC: 1명, (2,3,4..)개 BC: 1명 -> 사람1 - 무조건 그 BC선택
	 * 					    	 	      사람2 - 사람1이 고른것 제외하고 가장 큰것 선택
	 * 4번
	 * (2,3,4..)개  BC 겹치는 위치 2명 -> 사람1 - 가장 큰 BC선택
	 * 					    	       사람2 - 사람1이 고른것 제외하고 가장 큰것 선택
	 * 			근데 가장큰 BC가 동일하면 2번째꺼 비교해서 더 작은 사람이 가장 큰 BC, 더 큰 사람이 2번째 선택
	 */
	
	
	//A와 B의 위치가 들어 왔을 때, 계산
	public static void battery(int ax, int ay, int bx, int by) {
		int asize = map[ax][ay].size();
		int bsize = map[bx][by].size();
		
		//0번
		if(asize==0 && bsize==0) return;
		else if (asize==0) {
			result += map[bx][by].get(0).P;
			return;
		}
		else if (bsize==0) {
			result += map[ax][ay].get(0).P;
			return;
		}
		
		//else
		int anum = map[ax][ay].get(0).num;
		int bnum = map[bx][by].get(0).num;
		//1번, 2번
		if(asize==1 && bsize==1) {
			//1번
			if(anum==bnum) {
				result += map[ax][ay].get(0).P;
			}
			//2번
			else {
				result += map[ax][ay].get(0).P;
				result += map[bx][by].get(0).P;
			}
		}
		//3번, 4번
		else {
			//3번
			if(asize==1) {
				result += map[ax][ay].get(0).P;
				if(anum==bnum) {
					result += map[bx][by].get(1).P;
				}
				else {
					result += map[bx][by].get(0).P;
				}
			}
			else if(bsize==1) {
				result += map[bx][by].get(0).P;
				if(anum==bnum) {
					result += map[ax][ay].get(1).P;
				}
				else {
					result += map[ax][ay].get(0).P;
				}
			}
			//4번
			else {
				if(anum==bnum) {
					int a2 = map[ax][ay].get(1).P;
					int b2 = map[bx][by].get(1).P;
					if(a2>b2) {
						result += map[ax][ay].get(1).P;
						result += map[bx][by].get(0).P;
					}
					else {
						result += map[ax][ay].get(0).P;
						result += map[bx][by].get(1).P;
					}
				}
				else {
					result += map[ax][ay].get(0).P;
					result += map[bx][by].get(0).P;
				}
			}
		}
	}
}
