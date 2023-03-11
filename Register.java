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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mysql.cj.jdbc.CallableStatement;

public class Register {

	public Register() {
		// TODO Auto-generated constructor stub
	}
static JFrame f;
static Connection connection;
static ResultSet rs,rss,rsss;
static JTextField textnume,textprenume,textadresa,textcnp,textnick,textparola;

public void creare_student(int id,int year)
{
	
	try {
		Statement   selectStatement = connection.createStatement();
		 rss=selectStatement.executeQuery("call u.create_new_student("+id+",'"+textnume.getText()+"','"+textprenume.getText()+"',"+textcnp.getText()+",'"+textadresa.getText()+"',"+year+");");
		rss.next();		
		
	} catch (SQLException e) {
	}
}
public void creare_prof(int id,String dep)
{
	
	try {
		Statement   selectStatement = connection.createStatement();

 rss=selectStatement.executeQuery("call u.create_new_professor("+id+",'"+textnume.getText()+"','"+textprenume.getText()+"',"+textcnp.getText()+",'"+textadresa.getText()+"',"+"'"+dep+"',"+20+","+50+");");
		rss.next();		
		
	} catch (SQLException e) {
	}
}
public int get_id(String nick)
{
	
	try {
		Statement   selectStatement = connection.createStatement();
		
		 rsss=selectStatement.executeQuery("select idusers from users where users.username='"+nick+"'");
		rsss.next();	
		return Integer.parseInt(rsss.getString("idusers"));
		
	} catch (SQLException e) {
		return 0;
	}
}

	public void register()
	{
		
		 rs = null;
			try {
				
				 connection=DriverManager.getConnection("jdbc:mysql://localhost/u?user=root&password=12345&serverTimezone=UTC");
		          }
				
			
			catch(Exception ex)	{
				ex.printStackTrace();
			}
		
		
		f=new JFrame("Register");
		f.setBounds(0,0,900,800);

		JLabel nume=new JLabel("Nume:");
		JLabel prenume=new JLabel("Prenume:");
		JLabel cnp=new JLabel("CNP:");
		JLabel adresa=new JLabel("Adresa:");
		JLabel username=new JLabel("Nume utilizator:");
		JLabel parola=new JLabel("Parola:");
		nume.setVisible(true);
		prenume.setVisible(true);
		cnp.setVisible(true);
		adresa.setVisible(true);
		username.setVisible(true);
		parola.setVisible(true);
		
		nume.setBounds(100,0,80,50);
		prenume.setBounds(100,50,80,50);
		cnp.setBounds(100,100,80,50);
		adresa.setBounds(100,150,80,50);
		username.setBounds(100,200,90,50);
		parola.setBounds(100,250,80,50);
		
		f.getContentPane().add(nume);
		f.getContentPane().add(prenume);
		f.getContentPane().add(adresa);
		f.getContentPane().add(cnp);
		f.getContentPane().add(username);
		f.getContentPane().add(parola);
		
		textnume=new JTextField();
		textnume.setVisible(true);
		textnume.setBounds(200,17,200,20);
		f.getContentPane().add(textnume);
		
		textprenume=new JTextField();
		textprenume.setVisible(true);
		textprenume.setBounds(200,68,200,20);
		f.getContentPane().add(textprenume);
		
		textcnp=new JTextField();
		textcnp.setVisible(true);
		textcnp.setBounds(200,119,200,20);
		f.getContentPane().add(textcnp);
		
		textadresa=new JTextField();
		textadresa.setVisible(true);
		textadresa.setBounds(200,170,200,20);
		f.getContentPane().add(textadresa);

		textnick=new JTextField();
		textnick.setVisible(true);
		textnick.setBounds(200,221,200,20);
		f.getContentPane().add(textnick);
		
		
		textparola=new JTextField();
		textparola.setVisible(true);
		textparola.setBounds(200,272,200,20);
		f.getContentPane().add(textparola);
		
		JButton r=new JButton("REGISTER");
		r.setVisible(true);
		r.setBounds(250, 320, 100, 50);
		f.getContentPane().add(r);
		
		
		
		int nr=0;
		
		r.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			boolean ok=true;
			for(int i=0;i<textnume.getText().length();i++)
			{
				try {
				     Integer.parseInt(textnume.getText().charAt(i)+"");
				     ok=false;
				  } catch (NumberFormatException e) {
					    
				  }
			}
		
			for(int i=0;i<textprenume.getText().length();i++)
			{
				try {
				     Integer.parseInt(textprenume.getText().charAt(i)+"");
				     ok=false;
				  } catch (NumberFormatException e) {
					    
				  }
			}
			
			
			for(int i=0;i<textcnp.getText().length();i++)
			{
				try {
				     Integer.parseInt(textcnp.getText().charAt(i)+"");
				     
				  } catch (NumberFormatException e) {ok=false;
					    
				  }
			}
			
	
			
			if(textnick.getText().equals("")) ok=false;
			if(textparola.getText().equals(""))ok=false;
			if(textadresa.getText().equals(""))ok=false;
			boolean ok2=true;
			
			if(ok==false)
				JOptionPane.showMessageDialog(f, "Date introduse gresit!");
			else
			{
				try {
					
					 Statement   selectStatement = connection.createStatement();
			          rs=selectStatement.executeQuery("select* from users");
			          
					while(rs.next())
						if(rs.getString("username").equals(textnick.getText())) ok2=false;

				}
				catch(Exception e) {}
				
			}
			if(textparola.getText().length()<3&&ok)
				JOptionPane.showMessageDialog(f, "Parola e prea scurta!");

			if(ok&&!ok2) JOptionPane.showMessageDialog(f, "Username deja folosit!");
			if(ok&&ok2)
			{
				
				String[] options = { "Student","Profesor" };
				String rol=(String) JOptionPane.showInputDialog(null, "Rol",null, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				try {
				Statement   selectStatement = connection.createStatement();
				
				Statement   selectStatementt = connection.createStatement();
		       if(rol.equals("Student"))
		    		   {
		    	  
		    	   String[] options2 = { "1","2","3","4" };
		    	   int year=Integer.parseInt(JOptionPane.showInputDialog(null, "Anul de studiu",null, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]).toString());
		    	  rs=selectStatement.executeQuery("call u.create_new_user('"+textnick.getText()+"','"+textparola.getText()+"','"+"student"+"')");
		    	  
		    	 int id=get_id(textnick.getText());
		    creare_student(id,year);
		    	  JOptionPane.showMessageDialog(f, "Student inregistrat cu succes! ");
		    	  f.dispose();		    	  }
		       
		       else if(rol.equals("Profesor"))
		       {
		    	   String departament=(String) JOptionPane.showInputDialog("Introduceti departamentul");
		    	  rs=selectStatement.executeQuery(" call u.create_new_user('"+textnick.getText()+"','"+textparola.getText()+"','"+"profesor"+"')");
		    	   
		    	   int id=get_id(textnick.getText());
		    	   creare_prof(id,departament);
		    	   JOptionPane.showMessageDialog(f, "Profesor inregistrat cu succes! ");
			    	  f.dispose();		    
		       }
		       
		       
				}catch(Exception e) {};
			}
			
			}
		
		});
		
		
		
		SwingUtilities.updateComponentTreeUI(f);
	
		f.setLayout(null);
		f.setVisible(true);
	}
}
