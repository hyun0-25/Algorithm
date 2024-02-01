package SW역량테스트.ps231014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 시작: 8:20
 * 종료: 11:20
 * 
 * 문제해석
 * M명의 참가자가 미로 탈출
 * 미로의 구성
 * 1. NxN크기, (1,1)좌상단
 * 2. 미로는 3가지 상태중 하나
 * 	2-1. 빈칸->이동가능한 칸
 * 	2-2. 벽->이동불가능한 칸
 * 		1~9의 내구도, 회전하면 내구도--, 내구도 0되면 빈칸
 * 3. 출구->참가자가 도달하면 즉시 탈출
 * 
 * 1초마다 모든 참가자가 한칸씩 이동
 * 두 위치의 최단거리는 맨해튼거리
 * 모든 참가자는 동시에 이동
 * 상하좌우, 벽이 없는 곳으로 이동
 * 움직인 칸은 현재 머물러있는 칸보다 출구까지의 최단거리가 가까워야함
 * 움직일 수 있는 칸이 2개이상이면, 상하로 이동하는것 우선
 * 참가자가 움직일 수 없으면, 이동X
 * 한칸에 2명이상 참가자가 있을 수 있음
 * => 모든 참가자가 이동 끝나면, 미로 회전
 * - 1명 이상의 참가자와 출구를 포함한 가장 작은 정사각형
 * - 가장 작은 크기를 갖는 정사각형이 2개 이상이면, 좌상단 행값이 작은것 -> 그래도같으면 열값이 작은것
 * - 선택된 정사각형은 시계방향으로 90도 회전, 회전된 벽은 내구도가 1씩 감소
 * K번 반복, 그전에 모든 참가자가 탈출하면 게임 종료
 * 게임 종료시 모든 참가자들의 이동거리 합과 출구 좌표를 출력
 * 
 * 입력
 * 첫째줄: N미로크기, M참가자수, K반복횟수
 * N개줄: 미로정보
 * 0=빈칸, 1~9벽내구도
 * M개줄: CKARKWKDML WHKVY
 * 마지막줄: 출구 좌표
 * 
 * 출력
 * K초 또는 모든 참가자가 탈출시
 * 모든 참가자들의 이동거리 합과 출구 좌표 출력
 * 
 * 
 * 문제해결프로세스
 * 1. 이동
 * 	1-1. 출구와 사용자의 위치 행값 비교
 * 		행값 같지 않으면 -> 상 또는 하로 이동할 수 있는지
 * 		행값 같으면 열값 비교-> 좌 또는 우로 이동할 수 있는지
 * 	1-2. 이동 위치가 출구면 탈출
 * 2. 미로회전
 * 	2-1. 출구와 참가자list 돌면서
 * 		미로한변(val) = 출구와 참가자 행 또는 열값 차이의 최대
 * 		미로모양 -> 행값차이와 열값차이가 같으면 => 좌상단 우하단임
 * 					행값차이=val이면 => 출구 또는 참가자 중 (열값이 더 큰거-val)가 범위안쪽이면 열임
 * 					열값차이=val이면 => 출구 또는 참가자 중 (행값이 더 큰거-val)가 범위안쪽이면 행임
 * 		-> 좌상단 (i,j)와 val 저장
 * 		val보다 크면 continue;
 * 		val과 같으면 좌상단 비교
 * 		val보다 작으면 val+좌상단 갱신
 * 	2-2. val크기의 좌상단 기준 90도 회전
 * 		참가자list, 출구, map
 *		 회전할때 map[][]이 0보다크면 --값 대입
 * 3. k번반복
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_CT_메이즈러너 {
	static int N,M,K,map[][], exitx,exity, movecnt;
	static List<Pair> people = new ArrayList<>();
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Pair [x=" + x + ", y=" + y + "]";
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			people.add(new Pair(x,y));
		}
		st = new StringTokenizer(br.readLine());
		exitx = Integer.parseInt(st.nextToken())-1;
		exity = Integer.parseInt(st.nextToken())-1;
		
		for (int i = 0; i < K; i++) {
			//이동
			move();
			//참가자 모두 탈출했는지
			if(people.size()==0) break;
			//미로회전
			rotate();
			
		}
		System.out.println(movecnt);
		System.out.println((exitx+1)+ " "+(exity+1));
	}
	/* 문제해결프로세스
	 * 1. 이동
	 * 	1-1. 출구와 사용자의 위치 행값 비교
	 * 		행값 같지 않으면 -> 상 또는 하로 이동할 수 있는지
	 * 		행값 같으면 열값 비교-> 좌 또는 우로 이동할 수 있는지
	 * 	1-2. 이동 위치가 출구면 탈출
	 * 2. 미로회전
	 * 	2-1. 출구와 참가자list 돌면서
	 * 		미로한변(val) = 출구와 참가자 행 또는 열값 차이의 최대
	 * 		미로모양 -> 행값차이와 열값차이가 같으면 => 좌상단 우하단임
	 * 					행값차이=val이면 => 출구 또는 참가자 중 (열값이 더 큰거-val)가 범위안쪽이면 열임
	 * 					열값차이=val이면 => 출구 또는 참가자 중 (행값이 더 큰거-val)가 범위안쪽이면 행임
	 * 		-> 좌상단 (i,j)와 val 저장
	 * 		val보다 크면 continue;
	 * 		val과 같으면 좌상단 비교
	 * 		val보다 작으면 val+좌상단 갱신
	 * 	2-2. val크기의 좌상단 기준 90도 회전
	 * 		참가자list, 출구, map
	 *		 회전할때 map[][]이 0보다크면 --값 대입
	 * 3. k번반복
	 */
	private static Pair lefttop(int xx, int yy, int rsize, int csize, int square) {
		int row = xx-exitx;
		int col = yy-exity;
		if(rsize==csize) {
			return new Pair(Math.min(xx, exitx), Math.min(yy, exity));
		}
		else if(rsize>csize) {
			if(col<0) {
				int ny = exity-square;
				while(true) {
					if(ny<0) ny++;
					else break;
				}
				return new Pair(Math.min(xx, exitx), ny);
				
			}
			else {
				int ny = yy-square;
				while(true) {
					if(ny<0) ny++;
					else break;
				}
				return new Pair(Math.min(xx, exitx), ny);
			}
		}
		else {
			if(row<0) {
				int nx = exitx-square;
				while(true) {
					if(nx<0) nx++;
					else break;
				}
				return new Pair(nx, Math.min(yy, exity));
			}
			else {
				int nx = xx-square;
				while(true) {
					if(nx<0) nx++;
					else break;
				}
				return new Pair(nx, Math.min(yy, exity));
			}
		}
	}
	private static void rotate() {
		int lx = 10;
		int ly = 10;
		int val = 11;
//		System.out.println("h"+people.size());
		for (int i = 0; i < people.size(); i++) {
			int x = people.get(i).x;
			int y = people.get(i).y;
			int row = x-exitx;
			int col = y-exity;
			int rowsize = Math.abs(row);
			int colsize = Math.abs(col);
//			System.out.println(Math.max(rowsize, colsize));
			if(Math.max(rowsize, colsize) < val) {
				//좌상단 구하러가기
				//무조건 좌상단 업데이트
				Pair p = lefttop(x, y, rowsize, colsize, Math.max(rowsize, colsize));
				lx = p.x;
				ly = p.y;
				val = Math.max(rowsize, colsize);
			}
			else if(Math.max(rowsize, colsize) == val) {
				//좌상단 구하러가기
				//좌상단 행,열 비교
				Pair p = lefttop(x, y, rowsize, colsize,Math.max(rowsize, colsize));
				if(p.x<lx) {
					lx = p.x;
					ly = p.y;
				}
				else if(p.x==lx) {
					if(p.y<ly) {
						lx = p.x;
						ly = p.y;
					}
				}
			}
		}
//		System.out.println("좌상단 "+lx+" "+ly+" 크기 "+val);
		rightrotate(lx, ly, val);
	}
	/* 	2-2. val크기의 좌상단 기준 90도 회전
	 * 		참가자list, 출구, map
	 *		 회전할때 map[][]이 0보다크면 --값 대입
	 */
	private static void rightrotate(int leftx, int lefty, int square) {
		int copyrotate[][] = new int[square+1][square+1];
		boolean ismove[] = new boolean[people.size()];
		boolean exitmove = false;
//		System.out.println("--사람들이전--");
//		for(Pair p: people)
//			System.out.println(p);
//		System.out.println();
		for (int i = 0; i < square+1; i++) {
			for (int j = 0; j < square+1; j++) {
				int mx = leftx+square-j;
				int my = lefty+i;
				copyrotate[i][j] = map[mx][my];
				if(!exitmove && mx==exitx && my==exity) {
					exitx = i+leftx;
					exity = j+lefty;
					exitmove = true;
//					System.out.println("탈출구 이동 "+exitx + " "+exity);
				}
				else {
					for (int k = 0; k < people.size(); k++) {
						int x = people.get(k).x;
						int y = people.get(k).y;
						if(!ismove[k] && x==mx && y==my) {
//							System.out.println("사람이동전 "+people.get(k).x+" "+people.get(k).y);
							people.get(k).x = i+leftx;
							people.get(k).y = j+lefty;
							ismove[k] = true;
//							System.out.println("사람이동 "+people.get(k).x+" "+people.get(k).y);
						}
					}
				}
			}
		}
//		System.out.println("--사람들위치--");
//		for(Pair p: people)
//			System.out.println(p);
//		System.out.println();
//		System.out.println("--탈출구 위치--");
//		System.out.println(exitx+" "+exity);
//		System.out.println();
		
		for (int i = 0; i < square+1; i++) {
			for (int j = 0; j < square+1; j++) {
				if(copyrotate[i][j]>0) copyrotate[i][j]--;
				map[i+leftx][j+lefty] = copyrotate[i][j];
			}
		}
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
//		System.out.println();
	}
	private static void move() {
		int size = people.size()-1;
		for (int i = size; i >= 0 ; i--) {
			int x = people.get(i).x;
			int y = people.get(i).y;
			int row = x-exitx;
			int col = y-exity;
			if(row!=0 && col!=0) {
				if(row>0) {
					//이동가능
					if(map[x-1][y] == 0) {
						people.get(i).x = x-1;
						movecnt++;
					}
					else {
						if(col>0) {
							if(map[x][y-1] == 0) {
								people.get(i).y = y-1;
								movecnt++;
							}
						}
						else if(col<0) {
							if(map[x][y+1] == 0) {
								people.get(i).y = y+1;
								movecnt++;
							}
						}
					}
				}
				else if(row<0) {
					//이동가능
					if(map[x+1][y] == 0) {
						people.get(i).x = x+1;
						movecnt++;
					}
					else {
						if(col>0) {
							if(map[x][y-1] == 0) {
								people.get(i).y = y-1;
								movecnt++;
							}
						}
						else if(col<0) {
							if(map[x][y+1] == 0) {
								people.get(i).y = y+1;
								movecnt++;
							}
						}
					}
				}
			}
			else if(row==0) {
				if(col>0) {
					if(map[x][y-1] == 0) {
						people.get(i).y = y-1;
						movecnt++;
					}
				}
				else if(col<0) {
					if(map[x][y+1] == 0) {
						people.get(i).y = y+1;
						movecnt++;
					}
				}
			}
			else if(col==0) {
				if(row>0) {
					//이동가능
					if(map[x-1][y] == 0) {
						people.get(i).x = x-1;
						movecnt++;
					}
				}
				else if(row<0) {
					//이동가능
					if(map[x+1][y] == 0) {
						people.get(i).x = x+1;
						movecnt++;
					}
				}
			}
//		System.out.println(people.get(i).x +" "+people.get(i).y);
			if(people.get(i).x==exitx && people.get(i).y==exity) {
				people.remove(i);
			}
		}
	}
	
	private static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}
