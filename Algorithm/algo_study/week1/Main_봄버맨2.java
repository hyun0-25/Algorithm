package week1;
import java.util.Scanner;

/*
 * 폭탄이 있는 칸은 3초 뒤에 폭발
 * 폭발 이후에는 폭탄이 있던 칸이 파괴되어 빈칸이 되고, 인접한 네칸(상하좌우)도 함께 파괴
 * 단, 인접한 칸에 폭탄이 있는 경우는 폭발 없이 파괴됨.
 * map: R x C 크기, N초 동안
 * 
 * <이동 규칙>
 * 1. 가장 처음: 폭탄 설치
 * 2. 다음 1초 동안 아무것도 안함
 * 3. 다음 1초 동안 폭탄이 설치되어 있지 않은 모든 칸에 폭탄이 생김 
 *  -> 모든 칸은 폭탄을 가짐, 모두 동시에 설치
 * 4. 1초가 지난 후에 3초 전에 설치된 폭탄이 모두 폭발
 *  3,4 반복
 *  
 */

public class Main_봄버맨2 {
	public static int R, C, N;
	public static char[][] map = new char[201][201];
	public static char[][] bomb = new char[201][201];
//	public static char[][] map ;
//	public static char[][] bomb ;
	
	public static int[] dx = {1, -1, 0, 0};
	public static int[] dy = {0, 0, 1, -1};
	
	
	public static void func(int i, int j) {
		if(i<0 || i>=R || j<0 || j>=C) {
			return;
		}

		if(map[i][j]=='O') {
			bomb[i][j]='.';
			for(int k=0;k<4;k++) {
				int nx = i+dx[k];
				int ny = j+dy[k];
				if(nx<0 || nx>=R || ny<0 || ny>=C) {
					continue;
				}
				bomb[nx][ny]='.';
			}
			return;
		}
		return ;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		N = sc.nextInt();
		sc.nextLine();
//		map = new char[R][C];
//		bomb =new char[R][C];
		//입력 받기
		for (int i = 0; i<R; i++) {
			String str = sc.nextLine();
			for(int j = 0; j<C; j++) {
				map[i][j] = str.charAt(j);
				bomb[i][j] = 'O';
			}
		}
		
		
		//1초면 바로 출력
		if (N==1) {
			for (int i = 0; i<R; i++) {
				for(int j=0; j<C; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			return;
		}
		//짝수일때는 모두 폭탄임 O
		if(N%2==0) {
			for (int i = 0; i<R; i++) {
				for(int j=0; j<C; j++) {
					System.out.print(bomb[i][j]);
				}
				System.out.println();
			}
			return;
		}
		//else
		for (int t=3; t<=N; t+=2) {
			for(int i=0; i<R; i++) {
				for(int j=0; j<C; j++) {
					if(map[i][j]=='O') {
						func(i,j);
					}
				}
			}
			for(int i=0; i<R; i++) {
				for(int j=0; j<C; j++) {
					char temp= map[i][j];
					map[i][j] = bomb[i][j];
					bomb[i][j] = temp;
				}
			}
			//bomb초기화
			for (int i = 0; i<R; i++) {
				for(int j = 0; j<C; j++) {
					bomb[i][j] = 'O';
				}
			}
		}
		for (int i = 0; i<R; i++) {
			for(int j=0; j<C; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
}
