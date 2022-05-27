public class Product
{
	private int code;
	private String name;
	private double price;
	private int stock;
	private int iva;

	public Product(Product copy) {
		this.code = copy.code;
		this.name = copy.name;
		this.price = copy.price;
		this.iva = copy.iva;
	}

	public Product(String name, double price, int stock, int iva) {
		this.code = 0;
		this.name = name;
		this.price = price;
		this.iva = iva;
	}

	public Product(int code, String name, double price, int stock, int iva) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.iva = iva;
	}

	public int get_code() {
		return code;
	}

	public String get_name() {
		return name;
	}

	public double get_price() {
		return price;
	}

	public int get_stock() {
		return stock;
	}

	public int get_iva() {
		return iva;
	}

	public void set_code(int code) {
		this.code = code;
	}

	public void set_name(String name) {
		this.name = name;
	}

	public void set_price(int price) {
		this.price = price;
	}

	public void set_stock(int stock) {
		this.stock = stock;
	}

	public void set_iva(int iva) {
		this.iva = iva;
	}


	@Override
	public String toString() {
		return "╠ " + code + "\t" + name + "\t" + price + "\t" + iva + "%";
	}
}
