package ps230803_PowerSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 2명의 손님에게 음식 제공
 * 2명에게 최대한 비슷한 맛의 음식 제공
 * 
 * N개의 식재료
 * 식재료들을 각 N/2개씩 나누어 2개의 요리: A,B(N은 짝수)
 * 비슷한 맛의 음식 -> A,B의 맛의 차이가 최소 되도록 재료 배분
 * 
 * 음식의 맛은 구성하는 식재료들의 조합에 따라 다름
 * i와 j 요리 => 시너지 S(i,j) 발생
 * 음식의 맛 = 시너지들  S(i,j)의 합
 * 
 * */


public class Solution_4012_요리사_이현영 {
	static int T, N, result, map[][], food[], food2[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++){
			//N: 식재료 개수
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			StringTokenizer st;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			food = new int[N/2];
			food2 = new int[N/2];
			result = Integer.MAX_VALUE;
			C(0,0);

			System.out.printf("#%d %d\n",test_case, result);
			
			
		}
	}
	private static void C(int cnt, int start) {
		//식재료 2개 선택
		if(cnt==N/2) {
			int idx=0;
			for (int i = 0; i < N; i++) {
				boolean flag = false;
				for (int x : food) {
					if(x==i) {
						flag=true;
					}
				}
				if(!flag) {
					food2[idx++]=i;
				}
			}
			//음식 맛 차이 계산
			int f = Math.abs(taste(food)-taste(food2));
			result = Math.min(result, f);
			return;
		}
		for (int i = start; i < N; i++) {
			food[cnt]=i;
			C(cnt+1, i+1);
		}
	}
	
	
	//음식 맛 차이 계산
	private static int taste(int [] food) {
		int first=0, second=0, total=0;
		for (int i = 0; i < food.length; i++) {
			for (int j = i+1; j < food.length; j++) {
				first = food[i];
				second = food[j];
				total+=map[first][second]+map[second][first];
			}
		}
		return total;
	}
}
