package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * NxN 체스판 위에 퀸 N개를 서로 공격할 수 없게 놓음
 * output : N이 주어졌을 때, 퀸을 놓는 방법의 수 
 * N = 1~15
 * 
 * map을 boolean으로 두고
 * for i
 * 	for j
 * 	  map[i][j]
 * 그럼 조합으로 N개는 어떻게 뽑음?
 * 
 * 
 * 탐색하지 않아도 되는 경우 실행x
 * 
 * 퀸 이동 규칙
 * 8방 1칸, 가로,세로,대각선 이동 가능
 * */



public class N_Queen {
	static class Pair{
		private int x,y;
		public Pair(int x, int y) {
			this.x=x;
			this.y=y;
		}
	}
	static int N, result=0;
	static Pair [] map;
	static boolean [][] attack;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new Pair[N*N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i*N+j]=new Pair(i,j);
			}
		}
		
		C(new ArrayList<Pair>(), 0, N);
		
		System.out.println(result);
	}
	
	private static void C(ArrayList<Pair> list, int idx, int cnt) {
		if(cnt==0) {
//			for(Pair x:list) {
//				System.out.println(x.x+" "+x.y);
//			}
//			System.out.println();
			attack = new boolean[N][N];
			queen(list);
			for (int i = 0; i < N; i++) {
				System.out.println(Arrays.toString(attack[i]));
			}
			System.out.println();
			
			return;
		}
		for(int i=idx; i<N*N; i++) {
			if(list.contains(map[i])) {
				continue;
			}
			//list 안에 map[i]없으면 추가
			list.add(map[i]);
			C(list, i+1, cnt-1); //뽑을 개수 -1
			list.remove(list.size()-1); //마지막에 넣은 원소 제거
		}
		
	}
	//상,우상,우,우하,하,좌하,좌,좌상
	static int []dx = {-1,-1,0,1,1,1,0,-1};
	static int []dy = {0,1,1,1,0,-1,-1,-1};
	
	// N개의 queen 위치 담겨있음
	// 퀸 이동 규칙
	// 8방 1칸, 가로,세로,대각선 이동 가능
	private static void queen(ArrayList<Pair> list) {
		for (int i = 0; i < list.size(); i++) {
			int x = list.get(i).x;
			int y = list.get(i).y;
			
			if(attack[x][y]) {
				return;
			}
			attack[x][y]=true;
			//8방 1칸 
			for (int k=0; k < 8; k++) {
				int nx = x+dx[k];
				int ny = y+dy[k];
				if(nx<0 || nx>=N || ny<0 || ny>=N) {
					continue;
				}
				if(attack[nx][ny]) {
					return;
				}
				attack[nx][ny]=true;
			}
			//가로,세로,대각선
			for (int k=0; k < 8; k++) {
				//이미 인접1칸은 체크 했으니 +2칸부터 체크
				int xx = x+2*dx[k];
				int yy = y+2*dy[k];
				//더이상 못가는 방향
				while(xx>=0 && xx<N && yy>=0 && yy<N) {
					if(attack[xx][yy]) {
						return;
					}
					
					attack[xx][yy]=true;
					//그 방향으로 계속 이동
					xx += dx[k];
					yy += dy[k];
//					System.out.println(xx+ " "+ yy);
				}
			}
		}
		System.out.println("true");
		result++;
		
		return;
	}

}
