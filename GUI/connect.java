package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import test.DangNhap;

public class connect extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String username;
    private JLabel avataLabel;

    public connect(String username) {
        this.username = username;
        setTitle("Quản lý kì thi tốt nghiệp THPT");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Vo Van Chung\\Desktop\\Java_project\\connect\\image\\Screenshot 2024-12-07 234855.png");
        setIconImage(icon);
        setLayout(new BorderLayout());
        createToolbarAndPanel();
        setVisible(true);
    }

    public void createToolbarAndPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        QuanLyThiSinhMain ts = new QuanLyThiSinhMain();
        QuanLyMonThiMain mt = new QuanLyMonThiMain();
        QuanLyKetQuaMain kq = new QuanLyKetQuaMain();
        QuanLyLichThiMain lt = new QuanLyLichThiMain();
        
        mainPanel.add(ts.createThiSinhPanel(), "ThiSinh");
        mainPanel.add(mt.createMonThiPanel(), "MonThi");
        mainPanel.add(lt.createLichThiPanel(), "LichThi");   
        mainPanel.add(kq.createKetQuaTNPanel(), "Diem");

        thanhToolbar();
        add(mainPanel, BorderLayout.CENTER);
    }

    public void thanhToolbar() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel jPaneltop = new JPanel(new BorderLayout());
        JPanel topleftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        ImageIcon avata = new ImageIcon("C:\\Users\\Vo Van Chung\\Desktop\\Java_project\\connect\\image\\Screenshot 2024-12-28 172225.png");
        Image setavata = avata.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedAvataIcon = new ImageIcon(setavata);

        avataLabel = new JLabel(resizedAvataIcon);
        JLabel name = new JLabel(username);
        name.setFont(new Font("Arial", Font.BOLD, 20));

 
        avataLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn ảnh đại diện");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(connect.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ImageIcon newAvatar = new ImageIcon(selectedFile.getAbsolutePath());
                        Image resizedImage = newAvatar.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        avataLabel.setIcon(new ImageIcon(resizedImage));
                        
                        avataLabel.revalidate();
                        avataLabel.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(connect.this, 
                            "Không thể tải ảnh: " + ex.getMessage(), 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        topleftJPanel.add(avataLabel);
        topleftJPanel.add(name);

        JPanel toprightJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 100, 10));
        JButton logout = new JButton("Đăng xuất");
        toprightJPanel.add(logout);

        logout.addActionListener(e -> {
            dispose();
            new DangNhap();
        });

        jPaneltop.add(topleftJPanel, BorderLayout.WEST);
        jPaneltop.add(toprightJPanel, BorderLayout.EAST);
        topPanel.add(jPaneltop, BorderLayout.NORTH);

        JToolBar toolBar = new JToolBar();
        JButton button1 = new JButton("Thí sinh");
        JButton button2 = new JButton("Môn thi");
        JButton button3 = new JButton("Lịch thi");
        JButton button4 = new JButton("Kết quả");

        toolBar.add(button1); 
        toolBar.add(button2);
        toolBar.add(button3);
        toolBar.add(button4);

        button1.addActionListener(e -> cardLayout.show(mainPanel, "ThiSinh"));
        button2.addActionListener(e -> cardLayout.show(mainPanel, "MonThi"));
        button3.addActionListener(e -> cardLayout.show(mainPanel, "LichThi"));
        button4.addActionListener(e -> cardLayout.show(mainPanel, "Diem"));

        topPanel.add(toolBar, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
    }
    
    public static void main(String[] args) {
        new connect("TestUser");
    }
}
