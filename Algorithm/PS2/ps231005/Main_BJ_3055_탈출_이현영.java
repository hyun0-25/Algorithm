package ps231005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:05
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 숲은 RxC
 * 비어 있는 곳은 .
 * 물이 차있는곳 *
 * 돌은 X
 * 비버의 굴 D
 * 고슴도치의 위치 S
 * 
 * 고슴도치는 인접한 1칸 이동가능, 상하좌우
 * 물도 매분마다 비어있는 칸으로 확장
 * 물이 있는 칸과 인접해있는 비어있는 칸(적어도 한변을 공유)은 물이 참
 * 물과 고슴도치는 돌을 통과할 수 없음
 * 고슴도치는 물있는 곳 이동 불가, 물도 비버의 소굴로 이동 불가
 * 고슴도치가 안전하게 비버의 굴로 이동하기 위해 필요한 최소 시간
 * 고슴도치는 다음 시간에 물이 찰 예정인 칸으로 이동 불가, 물에 빠지기 때문
 * 
 * 입력
 * 첫째줄 : R,C
 * R개줄 : 지도 정보, 문자로 표시
 * 
 * 출력
 * 고슴도치가 비버의굴로 이동할 수 있는 가장 빠른 시간
 * 안전하게 이동 불가면 KAKTUS출력
 * 
 * 문제해결 프로세스 (BFS+구현)
 * 0. 시작점 S를 큐에 넣음
 * 1. 물이 찰 예정인 칸 체크( waterarea() )
 * 2. 고슴도치 한칸 이동 ( BFS )
 * 	이동가능 - . 또는 도착지 D
 * 	이동불가 - 돌X, 이미물*, 물찰예정 water[][]=true
 * 	2-1. 이동가능한 칸이 없으면 KAKTUS, 종료
 * 		(즉, Queue의 size가 0임)
 * 	2-2. D에 도착하면 depth출력, 종료
 * 3. 물이 참
 * 	3-1. water[][]=true인 칸 -> *로 변경
 * 4. 123반복
 * 
 * 
 * 제한조건
 * R,C<=50
 * D,S는 1개씩
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_3055_탈출_이현영 {
	static int R,C;
	static char map[][];
	static boolean water[][];
	static boolean isvisited[][];
	static class Pair {
		int x, y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int sx, sy, ex, ey;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new char[R][C];
		water = new boolean[R][C];
		isvisited = new boolean[R][C];
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j]=='S') {
					sx = i;
					sy = j;
				}
				else if(map[i][j]=='D') {
					ex = i;
					ey = j;
				}
				else if(map[i][j]=='*') {
					water[i][j] = true;
				}
			}
		}
		
		bfs(sx, sy);
		
	}
	/* 문제해결 프로세스 (BFS+구현)
	 * 0. 시작점 S를 큐에 넣음
	 * 1. 물이 찰 예정인 칸 체크( waterarea() )
	 * 	확산가능 - .
	 * 2. 고슴도치 한칸 이동 ( BFS )
	 * 	이동가능 - . 또는 도착지 D
	 * 	이동불가 - 돌X, 이미물*, 물찰예정 water[][]=true
	 * 	2-1. 이동가능한 칸이 없으면 KAKTUS, 종료
	 * 		(즉, Queue의 size가 0임)
	 * 	2-2. D에 도착하면 depth출력, 종료
	 * 3. 물이 참
	 * 	3-1. water[][]=true인 칸 -> *로 변경
	 * 4. 123반복
	 */
	public static void bfs(int xx, int yy) {
		Queue<Pair> q = new ArrayDeque<>();
		//0. 시작점 S를 큐에 넣음
		q.offer(new Pair(xx,yy));
		isvisited[xx][yy] = true;
		map[xx][yy] = '.';
		
		int depth = 1;
		while(!q.isEmpty()) {
			int size = q.size();
			
			//1. 물이 찰 예정인 칸 체크( waterarea() )
			waterarea();
			
			for (int d = 0; d < size; d++) {
				//2. 고슴도치 한칸 이동 ( BFS )
				Pair p = q.poll();
				
				for (int i = 0; i < 4; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];
					if(!rangecheck(nx, ny) || map[nx][ny]=='X' || map[nx][ny]=='*' || water[nx][ny] || isvisited[nx][ny]) continue;
					//else
					//정상도착
					if(map[nx][ny]=='D') {
						System.out.println(depth);
						System.exit(0);
					}
					if(map[nx][ny]=='.') {
						q.offer(new Pair(nx,ny));
						isvisited[nx][ny] = true;
					}
				}
			}
			depth++;
			//3. 물이 참
			waterdone();
		}
		//도착지 D가 아닌데, 이동가능한 칸이 없으로 KAKTUS 종료
		System.out.println("KAKTUS");
	}
	
	//물 확산 예정 칸 체크
	public static void waterarea() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if(water[i][j] && map[i][j]=='*') {
					for (int k = 0; k < 4; k++) {
						int nx = i + dx[k];
						int ny = j + dy[k];
						if(rangecheck(nx, ny) && map[nx][ny]=='.' && !water[nx][ny]) {
							water[nx][ny] = true;
						}
					}
				}
			}
		}
	}
	//물 확산
	public static void waterdone() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if(map[i][j]=='.' && water[i][j]) {
					map[i][j] = '*';
				}
			}
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<R && ry>=0 && ry<C;
	}
	
}
