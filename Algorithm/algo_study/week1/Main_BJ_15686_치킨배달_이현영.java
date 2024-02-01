package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 14:48
 * 종료시간: 14:55
 * 
 * 
 * 문제 해석
 * NxN인 도시에서 0은 빈칸, 1은 집, 2는 치킨집
 * 도시에 있는 치킨집 중에서 최대 M개를 고르고, 도시의 치킨 거리가 가장 작게 되는 치킨거리를 출력
 * 
 * 입력
 * 첫째줄: N과 M(고를 치킨집 개수)
 * N개의 줄 : 도시 정보
 * 
 * 출력
 * M개의 치킨집을 골랐을 때, 치킨 거리의 최솟값 출력
 * 
 * 문제해결 프로세스 (조합)
 * 1. 조합으로 총 치킨 집의 개수에서 M개 만큼 치킨집 고름
 * 2. 각 조합의 경우의 수마다 도시의 모든 집까지 치킨 거리 계산 + 치킨거리 저장
 * 3. 치킨 거리의 최솟값 출력
 * 
 * 제한조건
 * N<=50
 * M<=13
 * 1<= 집의 개수 < 2N
 * 
 * 
 * 시간복잡도
 * 집의 개수 * 고른 치킨집 조합의 수 
 * O(2N*13CM) = 
 * 
 */

public class Main_BJ_15686_치킨배달_이현영 {
	static int N, M, min_dis, map[][];
	static Pair chicken[], select[];
	static class Pair{
		int x,y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int [N][N];
		//최대 치킨집 개수
		chicken = new Pair[13];
		//치킨집 추가할 인덱스
		int idx = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				//치킨집 정보 저장
				if (map[i][j]==2) chicken[idx++] = new Pair(i,j);
			}
		}
		chicken = Arrays.copyOf(chicken, idx);
		select = new Pair[M];
		min_dis = Integer.MAX_VALUE;
		comb(0,0);
		System.out.println(min_dis);
	}
	
	public static void comb(int cnt, int start) {
		if(cnt == M) {
			//뽑힌 치킨집들 -> 모든 집과의 치킨거리 계산
			min_dis = Math.min(min_dis, dis(select));
			return;
		}
		//총 치킨집 개수만큼
		for (int i = start; i < chicken.length; i++) {
			select[cnt] = chicken[i];
			comb(cnt+1, i+1);
		}
	}
	
	public static int dis(Pair[] stores) {
		int total = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				//집이면 최소 치킨 거리 구하기
				min = Integer.MAX_VALUE;
				if(map[i][j]==1) {
					for(Pair s: stores) {
						int d = Math.abs(i-s.x)+ Math.abs(j-s.y);
						min = Math.min(min, d);
					}
					total += min;
				}
			}
		}
		return total;
	}

}
