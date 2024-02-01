package ps230809;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 풀이 시작 : 13:53
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 배열에  정수 x (0이 아닌)를 넣음 
 * 배열에서 절댓값이 가장 작은 값 출력, 그 값을 배열에서 제거
 * -> 여러개일 때는 가장 작은 수를 출력하고 제거
 * 
 * 
 * 
 * 구해야 하는 것
 * 
 * 
 * 문제 입력
 * 첫째줄 : 연산의 개수 N
 * 둘째줄부터~ : N개의 줄에는 연산 정보 나타내는 정수 x
 *     x!=0이면 배열에 x라는 값을 넣는 연산
 *     x==0이면 배열에서 절댓값이 가장 작은 값 출력, 그 값을 배열에서 제거
 * 
 * 출력
 * 입력에서 0이 주어진 횟수만큼 답을 출력
 * -> 만약 배열이 비어있는 경우인데 절댓값이 가장 작은 값을 출력하라고 한 경우 0을 출력
 * 
 * 
 * 제한 요소
 * -2^31 < x < 2^31 
 * 
 * 생각나는 풀이
 * 최소힙 : 절댓값 기준 정렬
 * pair -> (절댓값,부호)
 * 
 * 
 * 
 * 구현해야 하는 기능
 *
 */


public class Main_BJ_11286_절댓값힙_이현영 {
	static int N;
	public static void main(String[] args) throws IOException {
		
		PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				//1. 절댓값 기준
				//2. 절댓값 같으면 -> 작은값 기준
				if(Math.abs(o1)==Math.abs(o2)) {
					return o1-o2;
				}
				else {
					return Math.abs(o1)-Math.abs(o2);
				}
			}
		});
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int x = Integer.parseInt(br.readLine());
			if (x!=0) {
				heap.add(x);
			}
			//x==0
			else {
				if(heap.isEmpty()) {
					sb.append(0).append('\n');
				}
				else {
					sb.append(heap.poll()).append('\n');
				}
			}
		}
		System.out.println(sb);
		
	}

}
