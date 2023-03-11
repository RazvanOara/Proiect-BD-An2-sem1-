import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AfisareNote {

	public AfisareNote() {
		// TODO Auto-generated constructor stub
	}
	
	public int get_id(String nick)
	{
		
		try {
			Statement   selectStatement = connection.createStatement();
			
			 rsss=selectStatement.executeQuery("select idstudents from students where students.username='"+nick+"'");
			rsss.next();	
			return Integer.parseInt(rsss.getString("idusers"));
			
		} catch (SQLException e) {
			return 0;
		}
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
	static ResultSet rss,rsss;
	static JFrame afN;
	public void afisare(int studentId)
	{
		getConnToSql(connection,rs);
		afN=new JFrame("Note");
		afN.setBounds(0,0,400,800);
		afN.setLayout(null);
		afN.setVisible(false);
	        int size=0;
	        
	       String note="<html>";
	       String cursuri="<html>";
	       
		try {
			
			Statement   selectStatement = connection.createStatement();
        rs=selectStatement.executeQuery("select* from enrollments_students where idstudents="+studentId);
        Statement   selectStatementC = connection.createStatement();
        
    	while(rs.next())
		{
    		size+=100;
    		if(studentId==Integer.parseInt(rs.getString("idstudents")));
				{
					
					note+=rs.getString("grade")+"<br/>";
				    rss=selectStatementC.executeQuery("select* from courses where idcourses="+rs.getString("idcourses"));
				rss.next();
				cursuri+=rss.getString("type")+" "+rss.getString("name")+"<br/>";
					
					
				}
		}
		}catch(Exception e) {};
		
		note+="<html>";
		cursuri+="<html>";
		 JLabel not=new JLabel("note");
	        not.setBounds(0, 0, size,200);
	        not.setVisible(true);
	   	 JLabel curs=new JLabel("cursuri");
	        curs.setBounds(100, 0, size, 200);
	        curs.setVisible(true);
	        not.setText(note);
	        curs.setText(cursuri);
	        afN.getContentPane().add(curs);
	        afN.getContentPane().add(not);
		afN.setLayout(null);
		afN.setVisible(true);
        
	}
}
