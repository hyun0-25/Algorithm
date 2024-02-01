package ps231010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:41
 * 종료시간: 11:45
 * 
 * 
 * 문제 해석
 * N명의 사람이 살고 있음, 1~N번 사람까지 번호
 * 두사람은 서로를 알고 있는 관계일 수도, 아닐수도있음
 * 두사람이 서로 아는 관계이거나 몇 사람을ㅇ 거쳐서 알 수 있는 관계면
 * 이러한 사람들을 모두 다 묶어서 하나의 무리라고 함
 * 몇개의 무리가 존재하는지 계산
 * 
 * 
 * 입력
 * 첫째줄: N사람수,M관계수
 * M개줄: 서로알고있는 두사람의 번호
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. make,find,union 
 * 2. parents에서 서로다른 번호의 개수 세기 = 무리수
 * 3. 무리수 출력
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Solution_7465_창용마을무리의개수_이현영 {
	static int result,N,M,parents[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		for (int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			make();
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				union(from,to);
			}
			
			for (int i = 1; i <= N; i++) {
				if(parents[i]!=parents[parents[i]]) {
					parents[i] = find(parents[i]);
				}
			}
			//집합 수 구하기
//			boolean isset[] = new boolean[N+1];
//			int result = 0;
//			for (int i = 1; i <= N; i++) {
//				if(!isset[parents[i]]) {
//					result++;
//					isset[parents[i]]=true;
//				}
//			}
			Set<Integer> set = new HashSet<>();
			for (int i = 1; i <= N; i++) {
				set.add(parents[i]);
			}
			int result = set.size();
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	public static void make() {
		parents = new int[N+1];
		for (int i = 1; i <= N; i++) {
			parents[i] = i;
		}
	}
	public static int find(int a) {
		if(parents[a]==a) return a;
		//else
		return parents[a] = find(parents[a]);
	}
	public static void union(int a, int b) {
		int aroot = find(a);
		int broot = find(b);
		if(aroot==broot) return;
		else {
			parents[broot]=aroot;
		}
	}

}
