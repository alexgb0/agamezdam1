import java.util.Locale;
import java.util.Scanner;

public class Storefront
{
	static Scanner scan;
	static Auth auth;
	static {
		scan = new Scanner(System.in);
		auth = new Auth(true);
	}
	static void main()
	{
		System.out.println("╔═════[ Welcome ]═════");
		System.out.println("║");
		System.out.print("╠ [L]ogin or [R]egister: ");
		String opt = scan.nextLine();
		System.out.println("║");
		System.out.println("╚═════════════════════");
		for (int i = 0; i < 50; ++i) System.out.println();

		if (opt.toLowerCase().charAt(0) == 'l') {
			System.out.println("╔══════[ Login ]══════");
			System.out.print("╠ Dni: ");
			String usernm = scan.nextLine();
			System.out.print("╠ Password: ");
			String passwd = scan.nextLine();
			System.out.println("╚═════════════════════");
			for (int i = 0; i < 50; ++i) System.out.println();

			if (auth.login_user(usernm, passwd)) logged();
			else login_error();
		}
		register();

	}

	static void register()
	{
		boolean success = false;
		Client client = new Client();

		System.out.println("╔═════[ Register ]═════");
		System.out.println("║");

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
		client.set_nom(scan.nextLine());

		System.out.print("╠ Password: ");
		String passwd = scan.nextLine();

		if (auth.register_user(client, passwd))
		{
			for (int i = 0; i < 50; ++i) System.out.println();
			Auth.print_error_db();
			System.exit(1);
		}

		login();

	}

	static void login()
	{

	}

	static void logged()
	{

	}

	static void login_error()
	{
		System.out.println("╔══════════[ Login : Error ]═════════╗");
		System.out.println("║                                    ║");
		System.out.println("║ The DNI or password are incorrect  ║ ");
		System.out.println("║                                    ║");
		System.out.println("╚════[ Press enter to try again ]════╝");
		scan.nextLine();
		main();
	}
}
