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

public class Solution_1767_프로세서연결하기_이현영 {
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
		ArrayList<Integer> d;
		public Pair(int x, int y, ArrayList<Integer> d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		//코어 최대 개수
		core = new Pair[12];
		core_cnt = 0;
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			setmap = new int[N][N];
			setmap2 = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					//코어 인덱스 저장
					if(map[i][j]==1) {
						emptylist.clear();
						core[core_cnt++] = new Pair(i,j,emptylist);
					}
				}
			}
			//코어 최대 개수부터 -1씩 줄이면서 가능한 조합 구하기
			for (int i = core_cnt; i >= 1; i--) {
				core_sel=i;
				select = new Pair[core_sel];
				
				System.out.println(core_sel+"개 선택 ");
				comb(0,0);
			}
			
		}
	}
	
	public static void comb(int cnt, int start) {
		if(cnt==core_sel) {
			setcore(select);
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
	public static void setcore(Pair[] sel) {
		remain.clear();
		
		//코어 1
		//전선 2
		for (int i = 0; i < N; i++) {
			Arrays.fill(setmap[i], 0);
		}
		
		for(Pair p : sel) {
			//2-1. 벽에 붙어 있는 코어는 바로 연결
			if(p.x==0 || p.x==N-1 || p.y==0 || p.y==N-1) {
				//코어 설치
				setmap[p.x][p.y]=1;
				continue;
			}
			setmap[p.x][p.y]=1;
			emptylist.clear();
			remain.add(new Pair(p.x,p.y,emptylist));
		}
		int size = remain.size();
		for(int i=size-1; i>=0; i--) {
			//2-3. 3방의 같은 행,열에 코어 위치하면 무조건 남은 1방쪽에 전선
			int px = remain.get(i).x;
			int py = remain.get(i).y;
			if(direction(px,py).size()==1) {
				//전선 설치 가능하면
				if(setline(px, py, direction(px,py),setmap)) {
					remain.remove(i);
				};
			}
		}
		
		//2-2. 4방의 같은 행,열에 코어 위치하면 실패 -> return;
		size = remain.size();
		for(int i=size-1; i>=0; i--) {
			int px = remain.get(i).x;
			int py = remain.get(i).y;
			if(direction(px,py).size()==0) {
				return;
			}
			//설치 가능한 방향이 1개 이상이면 방향들 객체에 저장
			remain.get(i).d = direction(px,py);
		}
		
		//2-4. 남은 코어 가능한 방향 완탐 설치
		//넘겨줄 변수: 남은 코어들
		//뽑을 변수: 남은 코어들의 방향 중복 순열
		seldir = new int [remain.size()];
		
		min = Integer.MAX_VALUE;
		selectdir(0, remain.size());
		if(min!=Integer.MAX_VALUE) {
			System.out.println("결과 "+min);
			System.exit(0);
		}
		
		
		for (Pair p : remain) {
			System.out.println(p.x + " "+p.y);
		}
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(setmap[i]));
		}
	}
	
	//remain에 남아있는 코어의 방향들 결정하는 함수
	public static void selectdir(int cnt, int coresize) {
		if(cnt==coresize) {
//			for (int i = 0; i < N; i++) {
//				setmap2[i] = Arrays.copyOf(setmap[i], N);
//			}
//			
//			//코어의 방향에 따라 설치
//			for (int i = 0; i < coresize; i++) {
//				emptylist.clear();
//				emptylist.add(seldir[i]);
//				if(!setline(remain.get(i).x, remain.get(i).y, emptylist, setmap2)) {
//					return;
//				}
//			}
			//정상 탈출
			int result = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(setmap[i][j]==2) {
						result++;
						if(result>min) return;
					}
				}
			}
			System.out.println(result);
			min = Math.min(min, result);
			System.out.println(Arrays.toString(seldir));
			for (int i = 0; i < N; i++) {
				System.out.println(Arrays.toString(setmap[i]));
			}
			System.out.println();
			return;
		}
		
		for (int i = 0; i < remain.get(cnt).d.size(); i++) {
			emptylist.clear();
			emptylist.add(remain.get(cnt).d.get(i));
			if(!setline(remain.get(cnt).x, remain.get(cnt).y, emptylist, setmap)) {
				System.out.println("cnt "+cnt);
				continue;
			}
			seldir[cnt] = remain.get(cnt).d.get(i);
			///////////////
			//여기서 가지치기//
			///////////////
			selectdir(cnt+1, coresize);
			
		}
		
	}
	

	//전선 설치 완료 true
	//전선 설치 실패 false
	public static boolean setline(int x, int y, ArrayList<Integer> dirlist, int[][] trymap) {
		
		for(int dir: dirlist) {
			switch(dir) {
			case 0:
				for (int i = x-1; i >= 0; i--) {
					if(trymap[i][y]==1 || trymap[i][y]==2) {
						//설치했던 전선 철거
						for (int j = x-1; j >= i-1; j--) {
							trymap[j][y]=0;
						}
						return false;
					}
					//전선 정상 설치
					trymap[i][y]=2;
				}
				break;
			case 1:
				for (int i = x+1; i < N; i++) {
					if(trymap[i][y]==1 || trymap[i][y]==2) {
						//설치했던 전선 철거
						for (int j = x+1; j <= i-1; j++) {
							trymap[j][y]=0;
						}
						return false;
					}
					//전선 정상 설치
					trymap[i][y]=2;
				}
				break;
			case 2:
				for (int j = y-1; j >= 0; j--) {
					if(trymap[x][j]==1 || trymap[x][j]==2) {
						//설치했던 전선 철거
						for (int i = y-1; i >= j-1; j--) {
							trymap[x][i]=0;
						}
						return false;
					}
					//전선 정상 설치
					trymap[x][j]=2;
				}
				break;
			case 3:
				for (int j = y+1; j < N; j++) {
					if(trymap[x][j]==1 || trymap[x][j]==2) {
						//설치했던 전선 철거
						for (int i = y+1; i <= j-1; j++) {
							trymap[x][i]=0;
						}
						return false;
					}
					//전선 정상 설치
					trymap[x][j]=2;
				}
				break;
			}
		}
		return true;
	}
	
	
	//상하좌우 순
	public static ArrayList<Integer> direction(int x, int y) {
		//갈 수 있는 방향 담겨있는 리스트
		ArrayList<Integer> list = new ArrayList<>();
		//상
		boolean flag = false;
		for (int i = x-1; i >= 0; i--) {
			//이동 위치가 코어거나 전선이면  flag=true;
			if(setmap[i][y]==1 || setmap[i][y]==2) {
				flag=true;
				break;
			}
		}
		if(!flag) list.add(0);
		
		//하
		flag = false;
		for (int i = x+1; i < N; i++) {
			//이동 위치가 코어거나 전선이면  flag=true;
			if(setmap[i][y]==1 || setmap[i][y]==2) {
				flag=true;
				break;
			}
		}
		if(!flag) list.add(1);
		
		//좌
		flag = false;
		for (int j = y-1; j >= 0; j--) {
			//이동 위치가 코어거나 전선이면  flag=true;
			if(setmap[x][j]==1 || setmap[x][j]==2) {
				flag=true;
				break;
			}
		}
		if(!flag) list.add(2);
		
		//우
		flag = false;
		for (int j = y+1; j < N; j++) {
			//이동 위치가 코어거나 전선이면  flag=true;
			if(setmap[x][j]==1 || setmap[x][j]==2) {
				flag=true;
				break;
			}
		}
		if(!flag) list.add(3);
		
		//코어마다 가능한 방향 담겨 있음
		return list;
	}
}
