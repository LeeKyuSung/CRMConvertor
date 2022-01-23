package kyusung.crm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import kyusung.crm.pojo.Customer;
import kyusung.crm.pojo.Order;

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

		// save customers data
		File file = new File("res/raw/customers");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (Customer cust : customers) {
				writer.append(cust.getString());
			}
		}
	}

	public static void getOrders(HashSet<Customer> customers) throws Exception {
		String location = "mdb/IDOrder.mdb";

		int cnt1 = 0;
		int cnt2 = 0;
		int cnt3 = 0;

		Date maxDate = null;

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
					// 최대 날짜 계산
					Date date = row.getDate("ORDERDAY");
					if (null == maxDate) {
						maxDate = date;
					} else if (date.after(maxDate)) {
						maxDate = date;
					}

					String year = strWrap(date.getYear() + 1900 + "");
					String month = strWrap(date.getMonth() + 1 + "");
					String day = strWrap(date.getDate() + "");

					String menu = strWrap(row.get("MENUITEM").toString());
					String price = strWrap(row.get("PRICE").toString());
					String sawon = strWrap(row.get("SAWON").toString());

					Order order = new Order(year + "/" + month + "/" + day, menu, price, sawon);
					orderList.add(order);
				}

				// file write 시작
				File file = new File("res/raw/user" + id);
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					for (Order order : orderList) {
						writer.append(order.getString());
					}
				}

				cnt3++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("getOrder finished");
		System.out.println("last date : " + (maxDate.getYear() + 1900) + " " + (maxDate.getMonth() + 1) + " "
				+ (maxDate.getDate()));
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
