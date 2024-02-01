package week1;
import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;
public class Main_미로탐색 {
	public static int N,M;
	public static int[][] map = new int[201][201];
	
	public static int[] dx = {1, -1, 0, 0};
	public static int[] dy = {0, 0, 1, -1};
	
	public static int bfs(int i, int j) {
		class Pair{
			int x;
			int y;
			public Pair(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
		}
		Queue<Pair> q = new LinkedList<>();
		//시작 노드 queue에 넣음
		q.offer(new Pair(i,j));
		//큐가 빌때까지
		while(!q.isEmpty()) {
			Pair p = q.poll();
			for(int k=0; k<4; k++) {
				int nx = p.x+dx[k];
				int ny = p.y+dy[k];
				//경계 밖
				if(nx<0 || nx>=N || ny<0 || ny>=M) {
					continue;
				}
				//0은 이동불가
				if(map[nx][ny]==0) {
					continue;
				}
				//1은 이동가능
				if(map[nx][ny]==1) {
					q.offer(new Pair(nx,ny));
					map[nx][ny] = map[p.x][p.y] + 1;
				}
				
			}
		}
		return map[N-1][M-1];
		
		
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		sc.nextLine();
		
		for (int i = 0; i<N; i++) {
			String str = sc.nextLine();
			for(int j = 0; j<M; j++) {
				map[i][j] = str.charAt(j)-'0';
			}
		}
		
		//bfs시작
		System.out.println(bfs(0,0));
	}

}
