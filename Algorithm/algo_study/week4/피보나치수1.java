package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 피보나치수1 {
	static int n, cnt1, cnt2;
	static long [] f;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		f = new long [n+1];
		cnt1=1;
		recursivefibo(n);
		dpfibo(n);
		System.out.println(cnt1 +" "+ cnt2);
	}
	
	public static int recursivefibo(int n) {
		if(n==1 || n==2) {
			return 1;
		}
		else {
			cnt1++;
			return recursivefibo(n-1)+ recursivefibo(n-2);
		}
		
	}
	
	
	public static long dpfibo(int n) {
		f[1]=1;
		f[2]=2;
		for (int i = 3; i <= n; i++) {
			cnt2++;
			f[i] = f[i-1]+f[i-2];
		}
		return f[n];
	}
}
