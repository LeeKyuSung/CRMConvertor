package pojo;

public class Order {

	String date;
	String menu;
	String price;
	String sawon;
	public Order(String date, String menu, String price, String sawon) {
		super();
		this.date = date;
		this.menu = menu;
		this.price = price;
		this.sawon = sawon;
	}
	
	public String getJsonStr() {
		String ret = "{\"date\":\"" + date + "\", ";
		ret += "\"menu\":\"" + menu + "\", ";
		ret += "\"price\":\"" + price + "\", ";
		ret += "\"sawon\":\"" + sawon + "\", }, ";
		return ret;
	}
}
