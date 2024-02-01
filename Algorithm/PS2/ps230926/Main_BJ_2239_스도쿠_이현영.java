package ps230926;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 11:27
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 9X9크기의 보드
 * 하다 만 스도쿠 퍼즐이 있을 때, 마저 끝내는 프로그램을 작성
 * 
 * 
 * 입력
 * 9개줄: 9개의 숫자
 * 아직 숫자가 채워지지 않은 칸에는 0이 주어짐
 * 
 * 출력
 * 답이 여러개면 사전식으로 앞서는 것을 출력
 * 
 * 
 * 문제해결 프로세스 (백트래킹)
 * 1. 각 칸마다 가능한 숫자 Arraylist에 담음
 * 	1~9순서대로 체크
 * 2. 모든 경우의 수 돌리면서 안되면 재귀 탈출
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 *  ???
 * 
 * 
 */

public class Main_BJ_2239_스도쿠_이현영 {
	static int board[][];
	static ArrayList<sudoku> list = new ArrayList<>();
	static class sudoku {
		int x,y;
		ArrayList<Integer> nums;
		public sudoku(int x, int y, ArrayList<Integer> nums) {
			super();
			this.x = x;
			this.y = y;
			this.nums = nums;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[9][9];

		for (int i = 0; i < 9; i++) {
			int line = Integer.parseInt(br.readLine());
			for (int j = 0; j < 9; j++) {
				board[i][8-j] = line%10;
				line/=10;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(board[i][j]==0) {
					ArrayList<Integer> numlist = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
					numlist = numcheck(i,j,numlist);
					list.add(new sudoku(i,j, numlist));
				}
			}
		}
		func(0);
	}
	
	public static void func(int cnt) {
		//기저 조건
		if(cnt==list.size()) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					sb.append(board[i][j]);
				}
				sb.append('\n');
			}
			System.out.println(sb);
			System.exit(0);
		}
		
		for(int i=0; i <list.get(cnt).nums.size(); i++) {
			int nx = list.get(cnt).x;
			int ny = list.get(cnt).y;
			board[nx][ny] = list.get(cnt).nums.get(i);
			if(!sudokucheck(nx,ny)) {
				board[nx][ny] = 0;
				
			}
			else {
				func(cnt+1);
			}
			if(i==list.get(cnt).nums.size()-1) {
				board[nx][ny] = 0;
				return;
			}
		}
	}
	
	public static boolean sudokucheck(int xx, int yy) {
		for (int i = 0; i < 9; i++) {
			if(i==xx) continue;
			if(board[xx][yy]==board[i][yy]) {
				return false;
			}
		}
		
		for (int j = 0; j < 9; j++) {
			if(j==yy) continue;
			if(board[xx][yy]==board[xx][j]) {
				return false;
			}
		}
		int ix = xx/3;
		int iy = yy/3;
		for (int i = 3*ix; i < 3*ix+3; i++) {
			for (int j = 3*iy; j < 3*iy+3; j++) {
				if(i==xx && j==yy) continue;
				if(board[xx][yy]==board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static ArrayList<Integer> numcheck(int xx, int yy, ArrayList<Integer> idxcheck) {
		for (int i = 0; i < 9; i++) {
			if(i==xx) continue;
			for(int k=0; k<idxcheck.size(); k++) {
				if(idxcheck.get(k)==board[i][yy]) {
					idxcheck.remove(k);
				}
			}
		}
		
		for (int j = 0; j < 9; j++) {
			if(j==yy) continue;
			for(int k=0; k<idxcheck.size(); k++) {
				if(idxcheck.get(k)==board[xx][j]) {
					idxcheck.remove(k);
				}
			}
		}
		int ix = xx/3;
		int iy = yy/3;
		for (int i = 3*ix; i < 3*ix+3; i++) {
			for (int j = 3*iy; j < 3*iy+3; j++) {
				if(i==xx && j==yy) continue;
				for(int k=0; k<idxcheck.size(); k++) {
					if(idxcheck.get(k)==board[i][j]) {
						idxcheck.remove(k);
					}
				}
			}
		}
		return idxcheck;
	}
}
