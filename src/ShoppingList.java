import java.util.ArrayList;

public class ShoppingList
{
	private Auth auth;
	private ArrayList<Product> products;

	ShoppingList(Auth auth)
	{
		this.auth = auth;
		this.products = new ArrayList<>();
	}

	ShoppingList(Auth auth, ArrayList<Product> products)
	{
		this.auth = auth;
		this.products = products;
	}

	public void add_item(Product product)
	{
		products.add(product);
	}

	public boolean remove(int code)
	{
		return !products.removeIf(product -> product.get_code() == code);
	}

	public int total()
	{
		int balance = 0;
		for (Product product : products)
			balance += (product.get_price() * product.get_iva()) / 100;
		return balance;
	}

	public boolean checkout(String dni)
	{
		for (Product product : products)
			product.set_stock(product.get_stock() - 1);
		return auth.insert_bill(dni, products);
	}

	public Bill make_bill(String dni)
	{
		ClientManager client_manager = new ClientManager(auth);
		Client owner = client_manager.find_client(dni);

		assert owner != null;
		return new Bill(owner, products, auth.get_last_code_bills());
	}

	public String list_simple()
	{
		StringBuilder sb = new StringBuilder();
		for (Product product : products)
			sb.append("╠-→ ")
					.append(product.get_name()).append(" - ")
					.append(product.get_name()).append(" - ")
					.append(product.get_price()).append(" - ")
					.append(product.get_iva()).append("%").append("\n");

		return sb.toString();
	}
}
