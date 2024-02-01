package ps231010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 시작시간: 11:46
 * 종료시간: 11:56
 * 
 * 
 * 문제 해석
 * 총 25명의 여학생 -> 5x5격자로 자리 배치
 * 
 * 소문난칠공주 결성
 * 1. 7명의 여학생들로 구성
 * 2. 7명은 가로나,세로로 반드시 인접
 * 3. S로만 구성될 필요는 없음
 * 4. 그러나 S는 4명이상이어야함
 * 칠공주를 결성할 수 있는 모든 경우의 수
 * 
 * 입력
 * S,Y의 5X5행렬
 * 
 * 출력
 * 칠공주를 결성할 수 있는 모든 경우의 수
 * 
 * 문제해결 프로세스
 * 1. 조합으로 7개 위치 선택
 * 	1-1. S개수  4보다 작으면 바로 탈출 
 * 2. 7개 노드 연결여부 확인 -> list
 * 3. list의 첫번째 노드 꺼내고, 큐에 4방인접 노드 있으면 넣음 + S개수 세기
 * 	3-1. 큐에서 꺼내면서 list가 empty면 result++;
 * 	3-2. 		list에 원소가 남아있거나 S가 4보다 작으면 실패
 * 4. result출력
 * 
 * 
 * 제한조건
 * 25C7 = 48만
 * 연결 여부 -> 연결리스트 4(4방)*6(자신제외노드탐색) = 24
 * 충분한듯?
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_1941_소문난칠공주_이현영 {
	static char map[][] = new char[5][5];
	static int select[];
	static int result;
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 5; i++) {
			String str = br.readLine();
			for (int j = 0; j < 5; j++) {
				map[i][j] = str.charAt(j);
			}
		}
		select = new int[7]; 
		comb(0,0,0);
		System.out.println(result);
	}
	/* 문제해결 프로세스
	 * 1. 조합으로 7개 위치 선택
	 * 	1-1. S개수  4보다 작으면 바로 탈출 
	 * 2. 7개 노드 연결여부 확인 -> list
	 * 3. list의 첫번째 노드 꺼내고, 큐에 4방인접 노드 있으면 넣음 + S개수 세기
	 * 	3-1. 큐에서 꺼내면서 list가 empty면 result++;
	 * 	3-2. 		list에 원소가 남아있거나 S가 4보다 작으면 실패
	 * 4. result출력
	 */
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	public static void connect(int[] sel) {
		boolean isvisited [] = new boolean[7];
		Queue<Pair> q = new ArrayDeque<>();
		q.offer(new Pair(sel[0]/5, sel[0]%5));
		isvisited[0] = true;
		int cnt = 1;
		
		while(!q.isEmpty()) {
			Pair p = q.poll();
			int x = p.x;
			int y = p.y;
			for (int i = 0; i < 7; i++) {
				for (int k = 0; k < 4; k++) {
					int nx = x+dx[k];
					int ny = y+dy[k];
					int idx = nx*5+ny;
					if(!rangecheck(nx,ny) || isvisited[i]) continue;
					if(sel[i]==idx) {
						q.offer(new Pair(nx,ny));
						isvisited[i] = true;
						cnt++;
					}
				}
			}
		}
		if(cnt==7) result++;
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<5 && ry>=0 && ry<5;
	}
	public static void comb(int cnt, int start, int Ynum) {
		if(cnt==7) {
			connect(select);
			return;
		}
		for (int i = start; i < 5*5; i++) {
			select[cnt] = i;
			int Yn = Ynum;
			if(map[i/5][i%5]=='Y') Yn++;
			if(Yn>3) continue;
			//else
			comb(cnt+1, i+1, Yn);
		}
	}

}
