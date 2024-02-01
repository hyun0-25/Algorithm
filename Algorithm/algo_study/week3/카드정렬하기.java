package week3;

/**
 * n1,n2,n3,..,n100이라고하면 (틀림)
 * n1는 N-1번
 * n2는 N-1번
 * n3는 N-2번
 * ...
 * nN는 1번 더해진다
 * ---------------------------
 * i와 i+1번째를 
 * 
 * 따라서 오름차순 정렬 후 더하면됨
 * 
 * 
 * 시간복잡도
 * O(N
 * 
 * 
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 카드정렬하기 {
	static int N, card[], sum[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		card = new int [N];
		for (int i = 0; i < N; i++) {
			card[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(card);
		//짝수면 마지막 값 0
		//홀수면 마지막 값 최댓값 1개만
		sum = new int[N/2+1];
		for (int i = 0; i < N; i+=2) {
			if(i+1==N) {
				sum[i/2] = card[i];
			}
			else {
				sum[i/2] = card[i]+card[i+1];
			}
		}
//		System.out.println(Arrays.toString(sum));
		int result=0;
		for (int i = 0; i < N/2+1; i++) {
			result+=(sum[i]*(N/2+1-i));
		}
		System.out.println(result);

	}
}
