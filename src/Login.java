import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JPanel
{
	PannelloLogin panel = null;
	
	JTextField textAreaName = new JTextField();
	JPasswordField textAreaPassword = new JPasswordField();
	Text insertName = new Text("UserName");
	Text insertPassword = new Text("Password");
	MyButton buttonOk = new MyButton("Ok");
	MyButton buttonAnnulla = new MyButton("Annulla");
	
	boolean pressedOk = false;
	
	public Login(int larghezza, int altezza) 
	{
		super();
		this.setBackground(new Color(137,130,130));
		this.setLayout(new BorderLayout());
		
		int altezzaText = altezza/11;
		int altezzaButton = altezza/9;
		
		panel = new PannelloLogin(larghezza,altezza);
		System.out.println(altezza);
		this.add(panel, BorderLayout.CENTER);
		insertName.setPreferredSize(new Dimension(larghezza,altezzaText));
		insertName.setBounds(panel.getInsets().bottom*2, altezzaText,(int) insertName.getPreferredSize().getWidth(),(int) insertName.getPreferredSize().getHeight());
		panel.add(insertName);
		
		textAreaName.setPreferredSize(new Dimension(larghezza/3, altezzaText));
		textAreaName.setBounds(panel.getInsets().bottom*2, altezzaText + insertName.getY(),(int) textAreaName.getPreferredSize().getWidth(),(int) textAreaName.getPreferredSize().getHeight());
		panel.add(textAreaName);
		
		insertPassword.setPreferredSize(new Dimension(larghezza, altezzaText));
		insertPassword.setBounds(panel.getInsets().bottom*2, altezzaText*2 + textAreaName.getY(),(int) insertPassword.getPreferredSize().getWidth(),(int) insertPassword.getPreferredSize().getHeight());
		panel.add(insertPassword);
		
		textAreaPassword.setPreferredSize(new Dimension((int)textAreaName.getPreferredSize().getWidth(), altezzaText));
		textAreaPassword.setEchoChar('*');
		textAreaPassword.setBounds(panel.getInsets().bottom*2, altezzaText +insertPassword.getY(),(int) textAreaPassword.getPreferredSize().getWidth(),(int) textAreaPassword.getPreferredSize().getHeight());
		panel.add(textAreaPassword);
		
		buttonAnnulla.setPreferredSize(new Dimension(90,altezzaButton));
		buttonAnnulla.setBounds(larghezza-(int)buttonAnnulla.getPreferredSize().getWidth(), altezza-(int)buttonAnnulla.getPreferredSize().getHeight(), (int)buttonAnnulla.getPreferredSize().getWidth(), (int)buttonAnnulla.getPreferredSize().getHeight());
		panel.add(buttonAnnulla);
		
		buttonOk.setPreferredSize(new Dimension(70,altezzaButton));
		buttonOk.setBounds(larghezza-(int)buttonAnnulla.getPreferredSize().getWidth()-(int)buttonOk.getPreferredSize().getWidth(), altezza-(int)buttonOk.getPreferredSize().getHeight(), (int)buttonOk.getPreferredSize().getWidth(), (int)buttonOk.getPreferredSize().getHeight());
		panel.add(buttonOk);
		buttonOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				String text = textAreaName.getText();
				
				if (textAreaPassword.getText().isEmpty())
					System.out.println("inserisci la password");
				
				System.out.println(text);
				pressedOk = true;
			}
		 });
		
		buttonAnnulla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		 });
	}
	
	public boolean pareCheHaInseritoTutto()
	{
		if (pressedOk)
		{
			if (textAreaPassword.getText().isEmpty() || textAreaName.getText().isEmpty())
			{	
				pressedOk = false;
				if (textAreaPassword.getText().isEmpty() && textAreaName.getText().isEmpty())
				{
					textAreaPassword.setBackground(Color.RED);
					textAreaName.setBackground(Color.RED);
				}
				else if (textAreaName.getText().isEmpty())
					textAreaName.setBackground(Color.RED);
				else if (textAreaPassword.getText().isEmpty())
					textAreaPassword.setBackground(Color.RED);
				return false;
			}
			textAreaName.setBackground(Color.WHITE);
			textAreaPassword.setBackground(Color.WHITE);
			return true;
		}
		return false;
	}
	
	public String getTextName()
	{
		return textAreaName.getText();
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);		
	}

}
