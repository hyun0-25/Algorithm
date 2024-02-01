package ps230814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 16:28
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 흑백 영상을 압축하여 표현하는 데이터 구조인 쿼드 트리
 * 흰 점을 나타내는 0, 검은 점을 나타내는 1로만 이루어진 영상(2차원 배열)에서 
 * 같은 숫자의 점들이 한 곳에 많이 몰려있으면 쿼드 트리는 이를 압축하여 간단히 표현
 * 주어진 영상이 모두 0으로만 되어 있으면 압축 결과는 "0", 모두 1로만 되어 있으면 압축 결과는 "1"
 * 0과 1이 섞여 잇으면 전체를 한번에 나타내지 못하고 좌상,우상,좌하,우하 4개의 영상으로 나누어 압축
 * 4개의 영역을 압축한 결과를 차례대로 괄호 안에 묵어서 표현
 * 
 * 
 * 입력
 * 첫째줄 : 영상의 크기 N (2의 제곱수 1~64)
 * 둘째줄~N개줄 : 길이 N의 문자열 N개 (0 또는 1)
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. size=N 일때  더한 값이 0이면 0, 더한 값이 N*N이면 1추가
 * 2. 소괄호 ()를 재귀 맨앞과 맨뒤에 추가하고, 
 * 	   가로 N/2, 세로 N/2등분하여 더한 값이 0이면 0, 더한 값이 N/2*N/2이면 1추가
 * 3. 2번 반복 
 * 3-1. 기저 조건 size==1
 * 3-2. 기저 조건 더한 값이 0 ("0")
 * 3-3. 기저 조건 더한 값이 size*size ("1")
 * 
 * 
 * 제한조건
 * 1<=N<=64
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_1992_쿼드트리_이현영 {
	static int N, map[][];
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int [N][N];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			char c;
			for (int j = 0; j < N; j++) {
				c = str.charAt(j);
				if(c=='0') map[i][j]=0;
				else map[i][j]=1;
			}
		}
		divideSquare(0,0,N);
		
		System.out.println(sb);
		
	}
	//4등분한 구역의 합
	public static int squareSum(int x, int y, int size) {
		int sum = 0;
		for (int i = x; i < x+size; i++) {
			for (int j = y; j < y+size; j++) {
				sum+=map[i][j];
			}
		}
		return sum;
	}
	/* 문제해결 프로세스
	 * 1. size=N 일때  더한 값이 0이면 0, 더한 값이 N*N이면 1추가
	 * 2. 소괄호 ()를 재귀 맨앞과 맨뒤에 추가하고, 
	 * 	   가로 N/2, 세로 N/2등분하여 더한 값이 0이면 0, 더한 값이 N/2*N/2이면 1추가
	 * 3. 2번 반복 
	 * 3-1. 기저 조건 size==1
	 * 3-2. 기저 조건 더한 값이 0 ("0")
	 * 3-3. 기저 조건 더한 값이 size*size ("1")
	 */
	
	public static void divideSquare(int x, int y, int size) {
		
		
		if(size==1) {
			if(map[x][y]==0) sb.append(0);
			else sb.append(1);
		}
		
		else if(squareSum(x,y,size)==0) sb.append(0);
		else if(squareSum(x,y,size)==size*size) sb.append(1);
		else {
			sb.append('(');
			int half = size/2;
			divideSquare(x,y,half);
			divideSquare(x,y+half,half);
			divideSquare(x+half,y,half);
			divideSquare(x+half,y+half,half);
			sb.append(')');
		}
		
		
	}
}
