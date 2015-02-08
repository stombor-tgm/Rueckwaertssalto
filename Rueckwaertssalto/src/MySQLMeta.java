import java.sql.*;

	public class MySQLMeta implements DbMeta {
		
		// Der JDBC Driver wird gesetzt hier 
		   private String jdbcdriver;  
		   private String dburl;

		   //  Database credentials
		   private String user;
		   private String pass;
		   private String dbname;
		   private String tablename;

		   private Connection conn;
		   private Statement stmt;
		   private ResultSet rs;
		   
		   
		   public MySQLMeta(){
			   this.jdbcdriver="com.mysql.jdbc.Driver";
			   this.dburl = "jdbc:mysql://localhost/";
			   try {
				Class.forName(this.jdbcdriver);
			   } catch (ClassNotFoundException e) {
				e.printStackTrace();
			   }
		   }


			@Override
			public void setAdresse(String adr) {
				this.dburl="jdbc:mysql://"+adr;
			}
		
			@Override
			public void setUser(String usr) {
				this.user=usr;
			}
		
			@Override
			public void setPassword(String pwd) {
				this.pass=pwd;
			}
		
			@Override
			public void setDBname(String name) {
				this.dbname=name;
				
			}
			
			@Override
			public void setTablename(String tablename) {
				this.tablename=tablename;
			}

		@Override
		public boolean createConnection() {
		     try {
		    	System.out.println("Connecting to database: "+this.dburl+"/"+this.dbname+"...");
				conn = DriverManager.getConnection(this.dburl+"/"+this.dbname,this.user,this.pass);
			    
		     } catch (SQLException e) {
				e.printStackTrace();
				return false;
		     }
		    return true;
		}

		@Override
		public Datenbank read() {
			Datenbank db = null;
		    try {
		    	System.out.println("Creating statement...");
				this.stmt = this.conn.createStatement();
			    DatabaseMetaData dbmd = this.conn.getMetaData();
			    ResultSet rsmdtable = dbmd.getTables(null, null, null, null);
			    db = new Datenbank(this.dbname);
			    while(rsmdtable.next()){
			    	
			    	Tabelle table = new Tabelle(rsmdtable.getString(3));
			    	
			    	String sql="SELECT * FROM "+rsmdtable.getString(3);
				    this.rs = this.stmt.executeQuery(sql);
				    ResultSetMetaData rsmd = this.rs.getMetaData();
			    	int felderanz = rsmd.getColumnCount();
			    	
				    for(int i=1;i<=felderanz;i++){
				    	Attribut attribute = new Attribut(rsmd.getColumnName(i),rsmd.getColumnTypeName(i));
				    	table.addAttribute(attribute);
				    }
				    db.addTable(table);
			    }
			    ResultSet rsmdtablezwei = dbmd.getTables(null, null, null, null);
			    while(rsmdtablezwei.next()){
			    	
			    	Tabelle table = db.gettablebyname(rsmdtablezwei.getString(3));
			    	
			    	String sql="SELECT * FROM "+rsmdtablezwei.getString(3);
				    this.rs = this.stmt.executeQuery(sql);
				    ResultSetMetaData rsmd = this.rs.getMetaData();
			    	ResultSet rsmdfk = dbmd.getImportedKeys(null, null, rsmdtablezwei.getString(3));
			    	ResultSet rsmdpk = dbmd.getPrimaryKeys(null,null,rsmdtablezwei.getString(3));
			    	
			    	while(rsmdfk.next()){
				    	Attribut attribute = table.getattributebyname(rsmdfk.getString("FKCOLUMN_NAME"));
				    	attribute.setFK(true);
				    	attribute.setFKtable(db.gettablebyname(rsmdfk.getString("PKTABLE_NAME")));
				    	attribute.setFKattribute(db.gettablebyname(rsmdfk.getString("PKTABLE_NAME")).getattributebyname(rsmdfk.getString("PKCOLUMN_NAME")));
				    	table.setattributebyname(attribute.getName(), attribute);
				    }
			    	while(rsmdpk.next()){
				    	Attribut attribute = table.getattributebyname(rsmdpk.getString("COLUMN_NAME"));
				    	attribute.setPK(true);
				    	table.setattributebyname(attribute.getName(), attribute);
				    }
			    	db.settablebyname(table.getName(), table);
			    }
			    this.rs.close();
			    this.stmt.close();
			    this.conn.close();
			    
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
			//finally block used to close resources
				try{
					if(this.stmt!=null)
			        this.stmt.close();
			    }catch(SQLException se2){
			    }// nothing we can do
			    try{
			    	if(this.conn!=null)
			        this.conn.close();
			    }catch(SQLException se){
			    	se.printStackTrace();
			    }//end finally try
			}
			return db;
		}

	}

