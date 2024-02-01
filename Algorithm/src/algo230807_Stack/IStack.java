package algo230807_Stack;

public interface IStack<E> {
	void push(E e);
	E pop();
	E peek();
	int size();
	boolean isEmpty();
}
