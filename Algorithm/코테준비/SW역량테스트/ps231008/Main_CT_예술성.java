package SW역량테스트.ps231008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 23:35
 * 종료시간:
 * 
 * 
 * 문제 해석
 * nxn크기의 격자에서 각 칸의 색깔을 1~10의 숫자로 표현
 * 상하좌우로 인접해있는 경우 동일한 그룹
 * 예술점수 = 모든 그룹 쌍의 조화로움의 합
 * a,b의 조화로움 = (그룹a 속한칸의수+그룹b 속한칸의수)*그룹a의숫자값*그룹b의숫자값* 그룹 a,b가 서로맞닿아있는변의 수
 * 
 * 입력
 * 
 * 
 * 출력
 * 초기,1,2,3회전 후 예술 점수를 모두 합한 값
 * 
 * 문제해결 프로세스
 * 함수 - 4분할 회전 함수
 * 		예술 점수 구하는 함수
 * 		그룹쌍 뽑는 gC2-> 조합
 * 맞닿아있지 않으면 구할필요없음
 * 1. 새로운 [][]에 cnt로 그룹번호 저장 + []에 그룹의 값 저장
 * 2. bfs로 돌면서 맞닿아있는 면 정보 저장
 * 	2-1. 다음 이동 위치가 다른 숫자면, 각 그룹에 해당되는 []에 cnt값++
 * 	2-2. 		같은 숫자면, 그 그룹의 속한 칸의 수++
 * 3. 조화로움 구할때 그룹a의 0이 아닌 값에 대해 모두 곱한값 더함
 * 	3-1. 예술점수/2 -> 왜냐면 a,b나 b,a가 동일하니까
 * 4. 회전
 * 5. 1~4번 3번씩 반복
 * 
 * 제한조건
 * n<=29
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */


public class Main_CT_예술성 {
	static int N, map[][], nummap[][], groupnum, result, newmap[][];
	//맞닿은 면 정보
	static int groupinfo[][];
	//칸의 수
	static int groupidx[];
	static int groupn[];
	static boolean isvisited[][];
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//초기
		isvisited = new boolean[N][N];
		nummap = new int[N][N];
		start();
		
		//1,2,3회전
		for (int i = 0; i < 3; i++) {
			turn();
			isvisited = new boolean[N][N];
			nummap = new int[N][N];
			groupinfo = new int[N][N];
			start();
		}
		System.out.println(result);
	}
	/* 문제해결 프로세스
	 * 함수 - 4분할 회전 함수
	 * 		예술 점수 구하는 함수
	 * 맞닿아있지 않으면 구할필요없음
	 * 1. 새로운 [][]에 cnt로 그룹번호 저장 + []에 그룹의 값 저장
	 * 2. bfs로 돌면서 맞닿아있는 면 정보 저장
	 * 	2-1. 다음 이동 위치가 다른 숫자면, 각 그룹에 해당되는 []에 cnt값++
	 * 	2-2. 		같은 숫자면, 그 그룹의 속한 칸의 수++
	 * 3. 조화로움 구할때 그룹a의 0이 아닌 값에 대해 모두 곱한값 더함
	 * 	3-1. 예술점수-> 1~N까지 모두 구함
	 * 4. 회전
	 * 5. 1~4번 3번씩 반복
	 */
	public static void turn() {
		newmap = new int[N][N];
		//4분할 회전
		//1번째 좌상, 행 j 열 n-i
		for (int i = 0; i < N/2; i++) {
			for (int j = 0; j < N/2; j++) {
				newmap[j][N/2-i-1] = map[i][j];
			}
		}
		//2번째 우상
		for (int i = 0; i < N/2; i++) {
			for (int j = N/2+1; j < N; j++) {
				newmap[j-N/2-1][N-1-i] = map[i][j];
			}
		}
		//3번째 좌하
		for (int i = N/2+1; i < N; i++) {
			for (int j = 0; j < N/2; j++) {
				int ix = i-N/2-1;
				newmap[j+N/2+1][N-1-i] = map[i][j];
			}
		}
		//4번째 우하
		for (int i = N/2+1; i < N; i++) {
			for (int j = N/2+1; j < N; j++) {
				int ix = i-N/2-1;
				newmap[j][N-1-ix] = map[i][j];
			}
		}
		
		//십자 회전
		newmap[N/2][N/2] = map[N/2][N/2];
		for (int i = 0; i < N/2; i++) {
			//상하좌우
			newmap[N/2][i] = map[i][N/2];
			newmap[N/2][N-1-i] = map[N-1-i][N/2];
			newmap[N-1-i][N/2] = map[N/2][i];
			newmap[i][N/2] = map[N/2][N-1-i];
		}
		
		for (int i = 0; i < N; i++) {
			map[i] = Arrays.copyOf(newmap[i], N);
		}
	}
	public static void start() {
		groupnum = 0;
		//그룹화
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j]!=0 && !isvisited[i][j]) {
					groupnum++;
					bfs(i,j,groupnum);
				}
			}
		}
		groupinfo = new int[groupnum+1][groupnum+1];
		groupidx = new int[groupnum+1];
		groupn = new int[groupnum+1];
		//맞닿은 면 처리
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j]!=0 && isvisited[i][j]) {
					line(i,j);
					groupn[nummap[i][j]] = map[i][j];
				}
			}
		}
		
		for (int i = 1; i <= groupnum; i++) {
			for (int j = 1; j <= groupnum; j++) {
				if(groupinfo[i][j]!=0) {
					int n_a = groupidx[i];
					int n_b = groupidx[j];
					int g_a = groupn[i];
					int g_b = groupn[j];
					int ab = groupinfo[i][j];
					int cal = (n_a+n_b)*g_a*g_b*ab;
					result += cal;
				}
			}
		}
	}
	public static void line(int xx, int yy) {
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(xx,yy));
		isvisited[xx][yy]=false;
		groupidx[nummap[xx][yy]]++;
				
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(!rangecheck(nx,ny) || !isvisited[nx][ny]) continue;
				if(nummap[nx][ny] == nummap[p.x][p.y]) {
					q.offer(new Pair(nx,ny));
					isvisited[nx][ny]=false;
					groupidx[nummap[nx][ny]]++;
				}
				else {
					groupinfo[nummap[p.x][p.y]][nummap[nx][ny]]++;
				}
			}
		}
	}
	
	public static void bfs(int xx, int yy, int gnum) {
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(xx,yy));
		nummap[xx][yy] = gnum;
		isvisited[xx][yy]=true;
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(!rangecheck(nx,ny) || isvisited[nx][ny]) continue;
				if(map[nx][ny] == map[p.x][p.y]) {
					q.offer(new Pair(nx,ny));
					nummap[nx][ny] = gnum;
					isvisited[nx][ny]=true;
				}
			}
		}
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}
