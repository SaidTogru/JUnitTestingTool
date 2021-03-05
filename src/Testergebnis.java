import java.sql.Timestamp;

public class Testergebnis {
Timestamp testbeginn;
Timestamp testende;
int anzahlTests;
int failed;
int succeed;
public Testergebnis(Timestamp testbeginn,Timestamp testende,int anzahlTests,int failed,int succeed) {
	this.testbeginn=testbeginn;
	this.testende= testende;
	this.anzahlTests=anzahlTests;
	this.failed=failed;
	this.succeed=succeed;
}
public String toString() {
	return this.testbeginn+", "+this.testende+", "+this.anzahlTests+", "+this.failed+", "+this.failed;
}
}
