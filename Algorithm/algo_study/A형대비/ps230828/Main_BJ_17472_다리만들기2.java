package A형대비.ps230828;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 해석
 * 섬으로 이루어진 나라, 모든 섬을 다리로 연결
 * NxM 크기의 격자, 각 칸은 땅 또는 바다
 * 섬: 연결된 땅이 상하좌우로 붙어있는 덩어리
 * 다리는 바다에만 건설 가능, 다리의 길이는 다리가 격자에서 차지하는 칸의 수
 * 다리를 연결해서 모든 섬을 연결,
 * 다리의 양끝은 섬과 인접한 바다 위에 있어야함, 한 다리의 방향은 중간에 바뀌면 안됨
 * 다리의 길이는 2 이상
 * 다리의 방향은 가로 또는 세로
 * 섬A,B가 중간에 섬 C와 인접한 바다를 지나가는 경우, 섬 C는 A,B와 연결되어 있는 것이 아님
 * 다리가 교차하는 경우
 * - 교차하는 다리의 길이를 계산할 때는 각 다리의 길이에 모두 포함
 * 모든 섬을 연결하는 다리 길이의 최솟값
 * 
 * 입력
 * 첫째줄: N,M
 * N개의줄: 지도의 정보
 * 0은 바다 1은 땅
 * 
 * 출력
 * 모든 섬을 연결하는 다리 길이의 최솟값
 * 모든 섬을 연결하는 것이 불가능하면 -1출력
 * 
 * 문제 해결 프로세스
 * 그래프 같은데..
 * 0. 섬이 존재하면 2,3,4,..순으로 1값 채우기
 * 
 * 1. 섬의 상하,좌우에 연결할 수 있는 섬 조사 -> 다리 길이 측정
 * 	name, 연결된 섬 (이름 ,길이) 리스트
 * 2. 이을 수 있는 섬 완탐
 * 	최솟값 넘으면 가지치기
 * 	2-1. 모든 노드 선택 O -> 최솟값 더한 값 출력
 * 	2-2. 모든 노드 선택 X -> -1 출력
 * 
 * 제한 조건
 * N,M<=10
 * 2<=섬개수<=6
 * 
 * O(5^4
 * 
 * 
 */



public class Main_BJ_17472_다리만들기2 {
	static class info {
		int name;
		int length;
		public info(int name, int length) {
			super();
			this.name = name;
			this.length = length;
		}
	}
	static class island{
		int name,length;
		ArrayList<info> link;
		
		public island(int name, ArrayList<info> link) {
			super();
			this.name = name;
			this.link = link;
		}
	}
	static class Pair{
		int x,y;

		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static int N,M,map[][],alpa;
	static island isLand[];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//0. 섬이 존재하면 A,B,C,..순으로 1값 채우기
		alpa=1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//아직 방문하지 않은 섬
				if(map[i][j]==1) {
					alpa++;
					bfs(alpa,i,j);
				}
			}
		}
		//섬 개수만큼 배열 생성
		//이름 2부터 시작
		isLand = new island[alpa+1];
		for (int i = 2; i < alpa+1; i++) {
			isLand[i] = new island(i, new ArrayList<>());
		}
		
		
		/*1. 섬의 상하,좌우에 연결할 수 있는 섬 조사 -> 다리 길이 측정
		 * 	name, 연결된 섬 (이름 ,길이) 리스트
		 */
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//섬의 좌표 중에서 다른 섬과의 거리 측정
				if(map[i][j]>1) {
					//위치의 섬이름
					int idx_name = map[i][j];
					distance(idx_name, i, j);
				}
			}
		}
		
		
		for (int i = 2; i < alpa+1; i++) {
			System.out.println(i+"번째 섬");
			for (info f : isLand[i].link) {
				System.out.println(f.name+ " 길이 "+f.length);
			}
			
		}
		
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
	}
	public static void distance(int n, int x, int y) {
		for (int i = 0; i < 4; i++) {
			int nx = x+dx[i];
			int ny = y+dy[i];
			//다리 길이
			int count=0;
			while(true) {
				nx+=dx[i];
				ny+=dy[i];
				if(nx<0 || nx>=N || ny<0 || ny>=M) break;
				
				if(map[nx][ny]!=0) {
					break;
				}
				//else - 바다면 계속 연결
				count++;
			}
			//다리 길이가 1 이상이면
			//연결된 섬이름
			
			//같은 섬과 만나면 실패
			if(nx>=0 && nx<N && ny>=0 && ny<M && map[nx][ny]==n) {
				continue;
			}
			//같은섬이 아닌 다른섬이면서 count>1이면
			if(count>2 && nx>=0 && nx<N && ny>=0 && ny<M && map[nx][ny]>1) {
				int link_name = map[nx][ny];
				//이름이 n인 섬과 연결된 섬들
				boolean exist = false;
				for (int j = 0; j < isLand[n].link.size(); j++) {
					//이미 연결된 정보가 있다면
					if(isLand[n].link.get(j).name==link_name) {
						int min_dis = isLand[n].link.get(j).length;
						isLand[n].link.get(j).length = Math.min(min_dis, count-1);
						exist=true;
						System.out.println(x+ " "+y + " 연결섬 "+ link_name +" 길이 "+(count-1));
						break;
					}
				}
				//연결된 적 없다면
				if(!exist) {
					isLand[n].link.add(new info(link_name, count-1));
				}
			}
		}
	}
	
	/* 문제 해결 프로세스
	 * 그래프 같은데..
	 * 0. 섬이 존재하면 A,B,C,..순으로 1값 채우기
	 * 
	 * 1. 섬의 상하좌우에 연결할 수 있는 섬 조사 -> 다리 길이 측정
	 * 	name, 연결된 섬 (이름 ,길이) 리스트
	 * 2. 이을 수 있는 섬 완탐
	 * 	최솟값 넘으면 가지치기
	 * 	2-1. 모든 노드 선택 O -> 최솟값 더한 값 출력
	 * 	2-2. 모든 노드 선택 X -> -1 출력
	 */
	
	
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void bfs(int a, int x, int y) {
		Queue<Pair> q = new ArrayDeque<>();
		//섬 시작
		q.offer(new Pair(x,y));
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			map[p.x][p.y]=a;
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				if(nx<0 || nx>=N || ny<0 || ny>=M) continue;
				//인접한 섬 연결
				if(map[nx][ny]==1) {
					System.out.println("섬이름 "+a+ " 위치 "+nx+" "+ny);
					q.offer(new Pair(nx,ny));
					map[nx][ny]=a;
				}
			}
		}
	}
}
