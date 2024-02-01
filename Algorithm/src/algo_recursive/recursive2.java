package algo_recursive;

public class recursive2 {
	static int[] arr = {1,3,5,7,9};
//	static int sum = 0;
	public static void main(String[] args) {
		int idx=0;
//		while(idx<arr.length) {
//			System.out.println(arr[idx++]);
//		}
		recursive2(idx,0);
		
	}
//	private static void recursive(int i) {
////		if(i==arr.length) return;
////		System.out.println(arr[i]);
////		recursive(i+1);
//		System.out.println(i);
//		if(i==arr.length) {
//			System.out.println(sum);
//			return;
//		}
//		sum += arr[i];
//		recursive(i+1);
//		System.out.println(i);
//	}
	/*
	 * 배열의 i번째 요소 하나를 sum에 누적하고 다음 요소의 누적은 재귀호출
	 * sum이 
	 * */
	
	private static void recursive2(int i, int sum) {
		if(i==arr.length) {
			System.out.println(sum);
			return;
		}
		//매개변수 값을 바꾸는 것은 x
//		sum+=arr[i];
//		recursive2(i+1,sum);
		recursive2(i+1,sum+arr[i]);
	}

}
