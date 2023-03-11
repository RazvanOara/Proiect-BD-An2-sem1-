import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class chose_chat_or_activ   {

	public chose_chat_or_activ() {
		
	}
	public static void getConnToSql(Connection conection,ResultSet rs)
	{
		  rs = null;
			try {
				
				 connection=DriverManager.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		          }
				
			
			catch(Exception ex)	{
				ex.printStackTrace();
			}
			
		}
	boolean e_la_activ(int idUser,int idActivi)
	{
		try {
			
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("select* from u.activity_participants where idactivity="+idActivi);
			while( rs.next())
			{
				if(idUser==Integer.parseInt(rs.getString("idusers")))
					return true;
			}
		}catch(Exception ex) {};
		return false;
	}

	static Connection connection;
	static ResultSet rs,rs2,rs3,rsc;
	
	
	static JMenuBar mb;
	static JMenuItem creare_activ;
	static String numActiv,data;
	static int min_part,timp_expirare;
	JFrame f;
	public void afisare(int idGrup,int isProf,int idUser)
	{
		f= new JFrame();
		getConnToSql( connection, rs);
		f.setVisible(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Butone []activitati=new Butone[100];
		for(int i=0;i<100;i++)
			activitati[i]=new Butone();
		
		DoChat a= new DoChat();
		a.afisare(f,idUser,idGrup);
		
		 mb=new JMenuBar();
		 creare_activ=new JMenuItem("Creare activitate");
		 mb.add(creare_activ);
		if(isProf==1) f.setJMenuBar(mb);
		 
		 
		 
		creare_activ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int id=0;
				numActiv=JOptionPane.showInputDialog("Numele activitatii");
				if(numActiv!=null)
				data=JOptionPane.showInputDialog("Data sustinerii(YYYY-MM-DD hh:mm:ss)");
				if(data!=null)
				min_part= Integer.parseInt(JOptionPane.showInputDialog("Numarul minim de participanti"));
				if(min_part!=0)
				timp_expirare=3600*Integer.parseInt(JOptionPane.showInputDialog("Valabilitatea activitatii daca nu e atinge numarul minim de participanti(ore)"));
			try {	Statement   selectStatement = connection.createStatement();
			 System.out.println("call u.new_activity("+idGrup+",'"+numActiv+"','"+data+"',"+min_part+","+timp_expirare+")");
				 rs=selectStatement.executeQuery("call u.new_activity("+idGrup+",'"+numActiv+"','"+data+"',"+min_part+","+timp_expirare+")");
				 System.out.println("Eber");
				 rsc=selectStatement.executeQuery("SELECT * FROM u.group_activities order by idactivity desc" );
				 rsc.next();
				 id=Integer.parseInt(rsc.getString("idactivity"));
				 System.out.println(id+"k,mxgh,h");
				 
			}catch(Exception ex) {};
			
			try {
				Statement   selectStatement = connection.createStatement();
				 rs=selectStatement.executeQuery("call u.activity_participant("+idUser+","+ id+")");
				 
			}catch(Exception ex) {};
			
			}
		});
		
		
		
		int nr=0;
	
		try {
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("call u.activity_suggestions(" + idGrup + ");");
			 while(rs.next())
			 {
				
				 activitati[nr].i=Integer.parseInt(rs.getString("idactivity"));
				 activitati[nr].setBounds(0,nr*50,400,50);
				 activitati[nr].setVisible(true);
				f.getContentPane().add(activitati[nr]);
				activitati[nr].setText(rs.getString("name") + " " + rs.getString("date") + " participanti:"
                        + rs.getString("nr_participants") + "/" + rs.getString("min_participants"));
				 int n=nr;
				 nr++;
				 activitati[n].addActionListener(new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {	
							
							
							if(isProf==0)
						{try {
							Statement   selectStatement = connection.createStatement();
							int nad=JOptionPane.showConfirmDialog(f, "Doriti sa va inscrieti la activitate?");
							if(nad==JOptionPane.YES_OPTION)
								if((e_la_activ(idUser,activitati[n].i))==false)
									{
									
									rs=selectStatement.executeQuery("call u.activity_participant("+idUser+","+ activitati[n].i+")");
							JOptionPane.showMessageDialog(f, "V-ati inscris!");}
								else {
											JOptionPane.showMessageDialog(f, "Participati deja la activitate");
									};
									
							 
						}catch(Exception ex) {};}
						else
						{
							ParticipantiActiv a= new ParticipantiActiv();
							a.show_participants( activitati[n].i,idUser,idGrup);
						}
							
						}
						
						});
			 }
			
		}catch(Exception e) {};
	
		f.setLayout(null);
	}
}
