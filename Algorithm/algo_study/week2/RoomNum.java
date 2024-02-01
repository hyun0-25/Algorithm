package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 숫자를 구매하기 위해 준비한 금액 M원
 * 문방구에서 파는 숫자는 0~N-1
 * 각 숫자의 가격은 2번째 줄
 * 같은 숫자 여러 개 구매 가능
 * 방 번호가 0이 아니라면 0으로 시작 불가 -> 가장 싼 가격 1개만 구매 가능할 때
 * 
 * 문제 해결 프로세스
 * 1. 가장 싼 금액으로 최대한 많은 자리 수 구매
 * 2. 1개씩 그 다음 싼 금액의 번호로 교체
 * 3. 반복
 * 
 * 
 * **하지만 자리 수가 2 이상인데 전부 0이라면? = 1개도 교체하지 못했다면?**
 * 
 */



public class RoomNum {
	static int N, M;
	static Pair p[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			p[i]= new Pair(i, Integer.parseInt(st.nextToken()));
		}
	}
	static class Pair {
		private int idx, price;

		public Pair(int idx, int price){
			this.idx=idx;
			this.price=price;
		}
	}

}
