package algo230822_DisjointSet;

import java.util.Arrays;

public class DisjointSetTest {
	static int N;  //초기 집합의 개수
	static int parents[];
	public static void make() {
		parents = new int[N];
		for (int i = 0; i < N; i++) {
			parents[i] = i; //모든 요소는 자기만 원소로 갖는 단위 서로소 집합이 되게함 (자신이 곧 대표자인 루트로 표현)
		}
	}
	
	public static int find(int a) {
		if(a==parents[a]) return a;
		return parents[a] = find(parents[a]);
	}
	
	public static boolean union(int a, int b) { //a가 속한 집합과 b가 속한 집합을 합칠 수 있다면 합치고 true반환, 아니면 false반환
		int aRoot = find(a);
		int bRoot = find(b);
		if(aRoot == bRoot) return false; //서로의 대표자가 같은 즉, 같은 집합의 상황이므로 union하지 않음
		//union 처리 (bRoot를 aRoot 아래로 붙이기 : 임의로)
		parents[bRoot] = aRoot;
		return true;
		
	}
	public static void main(String[] args) {
		N = 5;
		make();
//		System.out.println("find(0) : "+find(0));
//		System.out.println("find(1) : "+find(1));
//		System.out.println("find(2) : "+find(2));
//		System.out.println("find(3) : "+find(3));
//		System.out.println("find(4) : "+find(4));
		System.out.println(Arrays.toString(parents));
		System.out.println(union(0,1));
		System.out.println(Arrays.toString(parents));
		System.out.println(union(2,1));
		System.out.println(Arrays.toString(parents));
		System.out.println(union(3,2));
		System.out.println(Arrays.toString(parents));
		System.out.println(union(4,3));
		System.out.println(Arrays.toString(parents));
		
		System.out.println("=========find==========");
		System.out.println(find(4));
		System.out.println(Arrays.toString(parents));
		System.out.println(find(3));
		System.out.println(Arrays.toString(parents));
		System.out.println(find(2));
		System.out.println(Arrays.toString(parents));
		System.out.println(find(1));
		System.out.println(Arrays.toString(parents));
		System.out.println(find(0));
		System.out.println(Arrays.toString(parents));
	
	}

}
