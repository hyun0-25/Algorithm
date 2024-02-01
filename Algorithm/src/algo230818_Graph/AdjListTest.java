package algo230818_Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

import algo230818_Graph.AdjListTest.Node;

//무향 그래프
public class AdjListTest {
	static class Node{ 
		int vertex; //관계를 맺고 있는 타정점 정보
		Node next; //연결리스트 유지를 위한 다음 노드 참조
		public Node(int vertex, Node next) {
			super();
			this.vertex = vertex;
			this.next = next;
		}
		@Override
		public String toString() {
			return "Node [vertex=" + vertex + ", next=" + next + "]";
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt();
		int E = sc.nextInt();
		
		Node adjList[] = new Node[V];  //head 리스트
		
		int[][] adjMatrix = new int[V][V]; //초기값 0
		//간선 있으면 1, 없으면 0
		
		for (int i = 0; i < E; i++) {
			int from = sc.nextInt();
			int to = sc.nextInt();
			adjList[from] = new Node(to, adjList[from]); //첫 원소로 삽입
			adjList[to] = new Node(from, adjList[to]);
		}
//		for(Node node: adjList) //node: 각 정점의 인접리스트의 헤드
//			System.out.println(node);
//		bfs(adjList);
		dfs(adjList, new boolean[V], 0);
	}
	
	public static void bfs(Node adjList[]) {
		int size = adjList.length;
		Queue<Integer> queue = new ArrayDeque<>(); //큐에 넣는 값은 방문대상을 관리할 값과 그 밖의 값들을 넣을 수 있다
		boolean [] visited = new boolean[size];
		
		//탐색 시작점 정점 0으로 함
		queue.offer(0);
		visited[0]=true;
		
		while(!queue.isEmpty()) {
			int current = queue.poll();
			System.out.println((char)(current+65));
			
			//현 정점의 인접 정점들 체크하며 대기열에 넣기
			for (Node temp = adjList[current]; temp!=null; temp=temp.next) {
				if(!visited[temp.vertex]) {
					dfs(adjList, visited, temp.vertex);
				}
			}
		}
	}
	public static void dfs(Node adjList[], boolean[] visited, int current) {
		visited[current] = true;
		System.out.println((char)(current+65));
		//현 정점의 인접정점들 체크하며 대기열에 넣기
		for (Node temp = adjList[current]; temp!=null; temp = temp.next) {
			if(!visited[temp.vertex]) {
				dfs(adjList, visited, temp.vertex);
			}
		}
	}

}
