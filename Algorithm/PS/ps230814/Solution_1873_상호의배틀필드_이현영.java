package ps230814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:03
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 전차는 사용자의 전차 1개뿐
 * . : 평지 (전차 가능)
 * * : 벽돌 벽
 * # : 강철 벽
 * - : 물 (전차 불가능)
 * ^ : 위쪽 보는 전차
 * v : 아래 보는 전차
 * < : 왼쪽 보는 전차
 * > : 오른쪽 보는 전차
 * 
 * 동작
 * U : 전차 위쪽 방향, 위가 평지면 이동
 * D : 전차 아래쪽 방향, 아래가 평지면 이동
 * L : 전차 왼쪽 방향, 왼쪽이 평지면 이동
 * R : 전차 오른쪽 방향, 오른쪽이 평지면 이동
 * S : 바라보는 방향으로 포탄 발사
 * 
 * 맵 밖 이동 불가,
 * 포탄 발사하면 벽돌벽 또는 강철벽에 충돌하거나 맵밖으로 나갈 때까지 직진
 * 포탄이 벽에 부딪히면 포탄 소멸, 벽돌 벽은 파괴-> 평지
 * 						   강철 벽은 아무일 없음
 * 						  맵 밖으로 나가면 아무일 없음
 * 모든 입력을 처리 후 맵 상태가 어떻게 되는지 출력
 * 
 * 
 * 입력
 * 첫째줄: 맵 높이 H, 너비 W
 * 다음H개줄: 맵 정보, 전차는 단 1개
 * 다음줄: 사용자가 넣을 입력을 개수 : N
 * 다음N개줄: 길이가 N인 문자열 (U,D,L,R,S)
 * 
 * 출력
 * 모든 입력을 처리 후 맵 상태가 어떻게 되는지 출력
 * 
 * 문제해결 프로세스 (구현)
 * 1. 방향 dx,dy
 * 2. for i = 0~N
 * 3. 포탄 발사 함수 (param: 현 좌표 i,j와 방향 k)
 * 이동불가: 물, 벽돌, 강철 ..이것보다는 -> 이동가능: 평지
 * 
 * 
 * 제한조건
 * H,W<=20
 * N<=100
 * 
 * 시간복잡도
 * O(H*W*N*
 * 
 * 
 */

// ***....
// *-..#**
// #<.****


public class Solution_1873_상호의배틀필드_이현영 {
	static int H,W,N;
	static char map[][];
	static int x,y,k;
	//상하좌우 순
	static int dx [] = {-1,1,0,0};
	static int dy [] = {0,0,-1,1};
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			map = new char [H][W];
			for (int i = 0; i < H; i++) {
				String str = br.readLine();
				for (int j = 0; j < W; j++) {
					map[i][j] = str.charAt(j);
					//전차 초기 위치 확인
					if(map[i][j]=='<' || map[i][j]=='>' || map[i][j]=='v' || map[i][j]=='^') {
						x=i;
						y=j;
						//전차 방향 확인 (상하좌우 순)
						if(map[i][j]=='^') k=0;
						else if(map[i][j]=='v') k=1;
						else if(map[i][j]=='<') k=2;
						else if(map[i][j]=='>') k=3;
					}
				}
			}
			
			
			N = Integer.parseInt(br.readLine());
			String str = br.readLine();
			char action;
			for (int i = 0; i < N; i++) {
				action = str.charAt(i);
				//move
				if(action == 'U' || action == 'D' || action == 'L' || action == 'R') {
					move(x,y,action);
				}
				//shoot
				else if(action == 'S') {
					shoot(x,y,k);
				}
			}
			sb.append('#').append(test_case).append(' ');
			for (int i = 0; i < H; i++) {
				sb.append(map[i]).append('\n');
			}
		}
		System.out.println(sb);
	}
	 /* 포탄이 벽에 부딪히면 포탄 소멸, 벽돌 벽은 파괴-> 평지
	 * 						   강철 벽은 아무일 없음
	 * 						  맵 밖으로 나가면 아무일 없음
	 */
	public static void shoot(int sx, int sy, int sk) {
		int nx = sx;
		int ny = sy;
		while(true) {
			nx += dx[sk];
			ny += dy[sk];
			//맵 밖이거나 강철벽이면 return;
			if(nx<0 || nx>=H || ny<0 || ny>=W || map[nx][ny]=='#') {
				return;
			}
			//벽돌벽 -> 평지로 변경
			if(map[nx][ny]=='*') {
				map[nx][ny]='.';
				return;
			}
		}
	}
	
	public static void move(int mx, int my, char act) {
		int nk = 0;
		if(act == 'U') nk = 0;
		else if(act == 'D') nk = 1;
		else if(act == 'L') nk = 2;
		else if(act == 'R') nk = 3;
		int nx = mx+dx[nk];
		int ny = my+dy[nk];
		//맵밖이거나 벽이면 이동 X -> 방향만 변경
		if(nx<0 || nx>=H || ny<0 || ny>=W || map[nx][ny]!='.') {
			k = nk;
			if(k==0) map[x][y]='^';
			else if(k==1) map[x][y]='v';
			else if(k==2) map[x][y]='<';
			else if(k==3) map[x][y]='>';
			return;
		}
		//평지일때만 이동 -> 이동+방향변경
		if(map[nx][ny]=='.') {
			map[x][y]='.';
			x = nx;
			y = ny;
			k = nk;
			if(k==0) map[nx][ny]='^';
			else if(k==1) map[nx][ny]='v';
			else if(k==2) map[nx][ny]='<';
			else if(k==3) map[nx][ny]='>';
		}
	}

}
