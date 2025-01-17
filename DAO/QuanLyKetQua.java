package DAO;

import connectSQL.Database;
import entity.KetQua;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyKetQua {
    public List<KetQua> getAll() {
        List<KetQua> list = new ArrayList<>();
        String query = "SELECT * FROM KetQua";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new KetQua(
                	rs.getString("MaKetQua"),
                    rs.getString("MaHS"),
                    rs.getString("MaMH"),
                    rs.getFloat("Diem"),
                    rs.getInt("LanThi")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(KetQua kq) {
        String sql = "INSERT INTO KetQua (MaKetQua, MaHS, MaMH, Diem, LanThi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kq.getMaKetQua());
            stmt.setString(2, kq.getMaThiSinh());
            stmt.setString(3, kq.getMaMonThi());
            stmt.setFloat(4, kq.getDiem());
            stmt.setInt(5, kq.getLanThi());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean delete(String maHS, String maMH) {        
        String sql = "DELETE FROM KetQua WHERE MaHS = ? AND MaMH = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, maHS);
            stmt.setString(2, maMH);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean update(KetQua kq) {
        String sql = "UPDATE KetQua SET Diem = ?, LanThi = ? WHERE MaKetQua = ? AND MaHS = ? AND MaMH = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, kq.getDiem());
            stmt.setInt(2, kq.getLanThi());
            stmt.setString(3, kq.getMaKetQua());
            stmt.setString(4, kq.getMaThiSinh());
            stmt.setString(5, kq.getMaMonThi());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteAll() {
        String query = "DELETE FROM KetQua";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeUpdate(query) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean isMaHSMaMHExist(String maHS, String maMH) {
    	String sql = "SELECT COUNT(*) FROM KetQua WHERE MaHS = ? AND MaMH = ?";
    	try (Connection conn = Database.getConnection();
    	     PreparedStatement stmt = conn.prepareStatement(sql)) {
    	    stmt.setString(1, maHS);
    	    stmt.setString(2, maMH);
    	    ResultSet rs = stmt.executeQuery();
    	    if (rs.next()) {
    	        return rs.getInt(1) > 0;  
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	return false;

    }
}
 
