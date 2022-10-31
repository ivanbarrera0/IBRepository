
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

public class KeywordHashTable {

	public static void main(String[] args) {

		File inputFile;
		Scanner fileInputScan = null;

		try {
			inputFile = new File("C:/Users/Ivan/Downloads/keywords.txt");
			fileInputScan = new Scanner(inputFile);
			HashTableEntry(fileInputScan);
		} catch (FileNotFoundException e) {
			System.out.println("Error - File cannot be found.");
		} finally {
			if (fileInputScan != null)
				fileInputScan.close();
		}
	}

	public static void HashTableEntry(Scanner fileInputScan) {

		long startTime = System.nanoTime();
		Vector<String> vec = new Vector<String>();
		String keyword = "";
		int lineCount = 0;

		while (fileInputScan.hasNextLine()) {

			keyword = fileInputScan.nextLine();

			String strArray[] = keyword.split(" ");
			for (int j = 0; j < strArray.length; j++) {
				// Checks for comments 
				if (strArray[j].startsWith("//") || strArray[j].startsWith("/*") || strArray[j].startsWith("*")
						|| strArray[j].endsWith("*/")) {
					continue;
				}
				// Checks to see if the string only alphabetic
				else if (strArray[j].matches("^[a-zA-Z]*$")) {
					vec.addElement(strArray[j]);
				}
			}
			lineCount++;

		}
		buildHashtable(vec);
		long endTime = System.nanoTime();
		System.out.println("The number of lines: " + lineCount);
		System.out.println("It took " + ((endTime - startTime) / 1000000) + " milliseconds");
	}

	public static void buildHashtable(Vector<String> vec) {
		Hashtable<Integer, String> hash = new Hashtable<Integer, String>();

		for (int i = 0; i < vec.size(); i++) {
			String word = vec.get(i);
			Integer index;
			int key = vec.indexOf(i);
			int g = 31;
			int length = word.length();
			// First and last character respectively
			char fletter = word.charAt(0);
			char lletter = word.charAt(word.length() - 1);
			index = (length + (26 * fletter) + (g * lletter) % vec.size());
			hash.put(index, word);
		}
		String str2 = hash.toString();
		System.out.println(str2);
		System.out.println("This is the number of keywords: " + vec.size());
	}
}
