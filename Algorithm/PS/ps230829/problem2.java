package ps230829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 시작시간: 13:11
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 1cm짜리 파란 막대, 1cm짜리 노란 막대, 2cm짜리 빨간 막대
 * 이 막대들을 연결하여 길이가 n인 막대를 만드는 방법의 수 f(n)
 * 
 * 2cm = 파+파
 * 		파+노
 * 		노+파
 * 		노+노
 * 		빨
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 점화식
 *  f(n+1) = f(n)*2 + f(n-1)*1
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class problem2 {
	static int f[] = new int [100];
	public static void main(String[] args) throws IOException {
		f[1] = 2;
		f[2] = 5;
		
		System.out.println(length(6));
	}
	public static int length(int num) {
		if(f[num]==0) {
			f[num] = length(num-1)*2 + length(num-2);
		}
		return f[num];
	}

}
