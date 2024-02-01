package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 8:55
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 방은 NxM 크기의 직사각형, 1x1크기의 정사각형 칸으로 나누어짐
 * 청소기는 바라보는 방향 존재 (동서남북)
 * 방의 각 칸의 좌표 (r,c) = (0,0)~(N-1,M-1)
 * 처음에 빈칸은 전부 청소되지 않은 상태
 * 1. 현재 칸이 아직 청소되지 않은 경우 현재 칸 청소
 * 2. 현재 칸의 주변 4칸 중 청소되지 않은 빈칸이 없는 경우
 *  2-1. 바라보는 방향을 유지하고 한칸 후진가능 -> 한칸 후진 후 1번으로 돌아감
 *  2-2. 바라보는 방향의 뒤쪽칸이 벽이라 후진불가 -> 작동멈춤
 * 3. 현재 칸의 주변 4칸 중 청소되지 않은 빈칸이 있는 경우
 *  3-1. 반시계 방향으로 90도 회전
 *  3-2. 바라보는 방향 기준으로 앞쪽 칸이 청소되지 않은 빈칸인 경우 한칸 전진
 *  3-3. 1번으로 돌아감
 * 
 * 방향 d(북0,동1,남2,서3)
 *  
 * 입력
 * 첫째줄: 방 크기 N,M
 * 둘째줄: 처음 로봇이 위치한 칸 (r,c) 방향 d
 * 셋째줄~N줄: 장소의 상태 0: 청소되지 않은 빈칸
 * 					 1: 벽 존재
 * 맵의 끝(북남서동) 중 1개 이상에 위치한 모든 칸은 벽이다
 * 로봇 청소기가 있는 칸은 항상 빈칸
 * 
 * 
 * 출력
 * 로봇 청소기가 작동을 시작한 후 작동을 멈출때까지 청소하는 칸의 개수
 * 
 * 문제해결 프로세스
 * 1. 현재 칸이 아직 청소되지 않은 경우(isClean=false) cnt++; -> 현재칸 isClean=true로
 * 2. 현재 칸의 주변 4칸 탐색
 *  2-1. 청소 되지 않은 빈칸 0이 없는 경우 -> 방향 유지 + 후진
 *  	후진 가능-> 1번으로 돌아감
 *		후진 불가-> 작동멈춤
 *	2-2. 청소 되지 않은 빈칸0이 있는 경우 -> 반시계 90도 회전
 *		회전한 1칸 앞이 청소되지 않은 빈칸 0인 경우 한칸 전진
 *		1번으로 돌아감

 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */
public class 로봇청소기 {
	static int N,M,map[][], idx_x, idx_y, dir, cnt;
	static boolean isClean[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		isClean = new boolean[N][M];
		
		st = new StringTokenizer(br.readLine());
		idx_x = Integer.parseInt(st.nextToken());
		idx_y = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		Move(idx_x,idx_y,dir);
		System.out.println(cnt);
	}
	
	 /* 문제해결 프로세스
	 * 1. 현재 칸이 아직 청소되지 않은 경우 현재 칸을 청소(isClean=false) cnt++; -> 현재칸 isClean=true로
	 * 2. 현재 칸의 주변 4칸 탐색
	 *  2-1. 청소 되지 않은 빈칸 0이 없는 경우 -> 방향 유지 + 후진
	 *  	후진 가능-> 1번으로 돌아감
	 *		후진 불가-> 작동멈춤
	 *	2-2. 청소 되지 않은 빈칸0이 있는 경우 -> 반시계 90도 회전
	 *		회전한 1칸 앞이 청소되지 않은 빈칸 0인 경우 한칸 전진
	 *		1번으로 돌아감
	 */
	//방향 d(북0,동1,남2,서3)
	static int [] dx = {-1,0,1,0};
	static int [] dy = {0,1,0,-1};
	public static void Move(int x, int y, int d) {
		while(true) {
			//1. 현재 칸이 아직 청소되지 않은 경우 현재 칸을 청소(isClean=false) cnt++; -> 현재칸 isClean=true로
			if(isClean[x][y]==false) {
				cnt++;
				isClean[x][y]=true;
			}
			//2. 현재 칸의 주변 4칸 탐색
			//2-1. 청소 되지 않은 빈칸 0이 없는 경우 -> 방향 유지 + 후진
			if(!isExist(x,y)) {
				int xx = x-dx[d];
				int yy = y-dy[d];
				//후진 가능-> 1번으로 돌아감
				if(xx>=0 && xx<N && yy>=0 && yy<M && map[xx][yy]==0) {
					x=xx;
					y=yy;
					continue; 
				}
				//후진 불가-> 작동멈춤
				else {
					return;
				}
				
			}
			//	2-2. 청소 되지 않은 빈칸0이 있는 경우 -> 반시계 90도 회전
			//		회전한 1칸 앞이 청소되지 않은 빈칸 0인 경우 한칸 전진
			//		1번으로 돌아감
			else {
				//반시계 90도 회전
				if(d-1<0) d+=4;
				d=d-1;
				//바라보는 방향 앞쪽 칸
				int xx = x+dx[d];
				int yy = y+dy[d];
				//앞쪽 칸이 청소되지 않은 빈칸이면 한칸 전진
				if(xx>=0 && xx<N && yy>=0 && yy<M && map[xx][yy]==0 && !isClean[xx][yy]) {
					x=xx;
					y=yy;
					continue;
				}
			}
		}
	}
	
	public static boolean isExist(int x, int y) {
		for (int k = 0; k < 4; k++) {
			int nx = x+dx[k];
			int ny = y+dy[k];
			//맵 밖인 경우
			if(nx<0 || nx>=N || ny<0 || ny>=M) continue;
			if(map[nx][ny]==0 && !isClean[nx][ny]) return true;
		}
		return false;
	}
}
