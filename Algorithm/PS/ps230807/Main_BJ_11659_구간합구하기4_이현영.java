package ps230807;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 13시 07분
 * 풀이 완료 : 
 * 풀이 시간 : 
 *
 * 문제 해석
 * N->수의 개수 / M->합을 구해야하는 횟수
 * 둘째줄 : N개의 수
 * 1<=N<=1000
 * 셋째줄 : M개의줄에는 합을 구해야하는 구간 i,j
 * 
 * 
 * 구해야 하는 것
 * 구간 i와 j가 주어졌을 때 그 구간(i번째 수 ~ j번째 수까지)안의 숫자들의 합
 *
 * 문제 입력
 * 수의 개수 N, 합을 구해야하는 횟수M
 * 
 * 제한 요소
 * ??
 * 
 * 생각나는 풀이 (시간초과남)
 * 1. 셋째줄부터 나오는 M개의 줄마다 각각의 합을 출력
 * 2. for x : i~j
 * 		sum+=x
 * 3. 결과 담은 string 출력
 * 
 * 
 * 구현해야 하는 기능
 *	i~j번째까지의 합
 * 1 2 3 4 ,,,, 100
 * s1 s2        s100
 * -> O(N)
 * 
 * 
 * 시간 복잡도
 *  O(N)
 */



public class Main_BJ_11659_구간합구하기4_이현영 {
	static int N,M, nums[], idx[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		nums = new int [N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		
		idx = new int[M][2];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			idx[i][0] = Integer.parseInt(st.nextToken());
			idx[i][1] = Integer.parseInt(st.nextToken());
		}
		
		//누적합 계산
		for (int i = 0; i < N; i++) {
			if(i!=0) {
				nums[i]+=nums[i-1];
			}
		}
		
		int start=0, end=0, result=0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++) {
			start = idx[i][0]-1;
			end = idx[i][1]-1;
			if(start-1<0) {
				result = nums[end];
			}
			else {
				result = nums[end]-nums[start-1];
			}
			sb.append(result).append('\n');
		}
		System.out.println(sb);
	}
}
