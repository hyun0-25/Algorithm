package algo_recursive;

import java.util.Arrays;
import java.util.Scanner;

public class Permutation {
	
	static int[] arr = {1,3,5,7,9};
	//isSelected[2] = true라면?
	//arr[2]값이 사용중 
	static boolean[] isSelected = new boolean[arr.length];
	static int R;
	static int[] picked;
	public static void main(String[] args) {
		//5P2 뽑아 모든 경우의 수 출력
		//i : 0번째 자리에 담을 요소의 인덱스
//		for(int i=0; i<arr.length; i++) {
//			//0번째 요소 뽑기
//			picked[0]=arr[i];
//			//j : 1번째 자리에 담을 요소의 인덱스
//			for(int j=0; j<arr.length; j++) {
//				//중복 처리
//				if(i==j) continue;
//				//1번째 요소 뽑기
//				picked[1]=arr[j];
//				//k : 2번째 자리에 담을 요소의 인덱스
//				for(int k=0; k<arr.length; k++) {
//					if(i==k || j==k) continue;
//					//2번째 요소 뽑기
//					picked[2]=arr[k];
//					System.out.println(Arrays.toString(picked));
//				}
//			}
//		}
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		picked = new int[R];
		permutation(0);
	}
	/*
	 * arr배열에서 요소 하나 뽑기
	 * @param idx 뽑을 숫자를 담을 인덱스 or 현재까지 뽑은 요소의 개수
	 * */
	private static void permutation(int idx) {
		if(idx==R) {
			System.out.println(Arrays.toString(picked));
			return;
		}
		//inductive part(유도 파트)
		for(int i=0; i<arr.length; i++) {
			//중복 제거
			if(isSelected[i]) continue;
			//요소 뽑기
			picked[idx]=arr[i];
			isSelected[i] = true;
			//다음 숫자 뽑기는 재귀에 넘김
			permutation(idx+1);
			//다음 숫자 뽑기 위해 체크 해제
			isSelected[i] = false;
		}
	}
}
