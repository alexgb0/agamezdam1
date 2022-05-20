import java.util.ArrayList;

public class ProductManager
{
	private ArrayList<Product> products;
	private int codes;

	ProductManager()
	{
		products = new ArrayList<>();
		codes = 0;
	}

	public void register(Product product)
	{
		product.set_code(codes);
		++codes;
		products.add(product);
	}

	public boolean modify(int code, Product product)
	{
		for (int i = 0; i < products.size(); ++i)
			if (products.get(i).get_code() == code)
			{
				product.set_code(code);
				products.set(i, product);
				return false;
			}

		return true;
	}

	public boolean delete(int code)
	{
		return !products.removeIf(product -> product.get_code() == code);
	}
}
