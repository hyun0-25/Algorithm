package ps230802_Combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 남학생
 * 스위치 번호가 받은 수의 배수면 -> 스위치의 상태 바꿈
 * 
 * 여학생
 * 받은 수와 같은 번호가 붙은 스위치를 중심으로 좌우 대칭이면서 가장 많은 스위치를 포함하는 구간
 * -> 그 구간에 속한 스위치의 상태 모두 바꿈
 * */

public class Main_BJ_1244_스위치켜고끄기_이현영 {
	static int N, M;
	static StringTokenizer st ;
	static int [] state;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		//입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		state = new int[N+1];
		for (int i = 1; i <= N; i++) {
			state[i] = Integer.parseInt(st.nextToken());
		}
		
		M = Integer.parseInt(br.readLine());
		map = new int[M][2];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			//성별
			map[i][0] = Integer.parseInt(st.nextToken());
			//받은 스위치 번호
			map[i][1] = Integer.parseInt(st.nextToken());
		}
		
		for (int i = 0; i < M; i++) {
			int num = map[i][1];
			//남학생
			if(map[i][0]==1) {
				//스위치 최대 인덱스까지
				while(num<=N) {
					//배수 스위치 상태 바꿈
					change(num);
					num+=map[i][1];
				}
			}
			
			//여학생
			else {
				int num_left = num-1;
				int num_right = num+1;
				//맨처음 받은 스위치 상태 바꿈
				change(num);
				while(num_right<=N && num_left>0) {
					//대칭 스위치 상태가 같으면
					if(state[num_left]==state[num_right]) {
						change(num_left);
						change(num_right);
						num_left -= 1;
						num_right += 1;
					}
					else {
						break;
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= N; i++) {
			sb.append(state[i]).append(' ');
			if(i%20==0) {
				sb.append('\n');
			}
		}
		System.out.println(sb);
		
	}
	private static void change(int num) {
		if (state[num]==0) {
			state[num]=1;
		}
		else {
			state[num]=0;
		}
	}
}
