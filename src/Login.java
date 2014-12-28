import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;


public class Login extends JPanel
{
	PannelloLogin panel = new PannelloLogin();
	
	JTextField textAreaName = new JTextField();
	JTextField textAreaPassword = new JTextField();
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
		
		this.add(panel, BorderLayout.CENTER);
		
		insertName.setPreferredSize(new Dimension(larghezza,20));
		insertName.setBounds(panel.getInsets().bottom*2, 20,(int) insertName.getPreferredSize().getWidth(),(int) insertName.getPreferredSize().getHeight());
		panel.add(insertName);
		
		textAreaName.setPreferredSize(new Dimension(80,20));
		textAreaName.setBounds(panel.getInsets().bottom*2, 20 +insertName.getY(),(int) textAreaName.getPreferredSize().getWidth(),(int) textAreaName.getPreferredSize().getHeight());
		panel.add(textAreaName);
		
		insertPassword.setPreferredSize(new Dimension(larghezza,20));
		insertPassword.setBounds(panel.getInsets().bottom*2, 30+textAreaName.getY(),(int) insertPassword.getPreferredSize().getWidth(),(int) insertPassword.getPreferredSize().getHeight());
		panel.add(insertPassword);
		
		textAreaPassword.setPreferredSize(new Dimension(80,20));
		textAreaPassword.setBounds(panel.getInsets().bottom*2, 20 +insertPassword.getY(),(int) textAreaPassword.getPreferredSize().getWidth(),(int) textAreaPassword.getPreferredSize().getHeight());
		panel.add(textAreaPassword);
		
		buttonAnnulla.setPreferredSize(new Dimension(90,30));
		buttonAnnulla.setBounds(larghezza-(int)buttonAnnulla.getPreferredSize().getWidth(), altezza-(int)buttonAnnulla.getPreferredSize().getHeight(), (int)buttonAnnulla.getPreferredSize().getWidth(), (int)buttonAnnulla.getPreferredSize().getHeight());
		panel.add(buttonAnnulla);
		
		buttonOk.setPreferredSize(new Dimension(70,30));
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
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);		
	}

}
