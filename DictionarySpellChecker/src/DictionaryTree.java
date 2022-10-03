
public class DictionaryTree {

	public WordNode root;

	public DictionaryTree() {
		root = null;
	}

	public void addWordNode(String Word) {

		WordNode temp = new WordNode(Word);

		if (root == null) {
			root = temp;
		} else {
			insert(root, temp);
		}
	}

	public void searchWordNode(String Word) {

		if (root == null) {
			System.out.println("There is no words in the binary tree!");
		} else {
			deleteWordNode(root, Word);
		}
	}

	public void insert(WordNode myRoot, WordNode temp) {

		while (true) {

			if (temp.getWord().compareTo(myRoot.getWord()) == 0) {
				assert(temp.getWord().compareTo(myRoot.getWord()) == 0): "The words are not the same!";
				return;
			}

			if (temp.getWord().compareTo(myRoot.getWord()) <= -1) {
				if (myRoot.left != null)
					myRoot = myRoot.left;
				else {
					myRoot.left = temp;
					break;
				}
			} else {
				if (myRoot.right != null)
					myRoot = myRoot.right;
				else {
					myRoot.right = temp;
					break;
				}
			}
		}
	}

	public WordNode smallerWord(WordNode root) {
		if (root.left == null)
			return root;
		else {
			return smallerWord(root.left);
		}
	}

	public WordNode deleteWordNode(WordNode root, String temp) {

		if (root == null)
			return null;

		if (temp.compareTo(root.getWord()) == 0) {
			if (root.left != null && root.right != null) {
				WordNode tempRoot = root;
				WordNode NodeFromRight = smallerWord(tempRoot.right);
				root.word = NodeFromRight.word;
				root.right = deleteWordNode(root.right, NodeFromRight.getWord());
				// If the node only has a left child
			} else if (root.left != null) {
				root = root.left;
				// If the node only has a right child
			} else if (root.right != null) {
				root = root.right;
				// If the node has no child
			} else
				root = null;

		} else if (temp.compareTo(root.getWord()) <= -1) {
			if (root.left != null)
				root.left = deleteWordNode(root.left, temp);
			else {
				System.out.println("Word is not located");
			}
		} else {

			if (root.right != null)
				root.right = deleteWordNode(root.right, temp);
			else {
				System.out.println("Word is not located");
			}
		}

		return root;

	}

	public void inOrder() {
		inOrderRecursive(root);
	}

	private void inOrderRecursive(WordNode myRoot) {
		if (myRoot != null) {
			inOrderRecursive(myRoot.left);
			System.out.println(myRoot.getWord());
			inOrderRecursive(myRoot.right);
		}
	}
}
