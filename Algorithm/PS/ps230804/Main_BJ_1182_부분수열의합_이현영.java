package ps230804;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * N개의 정수로 이루어진 수열
 * 크기가 양수인 부분수열 중 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수 출력
 * => 부분집합 

 * why? 더하는 원소의 개수가 정해지지 않았기 때문
 */



public class Main_BJ_1182_부분수열의합_이현영 {
	static int N, S, cnt, nums[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st ;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		nums = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			nums[i]=Integer.parseInt(st.nextToken());
		}
		
		cnt = 0;
		N_sum(0, 0);
		if (S!=0) {
			System.out.println(cnt);
		}
		else {
			System.out.println(cnt-1);
		}
		
	}
	private static void N_sum(int idx, int sum) {
		//인덱스 끝까지 다돌았는데 sum과 S가 같으면 cnt++;
		if(idx==N) {
			if(sum==S) {
				cnt++;
			}
			return;
		}
		
		//선택 x
		N_sum(idx+1, sum);
		//선택 o
		N_sum(idx+1, sum+nums[idx]);
	}

}
