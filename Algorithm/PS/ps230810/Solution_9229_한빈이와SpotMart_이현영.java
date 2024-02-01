package ps230810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:35
 * 종료시간: 14:40
 * 
 * 
 * 문제 해석
 * 마트에서 과자 2봉지를 사서 양손에 하나씩 들고감
 * 마트에는 N개의 과자, 각 과자는 aj그램의 무게
 * 과자 2봉지의 무게는 M그램 초과 불가
 * 들고 다닐 수 있는 과자들의 최대 무게의 합을 출력
 * 정확히 두봉지만 구매해야함
 * 
 * 입력
 * 첫번째줄: 과자 봉지 개수 N, 무게 합 제한 M
 * N개의 줄: 각 과자봉지의 무게 aj
 * 
 * 출력
 * 들고 갈 수 있는 과자 봉지의 무게 합 최대값
 * 들고 갈 방법이 없는 경우 -1 출력
 * 
 * 
 * 문제해결 프로세스 (조합)
 * 1. NC2로 가능한 과자 조합 모두 구함
 * 2. 각 조합의 과자 무게합이 M보다 작은 지 확인
 * 3. M보다 작은 경우의 최대값 갱신
 * ----------------------------------
 * 입력 받을 때 M이상인 과자는 입력받지 X
 * 정렬 후 풀면 더 빠를듯
 * 
 * 제한조건
 * N<=1000
 * M<=2,000,000
 * 
 * 시간복잡도
 * O(NC2) = O(1000*999/2) = O(약 50만)
 * 
 */

public class Solution_9229_한빈이와SpotMart_이현영 {
	static int T,N,M, weight[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			weight = new int [N];
			int size = 0;
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				//과자봉지 무게 초과면 저장 x
				int w = Integer.parseInt(st.nextToken());
				if(w > M) continue;
				weight[size++] = w;
			}
			weight = Arrays.copyOf(weight, size);
			int [] list = new int [2];
			int max = 0;
			//재귀 조합 구현 대신 for문 사용
			for (int i = 0; i < size; i++) {
				list[0] = weight[i];
				for (int j = i+1; j < size; j++) {
					list[1] = weight[j];
					//2봉지 뽑음
					int sum = list[0]+list[1];
					if(sum<=M) max = Math.max(max, sum);
				}
			}
			
			//가능한 최대 합이 없다면 -1출력
			if(max==0) {
				sb.append('#').append(test_case).append(' ').append(-1).append('\n');
			}
			//존재하면 최댓값 max 출력
			else {
				sb.append('#').append(test_case).append(' ').append(max).append('\n');
			}
		}
		System.out.println(sb);
	}
}
