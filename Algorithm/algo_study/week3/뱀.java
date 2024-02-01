package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간:
 * 종료시간:
 * 
 * 
 * 문제 해석
 * NxN 정사각형 보드
 * 보드의 끝에 벽이 있음
 * (0,0)에서 출발, 뱀의 길이는 1, 오른쪽 방향
 * 1. 몸길이를 늘려 머리를 다음칸에 위치
 * 2. 벽이나 자기자신의 몸과 부딪히면 게임 종료
 * 3. 이동한 칸에 사과가 있으면, 사과 사라지고 꼬리는 움직이지 않음
 * 4. 이동한 칸에 사과가 없으면, 몸길이를 줄여서 꼬리가 위치한 칸을 비움 -> 몸길이 변화X
 * 게임이 몇 초에 끝나는지 계산 
 * 
 * 
 * 입력
 * 첫째줄 : 보드의 크기 N
 * 둘째줄 : 사과의 개수 K
 * 다음 K줄 : 사과의 위치
 * 다음줄: 뱀의 방향 변환 횟수 L
 * 다음 L줄 : 뱀의 방향 변환 정보 X,C(L또는D)
 *		X초가 끝난 뒤 L(왼쪽 90도 회전)
 *				  D(오른쪽 90도 회전)
 * 
 * 출력
 * 게임이 몇초에 끝나는지
 * 
 * 문제해결 프로세스 (구현?!)
 * isApple(boolean[][]) 사과있는지 / isSnake(boolean[][]) 뱀의 몸
 * snake (queue<Pair>)로 사과 안만나면(isApple=false) 머리 이동위치넣고,꼬리 꺼내기 + isSnake(boolean) 처리
 * 				   사과 만나면(isApple=true) 머리 이동 위치만 넣기 + isSnake(boolean) 처리
 * 머리 이동위치 넣을때 (isSnake=true)면 종료
 * 
 * 
 * 제한조건
 * 맵크기 : N<=100
 * 사과개수: K<=100
 * 방향전환 : L<=100
 * 시간: X<=10,000
 * 
 * 시간복잡도
 * O(X* )
 * 
 */



public class 뱀 {
	static class Pair{
		int x,y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static class Dir{
		//X초에 C방향으로 90도 회전
		//왼쪽(C가 'L') 또는 오른쪽(C가 'D')로 90도 방향
		int X;
		char C;
		public Dir(int X, char C) {
			this.X = X;
			this.C = C;
		}
	}
	static int N,K,L;
	static boolean isApple[][], isSnake[][];
	static Dir dir[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		
		isApple = new boolean[N][N];
		isSnake = new boolean[N][N];
		StringTokenizer st;
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int x,y;
			x = Integer.parseInt(st.nextToken())-1;
			y = Integer.parseInt(st.nextToken())-1;
			isApple[x][y]=true;
		}
		
		L = Integer.parseInt(br.readLine());
		dir = new Dir[L];
		for (int i = 0; i < L; i++) {
			int x;
			char c;
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			c = st.nextToken().charAt(0);
			dir[i] = new Dir(x,c);
		}
		/**
		 * 문제해결 프로세스 (구현?!)
		 * isApple(boolean[][]) 사과있는지 / isSnake(boolean[][]) 뱀의 몸
		 * 
		 * snake (queue<Pair>)로 사과 안만나면(isApple=false) 머리 이동위치넣고,꼬리 꺼내기 + isSnake(boolean) 처리
		 * 				   사과 만나면(isApple=true) 머리 이동 위치만 넣기 + isSnake(boolean) 처리
		 * 머리 이동위치 넣을때 (isSnake=true)면 종료
		 * 
		 */
		//우 하 좌 상
		//0 1 2 3
		int dx[] = {0,1,0,-1};
		int dy[] = {1,0,-1,0};
		
		//뱀의 몸의 좌표들이 저장되어있는 큐
		Queue<Pair> q = new ArrayDeque<>();
		
		//시작점 넣기
		int x=0;
		int y=0;
		//오른쪽 방향으로 시작
		int k = 0;
		q.offer(new Pair(x,y));
		isSnake[x][y] = true;
		int time = 0;
		//방향전환 인덱스
		int cidx = 0;
		while(true) {
			time++;
			//한칸 이동
			x += dx[k];
			y += dy[k];
			//뱀 몸 isSnake 또는 벽
			if(x<0 || x>=N || y<0 || y>=N || isSnake[x][y]) {
				System.out.println(time);
				return;
			}
			/**
			 * snake (queue<Pair>)로 사과 만나면(isApple=true) 머리 이동 위치만 넣기 + isSnake(boolean) 처리, isApple(boolean)=false
			 * 					사과 안만나면(isApple=false) 머리 이동위치넣고,꼬리 꺼내기 + isSnake(boolean) 처리
			 */
			//사과 isApple
			//true
			if(isApple[x][y]) {
				//사과 없어짐
				isApple[x][y]=false;
				//머리넣기
				q.offer(new Pair(x,y));
				isSnake[x][y]=true;
				//꼬리 안꺼냄
			}
			//false
			else {
				//머리넣기
				q.offer(new Pair(x,y));
				isSnake[x][y]=true;
				//꼬리 꺼내기
				Pair tail = q.poll();
				isSnake[tail.x][tail.y]=false;
			}
			//X초가 끝난 뒤 방향 전환
			if(dir.length>cidx && dir[cidx].X==time) {
				if(dir[cidx].C=='L') {
					k-=1;
					k+=4;
					k%=4;
				}
				else if(dir[cidx].C=='D') {
					k+=1;
					k%=4;
				}
				//그다음 방향전환 시간
				cidx++;
			}
		}
	}
}
