import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLIMeta {
	
	public static void main (String[] args){
		ArrayList<String> listargs = new ArrayList<String>();
		for(int i=0;i<args.length;i++){
			if(Pattern.matches('-'+".", args[i])) listargs.add(args[i]);
		}
		for(int i=0;i<listargs.size();i++){
			for(int j=0;j<listargs.size();j++){
				if(i!=j&&listargs.get(i).equals(listargs.get(j))){
					System.out.println("Jedes Argument darf nur einmal vorkommen!");
					System.exit(0);
				}
			}
		}
		Options elemente=new Options();
		
		Option help = new Option( "help", "print this message" );
		
		Option h   = OptionBuilder.withArgName( "hostname" )
                .hasArgs(1)
                .withDescription(  "Hostname des DBMS. Standard: localhost" )
                .create( "h" );
		
		Option u   = OptionBuilder.withArgName( "username" )
                .hasArgs(1)
                .withDescription(  "Benutzername. Standard: Benutzername des im Betriebssystem angemeldeten Benutzers" )
                .create( "u" );
		
		Option p   = OptionBuilder.withArgName( "passwort" )
                .hasArgs(1)
                .withDescription(  "Passwort. Alternativ kann ein Passwortprompt angezeigt werden. Standard: keins" )
                .create( "p" );
		
		Option d   = OptionBuilder.withArgName( "dbname" )
                .hasArgs(1)
                .withDescription(  "Name der Datenbank" )
                .create( "d" );
		
		Option D   = OptionBuilder.withArgName( "dbtyp" )
                .hasArgs(1)
                .withDescription(  "Typ der Datenbank z.B. MySQL" )
                .create( "D" );
		
		Option T   = OptionBuilder.withArgName( "ausgabetyp" )
                .hasArgs(1)
                .withDescription(  "Ausgabetyp. r für RM. e für ERD" )
                .create( "T" );
		Option f   = OptionBuilder.withArgName( "filename" )
                .hasArgs(1)
                .withDescription(  "filename. Nur den Namen des Files angeben. Keine Endung" )
                .create( "f" );
		
		
		elemente.addOption(h);
		elemente.addOption(u);
		elemente.addOption(p);
		elemente.addOption(d);
		elemente.addOption(D);
		elemente.addOption(T);
		elemente.addOption(f);
		elemente.addOption(help);
		
		// create the parser
		CommandLineParser parser = new BasicParser();
		CommandLine line=null;
		try {
		    // parse the command line arguments
		    line = parser.parse( elemente, args );
		}
		catch( ParseException exp ) {
		    // oops, something went wrong
		    System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		}
		
		String hostname="localhost";
		String username=System.getProperty("user.name");
		String password=null;
		String dbname=null;
		boolean erd=false;
		String dbtype=null;
		String filename=null;
		boolean error=false;
		
		if( line.hasOption( "help" ) ) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "ant", elemente );
			System.exit(0);
		}
		
		if( line.hasOption( "h" ) ) {
		   
		   	hostname=line.getOptionValue( "h" );
		   	if(hostname.equals("")||hostname.equals(null)) System.out.println("Hostname fehlt! Geben sie -h <IP Adresse> an.");
		   
		} else if(!line.hasOption("h")){
			System.out.println("Hostname fehlt! Geben sie -h <IP Adresse> an.");
			error=true;
		}
		if( line.hasOption( "u" ) ) {
		    username=line.getOptionValue( "u" );
		} else if(!line.hasOption("u")){
			System.out.println("Username fehlt! Geben sie -u <Username> an.");
			error=true;
		}
		if( line.hasOption( "p" ) ) {
		    password=line.getOptionValue( "p" );
		} else if(!line.hasOption("p")){
			System.out.println("Passwort fehlt! Geben sie -p <Passwort> an.");
			error=true;
		}
		if( line.hasOption( "d" ) ) {
		    dbname=line.getOptionValue( "d" );
		} else if(!line.hasOption("d")){
			System.out.println("DBName fehlt! Geben sie -d <DBName> an.");
			error=true;
		}
		if( line.hasOption( "D" ) ) {
		    dbtype=line.getOptionValue( "D" ).toLowerCase();
		} else if(!line.hasOption("D")){
			System.out.println("Datenbanktyp fehlt! Geben sie -d <Datenbanktyp> an.");
			error=true;
		}
		if( line.hasOption( "T" ) ) {
		    if(line.getOptionValue( "T" ).equals("r")||line.getOptionValue( "T" ).equals("R")) erd=false;
		    else if (line.getOptionValue( "T" ).equals("e")||line.getOptionValue( "T" ).equals("E")) erd=true;
		    else System.out.println("Ein Falscher Ausgabetyp wurde angegeben. Es kann nur r/R für ein RM und e/E für ein ERD angegeben werden."
		    		+ "\n" + "Für jetzt wurde ein RM ausgewählt.");
		    
		}else if(!line.hasOption("T")){
			System.out.println("Ausgabetyp fehlt! Geben sie -T <Ausgabetyp> an.");
			error=true;
		}
		
		if( line.hasOption( "f" ) ) {
		    filename=line.getOptionValue( "f" );
		} else if(!line.hasOption("f")){
			System.out.println("Filename fehlt! Geben sie -f <Filename> an.");
			error=true;
		}
		
		if(error) System.exit(0);
		
		DbMeta dbcon=null;
		
		if(dbtype=="mysql"){
			dbcon=new MySQLMeta();
		} else {
			dbcon=new MySQLMeta();
		}
		
		System.out.println("Hostname   : "+hostname);
		System.out.println("Username   : "+username);
		//System.out.println("Passwort   : "+password);
		System.out.println("DBName     : "+dbname);
		if(!erd) System.out.println("Ausgabetyp : RM");
		if(erd)  System.out.println("Ausgabetyp : ERD ");
		System.out.println("Filename   : "+filename);
		System.out.println("");
		
		dbcon.setAdresse(hostname);
		dbcon.setUser(username);
		dbcon.setPassword(password);
		dbcon.setDBname(dbname);
		
		dbcon.createConnection();
		Datenbank db = dbcon.read();
		
		if(!erd){
			String inhalt=db.toRM();
			File file = new File(filename+".txt");
			RandomAccessFile raf=null;
			try {
				file.createNewFile();
				raf=new RandomAccessFile(file, "rw");
				raf.writeUTF(inhalt);
				raf.close();
				System.out.println("File wurde gespeichert!");
			} catch (FileNotFoundException e) {
				System.out.println("Das angegebene File wurde nicht gefunden!");
				e.printStackTrace();
			}
			catch (IOException e1) {
				System.out.println("Es gab einen IO Error das File Existiert warscheinlich bereits!");
				e1.printStackTrace();
			}
		}
		
		
		if(erd){
			try {
				db.toEER(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

