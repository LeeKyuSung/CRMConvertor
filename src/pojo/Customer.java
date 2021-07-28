package pojo;

public class Customer {

	String id;
	String tel1;
	String tel2;
	String name;
	String addr;
	String addr2;

	public Customer(String id, String tel1, String tel2, String name, String addr, String addr2) {
		super();
		this.id = id;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.name = name;
		this.addr = addr;
		this.addr2 = addr2;
	}

	public String getId() {
		return id;
	}

	public String getJsonStr() {
		String ret = "\"" + id + "\":{";
		ret += "\"tel1\":\"" + tel1 + "\", ";
		ret += "\"tel2\":\"" + tel2 + "\", ";
		ret += "\"name\":\"" + name + "\", ";
		ret += "\"addr\":\"" + addr + "\", ";
		ret += "\"addr2\":\"" + addr2 + "\", ";
		ret += "},";
		return ret;
	}
}
