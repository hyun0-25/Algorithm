package ps230807;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 16:35
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * N개의 높이가 서로다른 탑을 수평 직선의 왼쪽부터 오른쪽 방향으로 차례로 세우고
 * 각 탑의 꼭대기에 레이저 송신기를 설치
 * 모든 탑의 레이저 송신기는 레이저 신호를 지표면과 평행하게 수평 직선의 왼쪽 방향으로 발사
 * 탑의 기둥 모두에는 레이저 신호를 수신하는 장치가 설치
 * 하나의 탑에서 발사된 레이저 신호는 가장 먼저 만나는 단 하나의 탑에서만 수신 가능
 * 
 * 6 9 5 7 4 
 * ----------
 * 0 0 2 2 4
 * X X 9 9 7
 *
 * 구해야 하는 것
 * 각각의 탑에서 발사한 레이저 신호를 몇 번째 탑이 수신하는지 출력
 * 
 * 문제 입력
 * 첫째줄: 탑의 수 N
 * 둘째줄: 탑들의 높이
 * 
 * 제한 요소
 * 1<=N<=500,000
 * 1<=탑의 높이 <100,000,000
 *
 * 생각나는 풀이 (시간 초과)
 * 가장 오른쪽 인덱스부터 탐색해서 더 큰 값을 만나면 출력결과에 저장(?)
 * -> O(N^2) = O(
 * -----------------
 * 스택으로 풀자!
 * -> O(N+N) = O(N)
 * 
 *
 * 구현해야 하는 기능
 *
 * idx와 탑 높이를 저장하고
 * for i=n-1;i>=0;i++
 * 	idx가 더 크고, 탑
 *
 *
 */
public class Main_BJ_2493_탑_이현영 {
	static int N, height[];
	static int start, end;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		Stack<Integer> top = new Stack<Integer>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		height = new int[N];
		for (int i = 0; i < N; i++) {
			height[i] = Integer.parseInt(st.nextToken());
		}
		StringBuilder sb = new StringBuilder();
		/**
		 * 첫번째꺼 넣고 시작
		 * 앞에 더 큰게 있음 -> peek의 idx 추가 
		 * 앞에 더 작거나 같은게 있음 -> 더 큰게 나올때까지 pop + 자기자신 push
		 *                      -> 큐가 다 비었으면 0
		 */
		int idx=0;
		top.push(idx);
		while(idx<N) {
			if(height[top.peek()]>height[idx]) {
				sb.append(top.peek()+1).append(' ');
			}
			else {
				while(true) {
					if(top.isEmpty()) {
						sb.append(0).append(' ');
						break;
					}
					if(height[top.peek()]<=height[idx]) {
						top.pop();
					}
					else {
						sb.append(top.peek()+1).append(' ');
						top.push(idx);
						break;
					}
				}
			}
			top.push(idx++);
		}
		System.out.println(sb);
	}
}

