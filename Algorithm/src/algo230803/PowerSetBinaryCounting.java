package algo230803;

public class PowerSetBinaryCounting {
	static int N =5;
	static int [] arr = {1,3,5,7,9};
	public static void main(String[] args) {
//		for (int flag=0; flag<=Math.pow(2, N)-1; flag++) {
		for (int flag=0; flag<(1<<N); flag++) {
			System.out.printf("%5s\n",Integer.toBinaryString(flag));
			//0번째 요소가 선택됐는지
//			if((flag & 1)>0) {
//				System.out.println("0번째 요소 선택");
//			}
//			if((flag & 1<<1)>0) {
//				System.out.println("1번째 요소 선택");
//			}
//			if((flag & 1<<2)>0) {
//				System.out.println("2번째 요소 선택");
//			}
//			if((flag & 1<<3)>0) {
//				System.out.println("3번째 요소 선택");
//			}
//			if((flag & 1<<4)>0) {
//				System.out.println("4번째 요소 선택");
//			}
			for (int i = 0; i < N; i++) {
				if((flag & 1<<i)>0) {
					System.out.println(arr[i]+ " ");
				}
			}
			System.out.println();
		}
	}

}
