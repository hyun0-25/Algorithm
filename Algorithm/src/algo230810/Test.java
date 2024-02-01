package algo230810;

import java.util.Arrays;

public class Test {
	static int [] arr = {6,1,3,7,9,2};
	public static void main(String[] args) {
		Arrays.sort(arr);
		
		do {
			//사용
			System.out.println(Arrays.toString(arr));
		}while(np(arr));
	}
	/**
	 * 해당 arr 배열로 만들 수 있는 다음오로 큰 숫자 만들어줌
	 * @param arr
	 * @return true 경우 다음 순열 만들기 성공, false면 더 이상 만들 수 있는 큰 숫자 없음
	 */
	
	
	private static boolean np(int[] arr) {
		int N = arr.length;
		//step 01.
		//뒤에서부터 연속하는 두 자리 수 (i-1,i)를 비교했을 때,
		//오른쪽 (i) 큰 경우 탐색
		int i = N-1;
		while(i>0 && arr[i-1] >= arr[i]) i--; 
		
		//현재 가장 큰 순열인 상태 -> 더이상 순열 만들 수 없음
		if(i==0) return false;
		
		//1 2 7 9 6 3
		//arr[i-1]=7, arr[i]=9
		//j탐색, 7보다 큰 숫자의 위치
		
		int j = N-1;
		while(arr[i-1]>=arr[j]) j--;
		//arr[j]=9
		
		//arr[i-1], arr[j]스왑
		swap(arr, i-1, j);
		//1 2 9 7 6 3
		
		//arr[i] 요소부터 끝까지를 오름차순 정렬
		int k = N-1;
		while(i < k) swap(arr, i++ ,k--);
		//1 2 9 3 6 7
		
		//순열 만들기 성공
		return true;
	}
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = arr[i];
	}

}
