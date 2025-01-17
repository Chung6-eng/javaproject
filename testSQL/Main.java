package testSQL;

import java.sql.Connection;
import connectSQL.Database;

public class Main {

	public static void main(String[] args) {
		Connection con = Database.getConnection();
		if(con == null) {
			System.out.println("that bai");
		}else {
			System.out.println("thanh cong");
		}
	}

}
