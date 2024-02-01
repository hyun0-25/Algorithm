package ps230823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:16
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 비상 연락망과 연락을 시작하는 당번에 대한 정부가 주어질 때, 가장 나중에 연락을 받게 되는 사람 중 번호가 가장 큰 사람을 구하여라
 * 비상 연락망이 가동되면 같이 연락을 시작하는 당번은 이어진 번호에게 동시에 연락 취함
 * 
 * 한번 연락을 받은 사람은 다시 연락하지 않음
 * 
 * 
 * 입력
 * 첫째줄: 데이터의 길이, 시작번호
 * 둘쨰줄: from,to쌍
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 0. 입력받을 때, node번호에 대해 to관계를 갖는 번호를 해당 인덱스에 저장
 * 1. 시작번호부터 queue에 넣음 + 방문 처리
 * 2. 꺼내서 to에 해당되는 번호 중 방문하지 않은 것들을 queue에 넣음
 * 3. 1,2번 반복
 * 	3-1. 꺼낸 번호들이 to가 더이상 존재하지 않으면 끝
 * 	3-2. 그때의 번호 중 가장 큰 번호 출력
 * 
 * 
 * 
 * 제한조건
 * 연락 인원<=100, 번호<=100
 * 중간에 비어있는 번호 존재할 수 있음
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Solution_1238_Contact_이현영 {
	static int N, start;
	static ArrayList<Integer> number[];
	static boolean isVisited[];
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		for (int test_case = 1; test_case <= 10; test_case++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			start = Integer.parseInt(st.nextToken());
			
			number = new ArrayList[101];
			isVisited = new boolean[101];
			for (int i = 0; i <= 100; i++) {
				number[i] = new ArrayList<>();
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N/2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				number[from].add(to);
			}
			sb.append('#').append(test_case).append(' ');
			call();
			sb.append('\n');
		}
		System.out.println(sb);
	}
	
	/* 문제해결 프로세스
	 * 0. 입력받을 때, node번호에 대해 to관계를 갖는 번호를 해당 인덱스에 저장
	 * 1. 시작번호부터 queue에 넣음 + 방문 처리
	 * 2. 꺼내서 to에 해당되는 번호 중 방문하지 않은 것들을 queue에 넣음
	 * 3. 1,2번 반복
	 * 	3-1. 꺼낸 번호들이 to가 더이상 존재하지 않으면 끝
	 * 	3-2. 그때의 번호 중 가장 큰 번호 출력
	 */
	static class Node {
		int idx, depth;

		public Node(int idx, int depth) {
			super();
			this.idx = idx;
			this.depth = depth;
		}
	}
	
	static ArrayList<Node> list = new ArrayList<>();
	
	public static void call() {
		Queue<Node> q = new ArrayDeque<>();
		//1. 시작번호부터 queue에 넣음 + 방문 처리
		q.offer(new Node(start,1));
		isVisited[start]=true;
		
		int end_depth = 0;
		list.clear();
		while(!q.isEmpty()) {
			Node x = q.poll();
			isVisited[x.idx]=true;
			
			int cnt=0;
			for(int c : number[x.idx]) {
				if(!isVisited[c]) cnt++;
			}
			
			//말단 노드면
			if(cnt==0) {
				list.add(x);
			}
			end_depth = Math.max(end_depth, x.depth);
			
			for(int c : number[x.idx]) {
				//2. 꺼내서 to에 해당되는 번호 중 방문하지 않은 것들을 queue에 넣음
				if(!isVisited[c]) {
					q.offer(new Node(c, x.depth+1));
					isVisited[c]=true;
				}
			}
			
			
		}
		int max = 0;
		for(Node l: list) {
			if(l.depth == end_depth) {
				max = Math.max(max, l.idx);
			}
		}
		sb.append(max);
		return;
		
	}
}
