package SW역량테스트.ps231009;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 시작: 20:20
 * 종료: 
 * 
 * 문제해석
 * nxn 격자에 나무의 그루수와 벽의 정보
 * 제초제의 경우 k의 범위만큼 대각선으로 퍼지며, 벽이 있는 경우 가로막혀서 전파x
 * 
 * 나무의 성장과 억제
 * 1. 인접한 4개 칸 중 나무가 있는 칸의 수만큼 나무가 성장, 성장은 모든 나무에게 동시에 일어남
 * 2. 기존에 있던 나무들은 인접한 4개의 칸 중 벽,다른나무,제초제 모두 없는 칸에 변식
 * 		각 칸의 나무 그루수에서 총번식이 가능한 칸의 개수 만큼 나누어진 그루 수만큼 번식,
 * 		나눌때 생기는 나머지는 버림, 번식은 동시에 일어남
 * 3. 각 칸 중 제초제를 뿌렸을 때, 나무가 가장 많이 박멸되는 칸에 제초제 뿌림
 * 		나무가 없는 칸에 제초제를 뿌리면, 박멸되는 나무x
 * 		나무가 있는 칸에 제초제 뿌리면, 4개의 대각선 방향으로 k칸만큼 전파
 * 				도중 벽이거나 나무가 아예없으면 그 칸까지는 제초제o/그 이후칸은 제초제 전파x
 * 		제초제가 뿌려진 칸에는 c년만큼 제초제가 남아있다가 c+1년에 사라짐
 * 		제초제가 뿌려진 곳에 다시 제초제 뿌려지면 서로 뿌려진 해로부터 다시 c년동안 제초제o
 * 	박멸시키는 나무의 수가 동일한 칸이 있으면, 행이 작은 순서대로, 행도 같으면 열이 작은 칸에 제초제뿌림
 * 1,2,3번이 1년에 걸쳐 진행 -> m년 동안 총 박멸한 나무의 그루 수
 * 
 * 
 * 입력
 * 첫째줄: 격자크기n, 박멸년수m, 확산범위k, 제초제지속년수c
 * n개줄: 격자 상태
 * -1=벽, 0=빈칸
 * 
 * 출력
 * m년 동안 총 박멸한 나무의 그루 수
 * 
 * 문제해결프로세스
 * 1. 나무 성장 -> 각 나무 (i,j)돌면서 인접한 4방 나무수만큼 ++
 * 2. 나무 번식 -> 각 나무 (i,j)돌면서 인접한 4방중 빈칸인곳 개수로 나누기 -> 분배
 * 		isnothing=true면 map[i][j]에 더하기
 * 		박멸위치인지도 확인
 * 3. 제초제 -> 가장 많이 박멸되는 나무 구하기
 * 		Tree(x,y,val): t
 * 		t.val보다 크면 -> 갱신
 * 		t.val과 같으면, t.x비교
 * 					t.x도 같으면 t.y비교후 갱신
 * 	3-1. result에 t.val더하기
 * 	3-2. 기존 박멸남은 년수 있으면 모두 -1
 * 	3-3. t의 대각선 k만큼씩 박멸나무 년수 대입
 * 4. 1,2,3 m년 반복
 * 
 * 
 * 제한사항
 * 
 * 
 * 시간복잡도
 * 
 */


public class Main_CT_나무박멸 {
	static int n,m,k,c,map[][],spray[][],growing[][];
	static long result;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int ddx[] = {-1,1,-1,1};
	static int ddy[] = {1,-1,-1,1};
	static class Tree{
		int x,y,val;
		public Tree(int x, int y, int val) {
			super();
			this.x = x;
			this.y = y;
			this.val = val;
		}
		@Override
		public String toString() {
			return "Tree [x=" + x + ", y=" + y + ", val=" + val + "]";
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		spray = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < m; i++) {
			treegrow();
			treecopy();
			treespray();
		}
		
		
		System.out.println(result);
		
		
	}
	/* 문제해결프로세스
	 * 1. 나무 성장 -> 각 나무 (i,j)돌면서 인접한 4방 나무수만큼 ++
	 * 2. 나무 번식 -> 각 나무 (i,j)돌면서 인접한 4방중 빈칸인곳 개수로 나누기 -> 분배
	 * 		growing[][]!=0면 map[i][j]에 더하기
	 * 		박멸위치인지도 확인
	 * 3. 제초제 -> 가장 많이 박멸되는 나무 구하기
	 * 		Tree(x,y,val): t
	 * 		t.val보다 크면 -> 갱신
	 * 		t.val과 같으면, t.x비교
	 * 					t.x도 같으면 t.y비교후 갱신
	 * 	3-1. result에 t.val더하기
	 * 	3-2. 기존 박멸남은 년수 있으면 모두 -1
	 * 	3-3. t의 대각선 k만큼씩 박멸나무 년수 대입
	 * 4. 1,2,3 m년 반복
	 */
	public static void treegrow() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(map[i][j]>0) {
					int cnt = 0;
					for (int p = 0; p < 4; p++) {
						int nx = i+dx[p];
						int ny = j+dy[p];
						if(!rangecheck(nx,ny) || map[nx][ny]<=0 || spray[nx][ny]>0) continue;
						//else
						cnt++;
					}
					map[i][j]+=cnt;
				}
			}
		}
	}
	public static void treecopy() {
		growing = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(map[i][j]>0) {
					//빈칸 개수 세기
					int cnt = 0;
					for (int p = 0; p < 4; p++) {
						int nx = i+dx[p];
						int ny = j+dy[p];
						if(!rangecheck(nx,ny) || spray[nx][ny]>0) continue;
						//else
						else if(map[nx][ny]==0) cnt++;
					}
					//번식
					for (int p = 0; p < 4; p++) {
						int nx = i+dx[p];
						int ny = j+dy[p];
						if(!rangecheck(nx,ny) || spray[nx][ny]>0) continue;
						//else
						else if(map[nx][ny]==0) growing[nx][ny]+=map[i][j]/cnt;
					}
				}
			}
		}
		//번식나무 옮기기
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(growing[i][j]!=0) {
					map[i][j]=growing[i][j];
					growing[i][j]=0;
				}
			}
		}
	}
	
	public static void treespray() {
		//초기화
		Tree t = new Tree(20,20,0);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(map[i][j]>0) {
					int total=map[i][j];
					for (int p = 0; p < 4; p++) {
						int nx = i;
						int ny = j;
						for (int q = 0; q < k; q++) {
							nx+=ddx[p];
							ny+=ddy[p];
							if(!rangecheck(nx,ny) || map[nx][ny]<=0 || spray[nx][ny]>0) break;
							//else
							total+=map[nx][ny];
						}
					}
					if(total>t.val) {
						t.x = i;
						t.y = j;
						t.val = total;
					}
					else if(total==t.val) {
						if(i<t.x) {
							t.x = i;
							t.y = j;
							t.val = total;
						}
						else if(i==t.x) {
							if(j<t.y) {
								t.x = i;
								t.y = j;
								t.val = total;
							}
						}
					}
				}
			}
		}
		if(t.val==0) return;
		result+=t.val;
		//제초제 뿌리기
		//1. 1년씩 줄이기
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(spray[i][j]>0) spray[i][j]--;
			}
		}
		//2. 새로운 제초제 뿌리기
		for (int p = 0; p < 4; p++) {
			int nx = t.x;
			int ny = t.y;
			map[nx][ny] = 0;
			spray[nx][ny] = c;
			for (int q = 0; q < k; q++) {
				nx+=ddx[p];
				ny+=ddy[p];
				if(!rangecheck(nx,ny) || map[nx][ny]==-1) break;
				else if(map[nx][ny]==0) {
					spray[nx][ny] = c;
					break;
				}
				//else
				map[nx][ny] = 0;
				spray[nx][ny] = c;
			}
		}
		
	}
	
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<n && ry>=0 && ry<n;
	}
}
