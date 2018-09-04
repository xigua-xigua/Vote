package utils;

import bean.Place;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Console extends JFrame {
    private JLabel[] labels = null;
    private JLabel[] results = null;
    private Container container = null;
    public void init(int size) {


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        // 关闭方式

        labels = new JLabel[size];
        results = new JLabel[size];

        container = this.getContentPane();        // 获取一个容器
        this.setLayout(new GridLayout(size + 1, 2, 5, 5));    // 前两个参数为7行3列，后两个参数为网格间的间距

        this.setVisible(true);        // 可视化
        this.setSize(500, 50 * (size + 1));        // 大小
        this.setTitle("投票次数");        // 标题

        JLabel label = new JLabel("社区名称");        // 创建一个场所标签
        JLabel result = new JLabel("投票次数");        // 创建一个数据标签

        label.setHorizontalAlignment(SwingConstants.CENTER);
        result.setHorizontalAlignment(SwingConstants.CENTER);

        container.add(label);
        container.add(result);

        for(int i=0; i<size; i++) {
            labels[i] = new JLabel("");        // 创建一个场所标签
            results[i] = new JLabel("");        // 创建一个数据标签

            labels[i].setHorizontalAlignment(SwingConstants.LEFT);
            results[i].setHorizontalAlignment(SwingConstants.CENTER);

            container.add(labels[i]);
            container.add(results[i]);
        }
    }

    //更新容器内容
    public void refreshContainer(List<Place> list) {
        for (int i = 0; i < list.size(); i++) {
            Place place = list.get(i);
            this.labels[i].setText(place.getName());
            this.results[i].setText(Integer.toString(place.getTimes()));
        }
    }
}
