package ps230804;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * k개 만큼의 부분집합이 주어지면
 * 그 안에서 6개의 수를 고르는 경우의 수 모두 출력
 * => 조합
 * 
 */

public class Main_BJ_6603_로또_이현영 {
	static int k, nums[];
	static StringBuilder sb;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		while(true) {
			sb = new StringBuilder();
			st = new StringTokenizer(br.readLine());
			k = Integer.parseInt(st.nextToken());
			if(k==0) {
				return;
			}
			nums = new int[k];
			for (int i = 0; i < k; i++) {
				nums[i]=Integer.parseInt(st.nextToken());
			}
			
			C(new ArrayList<Integer>(), 0, 6);
//			sb.append('\n');
//			System.out.println();
			System.out.println(sb);
		}
		
		
	}
	private static void C(ArrayList<Integer> list, int idx, int cnt) {
		if(cnt==0) {
			for(int x : list) {
				sb.append(x).append(' ');
//				System.out.print(x+ " ");
			}
			sb.append('\n');
//			System.out.println();
			return;
		}
		for (int i = idx; i < k; i++) {
			if(list.contains(nums[i])) continue;
			list.add(nums[i]);
			C(list, i+1, cnt-1);
			list.remove(list.size()-1);
		}
	}
}
