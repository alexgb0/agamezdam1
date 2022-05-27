import java.util.Locale;
import java.util.Scanner;

public class Admin
{
	private static final String PASSWORD = "admin";
	private static final Scanner scan;
	private static final Auth auth;
	private static final ProductManager prodmngr;
	static
	{
		scan = new Scanner(System.in);
		auth = new Auth(true);

		var products = auth.fetch_products();
		if (products == null) {
			for (int i = 0; i < 50; ++i) System.out.println();
			Auth.print_error_db();
			System.exit(1);
		}

		prodmngr = new ProductManager(products, auth);
	}

	static void main(String passwd)
	{
		while (true) {
			if (passwd.compareTo(PASSWORD) != 0)
				return;

			//for (int i = 0; i < 50; ++i) System.out.println();
			System.out.println("╠═════[ Welcome : Admin ]═════");
			System.out.println("║");
			System.out.println("╠ Select an option:");
			System.out.println("╠ \t[A]dd product");
			System.out.println("╠ \t[M]odify item");
			System.out.println("╠ \t[L]ist items");
			System.out.println("╠ \t[Q]uit program");
			System.out.println("║");
			System.out.print("╠ Option: ");
			String opt = scan.nextLine();

			switch (opt.toLowerCase().charAt(0)) {
				case 'a' -> add_product();
				case 'm' -> modify_product();
				case 'l' -> list_items(false);
				case 'q' -> System.exit(0);
			}
		}
	}

	static void list_items(boolean clean)
	{
		if (!clean)
		{
			System.out.println("╠═════[ List items : Admin ]═════");
			System.out.println("║");
		}
		System.out.print(prodmngr.toString());
	}

	static void add_product()
	{
		boolean exit = false;
		while (!exit)
		{
			System.out.println("╠═════[ Add product : Admin ]═════");
			list_items(true);
			System.out.println("╟──────────────────────────────────");


			System.out.println("║");
			System.out.print("╠ Name: ");
			String name = scan.nextLine();

			System.out.print("╠ Price: ");
			double price = Double.parseDouble(scan.nextLine());

			System.out.print("╠ Stock: ");
			int stock = Integer.parseInt(scan.nextLine());

			System.out.print("╠ Iva (Default: 24): ");
			String siva = scan.nextLine();
			int iva = 0;
			if (siva.isEmpty())
				iva = 21;
			else
				iva = Integer.parseInt(scan.nextLine());

			prodmngr.register(new Product(name, price, stock, iva));
			System.out.println("║");
			System.out.print("╠ Add another product (y/n): ");

			exit = scan.nextLine().toLowerCase().charAt(0) == 'n';
		}
	}

	static void modify_product()
	{
		System.out.println("╠═════[ Modify item : Admin ]═════");
		list_items(true);
		System.out.println("╟──────────────────────────────────");

		boolean exit = false;

		while (!exit)
		{
			boolean success = true;

			int code = 0;
			Product product = null;
			while (success)
			{
				System.out.print("╠ Code of the item to modify: ");
				code = Integer.parseInt(scan.nextLine());
				product = prodmngr.find(code);

				success = product == null;
			}

			System.out.print("╠ Field to modify ([N]ame, [P]rice, [S]tock, [I]va) | You can select multiple items like: nsi): ");

			Product product1 = new Product(product);

			String opt = scan.nextLine().toLowerCase();
			for (int i = 0; i < opt.length(); ++i)
				switch (opt.charAt(i))
				{
					case 'n' -> {
						System.out.printf("╠ Name (original: %s): ", product1.get_name());
						product1.set_name(scan.nextLine());
					}
					case 'p' -> {
						System.out.printf("╠ Price (original: %s): ", product1.get_price());
						product1.set_price(Integer.parseInt(scan.nextLine()));
					}
					case 's' -> {
						System.out.printf("╠ Stock (original: %s): ", product1.get_stock());
						product1.set_stock(Integer.parseInt(scan.nextLine()));
					}
					case 'i' -> {
						System.out.printf("╠ Iva (original: %s): ", product1.get_iva());
						product1.set_iva(Integer.parseInt(scan.nextLine()));
					}

					default -> System.out.println("╠ [Error] Option unknown");
				}

			boolean res = prodmngr.modify(code, product1);
			assert res; // res should be true since we already checked in above.

			System.out.println("║");
			System.out.print("╠ Modify another item? (y/n): ");
			exit = scan.nextLine().toLowerCase().charAt(0) == 'n';
		}
	}
}
