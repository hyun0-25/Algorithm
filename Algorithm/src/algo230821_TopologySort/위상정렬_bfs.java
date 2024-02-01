package algo230821_TopologySort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class 위상정렬_bfs {
	static int N,M; //과목의 개수, 선수과목 정보 개수
	static int [] degrees; //degrees[3]:5 -> 3번 과목을 수강해야 5번을 수강할  수 있음
	static List<Integer> [] adjList;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		degrees = new int[N+1]; //과목 1~N
		adjList = new List[N+1]; //adjList[3] - 3번 과목을 수강한 이후 들을 수 잇는 과목 정보들
		for (int i = 1; i <= N; i++) adjList[i] = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			//1 3
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()); //1과목은 3과목을 듣기 위한 선수 과목
			int to = Integer.parseInt(st.nextToken());
			
			adjList[from].add(to);
			//to 과목을 듣기 위해 from이라는 선수 과목들 들어야하므로 진입차수 1증가
			degrees[to]++;
		}
		for (int i = 1; i <= N; i++) {
			System.out.println(i+"과목의 진입 차수: "+ degrees[i]);
		}
		
		BFS();
		
		System.out.println(sb);
		
		
	}
	public static void BFS() {
		Queue<Integer> q = new ArrayDeque<>();

		//1. 진입 차수가 0인 과목 정보를 모두 큐에 집어 넣기
		for (int i = 1; i <= N; i++) {
			if(degrees[i]==0) q.offer(i);
		}
		
		int cnt=0; //수강한 과목의 수
		while(!q.isEmpty()) {
			//큐에서 과목 정보를 하나 꺼냄
			//from 과목을 수강 완료했음
			int from = q.poll();
			sb.append(from).append(' ');
			
			cnt++;
			
			//from을 선수 과목으로 가지는 과목들의 진입  차수를 -1줄임
			for(int to: adjList[from]) {
				//진입 차수 0이됐다는 것은 현재 수강할 수 있다는 의미 = 큐에 넣어줌
				if(--degrees[to]==0) q.offer(to);
			}
		}
		//싸이클이 발생하여 모든 과목 수강이 불가한 경우
		if(cnt<N) {
			System.out.println("싸이클이 발생");
			System.exit(0);
		}
	}

}
