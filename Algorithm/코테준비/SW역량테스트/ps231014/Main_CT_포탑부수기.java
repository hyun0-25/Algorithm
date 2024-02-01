package SW역량테스트.ps231014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 1:05
 * 종료: 3:35
 * 
 * 문제해석
 * NxM격자, 모든 위치에는 포탑이 존재 (포탑개수=N*M개)
 * 각 포탑에는 공격력이 존재, 상황에따라 공격력 변화 -> 공격력이 0이하면 포탑 부서짐,공격불가(초기값이 0인 포탑도 존재)
 * 4가지 액션 K번반복
 * (부서지지않은 포탑이 1개가 되면 즉시 중지)
 * 1. 공격자 선정
 * 	부서지지 않은 포탑 중 가장 약한 포탑이 공격자
 * 	공격자로 선정되면 가장 약한 포탑 -> 핸티캡 N+M만큼 공격력 증가
 * 		<가장 약한 포탑기준>
 * 		1-1. 공격력이 가장 낮은 포탑
 * 		1-2. 같은 공격력이 2개 이상이면, 가장 최근에 공격한 포탑이 가장 약한 포탑 
 * 		1-3. 그런 포탑이 2개 이상이면, 행과 열의 합이 가장 큰 포탑이 가장 약한 포탑
 * 		1-4. 그런 포탑이 2개 이상이면, 열값이 가장 큰 포탑이 가장 약한 포탑
 * 2. 공격자의 공격
 * 	선정된 공격자는 자신을 제외한 가장 강한 포탑을 공격(약한 포탑 반대)
 * 		<가장 강한 포탑기준>
 * 		2-1. 공격력이 가장 높은 포탑
 * 		2-2. 같은 공격력이 2개 이상이면, 가장 공격한지 오래된 포탑이 가장 강한 포탑 
 * 		2-3. 그런 포탑이 2개 이상이면, 행과 열의 합이 가장 작은 포탑이 가장 강한 포탑
 * 		2-4. 그런 포탑이 2개 이상이면, 열값이 가장 작은 포탑이 가장 강한 포탑
 *  레이저 공격 먼저 시도 -> 안되면 포탄 공격
 *  (1) 레이저 공격
 *  	1. 상하좌우 4개방향으로 움직일 수 있음
 *  	2. 부서진 포탑이 있는 위치는 지날 수 없음
 *  	3. 가장자리에서 막힌 방향으로 진행하고자 하면, 반대편으로 나옴
 *  	레이저 공격은 공격자의 위치에서 공격 대상 포탑까지의 최단 경로로 공격
 *  	만약 최단 경로가 존재하지 않으면, (2)포탄공격 진행
 *  	만약 경로의 길이가 똑같은 최단 경로가 2개 이상이면, 우하좌상 순대로 먼저 움직인 경로가 선택
 *  최단 경로가 정해졌으면, 공격대상에는 공격자의 공격력 만큼 피해 -> 그만큼 공격력 줄어들음
 *  또한, 공격 대상을 제외한 레이저 경로에 있는 포탑도 공격, 공격자 공격력의 절반만큼 공격(/2값)
 * 	(2) 포탄 공격
 * 	공격 대상에 포탄을 던짐
 * 	공격 대상은 공격자의 공격력 만큼 피해, 추가로 주위 8방의 포탑도 피해(절반만큼)
 * 	공격자는 해당 공격에 영향X
 * 	가장 자리에 포탄이 떨어지면 포탄 피해는 반대편 격자에 미치게됨
 * 3. 포탑 부서짐
 * 	공격을 받아 공격력이 0이하가 된 포탑 부서짐
 * 4. 포탑 정비
 * 	공격이 끝났으면, 부서지지않은 포탑 중 공격과 무관했던 포탑은 공격력이 1씩 증가	
 * 		공격자도 아니고, 공격 피해 입은 포탑도 아니라는 뜻
 * 
 * 
 * 입력
 * 첫째줄: N,M, K실행횟수
 * N개줄 : NxM정보
 * 종료 조건 -> K실행 끝나거나/ 남은 포탑 1개거나
 * 
 * 출력
 * 남아있는 포탑 중 가장 강한 포탑의 공격력
 * 
 * 문제해결프로세스
 * shoottime[][] =공격하거나,공격받은 위치의 시간저장
 * 
 * 1. 공격자 선정 , map[][]>0 중 min값 설정하면서 갱신
 * 	min보다 작으면 min값,i,j 갱신
 * 	min과 같으면, time-shoottime[][]비교
 * 		time-shoottime[][]이 동일하면, i+j값 비교
 * 			i+j도 같으면 j값 비교
 * => i,j로 공격자 선정 -> map[][]+=(N+M)증가 + shoottime[][]=time;
 * 
 * 2. 공격자 공격 , min의 i,j를 제외한 가장 강한 포탑 찾기
 * max보다 크면 max값,i,j 갱신
 * 	max과 같으면, time-shoottime[][]비교
 * 		time-shoottime[][]이 동일하면, i+j값 비교
 * 			i+j도 같으면 j값 비교
 * => i,j로 공격 대상 선정 -> 공격 방식 고르기 + shoottime[][]=time;
 * 
 * 3. 레이저 공격 -> 포탄 공격
 * 	3-1. 레이저 공격 bfs (isvisited[][] boolean말고 방향을 저장) 
 * 		min(공격자) i,j에서 시작해 우하좌상 시작 순서 저장
 * 		부서진 포탑 못지남 => map[][]==0 continue;
 *  		max i,j에 도착하면, 그때의 +=[dir]를 return, isvisited[][]가 dir과 같은 위치 shoottime[][]=time;
 * 				도착하지 못하면, 포탄 공격 시작
 *	3-2. 포탄 공격, 공격 대상의 8방에 포탄(/2피해)
 *		맵 밖으로 나가면, 나간 방향 반대로 나와서 포탄피해
 *
 * 4. map[][]<=0인 포탑 부서짐
 * 
 * 5. 포탑 정비, shoottime[][]!=time이고 map[][]>0인 포탑들 ++
 * 6. 살아있는 포탑 수 2개이상인지 체크 
 * 		cnt<=1이면 바로 종료 -> 남은 포탑 출력
 * 		cnt>=2이면 다음턴 시작
 * 
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_CT_포탑부수기 {
	static int N,M,K,map[][],shoottime[][];
	static boolean shootattack[][];
	static int time;
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Pair [x=" + x + ", y=" + y + "]";
		}
	}
	//우하좌상 + 대각선들
	static int dx[] = {0,1,0,-1,-1,1,-1,1};
	static int dy[] = {1,0,-1,0,-1,-1,1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		shoottime = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int t = 1; t <= K; t++) {
			time = t;
			shootattack = new boolean[N][M];
			//공격자 선정
			Pair s = shooter();
			shoottime[s.x][s.y] = time;
			shootattack[s.x][s.y] = true;
			map[s.x][s.y] += N+M;
			//공격 대상 선정
			Pair a = attack();
			shootattack[a.x][a.y] = true;
			//레이저 공격 -> 실패시 포탄 공격
			lazer(s.x, s.y, a.x, a.y);
			//이번 턴에 영향 받지 않은 포탑 ++
			notattack();
			//남은 포탑이 1개인지 체크
			boolean isok = checkshoot();
			if(!isok) break;
		}
		//남은 포탑 중 가장 큰 포탑 공격력 출력
		int maxshoot = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]>0) maxshoot = Math.max(maxshoot, map[i][j]);
			}
		}
		System.out.println(maxshoot);
	}
	

	private static boolean checkshoot() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]>0) cnt++;
				if(cnt>=2) return true;
			}
		}
		return false;
	}


	private static void notattack() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(!shootattack[i][j] && map[i][j]>0) map[i][j]++;
			}
		}
	}


	/* 1. 공격자 선정 , map[][]>0 중 min값 설정하면서 갱신
	 * 	min보다 작으면 min값,i,j 갱신
	 * 	min과 같으면, time-shoottime[][]비교
	 * 		time-shoottime[][]이 동일하면, i+j값 비교
	 * 			i+j도 같으면 j값 비교
	 * => i,j로 공격자 선정 -> map[][]+=(N+M)증가 + shoottime[][]=time;
	 * 
	 * 2. 공격자 공격 , min의 i,j를 제외한 가장 강한 포탑 찾기
	 * max보다 크면 max값,i,j 갱신
	 * 	max과 같으면, time-shoottime[][]비교
	 * 		time-shoottime[][]이 동일하면, i+j값 비교
	 * 			i+j도 같으면 j값 비교
	 * => i,j로 공격 대상 선정 -> 공격 방식 고르기 + shoottime[][]=time;
	 * 
	 * 3. 레이저 공격 -> 포탄 공격
	 * 	3-1. 레이저 공격 bfs (isvisited[][] boolean말고 방향을 저장) 
	 * 		min(공격자) i,j에서 시작해 우하좌상 시작 순서 저장
	 * 		부서진 포탑 못지남 => map[][]<=0 continue;
	 *  		max i,j에 도착하면, 그때의 +=[dir]를 return, isvisited[][]가 dir과 같은 위치 shoottime[][]=time;
	 * 				도착하지 못하면, 포탄 공격 시작
	 *	3-2. 포탄 공격, 공격 대상의 8방에 포탄(/2피해)
	 *		맵 밖으로 나가면, 나간 방향 반대로 나와서 포탄피해
	 *
	 * 4. map[][]<=0인 포탑 부서짐
	 * 
	 * 5. 포탑 정비, shoottime[][]!=time이고 map[][]>0인 포탑들 ++
	 * 6. 살아있는 포탑 수 2개이상인지 체크 
	 * 		cnt<=1이면 바로 종료 -> 남은 포탑 출력
	 * 		cnt>=2이면 다음턴 시작
	 */
	//왔던길 역추적
	private static void sideattack(int sx, int sy, int ex, int ey, int val, int[][] bx, int[][] by) {
		Queue<Pair> q = new ArrayDeque<>();
		//도착지부터 시작
		q.offer(new Pair(ex,ey));
		while(!q.isEmpty()) {
			Pair p = q.poll();
			int x = p.x;
			int y = p.y;
			//시작점 도착
			if(x==sx && y==sy) {
				return;
			}
			if(x==ex && y==ey) {
				map[x][y] -= val;
				shootattack[x][y] = true;
			}
			else {
				map[x][y] -= val/2;
				shootattack[x][y] = true;
				
			}
			int nx = bx[x][y];
			int ny = by[x][y];
			q.offer(new Pair(nx,ny));
		}
		
	}
	
	private static void bomb(int sx, int sy, int ex, int ey) {
		map[ex][ey] -= map[sx][sy];
		shootattack[ex][ey] = true;
		for (int k = 0; k < 8; k++) {
			int nx = ex+dx[k];
			int ny = ey+dy[k];
			nx = rangex(nx);
			ny = rangey(ny);
			if (map[nx][ny]<=0 || (sx==nx && sy==ny)) continue;
			//else
			map[nx][ny] -= map[sx][sy]/2;
			shootattack[nx][ny] = true;
		}
	}
	private static void lazer(int x1, int y1, int x2, int y2) {
		boolean isvisited[][] = new boolean [N][M];
		int beforex[][] = new int[N][M];
		int beforey[][] = new int[N][M];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(x1,y1));
		isvisited[x1][y1]=true;
		
		while(!q.isEmpty()) {
			int size = q.size();
			for (int s = 0; s < size; s++) {
				Pair m = q.poll();
				int x = m.x;
				int y = m.y;
				//공격 대상 정상도착
				if(m.x == x2 && m.y == y2) {
					//해당 방향으로 온 길 공격/2 영향
					sideattack(x1, y1, x2, y2, map[x1][y1], beforex, beforey);
					return;
				}
				for (int k = 0; k < 4; k++) {
					int nx = x+dx[k];
					int ny = y+dy[k];
					nx = rangex(nx);
					ny = rangey(ny);
					if (map[nx][ny]<=0 || isvisited[nx][ny]) continue;
					//else
					beforex[nx][ny] = x;
					beforey[nx][ny] = y;
					q.offer(new Pair(nx,ny));
					isvisited[nx][ny] = true;
				}
			}
		}
		//큐 다돌았는데 못도착함
		//포탄 공격
		bomb(x1,y1,x2,y2);
	}
	
	
	private static Pair attack() {
		int maxx = -1;
		int maxy = -1;
		int maxval = 0 ;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]>0 && !shootattack[i][j]) {
					if(map[i][j] > maxval) {
						maxx = i;
						maxy = j;
						maxval = map[i][j];
					}
					else if (map[i][j] == maxval) {
						if(time-shoottime[i][j] > time-shoottime[maxx][maxy]) {
							maxx = i;
							maxy = j;
							maxval = map[i][j];
						}
						else if(time-shoottime[i][j] == time-shoottime[maxx][maxy]) {
							if(i+j < maxx+maxy) {
								maxx = i;
								maxy = j;
								maxval = map[i][j];
							}
							else if(i+j == maxx+maxy) {
								if(j < maxy) {
									maxx = i;
									maxy = j;
									maxval = map[i][j];
								}
							}
						}
					}
				}
			}
		}
		return new Pair(maxx, maxy);
	}
	private static Pair shooter() {
		int minx = -1;
		int miny = -1;
		int minval = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j] > 0) {
					if(map[i][j]<minval) {
						minx = i;
						miny = j;
						minval = map[i][j];
					}
					else if(map[i][j]==minval) {
						if(time-shoottime[i][j] < time-shoottime[minx][miny]) {
							minx = i;
							miny = j;
							minval = map[i][j];
						}
						else if(time-shoottime[i][j] == time-shoottime[minx][miny]) {
							if(i+j > minx+miny) {
								minx = i;
								miny = j;
								minval = map[i][j];
							}
							else if(i+j == minx+miny) {
								if(j > miny) {
									minx = i;
									miny = j;
									minval = map[i][j];
								}
							}
						}
					}
				}
			}
		}
		return new Pair(minx, miny);
	}
	private static int rangex(int rx) {
		if(rx<0) return N+rx;
		if(rx>=N) return rx-N;
		return rx;
	}
	private static int rangey(int ry) {
		if(ry<0) return M+ry;
		if(ry>=M) return ry-M;
		return ry;
	}
}
