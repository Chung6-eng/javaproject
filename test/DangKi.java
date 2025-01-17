package test;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import connectSQL.Database;

public class DangKi extends JFrame {

    private static final long serialVersionUID = 1L;

    public DangKi() {
        setTitle("Quản lý kì thi tốt nghiệp THPT");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Vo Van Chung\\Desktop\\Java_project\\connect\\image\\Screenshot 2024-12-07 234855.png");
        setIconImage(icon);

        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(160, 10, 250, 20);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Tên đăng nhập: ");
        usernameLabel.setBounds(20, 50, 100, 25);
        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(130, 50, 200, 25);
        add(usernameLabel);
        add(nameTextField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(20, 100, 100, 25);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(130, 100, 200, 25);
        add(passwordLabel);
        add(passField);

        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:");
        confirmPasswordLabel.setBounds(20, 150, 120, 25);
        JPasswordField confirmPassField = new JPasswordField();
        confirmPassField.setBounds(130, 150, 200, 25);
        add(confirmPasswordLabel);
        add(confirmPassField);

        JButton registerButton = new JButton("Đăng ký");
        registerButton.setBounds(100, 200, 200, 30);
        registerButton.addActionListener(e -> {
            String username = nameTextField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = Database.getConnection()) {
                if (conn != null) {
                    String checkQuery = "SELECT * FROM Login WHERE username = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                    checkStmt.setString(1, username);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String insertQuery = "INSERT INTO Login (username, password) VALUES (?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                        insertStmt.setString(1, username);
                        insertStmt.setString(2, password);

                        int rows = insertStmt.executeUpdate();
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                            dispose(); 
                        } else {
                            JOptionPane.showMessageDialog(this, "Đăng ký thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(registerButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DangKi();
    }
}
