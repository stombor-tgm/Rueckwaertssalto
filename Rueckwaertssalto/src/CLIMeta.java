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
		
		Option T   = OptionBuilder.withArgName( "tabellenname" )
                .hasArgs(1)
                .isRequired()
                .withDescription(  "Tabellenname" )
                .create( "T" );
		
		elemente.addOption(h);
		elemente.addOption(u);
		elemente.addOption(p);
		elemente.addOption(d);
		elemente.addOption(D);
		elemente.addOption(T);
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
		String tablename=null;
		String dbtype=null;
		
		if( line.hasOption( "h" ) ) {
		    hostname=line.getOptionValue( "h" );
		}
		if( line.hasOption( "u" ) ) {
		    username=line.getOptionValue( "u" );
		}
		if( line.hasOption( "p" ) ) {
		    password=line.getOptionValue( "p" );
		}
		if( line.hasOption( "d" ) ) {
		    dbname=line.getOptionValue( "d" );
		}
		if( line.hasOption( "D" ) ) {
		    dbtype=line.getOptionValue( "D" ).toLowerCase();
		}
		if( line.hasOption( "T" ) ) {
		    tablename=line.getOptionValue( "T" );
		}
		if( line.hasOption( "help" ) ) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "ant", elemente );
		}
		DbMeta dbcon=new MySQLMeta();
		
		if(dbtype=="mysql"){
			dbcon=new MySQLMeta();
		}
		
		System.out.println(hostname);
		System.out.println(username);
		System.out.println(password);
		System.out.println(dbname);
		System.out.println(tablename);
		
		dbcon.setAdresse(hostname);
		dbcon.setUser(username);
		dbcon.setPassword(password);
		dbcon.setDBname(dbname);
		dbcon.setTablename(tablename);
		
		dbcon.createConnection();
		System.out.println(dbcon.read());
		
	}

}

