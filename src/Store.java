public class Store
{
	private ClientManager client_mngr;
	private ProductManager product_mngr;

	Store()
	{
		client_mngr = new ClientManager();
		product_mngr = new ProductManager();
	}


}
