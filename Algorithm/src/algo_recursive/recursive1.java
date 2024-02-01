package algo_recursive;

public class recursive1 {

	public static void main(String[] args) {
		int n = 1;
//		while(n<=5) {
//			System.out.println(n);
//			n++;
//		}
		recursive(n);
	}
	/*
	 * n값을 한번 출력
	 */
	private static void recursive(int n) {
		//종료 조건
		if(n>5) return;
		//반복 파트(inductive part)
		//n을 출력
		System.out.println(n);
//		if(n==5) return;
		//다음 출력은 재귀 호출에 맡김
		recursive(n+1);
	}

}
