package ps230830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:33
 * 종료시간: 11:42
 * 
 * 
 * 문제 해석
 * RGB거리에는 집 N개 존재
 * 거리는 선분, 1~N번 집이 순서대로 있음
 * 집은 빨,초,파 중 하나로 색을 칠해야함
 * 각 집을 빨,초,파로 칠하는 비용이 주어졌을 때 규칙을 만족하며 모든 집을 칠하는 비용의 최솟값
 * 규칙
 * - 1번집의 색은 2번집의 색과 같지 않아야함
 * - N번집의 색은 N-1번집의 색과 같지 않아야함
 * - i번집의 색은 i-1번, i+1번 집의 색과 같지 않아야함
 * 따라서 인접한 집의 색은 서로 같지 않아야함

 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 빨-k=0
 * 초-k=1
 * 파-k=2
 * 
 * dp[i] = dp[i-1]+color[i-1][!pre] => 틀림
 * 
 * 빨 -> 파,초 중 최소
 * 파 -> 빨,초 중 최소
 * 초 -> 빨,파 중 최소
 * 
 * 3가지 색에 대해 모두 dp 테이블 만들어야됨
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_1149_RGB거리_이현영 {
	static int N, color[][], min;
	static int dp[];
	static int pre[];
	static int [] oc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		min = Integer.MAX_VALUE;
		N = Integer.parseInt(br.readLine());
		color = new int[N+1][3];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			color[i][0] = Integer.parseInt(st.nextToken());
			color[i][1] = Integer.parseInt(st.nextToken());
			color[i][2] = Integer.parseInt(st.nextToken());
		}
		house();
		System.out.println(Math.min(color[N][0], Math.min(color[N][1], color[N][2])));
		
	}
	
	public static void house() {
		for (int i = 2; i <= N; i++) {
			color[i][0] += Math.min(color[i-1][1], color[i-1][2]);
			color[i][1] += Math.min(color[i-1][0], color[i-1][2]);
			color[i][2] += Math.min(color[i-1][0], color[i-1][1]);
		}
	}
}
