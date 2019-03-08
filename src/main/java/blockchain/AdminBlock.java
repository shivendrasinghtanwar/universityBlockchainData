package blockchain;


import java.security.*;
import java.util.Date;

public class AdminBlock
{
	private boolean Authenticated;
    private String AdminID,PrevHashIDAdm,HashIDAdm;
    private String UserData,HashIDUser;
	private static int count=0;
	protected AdminBlock link;

	public AdminBlock(String prevHashIDAdm,String UserData,String HashIDUser,Boolean auth,String AdminID)
	{
		this.PrevHashIDAdm = PrevHashIDAdm;
		this.UserData = UserData;
        this.AdminID = AdminID;
        this.HashIDUser = HashIDUser;
        this.Authenticated = auth;
		HashIDAdm = AdminBlock.calculateHash(this);
		link=null;
		count++;
	}

	public boolean getAuthentication()
	{
		return Authenticated;
	}

	public String getHashIDUser()
	{
		return HashIDUser;
    }
    
    public String getHash()
	{
		return HashIDAdm;
	}

	public String getUserData()
	{
		return UserData;
	}

	public String getPrevHash()
	{
		return PrevHashIDAdm;
	}

	public static String calculateHash(AdminBlock blockAdm)
	{
		String all = StringUtil.applySHA(blockAdm.PrevHashIDAdm+blockAdm.UserData+blockAdm.HashIDUser);
		return all;
	}

	public static int noOfBlocks()
	{
		return count;
	}

	public void setLink(AdminBlock link)
	{
		this.link = link;
	}

	public AdminBlock getLink()
	{
		return link;
	}
}
