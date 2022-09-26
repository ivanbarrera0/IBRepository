import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class SalesListApp {

	private JFrame frame;
	private JTextArea salesList;
	private JTextArea totalSales;
	private JTextArea itemTxt;
	private JTextArea costTxt;
	private JTextArea quantityTxt;
	private JButton btnNewButton;
	private SalesSlip salesslip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SalesListApp window = new SalesListApp();
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
	public SalesListApp() {
		initialize();
		
		salesslip = new SalesSlip();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem();
			}
		});
	}
	
	private void addItem() {
		SalesItem salesitem = new SalesItem(itemTxt.getText(), costTxt.getText(), quantityTxt.getText());
		salesslip.addSalesSlip(salesitem);
		salesList.setText(salesslip.toString());
		totalSales.setText(salesslip.getTotalCost());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sales List");
		lblNewLabel.setBounds(171, 0, 87, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Item:");
		lblNewLabel_1.setBounds(47, 31, 45, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cost: $");
		lblNewLabel_2.setBounds(47, 67, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantity:");
		lblNewLabel_3.setBounds(47, 107, 65, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		btnNewButton = new JButton("Add Item to the Sales List");
		btnNewButton.setBounds(117, 137, 188, 21);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(205, 208, 2, 2);
		frame.getContentPane().add(scrollPane);
		
		salesList = new JTextArea();
		salesList.setBounds(117, 168, 188, 54);
		frame.getContentPane().add(salesList);
		
		JLabel lblNewLabel_4 = new JLabel("Total Sales:");
		lblNewLabel_4.setBounds(47, 240, 80, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		totalSales = new JTextArea();
		totalSales.setBounds(130, 234, 175, 22);
		frame.getContentPane().add(totalSales);
		
		itemTxt = new JTextArea();
		itemTxt.setBounds(117, 25, 188, 22);
		frame.getContentPane().add(itemTxt);
		
		costTxt = new JTextArea();
		costTxt.setBounds(117, 61, 188, 22);
		frame.getContentPane().add(costTxt);
		
		quantityTxt = new JTextArea();
		quantityTxt.setBounds(117, 101, 188, 22);
		frame.getContentPane().add(quantityTxt);
	}
}
