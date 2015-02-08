import java.util.ArrayList;


public class Datenbank {
	
	private ArrayList<Tabelle> tables;
	private String name;
	
	public Datenbank(String name){
		this.name=name;
		this.tables=new ArrayList<Tabelle>();
	}
	
	/**
	 * @return the tables
	 */
	public ArrayList<Tabelle> getTables() {
		return tables;
	}

	/**
	 * @param tables the tables to set
	 */
	public void setTables(ArrayList<Tabelle> tables) {
		this.tables = tables;
	}
	
	/**
	 * @param table the table to add
	 */
	public void addTable(Tabelle table){
		this.tables.add(table);
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Tabelle gettablebyname(String name){
		for(int i=0;i<this.tables.size();i++){
			if(tables.get(i).getName().equals(name)){
				return tables.get(i);
			}
		}
		return null;
	}
	
	public void settablebyname(String name,Tabelle table){
		for(int i=0;i<this.tables.size();i++){
			if(tables.get(i).getName().equals(name)){
				tables.set(i, table);
			}
		}
	}
	
	public String toRM(){
		String ausgabe=this.name+"\n\n";
		for(int i=0;i<this.tables.size();i++){
			Tabelle tabelle=this.tables.get(i);
			ausgabe+=tabelle.getName()+" (";
			boolean first=true;
			for (int j=0;j<tabelle.getAttributes().size();j++){
				Attribut attribut=tables.get(i).getAttributes().get(j);
				
				if(first) first=false;
				else ausgabe+=","; 
				
				if (attribut.isFK()&&attribut.isPK()) ausgabe+=attribut.getFKtable().getName()+"."+attribut.getFKattribute().getName()+":"+attribut.getName()+"<PK>";
				else if(attribut.isFK()) ausgabe+=attribut.getFKtable().getName()+"."+attribut.getFKattribute().getName()+":"+attribut.getName();
				else if(attribut.isPK()) ausgabe+=attribut.getName()+"<PK>";	
				else ausgabe+=attribut.getName();
			}
			ausgabe+=")\n";
		}
		return ausgabe;
	}
	
	public void toEER(){
		
	}

}
