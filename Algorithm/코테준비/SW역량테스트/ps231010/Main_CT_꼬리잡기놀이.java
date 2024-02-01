package SW역량테스트.ps231010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작: 22:34
 * 종료: 
 * 
 * 문제해석
 * nxn격자에서 꼬리잡기 놀이
 * <진행>
 * 3명 이상이 한팀
 * 모든 사람은 자신의 앞사람이랑 이어져서 움직임, 맨앞:머리사람/맨뒤:꼬리사람
 * 각 팀은 게임에서 주어진 이동 선을 따라서만 이동
 * 각 팀의 이동선은 끝이 이어져있음, 이동선끼리는 겹치지 않음
 * <초기 조건>
 * 라운드별 진행
 * 1. 각팀은 머리 사람을 따라 한칸 이동
 * 2. 각 라운드마다 공이 정해진 선을 따라 던져짐
 * 	(좌)우:1~n/(하)상:n+1~2n/(우)좌:2n+1~3n/(상)하:3n+1~4n 방향
 * 	다시 4n+1~5n    5n+1~6n ...
 * 3. 공이 던져지는 경우 해당 선에 사람이 있으면, 최초로 만나는 사람만 공을 얻어 점수 받음
 * 	점수는 해당 사람이 머리사람을 시작으로 팀 내에서 k번째 사람이면 k^2만큼 점수 얻음
 * 	아무도 공으 받지못하면 아무 점수도 x
 * 	공을 획득한 팀은 머리사람과 꼬리사람이 바뀜, 방향전환
 * 4. 1~3번
 * 
 * 
 * 입력
 * 첫째줄: n격자크기,m팀개수,라운드수k
 * n개줄: 격자정보
 * 
 * 출력
 * k라운드로 얻는 점수의 총합
 * 
 * 문제해결프로세스
 * 0. 팀의 지도 4,5,6..으로 숫자 변경(bfs) + 팀 개수 저장
 * 1. 팀의 머리 wherehead int[4~팀개수+4]가 head=1인지 tail=3인지 저장
 * 2. 머리 사람을 따라 팀원들 한칸씩 이동
 * 	2-1. 4팀부터 머리찾아서 이동
 * 		머리 앞 위치 = 4방탐색해서, 값이 팀번호와 같고 팀원이없는 (i,j)
 * 		다음 위치값을 이전 위치에 저장, 꼬리만나면 종료
 * 3. 공던지기
 * 	(round-1)/n = 방향 ->  방향이 4넘으면 %4
 * 	(round-1)%n = 행 또는 열 위치
 * 			방향 0-> 행 round%n
 * 				1-> 열 round%n
 * 				2-> 행 (n-1)-round%n
 * 				3-> 열 (n-1)-round%n
 * 4. 처음 공맞은사람 찾기 + 팀번호 -> wherehead [] 머리 교체
 * 		result+=점수 더하기
 * 5. 1~4번 k라운드 반복
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 */

public class Main_CT_꼬리잡기놀이 {
	static int n,m,k,teammap[][],map[][],result;
	static boolean isvisited[][];
	static int wherehead[];
	//우상좌하 순
	static int dx[] = {0,-1,0,1};
	static int dy[] = {1,0,-1,0};
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
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		teammap = new int[n][n];
		wherehead = new int[4+m];
		//초기 머리사람 저장
		for (int i = 4; i < 4+m; i++) {
			wherehead[i] = 1;
		}
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		isvisited = new boolean[n][n];
		maketeam();
		
		for (int i = 0; i < k; i++) {
			moveteam();
			throwball(i);
		}
		System.out.println(result);
	}
	/* 문제해결프로세스
	 * 0. 팀의 지도 4,5,6..으로 숫자 변경(bfs)
	 * 1. 팀의 머리 wherehead int[4~팀개수m+4]가 head=1인지 tail=3인지 저장
	 * 2. 머리 사람을 따라 팀원들 한칸씩 이동
	 * 	2-1. 4팀부터 머리찾아서 이동
	 * 		머리 앞 위치 = 4방탐색해서, 값이 팀번호와 같고 팀원이없는 (i,j)
	 * 		다음 위치값을 이전 위치에 저장, 꼬리만나면 종료
	 * 3. 공던지기
	 * 	(round-1)/n = 방향 ->  방향이 4넘으면 %4
	 * 	(round-1)%n = 행 또는 열 위치
	 * 			방향 우 0-> 행 round%n
	 * 				상 1-> 열 round%n
	 * 				좌 2-> 행 (n-1)-round%n
	 * 				하 3-> 열 (n-1)-round%n
	 * 4. 처음 공맞은사람 찾기 + 팀번호 -> wherehead [] 머리 교체
	 * 		result+=점수 더하기
	 * 5. 2~4번 k라운드 반복
	 */
	public static void throwball(int round) {
		int dir = round/n;
		if(dir>=4) dir%=4;
		int location = round%n;
		
		switch(dir) {
		case 0:
			for (int j = 0; j < n; j++) {
				if(map[location][j]!=0 && map[location][j]!=4) {
					int value = findorder(location,j);
					result+=value*value;
					changehead(teammap[location][j]);
					break;
				}
			}
			break;
		case 1:
			for (int i = n-1; i >= 0; i--) {
				if(map[i][location]!=0 && map[i][location]!=4) {
					int value = findorder(i,location);
					result+=value*value;
					changehead(teammap[i][location]);
					break;
				}
			}
			break;
		case 2:
			location = n-1-location;
			for (int j = n-1; j >= 0; j--) {
				if(map[location][j]!=0 && map[location][j]!=4) {
					int value = findorder(location,j);
					result+=value*value;
					changehead(teammap[location][j]);
					break;
				}
			}
			break;
		case 3:
			location = n-1-location;
			for (int i = 0; i < n; i++) {
				if(map[i][location]!=0 && map[i][location]!=4) {
					int value = findorder(i,location);
					result+=value*value;
					changehead(teammap[i][location]);
					break;
				}
			}
			break;
		}
	}
	public static void changehead(int tnum) {
		if(wherehead[tnum]==1) wherehead[tnum]=3;
		else wherehead[tnum]=1;
	}
	public static int findorder(int row, int col) {
		isvisited = new boolean[n][n];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(row, col));
		isvisited[row][col]=true;
		
		int teamhead = wherehead[teammap[row][col]];
		int teamtail = 0;
		if(teamhead==1) teamtail=3;
		else teamtail=1;
		
		boolean allbody = allbody(row,col);
		int depth = 1;
		while(!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				//머리발견
				if(map[x][y]==wherehead[teammap[x][y]]) {
					return depth;
				}
				for (int k = 0; k < 4; k++) {
					int nx = x+dx[k];
					int ny = y+dy[k];
					
					if(!rangecheck(nx,ny) || isvisited[nx][ny] || map[nx][ny]==0 || map[nx][ny]==4) {
						continue;
					}
					if(allbody && map[x][y]==teamtail && map[nx][ny]==teamhead) {
						continue;
					}
					q.offer(new Pair(nx,ny));
					isvisited[nx][ny] = true;
				}
			}
			depth++;
		}
		return depth;
	}
	
	public static void moveteam() {
		isvisited = new boolean[n][n];
		for (int t = 4; t < 4+m; t++) {
			int teamhead = wherehead[t];
			int teamtail = 0;
			if(teamhead==1) teamtail=3;
			else teamtail=1;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					//head 위치 찾기
					if(map[i][j]==teamhead && teammap[i][j]==t && !isvisited[i][j]) {
						Queue<Pair> q = new ArrayDeque<>();
						q.offer(new Pair(i,j));
						isvisited[i][j]=true;
						int sx = -1;
						int sy = -1;
						boolean allbody = allbody(i,j);
						if(allbody) {
							int x = i;
							int y = j;
							for (int k = 0; k < 4; k++) {
								int nx = x+dx[k];
								int ny = y+dy[k];
								if(!rangecheck(nx,ny)) {
									continue;
								}
								if(map[nx][ny]==teamtail) {
									map[x][y] = 2;
									map[nx][ny] = teamhead;
									isvisited[x][y] = true;
									x = nx;
									y = ny;
									break;
								}
							}
							for (int k = 0; k < 4; k++) {
								int nx = x+dx[k];
								int ny = y+dy[k];
								if(!rangecheck(nx,ny)) {
									continue;
								}
								if(map[nx][ny]==2 && !isvisited[nx][ny]) {
									map[nx][ny] = teamtail;
									isvisited[x][y] = true;
									break;
								}
							}
						}
						else {
							while(!q.isEmpty()) {
								Pair p = q.poll();
								int x = p.x;
								int y = p.y;
								for (int k = 0; k < 4; k++) {
									int nx = x+dx[k];
									int ny = y+dy[k];
									
									if(!rangecheck(nx,ny) || isvisited[nx][ny] || map[nx][ny]==0 ) {
										continue;
									}
									if(map[nx][ny]==4 && p.x==i && p.y==j) {
										sx = nx;
										sy = ny;
										isvisited[sx][sy] = true;
									}
									if(map[nx][ny]==4) continue;
									//4도아니고, 방문하지도않은곳 = 또다른몸통
									q.offer(new Pair(nx,ny));
									isvisited[nx][ny] = true;
									map[x][y] = map[nx][ny];
									map[nx][ny] = 4;
								}
							}
							map[sx][sy] = teamhead;
						}
					}
						
				}
			}
		}
	}
	public static void maketeam() {
		int teamnum = 4;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(map[i][j]!=0 && !isvisited[i][j]) {
					Queue<Pair> q = new ArrayDeque<>();
					q.offer(new Pair(i,j));
					isvisited[i][j]=true;
					teammap[i][j] = teamnum;
					while(!q.isEmpty()) {
						Pair p = q.poll();
						int x = p.x;
						int y = p.y;
						for (int k = 0; k < 4; k++) {
							int nx = x+dx[k];
							int ny = y+dy[k];
							if(!rangecheck(nx,ny) || isvisited[nx][ny] || map[nx][ny]==0) {
								continue;
							}
							q.offer(new Pair(nx,ny));
							isvisited[nx][ny] = true;
							teammap[nx][ny] = teamnum;
						}
					}
					teamnum++;
				}
			}
		}
	}
	public static boolean allbody(int bx,int by) {
		boolean allbody = true;
		for (int k = 0; k < 4; k++) {
			if(rangecheck(bx+dx[k],by+dy[k]) && map[bx+dx[k]][by+dy[k]]==4) allbody=false;
		}
		return allbody;
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<n && ry>=0 && ry<n;
	}
}
