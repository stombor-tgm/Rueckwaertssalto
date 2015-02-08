
public interface DbMeta {
	
	public void setAdresse(String adr);
	public void setUser(String usr);
	public void setPassword(String pwd);
	public void setDBname(String name);
	public void setTablename(String tablename);
	
	public boolean createConnection();
	public Datenbank read();

}
