package DAO;

import connectSQL.Database;
import entity.ThiSinh;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyThiSinh {
	public List<ThiSinh> getAll() {
	    List<ThiSinh> list = new ArrayList<>();
	    String query = "SELECT * FROM ThiSinh";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            list.add(new ThiSinh(
	                    rs.getString("MaHS"),
	                    rs.getString("HoTen"),
	                    rs.getString("Lop"),
	                    rs.getString("DiaChi"),
	                    rs.getString("SoDienThoai"),
	                    rs.getString("Gmail"),
	                    rs.getString("GioiTinh"), 
	                    rs.getString("Ngay_Sinh")  
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	public boolean add(ThiSinh thiSinh) {
	    String query = "INSERT INTO ThiSinh (MaHS, HoTen, Ngay_Sinh, GioiTinh, Lop, SoDienThoai, Gmail, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, thiSinh.getMaThiSinh());
	        stmt.setString(2, thiSinh.getHoTen());
	        stmt.setString(3, thiSinh.getLop());
	        stmt.setString(4, thiSinh.getDiaChi());
	        stmt.setString(5, thiSinh.getSoDienThoai());
	        stmt.setString(6, thiSinh.getGmail());
	        stmt.setString(7, thiSinh.getGioiTinh());  
	        stmt.setString(8, thiSinh.getNgaySinh());  
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


	public boolean update(ThiSinh thiSinh) {
	    String query = "UPDATE ThiSinh SET HoTen = ?, Lop = ?, SoDienThoai = ?,Ngay_Sinh = ?, GioiTinh = ?,  DiaChi = ?, Gmail = ? WHERE MaHS = ?";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, thiSinh.getHoTen());
	        stmt.setString(2, thiSinh.getLop());
	        stmt.setString(3, thiSinh.getDiaChi());
	        stmt.setString(4, thiSinh.getSoDienThoai());
	        stmt.setString(5, thiSinh.getGmail());
	        stmt.setString(6, thiSinh.getGioiTinh());  
	        stmt.setString(7, thiSinh.getNgaySinh());  
	        stmt.setString(8, thiSinh.getMaThiSinh());
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


	public boolean delete(String MaThiSinh) {
	    String query = "DELETE FROM ThiSinh WHERE MaHS = ?";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, MaThiSinh);
	        return stmt.executeUpdate() > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    public boolean deleteAll() {
        String query = "DELETE FROM ThiSinh";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    public void displayAllStudents() {
        List<ThiSinh> students = getAll();
        if (students.isEmpty()) {
            System.out.println("The student list is empty.");
        } else {
            System.out.println("List of Students:");
            for (ThiSinh ts : students) {
                System.out.println(ts);
            }
        }
    }
        public String getTenThiSinh(String maThiSinh) {
            String query = "SELECT HoTen FROM ThiSinh WHERE MaHS = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, maThiSinh);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("TenThiSinh");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getLop(String maThiSinh) {
            String query = "SELECT Lop FROM ThiSinh WHERE MaHS = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, maThiSinh);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("Lop");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public String getGioiTinh(String maThiSinh) {
            String query = "SELECT GioiTinh FROM ThiSinh WHERE MaHS = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, maThiSinh);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("GioiTinh");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}


