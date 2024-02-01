package SW역량테스트.ps231010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작: 16:50 21:30
 * 종료: 약 2시간
 * 
 * 문제해석
 * nxn 격자에서 각각의 격자에는 무기들 존재
 * 초기에는 무기들이 없는 빈 격자에 플레이어들이 위치, 각 플레이어는 초기 능력치를 가짐, 초기능력치는 각자 다름
 * 총은 공격력/플레이어는 초기능력치/숫자는 플레이어번호
 * 
 * 라운드 진행
 * 1. 1번플레이어부터 본인이 향하고 있는 방향대로 한칸이동
 * 		해당 방당이 격자를 벗어나면 정반대 방향으로 방향을 바꿔서 한칸이동
 * 2. 이동한 방향에 플레이어 없으면, 해당 칸에 총이 있는지 확인
 * 		총이 있으면 -> 플레이어 총 획득
 * 		플레이어가 이미 총있음 -> 놓여있는 총들과 가지고 있는 총 중 가장 쎈 총 획득, 나머지 격자에 둠
 * 	2-1. 이동한 방향에 플레이어 있는 경우, 2명 플레이어 싸움
 * 		초기능력치+총의 공격력의 합 비교해서 더크면 승리
 * 			같은 경우 -> 플레이어의 초기 능력치가 높은 플레이어가 승리
 * 	2-2. 승리한 플레이어는 각 플레이어의 초기 능력치+총의 공격력의합의 차이만큼 포인트 획득
 * 		 패배한 플레이어는 본인이 가지고 있는 총을 해당 위치에 내려놓고, 원래 방향으로 한칸 이동
 * 			이동하려는 칸에 다른 플레이어 있거나 격자 범위 밖이면 오른쪽으로 90씩 회전하여 빈칸이 보이는 순간 이동
 * 			해당 칸에총이 있다면, 플레이어는 가장 공격력이 높은 총 획득하고 나머지 총들은 해당 격자에 내려놓음
 * 	2-3. 승리한 플레이어는 승리한 칸에 떨어진 총과 원래 총을 비교해 가장 공격력 높은 총 획득, 나머지 내려놓음
 * 3. 1~n번 플레이어까지 진행
 * 4. 라운드 결과 -> k라운드동안 전체 플레이어가 획득한 포인트를 출력
 * 
 * 
 * 입력
 * 첫째줄: n격자크기,m플레이어수,k라운드수
 * n개줄: 격자의 총정보
 * 0은빈칸,0보다 큰값은 총의 공격력
 * m개줄: 플레이어정보 x,y,d,s(초기능력치)
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결프로세스
 * 
 * 총 고르는 함수 pickgun
 * 2명 플레이어 싸우는 함수 fight
 * 
 *  * 1. 1번플레이어부터 본인이 향하고 있는 방향대로 한칸이동 (rangecheck && player존재하면 fight())
 * 		해당 방향이 격자를 벗어나면 (!rangecheck) 정반대 방향으로 방향을 바꿔서 한칸이동
 * 2. 이동한 방향에 플레이어 없으면, 해당 칸에 총이 있는지 확인
 * 		총이 있으면 -> 플레이어 총 획득
 * 		플레이어가 이미 총있음 -> 놓여있는 총들과 가지고 있는 총 중 가장 쎈 총 획득, 나머지 격자에 둠
 * 	2-1. 이동한 방향에 플레이어 있는 경우, 2명 플레이어 싸움
 * 		초기능력치+총의 공격력의 합 비교해서 더크면 승리
 * 			같은 경우 -> 플레이어의 초기 능력치가 높은 플레이어가 승리
 * 	2-2. 승리한 플레이어는 각 플레이어의 초기 능력치+총의 공격력의합의 차이만큼 포인트 획득
 * 		 패배한 플레이어는 본인이 가지고 있는 총을 해당 위치에 내려놓고, 원래 방향으로 한칸 이동
 * 			이동하려는 칸에 다른 플레이어 있거나 격자 범위 밖이면 오른쪽으로 90씩 회전하여 빈칸이 보이는 순간 이동
 * 			해당 칸에총이 있다면, 플레이어는 가장 공격력이 높은 총 획득하고 나머지 총들은 해당 격자에 내려놓음
 * 	2-3. 승리한 플레이어는 승리한 칸에 떨어진 총과 원래 총을 비교해 가장 공격력 높은 총 획득, 나머지 내려놓음
 * 3. 1~n번 플레이어까지 진행
 * 4. 라운드 결과 -> k라운드동안 전체 플레이어가 획득한 포인트를 출력
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_CT_싸움땅 {
	//상우하좌 순
	static int dx[] = {-1,0,1,0};
	static int dy[] = {0,1,0,-1};
	static int n,m,k;
	static List<Integer> [][] map;
	static List<Player> playerlist = new ArrayList<>();
	static int point[];
	static class Player {
		int x,y,dir,val,gun;
		public Player(int x, int y, int dir, int val, int gun) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.val = val;
			this.gun = gun;
		}
		@Override
		public String toString() {
			return "Player [x=" + x + ", y=" + y + ", dir=" + dir + ", val=" + val + ", gun=" + gun + "]";
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		map = new List[n][n];
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = new ArrayList<>();
				map[i][j].add(Integer.parseInt(st.nextToken()));
			}
		}
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			playerlist.add(new Player(x,y,d,s,0));
		}
		
		point = new int[m];
		for (int i = 0; i < k; i++) {
			play();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			sb.append(point[i]).append(' ');
		}
		System.out.println(sb);
	}
	/* 문제해결프로세스
	 * 
	 * 총 고르는 함수 pickgun
	 * 2명 플레이어 싸우는 함수 fight
	 * 
	 *  * 1. 1번플레이어부터 본인이 향하고 있는 방향대로 한칸이동 (rangecheck && player존재하면 fight())
	 * 		해당 방향이 격자를 벗어나면 (!rangecheck) 정반대 방향으로 방향을 바꿔서 한칸이동
	 * 2. 이동한 방향에 플레이어 없으면, 해당 칸에 총이 있는지 확인
	 * 		총이 있으면 -> 플레이어 총 획득
	 * 		플레이어가 이미 총있음 -> 놓여있는 총들과 가지고 있는 총 중 가장 쎈 총 획득, 나머지 격자에 둠
	 * 	2-1. 이동한 방향에 플레이어 있는 경우, 2명 플레이어 싸움
	 * 		초기능력치+총의 공격력의 합 비교해서 더크면 승리
	 * 			같은 경우 -> 플레이어의 초기 능력치가 높은 플레이어가 승리
	 * 	2-2. 승리한 플레이어는 각 플레이어의 초기 능력치+총의 공격력의합의 차이만큼 포인트 획득
	 * 		 패배한 플레이어는 본인이 가지고 있는 총을 해당 위치에 내려놓고, 원래 방향으로 한칸 이동
	 * 			이동하려는 칸에 다른 플레이어 있거나 격자 범위 밖이면 오른쪽으로 90씩 회전하여 빈칸이 보이는 순간 이동
	 * 			해당 칸에총이 있다면, 플레이어는 가장 공격력이 높은 총 획득하고 나머지 총들은 해당 격자에 내려놓음
	 * 	2-3. 승리한 플레이어는 승리한 칸에 떨어진 총과 원래 총을 비교해 가장 공격력 높은 총 획득, 나머지 내려놓음
	 * 3. 1~n번 플레이어까지 진행
	 * 4. 라운드 결과 -> k라운드동안 전체 플레이어가 획득한 포인트를 출력
	 */
	public static void play() {
		for (Player p : playerlist) {
			int d = p.dir;
			int nx = p.x+dx[d];
			int ny = p.y+dy[d];
			if(!rangecheck(nx,ny)) {
				d+=2;
				if(d>3) d-=4;
				p.dir = d;
				nx = p.x+dx[d];
				ny = p.y+dy[d];
			}
			p.x = nx;
			p.y = ny;
			Player mp = meetplayer(p);
			if(mp!=null) {
				fight(p, mp);
			}
			else {
				pickgun(p);
			}
			
		}
	}
	public static void loser(Player lp) {
		int x = lp.x;
		int y = lp.y;
		int d = lp.dir;
		
		map[x][y].add(lp.gun);
		lp.gun = 0;
		
		for(int i = 0; i < 4; i++) {
			int nx = x+dx[d];
			int ny = y+dy[d];
			if(!rangecheck(nx,ny) || meetplayer(new Player(nx,ny,d,lp.val,lp.gun))!=null) {
				d++;
				if(d>3) d%=4;
				continue;
			}
			else {
				lp.dir = d;
				lp.x = nx;
				lp.y = ny;
				break;
			}
		}
		pickgun(lp);
	}
	public static void fight(Player p1, Player p2) {
		int p1val = p1.val;
		int p1gun = p1.gun;
		int p2val = p2.val;
		int p2gun = p2.gun;
		int compare = (p1val+p1gun) - (p2val+p2gun);
		if(compare==0) {
			compare = p1val - p2val;
		}
		//p1승리
		if(compare>0) {
			compare = (p1val+p1gun) - (p2val+p2gun);
			int idx = playerlist.indexOf(p1);
			point[idx]+=compare;
			loser(p2);
			pickgun(p1);
		}
		//p2승리
		else {
			compare = (p2val+p2gun) - (p1val+p1gun);
			int idx = playerlist.indexOf(p2);
			point[idx]+=compare;
			loser(p1);
			pickgun(p2);
		}
		
	}
	
	public static void pickgun(Player p1) {
		int x = p1.x;
		int y = p1.y;
		if(map[x][y].size()==0) {
			return;
		}
		else {
			int max = 0;
			int maxidx = -1;
			for (int i = 0; i < map[x][y].size(); i++) {
				if(map[x][y].get(i) > max) {
					max = Math.max(max, map[x][y].get(i));
					maxidx = i;
				}
			}
			if(max > p1.gun) {
				if(p1.gun>0) map[x][y].add(p1.gun);
				map[x][y].remove(maxidx);
				p1.gun = max;
			}
		}
	}
	
	public static Player meetplayer(Player p1) {
		for(Player pp : playerlist) {
			if(pp!=p1 & pp.x==p1.x && pp.y==p1.y) {
				return pp;
			}
		}
		return null;
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<n && ry>=0 && ry<n;
	}
}
