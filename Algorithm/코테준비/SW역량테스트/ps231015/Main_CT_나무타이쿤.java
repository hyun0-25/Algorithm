package SW역량테스트.ps231015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 시작: 11:54
 * 종료:
 * 
 * 문제해석
 * nxn격자에 서로 다른 높이를 가진 리브로수
 * 특수 영양제는 1개 리브로수의 높이를 1증가
 * 해당 땅에 씨앗만 있는 경우 높이 1의 리브로수 만듦
 * 초기 특수 영양제는 nxn격자의 좌파단 4개칸에 주어짐
 * 영양제 이동규칙
 * - 이동방향과 이동칸수
 * - 1~8번 이동방향 : 우 우상 상 좌상 좌 좌하 하 우하
 * - 이동 칸수만큼 방향으로 이동
 * - 격자의 모든 행열은 끝과 끝이 연결, 즉 반대편으로 나옴
 * 리브로수 성장
 * 1. 특수 영양제 이동
 * 2. 이동후 해당 위치에 투입, 땅에 있던 영양제 사라짐
 * 3. 투입한 리브로수는 대각선으로 인접한 방향에 높이가 1이상인 리브로수의 개수만큼 높이가 더 성장
 * 		대각선 인접 방향이 격자 벗어나는 경우는 세지 않음
 * 4. 투입한 리브로수를 제외하고 높이가 2이상인 리브로수는 높이2를 베어서 
 * 		잘라낸 리브로수로 특수 영양제를 사고, 해당 위치에 특수 영양제 올려둠
 * 
 * 
 * 입력
 * 첫째줄: n격자크기, m년수
 * n개줄: 리브로수 정보
 * m개줄: 각 년도의 이동규칙
 * 	d-방향1~8, p-이동칸수
 * 
 * 출력
 * 
 * 
 * 문제해결프로세스
 * 0. 초기 특수 영양제 위치
 * 		좌하단 4칸
 * 1. 특수 영양제 이동
 * 		isspecial[][]=true면 이동
 * 2. 영양제 투입 -> 나무성장
 * 		isspecial[][]=true인 위치의 대각선4방의 개수 세기
 * 		한개씩하면 안됨 -> 따로 배열생성 grow[][]후 저장
 * 		map[][]+=grow[][]
 * 3. 새로운 영양제 생성
 * 		map[][]>=1 + isspecial[][]=false이면 isspeical[][]=true
 * 		isspecial[][]=true(기존영양제)이면 isspeical[][]=false; 
 * 4. 1~3번 m번 반복
 * 5. 남은 나무높이 출력
 * 
 * 제한사항
 * 
 * 시간복잡도
 * 
 * 
 */

public class Main_CT_나무타이쿤 {
	static int N,M,map[][];
	static int dx[] = {0,-1,-1,-1,0,1,1,1};
	static int dy[] = {1,1,0,-1,-1,-1,0,1};
	static boolean special[][];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		special = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j]= Integer.parseInt(st.nextToken());
			}
		}
		//초기 영양제
		special[N-1][0] = true;
		special[N-2][0] = true;
		special[N-1][1] = true;
		special[N-2][1] = true;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int p = Integer.parseInt(st.nextToken());
//			for (int j = 0; j < N; j++) {
//				System.out.println(Arrays.toString(special[j]));
//			}
			special = movespecial(d,p);
//			for (int j = 0; j < N; j++) {
//				System.out.println(Arrays.toString(special[j]));
//			}
//			for (int j = 0; j < N; j++) {
//				System.out.println(Arrays.toString(map[j]));
//			}
//			System.out.println();
			growtree();
//			for (int j = 0; j < N; j++) {
//				System.out.println(Arrays.toString(map[j]));
//			}
			newspecial();
//			for (int j = 0; j < N; j++) {
//			System.out.println(Arrays.toString(special[j]));
//			}
		}
		int total = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				total+=map[i][j];
			}
		}
		System.out.println(total);
	}

	/* 문제해결프로세스
	 * 0. 초기 특수 영양제 위치
	 * 		좌하단 4칸
	 * 1. 특수 영양제 이동
	 * 		isspecial[][]=true면 이동
	 * 2. 영양제 투입 -> 나무성장
	 * 		isspecial[][]=true인 위치의 대각선4방의 개수 세기
	 * 		한개씩하면 안됨 -> 따로 배열생성 grow[][]후 저장
	 * 		map[][]+=grow[][]
	 * 3. 새로운 영양제 생성
	 * 		map[][]>=2 + isspecial[][]=false이면 isspeical[][]=true
	 * 		isspecial[][]=true(기존영양제)이면 isspeical[][]=false; 
	 * 4. 1~3번 m번 반복
	 * 5. 남은 나무높이 출력
	 */
	private static void newspecial() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(map[i][j]>=2 && !special[i][j]) {
					special[i][j]=true;
					map[i][j]-=2;
				}
				else if(special[i][j]) {
					special[i][j] = false;
				}
			}
		}
		
	}
	private static void growtree() {
		int grow[][] = new int [N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(special[i][j]) {
					int cnt = 0;
					for (int k = 1; k < 8; k+=2) {
						int nx = i+dx[k];
						int ny = j+dy[k];
						if(!rangecheck(nx,ny)) continue;
						if(map[nx][ny]>0) cnt++;
//						System.out.println(nx+" "+ny);
					}
//					System.out.println(cnt);
					grow[i][j] = cnt;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j]+=grow[i][j];
			}
		}
	}
	
	private static boolean[][] movespecial(int d, int p) {
		boolean[][] copyspecial = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(special[i][j]) {
					int nx = i;
					int ny = j;
					for (int k = 0; k < p; k++) {
						nx += dx[d];
						ny += dy[d];
						nx = rangex(nx);
						ny = rangey(ny);
					}
					copyspecial[nx][ny] = true;
					map[nx][ny]++;
				}
			}
		}
		
		return copyspecial;
	}
	
	private static int rangex(int rx) {
		if(rx<0) return N+rx;
		else if(rx>=N) return rx-N;
		return rx;
	}
	private static int rangey(int ry) {
		if(ry<0) return N+ry;
		else if(ry>=N) return ry-N;
		return ry;
	}
	private static boolean rangecheck(int rx, int ry) {
		return rx>=0 && rx<N && ry>=0 && ry<N;
	}

}
