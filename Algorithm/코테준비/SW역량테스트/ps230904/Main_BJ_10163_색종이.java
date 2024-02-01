package SW역량테스트.ps230904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main_BJ_10163_색종이 {
	static int N, map[][] = new int[1001][1001];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		int cnt=1;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken());
			int sy = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			for (int j = sx; j < sx+w; j++) {
				for (int j2 = sy; j2 < sy+h; j2++) {
					map[j][j2]=cnt;
				}
			}
			cnt++;
		}
		int cntarray[] = new int[cnt];
		for (int i = 0; i < 1001; i++) {
			for (int j = 0; j < 1001; j++) {
				if(map[i][j]!=0) {
					cntarray[map[i][j]]++;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < cnt; i++) {
			sb.append(cntarray[i]).append('\n');
		}
		System.out.println(sb);

	}

}
