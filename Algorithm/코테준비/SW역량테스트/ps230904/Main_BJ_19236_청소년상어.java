package SW역량테스트.ps230904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 4x4크기의 공간이 있고, 1x1인 정사각형의 칸으로 나누어져 있음
 * 한칸에는 물고기가 한마리 존재
 * 각 물고기는 번호와 방향을 가지고 있음
 * 번호 : 1~16 물고기는 서로 다른 번호
 * 방향 : 8방 
 * 청소년 상어는 (0,0)에 있는 물고기를 먹고 (0,0)에 들어감
 * 상어의 방향은 (0,0)에 있던 물고기의 방향과 같음, 이후 물고기가 이동
 * 물고기는 번호가 작은 물고기부터 순서대로 이동, 물고기는 한칸을 이동
 * 이동할 수 있는 칸 - 빈칸 또는 다른 물고기가 있는 칸
 * 이동할 수 없는 칸 - 상어가 있거나, 경계 밖
 * 각 물고기는 방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전
 * 이동할 수 있는 칸이 없으면 이동x
 * 그 외의 경우는 그 칸으로 이동
 * 물고기가 다른 물고기가 있는 카느올 이동할 때는 서로의 위치를 바꿈
 *  
 * 물고기의 이동이 모두 끝나면 상어가 이동
 * 상어는 방향에 있는 칸으로 이동
 * 한번에 여러 개의 칸으로 이동 가능
 * 상어가 물고기가 있는 칸으로 이동 - 그 칸의 물고기 먹고, 물고기의 방향 가짐
 * 						이동 중에 지나가는 칸에 있는 물고기는 먹지 않음
 * 		물고기가 없는 칸으로는 이동 불가
 * 상어가 이동할 수 있는 칸이 없으면 공간에서 벗어나 집으로 감
 * 상어가 이동한 후에는 다시 물고기가 이동
 * => 반복 
 * 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 완탐?
 * move - > 물고기 이동
 * 모든 가능한 경로 = 최댓값 갱신
 * 
 * 물고기 이동 <-> 물고기 이동 돌려놓기
 * 1. 상어 (0,0)에 놓기
 * 2. move 물고기 이동
 * 3. 상어의 방향에 이동가능 for문 
 * 		.remove
 * 		move함수
 * 		.add
 * 	기저조건: 상어가 더이상 이동 불가
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

public class Main_BJ_19236_청소년상어 {
	static class Fish implements Comparable<Fish>{
		int num, x, y, dir;
		public Fish(int num, int x, int y, int dir) {
			super();
			this.num = num;
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
		@Override
		public int compareTo(Fish o) {
			return this.num-o.num;
		}
	}
	static class Shark{
		int x, y, dir;
		public Shark(int x, int y, int dir) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}
	//반시계 상,좌상,좌,좌하,하,우하,우,우상
	static int dx[] = {-1,-1,0,1,1,1,0,-1};
	static int dy[] = {0,-1,-1,-1,0,1,1,1};
	static int max;
	static ArrayList<Fish> fishes= new ArrayList<>();
	static Shark shark = new Shark(0,0,0);
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		for (int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 4; j++) {
				int n = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken())-1;
				fishes.add(new Fish(n, i, j, d));
			}
		}
		Collections.sort(fishes);
		int eat = 0;
		for (int i = 0; i < fishes.size(); i++) {
			if(fishes.get(i).x==0 && fishes.get(i).y==0) {
				shark.dir = fishes.get(i).dir;
				eat = fishes.get(i).num;
				fishes.remove(i);
				break;
			}
		}
		dfs(eat, shark, fishes);
		System.out.println(max);
	}
	 /* 문제해결 프로세스
	 * 완탐?
	 * move - > 물고기 이동
	 * 모든 가능한 경로 = 최댓값 갱신
	 * 
	 * 물고기 이동 <-> 물고기 이동 돌려놓기
	 * 1. 상어 (0,0)에 놓기
	 * 2. move 물고기 이동
	 * 3. 상어의 방향에 이동가능 for문 
	 * 		.remove
	 * 		move함수
	 * 		.add
	 * 	기저조건: 상어가 더이상 이동 불가
	 */
	//물고기 이동
	public static void dfs(int neweat, Shark newshark, ArrayList<Fish> newfish) {
		ArrayList<Fish> movefish = move(newshark, newfish);

		int k = newshark.dir;
		int nx = newshark.x;
		int ny = newshark.y;
		ArrayList<Fish> fishlist = new ArrayList<>();
		while(nx>=0 && nx<4 && ny>=0 && ny<4) {
			nx+=dx[k];
			ny+=dy[k];
			for (int i = 0; i < movefish.size(); i++) {
				if(nx==movefish.get(i).x && ny==movefish.get(i).y) {
					fishlist.add(movefish.get(i));
				}
			}
		}
		//상어 더이상 이동 불가
		if(fishlist.isEmpty()) {
			max = Math.max(max, neweat);
			return;
		}
		
		for (int i = 0; i < fishlist.size(); i++) {
			ArrayList<Fish> nfish = new ArrayList<>();
			Shark nshark = new Shark(fishlist.get(i).x, fishlist.get(i).y, fishlist.get(i).dir);
			Fish removefish = fishlist.get(i);
			int neat = fishlist.get(i).num;
			movefish.remove(fishlist.get(i));
			
			for (int j = 0; j < movefish.size(); j++) {
				nfish.add(new Fish(movefish.get(j).num, movefish.get(j).x, movefish.get(j).y, movefish.get(j).dir));
			}
			dfs(neweat+neat, nshark, nfish);
			movefish.add(removefish);
			Collections.sort(movefish);
		}
		
		
	}
	public static ArrayList<Fish> move(Shark ns, ArrayList<Fish> nf) {
		
		for (int i = 0; i < nf.size(); i++) {
			int d = nf.get(i).dir;
			
			//물고기 이동
			while(true) {
				int mx = nf.get(i).x+dx[d];
				int my = nf.get(i).y+dy[d];
				//이동 불가 -> 반시계 45도 회전
				//상어가 있거나
				if(mx==ns.x && my==ns.y) d++;
				//공간의 경계를 넘음
				else if (mx<0 || mx>=4 || my<0 || my>=4) d++;
				//이동가능 -> 빈칸 또는 다른 물고기가 있는 칸
				else {
					for (int j = 0; j < nf.size(); j++) {
						//다른 물고기 있으면 서로의 위치 바꿈
						if(nf.get(j).x == mx && nf.get(j).y == my) {
							int temp = nf.get(i).x;
							nf.get(i).x = nf.get(j).x;
							nf.get(j).x = temp;
							
							temp = nf.get(i).y;
							nf.get(i).y = nf.get(j).y;
							nf.get(j).y = temp;
							
							nf.get(i).dir = d;
							break;
						}
						//빈칸이면 그냥 이동
						if(j==nf.size()-1) {
							nf.get(i).x = mx;
							nf.get(i).y = my;
							nf.get(i).dir = d;
						}
					}
					break;
				}
				//45도 회전
				if(d>=8) d-=8;
				//이동할 수 있는 칸이 없음 -> 이동하지 않음
				if(d==nf.get(i).dir) {
					break;
				}
			}
		}
		return nf;
	}

}
