package ps231011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 15:25
 * 종료시간:
 * 
 * 
 * 문제 해석
 * N개의 격자점
 * 이 점들을 몇번 움직여 모든 점을 원점(0,0)으로 이동
 * 한번의 움직임은 모든 점을 움직이게 함
 * i번째 움직임에서 각 점은 상하좌우로 i만큼의 거리를 반드시 이동
 * 최소 몇번의 움직임으로 모든 점을 원점으로 모을 수 있는지 구하여라
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. i번째 움직임에서 각 점은 상하좌우 i만큼씩 이동해야하므로
 *  boolean visited[][]
 * 2. bfs로 depth별 탐색
 * boolean visited[][]
 * 	depth = 1 -> 1
 * 			2~3 -> 2
 * 			4~6 -> 3
 * 			7~10 ->4
 * 			11~15 ->5 
 * 			...
 * 3. depth의 마지막값에서 도착지에 왔는지 확인
 * 	도착지면 -> 탈출 
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

public class Solution_8458_원점으로집합_이현영 {
	static int N,result;
	static Queue<Pair> q;
	static List<Pair> list = new ArrayList<>();
	static List<List<Numbers>> numlist = new ArrayList<>();
	static List<Integer> nlist = new ArrayList<>();
	static class Numbers {
		int x,y;
		public Numbers(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static class Pair{
		int x,y,n;
		public Pair(int x, int y, int n) {
			super();
			this.x = x;
			this.y = y;
			this.n = n;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		numlist.add(new ArrayList<>());
		numlist.get(0).add(new Numbers(0,0));
		
		for (int test_case = 1; test_case <= T; test_case++) {
			result = 0;
			q = new ArrayDeque<>();
			N = Integer.parseInt(br.readLine());
			
			//입력 값들이 모두 짝수인지 홀수인지 확인
			boolean iseven = false;
			boolean even = false;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
//				q.offer(new Pair(x,y,i));
				nlist.add(Math.abs(x)+Math.abs(y));
				even = false;
				if(i==0 && Math.abs(x)+Math.abs(y)%2==0) iseven=true;
				if(Math.abs(x)+Math.abs(y)%2==0) even = true;
				if(even!=iseven) {
					result = -1;
					break;
				}
				//else
			}
			if(even==iseven) move();
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	
	/* 문제해결 프로세스
	 * 1. i번째 움직임에서 각 점은 상하좌우 i만큼씩 이동해야하므로
	 *  boolean visited[][]
	 * 2. bfs로 depth별 탐색
	 * boolean visited[][]
	 * 	depth = 1 -> 1
	 * 			2~3 -> 2
	 * 			4~6 -> 3
	 * 			7~10 ->4
	 * 			11~15 ->5 
	 * 			...
	 * 3. depth의 마지막값에서 도착지에 왔는지 확인
	 * 	도착지면 -> 탈출 
	 */
	public static void move() {
		int round = 0;
		while(true) {
			for (int i=0; i<nlist.size();i++) {
				if(nlist.get(i)==0) {
					int r = round%2;
					r += nlist.get(i);
					nlist.set(i, r);
					continue;
				}
				else if(nlist.get(i)==1) {
					int r = round%2;
					if(r==1) {
						nlist.set(i, nlist.get(i)-r);
					}
					continue;
				}
				
				//else
				if(nlist.get(i) >= round) {
					int r = nlist.get(i)-round;
					nlist.set(i, r);
				}
				else {
					int r = round-(nlist.get(i));
					r %= 2;
					nlist.set(i, r);
				}
			}
			System.out.println();
			if(possible()) {
				result = round;
				break;
			}
			round++;
		}
	}
	public static boolean possible() {
		for (int remain : nlist) {
			if(remain!=0) return false;
		}
		return true;
	}
	
	public static void move1() {
		
		int numsize = 0;
			
		while(!q.isEmpty()) {
			if(numlist.size() <= numsize) {
				makelist(numsize);
				continue;
			}
			boolean isexit[] = new boolean[N];
			int cnt=0;
			//depth별로 확인
			int size = q.size();
			for (int i = 0; i < size; i++) {
				Pair p = q.poll();
				int x = p.x;
				int y = p.y;
				for (int j = 0; j < numlist.get(numsize).size(); j++) {
					//i번째 움직임이 가능한 경우들
					int gx = numlist.get(numsize).get(j).x;
					int gy = numlist.get(numsize).get(j).y;
					x += gx;
					y += gy;
					if(x==0 && y==0 && !isexit[p.n]) {
						cnt++;
						break;
					}
					q.offer(new Pair(x,y,p.n));
				}
			}
			if(cnt==N) {
				result = numsize;
				return;
			}
			//else
			numsize++;
		}
	}
	
	public static void makelist(int n) {
		numlist.add(new ArrayList<>());
		int x = n;
		int y = 0;
		while(true) {
			if(x==0) {
				numlist.get(n).add(new Numbers(x,y));
				numlist.get(n).add(new Numbers(x,-y));
				break;
			}
			else if(y==0) {
				numlist.get(n).add(new Numbers(x,y));
				numlist.get(n).add(new Numbers(-x,y));
			}
			else {
				numlist.get(n).add(new Numbers(x,y));
				numlist.get(n).add(new Numbers(-x,y));
				numlist.get(n).add(new Numbers(x,-y));
				numlist.get(n).add(new Numbers(-x,-y));
			}
			x--;
			y++;
		}
	}
}
