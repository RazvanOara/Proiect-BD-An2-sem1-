import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class CursuriProfPtAdmin {

	public CursuriProfPtAdmin() {
		
	}
	private String getFirstWord(String text) {
		  int index = text.indexOf(' ');
		  if (index > -1) { 
		    return text.substring(0, index).trim(); 
		  } else {
		    return text; 
		  }
	}
	
	
	public void inscriere_curs(int profId, int idCurs)//TODO inscrierea tre sa o faci
	{
	
		try
		{
			
			Statement   selectStatementt = connection.createStatement();
		
				rs=selectStatementt.executeQuery("call u.insert_enroll_prof"
						+ "("+profId+","+idCurs+");");
				
				
			
		}
		catch(Exception ex)
		{
			
		}
	}
	
	
	public int getCursId_name_and_type(String name,String type)
	{
		
		
		try {
			Statement   selectStatement = connection.createStatement();
			 rs6=selectStatement.executeQuery("select* from courses where name='"+name+"' and type ='"+type+"'");
			 rs6.next();
			 return Integer.parseInt(rs6.getString("idcourses"));
		}
		catch(Exception e) {};
		return 0;
		
	}
	
	public int getCursId(String name)
	{
		
		
		try {
			Statement   selectStatement = connection.createStatement();
			 rs3=selectStatement.executeQuery("select* from courses where name='"+name+"'");
			 rs3.next();
			 return Integer.parseInt(rs3.getString("idcourses"));
		}
		catch(Exception e) {};
		return 0;
		
	}
	public boolean checkRegCurs(int idCurs, int idProf)
	{
		int n=-1;
		try {
			Statement   selectStatement = connection.createStatement();
			
			 rss=selectStatement.executeQuery("select* from u.enrollments_professors where idprofessors= "+idProf+" and idcourses= "+idCurs);
			rss.next();
			try{
			 n=Integer.parseInt(rss.getString("idcourses"));
				}catch(Exception ex) {return false;};
		}catch (Exception e) {};
		if(n==-1) return false;
		return true;
		
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
	static ResultSet rs;
	static ResultSet rss,rs3,rs4,rs5,rs6,rs7;
	static JFrame afC;
	static int insc;
	
	public void afisare(int profId)
	{
		getConnToSql(connection,rs);
		afC=new JFrame("Afisare Cursuri");
		
		afC.setLayout(null);
		afC.setVisible(false);
		Butone  [] butone=new Butone[100];
		for(int i=0;i<100;i++)
			butone[i]=new Butone();

		int num=0;
		int nr=0;
		int num2=0; 
	   String []curs_nume=new String[1000];
	   String []curs_tip=new String[1000];
	   for(int i=0;i<1000;i++)
	   {
		   curs_nume[i]=new String();
		   curs_tip[i]= new String();
	   }
		String cursuri="";
		String text_cursuri="<html>";
		try {
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("select* from courses");
				while(rs.next())
				{
					curs_nume[nr]=rs.getString("name");
					curs_tip[nr]=rs.getString("type");
					cursuri+=rs.getString("name");
					butone[nr].setBounds(num2, num, 200, 50);
					butone[nr].setText(rs.getString("name")+" "+rs.getString("type"));
					butone[nr].setVisible(true);
					butone[nr].i=Integer.parseInt(rs.getString("idcourses"));
					afC.getContentPane().add(butone[nr]);
					int n=nr;
					nr++;
					num+=50;
					if(nr%15==0&&nr!=0)
					{
						num2+=200;
					}
					
					butone[n].addActionListener(new ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							int cursId=-1;
							try{
								cursId=getCursId_name_and_type(curs_nume[n],curs_tip[n]);
								
							}catch (Exception e) {};
						boolean inreg=checkRegCurs(cursId,profId);
					
					if(inreg==true)
					{
						
					 JOptionPane.showMessageDialog(afC, "Este inscris la acest curs!");
					 insc=JOptionPane.NO_OPTION;
					}
					else
					{
						
						 insc=JOptionPane.showConfirmDialog(afC,"Doriti sa inscrieti profesorul la curs?" );
							
					}
					
					if(insc==JOptionPane.YES_OPTION)
					{
					
						inscriere_curs(profId,butone[n].i);
						JOptionPane.showMessageDialog(afC, "Inscriere realizata cu succes!");
						
					}
							}
						
						});
				
				
				
				}
		}catch (Exception e){}
		
		text_cursuri+="<html>";
		SwingUtilities.updateComponentTreeUI(afC);
		afC.setBounds(300,50,num+100,800);
		afC.setLayout(null);
		afC.setVisible(true);
		
	}
}
