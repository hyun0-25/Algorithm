package ps230824;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:31
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN개의 정사각형 벌통들
 * 각 칸의 숫자는 각각의 벌통에 있는 꿀의 양, 꿀의 양은 서로 다를 수 있음
 * 벌꿀을 채취하여 최대한 많은 수익
 * 1. 2명의 일꾼, 꿀을 채취할 수 있는 벌통의 수 M
 * 		각각의 일꾼은 가로로 연속되게 M개 벌통 선택, 선택한 벌통에서 꿀 채취 가능
 * 		단, 2명은 서로 선택한 벌통이 겹치면 안됨
 * 2. 선택한 벌통에서 꿀을 채취하여 용기에 담음
 * 		단, 1개의 벌통에서 채취한 꿀은 1개의 용기에 담아야함
 * 		2명이 채취할 수 있는 꿀의 최대 양은 C
 * 3. 채취한 꿀은 1개의 용기에 있는 꿀의 양이 많을 수록 좋음
 * 		각 용기에 있는 꿀의 양의 제곱만큼의 수익이 생김
 * 
 * 2명의 일꾼 이 꿀을 채취해서 얻을 수 있는 수익의 합이 최대가 되는 경우를 찾고, 그때의 최대 수익을 출력
 * 
 * 입력
 * 첫째줄: 벌통 NxN, 선택하는 벌통 수 M, 꿀 최대 채취양 C
 * N개 줄: 맵 정보
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. N개 행을 M길이 만큼씩 자름 (N,시작)~(N,끝)
 * 2. 1번에서 자른 가능한 벌통을 2개 조합으로 선택
 * 3. 2번에서 고른 벌통에서, 부분집합을 이용
 * 		부분집합의 원소의 합이 C를 넘지않는 경우, 각 원소의 제곱의 합을 최댓값에서 갱신
 * 4. 3번을 2명의 일꾼에 대해 실행
 * 5. 4번의 실행 결과 2개를 더함
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

public class Solution_2115_벌꿀채취_이현영 {
	static class Honey {
		int r, c1, c2;
		public Honey(int r, int c1, int c2) {
			super();
			this.r = r;
			this.c1 = c1;
			this.c2 = c2;
		}
	}
	
	static int N,M,C, map[][], sum_h, max, sum_h1h2;
	static Honey honey[] ;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st ;
		for (int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			int idx=0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			honey = new Honey[N*(N-M+1)];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N-M+1; j++) {
					honey[idx++] = new Honey(i, j, j+M-1);
				}
			}
			
			max=0;
			Honey h1,h2;
			for (int i = 0; i < N*(N-M+1); i++) {
				h1 = honey[i];
				for (int j = i+1; j < N*(N-M+1); j++) {
					sum_h1h2 = 0;
					
					h2 = honey[j];
					//서로 다른 행이면 ok
					if(h1.r!=h2.r) getHoney(h1,h2);
					//서로 같은 행이면 c1~c2범위 겹치는지 확인
					else {
						if(h1.c2<h2.c1) getHoney(h1,h2);
					}
				}
			}
			sb.append('#').append(test_case).append(' ').append(max).append('\n');
		}
		System.out.println(sb);
	}
	/* 문제해결 프로세스
	 * 1. N개 행을 M길이 만큼씩 자름 (N,시작)~(N,끝)
	 * 2. 1번에서 자른 가능한 벌통을 2개 조합으로 선택
	 * 3. 2번에서 고른 벌통에서, 부분집합을 이용
	 * 		부분집합의 원소의 합이 C를 넘지않는 경우, 각 원소의 제곱의 합을 최댓값에서 갱신
	 * 4. 3번을 2명의 일꾼에 대해 실행
	 * 5. 4번의 실행 결과 2개를 더함
	 */
	
	//선택한 h1,h2로 벌꿀 채취 시작
	public static void getHoney(Honey honey1, Honey honey2) {
		sum_h = 0;
		powerset(honey1.r, honey1.c1, honey1.c2, 0, new ArrayList<Integer>());
		sum_h1h2 += sum_h;
		sum_h = 0;
		powerset(honey2.r, honey2.c1, honey2.c2, 0, new ArrayList<Integer>());
		sum_h1h2 += sum_h;
		max = Math.max(max, sum_h1h2);
	}
	
	
	public static void powerset(int sr, int sc1, int sc2, int cnt, ArrayList<Integer> list) {
		if(cnt==M) {
			if(list.size()==0) return;
			int sum=0;
			for (int h : list) {
				sum+=h;
				//C초과하면 return;
				if(sum>C) return;
			}
			//C보다 작거나 같음
			int result = 0;
			result = cal_square(list);
			sum_h = Math.max(sum_h, result);
			return;
		}
		
		powerset(sr, sc1, sc2, cnt+1, list);
		list.add(map[sr][sc1+cnt]);
		powerset(sr, sc1, sc2, cnt+1, list);
		list.remove(list.size()-1);
		
	}
	public static int cal_square(ArrayList<Integer> sel_honey) {
		int sum=0;
		for (int h : sel_honey) {
			sum += h*h;
		}
		return sum;
	}
}
