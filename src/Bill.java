import java.time.LocalDate;
import java.util.ArrayList;

public class Bill
{
	private int id;
	private Client owner;
	private ArrayList<Product> products;
	private int buy_id;


	public Bill(Client owner, ArrayList<Product> products, int buy_id, int id)
	{
		this.id = id;
		this.owner = owner;
		this.products = (ArrayList<Product>) products.clone();
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

	public String toString(int total) {
		StringBuilder sb = new StringBuilder();
		sb.append("╔═══════════════════════[ Bills : ").append(buy_id).append(" ]════════════════════").append("\n");
		sb.append("║ Client: ").append(owner.get_nom()).append("\n");
		sb.append("║ Dni: ").append(owner.get_dni()).append("\n");
		sb.append("║ Date: ").append(LocalDate.now()).append("\n");
		sb.append("╟──────────────────────────────────────────────────────────────").append("\n");
		sb.append("║ Code\tName\tPrice\tStock\tIVA").append("\n");
		for (Product product : products)
			sb.append(product.toString()).append("\n");
		sb.append("╟──────────────────────────────────────────────────────────────").append("\n");
		sb.append("║ Total: ").append(total);
		sb.append("╚══════════════════════════════════════════════════════════════").append("\n");

		return sb.toString();
	}
}
