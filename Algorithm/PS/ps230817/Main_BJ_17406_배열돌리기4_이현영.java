package ps230817;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:18
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxM 크기의 배열 A
 * A의 값은 각 행에 있는 모든 수의 합 중 최솟값
 * 회전 연산
 * 세 정수 (r,c,s)
 * 가장 왼쪽 윗 칸이 (r-s, c-s)
 * 가장 오른쪽 아랫 칸이(r+s,c+s)인 정사각형을 시계 방향으로 한 칸씩 회전
 * 
 * 배열A와 사용 가능한 회전 연산이 주어졌을 때, 배열 A의 값의 최솟값
 * 회전 연산은 1번씩 사용, 순서는 임의로 정함
 * 
 * 
 * 입력
 * 첫째줄: N,M 회전 연산의 개수 K
 * 둘째줄~N개줄: 배열 A에 들어있는 수 A[i][j]
 * 다음 K개줄: 회전 연산 정보 r,c,s
 * 
 * 출력
 * 배열 A의 값의 최솟값(각 행에 있는 모든 수의 합 중 최솟값)
 * 
 * 문제해결 프로세스
 * 배열 회전 함수
 * Rotate(int r-s, int c-s, int r+s, int c+s)
 * 행의 합의 최솟값
 * rowSum(int [][] map)
 * 1. 회전 연산을 K!순서 정하기(순열)
 * 2. 1번에서 정한 회전 연산을 순차적으로 Rotate함수에 대입
 * 3. rowSum을 통해 최솟값 구하기
 * 
 * 
 * 제한조건
 * N,M<=50
 * K<=6
 * r-s,r+s<=N
 * c-s,c+s<=M
 * 
 * 시간복잡도
 * O(K!*K*N*M+K!*N*N)
 * 
 * 
 */
public class Main_BJ_17406_배열돌리기4_이현영 {
	static int N,M,K, map[][], result[][];
	static int min = Integer.MAX_VALUE;
	static Info info[], select[];
	static boolean isSelect[];
	static int r,c,s;
	
	static class Info{
		int r,c,s;
		public Info(int r, int c, int s) {
			super();
			this.r = r;
			this.c = c;
			this.s = s;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N+1][M+1];
		
		//map
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//r,c,s
		info = new Info[K];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			s = Integer.parseInt(st.nextToken());
			info[i] = new Info(r,c,s);
		}
		isSelect = new boolean[K];
		select = new Info[K];
		per(0);
		System.out.println(min);
	}
	public static void rowSum(int [][] A) {
		int sum = 0;
		for (int i = 1; i <= N; i++) {
			sum=0;
			for (int j = 1; j <= M; j++) {
				sum+=A[i][j];
			}
			min = Math.min(min, sum);
		}
	}
	public static void per(int cnt) {
		if(cnt==K) {
			result = Arrays.copyOf(map, map.length);
			for(Info f: select) {
				result = Rotate(f.r-f.s,f.c-f.s,f.r+f.s,f.c+f.s);
			}
			rowSum(result);
			
			return;
		}
		for (int i = 0; i < K; i++) {
			if(isSelect[i]) continue;
			select[cnt]=info[i];
			isSelect[i] = true;
			per(cnt+1);
			isSelect[i] = false;
		}
	}
	/* 문제해결 프로세스
	 * 배열 회전 함수
	 * Rotate(int r-s, int c-s, int r+s, int c+s)
	 * 행의 합의 최솟값
	 * rowSum(int [][] map)
	 * 1. 회전 연산을 K!순서 정하기(순열)
	 * 2. 1번에서 정한 회전 연산을 순차적으로 Rotate함수에 대입
	 * 3. rowSum을 통해 최솟값 구하기
	 */
	
	//우,하,좌,상 순
	static int dx[] = {0,1,0,-1};
	static int dy[] = {1,0,-1,0};
	
	public static int[][] Rotate(int sr, int sc, int er, int ec) {
		//돌린 결과
		int [][] map2 = new int[N+1][M+1];
		int x=sr;
		int y=sc;
		int d=0;
		int cnt=0;
		while(true) {
			int nx = x+dx[d];
			int ny = y+dy[d];
			int rr = sr+cnt;
			int cc = sc+cnt;
			if(cnt==Math.min((er-sr)/2,(ec-sc)/2)) break;
			//다시 시작점으로 돌아오면 cnt++;
			if(nx==rr && ny==cc) {
				map2[nx][ny]=result[x][y];
				cnt++;
				x=rr+1;
				y=cc+1;
				continue;
			}
			//밖으로 나가거나 이미 다른 숫자 적혀있으면 방향 전환
			if(nx<sr || nx>er || ny<sc || ny>ec || map2[nx][ny]!=0) {
				d++;
				if(d==4) {
					d=0;
				}
				continue;
			}
			//else
			map2[nx][ny]=result[x][y];
			x=nx;
			y=ny;
		}
		map2[(sr+er)/2][(sc+ec)/2]=result[(sr+er)/2][(sc+ec)/2];
		
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if(i>=sr && i<=er && j>=sc && j<=ec) {
					continue;
				}
				map2[i][j]=result[i][j];
			}
		}
		return map2;
	}
}
