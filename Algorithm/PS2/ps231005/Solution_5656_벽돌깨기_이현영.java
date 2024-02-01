package ps231005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 시작시간: 15:50
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 구슬은 N번만 쏠 수 있음
 * 0은 빈공간, 그 외의 숫자는 벽돌
 * W*H 배열 -> H,W바뀜
 * 
 * 게임 규칙
 * 1. 구슬은 좌,우로만 움직일 수 있음 + 맨위 벽돌만 깨트릴수 있음
 * 2. 구슬이 명중한 벽돌은 상하좌우로 (벽돌에 적힌 숫자-1)칸만큼 같이 제거
 * 	2-1. 상하좌우의 다른 벽돌 모두 연쇄반응
 * 	2-2. 연쇄반응 후 0(빈공간)이 되면 밑으로 떨어짐
 * 3. N번 반복해서 최대한 많은 벽돌을 제거 후 남은 벽돌 개수 출력
 * 
 * 입력
 * 첫째줄 : N,W,T
 * H개줄 : 벽돌 정보 W개씩
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 1. 구슬쏘는 위치 N개 정하기, 중복순열
 * 2. 1에서 구한 위치에 구슬 쏘기 (shoot())
 * 	2-1. 맞은 구슬을 시작으로 상하좌우로 (벽돌에 적힌 숫자-1)칸만큼 같이 제거
 * 	2-2. 구슬의 숫자가 1보다 크면 큐에 넣고 반복
 * 3. 구슬 내리기
 * 	3-1. 열에서 구슬 발견할때마다 행값 조절
 * 4. 남은 벽돌 개수 cnt
 * 
 * 
 * 제한조건
 * N<=4
 * W<=12
 * H<=15
 * 
 * 
 * 시간복잡도
 * 구슬 쏘는 경우의 수 -> 12^4 = 2만
 * 연쇄반응 -> 12*15 * 32
 * 구슬떨어지기 ->
 * 
 */

public class Solution_5656_벽돌깨기_이현영 {
	static int N,W,H,result,map[][],isselect[], newmap[][];
	static int min = Integer.MAX_VALUE;
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static Queue<Pair> balllist = new ArrayDeque<>();
	static class Pair {
		int x,y;
		public Pair(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int test_case = 1; test_case <= T; test_case++) {
			min = Integer.MAX_VALUE;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); 
			W = Integer.parseInt(st.nextToken()); 
			H = Integer.parseInt(st.nextToken()); 
			map = new int[H][W];
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken()); 
				}
			}
			isselect = new int[N];
			select(0);
			
			sb.append('#').append(test_case).append(' ').append(min).append('\n');
		}
		System.out.println(sb);
		
	}
	public static void select(int cnt) {
		if(cnt==N) {
			shoot(isselect);
			return;
		}
		for (int i = 0; i < W; i++) {
			isselect[cnt] = i;
			select(cnt+1);
		}
	}
	/* 문제해결 프로세스
	 * 1. 구슬쏘는 위치 N개 정하기, 중복순열
	 * 2. 1에서 구한 위치에 구슬 쏘기 (shoot())
	 * 	2-1. 맞은 구슬을 시작으로 상하좌우로 (벽돌에 적힌 숫자-1)칸만큼 같이 제거
	 * 	2-2. 구슬의 숫자가 1보다 크면 큐에 넣고 반복
	 * 3. 구슬 내리기
	 * 	3-1. 열에서 구슬 발견할때마다 행값 조절
	 * 4. 남은 벽돌 개수 cnt
	 */
	public static void shoot(int[] issel) {
		newmap = new int [H][W];
		for (int i = 0; i < H; i++) {
			newmap[i] = Arrays.copyOf(map[i], W);
		}
		
		for (int k = 0; k < N; k++) {
			int col = issel[k];
			int findrow = -1;
			for (int f = 0; f < H; f++) {
				if(newmap[f][col]!=0) {
					findrow = f;
					break;
				}
			}
			//쏠 구슬이 없으면 통과
			if(findrow==-1) {
				continue;
			}
			//구슬 쏘기
			else if(newmap[findrow][col]==1) {
				newmap[findrow][col]=0;
				continue;
			}
			
			balllist.offer(new Pair(findrow,col));
			while(!balllist.isEmpty()) {
				Pair p = balllist.poll();
				shootball(p.x, p.y);
			}
			//구슬 내리기
			downball();
		}
		int count = 0;
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if(newmap[i][j]!=0) count++;
			}
		}
		min = Math.min(min, count);
	}
	
	
	public static void downball() {
		for (int j = 0; j < W; j++) {
			int cntx = H-1;
			for (int i = H-1; i >= 0; i--) {
				if(newmap[i][j]!=0) {
					newmap[cntx--][j] = newmap[i][j];
					if(cntx+1!=i) newmap[i][j] = 0;
				}
			}
		}
	}
	public static void shootball(int r, int c) {
		int num = newmap[r][c]-1;
		newmap[r][c] = 0;
		for (int i = 0; i < 4; i++) {
			int nx = r;
			int ny = c;
			for (int j = 0; j < num; j++) {
				nx+=dx[i];
				ny+=dy[i];
				if(!rangecheck(nx,ny)) {
					break;
				}
				else if(newmap[nx][ny]==0) {
					continue;
				}
				else if(newmap[nx][ny]==1) {
					newmap[nx][ny]=0;
				}
				//연쇄 작용
				else if(newmap[nx][ny]>1) {
					balllist.offer(new Pair(nx,ny));
				}
			}
		}
	}
	public static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<H && ry>=0 && ry<W;
	}

}
