package SW역량테스트.ps230904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 21:55
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 가로 w, 세로 h인 2차원 격자
 * 왼쪽 아래가 (0,0) 오른족 위가 (w,h)
 * (p,q)에ㅐ 개미 한마리 -> 개미는 오른쪽 위 45도 방향으로 일정한 속력으로 움직임
 * 1시간 후 (p+1,q+1)로 옮겨김
 * 단, 속력으로 움직이다가 경계면에 부딪치면 같은 속력으로 반사되어 움직임
 * 
 * 
 * 입력
 * 첫째줄: w h
 * 둘째줄: 개미 시작위치 (x,y)
 * 셋재줄: t시간
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 
 * 우하 - 행 막힘: 우상 1
 * 		 열 막힘: 좌하 3
 * 		행열 막힘: 좌상 2
 * 
 * 우상 - 행 막힘: 우하 0
 * 		 열 막힘: 좌상 2
 * 		행열 막힘: 좌하 3
 * 
 *  좌상 - 행 막힘: 우상 1
 * 		 열 막힘: 좌하 3
 * 		행열 막힘: 우하 0
 * 
 * 좌하 - 행 막힘: 좌상 2
 * 		 열 막힘: 우하 0
 * 		행열 막힘: 우상 1
 *
 * 우하, 우상, 좌상 좌하
 * 0	1	2	3
 * 
 * x: 4 5 6 5 4 3 2 1 0 1 2 3 ...
 * y: 1 2 3 4 3 2 1 0 1 2 3 4 ...
 * 
 * x: 5 6 5 4 3 2 1 0 1 2 3 ...
 * y: 3 4 3 2 1 0 1 2 3 4 1 ...
 * 제한조건
 * t<=2억
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_10158_개미 {
	//가로, 세로
	static int w,h;
	static int x,y,t;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		
		t = Integer.parseInt(br.readLine());
		int remainx = t%(2*w);
		int remainy = t%(2*h);
		
		if(x+remainx<=w) {
//			x+=remainx;
			sb.append(remainx+x);
		}
		else {
			x = Math.abs(2*w-(x+remainx));
			sb.append(x);
		}
		sb.append(' ');
		if(y+remainy<=h) {
//			y+=remainy;
			sb.append(remainy+y);
		}
		else {
			y = Math.abs(2*h-(y+remainy));
			sb.append(y);
		}
		System.out.println(sb);
	}
	 

}
