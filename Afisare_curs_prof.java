import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Afisare_curs_prof {

	public Afisare_curs_prof() {
	
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
	static Connection connection;
	static ResultSet rs,rs2,rs3;
	static JFrame f,afs;
	static int cursId,nr3,nr2,dis,enrlid,nrs;
	static int [] ids=new int[100];
	static boolean ok;
	static JMenuBar mb;
	static JMenuItem note;
	public void afisare(int idProf)
	{
		int num=0;
		int nr=0;
		int num2=0; 
		 nrs=0;
		ok=true;
		getConnToSql(connection,rs);
		f=new JFrame();
		f.setLayout(null);
		afs=new JFrame("Note");
		afs.setLayout(null);
		
		mb=new JMenuBar();
		 note=new JMenuItem("Calcularea notei finale");
		 mb.add(note);
		 f.setJMenuBar(mb);
		
		 note.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					calculare_nota_finala a= new calculare_nota_finala ();
					a.afisare(idProf);
					}
				
				});
		
		
		Butone  [] butone=new Butone[100];
		for(int i=0;i<100;i++)
			butone[i]=new Butone();
		
		Butone  [] b=new Butone[100];
		for(int i=0;i<100;i++)
			b[i]=new Butone();
		
		 Icon icon = new ImageIcon("C:\\Users\\razva\\Downloads\\bifa.png");
		JButton salvare=new JButton(icon);
		salvare.setVisible(false);
		afs.getContentPane().add(salvare);
		
		try {
			
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("call u.courses_prof("+idProf+")");
			 while(rs.next())
			 {
				 cursId=Integer.parseInt(rs.getString("idcourses"));
				 butone[nr].i=cursId;
				 butone[nr].setBounds(num2, num, 200, 50);
					butone[nr].setText(rs.getString("name")+" "+rs.getString("type"));
					butone[nr].setVisible(true);
					f.getContentPane().add(butone[nr]);
					int n=nr;
					nr++;
					num+=50;
					if(nr%15==0&&nr!=0)
					{
						num2+=200;
					}
					
					
					
					butone[n].addActionListener(new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							f.dispose();
							dis=15;
						
							try {
								 nr2=0;nr3=0;
							Statement   selectStatementt = connection.createStatement();
							rs2=selectStatementt.executeQuery("call u.students_prof("+idProf+","+ butone[n].i+");");
							while(rs2.next())
							{
								enrlid=Integer.parseInt(rs2.getString("idenrollments"));
								b[nrs].i=enrlid;
								
								b[nrs].setText(rs2.getString("first_name")+ " "+rs2.getString("last_name")+" -  nota:");
								b[nrs].setVisible(true);
								 b[nrs].setBounds(nr3,nr2,202,50);
								b[nrs].setOpaque(false);
								 b[nrs].setContentAreaFilled(false);
								 b[nrs].setBorderPainted(false);
								 b[nrs].setFont(new Font("Arial", Font.PLAIN,15));
									afs.getContentPane().add(b[nrs]);
									int ns=nrs;
									nrs++;
									
									
								 JTextField nota=new JTextField(rs2.getString("grade"));
								 nota.setBounds(nr3+210,nr2+13,30,25);
								 nota.setFont(new Font("Arial", Font.PLAIN,15));
								 nota.setVisible(true);
								 nota.setEnabled(false);
								 nr2+=50;
								 afs.getContentPane().add(nota);
								 if(nr2>1500)
								 {
									 nr3+=210;
								 }
					
								 b[ns].addActionListener(new ActionListener() {
										public void actionPerformed(java.awt.event.ActionEvent evt) {	
											
										if(ok)
										{
											
											nota.setEnabled(true);
											ok=false;
											
										}else
										{
											
												
											int schimb=JOptionPane.showConfirmDialog(afs, "Se va modifica nota.Contiuati?");
											if(schimb==JOptionPane.YES_OPTION)
											{
											nota.setEnabled(false);
											ok=true;
											
											try {
											
												Statement   selectStatement3 = connection.createStatement();
											
												rs3=selectStatement3.executeQuery("call u.update_grade("+b[ns].i+","+ nota.getText()+");");
												rs3.next();
												
											}catch(Exception e) {};
											
											}
										
										}
							
											
											

											}
										
										});
								 
								
								 
								 
							}
							
							
							
							
							
							
							
							afs.setBounds(0,0,1500,800);
							afs.setVisible(true);
							
							}catch(Exception e) {}
							
							}
						
						});
					
					
			 }
				
			
			
		}catch(Exception e) {};
		f.setBounds(300,50,num+200,800);
		f.setVisible(true);
	}
	
}
