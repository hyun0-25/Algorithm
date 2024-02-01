package algo_recursive;

import java.util.Scanner;

public class factorial {
	static int result=1;
	static int n;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
//		int result=1;
//		for(int i=1; i<=n;i++) {
//			result=result*i;
//		}

//		factorial(1);
//		factorial2(n);
//		System.out.println(result);
		System.out.println(factorial3(n));
	}
	/*
	 * result에 i값을 누적시키고 i+1값의 누적은 재귀호출에 넘김
	 * 
	 * */
	private static void factorial(int i) {
		if(i>n) return;
		//반복되는 부분
		result=result*i;
		factorial(i+1);
	}
	private static void factorial2(int i) {
		if(i<1) return;
		//반복되는 부분
		result=result*i;
		factorial2(i-1);
	}
	/*
	 * i! 값을 반환하는 메소드
	 * */
	private static int factorial3(int i) {
		//factorial3(1) == 1;
		if(i==1) return 1;
		return factorial3(i-1)*i;
	}
	

}
