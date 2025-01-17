package DAO;

import connectSQL.Database;
import entity.LichThi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyLichThi {

    public List<LichThi> getAll() {
        List<LichThi> list = new ArrayList<>();
        String query = "SELECT * FROM LichThi";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new LichThi(
                        rs.getString("MaLichThi"),
                        rs.getString("MaMonThi"),
                        rs.getString("PhongThi"),
                        rs.getDate("NgayThi") 
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

  
    public boolean add(LichThi lichThi) {
        String query = "INSERT INTO LichThi (MaLichThi, MaMH, PhongThi, NgayGioThi) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lichThi.getMaLichThi());
            stmt.setString(2, lichThi.getMaMonThi());
            stmt.setString(3, lichThi.getPhongthi());
            stmt.setDate(4, new java.sql.Date(lichThi.getNgayThi().getTime())); 
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(LichThi lichThi) {
        String query = "UPDATE LichThi SET MaMonThi = ?, PhongThi = ?, NgayThi = ? WHERE MaLichThi = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, lichThi.getMaMonThi());
            stmt.setString(2, lichThi.getPhongthi());
            stmt.setDate(3, new java.sql.Date(lichThi.getNgayThi().getTime())); 
            stmt.setString(4, lichThi.getMaLichThi());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maLichThi) {
        String query = "DELETE FROM LichThi WHERE MaLichThi = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maLichThi);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll() {
        String query = "DELETE FROM LichThi";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void displayAllLichThi() {
        List<LichThi> lichThis = getAll();
        if (lichThis.isEmpty()) {
            System.out.println("The exam schedule list is empty.");
        } else {
            System.out.println("List of Exam Schedules:");
            for (LichThi lt : lichThis) {
                System.out.println(lt);
            }
        }
    }
}

