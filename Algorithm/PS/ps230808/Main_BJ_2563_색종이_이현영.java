package ps230808;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 13:44
 * 풀이 완료 : 13:54
 * 풀이 시간 :
 *
 * 문제 해석
 * 가로x세로 = 100x100 정사각형 흰색 도화지
 * 10x10 정사각형 모양의 검은색 작은 색종이를 붙일때
 * 
 * 
 * 구해야 하는 것
 * 검은 영역의 넓이
 * 
 * 문제 입력
 * 첫째줄 : 색종이의 수 N
 * 둘째줄부터 ~ : 색종이를 붙인 위치 (왼쪽 아래 모서리 i,j)
 * 
 * 제한 요소
 * N<=100
 * 
 * 생각나는 풀이
 * 1. 100x100 크기의 boolean배열을 만들기
 * 2. 10x10 검은 종이를 붙이는 영역을 true로
 *    이미 true라면 중복으로 붙이는 경우니까 continue;
 * 3. 출력 = true의 개수
 * 
 * 시간복잡도
 * 검은 색종이 넓이 = 10*10
 * 색종이 최대 개수 = 100
 * 
 * for n = 1 ~ 100
 * 	for i ~ i+10
 *    for j ~ j+10
 *    	boolean
 * for n = 1 ~ 100
 * 	for n = 1 ~ 100
 *    true -> cnt++
 *    
 * -> 100*10*10 + 100*100 = 20000 충분?
 *
 * 구현해야 하는 기능
 * 
 */

public class Main_BJ_2563_색종이_이현영 {
	static int N, black[][];
	static boolean map [][] = new boolean[100][100];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		black = new int[N][2];
		StringTokenizer st;
		for (int k = 0; k < N; k++) {
			st = new StringTokenizer(br.readLine());
			black[k][0]=Integer.parseInt(st.nextToken());
			black[k][1]=Integer.parseInt(st.nextToken());
			int x = black[k][0];
			int y = black[k][1];
			for (int i = x; i < x+10; i++) {
				for (int j = y; j < y+10; j++) {
					map[i][j]=true;
				}
			}
		}
		int cnt=0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(map[i][j]) cnt++;
			}
		}
		System.out.println(cnt);
		
	}

}
