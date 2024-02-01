package algo230807_Stack;

import java.util.EmptyStackException;

public class LinkedListStack<E> implements IStack<E> {
	private Node<E> top = null;
	
	@Override
	public void push(E e) { //첫노드로 삽입
		//그 전 top을 link로 놓고 삽입하는 e를 top으로 둠
		top = new Node<>(e, top);
	}

	@Override
	public E pop() { //첫 노드 삭제 후 반환
		if(isEmpty()) {
			//UncheckedException
			throw new EmptyStackException();
		}
		Node<E> popNode = top;
		top = popNode.getLink();
		popNode.setLink(null);
		return popNode.getData();
	}

	@Override
	public E peek() {
		if(isEmpty()) {
			//UncheckedException
			throw new EmptyStackException();
		}
		return top.getData();
	}

	@Override
	public int size() {
		int size = 0;
		for(Node<E> temp = top; temp!=null; temp=temp.getLink()) {
			++size;
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		return top == null;
	}

}
