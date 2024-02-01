package ps230809;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 14:20
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * DNA문자열 = A C G T
 * 임의의 DNA문자열을 만들고 이 문자열의 부분 문자열을 비밀번호로 사용
 * 
 * 부분문자열에 등장하는 문자의 개수가 특정 개수 이상이여야 가능
 * 부분문자열의 길이와 A C G T의 등장 횟수가 주어졌을 때, 만들 수 있는 비밀번호의 종류의 수
 *
 * 구해야 하는 것
 * 만들 수 있는 비밀번호의 종류의 수
 * (부분문자열이 같더라도 등장 위치가 다르면 다른 문자열임)
 * 
 * 문제 입력
 * 첫번째 줄: 문자열 길이 S, 부분문자열 길이 P
 * 두번째 줄: 임의로 만든 문자열 S의 정보
 * 셋째줄: 
 *
 * 제한 요소
 *
 * 생각나는 풀이
 *
 *
 * 구현해야 하는 기능
 *
 */




public class Main_BJ_12891_DNA비밀번호_이현영 {
	static int S,P, sum[], subsum[], count;
	static char  DNA[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		DNA = br.readLine().toCharArray();
		
		//A C G T 순
		sum = new int[4];
		subsum = new int[4];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) {
			sum[i] = Integer.parseInt(st.nextToken());
		}
		//초기 부분수열 A C G T subsum개수
		for (int i = 0; i < P; i++) {
			if(DNA[i]=='A') subsum[0]++;
			else if(DNA[i]=='C') subsum[1]++;
			else if(DNA[i]=='G') subsum[2]++;
			else if(DNA[i]=='T') subsum[3]++;
		}
		isSubstring(subsum);
		
		//S-P+1번 비교
		for (int i = 1; i < S-P+1; i++) {
			//부분문자열 맨앞-1은 제거, 맨뒤는 추가
			deleteChar(DNA[i-1]);
			addChar(DNA[i+P-1]);
			
			isSubstring(subsum);
		}
		System.out.println(count);
		
	}
	public static void deleteChar(char c) {
		if(c=='A') subsum[0]--;
		else if(c=='C') subsum[1]--;
		else if(c=='G') subsum[2]--;
		else if(c=='T') subsum[3]--;
	}
	
	public static void addChar(char c) {
		if(c=='A') subsum[0]++;
		else if(c=='C') subsum[1]++;
		else if(c=='G') subsum[2]++;
		else if(c=='T') subsum[3]++;
	}
	
	
	public static void isSubstring(int [] sub) {
		for (int i = 0; i < 4; i++) {
			if(sub[i]>=sum[i]) continue;
			else return;
		}
		count++;
		return;
	}
}
