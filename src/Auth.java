import java.sql.*;
import java.util.ArrayList;


public class Auth
{
	static final String CONNECTION = "jdbc:mysql://localhost:3306/store";
	static final String USERNAME = "store";
	static final String PASSWD = "store";

	private Connection conn;
	private Statement stmt;

	private boolean debug;


	Auth(boolean debug)
	{

		this.debug = debug;
		if (connect())
		{
			print_error_db();
			System.exit(1);
		}
	}

	private boolean connect()
	{
		try
		{
			conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWD);
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			return false;

		} catch (SQLException e) {
			if (debug) e.printStackTrace();
			return true;
		}
	}

	public void close()
	{
		try { conn.close(); }
		catch (SQLException e) { if (debug) e.printStackTrace(); }
	}

	public boolean register_user(Client client, String password)
	{
		if (client == null || password.isEmpty()) return true;
		String hashed = Password.hashPassword(password);

		try
		{
			stmt = conn.createStatement();
			var res= stmt.executeUpdate(
					String.format(
							"INSERT INTO store.clients (dni, name, email, phone, address, password) VALUES ('%s', '%s', '%s', %d, '%s', '%s')",
							client.get_dni(), client.get_nom(), client.get_email(), client.get_phone(), client.get_address(), hashed));

			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return true;
		}
	}

	/**
	 *
	 * @return TRUE if is successful
	 */
	public boolean login_user(String dni, String passwd)
	{
		if (dni.isEmpty() || passwd.isEmpty()) return false;
		try
		{
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT password FROM store.clients WHERE dni = " + "'"+ dni + "'");

			if (res.next())
				return Password.checkPassword(passwd, res.getString(1));

			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return false;
		}
	}

	public boolean insert_product(Product product)
	{
		if (product == null) return true;

		try
		{
			stmt = conn.createStatement();
			var res = stmt.executeUpdate(
					String.format(
							"INSERT INTO store.products (code, name, price, stock, iva) VALUES (%d, '%s', %f, %d, %d)",
							product.get_code(), product.get_name(), product.get_price(),
							product.get_stock(), product.get_iva()
					));
			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Product> fetch_products()
	{
		try
		{
			ArrayList<Product> products = new ArrayList<>();
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT * FROM products");

			while (res.next())
			{
				int code = res.getInt(1);
				String name = res.getString(2);
				float price = res.getFloat(3);
				int stock = res.getInt(4);
				int iva = res.getInt(5);

				products.add(new Product(code, name, price, stock, iva));
			}

			return products;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return null;
		}
	}

	public Client get_user(String dni)
	{
		try
		{
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT * FROM clients WHERE dni = " + dni);

			Client client = null;

			while (res.next())
			{
				String _dni = 		res.getString(1);
				String nom = 		res.getString(2);
				String email = 		res.getString(3);
				int phone = 		res.getInt(4);
				String address = 	res.getString(5);

				client = new Client(_dni, nom, email, phone, address);
			}

			return client;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Client> fetch_users()
	{
		try
		{
			ArrayList<Client> clients = new ArrayList<>();
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT * FROM clients;");

			while (res.next())
			{
				String dni = res.getString(1);
				String name = res.getString(2);
				String email = res.getString(3);
				int phone = res.getInt(4);
				String address = res.getString(5);

				clients.add(new Client(dni, name, email, phone, address));
			}

			return clients;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return null;
		}
	}

	public int get_last_code_products()
	{
		try
		{
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res= stmt.executeQuery("SELECT (code) FROM products ORDER BY code DESC");

			while (res.next())
				return res.getInt(1);

			return -1;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return -1;
		}
	}

	public int get_last_code_bills()
	{
		try
		{
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT (buy_id) FROM bills ORDER BY buy_id DESC");

			while (res.next())
				return res.getInt(1);

			return -1;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return -1;
		}
	}

	public boolean modify_product(Product product)
	{
		if (product == null) return true;

		try
		{
			stmt = conn.createStatement();
			var res = stmt.executeUpdate(
					String.format(
							"UPDATE store.products t SET t.name = '%s', t.price = %f, t.stock = %d, t.iva = %d WHERE t.code = %d;",
							product.get_name(), product.get_price(), product.get_stock(),
							product.get_iva(), product.get_code()
					)
			);

			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return false;
		}
	}

	public boolean stock_reduce(Product product)
	{
		if (product == null) return true;

		try
		{
			stmt = conn.createStatement();
			var res = stmt.executeUpdate(
					String.format(
							"UPDATE store.products t SET t.stock = %d WHERE t.code = %d;",
							product.get_stock(),
							product.get_code()
					)
			);

			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return false;
		}
	}

	public boolean insert_bill(String dni, ArrayList<Product> products)
	{
		int code = get_last_code_bills() + 1;
		try
		{
			for (Product product : products)
			{
				stmt = conn.createStatement();
				var res = stmt.executeUpdate(
						String.format(
								"INSERT INTO bills (id, buy_id, client_id, product_id) VALUES (default, %d, '%s', %d);",
								code, dni, product.get_code()
						)
				);
			}
			return false;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Bill> fetch_bills()
	{
		try
		{
			ArrayList<Bill> bills = new ArrayList<>();
			stmt = conn.createStatement(0, ResultSet.CONCUR_UPDATABLE);
			var res = stmt.executeQuery("SELECT * FROM bills");
			ClientManager client_manager = new ClientManager(this);

			while (res.next())
			{
				int id = res.getInt(1);
				int buy_id = res.getInt(2);
				String client_id = res.getString(3);
				int product_id = res.getInt(4);


			}

			return bills;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return null;
		}
	}

	public static void print_error_db()
	{
		System.out.println("╔═══════[ Database : Error ]═════════╗");
		System.out.println("║                                    ║");
		System.out.println("║ There was an error while making a  ║");
		System.out.println("║      request to the database.      ║");
		System.out.println("║                                    ║");
		System.out.println("╚════════════════════════════════════╝");
	}
}
