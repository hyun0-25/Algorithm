package SW역량테스트.ps231012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 시작시간: 9:16
 * 종료시간:
 * 
 * 
 * 문제 해석
 * 공장에서 순서대로 q개의 명령에 따라 일을 진행
 * 일의 종류 5가지
 * 1. 공장 설립
 * 선물 공장에 m개의 벨트를 설치, 각 벨트 위에 n/m개의 물건들을 놓아 총 n개의 물건을 준비
 *  ex) n=12 -> m=3, 4개씩
 *  각 물건에는 고유번호ID와 무게
 * 2. 물건 하차
 * 상자 최대 무게 w_max가 주어짐
 * 1~m번까지 순서대로 벨트를 보며 
 * 		각 벨트의 맨 앞에 있는 선물 중 해당 선물 무게가 w_max이하면 하자
 * 										그렇지 않으면 벨트 맨뒤로 보냄
 * 		즉, 벨트에 있던 상자가 빠지면 한칸씩 앞으로 내려와야됨
 * 	=> 하자된 상자 무게의 총합 출력
 * 3. 물건 제거
 * 산타가 제거하기 원하는 물건의 고유 번호 r_id 주어짐
 * 	해당 고유 번호인 상자가 놓여있는 벨트가 있으면, 상자 제거하고 뒤에 있던 상자들은 앞으로 한칸씩 내려옴
 *	=> 상자 존재하면, r_id 출력
 *		존재하지 않으면 -1출력
 * 4. 물건 확인
 * 확인하기 원하는 물건의 고유 번호 f_id 주어짐
 * 해당 고유 번호에 해당하는 상자가 놓여있는 벨트 번호 출력/없으면 -1
 * 상자가 있는 경우, 해당 상자 위에 있는 모든 상자를 전부 앞으로 가져옴
 * 		가져올 시 순서는 그대로 유지되어야함
 * 5. 벨트 고장
 * 고장이 발생한 벨트의 번호 b_num 주어짐
 * b_num번째 벨트가 고장나면, 해당 벨트는 다시 사용불가
 * b_num벨트의 바로 오른쪽 벨트부터 순서대로 보며 아직 고장나지 않은 최초의 벨트위로 상자 순서대로 옮김
 * m번 벨트까지 봣는데도 고장나지 않은 벨트가 없으면, 다시 1번부터 벨트확인(최소 1개는존재함=모든벨트가 망가지는경우x)
 * 
 * 산타가 q번에 걸쳐 명령을 순서대로 진행하여 원하는 결과를 출력
 * 
 * 
 * 입력
 * 첫째줄 : 명령의 수 q
 * q개줄 : 명령의 정보
 * 공장설립 - 100 n개선물 m개벨트, (선물을 주어진 순서대로 n/m개씩 잘려 1~m벨트에 올림)
 *  -> 출력할건 없음
 * 물건하차 - 200 w_max
 * 	-> 하차된 상자 무게의 총합 출력
 * 물건제거 - 300 r_id
 * 	-> r_id상자 존재하면 r_id값 출력, 없으면 -1출력
 * 물건확인 - 400 f_id
 * 	-> f_id상자 존재하면 해당벨트번호 출력, 없으면 -1출력
 * 벨트고장 - 500 b_num
 * 	-> 이미 망가진 벨트면 -1을, 그렇지 않으면 b_num
 * 
 * 출력
 * 
 * 
 * 문제해결 프로세스
 * 0. list안에 벨트list넣기, 요소는 Box(id,weight)
 * 1. 공장설립 = add()
 * 2. 물건하차 = indexof(0)이 w_max를 넘지않으면 remove(0)+total에더하기
 * 								 넘으면 맨뒤로
 * 3. 물건제거 = 모든 박스 돌면서 r_id찾기
 * 			존재하면 r_id출력+ remove(i)
 * 			존재하지 않으면 -1출력
 * 4. 물건확인 = 모든 박스 돌면서 f_id찾기
 * 			존재하면 벨트번호 출력+그 위치 뒤에있는 모든 물건 맨뒤부터 remove하면서 다시 add(0,box)
 * 			존재하지 않으면 -1출력
 * 5. 벨트고장 = b_num에 해당하는 벨트 broken[]=true;
 * 			벨트 다음 인덱스 탐색하면서, broken[]=false면 그 벨트에 있는 모든 물건 addAll+해당벨트는removeAll
 * 				다음 인덱스가 i+1~m까지 없다면, 다시 1~i-1까지 탐색
 * 
 * 제한조건
 * 
 * 
 * 
 * 시간복잡도
 * 
 * 
 * 
 */

public class Main_CT_산타의선물공장 {
	static int n,m,boxnum;
	static boolean broken[];
	static StringBuilder sb = new StringBuilder();
	static List<Box> [] list;
	static class Box {
		int id,weight;
		public Box(int id, int weight) {
			super();
			this.id = id;
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "Box [id=" + id + ", weight=" + weight + "]";
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int q = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			int action = Integer.parseInt(st.nextToken());
			switch (action) {
			//공장설립
			case 100:
				n = Integer.parseInt(st.nextToken());
				m = Integer.parseInt(st.nextToken());
				boxnum = n/m;
				list = new ArrayList[m];
				broken = new boolean[m];
				for (int k = 0; k < m; k++) {
					list[k] = new ArrayList<>();
				}
				for (int k = 0; k < m; k++) {
					for (int j = 0; j < boxnum; j++) {
						int id = Integer.parseInt(st.nextToken());
						list[k].add(new Box(id,0));
					}
				}
				for (int k = 0; k < m; k++) {
					for (int j = 0; j < boxnum; j++) {
						int weight = Integer.parseInt(st.nextToken());
						int id = list[k].get(j).id;
						list[k].set(j, new Box(id,weight));
					}
				}
				break;
			//물건하차
			case 200:
				downbox(Integer.parseInt(st.nextToken()));
				break;
			//물건제거
			case 300:
				removebox(Integer.parseInt(st.nextToken()));
				break;
			//물건확인
			case 400:
				findbox(Integer.parseInt(st.nextToken()));
				break;
			//벨트고장
			case 500:
				brokenbelt(Integer.parseInt(st.nextToken()));
				break;
			default:
				break;
			}
		}
		System.out.println(sb);
	}
	/* 문제해결 프로세스
	 * 0. list안에 벨트list넣기, 요소는 Box(id,weight)
	 * 1. 공장설립 = add()
	 * 2. 물건하차 = indexof(0)이 w_max를 넘지않으면 remove(0)+total에더하기
	 * 								 넘으면 아무것도x
	 * 3. 물건제거 = 모든 박스 돌면서 r_id찾기
	 * 			존재하면 r_id출력+ remove(i)
	 * 			존재하지 않으면 -1출력
	 * 4. 물건확인 = 모든 박스 돌면서 f_id찾기
	 * 			존재하면 벨트번호 출력+그 위치 뒤에있는 모든 물건 맨뒤부터 remove하면서 다시 add(0,box)
	 * 			존재하지 않으면 -1출력
	 * 5. 벨트고장 = b_num에 해당하는 벨트 broken[]=true;
	 * 			벨트 다음 인덱스 탐색하면서, broken[]=false면 그 벨트에 있는 모든 물건 addAll+해당벨트는removeAll
	 * 				다음 인덱스가 i+1~m까지 없다면, 다시 1~i-1까지 탐색
	 */
	public static void brokenbelt(int b_num) {
		if(broken[b_num-1]) {
			sb.append(-1).append('\n');
			return;
		}
		broken[b_num-1] = true;
		//뒤쪽 벨트
		for (int i = b_num; i < m; i++) {
			if(!broken[i]) {
				list[i].addAll(list[b_num-1]);
				list[b_num-1].clear();
				sb.append(b_num).append('\n');
				return;
			}
		}
		//없으면 앞쪽 벨트
		for (int i = 0; i < b_num-1; i++) {
			if(!broken[i]) {
				list[i].addAll(list[b_num-1]);
				list[b_num-1].clear();
				sb.append(b_num).append('\n');
				return;
			}
		}
	}
	public static void findbox(int f_id) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < list[i].size(); j++) {
				if(list[i].get(j).id == f_id) {
					//해당벨트번호 출력
					sb.append(i+1).append('\n');
					//맨뒤부터 remove하고 0에 다시add
					int end = list[i].size()-1;
					for (int k = 0; k < end+1-j; k++) {
						Box box = list[i].get(end);
						list[i].add(0, box);
						list[i].remove(end+1);
					}
					return;
				}
			}
		}
		sb.append(-1).append('\n');
		return;
	}
	public static void removebox(int r_id) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < list[i].size(); j++) {
				if(list[i].get(j).id == r_id) {
					list[i].remove(j);
					sb.append(r_id).append('\n');
					return;
				}
			}
		}
		sb.append(-1).append('\n');
		return;
	}
	public static void downbox(int w_max) {
		int result = 0;
		for (int i = 0; i < m; i++) {
			if(broken[i] || list[i].size()==0) continue;
			
			if(list[i].get(0).weight<=w_max) {
				result += list[i].get(0).weight;
				list[i].remove(0);
			}
			else {
				list[i].add(list[i].get(0));
				list[i].remove(0);
			}
		}
		sb.append(result).append('\n');
	}

}
