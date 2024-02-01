package week3;

/**
 * n1,n2,n3,..,n100이라고하면 (틀림)
 * n1는 N-1번
 * n2는 N-1번
 * n3는 N-2번
 * ...
 * nN는 1번 더해진다
 * ---------------------------
 * i와 i+1번째를 (틀림)
 * 
 * 
 * ---------------------------
 * 우선순위 큐를 이용
 * 가장 작은것 두개씩 n-1번 더하자
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
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 카드정렬하기_2 {
	static int N;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> q = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			q.offer(Integer.parseInt(br.readLine()));
		}
		int sum=0;
		int n1=0,n2=0;
		for (int i = 0; i < N-1; i++) {
			n1 = q.poll();
			n2 = q.poll();
			q.offer(n1+n2);
			sum+=n1+n2;
		}
		System.out.println(sum);
	}
}
