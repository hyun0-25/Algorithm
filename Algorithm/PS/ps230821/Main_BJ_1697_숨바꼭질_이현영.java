package ps230821;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:19
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 걷거나 순간이동 가능
 * 위치가 X일때 걷는다면 1초 후 -> X-1 또는 X+1
 * 			순간이동 하는 경우 -> 2*X
 * 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하여라
 * 
 * 
 * 입력
 * 수빈이가 있는 위치 N, 동생이 있는 위치 K
 * 
 * 출력
 * 동생을 찾는 가장 빠른 시간
 * 
 * 문제해결 프로세스
 * dp? X
 * 점화식 dp(i) = min(dp(i*2), dp(i-1), dp(i+1))+1
 * 
 * bfs O
 * 
 * 
 * 제한조건
 * N<=100,000
 * K<=100,000
 * 
 * 
 * 시간복잡도
 * 
 * 
 */


public class Main_BJ_1697_숨바꼭질_이현영 {
	static int N,K;
	static int dp[] = new int[100_001];
	static boolean isVisited[] = new boolean[100_001];
	static Queue<Integer> queue = new ArrayDeque<>();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		
		dp[N]=0;
		isVisited[N]=true;
		
		minimum(N);
		
		int next=0;
		while(!queue.isEmpty()) {
			next = queue.poll();
			minimum(next);
			if(next==K) break;
		}
		System.out.println(dp[K]);
		
	}
	
	//누나의 위치의 (dp(i*2), dp(i-1), dp(i+1))+1
	public static void minimum(int idx) {
		if(idx==0) {
			dp[idx] = Math.min(dp[idx], dp[idx+1]+1);

			if(idx+1<=100_000 && !isVisited[idx+1]) {
				isVisited[idx+1]=true;
				dp[idx+1]=dp[idx]+1;
				queue.add(idx+1);
			}
		}
		else {
			if(idx-1<=100_000 && !isVisited[idx-1]) {
				isVisited[idx-1]=true;
				dp[idx-1]=dp[idx]+1;
				queue.add(idx-1);
			}
			if(idx+1<=100_000 && !isVisited[idx+1]) {
				isVisited[idx+1]=true;
				dp[idx+1]=dp[idx]+1;
				queue.add(idx+1);
			}
			if(idx*2<=100_000 && !isVisited[idx*2]) {
				isVisited[idx*2]=true;
				dp[idx*2]=dp[idx]+1;
				queue.add(idx*2);
			}
		}
	}
	
}