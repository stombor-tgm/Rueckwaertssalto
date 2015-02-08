import java.util.ArrayList;


public class Tabelle {
	
	private ArrayList<Attribut> attributes;
	private String name;
	
	public Tabelle(String name){
		this.name=name;
		this.attributes=new ArrayList<Attribut>();
	}
	
	/**
	 * @return the attributes
	 */
	public ArrayList<Attribut> getAttributes() {
		return attributes;
	}
	
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(ArrayList<Attribut> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(Attribut attribute){
		this.attributes.add(attribute);
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
	
	public Attribut getattributebyname(String name){
		for(int i=0;i<this.attributes.size();i++){
			if(attributes.get(i).getName().equals(name)){
				return attributes.get(i);
			}
		}
		return null;
	}
	public void setattributebyname(String name,Attribut attribute){
		for(int i=0;i<this.attributes.size();i++){
			if(attributes.get(i).getName().equals(name)){
				attributes.set(i, attribute);
			}
		}
	}
	

}
