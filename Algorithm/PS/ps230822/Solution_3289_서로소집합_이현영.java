package ps230822;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:05
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 1,2,3,...,n이 각각 n개의 집합
 * 합집합 연산과 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산ㅇ르 수행
 *  
 * 
 * 입력
 * 첫째줄: 집합의 개수 n, 연산의 개수 m
 * 다음 m개줄: 합집합- 0 a b
 * 		같은 집합인지- 1 a b
 * 
 * 
 * 출력
 * 1로 시작하는 입력에 대해서 같은 집합에 있으면 1, 아니면 0을 출력
 * 
 * 문제해결 프로세스
 * Union 함수 - b의 parents를 a로 변경
 * 같은set인지 확인하는 함수 - parents가 같은지 확인
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Solution_3289_서로소집합_이현영 {
	static int n,m;
	static int parents[];
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			//부모가 누구인지 저장하는 배열
			parents = new int [n+1];
			for (int i = 1; i <= n; i++) {
				parents[i]=i;
			}
			
			sb.append('#').append(test_case).append(' ');
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if(s==0) Union(a,b);
				else if(s==1) Same_set(a,b);
			}
			sb.append('\n');
			
		}
		System.out.println(sb);
	}
	public static void Union(int x, int y) {
		int x_parent = find(x);
		int y_parent = find(y);
		//이미 같은 집합이면 아무것도x
		if(x_parent==y_parent) return;
		//else
		parents[y_parent]=x_parent;
	}
	
	public static void Same_set(int x, int y) {
		int x_parent = find(x);
		int y_parent = find(y);
		if(x_parent==y_parent) sb.append(1);
		else sb.append(0);
	}
	
	public static int find(int k) {
		if(k==parents[k]) return k;
		return parents[k]=find(parents[k]);
	}
}
