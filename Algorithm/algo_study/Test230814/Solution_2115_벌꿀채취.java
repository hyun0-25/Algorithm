package Test230814;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 시작시간: 6:24
 * 종료시간: 6:40
 * 
 * 
 * 문제 해석
 * NxN개의 벌통이 정사각형 모양으로 배치
 * 각 칸의 숫자는 각각의 벌통에 있는 꿀의 양
 * 벌꿀 채취과정 (최대한 많은 수익 내야함)
 * 1. 2명의 일꾼, 꿀을 채취할 수 있는 벌통의 수 M
 * 	각 일꾼은 가로로 연속되도록 M개의 벌통을 선택, 선택한 벌통에서 꿀을 채취
 * 	2명의 일꾼이 선택한 벌통은 서로 겹치면 안됨
 * 2. 2명의 일꾼은 선택한 벌통에서 꿀을 채취하여 용기에 담아야함
 * 	단, 서로 다른 벌통에서 채취한 꿀이 섞이면 안됨
 * 	하나의 벌통에서 채취한 꿀은 하나의 용기에 담아야함
 * 	하나의 벌통에서 꿀을 채취할 때, 일부분만 채취할 수 없고 벌통에 있는모든 꿀을 한번에 채취해야함
 * 	2명이 채취할 수 있는 꿀의 최대 양은 C
 * 3. 하나의 용기에 있는 꿀의 양이 많을 수록 상품가치가 높음
 * 	각 용기에 있는 꿀의 양의 제곱만큼의 수익
 * 
 * 
 * 입력
 * 벌통들의 크기 N, 벌통에 있는 꿀의 양 정보, 
 * 선택할 수 있는 벌통의 개수 M, 꿀을 채취할 수 있는 최대 양 C
 * 
 * 출력
 * 최대 수익
 * 
 * 
 * 문제해결 프로세스
 * 1. 가로로 연속된 M개 만큼의 벌통의 개수 선택
 * 	1-1. 행 단위로 (j)~(j+M-1)까지의 열 정보 저장,
 * 	1-2. 순열로 2개를 선택하는데 열의 범위 겹치면 선택 x
 * 2. 부분집합으로 최대 채취양 C를 넘지 않을 때의 수익(제곱합)의 최댓값 찾기
 * 3. 
 * 
 * Honey(x,y,val)
 * 
 * 제한조건
 * 3<=N<=10
 * M<=5
 * 10<=C<=30
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */


public class Solution_2115_벌꿀채취 {
	static class Honey{
		int x,y,val;
		public Honey(int x, int y, int val) {
			this.x=x;
			this.y=y;
			this.val=val;
		}
	}
	static int N,M,C, map[][];
	static Honey work[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
						map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N-M+1; j++) {
					work[i] = new Honey(i,j,0);
				}
			}
			
			
			
			//2개뽑는 조합이니까 이중for문으로
			for (int i = 0; i < work.length; i++) {
				for (int j = i+1; j < work.length; j++) {
					//같은 행이면서 열이 겹치면 continue;
					if(work[i].x==work[j].x && work[i].y+M > work[j].y) {
						continue;
					}
					//else
					
					
				}
			}
			
			
		}
		
	}
	public static void honeySum(int i, int sum, int get) {
		if(sum>C) {
			return;
		}
		if(i==M) {
//			max1 = get;
			return;
		}
		
		
		
		
		
	}

}
