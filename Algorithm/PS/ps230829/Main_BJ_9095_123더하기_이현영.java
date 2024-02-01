package ps230829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 시작시간: 14:52
 * 종료시간: 14:57
 * 
 * 
 * 문제 해석
 * 정수 4를 1,2,3의 합으로 나타내는 방법은 총 7가지
 * 합을 나타낼 때는 수를 1개 이상 사용
 * 정수 n이 주어졌을 떄, n을 1,2,3의 합으로 나타내는 방법의 수
 * 
 * 
 * 입력
 * 첫째줄: 테스트 케이스 개수 T
 * T개줄: 순서대로 1개씩 테케
 * 
 * 출력
 * 각 테케마다 1,2,3의 합으로 나타내는 방법의 수
 * 
 * 
 * 문제해결 프로세스
 * 점화식
 * f(n) = f(n-1) + f(n-2) + f(n-3)
 * 		    더하기1		더하기2	더하기3
 * f(1)=1
 * f(2)=2
 * f(3)=4
 * f(4)=7
 * 5-> 13
 * 6-> 24
 * 7-> 44
 * 8-> 81
 * 9-> 149
 * 10-> 274
 * 
 * 
 * 제한조건
 * n<=11
 * 
 * 
 * 시간복잡도
 * O(n*n)
 * 
 * 
 */

public class Main_BJ_9095_123더하기_이현영 {
	static int f[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		f = new int [11];
		f[1]=1;
		f[2]=2;
		f[3]=4;
		for (int i = 0; i < T; i++) {
			int number = Integer.parseInt(br.readLine());
			sb.append(count(number)).append('\n');
		}
		System.out.println(sb);
	}
	public static int count(int n) {
		if(f[n]==0) {
			f[n]=count(n-1)+count(n-2)+count(n-3);
		}
		return f[n];
	}

}
