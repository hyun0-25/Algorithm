package ps231012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:23
 * 종료시간:
 * 
 * 
 * 문제 해석
 * N개의 숫자가 적혀있는 게임판
 * +-x/의 연산자 카드를 숫자 사이에 끼워 넣어 다양한 결과 값 구하기
 * 연산자 우선순위 고려 x, 무조건 왼->오 방향으로 계산
 * 결과가 최대가 되는 수식과 최소가 되는 수식을 찾고, 두 값의 차이를 출력
 * 
 * 입력
 * 첫째줄: N숫자개수
 * 다음줄: 각 연산자 개수
 * 		덧뺼곱나 순서
 * 다음줄: 수식에 사용되는 숫자
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 중복순열
 * 
 * 
 * 
 * 제한조건
 * N<=12
 * 연산자수<=11
 * 
 * 
 * 
 * 시간복잡도
 * 11! -> 4천만..
 * 
 * 
 */

public class Solution_4008_숫자만들기_이현영 {
	static int N, operator[], result, number[],select[],num[];
	static int max,min;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		for (int test_case = 1; test_case <= T; test_case++) {
			result = 0;
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			N = Integer.parseInt(br.readLine());
			
			operator = new int[4];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 4; i++) {
				operator[i] = Integer.parseInt(st.nextToken());
			}
			number = new int[N];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				number[i] = Integer.parseInt(st.nextToken());
			}
			select = new int[N-1];
			seloperator(0);
			result = max-min;
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	public static int calresult(int x, int op, int y) {
		switch (op) {
		case 0:
			return x+y;
		case 1:
			return x-y;
		case 2:
			return x*y;
		case 3:
			return x/y;
		default:
			break;
		}
		return 0;
	}
	public static void calculate(int[] sel) {
		int first = number[0];
		for (int i = 0; i < N-1; i++) {
			int oper = sel[i];
			int second = number[i+1];
			first = calresult(first, oper, second);
		}
		if(first > max) max = first;
		if(first < min) min = first;
	}
	public static void seloperator(int cnt) {
		if(!possible(select, cnt)) return;
		
		if(cnt==N-1) {
			calculate(select);
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			select[cnt] = i;
			seloperator(cnt+1);
		}
	}
	
	public static boolean possible(int[] sel, int count) {
		num = new int[4];
		for (int i = 0; i < count; i++) {
			num[sel[i]]++;
			if(num[sel[i]] > operator[sel[i]]) return false;
		}
		return true;
	}

}
