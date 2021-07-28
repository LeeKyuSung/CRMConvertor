package pojo;

public class Customer {

	String no;
	String id;
	String tel1;
	String tel2;
	String name;
	String hangcode;
	String sido;
	String sgg;
	String emdg;
	String bunji;
	String addr;
	String addr2;
	String day;
	String newgil;
	String newno;
	String coupon;

	public Customer(String no, String id, String tel1, String tel2, String name, String hangcode, String sido,
			String sgg, String emdg, String bunji, String addr, String addr2, String day, String newgil, String newno,
			String coupon) {
		super();
		this.no = no;
		this.id = id;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.name = name;
		this.hangcode = hangcode;
		this.sido = sido;
		this.sgg = sgg;
		this.emdg = emdg;
		this.bunji = bunji;
		this.addr = addr;
		this.addr2 = addr2;
		this.day = day;
		this.newgil = newgil;
		this.newno = newno;
		this.coupon = coupon;
	}

	public String getId() {
		if (id == null) {
			return "";
		} else {
			return id;
		}
	}
}
