import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import turban.utils.ErrorHandler;
import turban.utils.ErrorLevel;

public class Datenbankeintrag {
	Connection con= null;

	

	public void verbindeMitDB() throws SQLException {

		try {
		 con=	DriverManager.getConnection("jdbc:mysql://localhost:3306/programmieraufgabe?useSSL=false", "root", "hallo123");
		}catch(Exception e){
		ErrorHandler.logError(ErrorLevel.ERROR_HIGH, true, Datenbankeintrag.class, "Verbindung fehlgeschlagen");
		con.close();
		}
}
	public void speichereInDB(Testergebnis x) throws SQLException {
		Statement sta=con.createStatement();
		
		
int count= sta.executeUpdate("INSERT INTO testergebnisse (Testbeginn, Testende,Tests, Failed,Succeed)"+ "VALUES ('"+x.testbeginn+"','"+x.testende+"',"+x.anzahlTests+","+x.failed+","+x.succeed+");");
ErrorHandler.Assert(count==1, true,  Datenbankeintrag.class, "Fehlgeschlagen");
			sta.close();

	}
	 
}