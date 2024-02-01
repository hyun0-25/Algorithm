package ps230801_Recursive;

import java.io.FileInputStream;
import java.util.Scanner;





public class Solution_1210_Ladder1_이현영 {
	static int N , start;
	static boolean result;
	static int [][] ladder = new int [100][100];
	//방향 :  아래(default), 오, 왼
	static int [] dx = {1,0,0};
	static int [] dy = {0,1,-1};
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("src\\recursive230801\\input.txt"));
		Scanner sc = new Scanner(System.in);

		for(int test_case = 1; test_case <= 10; test_case++) {
			N = sc.nextInt();
			
			for(int i = 0; i<100; i++) {
				for(int j = 0; j<100; j++) {
					ladder[i][j]=sc.nextInt();
				}
			}
			
			//0행에서 ladder[0][j]==1이면 사다리 출발
			for(int j=0; j<100; j++) {
				int i=0;
				
				if(ladder[i][j]==1) {
					//사다리 타기 시작 
					for(int k=2; k>=0; k--) {
						int nx = i+dx[k];
						int ny = j+dy[k];
						if(nx<0 || nx>=100 || ny<0 || ny>=100 || ladder[nx][ny]==0) {
							continue;
						}
						else {
							//1. 시작하는 방향 확인
							//2. 그 방향으로 1칸 이동 
							start=j;

							if(func(nx, ny, k)) {
								result = true;
							}
						}
					}
					if(result) break;
				}
			}
		}
	}
	/*
	 * 1. 시작하는 방향 확인
	 * 2. 그 방향으로 1칸 이동 -> 오른쪽 또는 왼쪽에 1이 있는지 확인
	 * 3. 1이 있는 방향으로 바꿈
	 * 4. 계속 그 방향으로 0을 만날때까지 이동
	 * 
	 * 5. 방향을 다시 아래로 바꾸고 1칸이동 -> 2~5 반복
	 * 
	 * */
	
	private static boolean func(int i, int j, int k) {
		if(i==99) {
			if(ladder[i][j]==2) {
				System.out.printf("#%d %d",N, start);
				System.out.println();
				return true;
			}
			return false;
		}
		int nx = i+dx[k];
		int ny = j+dy[k];
		//아래방향이면 오른쪽 또는 왼쪽에 1이 있는지 확인
		
		if(k==0) {
			nx = i+dx[1];
			ny = j+dy[1];
			if(nx<0 || nx>=100 || ny<0 || ny>=100 || ladder[nx][ny]==0) {
				nx = i+dx[2];
				ny = j+dy[2];
//				func(nx, ny, 0);
			}
			else {
				func(nx,ny,1);
			}
			if(nx<0 || nx>=100 || ny<0 || ny>=100 || ladder[nx][ny]==0) {
				nx = i+dx[0];
				ny = j+dy[0];
				func(nx, ny, 0);
			}
			else {
				func(nx,ny,2);
			}
//			if(i+dx[1]>0 && i+dx[1]<100 && j+dy[1]>0 && j+dy[1]<100 && ladder[i+dx[1]][j+dy[1]]==1) {
//				nx = i+dx[1];
//				ny = j+dy[1];
//				func(nx, ny, 1);
//			}
//			else if(i+dx[2]>0 && i+dx[2]<100 && j+dy[2]>0 && j+dy[2]<100 && ladder[i+dx[2]][j+dy[2]]==1) {
//				nx = i+dx[2];
//				ny = j+dy[2];
//				func(nx, ny, 2);
//			}
//			else {
//				nx = i+dx[0];
//				ny = j+dy[0];
//				func(nx, ny, 0);
//			}
		}
		else if(k!=0) {
			nx = i+dx[k];
			ny = j+dy[k];
			//경계 밖이거나 이동불가하면 아래로 이동
			if(nx<0 || nx>=100 || ny<0 || ny>=100 || ladder[nx][ny]==0) {
				nx = i+dx[0];
				ny = j+dy[0];
				func(nx, ny, 0);
			}
			else {
				func(nx, ny, k);
			}
		}
		return false;
	}

	

}
