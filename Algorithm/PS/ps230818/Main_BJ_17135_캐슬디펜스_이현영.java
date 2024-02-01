package ps230818;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 시작시간: 15:46
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 성을 향해 몰려오는 적을 잡는 턴 방식의 게임
 * 게임 진행 격자판 NxM, 1x1 크기의 칸으로 나누어져 있고, 각 칸에는 적의 수는 최대 1개
 * N번행의 바로 아래 N+1번 행은 모든 칸에는 성임
 * 성을 적에게서 지키기 위해 궁수 3명을 배치
 * 궁수 - 성이 있는 칸에 배치
 * 		하나의 칸에는 최대 1명의 궁수
 * 		각 턴마다 궁수는 적 하나를 공격, 모든 궁수는 동시에 공격
 * 		궁수가 공격하는 적은 거리가 이하인 적 중 가장 가까운 적, 여럿일 경우 가장 왼쪽에 있는 적 공격
 * 		같은 적이 여러 궁수에게 공격 가능, 공격 받은 적은 게임에서 제외
 * 공격이 끝나면 적이 이동
 * 		적은 아래로 1칸 이동
 * 		성이 있는 칸으로 이동한 경우에는 게임에서 제외
 * 		모든 적이 격자판에서 제외되면 게임이 끝남
 * 
 * 궁수의 위치가 중요
 * 궁수의 공격으로 제거할 수 있는 적의 최대 수 계산
 * 
 * 
 * 입력
 * 첫째줄: N,M, 궁수의 공격 거리 제한 D
 * 둘째줄~N개줄: 격자판의 상태, 0-빈칸,1-적
 * 
 * 출력
 * 궁수의 공격으로 제거할 수 있는 적의 최대 수를 출력
 * 
 * 문제해결 프로세스
 * attacklist
 * 0. 적들을 배열에 담기
 * 
 * 1. 궁수의 위치를 MC3으로 결정 (조합)
 * 2. 3명의 궁수로 부터 가장 짧은 거리에 위치한 적 찾기
 * 	2-1. 궁수로 부터 떨어진 거리를 dis++;
 * 		공격할 적이 있음 - 동일한 dis를 가지는 적 중에서 열값이 작은 것 attacklist에 담기
 * 		공격할 적이 없음 - dis>D 종료
 * 	2-2. attacklist에 담아져 있는 적을 제거 (여러 궁수가 동시에 공격한 적이 존재할 수 있음) 
 * 		if map[i][j]==1
 * 			map[i][j]=0
 * 			cnt++;
 * 다른 2. 모든 적 배열을 for문을 돌면서 궁수와의 거리 저장 + dis_h1,dis_h2,dis_h3의 최솟값 갱신하면서
 * 		dis_h가 D를 넘지 않으면서 min과 동일하다면 제거
 * 
 * 3. 적 아래로 한칸 이동, move함수(적 배열)
 *  3-1. 적의 위치가 N행을 넘어가면 삭제
 *  3-2. 적 배열이 비어있으면 게임 종료
 * 4. 2-3번 반복
 * 
 * 
 * 제한조건
 * 3<=N,M<=15
 * D<=10
 * 
 * 
 * 시간복잡도
 * O(15C3*최대적15*15 *3 *15)
 * 
 */

public class Main_BJ_17135_캐슬디펜스_이현영 {
	static class Enemy {
		int x,y,h1,h2,h3;

		public Enemy(int x, int y, int h1, int h2, int h3) {
			super();
			this.x = x;
			this.y = y;
			this.h1 = h1;
			this.h2 = h2;
			this.h3 = h3;
		}
	}
	static class Hunter {
		int x,y;

		public Hunter(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	static int N,M,D, max, result, map[][];
	static ArrayList<Enemy> enemys;
	static Hunter hunter[], castle[];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		enemys = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//왼쪽에 있는 적이 우선이니까 열->행
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				//적이면 인덱스를 enemy list에 추가
				if(map[j][i]==1) {
					enemys.add(new Enemy(j,i,0,0,0));
				}
			}
		}
		
		castle = new Hunter[M];
		hunter = new Hunter[3];
		for (int i = 0; i < M; i++) {
			castle[i] = new Hunter(N,i);
		}
		comb(0,0);
		System.out.println(max);
	}
	/* 문제해결 프로세스
	 * attacklist
	 * 0. 적들을 배열에 담기
	 * 
	 * 1. 궁수의 위치를 MC3으로 결정 (조합)
	 * 2. 3명의 궁수로 부터 가장 짧은 거리에 위치한 적 찾기
	 * 	2-1. 궁수로 부터 떨어진 거리를 dis++;
	 * 		공격할 적이 있음 - 동일한 dis를 가지는 적 중에서 열값이 작은 것 attacklist에 담기
	 * 		공격할 적이 없음 - dis>D 종료
	 * 	2-2. attacklist에 담아져 있는 적을 제거 (여러 궁수가 동시에 공격한 적이 존재할 수 있음) 
	 * 		if map[i][j]==1
	 * 			map[i][j]=0
	 * 			cnt++;
	 * 다른 2. 모든 적 배열을 for문을 돌면서 궁수와의 거리 저장 + dis_h1,dis_h2,dis_h3의 최솟값 갱신하면서
	 * 		dis_h가 D를 넘지 않으면서 min과 동일하다면 제거
	 * 
	 * 3. 적 아래로 한칸 이동, move함수(적 배열)
	 *  3-1. 적의 위치가 N행을 넘어가면 삭제
	 *  3-2. 적 배열이 비어있으면 게임 종료
	 * 4. 2-3번 반복
	 */
	
	//궁수의 위치 3군데 뽑기
	// 1. 궁수의 위치를 MC3으로 결정 (조합)
	public static void comb(int cnt, int start) {
		if(cnt==3) {
			result=0;
			ArrayList<Enemy> enemy = new ArrayList<>();
			for(Enemy e: enemys) {
				enemy.add(new Enemy(e.x,e.y,e.h1,e.h2,e.h3));
			}
			distance(hunter, enemy);
			max = Math.max(max, result);
			return;
		}
		for (int i = start; i < M; i++) {
			hunter[cnt] = castle[i];
			comb(cnt+1,i+1);
		}
	}
	
	//적과 궁수와의 거리 구하는 함수
	//다른 2. 모든 적 배열을 for문을 돌면서 궁수와의 거리 저장 + dis_h1,dis_h2,dis_h3의 최솟값 갱신하면서
	// 		dis_h가 D를 넘지 않으면서 min과 동일하다면 제거
	
	public static void distance(Hunter[] hunters, ArrayList<Enemy> enemy) {
		//적이 한명도 안남으면 종료
		if(enemy.isEmpty()) {
			return;
		}
		
		int min_h1 = Integer.MAX_VALUE;
		int min_h2 = Integer.MAX_VALUE;
		int min_h3 = Integer.MAX_VALUE;
		int idx_h1 = -1; int idx_h2 = -1; int idx_h3 = -1;
		
		for (int k = 0; k < enemy.size(); k++) {
			Enemy e = enemy.get(k);
			for (int i = 0; i < 3; i++) {
				int rx = Math.abs(hunters[i].x-e.x);
				int ry = Math.abs(hunters[i].y-e.y);
				if(i==0) e.h1=rx+ry;
				else if(i==1) e.h2=rx+ry;
				else if(i==2) e.h3=rx+ry;
			}
			if(e.h1<=D && e.h1<min_h1) {
				idx_h1 = k;
				min_h1 = e.h1;
			}
			if(e.h2<=D && e.h2<min_h2) {
				idx_h2 = k;
				min_h2 = e.h2;
			}
			if(e.h3<=D && e.h3<min_h3) {
				idx_h3 = k;
				min_h3 = e.h3;
			}
		}
		ArrayList<Integer> set = new ArrayList<>();
		if(!set.contains(idx_h1) && idx_h1!=-1) {
			set.add(idx_h1); 
		}
		if(!set.contains(idx_h2) && idx_h2!=-1) {
			set.add(idx_h2); 
		}
		if(!set.contains(idx_h3) && idx_h3!=-1) {
			set.add(idx_h3); 
		}
		Collections.sort(set);

		for(int i=set.size()-1; i>=0; i--) {
			int k = set.get(i);
			enemy.remove(k);
			result++;
		}
		move(enemy);
	}
	/* 3. 적 아래로 한칸 이동, move함수(적 배열)
	 *  3-1. 적의 위치가 N행을 넘어가면 삭제
	 *  3-2. 적 배열이 비어있으면 게임 종료
	 */
	//적 이동 함수
	public static void move(ArrayList<Enemy> enemy) {
		for (int i = enemy.size()-1; i >= 0; i--) {
			Enemy e = enemy.get(i);
			e.x++;
			if(e.x>=N) {
				enemy.remove(i);
			}
		}
		//적이 한명도 안남으면 종료
		if(enemy.isEmpty()) {
			return;
		}
		//다시 distance로
		distance(hunter, enemy);
	}
}
