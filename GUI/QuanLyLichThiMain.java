package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import connectSQL.Database;

public class QuanLyLichThiMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField malichthiField;
    private JTextField mamonField;
    private JTextField phongThiField;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    private JButton addButton, updateButton, deleteButton, sortButton, resetButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteAllButton;
    
    public QuanLyLichThiMain() {
        setTitle("Quản Lý Lịch Thi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createLichThiPanel(), BorderLayout.CENTER);
    }

    public JPanel createLichThiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lịch thi"));

        JPanel leftPanel = new JPanel(new GridLayout(15, 2, 10, 10));
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));

        JLabel malichthiLabel = new JLabel("Mã lịch thi:");
        malichthiField = new JTextField(10);
        leftPanel.add(malichthiLabel);
        leftPanel.add(malichthiField);

        JLabel mamonLabel = new JLabel("Mã môn thi:");
        mamonField = new JTextField(10);
        leftPanel.add(mamonLabel);
        leftPanel.add(mamonField);

        JLabel ngaythiLabel = new JLabel("Ngày thi:");
        JPanel ngaythiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        dayComboBox = new JComboBox<>(days);
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        monthComboBox = new JComboBox<>(months);
        String[] years = new String[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        yearComboBox = new JComboBox<>(years);
        ngaythiPanel.add(dayComboBox);
        ngaythiPanel.add(monthComboBox);
        ngaythiPanel.add(yearComboBox);
        leftPanel.add(ngaythiLabel);
        leftPanel.add(ngaythiPanel);

        JLabel phongthiLabel = new JLabel("Phòng thi:");
        phongThiField = new JTextField(10);
        leftPanel.add(phongthiLabel);
        leftPanel.add(phongThiField);

        panel.add(leftPanel, BorderLayout.WEST);

        tableModel = new DefaultTableModel(
                new Object[] { "Mã lịch thi", "Mã môn thi", "Ngày thi", "Phòng thi" }, 0);
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        loadTableData();
        return addActionButtonsToPanel(panel);
    }

    public JPanel addActionButtonsToPanel(JPanel panel) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Thêm");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        updateButton = new JButton("Sửa");
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);

        deleteButton = new JButton("Xóa");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

       
        deleteAllButton = new JButton("Xóa tất cả");
        deleteAllButton.addActionListener(this);
        buttonPanel.add(deleteAllButton);

        sortButton = new JButton("Sắp Xếp");
        sortButton.addActionListener(this);
        buttonPanel.add(sortButton);

        resetButton = new JButton("Refresh");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);

        String query = "SELECT * FROM LichThi";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[] {
                    rs.getString("MaLichThi"),
                    rs.getString("MaMH"),
                    rs.getString("PhongThi"),
                    rs.getDate("NgayGioThi")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNgayThi() {
        String day = String.format("%02d", Integer.parseInt((String) dayComboBox.getSelectedItem())); 
        String month = String.format("%02d", monthComboBox.getSelectedIndex() + 1); 
        String year = (String) yearComboBox.getSelectedItem();
        return year + "-" + month + "-" + day;  
    }

    private void addLichThi() {
       
        String maMH = mamonField.getText();
        String checkQuery = "SELECT COUNT(*) FROM MonThi WHERE MaMH = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            
            checkStmt.setString(1, maMH);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                
                String query = "INSERT INTO LichThi (MaLichThi, MaMH, NgayGioThi, PhongThi) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, malichthiField.getText());
                    stmt.setString(2, maMH);
                    stmt.setDate(3, java.sql.Date.valueOf(getNgayThi()));  
                    stmt.setString(4, phongThiField.getText());
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Thêm lịch thi thành công!");
                    loadTableData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã môn thi không hợp lệ!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm lịch thi!");
        }
    }

    private void updateLichThi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String maLichThi = table.getValueAt(selectedRow, 0).toString();
            String query = "UPDATE LichThi SET MaMH = ?, NgayGioThi = ?, PhongThi = ? WHERE MaLichThi = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, mamonField.getText());
                stmt.setDate(2, java.sql.Date.valueOf(getNgayThi()));  
                stmt.setString(3, phongThiField.getText());
                stmt.setString(4, maLichThi);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập nhật lịch thi thành công!");
                loadTableData();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật lịch thi!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để cập nhật!");
        }
    }

    private void deleteLichThi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String maLichThi = table.getValueAt(selectedRow, 0).toString();
            String query = "DELETE FROM LichThi WHERE MaLichThi = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, maLichThi);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa lịch thi thành công!");
                loadTableData();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa lịch thi!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa!");
        }
    }
    
    private void deleteAllLichThi() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa tất cả lịch thi?", "Xóa tất cả", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM LichThi";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa tất cả lịch thi thành công!");
                loadTableData();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tất cả lịch thi!");
            }
        }
    }
    private void resetFields() {
        malichthiField.setText("");
        mamonField.setText("");
        phongThiField.setText("");
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addLichThi();
        } else if (e.getSource() == updateButton) {
            updateLichThi();
        } else if (e.getSource() == deleteButton) {
            deleteLichThi();
        } else if (e.getSource() == deleteAllButton) {
                deleteAllLichThi();
        } else if (e.getSource() == resetButton) {
            resetFields();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuanLyLichThiMain frame = new QuanLyLichThiMain();
            frame.setVisible(true);
        });
    }
}


