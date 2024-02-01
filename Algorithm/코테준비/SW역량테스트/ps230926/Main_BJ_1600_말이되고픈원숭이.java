package SW역량테스트.ps230926;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:50
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 말은 체스의 나이트와 같은 이동방식
 * 말은 장애물을 뛰어넘을 수 있음
 * 원숭이는 K번만 말처럼 이동가능, 그 이외는 4방 1칸씩만 이동가능
 * 
 * 원숭이는 (0,0)에서 시작해 (W-1,H-1)까지 이동
 * 원숭이가 최소 동작으로 시작지점에서 도착지점까지 갈 수 있는 이동 횟수의 최솟값
 * 
 * 입력
 * 첫째줄 : K 말처럼 이동가능, 가로W,세로H
 * H줄 : W개의 숫자
 * 0은 평지, 1은 장애물
 * 장애물은 이동불가
 * 
 * 출력
 * W,H<=200
 * K<=30
 * 
 * 문제해결 프로세스 (bfs)
 * map[i][j]
 * 이동 횟수와 K사용 횟수 저장
 * 
 * 경계 밖 또는 벽이면 continue
 * 이미 똑같은 조건의 객체가 있으면 continue;
 * 아니면
 * 	k사용x -> 이동횟수+1, K그대로
 * 	k사용o -> 이동횟수+1, --K 저장
 * 
 * map[W-1][H-1]에서 최소 이동 횟수
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */


public class Main_BJ_1600_말이되고픈원숭이 {
	static int visited[][];
	static int K, W, H, board[][];
	
	static class Move{
		int x,y,cnt_k;

		public Move(int x, int y, int cnt_k) {
			super();
			this.x = x;
			this.y = y;
			this.cnt_k = cnt_k;
		}

		@Override
		public String toString() {
			return "Move [x=" + x + ", y=" + y + ", cnt_k=" + cnt_k + "]";
		}
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		board = new int[H][W];
		visited = new int[H][W];
		
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < W; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		bfs();
		
	}
	/* 문제해결 프로세스 (bfs)
	 * map[i][j]
	 * 이동 횟수와 K사용 횟수 저장
	 * 
	 * 경계 밖 또는 벽이면 continue
	 * 이미 똑같은 조건의 객체가 있으면 continue;
	 * 아니면
	 * 	k사용x -> 이동횟수+1, K그대로
	 * 	k사용o -> 이동횟수+1, --K 저장
	 * 
	 * map[W-1][H-1]에서 최소 이동 횟수
	 */
	static int dx [] = {-1,1,0,0};
	static int dy [] = {0,0,-1,1};
	static int hx [] = {-2,-2,-1,-1,1,1,2,2};
	static int hy [] = {1,-1,2,-2,2,-2,1,-1};
	
	public static void bfs() {
		Queue<Move> q = new ArrayDeque<>();
		
		Move m = new Move(0,0,0);
		q.offer(m);
//		visited[0][0].add(m);
		
		int depth = 0;
		while(!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Move p = q.poll();
				int x = p.x;
				int y = p.y;
				int cntk = p.cnt_k;
				
				if(x==H-1 && y==W-1) {
					System.out.println(depth);
					System.exit(0);
				}
				
				//일반 이동
				for (int k = 0; k < 4; k++) {
					int nx = x + dx[k];
					int ny = y + dy[k];
					if(nx<0 || nx>=H || ny<0 || ny>=W || board[nx][ny]==1) continue;
					
					boolean visit = false;
//					for(Move vm: visited[nx][ny]) {
						//이미 방문
						if(visited[nx][ny] == p.cnt_k) visit=true;
//					}
					if(visit) continue;
					//방문한적 없음
					Move am = new Move(nx, ny, cntk);
					q.offer(am);
					visited[nx][ny] = cntk;
				}
				
				if(cntk < K) {
					//말처럼 이동
					for (int k = 0; k < 8; k++) {
						int nx = x + hx[k];
						int ny = y + hy[k];
						if(nx<0 || nx>=H || ny<0 || ny>=W || board[nx][ny]==1) continue;
						
						boolean visit = false;
//						for(Move vm: visited[nx][ny]) {
							//이미 방문
							if(visited[nx][ny] == cntk+1) visit=true;
//						}
						if(visit) continue;
						
						
						//방문한적 없음
						Move am = new Move(nx, ny, cntk+1);
						q.offer(am);
						visited[nx][ny] = cntk+1;
					}
				}
			}
			depth++;
		}
		System.out.println(-1);
	}
}
