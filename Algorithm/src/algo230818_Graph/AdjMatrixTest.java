package algo230818_Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

//무향 그래프
public class AdjMatrixTest {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt();
		int E = sc.nextInt();
		
		int[][] adjMatrix = new int[V][V]; //초기값 0
		//간선 있으면 1, 없으면 0
		
		for (int i = 0; i < E; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			adjMatrix[to][from] = adjMatrix[from][to] = 1;
		}
//		bfs(adjMatrix);
		dfs(adjMatrix, new boolean[V], 0);
		
	}
	public static void bfs(int[][] adjMatrix) {
		int size = adjMatrix.length;
		Queue<Integer> queue = new ArrayDeque<>(); //큐에 넣는 값은 방문대상을 관리할 값과 그 밖의 값들을 넣을 수 있다
		boolean [] visited = new boolean[size];
		
		//탐색 시작점 정점 0으로 함
		queue.offer(0);
		visited[0]=true;
		
		while(!queue.isEmpty()) {
			int current = queue.poll();
			System.out.println((char)(current+65));
			
			//현 정점의 인접 정점들 체크하며 대기열에 넣기
			for (int i = 0; i < size; i++) {
				//가중치 : 0이면 인접x, 0이 아니면 인접
				if(adjMatrix[current][i]!=0 && !visited[i]) {
					queue.offer(i);
					visited[i]=true;
				}
			}
		}
	}
	public static void dfs(int[][] adjMatrix, boolean[] visited, int current) {
		visited[current] = true;
		System.out.println((char)(current+65));
		//현 정점의 인접정점들 체크하며 대기열에 넣기
		for (int i = 0, size = adjMatrix.length; i < size; i++) {
			if(adjMatrix[current][i]!=0 && !visited[i]) {
				dfs(adjMatrix, visited, i);
			}
		}
	}

}
