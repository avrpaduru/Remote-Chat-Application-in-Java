
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.IOException;

public class ChatServer
{

	private String message;
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private ArrayList<PrintWriter> writer;
	
	public ChatServer()
	{
		try
		{
		serverSocket = new ServerSocket(5555);//socket(), creates a server socket bound, bind(), to port number 5555
		writer = new ArrayList<PrintWriter>();
		}catch(IOException ioe){System.out.println("IOException\n");}
	}
	
	public static void main(String[] args) throws IOException
	{
	
		new ChatServer().go();
	}
	
	public void go()throws IOException
	{
	
		while(true)//keeps listening to the clients, listen()
		{
		
			clientSocket = serverSocket.accept();//waits for an incoming client, listen() and then accept() when a client requests
			System.out.println("new client connected");
			
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
			writer.add(pw);
			
			Thread receive = new Thread(new ReceiveWork(clientSocket));
			receive.start();
		}
	}
	
	class ReceiveWork implements Runnable//initializing the reader
	{
	
		Socket client;
		private BufferedReader reader;
		
		public ReceiveWork(Socket client)throws IOException
		{
		
			this.client = client;
			reader = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		}
		
		public void run()
		{
		
			String message;
			try
			{
				while( (message = reader.readLine()) != null)//read()
				{
					System.out.println(message);
					broadcast(message);//write() after reading something
				}
			}catch(IOException ioe){System.out.println("IOException occured while reading clients message\n");}
		}
		
		public void broadcast(String message)//writing to client
		{
		
			for( PrintWriter pw : writer)
			{
				pw.println(message);
				pw.flush();//write()
			}
		}
	}
}