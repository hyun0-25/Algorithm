package ps231005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 9:22
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 정점 중심으로 자신보다 큰 번호 정보 저장
 * 2. N-1명인지 탐색
 * 	2-1. Large() -> 자신의 리스트에 있는 정점들을 따라서 찾은 번호의 수
 * 	2-2. small() -> 자신을 리스트의 원소로 갖고 있는 정점들을 따라서 찾은 번호의 수
 * 리스트보다는 2차원 인접행렬이 적합
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Solution_5643_키순서 {
	static int N,M;
	static boolean map[][], check[];
	static Queue<Integer> q = new ArrayDeque<>();
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			map = new boolean[N+1][N+1];
			StringTokenizer st;
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int small = Integer.parseInt(st.nextToken());
				int large = Integer.parseInt(st.nextToken());
				map[small][large] = true;
			}
			int result = 0;
			check = new boolean[N+1];
			
			//1~N번까지 학생이 키순서 알 수 있는지 파악
			for (int i = 1; i <= N; i++) {
				if(order(i)) result++;
			}
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	
	public static boolean order(int num) {
		int count = 0;
		Arrays.fill(check, false);
		
		//자신보다 큰 정점 개수 = 열값
		for (int i = 1; i <= N; i++) {
			if(map[num][i] && !check[i]) {
				q.offer(i);
				check[i] = true;
				count++;
			}
		}
		while(!q.isEmpty()) {
			int p = q.poll();
			for (int i = 1; i <= N; i++) {
				if(map[p][i] && !check[i]) {
					q.offer(i);
					check[i] = true;
					count++;
				}
			}
		}
		
		//자신보다 작은 정점 개수 = 행값
		for (int i = 1; i <= N; i++) {
			if(map[i][num] && !check[i]) {
				q.offer(i);
				check[i] = true;
				count++;
			}
		}
		while(!q.isEmpty()) {
			int p = q.poll();
			for (int i = 1; i <= N; i++) {
				if(map[i][p] && !check[i]) {
					q.offer(i);
					check[i] = true;
					count++;
				}
			}
		}
		
		if(count==N-1) return true;
		else return false;
	}

}
