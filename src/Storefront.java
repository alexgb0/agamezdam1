import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class Storefront
{
	static String DNI;
	private static final Scanner scan;
	private static final Auth auth;
	private static final ProductManager prodmngr;
	private static final ShoppingList shopping_list;

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
		shopping_list = new ShoppingList(auth);
		prodmngr = new ProductManager(products, auth);
	}

	static void main(String dni)
	{
		DNI = dni;

		for (;;) {
			boolean success = false;

			while (!success)
			{
				System.out.println("╠═════[ Welcome : User : Items on the cart ("+ shopping_list.items() +") ]═════");
				System.out.println("╠ Select an option:");
				System.out.println("╠ \t[B]uy items");
				System.out.println("╠ \t[C]heck cart");
				System.out.println("╠ \t[L]ist bills");
				System.out.println("╠ \t[Q]uit program");
				System.out.println("║");
				System.out.print("╠ Option: ");
				String opt = scan.nextLine();

				switch (opt.toLowerCase().charAt(0)) {
					case 'b' ->  { buy_items(); success = true; }
					case 'c' ->  { cart(); success = true; }
					case 'l' -> { history_of_bills(); success = true;}
					case 'q' -> System.exit(0);
					default -> System.out.println("╠ [Error] Invalid option");
				}
			}


		}
	}

	static void cart()
	{
		boolean success = false;
		while (!success)
		{
			System.out.printf("╠═════[ Items: %d : Total: %d€ ]═════\n", shopping_list.items(), shopping_list.total());
			System.out.println("╠ Select an option:");
			System.out.println("╠ \t[B]uy more items");
			System.out.println("╠ \t[R]emove items");
			System.out.println("╠ \t[C]heck out");
			System.out.println("║");
			System.out.print("╠ Option: ");
			String opt1 = scan.nextLine();

			switch (opt1.toLowerCase().charAt(0)) {
				case 'b' -> { buy_items(); success = true; }
				case 'r' -> { remove_item(); success = true; }
				case 'c' -> { checkout(); success = true; }
				default -> System.out.println("╠ [Error] Invalid option");
			}
		}
	}

	static void buy_items()
	{
		System.out.println("╠═════[ Buy items : User ]═════");
		System.out.print(prodmngr.toString());
		System.out.println("╟──────────────────────────────────");

		System.out.print("╠ Code of the items to buy (10 or 2 6 10): ");
		int[] items = Arrays.stream(scan.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		for (int i : items)
		{
			Product found = prodmngr.find(i);
			if (found == null)
			{
				System.out.println("╠ [Error] Item not found");
				continue;
			}

			if (found.get_stock() < 1)
			{
				System.out.println("╠ [Error] There's no stock for " + found.get_name());
				continue;
			}

			shopping_list.add_item(found);
		}
		System.out.print(shopping_list.list_simple());
	}

	static void remove_item()
	{
		System.out.println("╠═════[ Remove items : User ]═════");
		System.out.print(shopping_list.list_simple());
		System.out.println("╟──────────────────────────────────");
		System.out.print("╠ Code of the items to remove (10 or 2 6 10): ");
		int[] items = Arrays.stream(scan.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		for (int i : items)
			shopping_list.remove(i);
	}

	static void checkout()
	{
		if (shopping_list.checkout(DNI))
		{
			Auth.print_error_db();
			System.exit(1);
		}

		Bill bill = shopping_list.make_bill(DNI);

		try
		{
			FileWriter fw = new FileWriter("C:\\fitxers\\factura-" + LocalDate.now() + ".txt");
			String bill_str = bill.toString(shopping_list.total());
			System.out.print(bill_str);
			fw.write(bill_str);
			fw.close();
		} catch (Exception ignored)
		{

		}
	}

	static void history_of_bills()
	{

	}
}




























