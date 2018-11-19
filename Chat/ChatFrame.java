
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame extends JFrame
{

	JPanel panel,namePanel;
	JTextField text;
	JButton button;
	CardLayout cardLo;
	public ChatFrame()
	{

		panel = new JPanel();
		cardLo = new CardLayout();
		panel.setLayout(cardLo);
		
		namePanel = new JPanel();
		ChatClient client = new ChatClient();

		text = new JTextField(20);
		button = new JButton("Submit");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{

				client.user = text.getText();
				client.name.setText(text.getText());
				cardLo.next(panel);				
			}
		});

		namePanel.add(text);
		namePanel.add(button);
		panel.add(namePanel);
		panel.add(client);

		add(panel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		setSize(800,650);
		setVisible(true);
	}

	public static void main(String[] args)
	{

		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
					new ChatFrame();
			}
		});
	}
}