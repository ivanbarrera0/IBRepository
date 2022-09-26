
public class SalesItem {
	
	private String item;
	private Double cost;
	private Integer quantity;
	
	public SalesItem(String i, String c, String q) {
		
		item = i;
		cost = Double.parseDouble(c);
		quantity = Integer.parseInt(q);
	}
	
	public String getItem() {
		return item;
	}
	
	public Double getCost() {
		return cost;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public String toString() {
		return item + " \t" + "$" +  cost + " \t" + quantity;
	}
}

