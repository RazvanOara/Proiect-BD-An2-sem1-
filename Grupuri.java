import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Grupuri {

	public Grupuri() {
		
	}
	
	public int getCursId(String name_and_type)
	{
		StringTokenizer t = new StringTokenizer(name_and_type);
		String [] words=new String[10];
		int i=0,j;
		while(t.hasMoreTokens())
		{
		words[i] = t.nextToken();
		i++;
		}
		String type=words[i-1];
		String name= new String();
		for( j=0;j<i-2;j++)
		{
			name+=words[j]+"_";
		}
			try{name+=words[i-2];}catch(Exception e) {};
			
		try {
			Statement   selectStatement = connection.createStatement();
			 rs3=selectStatement.executeQuery("select* from courses where name='"+name+"' and type='"+type+"'");
			 rs3.next();
			 return Integer.parseInt(rs3.getString("idcourses"));
		}
		catch(Exception e) {};
		return 0;
		
	}
	public boolean checkRegCurs(int idCurs, int idStudentProf,int isProf)
	{
		if(isProf==0)
		{
		int n=-1;
		try {
			Statement   selectStatement = connection.createStatement();
			 rs2=selectStatement.executeQuery("select* from u.enrollments_students where idstudents= "+idStudentProf+" and idcourses= "+idCurs);
			rs2.next();
			try{
			 n=Integer.parseInt(rs2.getString("idcourses"));
				}catch(Exception ex) {return false;};
		}catch (Exception e) {};
		if(n==-1) return false;
		}
		else
		{
			int n=-1;
			try {
				Statement   selectStatement = connection.createStatement();
				 rs2=selectStatement.executeQuery("select* from u.enrollments_professors where idprofessors= "+idStudentProf+" and idcourses= "+idCurs);
				rs2.next();
				try{
				 n=Integer.parseInt(rs2.getString("idcourses"));
					}catch(Exception ex) {return false;};
			}catch (Exception e) {};
			if(n==-1) return false;
		}
		return true;
		
	}
	private String getFirstWord(String text) {
		  int index = text.indexOf(' ');
		  if (index > -1) { 
		    return text.substring(0, index).trim(); 
		  } else {
		    return text; 
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
	static ResultSet rs,rs2,rs3;
	static JFrame f;
	static int idGrup;
	public void afisare(int id,String user,int isProf,int idUser)
	{
		getConnToSql(connection,rs);
		f=new JFrame("Grupuri");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setBounds(0,0,1500,800);
		f.setLayout(null);
		f.setVisible(false);	
		Butone  [] butone=new Butone[100];
		for(int i=0;i<100;i++)
			butone[i]=new Butone();
		int nr=0,num2=0,num=0;
		try {
					
			Statement   selectStatement = connection.createStatement();
			 rs=selectStatement.executeQuery("SELECT * FROM u.study_groups;");
			 
			while(rs.next())
			{
				butone[nr].i=Integer.parseInt(rs.getString("idgroup"));
				String name=rs.getString("name");
				butone[nr].setBounds(num2, num, 200, 50);
				butone[nr].setText(name);
				butone[nr].setVisible(true);
				f.getContentPane().add(butone[nr]);
				int n=nr;
				nr++;
				num+=50;
				if(nr%15==0&&nr!=0)
				{
					num2+=200;
					num=0;
				}
				
				
				butone[n].addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
				
						int cursId=getCursId(butone[n].getText());
						
				     	boolean inreg=checkRegCurs(cursId,id,isProf);
				     	

					if(inreg)
					{
						
						chose_chat_or_activ nou=new chose_chat_or_activ();
						nou.afisare(butone[n].i,isProf,idUser);
						f.dispose();
					}
						
						
						
					
					}
					
					});
				
			}
			 
			
		}catch(Exception ex) {};
		
		
		
		
		SwingUtilities.updateComponentTreeUI(f);
		f.setBounds(300,50,num+100,800);
		f.setLayout(null);
		f.setVisible(true);
	}
}
