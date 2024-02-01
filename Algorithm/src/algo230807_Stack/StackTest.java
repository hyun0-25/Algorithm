package algo230807_Stack;

public class StackTest {

	public static void main(String[] args) {
		IStack<String> stack = new LinkedListStack<>();
		System.out.println(stack.size()+"//"+stack.isEmpty());
		stack.push("서다찬");
		stack.push("전은희");
		stack.push("유현지");
		stack.push("성준혁");
		stack.push("빅상진");
		stack.push("칙칙폭폭쌤");
		stack.push("하이쌤");
		stack.push("이기성");
		stack.push("여일구");
		System.out.println(stack.peek());
		System.out.println(stack.size()+"//"+stack.isEmpty());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.size()+"//"+stack.isEmpty());
		System.out.println(stack.pop());
	}

}
