import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

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
		System.out.println(111111);
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

	public static void getMDB2() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String location = "mdb/IDOrder.mdb";
		location = "mdb/IDOrder.mdb";
		String url = "jdbc:ucanaccess://" + location;

		int cnt1 = 0;
		int cnt2 = 0;

		try {
			System.out.println(1);
			conn = DriverManager.getConnection(url);
			System.out.println(2);
			stmt = conn.createStatement();
			System.out.println(3);

			for (Customer customer : customers) {
				String id = customer.getId();
				System.out.println(id + " 111111111111111");
				if (id.equals("")) {
					cnt1++;
					System.out.println("continue");
					continue;
				}

				String query = "SELECT * FROM " + id;

				rs = stmt.executeQuery(query);
				System.out.println(query);
				while (rs.next()) {
					String ORDERDAY = rs.getString("ORDERDAY");
					String ORDERTIME = rs.getString("ORDERTIME");
					String ORDERTIME2 = rs.getString("ORDERTIME2");
					String MENUITEM = rs.getString("MENUITEM");
					String PRICE = rs.getString("PRICE");
					String SAWON = rs.getString("SAWON");
					cnt2++;
					System.out.println(
							ORDERDAY + " " + ORDERTIME + " " + ORDERTIME2 + " " + MENUITEM + " " + PRICE + " " + SAWON);
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(cnt1 + " " + cnt2);
	}
}
