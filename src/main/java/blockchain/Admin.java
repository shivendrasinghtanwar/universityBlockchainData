package blockchain;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.util.Date;

import javax.swing.JTextArea;

public class Admin{
	private String AdminID = "admin123";
	private String AdminData = "adminstart";
	private String AdminUsername = "helloadmin";
	private int adminBlockCount = 0;
	protected AdminBlock genBlockAdm,nextBlockAdm,endBlockAdm;
	protected Block genBlockUser,nextBlockUser;
	// private String AdminPassword;

	public Admin()
	{
		genBlockAdm = new AdminBlock(AdminUsername,AdminData,"",true,AdminID);
		adminBlockCount++;
	}

	private void createBlock(String data,String HashIDUser,Boolean auth)
	{
		if(adminBlockCount==1)
			{
				nextBlockAdm = new AdminBlock(genBlockAdm.getHash(),data,HashIDUser,auth,AdminID);
				genBlockAdm.setLink(nextBlockAdm);
				endBlockAdm = nextBlockAdm;
				adminBlockCount++;
			}
			else
			{
				endBlockAdm = new AdminBlock(nextBlockAdm.getHash(),data,HashIDUser,auth,AdminID);
				nextBlockAdm.setLink(endBlockAdm);
				nextBlockAdm = endBlockAdm;
				adminBlockCount++;
			}
	}
	
	public void authenticateUserChain(User user)
	{
		//if user found
		try
		{
			FileReader fr=new FileReader("test.txt");
			BufferedReader br=new BufferedReader(fr);
			String test=br.readLine();
			String[] test_msg=null;
			
			while(test!=null)
			{
				int i=0;
				test_msg=test.split(":");
				
				if(test_msg[i].equals(user.getUsername()))
				{
					i++;
					nextBlockUser = user.getGen();
					while(nextBlockUser.getLink()!=null)
					{
						System.out.println("\t\t\n this \t\t" + nextBlockUser.getData());
						nextBlockUser = nextBlockUser.getLink();
						System.out.println("\t\t\n this \t\t" + nextBlockUser.getData());
						//If authentication is false of user block
						if(!nextBlockUser.getAuthentication())
						{
							//check from database the data
							while(i != (test_msg.length))
							{
								if(test_msg[i].equals(nextBlockUser.getData()))
								{
									//i++;
									System.out.print(i);
									nextBlockUser.setAuthentication(true);
									this.createBlock(nextBlockUser.getData(),nextBlockUser.getHash(),nextBlockUser.getAuthentication());
									break;
								}
								//data not in database
								else
								{
									i++;
									System.out.println("ERROR : Data not in database");
								}
							}
						}
						//if authentication of user block is true
						else
						{
							nextBlockAdm = genBlockAdm;
							while(nextBlockAdm.getLink()!=null)
							{
								nextBlockAdm=nextBlockAdm.getLink();
								//checking hash
								if(nextBlockUser.getHash().equals(nextBlockAdm.getHashIDUser()))
								{
									//if hash is found checking data
									if(nextBlockUser.getData().equals(nextBlockAdm.getUserData()))
									{
										System.out.println("Already added block!");
										System.out.print(i);
										i++;
									}
									else
									{
										System.out.println("ERROR : Hash Found but data not found!");
									}
								}
								else
								{
									System.out.println("ERROR : Hash not found when boolean true!");
								}
							}
						}
					}
				}
				test=br.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public void printAdminBlockchain(JTextArea jta)
	{
		int i = 1;
		jta.setText("");
		jta.setText("Block No \t Data \t\t Authenticated\n");
        nextBlockAdm = genBlockAdm;
        while (nextBlockAdm.getLink() != null)
        {
        	nextBlockAdm = nextBlockAdm.getLink();
        	jta.append(i+"\t\t"+nextBlockAdm.getUserData()+ "\t\t"+ nextBlockAdm.getAuthentication() +"\t\t" +nextBlockAdm.getHashIDUser() +"\n");
       	   	
        	i++;
        }
	}
	
	public void printAdminBlockchainConsole()
	{
		int i = 1;
		
		System.out.print("Block No \t Data \t\t Authenticated\n");
        nextBlockAdm = genBlockAdm;
        while (nextBlockAdm.getLink() != null)
        {
        	nextBlockAdm = nextBlockAdm.getLink();
        	System.out.print(i+"\t\t"+nextBlockAdm.getUserData()+ "\t\t"+ nextBlockAdm.getAuthentication() +"\n");
       	   	System.out.println(nextBlockAdm.getHashIDUser());
        	i++;
        }
	}
}