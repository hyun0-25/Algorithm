package ps231011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:48
 * 종료시간:
 * 
 * 문제 해석
 * 판에는 4개의 자석, 각 자석은 8개의 날을 가짐
 * 각 날마다 N 또는 S극의 자성
 * 
 *  규칙
 *  임의의 자석을 1칸씩 K번 회전할때
 *  하나의 자석이 1칸 회전하면, 붙어있는 자석은 서로 붙어있는 날의 자성과 다를경우에만 반대방향으로 1칸 회전
 *  모든 회전이 끝난 후 점수 계산
 *  	1번 자석은 화살표 위치에 날이 N극이면 0/S극이면 1
 *  	2번 자석은 화살표 위치에 날이 N극이면 0/S극이면 2
 *  	3번 자석은 화살표 위치에 날이 N극이면 0/S극이면 4
 *  	4번 자석은 화살표 위치에 날이 N극이면 0/S극이면 8
 *  4개 자석의 자성 정보와 자석을 1칸씩 K번 회전 -> K번 회전 후 획득하는 점수의 총합
 * 
 * 입력
 * 첫째줄 : K회전횟수
 * 4개줄: 1~4번자석 8개의 날 자성정보
 * 	N극:0/S극:1
 * K개줄: 회전시키려는 자석의 번호+회전방향
 * 	회전방향 1:시계/-1:반시계
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 각 자석의 화살표 위치 저장 point[]
 * 2. 오른쪽 자석과 닿는 위치는 point[]값+2 / 왼쪽 자석과 닿는 위치는 point[]값-2
 * 3. 해당 자석 기준 -> 왼쪽 자석들은 0~i-1까지/ 오른쪽 자석들은 i+1~3까지 -> 회전 하지 않으면 break;
 * 		회전할 결과 배열 turn[1, -1, ,,] 생성
 * 		시계방향 회전이면 turn[]--;
 * 		반시계 회전이면 turn[]++;
 * 		결과 -> point[]+=turn[];
 * 
 * 
 * 제한조건
 * K<=20
 * 하나의 자석이 1칸 회전->붙어있는 자석은 서로 붙어있는 날의 자성이 다를 경우에만 반대로 1칸회전
 * 날의 자성정보는 화살표위치의 날부터 시계방향으로 순서대로 주어짐
 * 
 * 시간복잡도
 * 
 * 
 */

public class Solution_4013_특이한자석_이현영 {
	static int K, magnet[][],result, points[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			K = Integer.parseInt(br.readLine());
			result = 0;
			magnet = new int[4][8];
			points = new int[4];
			StringTokenizer st;
			for (int i = 0; i < 4; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < 8; j++) {
					magnet[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int magnetnum = Integer.parseInt(st.nextToken())-1;
				int direction = Integer.parseInt(st.nextToken());
				rotate(magnetnum, direction);
			}
			calculate();
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	 /* 문제해결 프로세스
	 * 1. 각 자석의 화살표 위치 저장 point[]
	 * 2. 오른쪽 자석과 닿는 위치는 point[]값+2 / 왼쪽 자석과 닿는 위치는 point[]값-2
	 * 3. 해당 자석 기준 -> 왼쪽 자석들은 0~i-1까지/ 오른쪽 자석들은 i+1~3까지 -> 회전 하지 않으면 break;
	 * 		회전할 결과 배열 turn[1, -1, ,,] 생성
	 * 		시계방향 회전이면 turn[]--;
	 * 		반시계 회전이면 turn[]++;
	 * 		결과 -> point[]+=turn[];
	 */
	public static void calculate() {
		int total = 0;
		for (int i = 0; i < 4; i++) {
			if(magnet[i][points[i]]==1) total+=Math.pow(2, i);
		}
		result = total;
	}
	public static void rotate(int num, int dir) {
		//회전 결과 배열
		int turn[] = new int[4];
		turn[num] = -dir;
		
		//왼쪽 
		for (int i = num-1; i >= 0; i--) {
			int next = points[i];
			int nridx = next+2;
			if(nridx>=8) nridx-=8;
			
			int before = points[i+1];
			int blidx = before-2;
			if(blidx<0) blidx+=8;
			
			if(magnet[i][nridx]==magnet[i+1][blidx]) break;
			//else
			if(turn[i+1]==1) turn[i]=-1;
			else turn[i]=1;
		}
		
		//오른쪽
		for (int i = num+1; i < 4; i++) {
			int next = points[i];
			int nlidx = next-2;
			if(nlidx<0) nlidx+=8;
			
			int before = points[i-1];
			int bridx = before+2;
			if(bridx>=8) bridx-=8;
			
			if(magnet[i][nlidx]==magnet[i-1][bridx]) break;
			
			if(turn[i-1]==1) turn[i]=-1;
			else turn[i]=1;
		}
		for (int i = 0; i < 4; i++) {
			points[i]+=turn[i];
			if(points[i]>=8) points[i]-=8;
			else if(points[i]<0) points[i]+=8;
		}
	}

}
