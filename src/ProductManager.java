import java.util.ArrayList;

public class ProductManager
{
	private ArrayList<Product> products;
	private Auth auth;
	private int codes;

	ProductManager(ArrayList<Product> products)
	{
		products = products;
		auth = null;
		codes = 0;
	}

	ProductManager(ArrayList<Product> products, Auth auth)
	{
		this.products = products;
		this.auth = auth;
		this.codes = auth.get_last_code_products();
		if (codes == -1)
		{
			Auth.print_error_db();
			System.exit(1);
		}
	}

	public void register(Product product)
	{
		++codes;
		product.set_code(codes);
		products.add(product);

		auth.insert_product(product);
	}

	public boolean modify(int code, Product product)
	{
		for (int i = 0; i < products.size(); ++i)
			if (products.get(i).get_code() == code)
			{
				product.set_code(code);
				products.set(i, product);
				auth.modify_product(product);
				return false;
			}

		return true;
	}

	public boolean delete(int code)
	{
		return !products.removeIf(product -> product.get_code() == code);
	}

	public Product find(int code)
	{
		for (Product product : products)
			if (product.get_code() == code)
				return product;

		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Product product : products)
			sb.append(product.toString()).append("\n");
		return sb.toString();
	}
}
