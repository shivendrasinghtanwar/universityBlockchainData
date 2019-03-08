package blockchain;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

import javax.swing.*;

import io.vertx.core.AbstractVerticle;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.WriterException;

class LoginFrame extends JFrame implements ActionListener,FocusListener
{
 JTextField tuser;
 JPasswordField tpas;
 JButton bsubmit,bforget,bcreate;
  	
 LoginFrame()
 {
  super("Login");
  setSize(550,300);
  setLocation(200,150);
	
  JLabel l1=new JLabel("Username");
  l1.setFont(new Font("lucida consol",Font.PLAIN,18));
  l1.setForeground(Color.white);

  JLabel l2=new JLabel("Password");
  l2.setFont(new Font("lucida consol",Font.PLAIN,18));
  l2.setForeground(Color.white);

  tuser=new JTextField();  
  tuser.setFont(new Font("lucida consol",Font.PLAIN,18));

  tpas=new JPasswordField(); 
  tpas.setFont(new Font("lucida consol",Font.PLAIN,18));

  bsubmit=new JButton("submit");
  bsubmit.setFont(new Font("lucida consol",Font.PLAIN,18));
  bsubmit.setBackground(new Color(100,100,150));
  bsubmit.setForeground(Color.white);
  
  bforget=new JButton("forget password");
  bforget.setFont(new Font("lucida consol",Font.PLAIN,12));
  bforget.setBackground(new Color(100,100,150));
  bforget.setForeground(Color.green);

  bcreate=new JButton("sign up");
  bcreate.setFont(new Font("lucida consol",Font.PLAIN,12));
  bcreate.setBackground(new Color(100,100,150));
  bcreate.setForeground(Color.green);

  LoginPanel p1=new LoginPanel(5,5,5,5);
  p1.setLayout(new GridLayout(2,1,5,5));
   
  LoginPanel p11=new LoginPanel(20,1,20,1);
  p11.setLayout(new BorderLayout(5,5));
  p11.add(l1,BorderLayout.WEST);
  p11.add(tuser,BorderLayout.CENTER);

  LoginPanel p12=new LoginPanel(20,1,20,1);
  p12.setLayout(new BorderLayout(8,5));
  p12.add(l2,BorderLayout.WEST);
  p12.add(tpas,BorderLayout.CENTER);

  p1.add(p11);
  p1.add(p12); 

  LoginPanel p2=new LoginPanel(5,5,5,5);
  p2.setLayout(new GridLayout(1,3,5,5));
  
  	LoginPanel p21=new LoginPanel(5,5,5,5);
  	p21.setLayout(new BorderLayout(5,5));
  	p21.add(bforget,BorderLayout.CENTER);
  	LoginPanel p22=new LoginPanel(5,5,5,5);
  	p22.setLayout(new BorderLayout(5,5));
  	p22.add(bsubmit,BorderLayout.CENTER);
  	LoginPanel p23=new LoginPanel(5,5,5,5);
  	p23.setLayout(new BorderLayout(5,5));
  	p23.add(bcreate,BorderLayout.CENTER);
  p2.setBorder(BorderFactory.createLineBorder(Color.white,1));

  p2.add(p21);
  p2.add(p22);
  p2.add(p23);
  
  LoginPanel lp=new LoginPanel(30,15,15,15);
  lp.setLayout(new BorderLayout(10,10));
  lp.add(p1,BorderLayout.CENTER);
  lp.add(p2,BorderLayout.SOUTH); 
  add(lp);

  bsubmit.addActionListener(this);
  bforget.addActionListener(this);
  bcreate.addActionListener(this);
  
  tuser.addFocusListener(this);
  tpas.addFocusListener(this);
  
  
  addWindowListener(new LoginAdapter(this));
 }
 
 public void focusGained(FocusEvent fe) {}
 
 public void focusLost(FocusEvent fe)   {}
 
 public void actionPerformed(ActionEvent ae)
 {
	 if(ae.getSource()==bsubmit)
	 {
		 if(tuser.getText().length()==0 || tpas.getText().length()<5)
		 {
			 JOptionPane.showMessageDialog(this, "Invalid username or password !");
			 tuser.requestFocus();
			 return;
		 }
		
		 String signin= "Login:" +tuser.getText() +":" +tpas.getText();
		 
		 String msg="";
		 try
		 {
			 login.pw.println(signin);   
			 msg=login.sc.nextLine();
		 }
		 catch(NoSuchElementException e)
		 {
			 JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
			 return;
		 }
		 catch(NullPointerException e)
		 {
			 JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
			 return;	   
		 }   
		 
		 switch(msg)
		 {
		 	case "success" :
		 	{
		 		String info=login.sc.nextLine();
		 		String[] user_info=info.split(":");
		 		
		 	  	home h=new home(user_info[0], user_info[1], user_info[2]);
		    	h.setVisible(true);
		    	h.setDefaultCloseOperation(EXIT_ON_CLOSE);
		  
		    	dispose();
		    	
		    	break;
		 	}
		 	
		 	case "fail" :
		 	{
				 JOptionPane.showMessageDialog(this, "Invalid username or password !");
				 tuser.requestFocus();

				 break;
		 	}
		 }

	 }
  
	 if(ae.getSource()==bcreate)
	 {
		  account a=new account(this);
		  a.setVisible(true);
		  a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  this.setVisible(false);
	 }
	 
	 if(ae.getSource()==bforget)
	 {
			JOptionPane.showMessageDialog(this, "maje karo !");
	 }
 
 }


}


class account extends JFrame implements ActionListener,FocusListener
{
	JButton bsignup, bregistered;
	JLabel lname,lmail,lusername,lpassword, ltitle;
	JTextField tname,tmail,tusername;
	JPasswordField tpassword;
	
	LoginFrame lf;

	//int i=0;
	
	account(LoginFrame lf)
	{
		  super("Create Account");
		  setSize(550,300);
		  setLocation(200,150);
	
		  this.lf=lf;
		  
		  bsignup=new JButton("Sign Up");
		  bsignup.setFont(new Font("lucida consol",Font.PLAIN,18));
		  bsignup.setBackground(new Color(100,100,150));
		  bsignup.setForeground(Color.white);

		  bregistered=new JButton("Already Registered");
		  bregistered.setFont(new Font("lucida consol",Font.PLAIN,18));
		  bregistered.setBackground(new Color(100,100,150));
		  bregistered.setForeground(Color.green);

		  ltitle=new JLabel("Create Account");
		  ltitle.setFont(new Font("lucida consol",Font.PLAIN,25));
		  ltitle.setForeground(Color.gray);
		  
		  lname=new JLabel("Name");
		  lname.setFont(new Font("lucida consol",Font.PLAIN,18));
		  lname.setForeground(Color.white);
 
		  lmail=new JLabel("Mail id");
		  lmail.setFont(new Font("lucida consol",Font.PLAIN,18));
		  lmail.setForeground(Color.white);
 
		  lusername=new JLabel("Username");
		  lusername.setFont(new Font("lucida consol",Font.PLAIN,18));
		  lusername.setForeground(Color.white);
 
		  lpassword=new JLabel("Password");
		  lpassword.setFont(new Font("lucida consol",Font.PLAIN,18));
		  lpassword.setForeground(Color.white);
 
		  tname=new JTextField();  
		  tname.setFont(new Font("lucida consol",Font.PLAIN,18));
	
		  tmail=new JTextField();  
		  tmail.setFont(new Font("lucida consol",Font.PLAIN,18));
		  
		  tusername=new JTextField();  
		  tusername.setFont(new Font("lucida consol",Font.PLAIN,18));

		  tpassword=new JPasswordField(); 
		  tpassword.setFont(new Font("lucida consol",Font.PLAIN,18));

		  
		  LoginPanel p=new LoginPanel(5,5,5,5);
		  p.setLayout(new BorderLayout(5,5));
		  	
		  	LoginPanel p1=new LoginPanel(5,5,5,5);
		  	p1.setLayout(new GridLayout(4,1,5,5));
		  		LoginPanel p11=new LoginPanel(3,1,3,1);
		  		p11.setLayout(new BorderLayout(40,5));
		  		p11.add(lname,BorderLayout.WEST);
		  		p11.add(tname,BorderLayout.CENTER);
		  		
		  		LoginPanel p12=new LoginPanel(3,1,3,1);
		  		p12.setLayout(new BorderLayout(35,5));
		  		p12.add(lmail,BorderLayout.WEST);
		  		p12.add(tmail,BorderLayout.CENTER);
		  		
		  		LoginPanel p13=new LoginPanel(3,1,3,1);
		  		p13.setLayout(new BorderLayout(6,5));
		  		p13.add(lusername,BorderLayout.WEST);
		  		p13.add(tusername,BorderLayout.CENTER);
		  		
		  		LoginPanel p14=new LoginPanel(3,1,3,1);
		  		p14.setLayout(new BorderLayout(9,5));
		  		p14.add(lpassword,BorderLayout.WEST);
		  		p14.add(tpassword,BorderLayout.CENTER);
		  	p1.add(p11);
		  	p1.add(p12);
		  	p1.add(p13);
		  	p1.add(p14);
	
		  	LoginPanel p2=new LoginPanel(5,5,5,5);
		  	p2.setLayout(new GridLayout(1,2,10,10));
		  	p2.add(bregistered);
		  	p2.add(bsignup);
		    p2.setBorder(BorderFactory.createLineBorder(Color.white,1));

		  	LoginPanel p3=new LoginPanel(5,175,5,5);
		  	p3.setLayout(new BorderLayout(5,5));
		  	p3.add(ltitle);
		    
		  	p.add(p3,BorderLayout.NORTH);
		    p.add(p1,BorderLayout.CENTER);
		    p.add(p2,BorderLayout.SOUTH);
	
		  add(p);
		  
		  bregistered.addActionListener(this);
		  bsignup.addActionListener(this);
		  
		  tname.addFocusListener(this);
		  tmail.addFocusListener(this);
		  tusername.addFocusListener(this);
		  tpassword.addFocusListener(this);
		  
	}
	
	
	public void focusGained(FocusEvent fe) {}
	 
	public void focusLost(FocusEvent fe)   {}
	 
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==bregistered)
		{
			lf.setVisible(true);
			dispose();
		}
		
		if(ae.getSource()==bsignup)
		{
			if(tname.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this, "name required !");
				tname.requestFocus();
				return;
			}
			
			if(tmail.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this, "mail_id required !");
				tmail.requestFocus();
				return;
			}
			
			if(tusername.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this, "username required !");
				tusername.requestFocus();
				return;
			}
			
			if(tpassword.getPassword().length<5)
			{
				JOptionPane.showMessageDialog(this, "password is too short !"); 
				tpassword.requestFocus();
				return;
			}
			
			String signup="Create:" +tname.getText().trim() +":" +tmail.getText() +":" +tusername.getText() +":" +tpassword.getText();

			String msg="";
			try
			{
				login.pw.println(signup);   
			    msg=login.sc.nextLine();
			}
			catch(NoSuchElementException e)
			{
				JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
				return;
			}
			catch(NullPointerException e)
			{
			    JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
				return;	   
			}
			
			switch(msg)
			{
			    case "created" :
			    {
			    	JOptionPane.showMessageDialog(this, "Account Created Successfuly !");
			    	
			    	home h=new home(tname.getText(), tmail.getText(), tusername.getText());
			    	h.setVisible(true);
			    	h.setDefaultCloseOperation(EXIT_ON_CLOSE);
			    	
			     	dispose();
			    	lf.dispose();
			    	
					//login.userarr[i]=new User(tusername.getText(), Integer.toString(i), tname.getText());
			    	//i++;
					
			    	break;
			    }
			    
			    case "mail_exist" :
			    {
			        JOptionPane.showMessageDialog(this, "mail id already used !");
			        tmail.requestFocus();
	
			    	break;
			    }
			    
			    case "username_exist" :
			    {
			    	JOptionPane.showMessageDialog(this, "username already used !");
			        tusername.requestFocus();
	
			    	break;
			    }
			    
			}
			
		}
	}


}

class home extends JFrame implements ActionListener
{
	JButton bblock,bsignout;
	JLabel l1,l2,l3,l11,l22,l33, ltitle;
	
	String name,mail,username;
	
	home(String name, String mail, String username)
	{
		  super("Home");
		  setSize(550,300);
		  setLocation(200,150);
		  
		  this.name=name;
		  this.mail=mail;
		  this.username=username;
		  
		  l1=new JLabel("Name:");
		  l1.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l1.setForeground(Color.white);

		  l2=new JLabel("Mail-id:");
		  l2.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l2.setForeground(Color.white);

		  l3=new JLabel("Username:");
		  l3.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l3.setForeground(Color.white);

		  l11=new JLabel(name);
		  l11.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l11.setForeground(Color.white);

		  l22=new JLabel(mail);
		  l22.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l22.setForeground(Color.white);

		  l33=new JLabel(username);
		  l33.setFont(new Font("lucida consol",Font.PLAIN,18));
		  l33.setForeground(Color.white);
		  
		  ltitle=new JLabel("Profile");
		  ltitle.setFont(new Font("lucida consol",Font.PLAIN,25));
		  ltitle.setForeground(Color.lightGray);
		  
		  bblock=new JButton("Block Details");
		  bblock.setFont(new Font("lucida consol",Font.PLAIN,18));
		  bblock.setBackground(new Color(100,100,150));
		  bblock.setForeground(Color.white);

		  bsignout=new JButton("Sign out");
		  bsignout.setFont(new Font("lucida consol",Font.PLAIN,18));
		  bsignout.setBackground(new Color(100,100,150));
		  bsignout.setForeground(Color.white);

		  LoginPanel p=new LoginPanel(5,5,5,5);
		  p.setLayout(new BorderLayout(5,5));
		  
		  	LoginPanel p1=new LoginPanel(5,5,5,5);
		  	p1.setLayout(new GridLayout(4,1,5,5));
		  	
		  		LoginPanel p11=new LoginPanel(5,5,5,5);
		  		p11.setLayout(new BorderLayout(5,5));
		  		p11.add(ltitle,BorderLayout.CENTER);

		  		LoginPanel p12=new LoginPanel(5,5,5,5);
		  		p12.setLayout(new BorderLayout(42,5));
		  		p12.add(l1,BorderLayout.WEST);
		  		p12.add(l11,BorderLayout.CENTER);

		  		LoginPanel p13=new LoginPanel(5,5,5,5);
		  		p13.setLayout(new BorderLayout(37,5));
		  		p13.add(l2,BorderLayout.WEST);
		  		p13.add(l22,BorderLayout.CENTER);

		  		LoginPanel p14=new LoginPanel(5,5,5,5);
		  		p14.setLayout(new BorderLayout(10,5));
		  		p14.add(l3,BorderLayout.WEST);
		  		p14.add(l33,BorderLayout.CENTER);


		  	p1.add(p11);
		  	p1.add(p12);
		  	p1.add(p13);
		  	p1.add(p14);
		  	
		  	LoginPanel p2=new LoginPanel(5,35,5,5);
		  	p2.setLayout(new BorderLayout(5,5));
		  	p2.add(bsignout,BorderLayout.WEST);
		  	
		  	LoginPanel p3=new LoginPanel(75,5,75,5);
		  	p3.setLayout(new BorderLayout(5,5));
		  	p3.add(bblock,BorderLayout.CENTER);
		  	
		  p.add(p1,BorderLayout.CENTER);
		  p.add(p2,BorderLayout.SOUTH);
		  p.add(p3,BorderLayout.EAST);
		  
		  add(p);
		  
		  bblock.addActionListener(this);
		  bsignout.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==bsignout)
		{
			LoginFrame lf=new LoginFrame();
			lf.setVisible(true);
			lf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			dispose();
		}
		
		if(ae.getSource()==bblock)
		{
			blockframe bf=new blockframe(name, mail, username);
			bf.setVisible(true);
			bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			dispose();
		}
		
	}
	
	
}


class blockframe extends JFrame implements ActionListener
{
	 JTextField tdata;
	 JLabel ldata,ltitle;
	 JButton bblock, bback, bprint, bqr ;
	 JTextArea jta;
	 
	 int usercount;
	 String name,mail,username;
	 
	 blockframe(String name, String mail, String username)
		{
			  super("create block");
			  setSize(550,600);
			  setLocation(200,25);
			  
			  this.name=name;
			  this.mail=mail;
			  this.username=username;
			  
			  jta=new JTextArea();
			  jta.setFont(new Font("lucida console",Font.PLAIN,18));
			  JScrollPane jsp=new JScrollPane(jta);
			  jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			  //add(jsp);
			  
			  ldata=new JLabel("Data :");
			  ldata.setFont(new Font("lucida consol",Font.PLAIN,18));
			  ldata.setForeground(Color.white);
	  
			  ltitle=new JLabel("Block Details ");
			  ltitle.setFont(new Font("lucida consol",Font.PLAIN,25));
			  ltitle.setForeground(Color.lightGray);
			  
			  tdata=new JTextField();  
			  tdata.setFont(new Font("lucida consol",Font.PLAIN,18));
			  
			  bblock=new JButton("create block");
			  bblock.setFont(new Font("lucida consol",Font.PLAIN,18));
			  bblock.setBackground(new Color(100,100,150));
			  bblock.setForeground(Color.white);

			  bback=new JButton("Back");
			  bback.setFont(new Font("lucida consol",Font.PLAIN,18));
			  bback.setBackground(new Color(100,100,150));
			  bback.setForeground(Color.white);

			  bprint=new JButton("Blocks created by you");
			  bprint.setFont(new Font("lucida consol",Font.PLAIN,18));
			  bprint.setBackground(new Color(100,100,150));
			  bprint.setForeground(Color.white);
			  
			  bqr=new JButton("Generate QR Code");
			  bqr.setFont(new Font("lucida consol",Font.PLAIN,18));
			  bqr.setBackground(new Color(100,100,150));
			  bqr.setForeground(Color.white);
			  
			  LoginPanel p=new LoginPanel(5,5,5,5);
			  p.setLayout(new BorderLayout(5,5));
			  
			  	LoginPanel p1=new LoginPanel(5,5,5,5);
			  	p1.setLayout(new GridLayout(4,1,5,5));
			  	p1.add(ltitle);
			  				  	
			  		LoginPanel p22=new LoginPanel(5,5,5,5);
			  		p22.setLayout(new BorderLayout(30,5));
			  		p22.add(ldata,BorderLayout.WEST);
			  		p22.add(tdata,BorderLayout.CENTER);
			  		
			  	p1.add(p22);	
			  	
			  	LoginPanel p3=new LoginPanel(5,5,5,5);
			  	p3.setLayout(new GridLayout(1,3,5,5));
			  	p3.add(bback);
			  	p3.add(bblock);
			  	
			  	p1.add(p3);
			  	
			  	LoginPanel p2=new LoginPanel(5,5,5,5);
			  	p2.setLayout(new BorderLayout(5,5));
			  		LoginPanel p21=new LoginPanel(5,5,5,5);
			  		p21.setLayout(new GridLayout(1,2,5,5));
			  		p21.add(bprint);
			  		p21.add(bqr);
			  	p2.add(p21, BorderLayout.NORTH);
			  	p2.add(jsp, BorderLayout.CENTER);			  	
			  	
			  p.add(p1,BorderLayout.NORTH);
			  p.add(p2,BorderLayout.CENTER);
			  //p.add(p3,BorderLayout.SOUTH);
			  
			  add(p);
			  
			  bblock.addActionListener(this);
			  bback.addActionListener(this);
			  bprint.addActionListener(this);
			  bqr.addActionListener(this);
			  
			  //csubject.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getSource()==bback)
			{
		    	home h=new home(name, mail, username);
		    	h.setVisible(true);
		    	h.setDefaultCloseOperation(EXIT_ON_CLOSE);
		    	
		     	dispose();
			}
			
			if(ae.getSource()==bblock)
			{
				
				if(tdata.getText().length()==0)
				{
					JOptionPane.showMessageDialog(this, "Data field empty ... ");
					tdata.requestFocus();
					return ;
				}
				
				String create_block="Create Block" +":" +username +":" +tdata.getText();
				
				String msg="";
				try
				{
					login.pw.println(create_block);   
				    msg=login.sc.nextLine();
				    
				    if(msg.equals("done"))
				    {
				 		JOptionPane.showMessageDialog(this, "block is created succesfully");
				    }
				}
				catch(NoSuchElementException e)
				{
					JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
					return;
				}
				catch(NullPointerException e)
				{
				    JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
					return;	   
				}
				
				tdata.setText("");
				
				/*int i=0;
				while(login.userarr[i]!=null)
				{
					System.out.println(" if if if if");
					if(login.userarr[i].returnGenBlock(username))
					{
						System.out.println(" if testif if if");
						
						usercount = i;
						System.out.println(i);
						break;
					}
					
					i++;
				}
				
				login.userarr[usercount].createBlock(tdata.getText());
			
				*/
			}
			
			if(ae.getSource()==bprint)
			{
				String msg="";
				try
				{
					login.pw.println("Print"); 
					
					jta.setEditable(true);
					jta.setText("");
					JSONArray jarrclient = new JSONArray(login.sc.nextLine());
					JSONObject jobjclient;
					for(int i=0;i<jarrclient.length();i++)
					{
					    jobjclient = jarrclient.getJSONObject(i);
					    jta.append("" +jobjclient.get("Block_no") +"\t");
					    jta.append("" +jobjclient.get("Data") +"\t");
					    jta.append("" +jobjclient.get("Authenticated") + "\t");
					    jta.append("" +jobjclient.get("HashID") +"\n");
					}
				
				    jta.setEditable(false);
				    
				}
				catch(NoSuchElementException e)
				{
					JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
					return;
				}
				catch(NullPointerException e)
				{
				    JOptionPane.showMessageDialog(this, "Server Is Not Responding ! \ntry again later.."); 
					return;	   
				}
				
				
				//login.userarr[usercount].printUserBlockchain();
			}
			
			if(ae.getSource()==bqr)
			{
				login.pw.println("QRCode");
			
				/*String str=login.sc.nextLine();
				System.out.println("QR Code jhhdfjbjdf");
				
				QRCode qrc=new QRCode(username);
				
				try 
				{
					qrc.generateQRCodeImage(str);
				} 
				catch (WriterException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}*/
				
			}
		}


}

 
 class LoginPanel extends JPanel
 {
 	private int top,left,bottom,right;
 	
 	LoginPanel(int top, int left, int bottom, int right)
 	{
 		this.top=top;
 		this.left=left;
 		this.bottom=bottom;
 		this.right=right;	
 	}

 	public void paintComponent(Graphics g)
 	{
 		super.paintComponent(g);
 		setBackground(new Color(50,50,50));
 	}

 	public Insets getInsets()
 	{
 		return new Insets(top,left,bottom,right);
 	}
 }

 class LoginAdapter extends WindowAdapter
 {
   LoginFrame lf;
   
   LoginAdapter(LoginFrame lf)
   {
     this.lf=lf;
   }

   public void windowClosing(WindowEvent we)
   {
     int opt=JOptionPane.showConfirmDialog(lf, "Are you sure to quit ?", "Confirm", JOptionPane.YES_NO_OPTION);
     
     if(opt==JOptionPane.YES_OPTION)
     {
       System.exit(0); 
     }
        
   } 
 }




public class login extends AbstractVerticle
{

	 static Socket skt;
	 static PrintWriter pw;
	 static Scanner sc;
	public void start() {
		// Do something
		main();
	}
	//`static User userarr[]=new User[30];
	
	public static void main()
	{
		try
		{
		   skt=new Socket("127.0.0.1",1234);
		   pw=new PrintWriter(skt.getOutputStream(),true);
		   sc=new Scanner(skt.getInputStream());
		   System.out.println("connected");
		}
		catch(IOException e)
		{
			System.out.println("Server Alert - " +e.getMessage());
		}
		
		  LoginFrame lf=new LoginFrame();
		  lf.setVisible(true);	  
		  lf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		
	}
}
