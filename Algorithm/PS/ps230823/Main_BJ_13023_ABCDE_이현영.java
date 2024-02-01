package ps230823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:28
 * 종료시간:
 * 
 * 
 * 문제 해석
 * A,B,C,D,E
 * A,B는 친구
 * B,C는 친구
 * C,D는 친구
 * D,E는 친구
 * 위와 같은 친구 관계가 존재하는지 안하는지 구하여라
 * 
 * 입력
 * 첫째줄: 사람의 수 N, 친구 관계의 수 M
 * 둘째줄: M개의 줄에 정수 a,b (a,b는 친구)
 * 
 * 
 * 출력
 * 문제의 조건에 맞는 A,B,C,D,E가 존재하면 1 없으면 0
 * 
 * 문제해결 프로세스
 * 모든 친구에 대해서 
 * 		depth=5가 되면 1
 * 						안되면 0
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_13023_ABCDE_이현영 {
	static int N,M;
	static ArrayList<Integer> number[];
	static boolean isVisited[] = new boolean[2000];
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		number = new ArrayList[2000];
		
		for (int i = 0; i < 2000; i++) {
			number[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			number[from].add(to);
			number[to].add(from);
		}
		
		//적어도 1개의 간선이 존재
		for (int i = 0; i < 2000; i++) {
			if(number[i].size()!=0) {
				Arrays.fill(isVisited, false);
				dfs(i, 1);
			}
		}
		System.out.println(0);
	}
	
	public static void dfs(int x, int depth) {
		if(depth==5) {
			System.out.println(1);
			System.exit(0);
		}
		isVisited[x]=true;
		int cnt1=0;
		
		for (int i = 0; i < number[x].size() ; i++) {
			//x와 연결된 노드들 
			//방문하지 않은 노드면 dfs
			if(!isVisited[number[x].get(i)]) dfs(number[x].get(i), depth+1);
			//이미 방문했으면 x
			else {
				cnt1++;
			}
		}
		isVisited[x]=false;
		//방문한 인접 노드의 총 개수 = 노드와 인접한 노드들의 총 개수
		//더이상 방문 불가하므로 return
		if(cnt1==number[x].size()) return;
		
	}
}
