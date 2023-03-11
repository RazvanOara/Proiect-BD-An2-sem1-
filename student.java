import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class student{

	static JMenuBar mb;
	static JMenuItem logOff,info;
	static JButton cautareCurs,vizualizareNote,grupuri;
	static  String optionCurs;
	static int studentid;
	static JFrame f;
	public student(int id) {
		this.studentid=id;
	}

	
	public void do_student(JFrame popUpLogIn,String userName,String userPassword,int userid)
	{
		
		
		f=new JFrame("Proiect_bd");
		f.setBounds(0,0,1500,800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setLayout(null);
		f.setVisible(false);
		
	    mb=new JMenuBar();
		logOff=new JMenuItem(userName+" - Log off");
		info=new JMenuItem("Date personale");
		mb.add(logOff);
		mb.add(info);
		f.setJMenuBar(mb);

		
		logOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n=JOptionPane.showConfirmDialog(popUpLogIn, "Are u sure?");
				if(n==JOptionPane.OK_OPTION)
				{	popUpLogIn.show();
				f.dispose();
				}
				
			}
		});
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Afisare_inf_student a=new Afisare_inf_student();
				a.afisare_date(studentid,userName,userPassword,userid,f,popUpLogIn);
				
			}
		});
		
		cautareCurs=new JButton("Cursuri");
		vizualizareNote=new JButton("Note");
		grupuri=new JButton("Grupuri");
		
		cautareCurs.setVisible(true);
		vizualizareNote.setVisible(true);
		grupuri.setVisible(true);
		
		cautareCurs.setBounds(0, 0, 100, 50);
		vizualizareNote.setBounds(100, 0, 100, 50);
		grupuri.setBounds(200, 0, 100, 50);
		
		f.getContentPane().add(cautareCurs);
		f.getContentPane().add(	grupuri);
		f.getContentPane().add(vizualizareNote);
		
		cautareCurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			        	 AfisareCursuri nou=new  AfisareCursuri();
			        	 nou.afisare(studentid,0);
			            
			}
		});
		
		
		vizualizareNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AfisareNote afn= new AfisareNote();
				afn.afisare(studentid);
			}
		});
		
		grupuri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	        	Grupuri g=new Grupuri();
	        	g.afisare(studentid,userName,0,userid);
	            
	}
});
		 f.setVisible(true);
	}
}
