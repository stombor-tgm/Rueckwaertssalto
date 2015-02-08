
public class Attribut {
	
	private String name;
	private String typ;
	private boolean isFK;
	private Tabelle FKtable;
	private Attribut FKattribute;
	private boolean isPK;
	
	public Attribut(String name, String typ){
		this.name=name;
		this.typ=typ;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the typ
	 */
	public String getTyp() {
		return typ;
	}
	/**
	 * @param typ the typ to set
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}
	/**
	 * @return the isFK
	 */
	public boolean isFK() {
		return isFK;
	}
	/**
	 * @param isFK the isFK to set
	 */
	public void setFK(boolean isFK) {
		this.isFK = isFK;
	}
	/**
	 * @return the fKtable
	 */
	public Tabelle getFKtable() {
		return FKtable;
	}
	/**
	 * @param fKtable the fKtable to set
	 */
	public void setFKtable(Tabelle fKtable) {
		FKtable = fKtable;
	}
	/**
	 * @return the fKattribute
	 */
	public Attribut getFKattribute() {
		return FKattribute;
	}
	/**
	 * @param fKattribute the fKattribute to set
	 */
	public void setFKattribute(Attribut fKattribute) {
		FKattribute = fKattribute;
	}
	/**
	 * @return the isPK
	 */
	public boolean isPK() {
		return isPK;
	}
	/**
	 * @param isPK the isPK to set
	 */
	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}

}
