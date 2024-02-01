package SW역량테스트.ps230906;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 12:46
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN크기의 공간에 물고기 M마리와 상어 1마리
 * 한칸에는 물고기가 최대 1마리 존재
 * 아기 상어 크기:2, 1초에 상하좌우로 인접한 한칸씩 이동
 * 아기 상어는 자신의 크기보다 큰 물고기가 있는 칸을 지나갈 수 없고
 * 나머지 칸은 모두 지나갈 수 잇음
 * 
 * 자신의 크기보다 작은 물고기만 먹을 수 있음
 * => 크기가 같은 물고기는 먹을 수 없음, 그러나 지나갈 순 있음
 * 
 * 이동 규칙
 * - 더 이상 먹을 수 있는 물고기가 공간에 없으면 엄마 상어에게 도움 요청
 * - 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러감
 * - 먹을 수 있는 물고기가 1마리보다 많으면, 거리가 가장 가까운 물고기 먹으러감
 * 		거리는 아기상어가 있는 칸에서 물고기가 있는칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값
 * 		거리가 가까운 물고기가 많으면 -> 가장 위에 있는 물고기 우선 -> 여러마리면 가장 왼쪽에 있는 물고기
 * 
 * 아기 상어 이동 = 1초
 * 아기 상어가 먹을 수 있는 물고기가 있는카능로 이동했으면, 이동+물고기먹기 -> 그 칸은 빈칸
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때마다 크기가 1 증가
 * 
 * 예) 크기가 2인 아기상어 -> 물고기 2마리 먹으면 크기3
 * 
 * 아기 상어가 몇 초동안 엄마 상어에게 도움을 요청하지 않고 물고기 잡아 먹을 수 있는지
 * 
 * 입력
 * 첫째줄: 공간의 크기 N
 * N개줄: 공간의 상태 -0 1 2 3 4 5 6 9
 * 		0-빈칸
 * 		1,2,3,4,5,6-물고기 크기
 * 		9-아기 상어의 위치
 * 
 * 출력
 * 아기 상어가 <몇 초동안> 엄마 상어에게 도움을 요청하지 않고 물고기 잡아 먹을 수 있는지
 * 
 * 
 * 문제해결 프로세스
 * 이동가능 - 같은 물고기, 작은 물고기 , 빈칸
 * 이동불가 - 큰 물고기
 * 
 * 먹기가능 -  작은 물고기
 * 먹기불가 - 큰 물고기, 같은 물고기
 * 
 * 이동규칙
 * - 더이상 먹을 수 있는 물고기X -> 종료
 * - 먹을 수 있는 물고기 1마리 -> 그 물고기 먹음
 * - 먹을 수 있는 물고기 2마리이상 -> 거리가 가까운 물고기 먹음
 * 							-> 거리같은경우 여러마리 => 가장 위 => 가장 왼쪽
 * 
 * 0. 아기상어와 작은물고기(먹기가능) 최단 거리=bfs
 * 	이동불가-큰 물고기 있는 칸
 * 1. 먹을 수 있는 작은 물고기 bfs() return distance, eatlist 넣음-> 거리 가까운 물고기 먹음
 * 	1-1. 거리 같은 물고기 여러개 -> 정렬규칙: distance작은 순-행값 작은 순-열값 작은 순
 * 			물고기 1마리만 가능 -> 먹음
 * 	1-2. 가장 첫번째 물고기 먹음 + time+=distance;
 *  	먹은칸 = 빈칸 만들기
 * 2. 먹을 수 있는 작은 물고기 없음 -> 종료
 * 3. 먹은 물고기수 == 아기상어 크기
 * 		아기상어 크기++
 * 		먹은 물고기수 초기화.
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


public class Main_BJ_16236_아기상어 {
	static class Fish implements Comparable<Fish>{
		int x,y,size;
		public Fish(int x, int y, int size) {
			super();
			this.x = x;
			this.y = y;
			this.size = size;
		}
		@Override
		public int compareTo(Fish o) {
			return this.size-o.size;
		}
	}
	
	static class eatList implements Comparable<eatList>{
		int distance;
		Fish fish;
		public eatList(int distance, Fish fish) {
			super();
			this.distance = distance;
			this.fish = fish;
		}
		@Override
		public int compareTo(eatList o) {
			if(this.distance == o.distance) {
				if(this.fish.x==o.fish.x) {
					return this.fish.y-o.fish.y;
				}
				return this.fish.x-o.fish.x;
			}
			return this.distance - o.distance;
		}

	}
	static int N, map[][];
	//초기 아기상어 크기
	static int sharksize=2, sharkx,sharky, eatcnt;
	static int time;
	static ArrayList<Fish> fishes = new ArrayList<>();
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		StringTokenizer st ;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]!=0 && map[i][j]!=9) {
					fishes.add(new Fish(i,j,map[i][j]));
				}
				if(map[i][j]==9) {
					sharkx = i;
					sharky = j;
					map[i][j]=0;
				}
			}
		}
		Collections.sort(fishes);
		move();
		System.out.println(time);
	}
	/* 문제해결 프로세스
	 * 이동가능 - 같은 물고기, 작은 물고기 , 빈칸
	 * 이동불가 - 큰 물고기
	 * 
	 * 먹기가능 -  작은 물고기
	 * 먹기불가 - 큰 물고기, 같은 물고기
	 * 
	 * 이동규칙
	 * - 더이상 먹을 수 있는 물고기X -> 종료
	 * - 먹을 수 있는 물고기 1마리 -> 그 물고기 먹음
	 * - 먹을 수 있는 물고기 2마리이상 -> 거리가 가까운 물고기 먹음
	 * 							-> 거리같은경우 여러마리 => 가장 위 => 가장 왼쪽
	 * 
	 * 0. 아기상어와 작은물고기(먹기가능) 최단 거리=bfs
	 * 	이동불가-큰 물고기 있는 칸
	 * 1. 먹을 수 있는 작은 물고기 bfs() return distance, eatlist 넣음-> 거리 가까운 물고기 먹음
	 * 	1-1. 거리 같은 물고기 여러개 -> 정렬규칙: distance작은 순-행값 작은 순-열값 작은 순
	 * 			물고기 1마리만 가능 -> 먹음
	 * 	1-2. 가장 첫번째 물고기 먹음 + time+=distance;
	 *  	먹은칸 = 빈칸 만들기
	 * 2. 먹을 수 있는 작은 물고기 없음 -> 종료
	 * 3. 먹은 물고기수 == 아기상어 크기
	 * 		아기상어 크기++
	 * 		먹은 물고기수 초기화.
	 */
	
	public static void move() {
		ArrayList<eatList> eatlist = new ArrayList<>();
		//크기 작은 물고기 넣기
		for(Fish f : fishes) {
			if(f.size<sharksize) {
				eatlist.add(new eatList(0,f));
			}
		}
		
		eatlist = distance(sharkx, sharky, eatlist);
		
		Collections.sort(eatlist);
		for (int i = eatlist.size()-1; i >= 0; i--) {
			if(eatlist.get(i).distance==0) eatlist.remove(i);
		}
		
		if(eatlist.isEmpty()) return;
		
		sharkx = eatlist.get(0).fish.x;
		sharky = eatlist.get(0).fish.y;
		
		map[eatlist.get(0).fish.x][eatlist.get(0).fish.y] = 0;
		time += eatlist.get(0).distance;
		fishes.remove(eatlist.get(0).fish);
		eatcnt++;
		
		if(eatcnt==sharksize) {
			sharksize++;
			eatcnt=0;
		}
		move();
	}
	
	static class Pair{
		int x,y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	//distance
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	
	public static ArrayList<eatList> distance(int sx, int sy, ArrayList<eatList> el) {
		int [][] movemap = new int [N][N];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(sx,sy));
		movemap[sx][sy]=1;
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
				if(map[nx][ny]>sharksize) continue;
				
				else if(movemap[nx][ny]==0){
					movemap[nx][ny] = movemap[p.x][p.y]+1;
					q.offer(new Pair(nx, ny));
				}
			}
		}
		for(eatList f: el) {
			if(movemap[f.fish.x][f.fish.y]!=0) {
				f.distance = movemap[f.fish.x][f.fish.y]-1;
			}
		}
		return el;
	}
}
