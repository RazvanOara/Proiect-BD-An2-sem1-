import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

public class calculare_nota_finala {

	static Connection connection;
	static ResultSet rs;
	static JFrame f;
	static int cursId,nr;
	public static void getConnToSql()
	{
		  rs = null;
			try {
				
				 connection=DriverManager.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		          }
				
			
			catch(Exception ex)	{
				ex.printStackTrace();
			}
			
		}
	
	
	public void afisare(int idProf) {
		getConnToSql();
		f=new JFrame("Calculare nota finala");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setBounds(0,0,1400,800);
		int num=0;
		int num2=0; 
		Butone  [] butone=new Butone[100];
		for(int i=0;i<100;i++)
			butone[i]=new Butone();
		ArrayList<String> names = new ArrayList<String>();

try {
			
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("call u.courses_prof("+idProf+")");
			 while(rs.next())
			 {
				 if(names.contains(rs.getString("name"))) {}
				 else
				 {
				 cursId=Integer.parseInt(rs.getString("idcourses"));
				 butone[nr].i=cursId;
				 butone[nr].setBounds(num2, num, 200, 50);
					butone[nr].setText(rs.getString("name"));
					names.add(rs.getString("name"));
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
						Schimbare_procent a= new Schimbare_procent ();
						a.afisare(butone[n].getText());
							}
						
						});
				 }
					
			 }
}catch(Exception ex) {};
			
				
				
		
		
		
		f.setLayout(null);
		f.setVisible(true);
	}
	
}
