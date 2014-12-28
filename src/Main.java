import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) 
	{
		JFrame f = new FrameLogin();
		while (!((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
		{
//			System.out.println("non devo fare niente");
		}
		f.setVisible(false);
		f = new MyFrame();
		f.repaint();
	}

}
