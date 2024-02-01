package algo230824_MST_PRIM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MSTPrimTest {
	static int V, adjMatrix[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		V = Integer.parseInt(br.readLine());
		adjMatrix = new int[V][V];
		
		for (int i = 0; i < V; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < V; j++) {
				adjMatrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//방문 정점(트리정점표시)
		boolean[] visited = new boolean[V];
		int [] minEdge = new int[V]; //자신과 트리의 정점들 간 최소 간선 비용
		 
		Arrays.fill(minEdge, Integer.MAX_VALUE); //최솟값 갱신 위해 큰 값으로 세팅
		minEdge[0] = 0; //임의의 0 정점을 트리 구성의 시작으로 하기 위해 세팅
		
		int result = 0; //최소신장트리 비용
		int min = 0, minVertex = 0;
		
		for (int c = 0; c < V; c++) {
			minVertex = -1;
			min = Integer.MAX_VALUE;
			//step1. 미방문(비트리) 정점 중 최소간선비용의 정점을 선택
			for (int i = 0; i < V; i++) {
				if(!visited[i] && min>minEdge[i]) {
					minVertex = i;
					min = minEdge[i];
				}
			}
			//step2. 방문(트리) 정점에 추가
			visited[minVertex] = true; //방문 처리
			
			//step3. 트리에 추가된 새로운 정점 기준을 비트리 정점과의 간선비용 고려 
			for (int i = 0; i < V; i++) {
				if(!visited[i] && adjMatrix[minVertex][i] != 0 && minEdge[i]>adjMatrix[minVertex][i]) {
					minEdge[i] = adjMatrix[minVertex][i];
				}
			}
		}
		System.out.println(result);
	}

}
