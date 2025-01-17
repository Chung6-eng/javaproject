package test;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import GUI.connect;
import connectSQL.Database;

public class DangNhap extends JFrame {

    private static final long serialVersionUID = 1L;

    public DangNhap() {
        setTitle("Quản lý kì thi tốt nghiệp THPT");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Vo Van Chung\\Desktop\\Java_project\\connect\\image\\Screenshot 2024-12-07 234855.png");
        setIconImage(icon);

        JLabel titleLabel = new JLabel("Đăng nhập");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(140, 10, 300, 20);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Tên người dùng: ");
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

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(80, 170, 100, 30);

        loginButton.addActionListener(e -> {
            String username = nameTextField.getText();
            String password = new String(passField.getPassword());

            try (Connection conn = Database.getConnection()) {
                if (conn != null) {
                    String query = "SELECT * FROM Login WHERE username = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                        new connect(username);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton registerButton = new JButton("Đăng ký");
        registerButton.setBounds(220, 170, 100, 30);
        registerButton.addActionListener(e -> {
            new DangKi(); 
            dispose(); 
        });

        add(loginButton);
        add(registerButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DangNhap();
    }
}

