import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 해석
 * 유치원까지 갈 수 있는 지하 고속도로를 건설
 * 전체 도면의 크기는 R행 C열 구획을 나누어짐
 * 각 칸은 7가지 블록을 사용하거나 비어 있음
 * | - + 1 2 3 4
 * M은 집-출발/ Z는 유치원-도착
 * 
 * 도면의 그림 한 칸이 지워져 있을 때 빈칸으로 되어있음
 * 
 * 입력
 * 첫째줄: R,C
 * 둘째줄부터~: R행 C열의 구획정보
 * 			7가지 블록과 .은 빈칸
 * 			불필요한 블록은 존재하지 않음, 없어진 블록을 추가하면 모든 블록은 이동가능한 통로가 됨
 * 
 * 출력
 * 어떤 칸을 지웠고 그 칸에는 원래 어떤 블록이 있었는지 구하여라
 * 
 * 
 * 1
 * 3 7
 * .......
 * .M-.-Z.
 * .......
 * 
 * 
 */


public class Algo3_daejeon_06_이현영 {
	static int T, R, C;
	//초기상태: 시작 지점 위치 -> 이동하는 좌표 위치
	static int idx_x, idx_y;
	
	//끊어진 지점 위치
	static int dest_x, dest_y;
	//끊어진 지점 길 모양 정보
	static char dest_c;
	//방향 정보 변수
	static int dir;
	//위,오른쪽,아래,왼쪽 순서
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	
	static char map[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
//		for (int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(br.readLine());
			//도면의 크기 R,C
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			//도면 정보 RxC 입력 받기
			map = new char[R][C];
			for (int i = 0; i < R; i++) {
				String str = br.readLine();
				for (int j = 0; j < C; j++) {
					map[i][j] = str.charAt(j);
					if(map[i][j]=='M') {
						idx_x=i;
						idx_y=j;
					}
					if(map[i][j]=='Z') {
						dest_x=i;
						dest_y=j;
					}
				}
			}
			//시작 위치의 사방을 탐색하여 빈칸이 아닌 곳 찾기
			for (int i = 0; i < 4; i++) {
				int x = idx_x+dx[i];
				int y = idx_y+dy[i];
				//그 방향으로 이동 시작
				if(x>=0 && x<R && y>=0 && y<C && map[x][y]!='.') {
					dir=i;
					move(map[x][y]);
					break;
				}
			}
			
			sb.append(dest_x+1).append(' ').append(dest_y+1).append(' ').append(dest_c);
//		}
		System.out.println(sb);
		
	}
	
	public static void move(char c) {
		//도면 정보에 따른 방향 전환
		switch(c) {
		case '|':
			break;
		case '-':
			break;
		case '+':
			break;
		case '1':
			if(dir==3) dir=2;
			else if(dir==0) dir=1;
			break;
			
		case '2':
			if(dir==2) dir=1;
			else if(dir==3) dir=0;
			break;
			
		case '3':
			if(dir==1) dir=0;
			else if(dir==2) dir=3;
			break;
			
		case '4':
			if(dir==1) dir=2;
			else if(dir==0) dir=3;
			break;
		}
		// 그 방향으로 이동
		idx_x += dx[dir];
		idx_y += dy[dir];
		
		if(idx_x>=0 && idx_x<R && idx_y>=0 && idx_y<C && map[idx_x][idx_y]=='Z') {
//			dest_c = map[dest_x][dest_y];
			return ;
		}
		//길은 무조건 이어져 있으므로, 이동한 좌표의 문자가 빈칸이면 길이 끊어진 위치임
		if(idx_x>=0 && idx_x<R && idx_y>=0 && idx_y<C && map[idx_x][idx_y]=='.') {
			//끊어진 위치 정보
			dest_x = idx_x;
			dest_y = idx_y;
			
			//길이 끊어진 위치에서 7가지 문자 시도
			for (int i = 0; i < 7; i++) {
				//한칸 뒤로 다시 왔다가
//				int x = idx_x-dx[dir];
//				int y = idx_y-dy[dir];
				//7가지 문자 시도 -> true면 길 이어짐 성공
				if(isDestination(i, dest_x, dest_y)) {
					break;
				};
			}
		}
		if(idx_x>=0 && idx_x<R && idx_y>=0 && idx_y<C ) {
			move(map[idx_x][idx_y]);
		}
		
		
	}
	static char street[] = {'|','-','+','1','2','3','4'};
	//7가지 모두 넣어봤을 때, 도착했는 지 확인
	public static boolean isDestination(int idx_s, int x, int y) {

		map[x][y] = street[idx_s];
		switch(map[x][y]) {
		case '|':
			break;
		case '-':
			break;
		case '+':
			break;
		case '1':
			if(dir==3) dir=2;
			else if(dir==0) dir=1;
			break;
			
		case '2':
			if(dir==2) dir=1;
			else if(dir==3) dir=0;
			break;
			
		case '3':
			if(dir==1) dir=0;
			else if(dir==2) dir=3;
			break;
			
		case '4':
			if(dir==1) dir=2;
			else if(dir==0) dir=3;
			break;
		}
		int nx = idx_x+dx[dir];
		int ny = idx_y+dy[dir];
		
		//선택한 7가지 길 중 하나로 이어서 시도했을 때, 그 다음 칸이 빈칸이 아닌 또다른 길로 이어지면 계속 이동
		if(map[nx][ny]!='.') {
			idx_x=nx;
			idx_y=ny;
			dest_c = map[nx][ny];
			return true;
		}
		return false;
		

	}
	

}
