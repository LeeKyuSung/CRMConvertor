import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import pojo.Customer;

public class Test {
	static HashSet<Customer> customers;

	public static void main(String[] args) throws Exception {
		// TODO list
		// mdb 파일들을 모두 Object로 바꿈
		// Object들을 모두 바이트로 바꾸고 암호화해서 저장

		// Android에서 모두 복호화해서 Object로 바꿈
		// 필요한거 빼고 모두 폐기
		// 필요한 내용들만 메모리에 들고 있기

		customers = new HashSet<>();
		getMDB1();
		System.out.println(customers.size());
		System.out.println("end getMDB1");
		getMDB2();
	}

	public static void getMDB1() {
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
				String no = rs.getString("no");
				String id = rs.getString("id");
				String tel1 = rs.getString("tel1");
				String tel2 = rs.getString("tel2");
				String name = rs.getString("name");
				String hangcode = rs.getString("hangcode");
				String sido = rs.getString("sido");
				String sgg = rs.getString("sgg");
				String emdg = rs.getString("emdg");
				String bunji = rs.getString("bunji");
				String addr = rs.getString("addr");
				String addr2 = rs.getString("addr2");
				String day = rs.getString("day");
				String newgil = rs.getString("newgil");
				String newno = rs.getString("newno");
				String coupon = rs.getString("coupon");

				Customer customer = new Customer(no, id, tel1, tel2, name, hangcode, sido, sgg, emdg, bunji, addr,
						addr2, day, newgil, newno, coupon);
				customers.add(customer);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void getMDB2() throws Exception {
		String location = "mdb/IDOrder.mdb";

		int cnt1 = 0;
		int cnt2 = 0;
		int cnt3 = 0;

		try {
			Database db = DatabaseBuilder.open(new File(location));

			for (Customer customer : customers) {
				String id = customer.getId();
				System.out.println("id : [" + id + "] cnt : " + (cnt1+cnt2+cnt3));

				if (id.equals("")) {
					cnt1++;
					System.out.println("continue");
					continue;
				}

				Table table = db.getTable(id);
				if (table==null) {
					cnt2++;
					System.out.println("not exist id at IDOrder");
					continue;
				}
				for (Row row : table) {
					String ORDERDAY = row.get("ORDERDAY").toString();
					String ORDERTIME = row.get("ORDERTIME").toString();
					String ORDERTIME2 = row.get("ORDERTIME2").toString();
					String MENUITEM = row.get("MENUITEM").toString();
					String PRICE = row.get("PRICE").toString();
					String SAWON = row.get("SAWON").toString();
					//System.out.println(ORDERDAY + " " + ORDERTIME + " " + ORDERTIME2 + " " + MENUITEM + " " + PRICE + " " + SAWON);
				}
				cnt3++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(cnt1 + " " + cnt2 + " " + cnt3 + " " + (cnt1+cnt2+cnt3));
	}
}
