import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import com.healthmarketscience.jackcess.Database;

public class Test {
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		// lib 폴더에 jdbc jar를 classpath에 추가해줘야함.

		String mdb = "mdb/callOrder.mdb";
		String sqlite = "tmp.sqlite";
		
		final AccessExporter exporter = new AccessExporter(Database.open(new File(mdb), true));
		final Connection jdbc = DriverManager.getConnection("jdbc:sqlite:" + sqlite);
		exporter.export(jdbc);
	}
}
