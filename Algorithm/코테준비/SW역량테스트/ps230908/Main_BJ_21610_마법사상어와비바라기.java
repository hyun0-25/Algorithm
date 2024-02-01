package SW역량테스트.ps230908;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 12:54
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 크기가 NxN인 격자에서 연습
 * 각 칸에는 바구니가 1개
 * 바구니에 저장할 수 있는 물의 양에는 제한 없음
 * (r,c) -> A[r][c] 저장된 물의 양
 * 1-N행 연결 = N번행 아래에는 1번행
 * 1-N열 연결  = N번열 오른쪽에는 1번 열
 * 비바라기 시전 -> (N,1)(N,2)(N-1,1)(N-1,2)에 비구름
 * 구름에 이동을 M번 명령
 * i번째 이동 명령 -> 방향 d와 거리 s
 * 		좌,좌상,상,우상,우,우하,하,좌하 순
 * 이동명령 진행 방식
 * 1. 모든 구름이 d방향으로 s칸이동
 * 2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양 1 증가
 * 3. 구름 모두 사라짐
 * 4. 2에서 증가한 칸에 물복사버그 마법 시전 
 * 	물복사버그 마법: 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니 수 만큼 (r,c) 바구니의 물의 양 증가
 * 		(이동과 다르게) 경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아님
 * 		예) (N,2)에 인접한 대각선칸 = (N-1,1)(N-1,3)
 * 			(N,N)에 인접한 대각선칸 = (N-1,N-1)뿐
 * 5. 바구니에 저장된 물의 양이 2이상인 모든 칸에 구름 -> 물의양 -2
 * 		구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야함
 * 
 * M번의 이동이 모드 끝난 후 바구니에 들어있는 물의 양의 합
 * 
 * 
 * 입력
 * 첫째줄: N,M
 * N개줄: NxN
 * M개줄: 이동 정보 d,s
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 	M번 이동
 * 구름 이동 함수 d방향으로 s칸 이동 : cloudmove()
 * 		구름이 있는 칸의 바구니 물의 양 +1
 * 		구름 삭제
 * 물이 증가한 칸에 물복사버그 마법 watercopybug()
 * 		대각선 방향으로 거리가 1인 칸에 물이 있는 바구니만큼 (r,c)에 물의 양 증가
 * 구름생성 함수 : makecloud()
 * 		바구니에 저장된 물의 양이 2이상인 모든 칸에 구름 생성
 * 		물의 양 -2
 * 		단, 구름이 생기는 칸은 cloudmove에서 삭제된 구름이 아니어야함
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

public class Main_BJ_21610_마법사상어와비바라기 {
	static int N,M, A[][];
	//좌,좌상,상,우상,우,우하,하,좌하 순
	static int dx[] = {0,-1,-1,-1,0,1,1,1};
	static int dy[] = {-1,-1,0,1,1,1,0,-1};
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static ArrayList<Pair> cloud = new ArrayList<>();
	static ArrayList<Pair> deletecloud = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		A = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//초기 구름
		cloud.add(new Pair(N-1,0));
		cloud.add(new Pair(N-1,1));
		cloud.add(new Pair(N-2,0));
		cloud.add(new Pair(N-2,1));
		
		//d,s 정보 M개
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			
			cloudmove(d,s);
			watercopybug();
			makecloud();
		}
		
		int result=0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				result+=A[i][j];
			}
		}
		System.out.println(result);
	}
	/* 문제해결 프로세스
	 * 	M번 이동
	 * 구름 이동 함수 d방향으로 s칸 이동 : cloudmove(int d, int s)
	 * 		구름이 있는 칸의 바구니 물의 양 +1
	 * 		구름 삭제
	 * 물이 증가한 칸에 물복사버그 마법 watercopybug()
	 * 		대각선 방향으로 거리가 1인 칸에 물이 있는 바구니만큼 (r,c)에 물의 양 증가
	 * 구름생성 함수 : makecloud()
	 * 		바구니에 저장된 물의 양이 2이상인 모든 칸에 구름 생성
	 * 		물의 양 -2
	 * 		단, 구름이 생기는 칸은 cloudmove에서 삭제된 구름이 아니어야함
	 */
	public static void cloudmove(int dir, int size) {
		//이동
		for (Pair p: cloud) {
			int nx = p.x;
			int ny = p.y;
			for (int i = 0; i < size; i++) {
				nx+=dx[dir];
				ny+=dy[dir];
				if(nx<0) nx = N-1;
				if(nx>=N) nx = 0;
				if(ny<0) ny = N-1;
				if(ny>=N) ny = 0;
			}
			p.x = nx;
			p.y = ny;
		}
		deletecloud.clear();
		//물의양 +1
		for(Pair p: cloud) {
			int x = p.x;
			int y = p.y;
			A[x][y]++;
			deletecloud.add(p);
		}
		cloud.clear();
	}
	//좌,좌상,상,우상,우,우하,하,좌하 순
	//1,3,5,7
	public static void watercopybug() {
		for(Pair p: deletecloud) {
			int cnt=0;
			for (int i = 1; i < 8; i+=2) {
				int mx = p.x+dx[i];
				int my = p.y+dy[i];
				if(mx>=0 && mx<N && my>=0 && my<N) {
					if(A[mx][my]>0) cnt++;
				}
			}
			A[p.x][p.y]+=cnt;
		}
	}
	public static void makecloud() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(A[i][j]>=2) {
					boolean flag=false;
					for(Pair pp : deletecloud) {
						if(pp.x==i && pp.y==j) {
							flag=true;
						}
					}
					if(!flag) {
						cloud.add(new Pair(i,j));
						A[i][j]-=2;
					}
				}
			}
		}
	}
}
