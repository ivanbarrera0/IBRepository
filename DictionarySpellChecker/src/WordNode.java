
public class WordNode {
	
	// Data Members
	public String word;
	public WordNode left;
	public WordNode right;
	
	public WordNode(String word) {
		this.word = word;
		left = null;
		right = null;
	}
	
	public String getWord() {
		return word;
	}
}

