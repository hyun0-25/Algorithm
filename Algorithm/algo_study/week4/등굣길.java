package week4;

import java.util.Iterator;

/**
 * 시작시간: 22:42
 * 종료시간: 22:50
 * 정답시간:
 * 
 * 문제 해석
 * 물이 잠기지 않은 지역을 통해 학교를 가려고함
 * 집에서 학교까지 가는 길은 mxn 크기의 격자모양
 * 가장 왼쪽 위가 집의 좌표(1,1), 가장 오른쪽 아래가 학교(m,n)
 * 오른쪽과 아래쪽으로만 움직여 집에서 학교까지 갈 수 있는 최단경로의 개수를 
 * 1,000,000,007로 나눈 나머지를 return
 * 
 * 입력
 * 격자의 크기 m,n
 * 물이 잠긴 지역 puddles
 * 
 * 출력
 * 집에서 학교까지 갈 수 있는 최단경로의 개수를 
 * 1,000,000,007로 나눈 나머지
 * 
 * 문제해결 프로세스
 * dp(i,j) 좌표마다 집으로 부터 올 수 있는 경로의 개수를 저장
 * = 왼쪽과 위쪽 경로의 합
 * 점화식 : dp(i,j) = dp(i-1,j)+dp(i,j-1)
 * -----------------------------------------
 * 물에 잠긴 지역이나 맵 밖을 참조하는 경우는 dp값 안불러옴
 * 
 * 
 * 제한조건
 * m,n<=100
 * puddles.size<=10
 * 
 * 시간복잡도
 * O(m*n) 충분한듯!
 * 
 */

public class 등굣길 {
	
	public static void main(String[] args) {
		int [][] p = {};
		System.out.println(solution(4,3,p));
//		solution(4,3,p);
	}
	
	 /* 문제해결 프로세스
	 * dp(i,j) 좌표마다 집으로 부터 올 수 있는 경로의 개수를 저장
	 * = 왼쪽과 위쪽 경로의 합
	 * 점화식 : dp(i,j) = dp(i-1,j)+dp(i,j-1)
	 * -----------------------------------------
	 * 물에 잠긴 지역이나 맵 밖을 참조하는 경우는 dp값 안불러옴
	 */
	
	static int dp[][], M, N;
	public static long solution(int m, int n, int[][] puddles) {
		M=m;
		N=n;
        dp = new int[m][n];
        
        //물에 잠긴 지역은 -1
        for (int i = 0; i < puddles.length; i++) {
			int x = puddles[i][0]-1;
			int y = puddles[i][1]-1;
			dp[x][y]=-1;
		}

        dp[0][0]=1;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
            	//물에 잠긴 지역은 계산 안함
            	if(i==0 && j==0) continue;
                if(dp[i][j]==-1) continue;
                //정상적인 길 = 왼쪽+위
                dp[i][j] = isRoad(i-1,j)+isRoad(i,j-1);
            }
        }
        return dp[m-1][n-1]%1000000007;
    }
	 public static int isRoad(int x, int y) {
	        //(맵 밖이거나) 물에 잠긴 지역은 0 
	        if(x<0 || x>=M || y<0 || y>=N || dp[x][y]==-1) return 0;
	        //else
	        return dp[x][y]%1000000007;
	 }

}
