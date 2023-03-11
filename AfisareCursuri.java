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

public class AfisareCursuri {

	public AfisareCursuri() {
		
	}
	private String getFirstWord(String text) {
		  int index = text.indexOf(' ');
		  if (index > -1) { 
		    return text.substring(0, index).trim(); 
		  } else {
		    return text; 
		  }
	}

	public int id_prof_min(int idCurs)
	{
		try {
			
			Statement   selectStatement = connection.createStatement();
			rs7=selectStatement.executeQuery("call u.min_count("+idCurs+");");
			rs7.next();
			return Integer.parseInt(rs7.getString("idprofessors"));
		}
		catch(Exception ex) {};
		return 0;
	}
	public void inscriere_curs(int studentId, String cursName)
	{
	
		try
		{
			
			Statement   selectStatement = connection.createStatement();
			Statement   selectStatementt = connection.createStatement();
			rs4=selectStatement.executeQuery("select* from courses where name='"+cursName+"'");
			while(rs4.next())
			{
				int cursid=getCursId_name_and_type(rs4.getString("name"),rs4.getString("type"));
				int gavrea=id_prof_min(cursid);
				rs5=selectStatementt.executeQuery("call u.insert_s_enroll("+studentId+","+cursid+","+gavrea+");");
			}
			
		}
		catch(Exception ex)
		{
			
		}
	}
	public float getProcent(String name, String type)
	{
		String unde="";
		try {
		Statement   selectStatementt = connection.createStatement();
		 rs1=selectStatementt.executeQuery("SELECT * FROM u.percentagesgrades where course_name='"+name+"'");
		 rs1.next();
		 if(type.equals("Curs"))
		unde=rs1.getString("course_p");
		 if(type.equals("Laborator"))
			unde=rs1.getString("laboratory_p");
		 if(type.equals("Seminar"))
			unde=rs1.getString("seminar_p");
		 
		}catch(Exception ex) {};
		return Integer.parseInt(unde);
	}
	public String get_medie(int idStudent,String name)
	{
		
		float n=0;
		try {
				 Statement   selectStatementt = connection.createStatement();
				 rs8=selectStatementt.executeQuery("SELECT * FROM u.courses where name='"+name+"'");
				 while(rs8.next())
				 {	
					 Statement   selectStatement = connection.createStatement();
					 rs9=selectStatement.executeQuery("SELECT * FROM u.enrollments_students where idcourses="+Integer.parseInt(rs8.getString("idcourses"))+" and idstudents="+idStudent);
					 rs9.next();
					 if(rs9.getString("grade") == null) return "necules";
					 else
					 {
						float pr=getProcent(name,rs8.getString("type"))/100;
						 n+=Integer.parseInt(rs9.getString("grade"))*pr;
					 }
				 }
			 
			 
		}
		catch(Exception e) {};
		
		
		return String.valueOf(n);
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
	public boolean checkRegCurs(int idCurs, int idStudent)
	{
		int n=-1;
		try {
			Statement   selectStatement = connection.createStatement();
			 rss=selectStatement.executeQuery("select* from u.enrollments_students where idstudents= "+idStudent+" and idcourses= "+idCurs);
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
	static ResultSet rs1,rss,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rss2;
	static JFrame afC;
	static int insc;
	
	public void afisare(int studentId, int isAdmin)
	{
		getConnToSql(connection,rs);
		afC=new JFrame("Afisare Cursuri");
		
		afC.setLayout(null);
		afC.setVisible(false);
		JButton  [] butone=new JButton[100];
		for(int i=0;i<100;i++)
			butone[i]=new JButton();

		int num=0;
		int nr=0;
		int num2=0; 
	   
		String cursuri="";
		String text_cursuri="<html>";
		try {
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("select* from courses");
				while(rs.next())
				{
					boolean ok=true;
					if(cursuri.contains(rs.getString("name")))
						ok=false;
	
					if(ok)
					{
						
						cursuri+=rs.getString("name");
					butone[nr].setBounds(num2, num, 200, 50);
					butone[nr].setText(rs.getString("name"));
					butone[nr].setVisible(true);
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
							int cursId=getCursId(getFirstWord( butone[n].getText()));
						boolean inreg=checkRegCurs(cursId,studentId);
					
						
					if(inreg==true)
					{
						if(isAdmin==0)
						{
							
							
							JOptionPane.showMessageDialog(afC, "Sunteti deja inscris la acest curs!   Nota:"+get_medie(studentId,butone[n].getText()));
							
						
						}
						else
							 JOptionPane.showMessageDialog(afC, "Studentul este deja inscris la curs!");
					 insc=JOptionPane.NO_OPTION;
					}
					else
					{
						if(isAdmin==0)
						 insc=JOptionPane.showConfirmDialog(afC,"Doriti sa va inscrieti la curs?" );
						else
							 insc=JOptionPane.showConfirmDialog(afC,"Doriti sa inscrieti studentul la curs?" );
							
					}
					
					if(insc==JOptionPane.YES_OPTION)
					{
					
						inscriere_curs(studentId,getFirstWord( butone[n].getText()));
						JOptionPane.showMessageDialog(afC, "Inscriere realizata cu succes!");
						
					}
							}
						
						});
				}
				
				
				}
		}catch (Exception e){}
		
		text_cursuri+="<html>";
		SwingUtilities.updateComponentTreeUI(afC);
		afC.setBounds(300,50,num+100,800);
		afC.setLayout(null);
		afC.setVisible(true);
		
	}
}
