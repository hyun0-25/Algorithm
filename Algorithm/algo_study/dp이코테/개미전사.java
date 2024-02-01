package dp이코테;

import java.util.Scanner;

/**
 * 문제 해석
 * 식량창고는 일직선으로 이어져있음
 * 각 식량창고에는 정해진 수의 식량을 저장, 식량창고를 선택적으로 약탈하여 식량을 빼앗음
 * 정찰병들은 일직선상에 존재하는 식량창고 중에서 서로 인접한 식량창고가 공격받으면 바로 알아챔
 * 정찰병에게 들키지 않고 식량창고를 약탈하려면 최소 1칸이상 떨어진 식량창고를 약탈해야함
 * 
 * 1 3 1 5
 * 2번째 4번째 선택하면 최댓값인 8개의  식량 빼앗음
 * 
 * 입력
 * 첫째줄: 식량창고 개수 N
 * 둘째줄: 식량창고에 저장된 식량의 개수 K
 * 
 * 출력
 * 식량창고 N개에서 얻을 수 있는 식량의 최댓값
 * 
 */


public class 개미전사 {
	static int[] d = new int[100];
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int [] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		d[0] = arr[0];
		d[1] = Math.max(arr[0], arr[1]);
		//d(i) = max(d(i-1), d(i-2)+ arr(i))
		
		for (int i = 2; i < n; i++) {
			d[i] = Math.max(d[i-1], d[i-2]+arr[i]);
		}
		System.out.println(d[n-1]);
		
	}

}
