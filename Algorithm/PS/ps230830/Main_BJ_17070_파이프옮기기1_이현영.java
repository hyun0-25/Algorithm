package ps230830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 파이프를 밀어서 이동
 * 우 - 우
 * 	    우하
 * 하 - 우하
 * 	      하
 * 우하 - 우
 * 		우하
 * 		하
 * 긁는 벽지 : 우 - 우
 * 		     우하 - 우,우하,하
 * 			하 - 하
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * k= 0, 1, 2
 *    우,우하,하 순서
 * dp[i][j]  =  dp[i][j][0]  +  dp[i][j][1]  +   dp[i][j][2]
 * 		    dp[i-1][j] k=0,1  dp[i-1][j-1] k=0,1,2  dp[i][j-1] k=1,2
 * 
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

public class Main_BJ_17070_파이프옮기기1_이현영 {
	static int N, dp[][][], map[][];
	//우,우하,하
	static int dx [] = {0, 1, 1};
	static int dy [] = {1, 1, 0};		
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new int[N][N][3];
		StringTokenizer st;
		
		map = new int [N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(i==0 && j==1) dp[0][1][0]=1; //시작 위치
					
				if(!(i==0 && j==1) && map[i][j]==0) {
					//우
					if(i>=0 && i<N && j-1>=0 && j-1>=0)
						//긁 - 우
						if(map[i][j-1]==0) {
							dp[i][j][0] = dp[i][j-1][0] + dp[i][j-1][1];
						}
					//우하
					if(i-1>=0 && i-1<N && j-1>=0 && j-1>=0)
						//긁 - 우, 우하, 하
						if(map[i-1][j-1]==0 && map[i-1][j]==0 && map[i][j-1]==0) {
							dp[i][j][1] = dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2];
						}
					//하
					if(i-1>=0 && i-1<N && j>=0 && j>=0)
						//긁 - 하
						if(map[i-1][j]==0) {
							dp[i][j][2] = dp[i-1][j][1] + dp[i-1][j][2];	
						}
				}
			}
		}
		System.out.println(dp[N-1][N-1][0] + dp[N-1][N-1][1] + dp[N-1][N-1][2]);
	}
}
