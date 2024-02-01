package ps230822;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:17
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 사무실은 1X1 크기의 정사각형으로 나누어져 있는 NxM크기의 직사각형
 * 사무실에는 총 K개의 CCTV설치, 5가지 종류
 * 1번: 1방향
 * 2번: 2방향, 서로반대
 * 3번: 2방향, 직각
 * 4번: 3방향, 한쪽 빼고 
 * 5번: 4방향, 전부
 * 감시하는 방향에 있는칸 전체를 감시할 수 있음
 * 벽을 통과할 수 없음
 * CCTV가 감시할 수 없는 영역 - 사각지대
 * CCTV는 회전 가능 - 90도 방향만 가능, 감시하려고 하는 방향이 가로 또는 세로여야함
 * 
 * 0: 빈칸
 * 6: 벽
 * 1~5: CCTV번호
 * CCTV는 CCTV 통과 가능
 * 
 * 입력
 * 첫째줄: N,M
 * N개줄: 사무실 각 칸의 정보
 * 
 * 
 * 출력
 * CCTV방향을 적절히 정해서 사각 지대의 최소 크기
 * 
 * 문제해결 프로세스
 * CCTV1 - 4가지
 * CCTV2 - 2가지
 * CCTV3 - 4가지
 * CCTV4 - 4가지
 * CCTV5 - 1가지
 * 총 4*2*4*4*1 = 128가지 CCTV 배치 경우의 수 가능 -> 완전탐색?
 * 
 * 1. 조합으로 CCTV1~4의 상태를 결정하는 배치 선택(어느방향 볼건지), CCTV5는 고정임
 * 2. 1번에서 뽑은 조합을 바탕으로 CCTV를 배치하여 감시 가능 구역 map[i][j]='#'로 바꾸기
 * 3. 2번의 결과로 0의 개수(사각지대) 세고, 최솟값 갱신
 * 4. 2~3번 반복하여 최솟값 구하기
 * 
 * cctv(int[] ) //int[]에는 cctv마다 감시할 방향 0,1,2,3담겨있음
 * 	for(int x: int[])
 * 		switch(x)
 * 			상,하,좌,우: #처리
 * 
 * 함수: FOR문으로 switch문 CCTV의 상,하,좌,우 어디를 감시하는지 변수로 받기
 * 
 * 
 * 제한조건
 * N,M<=8
 * CCTV 최대개수<=8
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_15683_감시_이현영 {
	static int N, M, map[][], map2[][];
	static int min = Integer.MAX_VALUE;
	//상, 우, 하, 좌 순(시계방향)
	static int dx [] = {-1, 0, 1, 0};
	static int dy [] = {0, 1, 0, -1};
	static int cctv1[][]= {{0},{1},{2},{3}};
	static int cctv2[][]= {{0,2},{1,3}};
	static int cctv3[][]= {{0,1},{1,2},{2,3},{3,0}};
	static int cctv4[][]= {{0,1,2},{1,2,3},{2,3,0},{3,0,1}};
	static int cctv5[][] = {{0,1,2,3}};
	static int select[][];
	static ArrayList<Pair> cctvs = new ArrayList<>();
	
	static class Pair {
		int x,y,num;
		public Pair(int x, int y, int num) {
			super();
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]!=0 && map[i][j]!=6) cctvs.add(new Pair(i,j,map[i][j]));
			}
		}
		
		map2 = new int[N][M];
		//cctv1~5의 상태 선택
		select = new int [cctvs.size()][];
		pick(0);
		
		System.out.println(min);
	}
	
	public static int[][] sel_cctv(int n){
		if(n==1) return cctv1;
		else if(n==2) return cctv2;
		else if(n==3) return cctv3;
		else if(n==4) return cctv4;
		else return cctv5;
	}
	
	public static void pick(int cnt) {
		if(cnt==cctvs.size()) {
			func(select);
			return;
		}
		int l = cctvs.get(cnt).num;
		int [][] c = sel_cctv(l);
		for(int i=0; i < c.length; i++) {
			select[cnt] = c[i];
			pick(cnt+1);
		}
	}
	/* 문제해결 프로세스
	 * 
	 * 1. 조합으로 CCTV1~4의 상태를 결정하는 배치 선택(어느방향 볼건지), CCTV5는 고정임
	 * 2. 1번에서 뽑은 조합을 바탕으로 CCTV를 배치하여 감시 가능 구역 map[i][j]='#'로 바꾸기
	 * 3. 2번의 결과로 0의 개수(사각지대) 세고, 최솟값 갱신
	 * 4. 2~3번 반복하여 최솟값 구하기
	 * 
	 * cctv(int[] ) //int[]에는 cctv마다 감시할 방향 0,1,2,3담겨있음
	 * 	for(int x: int[])
	 * 		switch(x)
	 * 			상,하,좌,우: #처리
	 * 
	 * 함수: FOR문으로 switch문 CCTV의 상,하,좌,우 어디를 감시하는지 변수로 받기
	 */
	
	public static void func(int sel[][]) {
		for (int i = 0; i < N; i++) {
			map2[i] = Arrays.copyOf(map[i], map[i].length);
		}
		//cctv 1~5 감시
		for (int i = 0; i < sel.length; i++) {
			//cctv i번째 감시 시작
			for(int dir: sel[i]) {
				int x = cctvs.get(i).x;
				int y = cctvs.get(i).y;
				watch(x,y,dir);
			}
		}
		
		int cnt=0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map2[i][j]==0) cnt++;
			}
		}
		min = Math.min(min, cnt);
	}
	
	public static void watch(int x, int y, int d) {
		switch(d) {
		case 0:
			//상 방향 -> i=x-1~0
			for (int i = x-1; i >= 0; i--) {
				if(map2[i][y]!=6) map2[i][y]=-1;
				else {
					break;
				}
			}
			break;
		case 1:
			//우 방향 -> j=y+1~M-1
			for (int i = y+1; i < M; i++) {
				if(map2[x][i]!=6) map2[x][i]=-1;
				else {
					break;
				}
			}
			break;
			
		case 2:
			//하 방향 -> i=x+1~N-1
			for (int i = x+1; i < N; i++) {
				if(map2[i][y]!=6) map2[i][y]=-1;
				else {
					break;
				}
			}
			break;
		case 3:
			//좌 방향 -> j=y-1~0
			for (int i = y-1; i >= 0; i--) {
				if(map2[x][i]!=6) map2[x][i]=-1;
				else {
					break;
				}
			}
			break;
		}
	}
}
