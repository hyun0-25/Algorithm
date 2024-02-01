package ps230803_PowerSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

public class Solution_5215_햄버거다이어트_이현영 {
	static int T, N, L, ham[][], max;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++){
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			//재료의 수
			N = Integer.parseInt(st.nextToken());
			//제한 칼로리
			L = Integer.parseInt(st.nextToken());
			ham = new int[N][2];
			max = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				//민기 맛 점수
				ham[i][0] = Integer.parseInt(st.nextToken());
				//재료 칼로리
				ham[i][1] = Integer.parseInt(st.nextToken());
			}
			cook(0,0,0);
			System.out.printf("#%d %d\n", test_case, max);
		}
		
	}
	//
	private static void cook(int i, int taste, int cal) {
		//칼로리 초과
		if(cal>L) {
			return;
		}
		
		//인덱스 탐색 끝
		if(i==N) {
			//최대 맛점수
			max = Math.max(max, taste);
			return;
		}
		
		//선택 o
		cook(i+1, taste+ham[i][0], cal+ham[i][1]);
		//선택 x
		cook(i+1, taste, cal);
	}

}
