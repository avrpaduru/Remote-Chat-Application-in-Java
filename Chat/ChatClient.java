
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class ChatClient extends JPanel
{

	static String user;
	static JLabel name;
	private JPanel panel1,panel2;
	private JTextArea textArea;
	private JTextField text;
	private JButton button;
	private JScrollPane scroll;
	private Socket clientSocket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ChatClient()
	{
	
		setLayout(new BorderLayout());
		try
		{
			clientSocket = new Socket("192.168.1.41",5555);//socket(), connect to server 5555
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new PrintWriter(clientSocket.getOutputStream());
		}catch(Exception e){System.out.println("Exception Occured");}

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel2 = new JPanel();
		name = new JLabel(" ");
		textArea = new JTextArea(35,20);
		textArea.setEnabled(false);
		textArea.setBackground(Color.BLACK);
		scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		text = new JTextField(30);
		button = new JButton("Send");
		button.addActionListener(new SendListener());
		Thread receive = new Thread(new ReceiveWork());
		receive.start();

		panel1.add("North",name);
		panel1.add("Center",scroll);
		panel2.add(text);
		panel2.add(button);

		add("North",panel1);
		add("South",panel2);
	}

	class ReceiveWork implements Runnable
	{

		public void run()
		{

			String message="";

			try
			{
				while( (message = reader.readLine())!= null)//read()
					textArea.append(message+"\n");
			}catch(IOException ioe){textArea.append("Server disconnected\n");
									setVisible(false);}
		}
	}

	class SendListener implements ActionListener
	{

		public void actionPerformed(ActionEvent ae)
		{

			String message = text.getText();
			writer.println(user+":"+message);
			writer.flush();//write()
		}
	}
}