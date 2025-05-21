package com.example.monit;

import com.sun.management.OperatingSystemMXBean;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.net.URL;

public class OpenForm extends JWindow {

    public OpenForm window;   //Публичное для тестов

    //OperatingSystemMXBean
    //класс, для доступа к информации о системе
    private static final OperatingSystemMXBean osBean =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    private JPopupMenu contextMenu; //поле для контекстного меню
    private SystemTray tray;
    private TrayIcon trayIcon;

    public OpenForm(){

    }

    public void openFrame() {

        window = new OpenForm();
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.setAlwaysOnTop(true);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setEnabled(true);

        JTextField label = new JTextField();
        JTextField label1 = new JTextField();
        JTextField label2 = new JTextField();

        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.GREEN);
        label.setBorder(null);
        label.setEditable(false);
        label.setOpaque(false);
        label.setEnabled(true);


        label1.setFont(new Font("Arial", Font.BOLD, 15));
        label1.setForeground(Color.GREEN);
        label1.setBorder(null);
        label1.setEditable(false);
        label1.setOpaque(false);
        label1.setEnabled(true);

        //Color currentColor = label1.getForeground();

        label2.setFont(new Font("Arial", Font.BOLD, 15));
        label2.setForeground(Color.GREEN);
        label2.setBorder(null);
        label2.setEditable(false);
        label2.setOpaque(false);
        label2.setEnabled(true);

        window.getContentPane().add(label);
        window.getContentPane().add(label1);
        window.getContentPane().add(label2);


        window.pack();
        //window.setLocation(70, 600);
        window.setLocationRelativeTo(null); // Центрируем
        window.setSize(250, 100);
        window.setVisible(true);

        //вывод данных параллельно в отдельном потоке
        Thread updateThread = new Thread(() -> {
            while (true) {
                double cpuLoad = osBean.getCpuLoad() * 100;
                long totalMem = osBean.getTotalMemorySize() / (1024 * 1024);
                long freeMem = osBean.getFreeMemorySize() / (1024 * 1024);
                long usedMem = totalMem - freeMem;

                //специальный поток, ответственный за обработку событий графического интерфейса
                SwingUtilities.invokeLater(() -> {
                    label.setText("CPU: " + String.format("%.1f%%", cpuLoad));
                    label1.setText("DRAM: " + usedMem + " MB / " + totalMem + " MB");
                    label2.setText("GPU: N/A");
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        //демоны: потоки, которые
        // выполняют фоновые задачи, такие как запись логов, мониторинг или сбор данных
        updateThread.setDaemon(true);
        updateThread.start();

        //TrayIcon
        window.tray = SystemTray.getSystemTray();
        Image icon;
        try {
            URL imageUrl = OpenForm.class.getResource("");//путь до иконки
            if (imageUrl == null) {
                throw new RuntimeException("иконка не найдена");
            }
            icon = Toolkit.getDefaultToolkit().getImage(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
        window.trayIcon = new TrayIcon(icon, "Мониторинг", null);
        window.trayIcon.setImageAutoSize(true);  // авто масштабирование


        window.contextMenu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Свернуть");
        JMenuItem item2 = new JMenuItem("Закрыть");

        //слушатели списка меню
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Добавляем TrayIcon в трей
                try {
                    window.tray.add(window.trayIcon);
                    window.setVisible(false);
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });

        //обработчик событий для возврата из трея
        window.trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(true);
                window.toFront();
                window.tray.remove(window.trayIcon);
            }
        });

        window.contextMenu.add(item1);
        window.contextMenu.add(item2);

        MouseAdapter mouseap = new MouseAdapter() {

            //доступ к initlClick ограничен, только для внутри объекта
            private Point initlClick;

            @Override
            public void mousePressed(MouseEvent e) {
                initlClick = e.getPoint();
                window.getRootPane().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                window.getRootPane().getGlassPane().setCursor(Cursor.getDefaultCursor());

                if (e.getButton() == MouseEvent.BUTTON3) { // Проверка на нажатие правой кнопки
                    // Показываем уже созданное контекстное меню
                    window.contextMenu.show(window, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = window.getLocation().x;
                int thisY = window.getLocation().y;

                int xMoved = e.getXOnScreen() - initlClick.x - thisX;
                int yMoved = e.getYOnScreen() - initlClick.y - thisY;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                window.setLocation(X, Y);
            }
        };


        window.addMouseListener(mouseap);
        window.addMouseMotionListener(mouseap);
        label.addMouseListener(mouseap);
        label.addMouseMotionListener(mouseap);
        label1.addMouseListener(mouseap);
        label1.addMouseMotionListener(mouseap);
        label2.addMouseListener(mouseap);
        label2.addMouseMotionListener(mouseap);

    }

}



