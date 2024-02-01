package ps231010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 12:39
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 집 크기 RxC
 * 공기청정기는 항상 1번열에 설치, 크기는 2개행 차지
 * 1초 과정
 * 1. 미세먼지 확산, 확산은 미세먼지가 있는 모든 칸에서 동시에 발생
 * 	- (r,c)에 있는 미세먼지는 인접 4방으로 확산
 * 	- 인접한 4방에 공기청정기 있거나, 칸이 없으면 그 방향으로는 확산x
 * 	- 확산되는 양  = (A[r][c]/5)
 * 	- (r,c)에 남은 미세먼지 양은 A[r][c]-(A[r][c]/5)*(확산된 방향의 개수)
 * 2. 공기청정기 작동
 * 	- 공기청정기에서는 바람 나옴
 * 	- 위 바람-> 반시계 순환 / 아래 바람-> 시계 순환
 * 	- 바람 불면, 미세먼지가 바람의 방향대로 1칸씩 이동
 * 	- 바람은 미세먼지가 없는 바람, 공기청정기로 들어간 미세먼지는 모두 정화됨
 * 
 * 
 * 입력
 * 첫째줄: R,C,T초
 * R개줄: 맵 정보
 * -1이면 공기청정기
 * 나머지는 미세먼지
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 미세먼지 확산
 * 	1-1. 따로 확산배열 spread[][]생성
 * 	1-2. map[][]에 미세먼지 위치에서 4방 확산 -> 결과 spread[][]에 저장+확산한만큼 map[][]값--
 * 	1-3. map[][]+=spread[][]
 * 2. 공기청정기 작동
 * 	2-1. 공기청정기 윗부분
 * 		반시계 회전
 * 	2-2. 공기 청정기 아랫부분
 * 		시계 회전
 * 	2-3. 회전해서 공기청정기에 미세먼지 들어가면 사라짐
 * 	2.4. 			안들어가면 미세먼지 이동만함
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_17144_미세먼지안녕_이현영 {
	static int R,C,T,map[][];
	static Cleaner upcleaner, downcleaner;
	static class Cleaner {
		int x,y;
		public Cleaner(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		int cleanernum = 0;
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]==-1 && cleanernum==0) {
					upcleaner = new Cleaner(i,j);
					cleanernum++;
				}
				else if(map[i][j]==-1 && cleanernum==1) {
					downcleaner = new Cleaner(i,j);
				}
			}
		}
		for (int i = 0; i < T; i++) {
			dustspread();
			dustclean();
		}
		//남은 미세먼지 수 더하기
		int result = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if(map[i][j]>0) result+=map[i][j];
			}
		}
		System.out.println(result);
	}
	/* 문제해결 프로세스
	 * 1. 미세먼지 확산
	 * 	1-1. 따로 확산배열 spread[][]생성
	 * 	1-2. map[][]에 미세먼지 위치에서 4방 확산  (A[r][c]/5)
	 * 			-> 결과 spread[][]에 저장+확산한만큼 map[][]값--:(A[r][c]/5)*(확산된 방향의 개수)
	 * 	1-3. map[][]+=spread[][]
	 * 2. 공기청정기 작동
	 * 	2-1. 공기청정기 윗부분
	 * 		반시계 회전
	 * 	2-2. 공기 청정기 아랫부분
	 * 		시계 회전
	 * 	2-3. 회전해서 공기청정기에 미세먼지 들어가면 사라짐
	 * 	2.4. 			안들어가면 미세먼지 이동만함
	 */
	
	//우하좌상 순
	static int dx[] = {0,1,0,-1};
	static int dy[] = {1,0,-1,0};
	public static void dustclean() {
		int[][] dustmap = new int[R][C];
		//반시계 회전
		int upx = upcleaner.x;
		int upy = upcleaner.y;
		int nx = upx;
		int ny = upy;
		for (int i = 0; i >= -3; i--) {
			int dir = i;
			if(dir<0) dir+=4;
			nx-=dx[dir];
			ny-=dy[dir];
			while(true) {
				nx+=dx[dir];
				ny+=dy[dir];
				
				int mx = nx+dx[dir];
				int my = ny+dy[dir];
				//벽 만남, 방향 전환
				if(!rangecheck(mx,my)) {
					break;
				}
				//else
				//미세먼지 만남
				if(map[nx][ny]>0) {
					dustmap[mx][my] = map[nx][ny];
				}
				if(map[nx][ny]!=-1) {
					map[nx][ny] = dustmap[nx][ny];
				}
				else if(map[mx][my]==-1) {
					break;
				}
			}
		}
		
		//시계 회전
		int downx = downcleaner.x;
		int downy = downcleaner.y;
		nx = downx;
		ny = downy;
		
		for (int i = 0; i < 4; i++) {
			int dir = i;
			nx-=dx[dir];
			ny-=dy[dir];
			while(true) {
				nx+=dx[dir];
				ny+=dy[dir];
				int mx = nx+dx[dir];
				int my = ny+dy[dir];
				//벽 만남, 방향 전환
				if(!rangecheck(mx,my)) {
					break;
				}
				//else
				//미세먼지 만남
				if(map[nx][ny]>0) {
					dustmap[mx][my] = map[nx][ny];
				}
				if(map[nx][ny]!=-1) {
					map[nx][ny] = dustmap[nx][ny];
				}
				else if(map[mx][my]==-1) {
					break;
				}
			}
		}
	}
	
	public static void dustspread() {
		int[][] resultmap = new int[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if(map[i][j]>0) {
					int cnt = 0;
					for (int k = 0; k < 4; k++) {
						int nx = i+dx[k];
						int ny = j+dy[k];
						if(!rangecheck(nx,ny) || map[nx][ny]==-1) continue;
						//else
						cnt++;
						resultmap[nx][ny] += map[i][j]/5;
					}
					resultmap[i][j] -= (map[i][j]/5)*cnt;
				}
			}
		}
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				map[i][j] += resultmap[i][j];
			}
		}
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<R && ry>=0 && ry<C;
	}
}
