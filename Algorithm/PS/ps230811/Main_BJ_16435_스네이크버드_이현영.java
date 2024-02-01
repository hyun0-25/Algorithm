package ps230811;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:45
 * 종료시간: 13:51
 * 
 * 
 * 문제 해석
 * 스네이크버드가 과일 하나를 먹으면 길이가 1만큼 늘어남
 * 과일들은 지상으로부터 일정 높이를 두고 떨어져 있으며 i번째 과일의 높이는 hi
 * 자신의 길이보다 작거나 같은 높이에 있는 과일들을 먹을 수 있다
 * 처음 길이가 L일때, 과일을 먹어 늘릴 수 있는 최대 길이를 구하여라
 * 
 * 입력
 * 첫번째줄: 과일의 개수 N, 초기 길이 L
 * 두번째줄: h1~hN 과일 높이
 * 
 * 출력
 * 스네이크버드의 최대 길이 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 과일의 높이를 오름차순 정렬
 * 2. 스네이크버드의 길이 L과 0번인덱스부터 순차적으로 비교하며 길이 늘려감
 * 3. 자신의 몸길이보다 더 높은 과일이 나오면 종료
 * 
 * 
 * 제한조건
 * N<=1000
 * L<=10,000
 * h<=10,000
 * 
 * 시간복잡도
 * sort = O(NlogN)
 * 탐색 = O(N)
 * 
 * 따라서 O(NlogN)
 * 
 */

public class Main_BJ_16435_스네이크버드_이현영 {
	static int N,L,h[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		h = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			h[i] = Integer.parseInt(st.nextToken());
		}
		//과일 높이 정렬
		Arrays.sort(h);
		/**
		 * 2. 스네이크버드의 길이 L과 0번인덱스부터 순차적으로 비교하며 길이 늘려감
		 * 3. 자신의 몸길이보다 더 높은 과일이 나오면 종료
		 */
		for (int i = 0; i < N; i++) {
			if(h[i]>L) {
				System.out.println(L);
				return;
			}
			L++;
		}
		System.out.println(L);
	}

}
