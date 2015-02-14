package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.WindowConstants;

public class FileLocali extends JFrame {
    
    private final String title = "JDOM XML Tree";
//    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu = new Menu();
    private final MenuItem open = new MenuItem();
    final JFileChooser fileChooser = new JFileChooser();
  
    private File file;
    
    public FileLocali() {        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileLocaliFilter());
//        fileChooser.setCurrentDirectory(new File("C:/"));
        
        fileMenu.setLabel("File");
        
        open.setLabel("Browse");
        open.addActionListener(new MyActionListener());
        
        open.dispatchEvent(new ActionEvent(open,1001,open.getActionCommand()));
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == open) {

                int returnVal = fileChooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    file = fileChooser.getSelectedFile();
                    
                    // update the gui for this file
                    setTitle(title + " | " + (file != null ? file.getAbsolutePath() : "Select A File"));
                    
                    // remember last directory used
                    fileChooser.setCurrentDirectory(file);
                   
                }
            }
        }
    }
//    
    public static void main(String[] argv) {
        FileLocali x = new FileLocali();
        File folder = new File(x.fileChooser.getSelectedFile().toString());
        File[] listOfFiles = folder.listFiles();
        
        Arrays.sort(listOfFiles, new Comparator<File>()
		{
        	public int compare(File file1, File file2) {
        		return Integer.parseInt(file1.getName()) - Integer.parseInt(file2.getName());
        	};
		});
        
        for (File file : listOfFiles)
		{
			System.out.println(file.getName());
		}
    }
}

