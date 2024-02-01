package ps230816;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:42
 * 종료시간: 
 * 
 * 
 * 문제 해석
 * 9x9 크기의 스도쿠 퍼즐의 숫자들이 주어졌을 때,
 * 겹치는 숫자가 없는 경우 1 출력
 * 		그렇지 않은 경우 0 출력
 * 
 * 
 * 입력
 * 첫째줄: 테스트 케이스 개수 T
 * 둘째줄부터 : 9x9 테케 
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 크기가 9+1인 boolean배열인 nums를 생성하여 
 * map[x][y]과 일치하는 인덱스를 true로 바꾸기
 * -처음 만난 숫자면 nums[i] = true;
 * -nums[i]가 true면 이미 범위에 그 숫자가 존재하는 경우이므로 false반환
 * 
 * 
 * boolean isRow(x,y) : x고정 y=0~8까지
 * boolean isCol(x,y) : y고정 x=0~8까지
 * boolean isSquare(x,y) : 
 * 
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 완탐
 * 
 * 
 */
public class Solution_1974_스도쿠검증_이현영 {
	static int T, board[][] = new int[9][9];
	static boolean[] nums;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++) {
			for (int i = 0; i < 9; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < 9; j++) {
					board[i][j]=Integer.parseInt(st.nextToken());
				}
			}
			sb.append('#').append(test_case).append(' ').append(isSudoku()).append('\n');
		}
		System.out.println(sb);
		
		
	}
	public static int isSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
//				if(!(isRow(i,j) && isCol(i,j) && isSquare(i,j))) {
//					return 0;
//				}
				if(!isRow(i,j)) return 0;
				if(!isCol(i,j)) return 0;
				if(!isSquare(i,j)) return 0;
			}
		}
		return 1;
	}
	public static boolean isRow(int x, int y) {
		int idx;
		nums = new boolean[10];
		for (int i = 0; i < 9; i++) {
			idx = board[x][i];
			if(nums[idx]) return false;
			nums[idx]=true;
		}
		return true;
	}
	
	public static boolean isCol(int x, int y) {
		int idx;
		nums = new boolean[10];
		for (int i = 0; i < 9; i++) {
			idx = board[i][y];
			if(nums[idx]) return false;
			nums[idx]=true;
		}
		return true;
	}
	
	public static boolean isSquare(int x, int y) {
		int idx;
		nums = new boolean[10];
		int row = x/3;
		int col = y/3;
		for (int i = row*3; i < row*3+3; i++) {
			for (int j = col*3; j < col*3+3; j++) {
				idx = board[i][j];
				if(nums[idx]) return false;
				nums[idx]=true;
			}
		}
		return true;
	}
}
