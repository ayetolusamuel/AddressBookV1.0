package views;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;

public class DisplayUSers extends JFrame {
    private JPanel main = new JPanel();
    private Container c;
    private JTable table;
    private DatabaseConnection mConnection;
    private JButton printButton;
    private JButton cancleButton;

    DisplayUSers() {
        mConnection = new DatabaseConnection();
        mConnection.open();
        setSize(850, 650);
        setTitle("User's List");
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                UserView userView = new UserView();
                userView.setVisible(true);
                userView.setLocationRelativeTo(null);
                setVisible(false);
            }
        });
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images//address_book_icon.jpg"));
        c = getContentPane();

        main.setLayout(new BorderLayout());
        main.setBackground(new Color(245, 240, 255));


        JLabel label = new JLabel("<html><font size=6 color=#800080><i>User's List");
        JPanel title = new JPanel() {
            public void paintComponent(Graphics g) {

                Toolkit kit = Toolkit.getDefaultToolkit();
                Image img = kit.getImage("images//Gradien.jpg");

                MediaTracker t = new MediaTracker(this);
                t.addImage(img, 1);
                while (true) {
                    try {
                        t.waitForID(1);
                        break;
                    } catch (Exception e) {
                    }
                }
                g.drawImage(img, 0, 0, 1350, 50, null);
            }
        };


        title.add(label);
        main.add("North", title);
        Icon prt = new ImageIcon("images//printer.png");
        printButton = new JButton("print", prt);
        printButton.setBackground(Color.white);
        printButton.setToolTipText("printButton");
        cancleButton = new JButton("Exit");
        cancleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                UserView userView = new UserView();
                userView.setVisible(true);
                userView.setLocationRelativeTo(null);
                setVisible(false);
            }
        });


        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               PrinterJob printerJob = PrinterJob.getPrinterJob();
               printerJob.printDialog();

            }
        });
        cancleButton.setToolTipText("Exit");
        JPanel butpan = new JPanel();
        butpan.add(printButton);
        butpan.add(cancleButton);
        butpan.setBackground(new Color(255, 197, 68));
        c.add("South", butpan);
        try {

            ResultSet set = mConnection.getStatement().executeQuery("select * from users ");
            int row = 0;
            int i = 0;
            while (set.next()) {
                row++;
            }
            DefaultTableModel model = new DefaultTableModel(new String[]{"id_number", "full_name", "email_address",
                    "phone_number", "resident_address","state","age","gender","salary",}, row);


            table = new JTable(model);
            set = mConnection.getStatement().executeQuery("select * from users");
            while (set.next()) {
                model.setValueAt(set.getString("id_number").trim(), i, 0);
                model.setValueAt(set.getString("full_name").trim(), i, 1);
                model.setValueAt(set.getString("email_address").trim(), i, 2);
                model.setValueAt(set.getString("phone_number").trim(), i, 3);
                model.setValueAt(set.getString("resident_address").trim(), i, 4);
                model.setValueAt(set.getString("state").trim(), i, 5);
                model.setValueAt(set.getString("age").trim(), i, 6);
                model.setValueAt(set.getString("gender").trim(), i, 7);
                model.setValueAt(set.getString("salary").trim(), i, 8);

                i++;
            }
            table = new JTable(model);
        } catch (Exception ex) {
        }
        JScrollPane sp = new JScrollPane(table);
        main.add(sp);
        table.setSelectionMode(0);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        table.setForeground(Color.MAGENTA);
        table.setGridColor(new Color(0, 128, 192));
        table.getTableHeader().setReorderingAllowed(true);
        table.setEnabled(false);
        table.setRowHeight(36);
        table.setDragEnabled(true);
        c.add(main);
    }



}