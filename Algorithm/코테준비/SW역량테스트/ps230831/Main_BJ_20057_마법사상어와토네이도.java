package SW역량테스트.ps230831;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 10:23
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 토네이도를 크기가 NxN인 격자로 나누어진 모래밭에서 연습
 * A[r][c]는 모래의 양
 * 토네이도 -> 격자의 가운데 칸붕터 토네이도의 이동 시작
 * 토네이도는 한번에 한 칸 이동
 * 토네이도가 한 칸 이동할 때마다 모래는 일정한 비율로 흩날림
 * x->y로 이동 : y의 모든 모래가 비율과 a가 적혀있는 칸으로 이동
 * 			비율이 적혀있는 칸으로 이동하는 모래의 양은 y에 있는 모래의 해당 비율만큼, 소수점 아래는 버림
 * 			a로 이동하는 모래의 양 == 비율이 적혀있는 칸으로 이동하지 않은 남은 모래의 양
 *  모래가 이미 있는 칸으로 모래가 이동하면 모래의 양은 더해짐
 *  토네이도는 (1,1)까지 이동한 뒤 소멸
 *  모래가 격자 밖으로 이동 가능
 *  토네이도가 소멸되었을 때, 격자의 밖으로 나간 모래의 양
 * 
 * 
 * 입력
 * 첫째줄 : 격자 크기 N
 * N개줄: 격자의 각 칸의 모래양
 * 
 * 출력
 * 격자 밖으로 나간 모래의 양
 * 
 * 문제해결 프로세스 - 구현
 * 
 * 10% : i+dx[k]+dx[k-1],j+dy[k]+dy[k-1]
 * 		 i+dx[k]+dx[k+1],j+dy[k]+dy[k+1]
 * 
 * 7%  : i+dx[k-1],j+dy[k-1]
 * `	 i+dx[k+1],j+dy[k+1]
 * 
 * 5%  : i+dx[k*2],j+dy[k*2]
 * 
 * 2%  : i+dx[2*(k-1)],j+dy[2*(k-1)]
 * `	 i+dx[2*(k+1)],j+dy[2*(k+1)]
 * 
 * 1%  : i-dx[k]+dx[k-1],j-dy[k]+dy[k-1]
 * 		 i-dx[k]+dx[k+1],j-dy[k]+dy[k+1]
 * 
 * a = (i,j모래) - (위 모래 전체)
 * 
 * 단 k-1과 k+1이 0~3범위로 
 * 범위 밖 -> result에 더함
 * ------------------------
 * 좌 하 우 상
 * 0  1  2  3
 * 
 * 달팽이 회전 -> 초기 위치 = (N/2,N/2)
 * 1 1 2 2 3 3 4 4 ... 6 6 7(범위밖) = N이 7
 * 1 1 2 2 3(범위밖) = N이 3
 * 
 * 
 * 제한조건
 * 
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_BJ_20057_마법사상어와토네이도 {
	static int N, map[][];
	static int dx[] = {0,1,0,-1};
	static int dy[] = {-1,0,1,0};
	static int x,y;
	static int result;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st ;
		
		map = new int [N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//초기 방향과 위치
		int dir = 0;
		x = N/2;
		y = N/2;
		for (int i = 1; i <= N; i++) {
			move(i,dir);
			dir++; if(dir>3) dir=0;
			if(i==N) break; //N일 때는 한번만 실행
			move(i,dir);
			dir++; if(dir>3) dir=0;
		}
		System.out.println(result);
	}
	
	//d방향으로 cnt번 이동
	public static void move(int cnt, int d) {
		int nx = x;
		int ny = y;
		for (int i = 0; i < cnt; i++) {
			nx += dx[d];
			ny += dy[d];
			//한칸 이동 위치
			tornado(nx, ny, d);
			if(nx==0 && ny==0) return;
		}
		x=nx;
		y=ny;
	}
	/* 문제해결 프로세스 - 구현
	 * 
	 * 10% : i+dx[k]+dx[k-1],j+dy[k]+dy[k-1]
	 * 		 i+dx[k]+dx[k+1],j+dy[k]+dy[k+1]
	 * 
	 * 7%  : i+dx[k-1],j+dy[k-1]
	 * `	 i+dx[k+1],j+dy[k+1]
	 * 
	 * 5%  : i+dx[k*2],j+dy[k*2]
	 * 
	 * 2%  : i+dx[2*(k-1)],j+dy[2*(k-1)]
	 * `	 i+dx[2*(k+1)],j+dy[2*(k+1)]
	 * 
	 * 1%  : i-dx[k]+dx[k-1],j-dy[k]+dy[k-1]
	 * 		 i-dx[k]+dx[k+1],j-dy[k]+dy[k+1]
	 * 
	 * a = (i,j모래) - (위 모래 전체)
	 * 
	 * 단 k-1과 k+1이 0~3범위로 
	 * 범위 밖 -> result에 더함
	 * ------------------------
	 * 좌 하 우 상
	 * 0  1  2  3
	 */
	public static void tornado(int mx, int my, int k) {
		int total_sand = map[mx][my];
		//10%
		int per10 = (int)(total_sand * 0.1);
		int per10_x1,per10_x2,per10_y1,per10_y2;
		if(k-1<0) {
			per10_x1 = mx+dx[k]+dx[3];
			per10_y1 = my+dy[k]+dy[3];
			outofmap(per10_x1, per10_y1, per10);
			per10_x2 = mx+dx[k]+dx[1];
			per10_y2 = my+dy[k]+dy[1];
			outofmap(per10_x2, per10_y2, per10);
		}
		else if(k+1>3) {
			per10_x1 = mx+dx[k]+dx[2];
			per10_y1 = my+dy[k]+dy[2];
			outofmap(per10_x1, per10_y1, per10);
			per10_x2 = mx+dx[k]+dx[0];
			per10_y2 = my+dy[k]+dy[0];
			outofmap(per10_x2, per10_y2, per10);
			
		}
		else {
			per10_x1 = mx+dx[k]+dx[k-1];
			per10_y1 = my+dy[k]+dy[k-1];
			outofmap(per10_x1, per10_y1, per10);
			per10_x2 = mx+dx[k]+dx[k+1];
			per10_y2 = my+dy[k]+dy[k+1];
			outofmap(per10_x2, per10_y2, per10);
		}
		//7%
		int per7 = (int)(total_sand * 0.07);
		int per7_x1,per7_x2,per7_y1,per7_y2;
		if(k-1<0) {
			per7_x1 = mx+dx[3];
			per7_y1 = my+dy[3];
			outofmap(per7_x1, per7_y1, per7);
			per7_x2 = mx+dx[1];
			per7_y2 = my+dy[1];
			outofmap(per7_x2, per7_y2, per7);
		}
		else if(k+1>3) {
			per7_x1 = mx+dx[2];
			per7_y1 = my+dy[2];
			outofmap(per7_x1, per7_y1, per7);
			per7_x2 = mx+dx[0];
			per7_y2 = my+dy[0];
			outofmap(per7_x2, per7_y2, per7);
			
		}
		else {
			per7_x1 = mx+dx[k-1];
			per7_y1 = my+dy[k-1];
			outofmap(per7_x1, per7_y1, per7);
			per7_x2 = mx+dx[k+1];
			per7_y2 = my+dy[k+1];
			outofmap(per7_x2, per7_y2, per7);
		}
		
		//5%
		int per5 = (int)(total_sand * 0.05);
		int per5_x,per5_y;
		per5_x = mx+dx[k]*2;
		per5_y = my+dy[k]*2;
		outofmap(per5_x, per5_y, per5);
		//2%
		int per2 = (int)(total_sand * 0.02);
		int per2_x1,per2_x2,per2_y1,per2_y2;
		if(k-1<0) {
			per2_x1 = mx+dx[3]*2;
			per2_y1 = my+dy[3]*2;
			outofmap(per2_x1, per2_y1, per2);
			per2_x2 = mx+dx[1]*2;
			per2_y2 = my+dy[1]*2;
			outofmap(per2_x2, per2_y2, per2);
		}
		else if(k+1>3) {
			per2_x1 = mx+dx[2]*2;
			per2_y1 = my+dy[2]*2;
			outofmap(per2_x1, per2_y1, per2);
			per2_x2 = mx+dx[0]*2;
			per2_y2 = my+dy[0]*2;
			outofmap(per2_x2, per2_y2, per2);
		}
		else {
			per2_x1 = mx+dx[k-1]*2;
			per2_y1 = my+dy[k-1]*2;
			outofmap(per2_x1, per2_y1, per2);
			per2_x2 = mx+dx[k+1]*2;
			per2_y2 = my+dy[k+1]*2;
			outofmap(per2_x2, per2_y2, per2);
		}
		
		//1%
		int per1 = (int)(total_sand * 0.01);
		int per1_x1,per1_x2,per1_y1,per1_y2;
		if(k-1<0) {
			per1_x1 = mx-dx[k]+dx[3];
			per1_y1 = my-dy[k]+dy[3];
			outofmap(per1_x1, per1_y1, per1);
			per1_x2 = mx-dx[k]+dx[1];
			per1_y2 = my-dy[k]+dy[1];
			outofmap(per1_x2, per1_y2, per1);
		}
		else if(k+1>3) {
			per1_x1 = mx-dx[k]+dx[2];
			per1_y1 = my-dy[k]+dy[2];
			outofmap(per1_x1, per1_y1, per1);
			per1_x2 = mx-dx[k]+dx[0];
			per1_y2 = my-dy[k]+dy[0];
			outofmap(per1_x2, per1_y2, per1);
			
		}
		else {
			per1_x1 = mx-dx[k]+dx[k-1];
			per1_y1 = my-dy[k]+dy[k-1];
			outofmap(per1_x1, per1_y1, per1);
			per1_x2 = mx-dx[k]+dx[k+1];
			per1_y2 = my-dy[k]+dy[k+1];
			outofmap(per1_x2, per1_y2, per1);
		}
		
		int a = total_sand-per10*2-per7*2-per5-per2*2-per1*2;
		int a_x,a_y;
		a_x = mx+dx[k];
		a_y = my+dy[k];
		outofmap(a_x, a_y, a);
		
		map[mx][my] = 0;
	}
	
	public static void outofmap(int xx, int yy, int val) {
		if(xx<0 || xx>=N || yy<0 || yy>=N) result+=val;
		else {
			map[xx][yy]+=val;
		}
	}

}
