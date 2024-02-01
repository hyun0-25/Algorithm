package ps230830;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:30
 * 종료시간: 11:33
 * 
 * 
 * 문제 해석
 * 도시를 동,서로 나누는 큰 일직선 모양의 강이 흐름
 * 서: N개 사이트, 동: M개 사이트 (N<=M)
 * 서-동 사이트를 다리로 연결 (한 사이트에는 최대 한개의 다리만 연결 가능
 * 서쪽은 모두 선택해야함
 * 다리끼리는 서로 겹칠 수 없을 때, 다리를 지을 수 있는 경우의 수
 * 
 * 입력
 * 첫째줄: 테케수 T
 * T개줄: 각 N과 M
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * mCn의 수 -> n이 m/2보다 크면 -> n=m-n으로 교체
 * nCm = nCm-n
 * 
 * 제한조건
 * N,M<=30
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_1010_다리놓기_이현영 {
	static int N,M;
	static int result[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		result = new int[31][31];
		for (int i = 0; i <= 30; i++) {
			result[i][0]=1;
			result[i][i]=1;
		}
		for (int test_case = 0; test_case < T; test_case++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			sb.append(pascal(M,N)).append('\n');
		}

		System.out.println(sb);
	}
	
	/* 문제해결 프로세스
	 * 파스칼 삼각형
	 * (직접 조합 mCn을 계산하면 시간초과남, 최대 30C15=1억5천)
	 */
	public static int pascal(int m, int n) {
		if(result[m][n]==0) {
			result[m][n] = pascal(m-1,n-1) + pascal(m-1,n);
		}
		return result[m][n];
	}
}
