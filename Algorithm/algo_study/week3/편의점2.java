package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 입력: n / map-> 고객들의 위치 n줄
 *  n<=100,000
 *  x,y = -1,000,000 ~ 1,000,000
 * 
 * 문제 해결 프로세스 1 (시간 초과)
 * 1. 가능한 편의점의 위치 = (x의 최소~최대, y의 최소~최대) 
 * 2. for i 
 * 		for j
 * 			dis 계산
 * 3. 출력: dis의 최솟값 
 * ----------------------------
 * 문제 해결 프로세스 2 (틀림)
 * (x의 평균, y의 평균 좌표)
 * 시간복잡도 O(n+n) = O(20만)
 * ---------------------------- 
 * 문제 해결 프로세스 3 (시간 초과)
 * x값 차가 최소가 되는 x좌표
 * y값 차가 최소가 되는 y좌표 각각 구하기
 * O(X범위*n+Y범위*n)=O(2백만*n+2백만*n)=O(4*10^11)
 * ----------------------------
 * 문제 해결 프로세스 4
 * x값들의 중앙값
 * y값들의 중앙값
 * 시간복잡도 O(n+n) = O(20만)
 * 
 * 
 * 시간 복잡도 
 * 완전 탐색? (시간초과)
 *  i x j = 4*10^12
 * 1억 = 10^8 ...
 * 
 */

public class 편의점2 {
	static int n, x[], y[];
	static int max_x, min_x, max_y, min_y;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		x = new int [n];
		y = new int [n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			x[i]=Integer.parseInt(st.nextToken());
			y[i]=Integer.parseInt(st.nextToken());
		}
		Arrays.sort(x);
		Arrays.sort(y);
		//x와 y의 중앙값 구하기
		int mid_x=0, mid_y=0;
		//짝수 -> 가운데 두개의 평균
		//    n/2 또는 n/2-1 또는 두개의 평균 중 어떤걸로 해도 같은 결과임
		if(n%2==0) {
			mid_x = (x[n/2]+x[n/2-1])/2;
			mid_y = (y[n/2]+y[n/2-1])/2;
		}
		//홀수 -> n/2번째 값
		else {
			mid_x = x[n/2];
			mid_y = y[n/2];
		}
		
		//x,y범위가 -1백만~1백만이라 거리합이 int를 넘어섬
		long dis=0;
		for (int k = 0; k < n; k++) {
			int nx = x[k];
			int ny = y[k];
			dis += Math.abs(nx-mid_x)+Math.abs(ny-mid_y);
		}
		
		System.out.println(dis);
	}
}