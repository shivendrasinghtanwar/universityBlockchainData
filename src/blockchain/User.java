package blockchain;


import java.security.*;

public class User{
	private String UserID,Username,UserPassword;
	private static int UserCount = 0;
	private int UserBlockCount = 0;
	protected Block genBlock,nextBlock,nextBlock2,endBlock;

	public User(String Username,String UserData,String UserID)
	{
		this.Username = Username;
		this.UserID = UserID;
		UserCount++;
		genBlock = new Block(Username,UserData,UserID,null);
		endBlock = genBlock;
		UserBlockCount++;
	}

	public void createBlock(String UserData)
	{
		if(UserBlockCount==1)
		{
			nextBlock = new Block(genBlock.getHash(),UserData,UserID,genBlock);
			genBlock.setLink(nextBlock);
			endBlock = nextBlock;
			UserBlockCount++;
		}
		else
		{
			endBlock = new Block(nextBlock.getHash(),UserData,UserID,null);
			nextBlock.setLink(endBlock);
			nextBlock = endBlock;
			UserBlockCount++;
		}
	}

	public void printUserBlockchain()
	{
		int i = 1;
		System.out.println(Username+"'s Blockchain = ");
		if (UserBlockCount==1) 
		{
			System.out.println("Block No = " + genBlock.noOfBlocks());
			System.out.println("Data = " + genBlock.getData());
		}
		else
		{
			nextBlock = genBlock;
        	System.out.println("\n\nBlock No = "+i);
        	i++;
        	System.out.println("Data = "+genBlock.getData());
        	System.out.println("HashID = "+genBlock.getHash());
        	System.out.println("Prev HashID = "+genBlock.getPrevHash());
        	System.out.print("\n");
        	nextBlock = genBlock.getLink();
        	// if (nextBlock.getPrevHash() == genBlock.getHash())
        	// {
        	// 	System.out.println("\n\n\t\t Yeah !!!\n\n");
        	// }
        	while (nextBlock.getLink() != null)
        	{
         	   	System.out.println("Block No = "+i);
        		i++;
        		System.out.println("Data = "+nextBlock.getData());
        		System.out.println("HashID = "+nextBlock.getHash());
        		System.out.println("Prev HashID = "+nextBlock.getPrevHash());
        		System.out.print("\n");
        		// nextBlock2 = nextBlock;
          	   	nextBlock = nextBlock.getLink();
          		//if (nextBlock.getPrevHash() == nextBlock2.getHash())
        		// {
        		// 	System.out.println("\n\n\t\t Yeah !!!\n\n");
        		// }
        	}
        	System.out.println("Block No = "+i);
        	i++;
        	System.out.println("Data = "+nextBlock.getData());
        	System.out.println("HashID = "+nextBlock.getHash());
        	System.out.print("\n");
		}
	}

	public void printAuthenticatedBlocks()
	{
		int i = 1;
		System.out.println(Username+"'s Blockchain = ");
		if (UserBlockCount==1) 
		{
			System.out.println("Block No = " + genBlock.noOfBlocks());
			System.out.println("Data = " + genBlock.getData());
		}
		else
		{
			nextBlock = genBlock;
        	
        	if(genBlock.getAuthentication())
        	{
        		System.out.println("\nBlock No = "+i);
        			System.out.println(" Data = "+nextBlock.getData()+
        				"\n HashID = "+nextBlock.getHash()+
        				"\n Prev HashID = "+nextBlock.getPrevHash()+
        				"\n Authenticated = "+nextBlock.getAuthentication()+"\n");
        	}
			i++;
        	nextBlock = genBlock.getLink();
        	while (nextBlock.getLink() != null)
        	{
        		if(nextBlock.getAuthentication())
        		{
        			System.out.println("Block No = "+i);
        			System.out.println(" Data = "+nextBlock.getData()+
        				"\n HashID = "+nextBlock.getHash()+
        				"\n Prev HashID = "+nextBlock.getPrevHash()+
        				"\n Authenticated = "+nextBlock.getAuthentication()+"\n");
        		}
        		i++;
          	   	nextBlock = nextBlock.getLink();
        	}
        	if(nextBlock.getAuthentication())
        		{
        			System.out.println("Block No = "+i);
        			System.out.println(" Data = "+nextBlock.getData()+
        				"\n HashID = "+nextBlock.getHash()+
        				"\n Prev HashID = "+nextBlock.getPrevHash()+
        				"\n Authenticated = "+nextBlock.getAuthentication()+"\n");
        		}
        		i++;
		}
	}

	public void setBlockAuthentication(User one,int blockNo,boolean Authenticated)
	{
		Block nptr = new Block(null,null,null,null);
		int i;
		if(blockNo==1)
		{
			nptr = one.genBlock;
		}
		else
		{
			blockNo--;
			nptr = one.genBlock.getLink();
			for (i=1;i<blockNo;i++) 
			{
				nptr = nptr.getLink();
			}
		}
		nptr.setAuthentication(Authenticated);
	}

	public void printBlocksToAuthenticate()
	{
		
	}
	
	public boolean returnGenBlock(String Username)
	{
		System.out.println("dsjjbhhjds");
		if(this.Username.equals(Username))
		{
			System.out.println("true given");
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getUserBlockNo()
	{
		return UserBlockCount;
	}

	public void deleteUser()
	{
		UserID = "";
		// UserData = "";
		UserCount--;
	}
}