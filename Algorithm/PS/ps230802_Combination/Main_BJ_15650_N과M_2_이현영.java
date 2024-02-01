package ps230802_Combination;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_BJ_15650_N과M_2_이현영 {
	public static int N,M;
	public static int [] arr;
	public static void C(ArrayList<Integer> list, int idx, int cnt) {
		if(cnt==0) {
			for(int x:list) {
				System.out.print(x+" ");
			}
			System.out.println();
			return;
		}
		for(int i=idx; i<N; i++) {
			if(list.contains(arr[i])) {
				continue;
			}
			//list 안에 arr[i]없으면 추가
			list.add(arr[i]);
			C(list, i+1, cnt-1); //뽑을 개수 -1
			list.remove(list.size()-1); //마지막에 넣은 원소 제거
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		arr = new int[N];
		for(int i=1; i<=N; i++) {
			arr[i-1]=i;
		}
		C(new ArrayList<Integer>(), 0, M);
	}
}
