
public class Textbook implements java.io.Serializable {
	public Integer sku;
	public String title;
	public Double price;
	public Integer quantity;

	public Textbook(Integer SKU, String Title, Double Price, Integer Quantity) {
		sku = SKU;
		title = Title;
		price = Price;
		quantity = Quantity;
	}

	public Integer getSku() {
		return sku;
	}

	public String getTitle() {
		return title;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String toString() {
		return sku + " " + title + " " + price + " " + quantity;
	}

}
