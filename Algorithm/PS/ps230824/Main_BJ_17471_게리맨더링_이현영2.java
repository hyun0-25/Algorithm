package ps230824;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:18
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 최대한 공평하게 선거구를 획정
 * N개의 구역으로 나누어져 있고, 구역은 1~N번까지 번호
 * 구역을 두개의 선거구로 나눠야하고, 각 구역은 두 선거구 중 하나에 포함
 * 선거구는 구역을 적어도 하나 포함
 * 한 선거구에 포함되어 있는 구역은 모두 연결
 * 구역 A에서 인접한 구역을 통해서 구역 B로 갈 수 있을때, 두 구역은 연결되어 있음
 * 중간에 통하는 인접한 구역은 0개 이상, 모두 같은 선거구에 포함된 구역이어야함
 * 두 선거구에 포함된 인구의 차이를 최소로하여 최솟값 출력
 * 
 * 입력
 * 첫째줄: 구역의 개수 N
 * 둘째줄: 구역의 인구 1~N번까지 순서대로
 * 셋째줄부터 N개줄: 각 구역과 인접한 구역의 정보
 * 
 * 각 정보의 첫번째 정수- 그 구역과 인접한 구역의 수
 * 			    이후 - 인접한 구역의 번호
 * 
 * 출력
 * 두 선거구의 인구 차이의 최솟값을 출력
 * 2개로 나눌 수 없는 경우 -1출력
 * 
 * 문제해결 프로세스
 * 1. 한개 노드는 무조건 한 구역에 속해 있으니까 1번 노드를 시작으로 인접한 노드들 부분집합 탐색
 * 2. 시작 노드에 연결되어 있지 않은 노드들(isSelected=false인 노드들)이 모두 연결되어 있는지 확인
 * 	2-1. 연결되어 있으면 (isConnected 함수=true) 인구합 a,b선거구 모두 구함 -> 인구 차이 최솟값 갱신
 * 	2-2. 연결되어 있지 않으면 (isConnected 함수=false) -> return;
 * 3. 최솟값 출력
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_17471_게리맨더링_이현영2 {
	static int N, nums[], total, true_cnt, false_cnt;
	static ArrayList<Integer> list[];
	static boolean isSelected[];
	static int min = Integer.MAX_VALUE;
	static ArrayList<Integer> exista = new ArrayList<>();
	static ArrayList<Integer> existb = new ArrayList<>();
	static Queue<Integer> q1 = new ArrayDeque<>();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		
		list =  new ArrayList[N];
		nums = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		
		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			for (int j = 0; j < n; j++) {
				list[i].add(Integer.parseInt(st.nextToken())-1);
			}
		}
		isSelected = new boolean[N];
		powerset(0);
		if(min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}
		else{
			System.out.println(min);
		}
		
	}
	/* 문제해결 프로세스
	 * 1. 한개 노드는 무조건 한 구역에 속해 있으니까 1번 노드를 시작으로 인접한 노드들 부분집합 탐색
	 * 2. 시작 노드에 연결되어 있지 않은 노드들(isSelected=false인 노드들)이 모두 연결되어 있는지 확인
	 * 	2-1. 연결되어 있으면 (isConnected 함수=true) 인구합 a,b선거구 모두 구함 -> 인구 차이 최솟값 갱신
	 * 	2-2. 연결되어 있지 않으면 (isConnected 함수=false) -> return;
	 * 3. 최솟값 출력
	 */
	
	public static void powerset(int cnt) {
		if(cnt==N || total==Math.pow(2, N-1)) {
			total++;
			
			true_cnt = 0;
			false_cnt = 0;
			for (int i = 0; i < N; i++) {
				if(isSelected[i]) true_cnt++;
				if(!isSelected[i]) false_cnt++;
			}
			if(true_cnt==N || false_cnt==N) return;
			//A와 B모두 연결
			if(isConnectedA(isSelected) && isConnectedB(isSelected)){
				cal(isSelected);
			}
			return;
		}
		powerset(cnt+1);
		isSelected[cnt]=true;
		powerset(cnt+1);
		isSelected[cnt]=false;
	}
	
	public static boolean isConnectedA(boolean[] A) {
		exista.clear();
		int start = 0;
		for (int i = 0; i < N; i++) {
			if(!A[i]) {
				start = i;
			}
		}
		q1.clear();
		exista.add(start);
		q1.offer(start);
		while(!q1.isEmpty()) {
			int x = q1.poll();
			//x와 인접해있는 노드들
			for (int a : list[x]) {
				if(!A[a] && !exista.contains(a)) {
					exista.add(a);
					q1.offer(a);
				}
			}
		}
		
		boolean [] copya = new boolean[N];
		copya = Arrays.copyOf(A, A.length);
		for(int a : exista) {
			if(copya[a]) return false;
			else {
				copya[a]=true;
			}
		}
		
		for (int i = 0; i < N; i++) {
			if(!copya[i]) return false;
		}
		return true;

	}
	
	public static boolean isConnectedB(boolean[] B) {
		existb.clear();
		int start = 0;
		for (int i = 0; i < N; i++) {
			if(B[i]) {
				start = i;
			}
		}
		q1.clear();
		existb.add(start);
		q1.offer(start);
		while(!q1.isEmpty()) {
			int x = q1.poll();
			//x와 인접해있는 노드들
			for (int a : list[x]) {
				if(B[a] && !existb.contains(a)) {
					existb.add(a);
					q1.offer(a);
				}
			}
		}
		boolean [] copyb = new boolean[N];
		copyb = Arrays.copyOf(B, B.length);
		for(int b : existb) {
			if(!copyb[b]) return false;
			else {
				copyb[b]=false;
			}
		}
		for (int i = 0; i < N; i++) {
			if(copyb[i]) return false;
		}
		return true;

	}
	
	public static void cal(boolean[] AB) {
		int suma=0, sumb=0;
		for (int i = 0; i < N; i++) {
			//false면 A구역
			if(!AB[i]) {
				suma+=nums[i];
			}
				
			//true면 B구역
			else {
				sumb+=nums[i];
			}
		}
		min = Math.min(min, Math.abs(suma-sumb));

	}
}
