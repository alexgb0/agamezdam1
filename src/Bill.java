import java.time.LocalDate;
import java.util.ArrayList;

public class Bill
{
	private Client owner;
	private final ArrayList<Product> products;
	private LocalDate date;


	public Bill(Client owner, LocalDate date)
	{
		this.owner = owner;
		this.date = date;
		products = new ArrayList<>();
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

	public LocalDate get_date()
	{
		return date;
	}

	public void set_date(LocalDate date)
	{
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("╔══════[ Bills : ").append(date.toString()).append("]══════").append("\n");
		sb.append("║\n");
		for (Product product : products)
			sb.append("╠ ").append(product.toString()).append("\n");
		sb.append("║\n");
		sb.append("╚═════════════════════════════════").append("\n");

		return sb.toString();
	}
}
