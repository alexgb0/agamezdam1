import java.util.Scanner;

public class Admin
{
	private static final String PASSWORD = "admin";
	private static final Scanner scan;
	private static final Auth auth;
	static
	{
		scan = new Scanner(System.in);
		auth = new Auth(true);
	}

	static void main(String passwd)
	{
		if (passwd.compareTo(PASSWORD) != 0)
			return;

		//for (int i = 0; i < 50; ++i) System.out.println();
		System.out.println("╔═════[ Welcome : Admin ]═════");
		System.out.println("║");
		System.out.println("╠ Select an option:");
		System.out.println("╠ \t[A]dd product");
		System.out.println("║");
		System.out.println("╠ Option: ");
		String opt = scan.nextLine();

		if (opt.toLowerCase().charAt(0) == 'a') add_product();
	}

	static void add_product()
	{
		var products = auth.fetch_products();
		if (products == null) {
			for (int i = 0; i < 50; ++i) System.out.println();
			Auth.print_error_db();
			System.exit(1);
		}

		ProductManager prodmngr = new ProductManager(products, auth);
		System.out.println("╔═════[ Add Product : Admin ]═════");
		System.out.println("║");
		System.out.println("╠ Items in the database:");
		System.out.print(prodmngr.toString());
		System.out.println("║");
		System.out.println("╟──────────────────────────────────");
		System.out.println("║");
		System.out.println("╠ Items:");

	}
}
