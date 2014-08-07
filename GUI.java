package documentclassifier;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.miginfocom.swing.MigLayout;

public class GUI extends JFrame {  
   DefaultTableModel model;
   public GUI(String title, int width, int height){
       
        //setting up frame
        setTitle(title);
        setSize(width, height);        
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 500));
        setVisible(true);
        setLayout(new MigLayout("fillx, gap unrel rel","[grow, fill][fill]","[fill][fill]"));
        
        
        //Populating class list
        Class_info cs = new Class_info("CS","D:\\cs_commonwords.txt" );            
        Class_info physics = new Class_info("Physics","D:\\phy_commonwords.txt" );
        Class_info bio = new Class_info("Biology","D:\\bio_commonwords.txt" );
            
        final ClassList class_list = new ClassList();
        class_list.addClass(cs);
        class_list.addClass(physics);
        class_list.addClass(bio);
        
        //Populating index file
        Index i = new Index();
        i.updateIndex();
        final TrieNode trie = PrefixTree.createTree();
        PrefixTree.insertFromFolder(trie);
        
        //classify
        final Tf_Idf classifier = new Tf_Idf();
        classifier.classify(i, class_list);
        i.print();
        i.updateIndexTextFile("D:/index.txt");        
        
        Scanner inputStream = null;
        String s;
        String[] fields= new String[5];
        try{
        	inputStream = new Scanner(new FileInputStream("D:\\index.txt"));
        }
        catch(FileNotFoundException e){
        	System.out.print("The index file cannot be found at specified location or could not be accessed");
        	System.exit(0);
        }
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        
	model = new DefaultTableModel();
	model.addColumn("Name");
	model.addColumn("Year");
	model.addColumn("Author");
	model.addColumn("Publication");
        model.addColumn("Class");
        
	while(inputStream.hasNextLine()){
		s = inputStream.nextLine();
		fields = s.split(",");
		model.addRow(fields);
	}
        
	JTable table = new JTable(model){
           public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col){
             Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
             if (Index_row % 2 == 0 && !isCellSelected(Index_row, Index_col)) 
                comp.setBackground(new Color(230,230,255));
             else
                comp.setBackground(new Color(255,255,255));
             return comp;
             }
        };
    
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        
        //Menubar       
        JMenuBar m = new JMenuBar();
        m.setBackground(new Color(230,220,220));
        
        JMenu op = new JMenu("Open");
        op.add(new JMenuItem("Read a Paper!"));
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        op.add(exit);
        
        JMenu run = new JMenu("Run");
        run.add(new JMenuItem("Search My System"));  
        JMenuItem synchr = new JMenuItem("Start Synchronizer", 'S');   
        synchr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));  
        run.add(synchr);
        
        JMenu help = new JMenu("Help");     
        JMenuItem h = new JMenuItem("Help Contents", 'H');   
        h.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));  
        help.add(h);
        help.add(new JMenuItem("Keyboard Shortcut Card"));
        help.add(new JMenuItem("About Application"));        
        
        m.add(op);
        m.add(run);
        m.add(help);        
        setJMenuBar(m);  
        
        //Toolbar
        JButton Tab1 = new JButton("Category");
        Tab1.setBorderPainted(false);
        Tab1.setFocusable(false);
        Tab1.setSize(50, 25);
        JButton Tab2 = new JButton("Author");
        Tab2.setBorderPainted(false);
        Tab2.setFocusable(false);
        Tab2.setSize(50, 25);
        JButton Tab3 = new JButton("Journal");
        Tab3.setBorderPainted(false);
        Tab3.setFocusable(false);
        Tab3.setSize(50, 25);
        JButton Tab4 = new JButton("Year");
        Tab4.setBorderPainted(false);
        Tab4.setFocusable(false);
        Tab4.setSize(50, 25);       

        Tab1.setActionCommand(new String("Here"));

        JToolBar toolBar = new JToolBar("Draggable");
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(230,230,250));
        toolBar.add(Tab1);
        toolBar.add(Tab2);
        toolBar.add(Tab3);
        toolBar.add(Tab4);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        add(toolBar, "span, height 20:35:50, wrap");
  
        JPanel panelButton1 = new JPanel();
        JButton button1 = new JButton("POPULATE MY PAPERS!");
        button1.setBorderPainted(false);
        button1.setBounds(60, 100, 120, 30);
        button1.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){
                Index i = new Index();
                i.updateIndex();
                classifier.classify(i, class_list);
                i.updateIndexTextFile("D:/index.txt");     
                PrefixTree.insertFromFolder(trie);
                Scanner inputStream = null;
	        String s;
	        String[] fields= new String[4];
	        try{
	            inputStream = new Scanner(new FileInputStream("D:\\index.txt"));
                }
	        catch(FileNotFoundException e2){
		    System.out.print("The index file cannot be found at specified location or could not be accessed");
		    System.exit(0);
                } 
                model.getDataVector().removeAllElements();
              	while(inputStream.hasNextLine()){
		    s = inputStream.nextLine();
		    fields = s.split(",");
		    model.addRow(fields);
	        }
            }
        });      
 
        panelButton1.setBounds(1100, 700, 100, 100);
        
        JPanel panelButton2 = new JPanel();
        JButton button2 = new JButton("PRINT");
        button2.setBorderPainted(false);
        button2.setBounds(60, 300, 120, 30);
        panelButton2.setBounds(1100, 700, 100, 100);
        JPanel panelButton3 = new JPanel();        
        JButton button3 = new JButton("PREVIEW");
        button3.setBorderPainted(false);
        button3.setBounds(60, 500, 120, 30);
        panelButton3.setBounds(1100, 700, 100, 100);
        panelButton1.add(button1);
        panelButton2.add(button2);
        panelButton3.add(button3);
     
        JPanel mainpanel = new JPanel();
        mainpanel.setBackground(new Color(255,255,255));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1075, 570));
        mainpanel.add(scrollPane);
        mainpanel.add(panelButton1);
        mainpanel.add(panelButton2);
        mainpanel.add(panelButton3);
        add(mainpanel, "width 400:600:, dock center, growy");
        
        JPanel sideBar = new JPanel();
        JLabel search = new JLabel("Search");
        sideBar.add(search);
        sideBar.setBackground(new Color(255,250,250));
        final JTextField query = new JTextField(20); 
        JButton find = new JButton("Search");
        find.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent ae){
              String textFieldValue = query.getText();
              textFieldValue = textFieldValue.toLowerCase();
              System.out.println(PrefixTree.find(trie, textFieldValue));
           }
        });
        sideBar.add(query);
        sideBar.add(find);
        add(sideBar, "width 250:200:300, dock west, growy");    
        
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI("ResearchSoft",1350,720).setVisible(true);
            }
        });
    }
}