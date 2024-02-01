package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 해석
 * NxN 격자
 * 꽃을 심으면 정확히 1년후 꽃이 핌
 * 다음해 식목일부터 꽃길으 ㄹ걸을 수 있다
 * 꽃의 씨앗 3개밖에 없음, 3개의 꽃이 하나도 죽지 않고 1년후 꽃잎이 만개해야함
 * ->어떤 씨앗이 꽃이 핀 뒤 다른 꽃잎과 닿으면 두 꽃 모두 죽음
 * ->화단 밖으로 꽃잎이 나가면 그 꽃은 죽음
 * 화단의 대여 가격은 격자마다 다름
 * 목표 : 서로 다른 3개 씨앗 모두 피게하면서 가장 싼 가격에 화단 대여
 * 
 * 
 * 입력
 * 첫째줄: 화단 한 변의 길이 N
 * 둘째줄~ : N개줄에 N개씩 화단의 지점당 가격
 * 
 * 출력
 * 3개 꽃을 심기 위해 필요한 최소비용
 * 
 * 제한사항
 * 6<=N<=10
 * 
 * 
 * 시간복잡도
 * N*N=100
 * 1. 100C3 -> 100*99*98/6 = 1,000,000/6 < 200,000: 20만
 * 2. 20만 * 5 = 100만
 * 충분..?
 * 
 * 
 * 
 * 문제해결 프로세스
 * 1. 조합으로 NxN 격자에서 3개를 뽑음 
 * 2. 3개뽑은 씨앗을 기준으로 상하좌우 boolean=true
 * 3. 이미 true라면 반복문 탈출, 다음 경우의 수 탐색
 *    문제없이 boolean배열이 생성되었다면 격자의 값 더하는 함수 실행
 * 4. 격자의 값 더하는 함수에서 최솟값 갱신
 * 5. 최솟값 출력
 * 
 * 
 */


public class 꽃길 {
	static int N, min, map[][];
	static Pair index[], select[];
	static boolean isLeaf[][];
	
	static class Pair{
		private int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		min = Integer.MAX_VALUE;
		map = new int[N][N];
		
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
//				index[i*N+j] = new Pair(i,j);
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int idx=0;
		index = new Pair[(N-2)*(N-2)];
        //맵 테두리는 어짜피 못심음
		for (int i = 1; i < N-1; i++) {
			for (int j = 1; j < N-1; j++) {
				index[idx++] = new Pair(i,j);
			}
		}
		select = new Pair[3];
		comb(0,0);
		System.out.println(min);
	}
	

	public static void comb(int cnt, int start) {
		if(cnt==3) {
			Flower(select);
			return;
		}
		for (int i = start; i < (N-2)*(N-2); i++) {
			select[cnt]=index[i];
			comb(cnt+1,i+1);
		}
	}



	public static void Flower(Pair[] pair) {
		isLeaf = new boolean[N][N];
		int [] dx = {1,-1,0,0};
		int [] dy = {0,0,1,-1};
		for(Pair p : pair) {
			if(isLeaf[p.x][p.y]) {
				return;
			}
			isLeaf[p.x][p.y]=true;
			for (int k = 0; k < 4; k++) {
				int x = p.x+dx[k];
				int y = p.y+dy[k];
				if(x<0 || x>=N || y<0 || y>=N || isLeaf[x][y]) {
					return;
				}
				else {
					isLeaf[x][y]=true;
				}
			}
		}
		//꽃 3개 다 심음
		int result = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(isLeaf[i][j]) {
					result += map[i][j];
					if(result>min) {
						return;
					}
				}
			}
		}
		min = Math.min(min, result);
		
	}
}
