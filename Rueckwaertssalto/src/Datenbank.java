import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxPartitionLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

public class Datenbank {
	
	private ArrayList<Tabelle> tables;
	private ArrayList<Relation> relations;
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
	
	public void toEER(String filename) throws IOException{
		HashMap<Tabelle,Object> tablemap=new HashMap<Tabelle,Object>();
		
		final mxGraph graph = new mxGraph();
		graph.setDropEnabled(false);
		Object parent = graph.getDefaultParent();
		for(int i=0;i<this.tables.size();i++){
			Tabelle tabelle=this.tables.get(i);
			graph.getModel().beginUpdate();
	        try {
			tablemap.put(tabelle, graph.insertVertex(parent, null, tabelle.getName(), 100, 100, 100, 50));
			new mxOrganicLayout(graph).execute(parent);
			for(int j=0;j<tabelle.getAttributes().size();j++){
				Attribut attribute=tabelle.getAttributes().get(j);
				graph.insertEdge(parent, null, "", graph.insertVertex(parent, null, attribute.getName(), 50, 50, 100, 50,"shape=ellipse"), tablemap.get(tabelle));
				new mxOrganicLayout(graph).execute(parent);
			}
	        } finally {
	        	graph.getModel().endUpdate();
	        }
		}
		for(int i=0;i<this.tables.size();i++){
			Tabelle tabelle=this.tables.get(i);
			for(int j=0;j<tabelle.getAttributes().size();j++){
				Attribut attribute=tabelle.getAttributes().get(j);
				graph.getModel().beginUpdate();
				try {
					if(attribute.isFK()&&attribute.isPK()){
						
						graph.insertEdge(parent, null, "1/N Weak", tablemap.get(attribute.getFKtable()), tablemap.get(tabelle),"dashed=1");
					}
					else if(attribute.isFK()){
						graph.insertEdge(parent, null, "1/N", tablemap.get(attribute.getFKtable()), tablemap.get(tabelle));
					}
				} finally {
					graph.getModel().endUpdate();
				}
			}
		}
		
        mxIGraphLayout layout = new mxOrganicLayout(graph);
        layout.execute(graph.getDefaultParent());
        //new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
        new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", new File(filename+".png"));
	}

	/**
	 * @return the relations
	 */
	public ArrayList<Relation> getRelations() {
		return relations;
	}

	/**
	 * @param relations the relations to set
	 */
	public void setRelations() {
		
	}

}
