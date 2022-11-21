import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class CampusTextBookStore {

	private JFrame frame;
	private JTextField textField;
	private JButton addItembtn;
	private JTextArea textArea;
	private JButton displayStorebtn;
	private JButton deleteItembtn;
	private JButton displayTextbookbtn;
	private JScrollPane scrollPane;

	ArrayList<Textbook> textbookList = new ArrayList<Textbook>();
	String a;
	String strArray[];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CampusTextBookStore window = new CampusTextBookStore();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CampusTextBookStore() {

		try {
			FileInputStream readData = new FileInputStream("textbookList");
			ObjectInputStream readStream = new ObjectInputStream(readData);

			ArrayList<Textbook> textBookList2 = (ArrayList<Textbook>) readStream.readObject();
			readStream.close();
			readData.close();
			for (int i = 0; i < textBookList2.size(); i++) {
				textbookList.add(textBookList2.get(i));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("No previous inventory found");
			c.printStackTrace();
			return;
		}

		initialize();

		addItembtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});

		displayStorebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayStoreInventory();
			}
		});

		deleteItembtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});

		displayTextbookbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextbook();
			}
		});
	}

	private void addItem() {
		a = textField.getText();
		int t = 0;
		String strArray[] = a.split(" ");

		// Check that the format is correct
		if (correctFormat() == true) {
			textbookList.add(new Textbook(Integer.parseInt(strArray[t]), strArray[t + 1],
					Double.parseDouble(strArray[t + 2]), Integer.parseInt(strArray[t + 3])));
			textArea.setText("New textbook added!");
			saveStoreInventory();
		}
	}

	private void deleteItem() {

		a = textField.getText();
		String regex = "\\d+";
		if (a.matches(regex) == true) {

			int test = Integer.parseInt(a);
			if (textbookList.isEmpty() == true) {
				textArea.setText("There are no textbooks to delete!");
			} else {
				for (int i = 0; i < textbookList.size(); i++) {
					if (textbookList.get(i).getSku() == test) {
						textbookList.remove(i);
						textArea.setText("Textbook deleted");
						saveStoreInventory();
						break;
					} else {
						textArea.setText("Textbook not found!");
						continue;
					}
				}
			}

		} else {
			textArea.setText("Input is not a number!");
		}

	}

	private void displayTextbook() {

		a = textField.getText();
		String dis = "";
		String regex = "\\d+";
		if (a.matches(regex) == true) {

			int test = Integer.parseInt(a);
			if (textbookList.isEmpty() == true) {
				textArea.setText("There are no textbooks to display!");
			} else {
				for (int i = 0; i < textbookList.size(); i++) {
					if (textbookList.get(i).getSku() == test) {
						dis += textbookList.get(i);
						textArea.setText(dis);
						break;
					} else {
						textArea.setText("Textbook not found!");
						continue;
					}
				}
			}

		} else {
			textArea.setText("Input is not a number!");
		}

	}

	private boolean correctFormat() {
		int t = 0;
		String strArray[] = a.split(" ");
		try {
			Integer.parseInt(strArray[t]);
			Double.parseDouble(strArray[t + 2]);
			Integer.parseInt(strArray[t + 3]);
			return true;
		} catch (NumberFormatException e) {
			textArea.setText("Incorrect Format!");
		}
		return false;
	}

	private void displayStoreInventory() {

		String dis = "SKU Title Price Quantity \n";

		if (textbookList.isEmpty()) {
			dis = "There are no textbooks in the store!";
		} else {

			for (int i = 0; i < textbookList.size(); i++) {
				dis += textbookList.get(i) + "\n";
			}
		}

		textArea.setText(dis);
	}

	private void saveStoreInventory() {
		try {
			FileOutputStream writeData = new FileOutputStream("textbookList");
			ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			writeStream.writeObject(textbookList);
			writeStream.flush();
			writeStream.close();
			writeData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Campus Textbook Store");
		lblNewLabel.setBounds(154, 10, 142, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Input a new textbook, in this order sku, title, price, quantity");
		lblNewLabel_1.setBounds(46, 51, 353, 13);
		frame.getContentPane().add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(108, 74, 209, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		addItembtn = new JButton("Input new textbook");
		addItembtn.setBounds(118, 129, 147, 21);
		frame.getContentPane().add(addItembtn);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 196, 237, 57);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		displayStorebtn = new JButton("Display Store");
		displayStorebtn.setBounds(275, 129, 151, 21);
		frame.getContentPane().add(displayStorebtn);

		deleteItembtn = new JButton("Delete textbook");
		deleteItembtn.setBounds(118, 165, 147, 21);
		frame.getContentPane().add(deleteItembtn);

		displayTextbookbtn = new JButton("Display Textbook");
		displayTextbookbtn.setBounds(275, 165, 151, 21);
		frame.getContentPane().add(displayTextbookbtn);
	}
}
