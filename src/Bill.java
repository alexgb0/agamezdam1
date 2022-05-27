import java.time.LocalDate;
import java.util.ArrayList;

public class Bill
{
	private Client owner;
	private final ArrayList<Product> products;
	private int buy_id;


	public Bill(Client owner, ArrayList<Product> products, int buy_id)
	{
		this.owner = owner;
		this.products = products;
		this.buy_id = buy_id;
	}

	public Client get_client()
	{
		return owner;
	}

	public ArrayList<Product> get_products()
	{
		return products;
	}

	public void insert(Product product)
	{
		products.add(product);
	}

	public int get_buy_id()
	{
		return buy_id;
	}

	public void set_date(int buy_id)
	{
		this.buy_id = buy_id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("╔══════[ Bills : ").append(buy_id).append("]══════").append("\n");
		sb.append("║\n");
		for (Product product : products)
			sb.append("╠ ").append(product.toString()).append("\n");
		sb.append("║\n");
		sb.append("╚═════════════════════════════════").append("\n");

		return sb.toString();
	}
}
