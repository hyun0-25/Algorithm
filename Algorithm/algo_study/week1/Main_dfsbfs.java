package week1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Main_dfsbfs {
	public static int N,M,V;
	public static boolean[] visited = new boolean[1001];
	public static boolean[] visited2 = new boolean[1001];
	public static ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
	
	public static void dfs(int x) {
		// 현재 노드 방문 처리
		visited[x] = true;
		System.out.print(x + " ");
		//현재 노드와 연결된 다른 노드를 재귀적으로 방문
		for(int i = 0; i<graph.get(x).size(); i++) {
			int y = graph.get(x).get(i);
			//방문하지 않았다면 dfs 스택에 넣음
			if(!visited[y])
				dfs(y);
		}
	}
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.offer(start);
		// 현재 노드 방문 처리
		visited2[start] = true;
		//큐가 빌 때까지 반복
		while(!q.isEmpty()) {
			//큐에서 하나의 원소를 뽑아 출력
			int x = q.poll();
			System.out.print( x + " ");
			for(int i = 0; i<graph.get(x).size(); i++) {
				int y = graph.get(x).get(i);
				//방문하지 않았다면 bfs 큐에 넣음
				if(!visited2[y]) {
					q.offer(y);
					visited2[y] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		V = sc.nextInt();
		sc.nextLine();
		graph.add(new ArrayList<Integer>());
		for(int i = 1; i<N+1; i++) {
			graph.add(new ArrayList<Integer>(Arrays.asList()));
		}
		for(int i = 1; i<M+1; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			graph.get(a).add(b);
			graph.get(b).add(a);
			sc.nextLine();
		}
		for(int i=0;i<N+1;i++) {
			Collections.sort(graph.get(i));
		}
		dfs(V);
		System.out.println();
		bfs(V);
		
	}

}