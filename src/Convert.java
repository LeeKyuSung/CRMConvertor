import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import pojo.Customer;
import pojo.Order;

public class Convert {
	public static void main(String[] args) throws Exception {
		HashSet<Customer> customers = new HashSet<>();

		getCustomers(customers);
		System.out.println(customers.size());
		getOrders(customers);
	}

	public static void getCustomers(HashSet<Customer> customers) throws Exception {
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
				String id = strWrap(rs.getString("id"));
				String tel1 = strWrap(rs.getString("tel1")).replace("-", "");
				String tel2 = strWrap(rs.getString("tel2")).replace("-", "");
				String name = strWrap(rs.getString("name")); // 이천쌀유통, 강화농산
				String sido = strWrap(rs.getString("sido"));
				String sgg = strWrap(rs.getString("sgg"));
				String emdg = strWrap(rs.getString("emdg"));
				String bunji = strWrap(rs.getString("bunji"));
				String addr = strWrap(rs.getString("addr"));
				addr = strWrap(strWrap(sido + " " + sgg + " " + emdg + " " + bunji) + " " + addr);
				String addr2 = strWrap(rs.getString("addr2"));

				Customer customer = new Customer(id, tel1, tel2, name, addr, addr2);
				customers.add(customer);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// for test add kyusung
		Customer customer = new Customer("55555", "01091019320", "", "이천쌀유통", "인천광역시 부평구 부평2동 760-836 동수북로90번길 17",
				"주소2");
		customers.add(customer);

		// save customers data
		File file = new File("results/customers");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.append("{");
			for (Customer cust : customers) {
				writer.append(cust.getJsonStr());
			}
			writer.append("}");
		}
	}

	public static void getOrders(HashSet<Customer> customers) throws Exception {
		String location = "mdb/IDOrder.mdb";

		int cnt1 = 0;
		int cnt2 = 0;
		int cnt3 = 0;

		try {
			Database db = DatabaseBuilder.open(new File(location));

			for (Customer customer : customers) {
				String id = customer.getId();

				if (id.equals("")) {
					cnt1++;
					System.out.println("id : [" + id + "] cnt : " + (cnt1 + cnt2 + cnt3));
					System.out.println("id is blank. continue...");
					continue;
				}

				ArrayList<Order> orderList = new ArrayList<>();
				
				Table table = db.getTable(id);
				if (table == null) {
					cnt2++;
					System.out.println("id : [" + id + "] cnt : " + (cnt1 + cnt2 + cnt3));
					System.out.println("not exist id at order list");
					continue;
				}
				for (Row row : table) {
					String year = strWrap(row.getDate("ORDERDAY").getYear()+1900 +"");
					String month = strWrap(row.getDate("ORDERDAY").getMonth()+1 + "");
					String day = strWrap(row.getDate("ORDERDAY").getDate() + "");
					
					String menu = strWrap(row.get("MENUITEM").toString());
					String price = strWrap(row.get("PRICE").toString());
					String sawon = strWrap(row.get("SAWON").toString());
					
					Order order = new Order(year+"/"+month+"/"+day, menu, price, sawon);
					orderList.add(order);
				}
				
				// file write 시작
				File file = new File("results/" + id);
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					writer.append("[");
					for (Order order : orderList) {
						writer.append(order.getJsonStr());
					}
					writer.append("]");
				}
				
				cnt3++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("getOrder finished");
		System.out.println("blank id cnt : " + cnt1);
		System.out.println("no orders at IDOrder.mdb cnt : " + cnt2);
		System.out.println("success : " + cnt3);
		System.out.println("total : " + (cnt1 + cnt2 + cnt3));
	}

	public static String strWrap(String input) {
		if (input == null) {
			return "";
		} else {
			return input.trim();
		}
	}
}
