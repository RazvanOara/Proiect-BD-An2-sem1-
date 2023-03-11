import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Profesor {

	public Profesor(int id) {
	this.profid=id;
	}

	static JMenuBar mb;
	static JMenuItem logOff,info;
	static JButton cautareCurs,grupuri;
	static  String optionCurs;
	static int profid;
	static JFrame f;
	public void do_profesor(JFrame popUpLogIn,String userName,String userPassword,int userid)
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
				int n=JOptionPane.showConfirmDialog(popUpLogIn, "Sunteti sigur?!");
				if(n==JOptionPane.OK_OPTION)
				{	popUpLogIn.show();
				f.dispose();
				}
				
			}
		});
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Afisare_inf_prof a=new Afisare_inf_prof();
				a.afisare_date(profid,userName,userPassword,userid,f,popUpLogIn);
				
			}
		});
		
		cautareCurs=new JButton("Cursuri");
		grupuri=new JButton("Grupuri");
		
		cautareCurs.setVisible(true);
		grupuri.setVisible(true);
		
		cautareCurs.setBounds(0, 0, 100, 50);
		
		grupuri.setBounds(100, 0, 100, 50);
		
		f.getContentPane().add(cautareCurs);
		f.getContentPane().add(	grupuri);
	
		
		cautareCurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			        	 Afisare_curs_prof nou=new   Afisare_curs_prof();
			        	 nou.afisare(profid);
			        	 
			            
			}
		});
		
		grupuri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	        	Grupuri g=new Grupuri();
	        	g.afisare(profid,userName,1,userid);
	            
	}
});
		 f.setVisible(true);	
	}
}
