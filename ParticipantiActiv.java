import java.sql.*;

import javax.swing.*;

public class ParticipantiActiv {

	public ParticipantiActiv() {
		
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
	static JFrame f;
	static JLabel [] studenti= new JLabel[100];
	static ResultSet rs,rs2;
	static Connection connection;
	
	String getNume(int idUser)
	{
		try {
		Statement   selectStatement = connection.createStatement();
	
		 rs2=selectStatement.executeQuery("SELECT * FROM u.students where idusers="+idUser);
		 rs2.next();
	return rs2.getString("first_name")+" "+rs2.getString("last_name");
		 
		}catch(Exception ex) {};
		return null;
	}
	
	 void show_participants(int idActiv,int idUser,int idGrup)
	 {
		 getConnToSql( connection, rs);
		 f=new JFrame();
		 f.setBounds(0,0,1500,800);
		 f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		 
		 for(int i=0;i<100;i++)
			 studenti[i]=new JLabel();
		 
		 int nr=0;
		 int nr2=0;
		 int n=0;
		 try {

				Statement   selectStatement = connection.createStatement();
				 rs=selectStatement.executeQuery("select* from u.activity_participants where idactivity="+idActiv);
				 while(rs.next())
				 { 
					
					 studenti[n].setText(getNume(Integer.parseInt(rs.getString("idusers"))));
					 studenti[n].setVisible(true);
					 f.getContentPane().add(studenti[n]);
					 studenti[n].setBounds(nr2,nr,250,30);
					 nr+=30;
					 if(nr%14==0)
					 {
						 nr=0;
						 nr2+=250;
					 }
					 n++;
				 }
			 
			 
		 }catch(Exception ex) {};
		 
		 
		 
		 
		 
		 f.setLayout(null);
		 f.setVisible(true); 
	 }
}
