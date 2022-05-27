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
	}

	public boolean connect()
	{
		try
		{
			conn = DriverManager.getConnection(CONNECTION, "root", PASSWD);
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
			//"INSERT INTO store.clients (dni, nom, email, phone, address) VALUES ('sdfins', 'efison', 'oinsf', 23423423, 'sdfsdfsdf');"
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO store.clients (dni, name, email, phone, address) VALUES (?, ?, ?, ?, ?);"
			);
			query.setString(1, client.get_dni());
			query.setString(2, client.get_nom());
			query.setString(3, client.get_email());
			query.setInt(3, client.get_phone());
			query.setString(4, client.get_address());

			var res = query.executeQuery();
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
			PreparedStatement query = conn.prepareStatement(
					"SELECT (dni, password) FROM store.clients WHERE dni = ?;"
			);
			query.setString(1, passwd);
			var res = query.executeQuery();

			if (res.next())
				return Password.checkPassword(passwd, res.getString(2));

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
			PreparedStatement query = conn.prepareStatement(
					"INSERT INTO store.products (code, name, price, iva) VALUES (?, ?, ?, ?);"
			);
			query.setDouble(1, product.get_code());
			query.setString(2, product.get_name());
			query.setDouble(3, product.get_price());
			query.setInt(4, product.get_iva());

			var res = query.executeQuery();
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
			var res = conn.prepareStatement("SELECT * FROM products;").executeQuery();

			while (res.next())
			{
				int code = res.getInt(1);
				String name = res.getString(2);
				float price = res.getFloat(3);
				int iva = res.getInt(4);

				products.add(new Product(code, name, price, iva));
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
			var query = conn.prepareStatement("SELECT * FROM clients WHERE dni = ?;");
			query.setString(1, dni);
			var res = query.executeQuery();

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

	public int get_last_code_products()
	{
		try
		{
			var query = conn.prepareStatement("SELECT (code) FROM products ORDER BY code");
			var res = query.executeQuery();

			while (res.next())
				return res.getInt(1);

			return -1;
		} catch (SQLException e)
		{
			if (debug) e.printStackTrace();
			return -1;
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
