package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.QuanLyMonThi;
import entity.MonThi;

public class QuanLyMonThiMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private QuanLyMonThi dao1;

    public QuanLyMonThiMain() {
        dao1 = new QuanLyMonThi();
        setTitle("Quản Lý Môn Thi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        add(createMonThiPanel());

    }

    public JPanel createMonThiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        tableModel = new DefaultTableModel(new String[]{"Mã môn thi", "Tên môn thi", "Loại môn thi"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton sortButton = new JButton("Sắp Xếp");
        JButton clearButton = new JButton("Xóa Tất Cả");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(clearButton);
       

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        
        loadTableData();

        addButton.addActionListener(this::Add);
        updateButton.addActionListener(this::Update);
        deleteButton.addActionListener(this::Delete);
        clearButton.addActionListener(this::Clear);
        sortButton.addActionListener(this::Sort);

        return panel;
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<MonThi> list = dao1.getAll();
        for (MonThi mh : list) {
            tableModel.addRow(new Object[]{mh.getMaMH(), mh.getTenMH(), mh.getLoaiMonThi()});
        }
    }

    private void Add(ActionEvent e) {
        String maMH = JOptionPane.showInputDialog(this, "Nhập Mã Môn Học:");
        String tenMH = JOptionPane.showInputDialog(this, "Nhập Tên Môn Học:");
        String loaiMonThi = JOptionPane.showInputDialog(this, "Nhập Loại Môn Thi:");
        if (maMH != null && tenMH != null && loaiMonThi != null && dao1.add(new MonThi(maMH, tenMH, loaiMonThi))) {
            loadTableData();
        }
    }

    private void Update(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để sửa.");
            return;
        }

        String maMH = (String) tableModel.getValueAt(selectedRow, 0);
        String tenMH = JOptionPane.showInputDialog(this, "Nhập Tên Môn Học mới:");
        String loaiMonThi = JOptionPane.showInputDialog(this, "Nhập Loại Môn Thi mới:");
        if (tenMH != null && loaiMonThi != null && dao1.update(new MonThi(maMH, tenMH, loaiMonThi))) {
            loadTableData();
        }
    }

    private void Delete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để xóa.");
            return;
        }

        String maMH = (String) tableModel.getValueAt(selectedRow, 0);
        if (dao1.delete(maMH)) {
            loadTableData();
        }
    }

    private void Clear(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tất cả?") == JOptionPane.YES_OPTION) {
            if (dao1.deleteAll()) {
                loadTableData();
            }
        }
    }

    private void Sort(ActionEvent e) {
        List<MonThi> list = dao1.getAll();
        Collections.sort(list, Comparator.comparing(MonThi::getTenMH));
        tableModel.setRowCount(0);
        for (MonThi mh : list) {
            tableModel.addRow(new Object[]{mh.getMaMH(), mh.getTenMH(), mh.getLoaiMonThi()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyMonThiMain().setVisible(true));
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
