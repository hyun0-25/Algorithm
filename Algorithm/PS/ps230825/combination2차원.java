package ps230825;

public class combination2차원 {
	static int R=3,C=4,M=3,K=2; //뽑는개수K
	static int []picked = new int [2];
	static int arr [][] 
			= {{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12}};

	public static void main(String[] args) {
		combination2(0,0);
	}
	
	public static void combination1(int cnt, int startR, int startC) {
		if(cnt==K) {
			
			return;
		}
		
		//유도 파트 요소 뽑기
		//2차원 배열의 요소 중 하나 뽑고 다음 요소 뽑기는 재귀로 넘기기
		for (int i = startR; i < R; i++) {
			for (int j = 0; j < C; j++) {
				//요소 뽑기
				picked[cnt] = arr[i][j];
				//다음 요소 뽑기는 재귀로 넘김
				if(j==C-1) combination1(cnt+1,i,j);
				else combination1(cnt+1,i,j);
			}
		}
	}
	
	public static void combination2(int cnt, int start) {
		if(cnt==K) {
			
			return;
		}
		
		//유도 파트 요소 뽑기
		for (int idx = 0; idx < R*C; idx++) {
			int r = idx/C;
			int c = idx%C;
			
			if(c > C-M) continue;  //M개를 연속해서 뽑을 수 없는 경우
			//요소 뽑기
			picked[cnt] = arr[r][c];
			//다음 요소 뽑기
			combination2(cnt+1,idx+M);
		}
	}

}
