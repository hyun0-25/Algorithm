package ps231011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:09
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN 크기의 절벽지대에 활주로를 건설
 * 셀의 숫자는 그 지형의 높이
 * 활주로를 가로 또는 세로 방향으로 건설하는 가능성을 확인
 * 활주로는 높이가 동일한 구간에서 건설 가능
 * 높이가 다른 구간의 경우 활주로가 끊어지기 때문에, 경사로를 설치해야만 활주로를 건설할 수 있음
 * 경사로는 길이가 X고 높이는 1 (X는 2이상 6이하)
 * 경사로는 높이 차이가 1이고 낮은 지형의 높이가 동일하게 경사로의 길이만큼 연속되는 곳에 설치 가능
 * 
 * 
 * 입력
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 0~N-1까지 활주로 건설할 수 있는지 for문
 * 2. 높이가 다른 구간 발견시
 * 	2-1. 높이차이가 2 이상이면, 건설불가 -> break;
 * 	2-2. 높이 차이가 1이면
 * 		높이가 -1이면-> 같은 높이가 앞으로 연속 X번 발생+활주로 건설한 적없으면 가능
 * 		높이가 +1이면-> 같은 높이가 전으로 연속 X번 발생+활주로 건설한 적없으면 가능
 * 3. 활주로를 건설한 위치는 boolean[][]으로 체크하기
 * 4. 끝까지 건설가능한 개수 ++;
 * 
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

public class Solution_4014_활주로건설_이현영 {
	static int N,X,map[][],result;
	static boolean isbuild[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			result = 0;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			row();
			col();
			sb.append('#').append(test_case).append(' ').append(result).append('\n');
		}
		System.out.println(sb);
	}
	/* 문제해결 프로세스
	 * 1. 0~N-1까지 활주로 건설할 수 있는지 for문
	 * 2. 높이가 다른 구간 발견시
	 * 	2-1. 높이차이가 2 이상이면, 건설불가 -> break;
	 * 	2-2. 높이 차이가 1이면
	 * 		높이가 -1이면-> 같은 높이가 앞으로 연속 X번 발생+활주로 건설한 적없으면 가능
	 * 		높이가 +1이면-> 같은 높이가 전으로 연속 X번 발생+활주로 건설한 적없으면 가능
	 * 3. 활주로를 건설한 위치는 boolean[][]으로 체크하기
	 * 4. 끝까지 건설가능한 개수 ++;
	 */
	public static void row() {
		isbuild = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			int j = 0;
			boolean impossible = false;
			while(true) {
				int ny = j+1;
				//범위 밖 -> 끝에 도달
				if(!rangecheck(ny)) {
					result++;
					break;
				}
				
				//같은 높이 이동
				if(map[i][ny]==map[i][j]) {
					j = ny;
					continue;
				}
				//else
				//높이차 2 이상-> 건설불가
				if(Math.abs(map[i][ny]-map[i][j])>=2) break;
				
				//높아짐
				else if(map[i][ny]-map[i][j]==1) {
					int height = map[i][j];
					
					for (int k = j; k > j-X; k--) {
						if(!rangecheck(k) || map[i][k]!=height || isbuild[i][k]) {
							impossible = true;
							break;
						}
					}
					if(impossible) break;
					//건설 성공
					for (int k = j; k > j-X; k--) {
						isbuild[i][k]=true;
					}
					j = ny;
				}
				//낮아짐
				else if(map[i][ny]-map[i][j]==-1) {
					int height = map[i][ny];
					
					for (int k = ny; k < ny+X; k++) {
						if(!rangecheck(k) || map[i][k]!=height || isbuild[i][k]) {
							impossible = true;
							break;
						}
					}
					if(impossible) break;
					//건설 성공
					for (int k = ny; k < ny+X; k++) {
						isbuild[i][k]=true;
						j = k;
					}
				}
			}
		}
	}
	
	public static void col() {
		isbuild = new boolean[N][N];
		for (int j = 0; j < N; j++) {
			int i = 0;
			boolean impossible = false;
			while(true) {
				int nx = i+1;
				//범위 밖 -> 끝에 도달
				if(!rangecheck(nx) ) {
					result++;
					break;
				}
				
				//같은 높이 이동
				if(map[nx][j]==map[i][j]) {
					i = nx;
					continue;
				}
				//else
				//높이차 2 이상-> 건설불가
				if(Math.abs(map[nx][j]-map[i][j])>=2) break;
				
				//높아짐
				else if(map[nx][j]-map[i][j]==1) {
					int height = map[i][j];
					
					for (int k = i; k > i-X; k--) {
						if(!rangecheck(k) || map[k][j]!=height || isbuild[k][j]) {
							impossible = true;
							break;
						}
					}
					if(impossible) break;
					//건설 성공
					for (int k = i; k > i-X; k--) {
						isbuild[k][j]=true;
					}
					i = nx;
				}
				//낮아짐
				else if(map[nx][j]-map[i][j]==-1) {
					int height = map[nx][j];
					
					for (int k = nx; k < nx+X; k++) {
						if(!rangecheck(k) || map[k][j]!=height || isbuild[k][j]) {
							impossible = true;
							break;
						}
					}
					if(impossible) break;
					//건설 성공
					for (int k = nx; k < nx+X; k++) {
						isbuild[k][j]=true;
						i = k;
					}
				}
			}
		}
	}
	public static boolean rangecheck(int idx) {
		return idx>=0 && idx<N;
	}
}
