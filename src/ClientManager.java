import java.util.ArrayList;

public class ClientManager
{
	private final ArrayList<Client> clients;


	ClientManager()
	{
		clients = new ArrayList<>();
	}

	/**
	 * Inserta un usuario
	 * @param client Objeto del cliente, <b>el cliente no deve de ser null</b>
	 */
	public void register(Client client)
	{
		clients.add(client);
	}

	/**
	 * Modifica un cliente
	 * @param dni Dni del cliente
	 * @param data Los nuevos data, <b>data tiene que ser una copia actualizada del cliente a modificar</b>
	 * @return True en caso de error
	 */
	public boolean modify(String dni, Client data)
	{
		/*
			Nota: Obviamente java tiene de copiar cada puto objecto en vez de pasar el puntero o referencia.

			Client client = find_client(dni);
			if (client == null) return true;
		*/

		for (int i = 0; i < clients.size(); ++i)
			if (clients.get(i).get_dni().equalsIgnoreCase(dni))
			{
				clients.set(i, data);
				return false;
			}

		return true;
	}

	/**
	 * Elimina un cliente
	 * @param dni Dni del cliente a eliminar
	 * @return True en caso de error
	 */
	public boolean delete(String dni)
	{
		Client client = find_client(dni);
		if (client == null) return true;

		clients.remove(client);
		return false;
	}

	/**
	 * Muestra un usuario
	 * @param dni Dni del cliente
	 * @return Devuelve un "empty string" en caso de no encontrar al usuario
	 */
	public String show(String dni)
	{
		Client client = find_client(dni);
		if (client == null) return "";

		return client.toString();
	}

	/**
	 * Muestra todos los usuarios
	 * @return Devuelve el string
	 */
	public String show_all()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Dni\tNom\tEmail\tPhone\tAddress\n");
		for (Client client : clients)
			sb.append(client.toString()).append("\n");

		return sb.toString();
	}

	/**
	 * Encuentra un usuario
	 * @param dni Dni del usuario
	 * @return Devuelve "null" en caso de no encontrar al usuario, o el objeto
	 */
	private Client find_client(String dni)
	{
		for (Client client : clients)
			if (client.get_dni().equalsIgnoreCase(dni))
				return client;

		return null;
	}
}
