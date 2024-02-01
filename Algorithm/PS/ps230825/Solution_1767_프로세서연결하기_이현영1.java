package ps230825;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:32
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 멕시노스는 NxN으로 cell로 구성
 * cell 1개에는 1개 코어 또는 전선 가능
 * 가장 자리에는 전원이 흐름
 * 코어와 전원을 연결하는 전선은 직선으로만 설치 가능
 * 전선은 교차 불가
 * 초기 상태 -> 전선을 연결하기 전 상태의 멕시노스 정보
 * 		멕시노스의 가장 자리에 위치한 코어는 이미 전원 연결됨
 * 최대한 많은 코어에 전원을 연결했을 때, 전선 길이의 합을 구하여라
 * 여러 방법이 있는 경우, 전선 길이의 합이 최소가 되는 값을 구하여라
 *  
 * 
 * 입력
 * 첫째줄: N
 * N개줄: 멕시노스 초기 상태
 * 		0-빈칸, 1-코어
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 입력 받을 때, 코어의 개수 CNT, 각 코어의 위치 (i,j)
 * 최대한 많은 코어에 전원 연결 -> 조합으로 cnt개중 cnt, cnt개중 cnt-1, ...
 * 1. 조합으로 cnt개중 n개(cnt개~1개) 선택
 * 2. 뽑은 조합의 코어로 전부 연결 가능한지 확인
 * 	2-1. 벽에 붙어 있는 코어는 바로 연결
 * 	2-2. 4방의 같은 행,열에 코어 위치하면 실패 -> return;
 * 	2-3. 3방의 같은 행,열에 코어 위치하면 무조건 남은 1방쪽에 전선
 * 		전선 설치한 행 또는 열 영역 전부 true로
 * 	2-4. 남은 코어 가능한 방향 완탐 설치
 * 		안되는 경우 return;
 * 		모든 코어 설치완료 
 * 
 * 부분집합이 별로인 이유
 * nCn~개수 일정하게 연속해서 못뽑음
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

public class Solution_1767_프로세서연결하기_이현영1 {
	static int N, map[][], setmap[][], core_sel, core_cnt, seldir[], min = Integer.MAX_VALUE;
	static int[][] setmap2;
	//상하좌우 순
	static int dx [] = {-1,1,0,0};
	static int dy [] = {0,0,-1,1};
	
	static ArrayList<Pair> remain = new ArrayList<>();
	static ArrayList<Integer> emptylist = new ArrayList<>();
	static Pair select[];
	static Pair core[];
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
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			setmap = new int[N][N];
			
			//코어 최대 개수
			core = new Pair[12];
			core_cnt = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					//코어 인덱스 저장
					if(map[i][j]==1) {
						core[core_cnt++] = new Pair(i,j);
					}
				}
			}
			//코어 최대 개수부터 -1씩 줄이면서 가능한 조합 구하기
			for (int i = core_cnt; i >= 1; i--) {
				core_sel=i;
				select = new Pair[core_sel];
				
				min = Integer.MAX_VALUE;
				comb(0,0);
				if(min!=Integer.MAX_VALUE) {
					break;
				}
			}
			sb.append('#').append(test_case).append(' ').append(min).append('\n');
		}
		System.out.println(sb);
	}
	
	public static void comb(int cnt, int start) {
		if(cnt==core_sel) {
			remain.clear();
			for (int i = 0; i < N; i++) {
				Arrays.fill(setmap[i], 0);
			}
			for(Pair p : select) {
				//2-1. 벽에 붙어 있는 코어는 바로 연결
				if(p.x==0 || p.x==N-1 || p.y==0 || p.y==N-1) {
					//코어 설치
					setmap[p.x][p.y]=1;
					continue;
				}
				setmap[p.x][p.y]=1;
				remain.add(new Pair(p.x,p.y));
			}
			
			int size = remain.size();
			setcore(0,size);
			return;
		}
		
		for (int i = start; i < core_cnt; i++) {
			select[cnt] = core[i];
			comb(cnt+1, i+1);
		}
		
	}
	
	/* 문제해결 프로세스
	 * 입력 받을 때, 코어의 개수 CNT, 각 코어의 위치 (i,j)
	 * 최대한 많은 코어에 전원 연결 -> 조합으로 cnt개중 cnt, cnt개중 cnt-1, ...
	 * 1. 조합으로 cnt개중 n개(cnt개~1개) 선택
	 * 2. 뽑은 조합의 코어로 전부 연결 가능한지 확인
	 * 	2-1. 벽에 붙어 있는 코어는 바로 연결
	 * 	2-2. 4방의 같은 행,열에 코어 위치하면 실패 -> return;
	 * 	2-3. 3방의 같은 행,열에 코어 위치하면 무조건 남은 1방쪽에 전선
	 * 		전선 설치한 행 또는 열 영역 전부 true로
	 * 	2-4. 남은 코어 가능한 방향 완탐 설치
	 * 		안되는 경우 return;
	 * 		모든 코어 설치완료 
	 * 
	 * 부분집합이 별로인 이유
	 * nCn~개수 일정하게 연속해서 못뽑음
	 */
	//dfs로 가지치기
	public static void setcore(int cnt, int s) {
		if(cnt==s) {
			int result = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(setmap[i][j]==2) {
						result++;
						if(result>min) return;
					}
				}
			}
			min = Math.min(min, result);
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			int x = remain.get(cnt).x;
			int y = remain.get(cnt).y;
			boolean success = false;
			while(true) {
				x+=dx[i];
				y+=dy[i];
				if(x<0 || x>=N || y<0 || y>=N) {
					success = true; //true
					break; 
				}
				if(setmap[x][y]!=0) break; //false
				
				//else
				setmap[x][y]=2; //전선 설치
			}
			
			if(success) setcore(cnt+1,s); //다음 코어 전선 설치하러
			
			while(true) {
				x-=dx[i];
				y-=dy[i];
				//다시 시작 위치까지 돌려놓기
				if(x==remain.get(cnt).x && y==remain.get(cnt).y) break;
				setmap[x][y]=0;
			}
			
		}
	}
}
