package DAO;

import connectSQL.Database;
import entity.MonThi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyMonThi {
	 public List<MonThi> getAll() {
	        List<MonThi> list = new ArrayList<>();
	        String query = "SELECT * FROM MonThi";

	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                list.add(new MonThi(
	                        rs.getString("MaMH"),
	                        rs.getString("TenMH"),
	                        rs.getString("LoaiMonThi")
	                ));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    public boolean add(MonThi monHoc) {
	        String query = "INSERT INTO MonThi (MaMH, TenMH, LoaiMonThi) VALUES (?, ?, ?)";

	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setString(1, monHoc.getMaMH());
	            stmt.setString(2, monHoc.getTenMH());
	            stmt.setString(3, monHoc.getLoaiMonThi());
	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public boolean update(MonThi monthi) {
	        String query = "UPDATE MonThi SET TenMH = ?, LoaiMonThi = ? WHERE MaMH = ?";

	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setString(1, monthi.getTenMH());
	            stmt.setString(2, monthi.getLoaiMonThi());
	            stmt.setString(3, monthi.getMaMH());
	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public boolean delete(String MaMH) {
	        String query = "DELETE FROM MonThi WHERE MaMH = ?";

	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setString(1, MaMH);
	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public boolean deleteAll() {
	        String query = "DELETE FROM MonThi";

	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public String getTenMonThi(String maMH) {
	        String query = "SELECT TenMH FROM MonThi WHERE MaMH = ?";
	        try (Connection conn = Database.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.setString(1, maMH);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getString("TenMH");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;  
	    }

	    public String getTenMH(String maMonThi) {
	        String query = "SELECT TenMonThi FROM MonThi WHERE MaMonThi = ?";
	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, maMonThi);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getString("TenMonThi");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public String getLoaiMonThi(String maMonThi) {
	        String query = "SELECT LoaiMonThi FROM MonThi WHERE MaMonThi = ?";
	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, maMonThi);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getString("LoaiMonThi");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	}