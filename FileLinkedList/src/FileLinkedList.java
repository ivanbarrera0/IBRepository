
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

public class FileLinkedList extends JFrame implements ActionListener {

	JMenuBar mb;
	JMenu file;
	JMenuItem open;
	JTextArea ta;

	FileLinkedList() {
		open = new JMenuItem("Open File");
		open.addActionListener(this);
		file = new JMenu("File");
		file.add(open);
		mb = new JMenuBar();
		mb.setBounds(0, 0, 800, 20);
		mb.add(file);
		ta = new JTextArea(800, 800);
		ta.setBounds(0, 20, 800, 800);
		add(mb);
		add(ta);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String filepath = f.getPath();
				LinkedList<Integer> Nums = new LinkedList<Integer>();
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "", s2 = "";
					while ((s1 = br.readLine()) != null) {
						String strArray[] = s1.split(" ");
						for(int j = 0; j < strArray.length; j++) {
							// Checks to see if the string is a number
							if(strArray[j].matches("[0-9]+"))
								Nums.add(Integer.parseInt(strArray[j])); 
						}
					}
					double sum = 0.0;
					for(int j = 0; j < Nums.size(); j++) {
						sum += Nums.get(j);
					}
					double mean = sum / Nums.size();
					double standardDeviation = 0.0;
					double sq;
					double res;
					
					for (int j = 0; j < Nums.size(); j++) {
						standardDeviation = standardDeviation + Math.pow((Nums.get(j) - mean), 2);
					}

					sq = standardDeviation / Nums.size();
					res = Math.sqrt(sq);
					
					s2 = "This is the sum: " + sum + "\nThis is the mean: " + mean + 
							"\nThis is the standard deviation: " + res;
					
					ta.setText(s2);
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		FileLinkedList om = new FileLinkedList();
		om.setSize(500, 500);
		om.setLayout(null);
		om.setVisible(true);
		om.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
