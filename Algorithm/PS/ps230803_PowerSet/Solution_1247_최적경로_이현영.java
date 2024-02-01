package ps230803_PowerSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 위치는 (x,y)
 * output: 회사에서 출발 -> N명의 고객 모두 방문 -> 집으로 돌아오는 최단 경로
 * 회사, 집, 2~10명 고객
 * 
 * 모든 가능한 경로를 살펴서 해 찾음
 * -> 순서 상관있는 nPn = n!
 * */


public class Solution_1247_최적경로_이현영 {
	static int T, N, min, map[][], picked[][];
	static boolean[] isSelected;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++)	{
			//N은 고객의 수
			N = Integer.parseInt(br.readLine());
			map = new int[N+2][2];
			StringTokenizer st = new StringTokenizer(br.readLine());
			//회사, 집, N명 고객 (x,y)좌표
			for (int i = 0; i < N+2; i++) {
				//x: map[i][0], y: map[i][1]
				for (int j = 0; j < 2; j++) {
					map[i][j]=Integer.parseInt(st.nextToken());
				}
			}
			isSelected = new boolean[N+2];
			picked = new int[N][];
			min=Integer.MAX_VALUE;
			P(0);
			System.out.printf("#%d %d\n",test_case,min);
		}
	}
	private static void P(int idx) {
		if(idx==N) {
			//함수 실행
			dis(picked);
			return;
		}
		for (int i = 2; i < N+2; i++){
			if(isSelected[i])  continue;
			picked[idx] = map[i];
			isSelected[i] = true;
			P(idx+1);
			isSelected[i] = false;
		}
	}
	//N명 손님들의 위치 순서 모든 경우의 수
	private static void dis(int [][] picked) {
		//회사 좌표
		//map[0][0] map[0][1]
		int x = map[0][0];
		int y = map[0][1];
		int total = 0;
		//N명의 손님 집 방문
		for (int i = 0; i < N; i++) {
			total+=Math.abs(x-picked[i][0])+Math.abs(y-picked[i][1]);
			x = picked[i][0];
			y = picked[i][1];
		}
				
		//집 도착
		//map[1][0] map[1][1]
		total+=Math.abs(x-map[1][0])+Math.abs(y-map[1][1]);
		min = Math.min(min, total);
	}
}
