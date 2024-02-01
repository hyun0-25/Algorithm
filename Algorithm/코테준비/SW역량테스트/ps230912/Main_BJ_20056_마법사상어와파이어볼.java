package SW역량테스트.ps230912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN인 격자에 파이어볼 M개 발사
 * 가장 처음에 파이어볼은 각자 위치에서 이동을 대기
 * i번 파이어볼의 위치는 (ri,ci), 질량 mi, 방향 di, 속력 si
 * 
 * 격자의 행,열은 1~N까지 번호, 1번행-N번과 연결 / 1번열-N번열과 연결
 * 파이어볼의 방향은 인접한 8방 칸
 * 
 * 이동명령
 * 1. 모든파이어볼은 자신의 방향 di로 속력 si칸만큼 이동
 * 		이동하는 중에는 같은 칸에 여러개의 파이어볼이 있을 수 있음
 * 2. 이동이 모두 끝난뒤, 2개 이상의 파이어볼이 있는 칸
 * 		2-1. 같은 칸에 있는 파이어볼 모두 하나로 합침
 *  	2-2. 파이어볼은 4개의 파이어볼로 나누어짐
 *  	2-3. 나누어진 파이어볼의 질량,속력,방향
 *  		1. 질량은 (합쳐진 파이어볼 질량의 합)/5
 *  		2. 속력은 (합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)
 *  		3. 합쳐지는 파이어볼의 방향이 모두 홀수거나 모두 짝수면 방향은 0,2,4,6
 *  						그렇지 않으면 1,3,5,7
 *  	2-4. 질량이 0인 파이어볼은 소멸되어 없어짐
 * 마법사 상어가 이동을 K번 명령한 후, 남아있는 파이어볼 질량의 합
 *  	
 *  			
 * 입력
 * 첫째줄 : N,M,K
 * M개줄 : M개 파이어볼 정보 r,c,m,s,d
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * fireball 클래스 r,c,m,s,d
 * ArrayList<fireball> map[][] 
 * 1. 파이어볼 (i,j)에서 제거 -> 이동 위치에 추가
 * 						fireball클래스의 인자의 r,c도 이동위치로 수정
 * 2. 같은 칸에 2개 이상 파이어볼 존재하면 하나로 합치고 파이어볼 4개로 나누기
 * 3. 1,2번 K번 반복
 * 4. 남아 있는 파이어볼 질량의 합
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

public class Main_BJ_20056_마법사상어와파이어볼 {
	static class FireBall{
		int r,c,m,s,d;
		public FireBall(int r, int c, int m, int s, int d) {
			super();
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}
	static int N,M,K;
	static ArrayList<FireBall> map[][];
	static ArrayList<FireBall> fblist = new ArrayList<>();
	
	static int dx[] = {-1,-1,0,1,1,1,0,-1};
	static int dy[] = {0,1,1,1,0,-1,-1,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayList<>();
			}
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			FireBall fireball = new FireBall(r,c,m,s,d);
			map[r][c].add(fireball);
			fblist.add(fireball);
		}
		for (int i = 0; i < K; i++) {
			move();
		}
		
		int sum = 0;
		for(FireBall fb: fblist) {
			sum+=fb.m;
		}
		System.out.println(sum);
		
	}
	/* 문제해결 프로세스
	 * fireball 클래스 r,c,m,s,d
	 * ArrayList<fireball> map[][] 
	 * 1. 파이어볼 (i,j)에서 제거 -> 이동 위치에 추가
	 * 						fireball클래스의 인자의 r,c도 이동위치로 수정
	 * 2. 같은 칸에 2개 이상 파이어볼 존재하면 하나로 합치고 파이어볼 4개로 나누기
	 * 3. 1,2번 K번 반복
	 * 4. 남아 있는 파이어볼 질량의 합
	 */
	public static void move() {
		for(FireBall fb: fblist) {
			int dir = fb.d;
			int nx = fb.r;
			int ny = fb.c;
			map[fb.r][fb.c].remove(fb);
			for (int i = 0; i < fb.s; i++) {
				nx+=dx[dir];
				ny+=dy[dir];
				if(nx>=N) nx=0;
				if(nx<0) nx=N-1;
				if(ny>=N) ny=0;
				if(ny<0) ny=N-1;
			}
			fb.r = nx;
			fb.c = ny;
			map[fb.r][fb.c].add(fb);
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j].size()>=2) {
					int mass = 0;
					int speed = 0;
					int size = map[i][j].size();
					
					int value = map[i][j].get(0).d%2;
					boolean issame = true;
					
					for(FireBall fbidx: map[i][j]) {
						mass+=fbidx.m;
						speed+=fbidx.s;
						//방향 짝->홀이거나 홀->짝이면
						if(value!=fbidx.d%2) issame = false;
						fblist.remove(fbidx);
					}
					
					map[i][j].clear();
					
					mass /= 5;
					if(mass==0) continue;
					speed /= size;
					
					int direction;
					// 0,2,4,6
					if(issame) {
						direction = 0;
					}
					// 1,3,5,7
					else {
						direction = 1;
					}
					
					for (int k = 0; k < 4; k++) {
						FireBall fireball = new FireBall(i,j,mass,speed,direction);
						map[i][j].add(fireball);
						fblist.add(fireball);
						direction+=2;
					}
				}
			}
		}
	}

}
