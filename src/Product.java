public class Product
{
	private int code;
	private String name;
	private int price;
	private int iva;

	public Product(int code, String name, int price, int iva) {
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

	public int get_price() {
		return price;
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

	public void set_iva(int iva) {
		this.iva = iva;
	}

	@Override
	public String toString() {
		return "" + code + "\t" + name + "\t" + price + "\t" + iva + "%";
	}
}
