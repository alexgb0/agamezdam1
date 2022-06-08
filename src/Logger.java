import java.util.Scanner;

public class Logger
{
	private static final Scanner scan;
	private static final Auth auth;
	static
	{
		scan = new Scanner(System.in);
		auth = new Auth(true);
	}
	static void main()
	{
		System.out.println("╔═════[ Welcome ]═════");
		System.out.print("╠ [L]ogin or [R]egister: ");
		String opt = scan.nextLine();
		if (opt.toLowerCase().charAt(0) == 'l')
			login();
		register();

	}

	static void register()
	{
		boolean success = false;
		Client client = new Client();

		System.out.println("╠═════[ Register ]═════");

		System.out.print("╠ DNI: ");
		success = client.set_dni(scan.nextLine());
		while (success)
		{
			System.out.print("╠ [Invalid] DNI: ");
			success = client.set_dni(scan.nextLine());
		}

		System.out.print("╠ Name: ");
		client.set_nom(scan.nextLine());

		System.out.print("╠ Email: ");
		success = client.set_email(scan.nextLine());
		while (success)
		{
			System.out.print("╠ [Invalid] Email: ");
			success = client.set_email(scan.nextLine());
		}

		System.out.print("╠ Phone number: ");
		success = client.set_phone(Integer.parseInt(scan.nextLine()));
		while (success)
		{
			System.out.print("╠ [Invalid] Phone number: ");
			success = client.set_phone(Integer.parseInt(scan.nextLine()));
		}

		System.out.print("╠ Address: ");
		client.set_address(scan.nextLine());

		System.out.print("╠ Password: ");
		String passwd = scan.nextLine();

		if (auth.register_user(client, passwd))
		{
			//for (int i = 0; i < 50; ++i) System.out.println();
			Auth.print_error_db();
			System.exit(1);
		}

		login();
	}

	static void login()
	{
		System.out.println("╠══════════[ Login ]══════════");

		System.out.print("╠ DNI: ");
		String dni = scan.nextLine();

		System.out.print("╠ Password: ");
		String passwd = scan.nextLine();

		if (auth.login_user(dni, passwd))
			Storefront.main(dni);
		else
		{
			for (int i = 0; i < 50; ++i) System.out.println();
			login_error();
		}
	}

	static void login_error()
	{
		System.out.println("╔══════════[ Login : Error ]═════════╗");
		System.out.println("║                                    ║");
		System.out.println("║ The DNI or password are incorrect  ║ ");
		System.out.println("║                                    ║");
		System.out.println("╚════[ Press enter to try again ]════╝");
		scan.nextLine();
		for (int i = 0; i < 50; ++i) System.out.println();
		main();
	}
}
