package ps230825;

/**
 * 시작시간: 5:36
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 낚시왕이 상어 낚시 RxC 격지판
 * 칸에는 상어가 최대 한마리
 * 상어는 크기와 속도 가짐
 * 낚시왕은 0번 열에 위치
 * 1초 진행과정
 * 1. 낚시왕이 오른쪽을 한칸 이동
 * 2. 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어 잡음
 * 		상어를 잡으면 격자판에서 상어 사라짐
 * 3. 상어가 이동
 * 
 * 상어는 주어진 속도로 이동, 칸/초
 * 이동하려고 하는 칸이 격자판의 경계를 넘으면 방향을 반대로 바꿈, 속력은 유지
 * 상어가 이동을 마친 후 한칸에 두마리 이상 불가
 * -> 크기가 가장 큰 상어가 나머지 상어 잡아먹음
 * 낚시왕이 잡은 상어 크기의 합을 출력
 * 
 * 	 상 하 우 좌
 * d=1,2,3,4
 * 
 * 
 * 입력
 * 첫째줄 : 격자판의 크기 R, C, 상어의 수 M
 * M개의줄 : 상어의 정보 -> 위치 (r,c), 속력 s, 이동방향 d, 크기 z
 * 
 * 
 * 출력
 * 낚시왕이 잡은 상어 크기의 합
 * 
 * 
 * 문제해결 프로세스 (구현)
 * 1. 낚시왕 열 한칸 이동 
 * 2. 상어 낚시 (상어 잡았는지 Fishing)
 * 3. 상어 이동 (SharkMove -> isShark)
 * 1,2,3반복 
 * 
 * 제한조건
 * R,C<=100
 * M<=RxC
 * 
 * r<=R
 * c<=C
 * s<=1000
 * z<=10,000
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 * 4,1에 있는 상어 처음에 잡아야됨
 * 
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_BJ_17143_낚시왕_이현영 {
	static int R,C,M;
	static ArrayList<Shark> sharks = new ArrayList<>();
	static int isShark[][];
	static int score;
	//상하우좌
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,1,-1};
	static class Shark{
		int r,c,s,d,z;
		public Shark(int r, int c, int s, int d, int z) {
			this.r=r;
			this.c=c;
			this.s=s;
			this.d=d;
			this.z=z;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		isShark = new int[R+1][C+1];
		resetShark();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken())-1;
			int z = Integer.parseInt(st.nextToken());
			sharks.add(new Shark(r,c,s,d,z));
			isShark[r][c]=z;
		}
		
		//낚시꾼 0~C까지 이동
		/* 1초 진행과정
		 * 1. 낚시왕이 오른쪽을 한칸 이동
		 * 2. 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어 잡음
		 * 		상어를 잡으면 격자판에서 상어 사라짐
		 * 3. 상어가 이동
		 */
		for (int i = 1; i <= C; i++) {
			Fishing(i);
			resetShark();
			//모든 상어에 대해 실행
			for (int j = sharks.size()-1; j >=0 ; j--) {
				SharkMove(sharks.get(j));
			}
		}
		System.out.println(score);
	}
	
	//해당 열에 있는 상어 중에서 땅과 제일 가까운 상어 잡음
	public static void Fishing(int king) {
		//해당 열에 위치한 상어 중 가장 작은 열 값인 상어 제거
		for (int i = 1; i <= R; i++) {
			//해당 열에 상어 있으면 제일 가까운 상어 제거
			//상어의 크기z
			if(isShark[i][king]>=0) {
				for (int j = 0; j < sharks.size(); j++) {
					if(sharks.get(j).z==isShark[i][king]) {
						score += sharks.get(j).z;
						sharks.remove(j);
						return;
					}
					
				}
			}
		}
		//해당 열에 상어 없으면 0
	}
	
	
	public static void SharkMove(Shark shark) {
		//상어의 속력만큼 이동
		int nx = shark.r;
		int ny = shark.c;
		int dir = shark.d;
		//기존 위치 비우고
		for (int i = 0; i < shark.s; i++) {
			 nx += dx[dir];
			 ny += dy[dir];
			//벽 만나면 방향 전환하고 한칸 이동
			 if(nx<=0 || nx>R || ny<=0 || ny>C) {
				 nx -= dx[dir];
				 ny -= dy[dir];
				//상 하 우 좌
				//0 1 2 3
				 switch(dir) {
				 case 0:
					 dir=1;
					 break;
				 case 1:
					 dir=0;
					 break;
				 case 2:
					 dir=3;
					 break;
				 case 3:
					 dir=2;
					 break;
				 }
				 nx += dx[dir];
				 ny += dy[dir];
			}
		}
		shark.r = nx;
		shark.c = ny;
		shark.d = dir;
		//이동 끝나면 isShark 작업하러
		deleteShark(shark);
	}

	public static void deleteShark(Shark shark) {
		int x = shark.r;
		int y = shark.c;
		int size = shark.z;
		
		//해당 위치에 isShark==0이면 
		//해당위치에 상어 넣음 -> isShark==값
		if(isShark[x][y]==-1) {
			isShark[x][y]=size;
		}
		
		//해당 위치에 isShark값이 있으면 
		//그 위치의 상어와 크기 비교해서 
			//작으면 잡아먹힘 -> 상어안넣음, 제거
			//크면 잡아먹고 -> 상어 넣음
		else {
			if(isShark[x][y]>size) sharks.remove(shark);
			else {
				//작은 상어 삭제
				for (int i = 0; i < sharks.size(); i++) {
					if(sharks.get(i).z==isShark[x][y]) sharks.remove(i);
				}
				//큰 상어 넣기
				isShark[x][y]=size;
			}
		}
		return ;
	}
	public static void resetShark() {
		for (int i = 0; i < R+1; i++) {
			for (int j = 0; j < C+1; j++) {
				isShark[i][j]=-1;
			}
		}
	}
}
