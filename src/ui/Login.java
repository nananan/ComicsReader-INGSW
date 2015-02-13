package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import web.ErroreAutenticazioneException;
import web.GestoreJSON;
import web.WebLogin;
import domain.AppManager;
import domain.Lettore;


public class Login extends JPanel 
{
	
	private JTextField textAreaName = new JTextField("eliana-c@live.it");
	private JTextPane erroreLogin = new JTextPane();

	private WebLogin webLogin = new WebLogin();
	private PannelloLoading pannelloLoading = PannelloLoading.getIstanza();
	private JPasswordField textAreaPassword = new JPasswordField("nisdunimfe56");
	private JButton buttonOk = new JButton("Login");
	private JButton buttonIscriviti = new JButton("Iscriviti");
	private JButton buttonAnnulla = new JButton("Annulla");
	private Lettore lettore; 
	
	boolean pressedOk = false;
	
	public Login(int larghezza, int altezza) 
	{
		super();
		this.setBackground(new Color(18, 19, 20));
		this.setLayout(null);
		
		int altezzaText = altezza/14;
		int altezzaButton = 30;
		int distanzaBordo = 35;
		int distanzaText = 15;
		int larghezzaText = larghezza -(distanzaBordo*2);
		
		JLabel iconLabel = new JLabel();
		iconLabel.setIcon(new ImageIcon("image/iconLogin.png"));
		iconLabel.setPreferredSize(new Dimension(150, 200));
		iconLabel.setBounds(12+larghezza/2-(int)iconLabel.getPreferredSize().getWidth()/2, (int)getPreferredSize().getHeight(), (int)iconLabel.getPreferredSize().getWidth(), (int)iconLabel.getPreferredSize().getHeight());
		this.add(iconLabel);
		JLabel nomeLabel = new JLabel();
		ImageIcon iconCR= new  ImageIcon("image/ComicsReader.jpg");
		nomeLabel.setIcon(iconCR);
		nomeLabel.setPreferredSize(new Dimension(iconCR.getIconHeight(), iconCR.getIconWidth()));
		nomeLabel.setBounds(25, nomeLabel.getIcon().getIconHeight()+nomeLabel.getY(), iconCR.getIconWidth(), (int)nomeLabel.getPreferredSize().getHeight());
		this.add(nomeLabel);
	
		textAreaName.setPreferredSize(new Dimension(larghezzaText, altezzaText));
		textAreaName.setBounds(distanzaBordo, altezza-altezza/3,(int) textAreaName.getPreferredSize().getWidth(),(int) textAreaName.getPreferredSize().getHeight());

		textAreaName.setUI(new TextAreaUI("Email Facebook", Color.gray));
		this.add(textAreaName);
		
//		insertPassword.setPreferredSize(new Dimension(larghezza, altezzaText));
//		insertPassword.setBounds(l, altezzaText*2 + textAreaName.getY(),(int) insertPassword.getPreferredSize().getWidth(),(int) insertPassword.getPreferredSize().getHeight());
//		panel.add(insertPassword);
		
		textAreaPassword.setPreferredSize(new Dimension((int)textAreaName.getPreferredSize().getWidth(), altezzaText));
		textAreaPassword.setEchoChar('*');
//		textAreaPassword.setUI(new TextAreaUI("Password",Color.gray));
		textAreaPassword.setBounds(distanzaBordo, altezzaText+textAreaName.getY()+distanzaText,(int) textAreaPassword.getPreferredSize().getWidth(),(int) textAreaPassword.getPreferredSize().getHeight());
		this.add(textAreaPassword);
		
		buttonIscriviti.setBounds(textAreaPassword.getX()-20, textAreaPassword.getY()+(int)textAreaPassword.getPreferredSize().getHeight(),(int) buttonIscriviti.getPreferredSize().getWidth(),(int) buttonIscriviti.getPreferredSize().getHeight());
		buttonIscriviti.setFont(new Font("Caladea",Font.BOLD, 12));
		buttonIscriviti.setForeground(Color.WHITE);
		buttonIscriviti.setBackground(this.getBackground());
		buttonIscriviti.setBorderPainted(false);
		buttonIscriviti.setContentAreaFilled(false);
		this.add(buttonIscriviti);
		
		String errore  = "Login Fallito. L'email facebook o \nla password è sbagliata. Oppure iscriviti";
		erroreLogin.setBackground(new Color(18, 19, 20));
		erroreLogin.setText(errore);
		erroreLogin.setForeground(Color.red);
		erroreLogin.setPreferredSize(new Dimension(larghezza, altezzaText));
		erroreLogin.setBounds(distanzaBordo,240,(int) erroreLogin.getPreferredSize().getWidth(),(int) erroreLogin.getPreferredSize().getHeight());
		erroreLogin.setEditable(false);
		this.add(erroreLogin);
		erroreLogin.setVisible(false);
		
		textAreaPassword.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
					provaLogin();
					
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}
		});
		
		buttonAnnulla.setPreferredSize(new Dimension(90,altezzaButton));
		buttonAnnulla.setBounds(larghezza-(distanzaBordo)-90, buttonIscriviti.getY()+buttonIscriviti.getHeight(), (int)buttonAnnulla.getPreferredSize().getWidth(), (int)buttonAnnulla.getPreferredSize().getHeight());
		buttonAnnulla.setBackground(new Color(4, 174, 218));
		this.add(buttonAnnulla);
		
		buttonOk.setPreferredSize(new Dimension(90,altezzaButton));
		buttonOk.setBounds(distanzaBordo, buttonIscriviti.getY()+(int)buttonIscriviti.getPreferredSize().getHeight(), (int)buttonOk.getPreferredSize().getWidth(), (int)buttonOk.getPreferredSize().getHeight());
		buttonOk.setBackground(new Color(4, 174, 218));
		this.add(buttonOk);
		
		buttonOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				provaLogin();
			}
		 });
		
		buttonAnnulla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		 });
		buttonIscriviti.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					Runtime.getRuntime().exec("firefox https://www.facebook.com/dialog/oauth?client_id=1526250877630357&redirect_uri=http://5.196.65.101/~ComicsReader/facebook/comicsIscrizione.php&scope=public_profile,user_friends,user_photos");
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		 });
	}
	
	public boolean pareCheHaInseritoTutto()
	{
		if (pressedOk)
		{
//			if (textAreaPassword.getText().isEmpty() || textAreaName.getText().isEmpty())
//			{	
//				pressedOk = false;
//				if (textAreaPassword.getText().isEmpty() && textAreaName.getText().isEmpty())
//				{
//					textAreaPassword.setBackground(Color.RED);
//					textAreaName.setBackground(Color.RED);
//				}
//				else if (textAreaName.getText().isEmpty())
//					textAreaName.setBackground(Color.RED);
//				else if (textAreaPassword.getText().isEmpty())
//					textAreaPassword.setBackground(Color.RED);
//				return false;
//			}
//			textAreaName.setBackground(Color.WHITE);
//			textAreaPassword.setBackground(Color.WHITE);
			return true;
		}
		return false;
	}
	private void provaLogin(){
		String email = textAreaName.getText();
		String pass = textAreaPassword.getText();
		try
		{
			webLogin.setEmail(email);
			webLogin.setPass(pass);
			webLogin.clickLoginBotton();
			GestoreJSON gestoreJSON = new GestoreJSON(webLogin.getURLAmiciJSON(), webLogin.getURLUtenteJSON());
			
			String id=gestoreJSON.getIdUtente();
			String nome = gestoreJSON.getNomeUtente();
			String url = webLogin.getURLFoto(id);
			String[] amiciId = gestoreJSON.getIdAmici();
			AppManager.effettuaLogin(id,nome,url,amiciId);
			lettore = AppManager.getLettore();
			pressedOk = true;
		} catch (ErroreAutenticazioneException e1)
		{
			erroreLogin.setVisible(true);
		}
		finally{
			
		}
	}
	public Lettore getLettore()
	{
		return lettore;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
	}
}
