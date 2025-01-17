package testSQL;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class testJFreechart {
    public static void main(String[] args) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Series1", "Tháng 1");
        dataset.addValue(20, "Series1", "Tháng 2");
        dataset.addValue(30, "Series1", "Tháng 3");

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Biểu đồ đường", 
                "Thời gian",     
                "Giá trị",       
                dataset
        );

        JFrame frame = new JFrame("JFreeChart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(lineChart));
        frame.pack();
        frame.setVisible(true);
    }
}

