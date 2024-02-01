package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * N개의 돌이 일렬로 나열되어 있는 강가
 * 마지막 돌 틈 사이에 산삼이 존재
 * 산삼을 캐기 위해 돌과 돌사이를 점프
 * 점프 종류 3가지
 * 작은점프: 현재 위치에서 다음돌로 이동
 * 큰점프: 1개의 돌을 건너뛰어 이동
 * 매우 큰 점프: 2개의 돌을 건너뛰어 이동
 * 
 * 각 점프를 할 때는 에너지를 소비
 * 소비되는 에너지는 점프를 하는 돌의 번호마다 다름
 * 매우 큰 점프는 단 1번의 기회, 점프하는 돌의 번호와 상관없이 k만큼의 에너지를 소비
 * 
 * 에너지를 최대한 아껴서 산삼을 얻기 위해 필요한 에너지의 최솟값
 * 
 * 
 * 입력
 * 첫째줄: 돌의 개수 N
 * N-1개줄: 1번~N-1번 돌까지의 작은점프하기 위한 에너지, 큰점프하기 위한 에너지
 * 마지막줄: K (단 1번 매우큰 점프의 에너지)
 * 
 * 
 * 출력
 * 
 * 
 * 
 * 문제해결 프로세스
 * dp[i]는 i번째 돌까지 소비한 에너지
 * 
 * 점화식
 * 현 위치는 dp[i] = min(dp[i-1]+s(i-1),dp[i-2]+l[i-2])
 * 그러면 k는 언제 사용?
 * k를 사용할 위치를 먼저 정하자!
 * idx_k=-1(사용x), 3~N-1
 * 그리고 DP를 이용해서 idx_k, idx_k+1, idx_k+2 건너뜀, dp[idx_k+3]=dp[idx_k]+K
 * 주의 -> idx_k+4에서 i-2와 비교는 존재하지 않음
 * 
 * 이 중에서 dp[n]의 최솟값 출력
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class 징검다리건너기 {
	static int N,K;
	static int dp[], small[],large[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		small = new int[N-1];
		large = new int[N-1];
		
		for (int i = 0; i < N-1; i++) {
			st = new StringTokenizer(br.readLine());
			small[i] = Integer.parseInt(st.nextToken());
			large[i] = Integer.parseInt(st.nextToken());
		}
		K = Integer.parseInt(br.readLine());
		
		int min = Integer.MAX_VALUE;
		//K사용안함
		min = Math.min(min, DP_k(-1));
		
		//K사용
		for (int i = 3; i < N; i++) {
			min = Math.min(min, DP_k(i));
		}
		System.out.println(min);
	
	}
	
	 /* 문제해결 프로세스
	 * dp[i]는 i번째 돌까지 소비한 에너지
	 * 
	 * 점화식
	 * 현 위치는 dp[i] = min(dp[i-1]+small(i-1),dp[i-2]+large[i-2])
	 * 
	 * 그러면 k는 언제 사용?
	 * k를 사용할 위치를 먼저 정하자!
	 * idx_k=-1(사용x), 3~N-1
	 * 그리고 DP를 이용해서 idx_k-1, idx_k-2 건너뜀, dp[idx_k]=dp[idx_k-3]+K
	 * 주의 -> idx_k+4에서 i-2와 비교는 존재하지 않음
	 * 
	 * 이 중에서 dp[n]의 최솟값 출력
	 */
	
	public static int DP_k(int idx) {
		int dp[] = new int[N];
		if(N!=1) dp[1] = dp[0]+small[0];
		for (int i = 2; i < N; i++) {
			//k 점프할때
			if(i==idx) {
				dp[i]=dp[i-3]+K;
			}
			//k 점프 다음 인덱스는 i-2와 비교 불가
			else if(i==idx+1) {
				dp[i] = dp[i-1]+small[i-1];
			}
			else {
				dp[i] = Math.min(dp[i-1]+small[i-1], dp[i-2]+large[i-2]);
			}
		}
		return dp[N-1];
	}
}
