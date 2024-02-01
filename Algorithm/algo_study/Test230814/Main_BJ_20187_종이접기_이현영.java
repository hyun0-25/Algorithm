package Test230814;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 해석
 * 정사각형의 손수건을 중앙선을 중심으로 접는 방법 4가지
 * D: (가로)상하 접기 - 아랫면 덮음
 * U: (가로)상하 접기 - 윗면 덮음
 * R: (세로)좌우 접기 - 오른쪽면 덮음
 * L: (세로)좌우 접기 - 왼쪽면 덮음
 * 
 * 한변의 길이 2^k 정사각형 손수건
 * 세로 k번 가로 k번 접으면 -> 각 변이 1인 정사각형 손수건
 * 각 변이 1인 정사각형 손수건 -> 네 귀퉁이 중 1곳에 구멍냄, 구명 위치는 0,1,2,3의 숫자
 * 
 * 입력
 * 첫째줄: k (총 2k번 접음)
 * 둘째줄: 종이 접는 방법 문자 2k
 * 셋째줄: 구멍 뚫는 위치를 나타내는 정수 h(0,1,2,3 중)
 * 
 * 
 * 출력
 * 종이를 펼쳤을 때 정사각형에 뚫린 구멍의 위치를 번호(0,1,2,3)으로 출력
 * 출력은 총 2^k줄
 * 
 */



public class Main_BJ_20187_종이접기_이현영 {
	static int k, h;
	static int paper[][] = new int [2][2];
	//(0,0) 자리의 상하, 좌우 접힌 정보
	static char dir, dir_DU, dir_RL;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		k = Integer.parseInt(br.readLine());
		//한변의 길이 2^k
		int cnt = (int) Math.pow(2, k);
		// 0 1
		// 2 3
		paper[0][0]=0; paper[0][1]=1;
		paper[1][0]=2; paper[1][1]=3;

		//(0,0)의 초기 접는 방향은 U,L
		//U과 L로 접으면 방향이 안바뀌기 때문에
		dir_DU = 'U';
		dir_RL = 'L';
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 2*k; i++) {
			dir = st.nextToken().charAt(0);
			if(dir=='D' || dir=='U') {
				if(dir==dir_DU) continue;
				else {
					fold_DU();
					dir_DU=dir;
				}
			}
			if(dir=='R' || dir=='L') {
				if(dir==dir_RL) continue;
				else {
					fold_RL();
					dir_RL=dir;
				}
			}
		}
		h = Integer.parseInt(br.readLine());
		//(0,0)기준으로 type 4가지 존재
		int type = 0;
		if(h==0) type=paper[0][0];
		else if(h==1) type=paper[0][1];
		else if(h==2) type=paper[1][0];
		else if(h==3) type=paper[1][1];
		
		
		if(type==0) {
			paper[0][0]=0; paper[0][1]=1;
			paper[1][0]=2; paper[1][1]=3;
		}
		else if(type==1) {
			paper[0][0]=1; paper[0][1]=0;
			paper[1][0]=3; paper[1][1]=2;
		}
		else if(type==2) {
			paper[0][0]=2; paper[0][1]=3;
			paper[1][0]=0; paper[1][1]=1;
		}
		else if(type==3) {
			paper[0][0]=3; paper[0][1]=2;
			paper[1][0]=1; paper[1][1]=0;
		}
		
		for (int i = 0; i < cnt; i++) {
			for (int j = 0; j < cnt; j++) {
				if(i%2==0 && j%2==0) {
					sb.append(paper[0][0]).append(' ');
				}
				if(i%2==0 && j%2==1) {
					sb.append(paper[0][1]).append(' ');
				}
				if(i%2==1 && j%2==0) {
					sb.append(paper[1][0]).append(' ');
				}
				if(i%2==1 && j%2==1) {
					sb.append(paper[1][1]).append(' ');
				}
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
	public static void fold_DU() {
		int temp = paper[0][0];
		paper[0][0] = paper[1][0]; 
		paper[1][0] = temp;
		
		temp = paper[0][1];
		paper[0][1] = paper[1][1]; 
		paper[1][1] = temp;
	}
	public static void fold_RL() {
		int temp = paper[0][0];
		paper[0][0] = paper[0][1]; 
		paper[0][1] = temp;
		
		temp = paper[1][0];
		paper[1][0] = paper[1][1]; 
		paper[1][1] = temp;
	}
}
