
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

public class CountingLines extends JFrame implements ActionListener {

	JMenuBar mb;
	JMenu file;
	JMenuItem open;
	JTextArea ta;

	CountingLines() {
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
				int count = 0;
				int forcount = 0;
				int whilecount = 0;
				int ifcount = 0;
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "", s2 = "";
					while ((s1 = br.readLine()) != null) {
						String strArray[] = s1.split("\\\\r?\\\\n");
						for (int j = 0; j < strArray.length; j++) {
							// Checks for white space or comments
							if (!(strArray[j].isBlank() || strArray[j].contains("//") || strArray[j].startsWith("/*")
									|| strArray[j].startsWith("*") || strArray[j].endsWith("*/"))) {
								if (strArray[j].contains("for(")) {
									forcount++;
								} else if (strArray[j].contains("while(")) {
									whilecount++;
								} else if (strArray[j].contains("if(")) {
									ifcount++;
								}
								count++;
							}
						}
					}
					s2 = "The lines of code are: " + count + "\nThe number of for loops: " + forcount
							+ "\nThe number of while loops: " + whilecount + "\nThe number of if loops: " + ifcount;

					ta.setText(s2);
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		CountingLines om = new CountingLines();
		om.setSize(500, 500);
		om.setLayout(null);
		om.setVisible(true);
		om.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
