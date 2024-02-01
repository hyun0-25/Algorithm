package ps230821;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_BJ_2252_줄세우기_이현영 {
	static int N,M;
	static List<Integer> student[];
	static int degrees[];
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		//학생 키 정보
		student = new List[N+1];
		//진입 차수 정보
		degrees = new int[N+1];
		for (int i = 1; i <= N; i++) {
			student[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			student[from].add(to);
			degrees[to]++;
		}
		BFS();
		System.out.println(sb);
	}
	
	public static void BFS() {
		Queue<Integer> q = new ArrayDeque<>();
		for (int i = 1; i <= N; i++) {
			//진입 차수가 아무 것도 없으면 큐에 넣음
			if(degrees[i]==0) q.offer(i);
		}
		
		while(!q.isEmpty()) {
			//꺼낸 숫자 = 진입 차수가 0인 숫자
			int from = q.poll();
			
			sb.append(from).append(' ');
			//그 숫자와 인접한 숫자의 간선을 1 감소
			for(int to: student[from]) {
				degrees[to]--;
				if(degrees[to]==0) q.offer(to);
			}
		}
		
	}
	
}
