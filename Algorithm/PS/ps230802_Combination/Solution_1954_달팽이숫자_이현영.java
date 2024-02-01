package ps230802_Combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_1954_달팽이숫자_이현영  {

	//오른쪽, 아래, 왼쪽, 위 순서
	static int [] dx = {0,1,0,-1};
	static int [] dy = {1,0,-1,0};
	static int [] TN ;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		TN = new int[T];
		for (int i = 0; i < T; i++) {
			TN[i]=Integer.parseInt(br.readLine());
		}
		for(int test_case = 1; test_case <= T; test_case++){
			int N = TN[test_case-1];
			//달팽이 숫자 1~N*N
			int nx = 0, ny = 0, k = 0;
			map = new int[N][N];
			for (int num = 1; num <= N*N; num++) {
				map[nx][ny] = num;
				//다음위치
				nx += dx[k];
				ny += dy[k];
				//맵 밖으로 나가거나 이미 숫자가 써있는 공간 만나면 
				//방향 전환
				if(nx<0 || nx>=N || ny<0 || ny>=N || map[nx][ny]!=0) {
					nx -= dx[k];
					ny -= dy[k];
					k+=1;
					k%=4;
					nx += dx[k];
					ny += dy[k];
				}
			}
			
			System.out.printf("#%d\n",test_case);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
			
		}
		
	}

}
