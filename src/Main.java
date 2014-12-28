import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) 
	{
		JFrame f = new FrameLogin();
		while (!((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
		{
			System.out.println("non devo fare niente");
		}
		if (((FrameLogin) f).getPanel().pareCheHaInseritoTutto())
		{
			f = new MyFrame();
			f.repaint();
		}
	}

}
