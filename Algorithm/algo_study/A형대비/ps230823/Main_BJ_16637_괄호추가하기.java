package A형대비.ps230823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

/**
 * 시작시간: 
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 길이가 N인 수식
 * 수식은 0~9인 정수와 연산자(+,-,x)로 이루어짐
 * 연산자 우선순위는 모두 동일, 왼쪽부터 순서대로 계산
 * 수식에 괄호를 추가하면 괄호 안에 들어있는 식을 먼저 계산
 * 괄호 안에는 연산자가 하나만 들어 있어야함
 * 괄호를 적절히 추가해 만들 수 있는 식의 결과의 최댓값
 * 추가하는 괄호 개수의 제한X, 추가하지 않아도 됨
 * 
 * 입력
 * 첫째줄: 수식의 길이 N
 * 둘째줄: 수식
 * 
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스 (부분집합)
 * 부분집합인 이유 - 선택하는 괄호의 개수는 0~N/2/2개 이므로
 * 				개수가 일정하지 않다.
 * 
 * 1. 부호는 N/2개
 * 2. 1~N/2개 중 부분집합 선택
 * 	2-1. 앞서 선택한 idx와 idx+1인 경우는(인접한 괄호 (1+(2)*3)같은건 존재하지 않기 때문) 선택하지 않음
 * 	2-2. 선택 했다면, 선택한 idx 저장하기
 * 3. 선택한 부분 집합들의 인접한 두개 숫자 먼저 계산 (cal함수)
 * 4. (이미 처리된 괄호들..) 3번 결과 큐에 넣기
 * 5. 큐에 들어있는 숫자 계산 (cal함수)
 * 6. 결과 출력
 * 
 *  * 큐 2개 필요
 * 큐1 괄호 처리 먼저 -> 계산하는 함수 -> 계산 결과 큐2에 넣음
 * 괄호 전부 처리한 큐2 -> 계산하는 함수
 * 
 * 제한조건
 * N<=19
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_BJ_16637_괄호추가하기 {
	static int N, operator [];
	static int max = Integer.MIN_VALUE;
	static String line[] ;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		if(N==1) {
			System.out.println(br.readLine());
			System.exit(0);
		}
		String str = br.readLine();
		line = new String[str.length()];
		int oi = 0;
		for (int i = 0; i < N; i++) {
			char c = str.charAt(i);
			line[i] = Character.toString(c);
		}
		
		operator = new int[N/2];
		for (int i = 0; i < N/2; i++) {
			operator[i] = i;
		}
		sel(0, new ArrayList<Integer>());
		System.out.println(max);
		
	}
	/* 문제해결 프로세스 (부분집합)
	 * 부분집합인 이유 - 선택하는 괄호의 개수는 0~N/2/2개 이므로
	 * 				개수가 일정하지 않다.
	 * 
	 * 1. 부호는 N/2개
	 * 2. 0~N/2개 중 부분집합 선택
	 * 	2-1. 앞서 선택한 idx와 idx+1인 경우는(인접한 괄호 (1+(2)*3)같은건 존재하지 않기 때문) 선택하지 않음
	 * 	2-2. 선택 했다면, 선택한 idx 저장하기
	 * 3. 선택한 부분 집합들의 인접한 두개 숫자 먼저 계산 (cal함수)
	 * 4. (이미 처리된 괄호들..) 3번 결과 큐에 넣기
	 * 5. 큐에 들어있는 숫자 계산 (cal함수)
	 * 6. 결과 출력
	 * 
	 *  * 큐 2개 필요
	 * 큐1 괄호 처리 먼저 -> 계산하는 함수 -> 계산 결과 큐2에 넣음
	 * 괄호 전부 처리한 큐2 -> 계산하는 함수
	 */
	public static void sel(int cnt, ArrayList<Integer> list) {
		if(cnt==N/2) {
			if(list.size()>0) {
				addqueue(list);
			}
			return;
		}
		
		//선택 x
		sel(cnt+1, list);
		
		//선택 o
		//list의 마지막으로 넣은 요소와 1차이나면 못넣음
		//						2차이나는거 넣음
		if(list.size()>0) {
			int x = operator[cnt];
			if(list.get(list.size()-1)+1 != x) {
				list.add(x);
				sel(cnt+1, list);
				list.remove(list.size()-1);
			}
		}
		else {
			list.add(operator[cnt]);
			sel(cnt+1, list);
			list.remove(list.size()-1);
		}
	}
	
	public static void addqueue(ArrayList<Integer> nums) {
		Queue<String> q = new ArrayDeque<>();
		int end = 0;
		for (int i = 0; i < nums.size(); i++) {
			int idx = nums.get(i);
			int a = idx*2;
			int b = idx*2+2;
			//괄호 시작 전까지 모두 넣음
			for (int k = end; k < a; k++) {
				q.add(line[k]);
			}
			end = b+1;
			//a와 idx와 b연산
			int c = calculate(line[a],line[idx*2+1],line[b]);
			q.add(Integer.toString(c));
		}
		//남은 뒤 원소들 넣기
		for (int i = end; i < N; i++) {
			q.add(line[i]);
		}
		
		findmax(q);
	}
	public static int calculate(String first, String oper, String second) {
		int output = 0;
		int num1 = Integer.parseInt(first);
		int num2 = Integer.parseInt(second);
		switch(oper) {
		case "+":
			output = num1+num2;
			break;
		case "-":
			output = num1-num2;
			break;
		case "*":
			output = num1*num2;
			break;
		}
		return output;
	}
	
	public static void findmax(Queue<String> queue) {
		String a = queue.poll();
		String s="",b="";
		int size = queue.size();
		for (int i = 0; i < size; i++) {
			//기호 꺼냄
			if(i%2==0) s = queue.poll();
			else {
				b = queue.poll();
				a = Integer.toString(calculate(a,s,b));
			}
		}
		int result = Integer.parseInt(a);
		max = Math.max(max, result);
	}
}
