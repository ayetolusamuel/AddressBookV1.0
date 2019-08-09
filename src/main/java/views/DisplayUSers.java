package views;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

public class DisplayUSers extends JFrame {
    private final JScrollPane jScrollPane;
    JPanel pane;
    private JTable mTable;
    private DatabaseConnection mConnection = new DatabaseConnection();


    DisplayUSers(DatabaseConnection connection) {
        this.mConnection = connection;
        mConnection.open();
        setSize(480, 300);


        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {

                UserView userView = new UserView();
                userView.setVisible(true);
                userView.setLocationRelativeTo(null);
                setVisible(false);





            }
        });

        Image image = Toolkit.getDefaultToolkit().getImage("images//address_book_icon.jpg");
        setIconImage(image);

        pane = new JPanel() {

            public void paintComponent(Graphics g) {
                Toolkit kit = Toolkit.getDefaultToolkit();
                Image img = kit.getImage("images//background.png");//background image...add format of the file
                MediaTracker t = new MediaTracker(this);
                t.addImage(img, 1);
                while (true) {
                    try {
                        t.waitForID(1);
                        break;
                    } catch (Exception e) {
                    }
                }
                g.drawImage(img, 0, 0, 500, 350, null);
            }
        };


        try {

            ResultSet set = mConnection.getStatement().executeQuery("select * from users ");

            int row = 0;
            int i = 0;
            while (set.next()) {
                row++;
            }
            DefaultTableModel model = new DefaultTableModel(new String[]{"id_number","full_name","email_address","phone_number","email_address",}, row);

            mTable = new JTable(model);
            set = mConnection.getStatement().executeQuery("select * from users ");
            while (set.next()) {
                model.setValueAt(set.getString("id_number").trim(), i, 0);
                model.setValueAt(set.getString("full_name").trim(), i, 1);
                model.setValueAt(set.getString("email_address").trim(), i, 2);
                model.setValueAt(set.getString("phone_number").trim(), i, 3);
                model.setValueAt(set.getString("resident_address").trim(), i, 4);
                i++;
            }
            mTable = new JTable(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mTable.setSelectionMode(0);
        mTable.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mTable.setForeground(Color.MAGENTA);
        mTable.setGridColor(new Color(0, 128, 192));
        mTable.setEnabled(false);
        mTable.setRowHeight(36);
        mTable.setDragEnabled(true);
        mTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane = new JScrollPane(mTable);
        jScrollPane.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        pane.add(jScrollPane).setBounds(0, 0, 400, 350);


        getContentPane().add(pane, BorderLayout.CENTER);
    }


}
