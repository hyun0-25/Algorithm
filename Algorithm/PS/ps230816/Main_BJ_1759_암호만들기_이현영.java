package ps230816;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 15:12
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 암호는 서로 다른 L개의 알파벳 소문자들로 구성
 * 최소 1개 모음(a e i o u)과 최소 2개 자음으로 구성
 * 문자열 정렬
 * C가지의 문자의 종류가 주어졌을 때, 가능한 암호들을 모두 구함
 * 
 * 
 * 입력
 * 첫째줄: 암호 길이 L, 문자의 종류 C
 * 둘째줄: C개의 문자들
 * 
 * 
 * 출력
 * 가능한 암호 모두 출력
 * 
 * 
 * 문제해결 프로세스 1
 * 1. 모음, 자음 분리
 * 2. 모음 1개/자음 2개 를 뽑고, 나머지 L-3개는 모음 자음 합쳐서 선택
 * 3. 오름차순 정렬
 * 4. 출력
 * 
 * 문제해결 프로세스 2
 * 1. 오름차순 정렬 후, 조합으로 L개 선택
 * 2. 모음 최소 1개, 자음 최소2개 가지고 있는 지 판별
 * 3. 2번 조건을 만족하지 않으면 출력 X
 * 			    만족하면 출력 O
 * 
 * 제한조건
 * 3<=L,C<=15
 * 
 * 
 * 시간복잡도
 * 최대 15개 중 7개 뽑고, 각 자리 수 모음,자음 검증
 * O(15C7*7)
 * 
 */



public class Main_BJ_1759_암호만들기_이현영 {
	static int L,C;
	static char alpa[], select[];
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		alpa = new char[C];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < C; i++) {
			alpa[i] = st.nextToken().charAt(0);
		}
		
		Arrays.sort(alpa);
		select = new char[L];
		comb(0,0);
		System.out.println(sb);
	}
	
	public static void comb(int cnt, int start) {
		if(cnt==L) {
			if(isKey(select)) {
				for(char c : select) {
					sb.append(c);
				}
				sb.append('\n');
			}
			return;
		}
		for (int i = start; i < C; i++) {
			select[cnt] = alpa[i];
			comb(cnt+1, i+1);
		}
	}
	
	public static boolean isKey(char [] sel) {
		int cnt=0;
		for (int i = 0; i < L; i++) {
			if(isAEIOU(sel[i])) cnt++;
		}
		if(cnt>=1 && L-cnt>=2) return true;
		return false;
		
	}
	
	public static boolean isAEIOU(char c) {
		if(c=='a' || c=='e' || c=='i' || c=='o' || c=='u') return true;
		return false;
	}
	

}
