package ps230802_Combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_2805_농작물_수확하기_이현영 {
	static int T, N;
	static int [][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++)	{
			//농장의 크기 NxN
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					map[i][j] = str.charAt(j)-'0';
				}
			}			
		
		//시작 행의 가장 가운데 값
		int idx = N/2;
		int result = 0;
		for (int i = 0; i < N; i++) {
			
			if(i<idx) {
				for (int j = idx-i; j < idx+i+1; j++) {
					result+=map[i][j];
				}
			}
			else {
				for (int j = i-idx ; j < N-(i-idx); j++) {
					result+=map[i][j];
				}
			}
			
		}
		System.out.printf("#%d %d",test_case,result);
		System.out.println();
		
		}
	}

}
