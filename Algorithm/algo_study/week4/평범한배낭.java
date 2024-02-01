package week4;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 9:17
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 여행에 필요한 N개 물건
 * 각 물건은 무게 W와 가치 V
 * 해당 물건을 배낭에 넣어서 가면 준서가 V만큼 즐ㄹ길 수 있다
 * 최대 K만큼의무게만을 넣을 수 있는 배낭
 * 배낭에 넣을 수 있는 물건들의 가치의 최댓값
 * 
 * 입력
 * 첫째줄: 물품의 수 N, 버틸 수 있는 무게 K
 * N개줄: 각 물건의 무게 W, 가치V
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 100_000_000
 * 1- 부분집합 -시간초과 날거같음
 * 2- DP -어떻게?
 * 		k값을 넘지 않는 것 입력 받고, dp[>=k]의 최댓값 찾기
 * 일단 weight기준으로 sort
 * dp[i] = dp[
 * 바텀업을 해야할 것 같음
 * 
 * 제한조건

 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class 평범한배낭 {
	static int N,K, weight[], value[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
	}

}
