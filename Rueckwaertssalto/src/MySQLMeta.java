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
		public String read() {
			String ausgabe="";
		    try {
		    	System.out.println("Creating statement...");
				this.stmt = this.conn.createStatement();
			    DatabaseMetaData dbmd = this.conn.getMetaData();
			    ResultSet rsmdtable = dbmd.getTables(null, null, null, null);

			    ausgabe=ausgabe+"\n"+"\n";
			    while(rsmdtable.next()){
			    	ausgabe=ausgabe+rsmdtable.getString(3)+"\n";
			    	String sql="SELECT * FROM "+rsmdtable.getString(3);
				    this.rs = this.stmt.executeQuery(sql);
				    ResultSetMetaData rsmd = this.rs.getMetaData();
			    	ResultSet rsmdfk = dbmd.getImportedKeys(null, null, rsmdtable.getString(3));
			    	ResultSet rsmdpk = dbmd.getPrimaryKeys(null,null,rsmdtable.getString(3));
			    	int felderanz = rsmd.getColumnCount();
				    for(int i=1;i<=felderanz;i++){
				    	ausgabe=ausgabe+rsmd.getColumnName(i)+" <"+rsmd.getColumnTypeName(i)+"> | ";
				    }
				    ausgabe=ausgabe+"\n";
			    	while(rsmdfk.next()){
				    	ausgabe=ausgabe+rsmdfk.getString("FKCOLUMN_NAME")+":"+rsmdfk.getString("PKTABLE_NAME")+"."+rsmdfk.getString("PKCOLUMN_NAME")+" %% ";
				    }
			    	ausgabe=ausgabe+"\n";
			    	while(rsmdpk.next()){
				    	ausgabe=ausgabe+rsmdpk.getString("COLUMN_NAME")+" ;; ";
				    }
			    	ausgabe=ausgabe+"\n"+"\n";
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
			return ausgabe;
		}

	}

