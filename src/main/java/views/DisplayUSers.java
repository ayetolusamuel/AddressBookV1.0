package views;

import database.DatabaseConnection;
import model.User;
import org.jdesktop.swingx.prompt.PromptSupport;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DisplayUSers extends JFrame implements ActionListener {
    private final JTextField jTextField;
    private final JButton jButtonSearch;
    private JComboBox<String> jComboBox;
    private JPanel main = new JPanel();
    private String value = "Top 5";
    private Container c;
    private JTable table;
    private DatabaseConnection mConnection;
    private JButton printButton,cancleButton;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    DisplayUSers() {
        mConnection = new DatabaseConnection();
        mConnection.open();
        setSize(850, 650);
        setTitle("User's List");
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                UserView userView = new UserView();
                userView.setVisible(true);
                userView.setLocationRelativeTo(null);

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
        String[] searchVar = {"Top 5", "Male", "Female", "below 5000", "above 5000", "Name", "All users"};
        jComboBox = new JComboBox<>(searchVar);
        main.add(jComboBox).setBounds(10, 10, 100, 25);
        jComboBox.addActionListener(this);


        jTextField = new JTextField();
        PromptSupport.setPrompt("enter full name here!!!",jTextField);
        main.add(jTextField).setBounds(120, 10, 150, 25);
        jButtonSearch = new JButton("search");
        jButtonSearch.setFont(new Font("New Times Romans", Font.BOLD, 10));
        main.add(jButtonSearch).setBounds(280, 10, 70, 25);
        jButtonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String returnValue = getValue();
                if (returnValue.equalsIgnoreCase("Top 5")) {
                    String query = "SELECT * FROM users LIMIT 0,5";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("Male")) {
                    String gender = "male";
                    String query = "select * from users where gender =" + "'" + gender + "'";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("Female")) {
                    String gender = "female";
                    String query = "select * from users where gender =" + "'" + gender + "'";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("below 5000")) {
                    String query = "select * from users where salary <5000";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("above 5000")) {
                    String query = "select * from users where salary >5000";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("all users")) {
                    String query = "select * from users ";
                    table.setModel(new UserTableModel(getInfo(query)));
                } else if (returnValue.equalsIgnoreCase("name")) {
                    String name = jTextField.getText();
                    String query = "select * from users where full_name like " + "'" + "%" + name + "%" + "'";
                    table.setModel(new UserTableModel(getInfo(query)));
                }
            }

        });


        if (value.equalsIgnoreCase("Top 5")) {
            jTextField.setBounds(0, 0, 0, 0);
            jButtonSearch.setBounds(120, 10, 70, 25);
        }

        main.add("North", title);
        Icon prt = new ImageIcon("images//printer.png");
        printButton = new JButton("print", prt);
        printButton.setBackground(Color.white);
        printButton.setToolTipText("printButton");
        cancleButton = new JButton("Exit");
        cancleButton.addActionListener(e -> {

            setVisible(false);
            UserView userView = new UserView();
            userView.setVisible(true);
            userView.setLocationRelativeTo(null);

        });


        printButton.addActionListener(e-> {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.printDialog();

        });
        cancleButton.setToolTipText("Exit");
        JPanel butpan = new JPanel();
        butpan.add(printButton);
        butpan.add(cancleButton);
        butpan.setBackground(new Color(255, 197, 68));
        c.add("South", butpan);
        c.add(main);
        String query = "SELECT * FROM users";
        populateTable(query);
        displayTable();
    }

    private void populateTable(String query) {
        try {


            ResultSet set = mConnection.getStatement().executeQuery(query);
            int row = 0;
            int i = 0;
            while (set.next()) {
                row++;
            }
            DefaultTableModel model = new DefaultTableModel(new String[]{"id_number", "full_name", "email_address",
                    "phone_number", "resident_address", "state", "age", "gender", "salary",}, row);


            table = new JTable(model);

            set = mConnection.getStatement().executeQuery(query);
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
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String values;
        jComboBox = (JComboBox<String>) e.getSource();
        values = (String) jComboBox.getSelectedItem();
        setValue(values);
        if (values.equalsIgnoreCase("Top 5") || values.equalsIgnoreCase("male")
                || values.equalsIgnoreCase("female") || values.equalsIgnoreCase("below 5000")
                || values.equalsIgnoreCase("above 5000")|| values.equalsIgnoreCase("all users")) {
          jTextField.setBounds(0, 0, 0, 0);
            main.add(jButtonSearch).setBounds(120, 10, 70, 25);

        }else if (values.equalsIgnoreCase("name")) {
            main.add(jTextField).setBounds(120, 10, 150, 25);
            main.add(jButtonSearch).setBounds(280, 10, 70, 25);

        }}

    private List<User> getInfo(String query) {
        List<User> arrayList = new ArrayList<>();
        try {
            ResultSet resultSet = mConnection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                arrayList.add(new User(resultSet.getString("id_number"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email_address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("resident_address"),
                        resultSet.getString("state"),
                        resultSet.getString("age"),
                        resultSet.getString("gender"),
                        resultSet.getString("salary")
                ));
            }} catch (Exception ex) {
            ex.printStackTrace();
        }

        return arrayList;
    }

    private void displayTable() {
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