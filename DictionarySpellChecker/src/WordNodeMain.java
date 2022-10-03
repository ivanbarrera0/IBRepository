
public class WordNodeMain {

	public static void main(String[] args) {

		DictionaryTree dictionary = new DictionaryTree();
		
		dictionary.addWordNode("carlos");
		dictionary.addWordNode("apple");
		dictionary.addWordNode("aloof");
		dictionary.addWordNode("attack");
		dictionary.addWordNode("trees");
		dictionary.addWordNode("oof");
		dictionary.addWordNode("doom");
		
		dictionary.searchWordNode("hungry");
		dictionary.searchWordNode("doom");
		dictionary.searchWordNode("oof");
		dictionary.inOrder();

	}

}
