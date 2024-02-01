package ps230811;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 시작시간: 13:51
 * 종료시간: 13:57
 * 
 * 
 * 문제 해석
 * 신기한 소수는 왼쪽부터 1자리, 2자리, 3자리, 4자리 모두 소수
 * N자리의 숫자 중에서 신기한 소수를 오름차순으로 정렬해서 모두 출력
 * 
 * 입력
 * 첫째줄: N
 * 
 * 출력
 * N자리 수 중에서 신기한 소수를 오름차순으로 정렬해서 하나씩 출력
 * 
 * 문제해결 프로세스
 * 1. 왼쪽 1자리는 2,3,5,7만 가능 -> 4가지
 * 2. 왼쪽 2~N자리는 홀수만 가능 : 1 3 7 9 -> 4가지
 * 3. 만들 수 있는 모든 조합 중 신기한 소수 순차적으로 출력
 * 3-1. 소수 판별 함수 -> isPrime(2~루트(자기자신))까지 나누어지는 숫자 있는지 탐색
 * 
 * 
 * isPrime을 조합에서 자리수가 1개 늘어날때마다 체크해서
 *  안되는 경우는 바로 가지치기하면 빨라지지않을까?
 * 
 * 제한조건
 * N<=8
 * 
 * 
 * 시간복잡도
 * O((4*4^7) * 소수판별(N-1번)) = O
 * 
 * 
 */

public class Main_BJ_2023_신기한소수_이현영 {
	static int N, Selectnums[];
	static int digit1 [] = {2,3,5,7};
	static int digit [] = {1,3,7,9};
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		//고른 N자리수
		Selectnums = new int[N];
		for (int i = 0; i < 4; i++) {
			//왼쪽에서 1번째 자리는 2,3,5,7만 가능
			Selectnums[0] = digit1[i];
			//cnt==1부터 시작
			comb(1, 0);
		}
		System.out.println(sb);
	}
	
	//소수 판단
	public static boolean isPrime(int[] numbers, int idx) {
		int number = 0;
		for (int i = 0; i < idx; i++) {
			number+=numbers[i];
			number*=10;
		}
		number/=10;
		for (int i = 2; i <= Math.sqrt(number); i++) {
			double div = (double)number%i;
			if(div == 0) return false;
		}
		return true;
	}
	
	public static void comb(int cnt, int start) {
		if(cnt==N) {
			//2자리~N자리까지 확인
			if(!isPrime(Selectnums, cnt)) return;
			
			//모든 자리수 소수 -> 정상 탈출
			int total=0;
			for (int i = 0; i < N; i++) {
				total+=Selectnums[i];
				total*=10;
			}
			total/=10;
			sb.append(total).append('\n');
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			Selectnums[cnt] = digit[i];
			if(!isPrime(Selectnums, cnt)) return;
			comb(cnt+1, i);
		}
	}
}
