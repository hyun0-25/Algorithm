package algo230808_Tree;

public class CompleteBinaryTreeTest {

	public static void main(String[] args) {
		int size=9;
		CompleteBinaryTree<Character> tree = new CompleteBinaryTree<>(size);
		for (int i = 0; i < size; i++) {
			tree.add((char)(65+i));
		}
//		tree.bfs();
//		tree.bfs2();
//		tree.bfs3();
		
//		tree.dfsByPreOrder(1);
//		System.out.println();
//		tree.dfsByInOrder(1);
//		System.out.println();
		tree.dfsByPostOrder(1);
		System.out.println();
//		tree.dfs(); //전위 순회 preorder와 동일한 결과
		tree.dfs2(); //후위 순회 postorder 역순과 동일한 결과
	}

}
