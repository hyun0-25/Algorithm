package A형대비.ps230825;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 12:41
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 정사각형 모양의 5종류 색종이 1x1,2x2,3x3,4x4,5x5 
 * 각 종류별 5장씩
 * 10x10인 종이 위에 붙이려고 할 때, 
 * 각 칸에는 0 또는 1이 적혀있음
 * 1이 적힌 칸은 모두 색종이로 덮여져야함
 * 붙일 때는 종이의 경계 밖으로 나가면 안되고, 겹쳐도 안됨
 * 칸의 경계와 일치하게 붙여야함
 * 0이 적힌 칸에는 색종이가 있으면 안됨
 * 모든 칸을 붙이는데 필요한 색종이의 최소 개수
 * 
 * 
 * 입력
 * 총 10x10 칸의 적힌 수
 * 
 * 출력
 * 모든 1을 덮는데 필요한 색종이의 최소 개수
 * 1을 모두 덮는 것이 불가능한 경우 -1 출력
 * 
 * 
 * 문제해결 프로세스
 * paper1~5는 각 5장씩
 * paper[] = {0,0,0,0,0};
 * 
 * 1. 5x5부터 넣을 수 있는 칸이 있는지 시도 ((0,0)~(9,9)까지 완탐)
 *  1-1. 넣을 수 있다면 
 *  	paper[i]에 5를 넘지 않는지 확인
 *  	바로 5x5넣고 그 범위 모두 0으로 바꿈 -> paper[i]--;
 *  1-2. 넣을 수 없다면 continue;
 * 2. 1번 1x1까지 시도 했는데 남은 1이 존재하면 -1 출력
 * 	   전부 0이면 paper[i]값 1~5색종이 모두 더한 값 출력
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


public class Main_BJ_17136_색종이붙이기 {
	static int map[][] = new int[10][10];
	static int paper[] = new int[5+1];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		for (int i = 0; i < 10; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 10; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 5; i >= 1; i--) {
			func(i);
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(map[i][j]==1) {
					//붙일 수 있는 경우의 수 x
					System.out.println(-1);
					System.exit(0);
				}
			}
		}
		//붙인 색종이 수 출력
		int total=0;
		for (int i = 1; i <= 5; i++) {
			total+=paper[i];
		}
		System.out.println(total);
		
	}
	
	/* 문제해결 프로세스
	 * paper1~5는 각 5장씩
	 * paper[] = {0,0,0,0,0};
	 * 
	 * 1. 5x5부터 넣을 수 있는 칸이 있는지 시도 ((0,0)~(9,9)까지 완탐)
	 *  1-1. 넣을 수 있다면 
	 *  	paper[i]에 5를 넘지 않는지 확인
	 *  	바로 5x5넣고 그 범위 모두 0으로 바꿈 -> paper[i]--;
	 *  1-2. 넣을 수 없다면 continue;
	 * 2. 1번 1x1까지 시도 했는데 남은 1이 존재하면 -1 출력
	 * 	   전부 0이면 paper[i]값 1~5색종이 모두 더한 값 출력
	 */
	
	//n은 색종이 한변의 길이 1~5
	public static void func(int n) {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				//1이 적혀 있으면 
				if(map[i][j]==1) {
					//종이 이미 다 사용
					if(paper[n]==5) return;
					//종이 사용 가능하면 붙일 수 있는지 체크
					isPossible(i,j,n);
				}
			}
		}
	}
	
	//x,y 기준으로 x~x+n,y~y+n범위가 모두 1인지 체크
	public static void isPossible(int x, int y, int n) {
		//맵 밖으로 넘어감
		int endx = x+n-1;
		int endy = y+n-1;
		if(endx>=10 || endy>=10) return;
		
		//isPossible == false면
		//return;
		for (int i = x; i < x+n; i++) {
			for (int j = y; j < y+n; j++) {
				if(map[i][j]==0) return;
			}
		}
		
		//정상 통과
		//isPossible == true면
		//그 구역 색종이 붙힘
		paper[n]+=1;
		for (int i = x; i < x+n; i++) {
			for (int j = y; j < y+n; j++) {
				map[i][j] = 0;
			}
		}
	}
}
