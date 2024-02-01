package ps230817;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작시간: 13:29
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 빵집이 있는 곳 R*C격자
 * 첫째열 - 근처 빵집의 가스관
 * 마지막열 - 원웅이의 빵집
 * 가스관과 빵집을 연결하는 파이프를 설치
 * 빵집과 가스관 사이에는 건물 존재, 건물이 있으면 파이프 놓을수 없음
 * 가스관과 빵집을 연결하는 모드 파이프라인은 첫째 열에서 시작~마지막열에서 끝남
 * 각 칸은 우,우상,우하로 연결가능 - 각 칸의 중심끼리 연결
 * 가스를 되도록 많이 훔침, 가스관과 빵집을 연결하는 파이프라인을 여러 개 설치
 * 경로는 겹칠 수 없고, 접할 수 없음, 즉 각 칸을 지나는 파이프는 1개
 * 가스관과 빵집을 연결하는 파이프라인의 최대 개수
 * 
 * 입력
 * 첫째줄: R, C
 * 다음R개줄: 빵집 근처의 모습 (.:빈칸, x:건물)
 * 
 * 출력
 * 원웅이가 놓을 수 있는 파이프라인의 최대 개수
 * 
 * 문제해결 프로세스 dfs
 * 우상,우,우하 순으로 탐색
 * 지나간 길은 X처리
 * 
 * 
 * 제한조건
 * R<=10000
 * C<=500
 * 
 * 
 * 시간복잡도
 * 
 * 
 */
public class Main_BJ_3109_빵집_이현영 {
	static int R,C,result;
	static char[][] street;
	static boolean [][] isVisit;
	//우상,우,우하
	static int dx[] = {-1,0,1};
	static int dy[] = {1,1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		street = new char[R][C];
		isVisit = new boolean[R][C];
		for (int i = 0; i < R; i++) {
			String str = br.readLine();
			for (int j = 0; j < C; j++) {
				street[i][j] = str.charAt(j);
			}
		}
		for (int i = 0; i < R; i++) {
			dfs(i,0);
		}
		
		System.out.println(result);

	}
	public static boolean dfs(int x, int y) {
		if(y==C-1) {
			result++;
			return true;
		}
		for (int i = 0; i < 3; i++) {
			int nx = x+dx[i];
			int ny = y+dy[i];
			if(nx<0 || nx>=R || ny<0 || ny>=C || street[nx][ny]=='x') {
				continue;
			}
			//else
			else if(street[nx][ny]=='.'){
				if(dfs(nx,ny)) {
					street[nx][ny]='x';
					return true;
				}
			}
		}
		street[x][y]='x';
		return false;
	}
}
