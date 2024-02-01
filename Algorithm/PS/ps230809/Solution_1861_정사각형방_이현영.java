package ps230809;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.management.Query;

/**
 * 풀이 시작 :
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * N^2개의 방이 NxN형태
 * i번째줄의 왼쪽에서 j번째 방에는 1<=Aij<=N^2의 수가 적혀있고 이 숫자는 모든 방에 대해 서로 다름
 * 상하좌우에 있는 다른 방으로 이동 가능
 * 이동하려는 방에 적힌 숫자가 현재 방에 적힌 숫자보다 정확히 1 더 커야 이동 가능
 * 처음 어떤 수가 적힌 방에서 출발해야 가장 많은 개수의 방을 이동할 수 있는지?
 *
 * 구해야 하는 것
 * 처음 어떤 수가 적힌 방에서 출발해야 가장 많은 개수의 방을 이동할 수 있는지?
 * 최대가 여러개라면 그 중에서 적힌 수가 가장 작은 것을 출력
 * 
 * 문제 입력
 * 첫번째줄 : N
 * N개의 줄: Aij값
 * 
 *
 * 제한 요소
 * N<=1000
 * 
 * 생각나는 풀이
 * 어짜피 연속하는 자연수의 개수가 가장 많은 값을 찾는거니까
 * 가장 작은 수 부터 출발해서 이동가능하면 boolean배열을 true로 만들면서 움직임
 * 
 * 만약 그 다음 작은 수의 boolean이 true면 굳이 이동할 필요가 없음 
 * -> 이미 더작은 연속하는 수가 탐색했을테니까
 * 
 * 그럼 입력을 받을 때 1~N^2 값의 인덱스를 Pair[i]에 저장
 * Pair[i] -> i=0~N^2-1 탐색
 * 
 * bfs
 * 
 * 시간 복잡도 
 * O(N*N)
 * 
 *
 * 구현해야 하는 기능
 *
 */



public class Solution_1861_정사각형방_이현영 {
	static int T, N, A[][], max, num, result;
	static boolean isVisited[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			A = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					A[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			isVisited = new boolean[N][N];
			max = 0;
			int result_num=Integer.MAX_VALUE;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(isVisited[i][j]) continue;
					result=0;
					//아직 방문 전이면
					bfs(i,j);
					if(result>max) {
						max = result;
						result_num=num;
					}
					else if(result>=max && result_num>num) {
						max = result;
						result_num=num;
					}
				}
			}
			sb.append('#').append(test_case).append(' ').append(result_num).append(' ').append(max).append('\n');
		}
		System.out.println(sb);
	}
	//
	static int [] dx = {1,-1,0,0};
	static int [] dy = {0,0,1,-1};
	public static void bfs(int i, int j) {
		num=Integer.MAX_VALUE;
		class Pair{
			int x,y;
			public Pair(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}
		
		Queue<Pair> q = new LinkedList<>();
		q.offer(new Pair(i,j));
		result++;
		num = Math.min(num, A[i][j]);
		while(!q.isEmpty()) {
			Pair p = q.poll();
			isVisited[p.x][p.y]=true;
			for (int k = 0; k < 4; k++) {
				int nx = p.x+dx[k];
				int ny = p.y+dy[k];
				//경계 밖
				if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
				//이미 방문
				if(isVisited[nx][ny]) continue;
				//값이 1만큼 차이나면 이동가능 + 방문처리 , result++
				if(Math.abs(A[nx][ny]-A[p.x][p.y])==1) {
					result++;
					isVisited[nx][ny]=true;
					q.offer(new Pair(nx,ny));
					num = Math.min(num, A[nx][ny]);
				}
				//else면 contine;
				else continue;
			}
		}
	}
}
