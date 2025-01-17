package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.QuanLyThiSinh;
import connectSQL.Database;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class QuanLyThiSinhMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;  
    private JTextField mathisinhField;
    private JTextField diachiField;
    private JTextField hotenField;
    private JTextField lopField;
    private JTextField SoCCCD;
    private JTextField GmailField;
    private JButton addButton, updateButton, deleteButton, sortButton, deleteallButton;
    private JTable table;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    private JRadioButton maleButton, femaleButton;
    private ButtonGroup genderGroup;
	private JButton searchButton;
	private DefaultTableModel tableModel;
	
    public QuanLyThiSinhMain() {
    	new QuanLyThiSinh();
        setTitle("Quản Lý Thí Sinh");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createThiSinhPanel(), BorderLayout.CENTER);
        
    }

    public JPanel createThiSinhPanel() {
    	
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin thí sinh"));
        
        JPanel leftPanel = new JPanel(new GridLayout(16, 2, 10, 10));
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        
        JLabel id = new JLabel("Mã thí sinh:");
        mathisinhField = new JTextField(10);
        leftPanel.add(id);
        leftPanel.add(mathisinhField);

        JLabel nameLabel = new JLabel("Họ tên:");
        hotenField = new JTextField(10);
        leftPanel.add(nameLabel);
        leftPanel.add(hotenField);

        JLabel dob = new JLabel("Ngày sinh:");
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        dobPanel.add(dayComboBox);
        dobPanel.add(monthComboBox);
        dobPanel.add(yearComboBox);
        leftPanel.add(dob);
        leftPanel.add(dobPanel);

        JLabel genderLabel = new JLabel("Giới tính:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleButton = new JRadioButton("Nam");
        femaleButton = new JRadioButton("Nữ");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        leftPanel.add(genderLabel);
        leftPanel.add(genderPanel);

        JLabel lopJLabel = new JLabel("Lớp:");
        lopField = new JTextField(10);
        leftPanel.add(lopJLabel);
        leftPanel.add(lopField);

        JLabel idCardLabel = new JLabel("Số điện thoại:");
        SoCCCD = new JTextField(10);
        leftPanel.add(idCardLabel);
        leftPanel.add(SoCCCD);

        JLabel loai = new JLabel("Gmail:");
        GmailField = new JTextField(10);
        leftPanel.add(loai);
        leftPanel.add(GmailField);

        JLabel diachi = new JLabel("Địa chỉ:");
        diachiField = new JTextField(10);
        leftPanel.add(diachi);
        leftPanel.add(diachiField);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Tìm kiếm theo Mã học sinh:"));
        searchPanel.add(searchField);
        
        searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        
        tableModel = new DefaultTableModel(
                new Object[]{"Mã học sinh", "Tên", "Ngày sinh", "Giới tính", "Lớp", "Số điện thoại", "Gmail", "Địa chỉ"}, 0);
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
        deleteButton.addActionListener(this::deleteThiSinh);

        sortButton = new JButton("Sắp xếp");
        sortButton.addActionListener(this);
        buttonPanel.add(sortButton);

        deleteallButton = new JButton("Xóa tất cả");
        deleteallButton.addActionListener(this);
        buttonPanel.add(deleteallButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private String getNgaySinh() {
        return dayComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + yearComboBox.getSelectedItem();
    }

    private String getGioiTinh() {
        return maleButton.isSelected() ? "Nam" : "Nữ";
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);  
        String sql = "SELECT * FROM ThiSinh";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MaHS"),
                    rs.getString("HoTen"),
                    rs.getString("Ngay_Sinh"),
                    rs.getString("GioiTinh"),
                    rs.getString("Lop"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Gmail"),
                    rs.getString("DiaChi")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ CSDL!");
        }
    }

    private void addThiSinh() {
        if (!validateInputFields()) {
            return;
        }

        String maHS = mathisinhField.getText();

        String checkSql = "SELECT COUNT(*) FROM ThiSinh WHERE MaHS = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setString(1, maHS);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Mã thí sinh đã tồn tại. Vui lòng nhập mã khác.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra mã thí sinh!");
            return;
        }

        String sql = "INSERT INTO ThiSinh (MaHS, HoTen, Ngay_Sinh, GioiTinh, Lop, SoDienThoai , Gmail, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHS);
            ps.setString(2, hotenField.getText());
            ps.setString(3, getNgaySinh());
            ps.setString(4, getGioiTinh());
            ps.setString(5, lopField.getText());
            ps.setString(6, SoCCCD.getText());
            ps.setString(7, GmailField.getText());
            ps.setString(8, diachiField.getText());

            int slhang = ps.executeUpdate();
            if (slhang > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thí sinh thành công!");
                loadTableData(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm thí sinh!");
        }
    }


    private void updateThiSinh() {
        if (!validateInputFields()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một thí sinh để sửa.");
            return;
        }

        String maHS = (String) tableModel.getValueAt(selectedRow, 0);

        String sql = "UPDATE ThiSinh SET HoTen=?, Ngay_Sinh=?, GioiTinh=?, Lop=?, SoDienThoai=?, Gmail=?, DiaChi=? WHERE MaHS=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hotenField.getText());
            ps.setString(2, getNgaySinh());
            ps.setString(3, getGioiTinh());
            ps.setString(4, lopField.getText());
            ps.setString(5, SoCCCD.getText());
            ps.setString(6, GmailField.getText());
            ps.setString(7, diachiField.getText());
            ps.setString(8, maHS);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thí sinh thành công!");
                loadTableData(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thí sinh!");
        }
    }


    private void sortThiSinh() {
     
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<Object[]> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] row = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j);
            }
            rows.add(row);
        }

     
        rows.sort(Comparator.comparing(o -> (String) o[1]));

        model.setRowCount(0);

        for (Object[] row : rows) {
            model.addRow(row);
        }

        JOptionPane.showMessageDialog(this, "Danh sách thí sinh đã được sắp xếp!");
    }


    private void deleteThiSinh(ActionEvent actionevent1) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để xóa.");
            return;
        }

        String maHS = (String) tableModel.getValueAt(selectedRow, 0);
        String sql = "DELETE FROM ThiSinh WHERE MaHS = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHS);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thí sinh thành công!");
                loadTableData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa thí sinh!");
        }
    }
    
    private void searchThiSinh() {
        String searchText = searchField.getText().toLowerCase(); 
        if (searchText.isEmpty()) {
            return; 
        }

        String sql = "SELECT * FROM ThiSinh WHERE LOWER(MaHS) LIKE ?"; 
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchText + "%"); 

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); 

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("MaHS"),
                        rs.getString("HoTen"),
                        rs.getString("Ngay_Sinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("Lop"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Gmail"),
                        rs.getString("DiaChi")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm thí sinh!");
        }
    }

    private void deleteAllThiSinh() {
        String sql = "DELETE FROM ThiSinh";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tất cả thí sinh?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Đã xóa tất cả thí sinh!");
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa tất cả thí sinh!");
        }
    }
    
    private boolean validateInputFields() {
        if (mathisinhField.getText().trim().isEmpty() || hotenField.getText().trim().isEmpty() || 
            lopField.getText().trim().isEmpty() || SoCCCD.getText().trim().isEmpty() || 
            GmailField.getText().trim().isEmpty() || diachiField.getText().trim().isEmpty() || 
            genderGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addButton) {
            addThiSinh();
        } else if (e.getSource() == updateButton) {
            updateThiSinh();
       } else if (e.getSource() == sortButton) {
            sortThiSinh();
        } else if (e.getSource() == deleteallButton) {
        	deleteAllThiSinh();
        } else if (e.getSource() == searchButton) { 
            searchThiSinh();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuanLyThiSinhMain frame = new QuanLyThiSinhMain();
            frame.setVisible(true);
        });
    }
    
}
