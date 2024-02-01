package SW역량테스트.ps231009;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작: 22:45
 * 종료: 24:30
 * 
 * 문제해석
 * nxn 격자, 술래는 정중앙
 * m명의 도망자, 도망자는 처음 지정된 곳에 서있음 -> 2종류:상하,좌우
 * 								좌우:항상 오른쪽보고 시작, 상하:항상 아래쪽보고 시작
 * h개의 나무, 초기에 도망자와 겹치기 가능
 * 
 * 게임
 * m명의 도망자 동시에 이동 -> 술래이동 ->다시 도망자이동 :k번반복
 * 술래와 거리가 3초과인 도망자는 안움직임
 * 술래와 거리가 3이하인 도망자 이동규칙
 * - 현재 바라보고 있는 방향으로 1칸 이동
 * 		격자 안벗어남
 * 			- 움직이려는 칸에 술래있으면 이동x
 * 			- 움직이려는 칸에 술래없으면 이동o,나무도 가능
 * 		격자 벗어남
 * 			- 반대방향으로 틀기+ 한칸이동에 술래 없으면 이동o
 * 술래 이동
 * - 달팽이 모양으로 이동->상우하좌 순으로
 * 		끝에 도달하면 다시 거꾸로 중심으로 이동
 * 		다시 중심에 오게되면 처음처럼 상부터 시작해서 시계방향으로 도는 것 k턴 반복
 * 술래는 1턴에 그 방향으로 1칸이동
 * 		이동후 위치가 이동방향이 틀어지는 지점이면 방향바꿈
 * 		(1,1) 또는 정중앙도 역시 방향바꾸고 종료해야됨
 * 이동 직후 술래가 턴을 넘기기 전 시야 내에 도망자 잡음
 * 		술래의 시야=바라보는 방향 기준 현재 칸 포함 3칸
 * 		나무가 놓여있는 칸에 있는 도망자는 나무에 가려져서 안잡힘
 * 		도망자들 잡으면 잡힌 도망자 사라지고, (현재 턴t * 현재턴에서 잡힌 도망자수)만큼 점수 얻음
 * 다시 도망자 턴 시작 -> 술래 턴 진행 k번
 * 
 * 입력
 * 첫째줄 : n(맵크기), m도망자위치(x,y,d), h나무위치(x,y) k(턴수)
 * d=1이면 좌우, d=2이면 상하
 * m개줄: 도망자정보
 * h개줄: 나무위치
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 도망자턴
 * 	술래와 거리 3초과->이동x
 * 	술래와 거리 3이상->이동o
 * 2. 술래턴
 * 	달팽이모양 이동+방향 저장
 * 3. 술래잡기
 * 	술래방향 3칸이내 도망자잡기
 * 		나무칸이면 continue;
 * 		나무x이면 도망자 잡음+(cnt++)
 * 		result+=cnt*턴수;
 * 4. 1~3 k번 반복	
 * 
 * 
 * 제한사항
 * 
 * 
 */

public class Main_CT_술래잡기 {
	static int n,m,h,t,result;
	static boolean tree[][];
	static boolean iscycle = true;
	static int move[];
	static int moveidx;
	static List<Runner> runner = new ArrayList<>();
	static Catcher catcher;
	static class Catcher {
		int x,y,d;
		public Catcher(int x, int y, int d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}
		@Override
		public String toString() {
			return "Catcher [x=" + x + ", y=" + y + ", d=" + d + "]";
		}
	}
	static class Runner {
		int x,y,d;
		public Runner(int x, int y, int d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}
		@Override
		public String toString() {
			return "Runner [x=" + x + ", y=" + y + ", d=" + d + "]";
		}
	}
	//상우하좌 순
	static int dx[]={-1,0,1,0};
	static int dy[]={0,1,0,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		tree = new boolean[n][n];
		catcher = new Catcher(n/2,n/2,0);
		
		makemove();
		
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			runner.add(new Runner(x,y,d));
		}
		for (int i = 0; i < h; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			tree[x][y] = true;
		}
		
		for (int i = 1; i <= t; i++) {
			runnerturn();
			catcherturn();
			result+=i*catching();
		}
		System.out.println(result);
	}
	/* 문제해결 프로세스
	 * 1. 도망자턴
	 * 	술래와 거리 3초과->이동x
	 * 	술래와 거리 3이상->이동o
	 * 2. 술래턴
	 * 	달팽이모양 이동+방향 저장
	 * 3. 술래잡기
	 * 	술래방향 3칸이내 도망자잡기
	 * 		나무칸이면 continue;
	 * 		나무x이면 도망자 잡음+(cnt++)
	 * 		result+=cnt*턴수;
	 * 4. 1~3 k번 반복
	 */
	/* 게임
	 * m명의 도망자 동시에 이동 -> 술래이동 ->다시 도망자이동 :k번반복
	 * 술래와 거리가 3초과인 도망자는 안움직임
	 * 술래와 거리가 3이하인 도망자 이동규칙
	 * - 현재 바라보고 있는 방향으로 1칸 이동
	 * 		격자 안벗어남
	 * 			- 움직이려는 칸에 술래있으면 이동x
	 * 			- 움직이려는 칸에 술래없으면 이동o,나무도 가능
	 * 		격자 벗어남
	 * 			- 반대방향으로 틀기+ 한칸이동에 술래 없으면 이동o
	 * 술래 이동
	 * - 달팽이 모양으로 이동->상우하좌 순으로
	 * 		끝에 도달하면 다시 거꾸로 중심으로 이동
	 * 		다시 중심에 오게되면 처음처럼 상부터 시작해서 시계방향으로 도는 것 k턴 반복
	 * 술래는 1턴에 그 방향으로 1칸이동
	 * 		이동후 위치가 이동방향이 틀어지는 지점이면 방향바꿈
	 * 		(1,1) 또는 정중앙도 역시 방향바꾸고 종료해야됨
	 * 이동 직후 술래가 턴을 넘기기 전 시야 내에 도망자 잡음
	 * 		술래의 시야=바라보는 방향 기준 현재 칸 포함 3칸
	 * 		나무가 놓여있는 칸에 있는 도망자는 나무에 가려져서 안잡힘
	 * 		도망자들 잡으면 잡힌 도망자 사라지고, (현재 턴t * 현재턴에서 잡힌 도망자수)만큼 점수 얻음
	 * 다시 도망자 턴 시작 -> 술래 턴 진행 k번
	 */
	public static void makemove() {
		move = new int[(n-1)*2+1];
		for (int i = 0; i < (n-1); i++) {
			move[2*i] = i+1;
			move[2*i+1] = i+1;
		}
		move[2*(n-1)] = n-1;
		if(iscycle) moveidx=0;
		else moveidx=2*(n-1);
	}
	
	public static void runnerturn() {
		for(Runner rn: runner) {
			int x = rn.x;
			int y = rn.y;
			int d = rn.d;
			//술래와 거리 계산
			int distance = Math.abs(x-catcher.x)+Math.abs(y-catcher.y);
			//이동o
			if(distance<=3) {
				x+=dx[d];
				y+=dy[d];
				//격자 안벗어남
				if(rangecheck(x,y)){
					if(x==catcher.x && y==catcher.y) continue;
					else {
						rn.x = x;
						rn.y = y;
					}
				}
				//격자 벗어남
				else {
					//되돌리기
					x-=dx[d];
					y-=dy[d];
					//방향 반대로 틀기
					d+=2;
					d%=4;
					x+=dx[d];
					y+=dy[d];
					if(x==catcher.x && y==catcher.y) continue;
					else {
						rn.x = x;
						rn.y = y;
						rn.d = d;
					}
				}
			}
		}
	}
	

	public static void catcherturn() {
		int x = catcher.x;
		int y = catcher.y;
		int d = catcher.d;
		
		//11223344 5(범위밖).. 
		
		int nx = x+dx[d];
		int ny = y+dy[d];
		//방향 전환점, (0,0) 또는 (n/2,n/2)
		//순-> 역
		if(nx==0 && ny==0) {
			catcher.x = nx;
			catcher.y = ny;
			catcher.d = 2;
			iscycle = false;
			makemove();
		}
		//역-> 순
		else if(nx==n/2 && ny==n/2) {
			catcher.x = nx;
			catcher.y = ny;
			catcher.d = 0;
			iscycle = true;
			makemove();
		}
		else {
			move[moveidx]--;
			if(move[moveidx]==0) {
				if(iscycle) {
					moveidx++;
					catcher.d++;
					if(catcher.d>=4) catcher.d-=4;
				}
				else {
					moveidx--;
					catcher.d--;
					if(catcher.d<0) catcher.d+=4;
				}
				
				catcher.x = nx;
				catcher.y = ny;
				
			}
			else {
				catcher.x = nx;
				catcher.y = ny;
			}
		}
		
		
	}
	public static int catching() {
		int count = 0;
		int d = catcher.d;
		int nx = catcher.x-dx[d];
		int ny = catcher.y-dy[d];
		
		for (int i = 0; i < 3; i++) {
			nx+=dx[d];
			ny+=dy[d];
			if(!rangecheck(nx,ny) || tree[nx][ny]) continue;
			//else
			int size = runner.size();
			for(int k = size-1; k>=0; k--) {
				Runner rn = runner.get(k);
				if(rn.x==nx && rn.y==ny) {
					runner.remove(k);
					count++;
				}
			}
		}
		return count;
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<n && ry>=0 && ry<n;
	}
}
