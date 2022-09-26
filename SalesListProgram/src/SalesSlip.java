
import java.util.ArrayList;

public class SalesSlip {
	
	ArrayList<SalesItem> listofItems = new ArrayList<SalesItem>();
	
	public SalesSlip() {
		listofItems = new ArrayList<SalesItem>();
	}
	
	public void addSalesSlip(SalesItem salesitem) {
		listofItems.add(salesitem);
	}

	public String toString() {
		String stringList = "";
		for (int i = 0; i < listofItems.size(); i++) {
			SalesItem temp = listofItems.get(i);
			stringList += temp.toString() + "\n";
		}
		return stringList;
	}
	
	public String getTotalCost() {
		
		double totalCost = 0;
		double bought = 0;
		
		for (int i = 0; i < listofItems.size(); i++) {
			SalesItem temp = listofItems.get(i);
			bought = temp.getQuantity() * temp.getCost();
			totalCost += bought;
		}
		
		String cost = "$" + String.format("%.2f", totalCost);
		return cost;	
	}
}