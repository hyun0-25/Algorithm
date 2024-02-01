package ps230801_Recursive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution_1208_Flatten_이현영 {
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		for(int test_case = 1; test_case <= 10; test_case++) {
			int N = sc.nextInt();
			//가로 길이는 항상 100
			int [] height = new int [100];
			for(int i = 0; i<height.length; i++) {
				height[i]=sc.nextInt();
			}
			int max_idx = 0;
			int min_idx = 0;
			int max = height[0];
			int min = height[0];
			//flatten 횟수
			for (int k=0; k<N; k++) {
				max_idx = 0;
				min_idx = 0;
				max = height[0];
				min = height[0];
				//최댓값과 최솟값 인덱스 찾기
				for(int i=0; i<height.length; i++) {
					if(height[i]>max) {
						max = height[i];
						max_idx = i;
					}
					if(height[i]<min) {
						min = height[i];
						min_idx = i;
					}
				}
				//flatten 작업
				height[max_idx]-=1;
				height[min_idx]+=1;
			}
			//flatten한 상태에서 (최대 높이-최소 높이) 구하기
			max = height[0];
			min = height[0];
			for(int i=0; i<height.length; i++) {
				max = Math.max(max, height[i]);
				min = Math.min(min, height[i]);
			}
			int result = max-min;
			System.out.printf("#%d %d",test_case, result);
			System.out.println();
		}
		
	}

}

