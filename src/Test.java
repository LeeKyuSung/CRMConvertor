import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) throws Exception {
		// TODO list
		// mdb 파일들을 모두 Object로 바꿈
		// Object들을 모두 바이트로 바꾸고 암호화해서 저장

		// Android에서 모두 복호화해서 Object로 바꿈
		// 필요한거 빼고 모두 폐기
		// 필요한 내용들만 메모리에 들고 있기

		getMDB();
	}

	public static void getMDB() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String location = "mdb/iriwaamain.mdb";
		String url = "jdbc:ucanaccess://" + location;
		String query = "SELECT * FROM customer";
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String first = rs.getString(1);
				System.out.println(first);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
