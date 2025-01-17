package GUI;

import DAO.QuanLyKetQua;
import DAO.QuanLyMonThi;
import entity.KetQua;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuanLyKetQuaMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private QuanLyKetQua dao;
    private QuanLyMonThi dao1;
    public QuanLyKetQuaMain() {
        dao = new QuanLyKetQua();
        dao1 = new QuanLyMonThi();
        setTitle("Quản Lý Kết Quả Thi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        add(createKetQuaTNPanel());
    }

    public JPanel createKetQuaTNPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Mã kết quả","Mã học sinh", "Mã môn thi", "Điểm", "Lần thi"}, 0);
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton sortButton = new JButton("Sắp Xếp");
        JButton clearButton = new JButton("Xóa Tất Cả");
        JButton chartButton = new JButton("Xem Biểu Đồ");
        JButton exportButton = new JButton("Xuất Excel"); 

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(chartButton);
        buttonPanel.add(exportButton); 

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        addButton.addActionListener(this::Add);
        updateButton.addActionListener(this::Update);
        deleteButton.addActionListener(this::Delete);
        clearButton.addActionListener(this::Clear);
        sortButton.addActionListener(this::Sort);
        chartButton.addActionListener(this::Chart);
        exportButton.addActionListener(this::Export);
        loadTableData();
        return panel;
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<KetQua> list = dao.getAll();
        for (KetQua kq : list) {
            tableModel.addRow(new Object[]{kq.getMaKetQua(), kq.getMaThiSinh(), kq.getMaMonThi(), kq.getDiem(), kq.getLanThi()});
        }
    }

    private void Add(ActionEvent e) {
        
        String maKetQua = JOptionPane.showInputDialog(this, "Nhập mã kết quả:"); 
        if (maKetQua == null || maKetQua.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã kết quả không được bỏ trống.");
            return;
        }
  
        String maHS = JOptionPane.showInputDialog(this, "Nhập mã học sinh:");
        String maMH = JOptionPane.showInputDialog(this, "Nhập mã môn thi:");

        maHS = maHS.trim();
        maMH = maMH.trim();

        if (maHS.isEmpty() || maMH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã học sinh và mã môn thi không được bỏ trống.");
            return;
        }

        boolean isExisting = dao.isMaHSMaMHExist(maHS, maMH);
        if (isExisting) {
            JOptionPane.showMessageDialog(this, "Mã học sinh " + maHS + " đã thi môn " + maMH + " rồi. Không thể thêm!");
            return; 
        }

        float diem = 0;
        try {
            diem = Float.parseFloat(JOptionPane.showInputDialog(this, "Nhập điểm:"));
            if (diem < 0 || diem > 10) {
                JOptionPane.showMessageDialog(this, "Điểm phải nằm trong khoảng 0 đến 10.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm không hợp lệ.");
            return;
        }

        int lanThi = 0;
        try {
            lanThi = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập số lần thi:"));
            if (lanThi < 1) {
                JOptionPane.showMessageDialog(this, "Số lần thi phải lớn hơn hoặc bằng 1.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lần thi không hợp lệ.");
            return;
        }

        KetQua kq = new KetQua(maKetQua, maHS, maMH, diem, lanThi);
        boolean isAdded = dao.add(kq);
        if (isAdded) {
            JOptionPane.showMessageDialog(this, "Thêm kết quả thành công.");
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm kết quả.");
        }
    }

    private void Update(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa.");
            return;
        }

        String maKetQua = (String) tableModel.getValueAt(selectedRow, 0);
        String maHS = (String) tableModel.getValueAt(selectedRow, 1);
        String maMH = (String) tableModel.getValueAt(selectedRow, 2);

        float diem = -1;
        try {
            String diemInput = JOptionPane.showInputDialog(this, "Nhập điểm mới (0-10):");
            if (diemInput != null && !diemInput.trim().isEmpty()) {
                diem = Float.parseFloat(diemInput);
                if (diem < 0 || diem > 10) {
                    JOptionPane.showMessageDialog(this, "Điểm phải trong khoảng từ 0 đến 10.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Điểm không được bỏ trống.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số hợp lệ.");
            return;
        }

        
        int lanThi = -1;
        try {
            String lanThiInput = JOptionPane.showInputDialog(this, "Nhập số lần thi mới (>=1):");
            if (lanThiInput != null && !lanThiInput.trim().isEmpty()) {
                lanThi = Integer.parseInt(lanThiInput);
                if (lanThi < 1) {
                    JOptionPane.showMessageDialog(this, "Số lần thi phải lớn hơn hoặc bằng 1");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Số lần thi không được bỏ trống.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Số lần thi phải là số nguyên hợp lệ");
            return;
        }


        KetQua kq = new KetQua(maKetQua, maHS, maMH, diem, lanThi);

        boolean isUpdated = dao.update(kq);
        if (isUpdated) {

            loadTableData();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật kết quả.");
        }
    }


    private void Delete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để xóa.");
            return;
        }

        String maHS = (String) tableModel.getValueAt(selectedRow, 1);
        String maMH = (String) tableModel.getValueAt(selectedRow, 2);

        if (dao.delete(maHS, maMH)) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Xóa thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Không thể xóa kết quả.");
        }
    }


    private void Sort(ActionEvent e) {
        List<KetQua> ketQuas = dao.getAll();
        ketQuas.sort((kq1, kq2) -> Float.compare(kq2.getDiem(), kq1.getDiem()));
        tableModel.setRowCount(0);
        for (KetQua kq : ketQuas) {
            tableModel.addRow(new Object[]{kq.getMaKetQua(), kq.getMaThiSinh(), kq.getMaMonThi(), kq.getDiem(), kq.getLanThi()});
        }
    }

    private void Clear(ActionEvent e) {
        if (dao.deleteAll()) {
            loadTableData();
        }
    }

    private void Chart(ActionEvent e) {
        DefaultCategoryDataset setdata = new DefaultCategoryDataset();
        Map<String, List<Float>> subjectScore = new HashMap<>();
 
        List<KetQua> ketQuaList = dao.getAll();
        for (KetQua kq : ketQuaList) {
            String maMH = kq.getMaMonThi();
            float diem = kq.getDiem();
   
            subjectScore.computeIfAbsent(maMH, k -> new ArrayList<>()).add(diem);
        }

        for (Map.Entry<String, List<Float>> entry : subjectScore.entrySet()) {
            String maMH = entry.getKey();
            List<Float> scores = entry.getValue();

            float sum = 0;
            for (float score : scores) {
                sum += score;
            }
            float avgScore = sum / scores.size();

          
            String tenMH = dao1.getTenMonThi(maMH); 

            setdata.addValue(avgScore, "Điểm trung bình", tenMH);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu Đồ Điểm Trung Bình Theo Từng Môn",
                "Môn Học",
                "Điểm Trung Bình",
                setdata,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.RED);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 12, 24));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 10); 
        rangeAxis.setTickUnit(new NumberTickUnit(1));

        barChart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));
        plot.getDomainAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 10));
        plot.getRangeAxis().setLabelFont(new Font("Arial", Font.PLAIN, 12));

        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Biểu Đồ");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(800, 600);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
        chartFrame.setLocationRelativeTo(null);
    }


    private void Export(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Kết Quả");

                String[] columns = {"Mã Kết Quả", "Mã Thí Sinh", "Mã Môn Thi", "Điểm", "Lần Thi"};
                XSSFRow headerRow = sheet.createRow(0);
                XSSFCellStyle headerStyle = workbook.createCellStyle();
                XSSFFont headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);

                for (int i = 0; i < columns.length; i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }
                
                List<KetQua> ketQuaList = dao.getAll();
                int rowNum1 = 1;
                for (KetQua kq : ketQuaList) {
                    XSSFRow row = sheet.createRow(rowNum1++);
                    row.createCell(0).setCellValue(kq.getMaKetQua()); 
                    row.createCell(1).setCellValue(kq.getMaThiSinh()); 
                    row.createCell(2).setCellValue(kq.getMaMonThi()); 
                    row.createCell(3).setCellValue(kq.getDiem()); 
                    row.createCell(4).setCellValue(kq.getLanThi()); 
                    
                }
               
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                java.nio.file.Path path = java.nio.file.Paths.get(filePath);
                java.nio.file.Path parentDir = path.getParent();
                if (parentDir != null && !java.nio.file.Files.exists(parentDir)) {
                    java.nio.file.Files.createDirectories(parentDir);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(this, "Dữ liệu đã được xuất ra Excel thành công.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi ghi tệp: " + ex.getMessage());
            }
        }
    }
   

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

