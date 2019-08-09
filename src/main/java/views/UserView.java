package views;

import database.DatabaseConnection;
import model.User;
import org.jdesktop.swingx.prompt.PromptSupport;
import utils.MarqueeLabel;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserView extends JFrame implements ActionListener {
    private final JScrollPane addressScrollPane;
    private JLabel mLabelId, mLabelName, mLabelEmail, mLabelPhoneNumber, mLabelResidentAddress;
    private JButton mJButtonCheck, mJButtonSave, mJButtonDelete, mJButtonUpdate, mJButtonRead, mJButtonExit;
    private JTextField mFieldId, mFieldName, mFieldEmail, mFieldPhoneNumber;
    private JTextArea mFieldResidentAddress;
    private JPanel pane;
    private DatabaseConnection mConnection = new DatabaseConnection();


    public UserView() {

        mConnection.open();
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
        setResizable(false);
        setTitle("Address Book by Samuel V1.0");
        setSize(420, 350);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pane.setLayout(null);
        JLabel jLabel = new MarqueeLabel("for saving,dont input id, its auto generated.", MarqueeLabel.RIGHT_TO_LEFT, 20);
        jLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel.setForeground(Color.white);
        pane.add(jLabel).setBounds(10, 0, 400, 20);

        mLabelId = new JLabel("Id Num. :");
        mLabelId.setForeground(Color.white);
        pane.add(mLabelId).setBounds(10, 40, 100, 35);
        mLabelId.setFont(new Font("Times New Roman", Font.BOLD, 20));

        mFieldId = new JTextField();
        mFieldId.setToolTipText("search with phone num.");
        PromptSupport.setPrompt("search via contact", mFieldId);
        PromptSupport.setForeground(Color.magenta, mFieldId);
        mFieldId.setForeground(Color.RED);

        mFieldId.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        pane.add(mFieldId).setBounds(100, 40, 110, 25);


        mLabelName = new JLabel("Name :");
        pane.add(mLabelName).setBounds(10, 80, 70, 35);
        mLabelName.setForeground(Color.white);
        mLabelName.setFont(new Font("Times New Roman", Font.BOLD, 20));

        mFieldName = new JTextField();
        mFieldName.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        pane.add(mFieldName).setBounds(100, 82, 200, 25);


        mLabelEmail = new JLabel("Email: ");
        mLabelEmail.setForeground(Color.white);
        pane.add(mLabelEmail).setBounds(10, 120, 80, 35);
        mLabelEmail.setFont(new Font("Times New Roman", Font.BOLD, 20));

        mFieldEmail = new JTextField();
        mFieldEmail.setCaretPosition(mFieldEmail.getText().length());
        mFieldEmail.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        pane.add(mFieldEmail).setBounds(100, 122, 200, 25);

        mLabelPhoneNumber = new JLabel("Phone: ");
        pane.add(mLabelPhoneNumber).setBounds(10, 160, 80, 35);
        mLabelPhoneNumber.setForeground(Color.white);
        mLabelPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 20));

        mFieldPhoneNumber = new JTextField();
        mFieldPhoneNumber.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        pane.add(mFieldPhoneNumber).setBounds(100, 162, 200, 25);
        mFieldPhoneNumber.addKeyListener (new KeyAdapter() {
            public void keyTyped (KeyEvent ke) {
                char c = ke.getKeyChar ();
                if (! ( (c == KeyEvent.VK_BACK_SPACE))) {
                    if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' ||
                            c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) {
                        getToolkit().beep ();
                        ke.consume ();}}}});


        mLabelResidentAddress = new JLabel("Address: ");
        mLabelResidentAddress.setForeground(Color.white);
        pane.add(mLabelResidentAddress).setBounds(10, 200, 80, 35);
        mLabelResidentAddress.setFont(new Font("Times New Roman", Font.BOLD, 20));

        mFieldResidentAddress = new JTextArea();
        mFieldResidentAddress.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        mFieldResidentAddress.setToolTipText("Enter resident Address here.");
        mFieldResidentAddress.setBorder(BorderFactory.createBevelBorder(1, new Color(192, 192, 255), new Color(192, 192, 255)));
        mFieldResidentAddress.setWrapStyleWord(true);
        mFieldResidentAddress.setLineWrap(true);

        addressScrollPane = new JScrollPane(mFieldResidentAddress);
        pane.add(addressScrollPane).setBounds(100, 202, 200, 70);


        mJButtonCheck = new JButton(new ImageIcon("images/search.png"));
        pane.add(mJButtonCheck).setBounds(220, 38, 30, 30);
        mJButtonCheck.addActionListener(this);

        mJButtonSave = new JButton("Save");
        mJButtonSave.addActionListener(this);
        pane.add(mJButtonSave).setBounds(10, 280, 65, 30);

        mJButtonUpdate = new JButton("Update");
        mJButtonUpdate.setEnabled(false);
        mJButtonUpdate.addActionListener(this);
        pane.add(mJButtonUpdate).setBounds(80, 280, 75, 30);


        mJButtonDelete = new JButton("Delete");
        mJButtonDelete.setEnabled(false);
        mJButtonDelete.addActionListener(this);
        pane.add(mJButtonDelete).setBounds(160, 280, 75, 30);

        mJButtonRead = new JButton("View Users");
        mJButtonRead.addActionListener(this);
        pane.add(mJButtonRead).setBounds(240, 280, 100, 30);

        mJButtonExit = new JButton("Exit");
        mJButtonExit.addActionListener(this);
        pane.add(mJButtonExit).setBounds(345, 280, 60, 30);


        getContentPane().add(pane, BorderLayout.CENTER);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String id = mFieldId.getText();
        String name = mFieldName.getText();
        String email = mFieldEmail.getText();
        String phone_number = mFieldPhoneNumber.getText();
        String address = mFieldResidentAddress.getText();
        Object source = e.getSource();


        if (source == mJButtonSave) {

            id = Utils.generateId();


            if (validateText() && id.startsWith("set")) {
                User user = new User(id, name, email, phone_number, address);
                if (!confirmId(id)) {
                    user.setId(Utils.generateId());
                    saveUserDetails(user);
                } else {
                    JOptionPane.showMessageDialog(null, "User already registered..");
                    mFieldId.setText(Utils.generateId());
                    clearText();
                }
            } else {
                System.out.println("error");
            }

        } else if (source == mJButtonCheck) {
            mFieldId.setEditable(true);

            User user = searchUser(id);
            try {

                if (user != null && !user.getId().equals("")) {

                    mFieldId.setText(user.getId());
                    mFieldName.setText(user.getName());
                    mFieldEmail.setText(user.getEmail());
                    mFieldPhoneNumber.setText(user.getPhone_number());
                    mFieldResidentAddress.setText(user.getResident_address());
                    mJButtonUpdate.setEnabled(true);
                    mJButtonDelete.setEnabled(true);
                    mJButtonRead.setEnabled(true);

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No record for this user...\n search with phone number");
                clearText();
                mFieldId.setText("");
            }


        } else if (source == mJButtonUpdate) {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);
            user.setPhone_number(phone_number);
            user.setResident_address(address);
            if (validateText() && confirmBaseId(user)) {
                updateUser(user);
            }

        } else if (source == mJButtonDelete) {
            deleteUser(id);
        } else if (source == mJButtonRead) {
            setVisible(false);
            DisplayUSers displayUSers = new DisplayUSers(mConnection);
            displayUSers.setLocationRelativeTo(null);
            displayUSers.setVisible(true);


        } else if (source == mJButtonExit) {
            System.exit(0);
        }
    }


    private boolean validateText() {
        boolean flag = true;
        if (mFieldName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name can't be empty..");
            flag = false;
        } else if (mFieldEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email can't be empty..");
            flag = false;
        } else if (mFieldPhoneNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone number can't be empty..");
            flag = false;
        } else if (mFieldPhoneNumber.getText().length() != 11) {
            JOptionPane.showMessageDialog(null, "Invalid phone number, must be 11 digits..");
            flag = false;
        } else if (mFieldResidentAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Resident address can't be empty..");
            flag = false;
        }
        return flag;
    }

    //search user
    private User searchUser(String id) {
        ResultSet resultSet = null;
        try {
            //  System.out.println(id);
            resultSet = mConnection.getStatement().executeQuery("select * from users where phone_number ='" + id + "' ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

        }

        User user = new User();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());
            }
            String userID, userEmail, userPhone;
            try {
                userID = resultSet.getString("id_number");
                userEmail = resultSet.getString("email_address");
                userPhone = resultSet.getString("phone_number");
                if (userID.equals(mFieldId.getText()) || userEmail.equalsIgnoreCase(mFieldId.getText().toString()) || userPhone.equalsIgnoreCase(mFieldId.getText())) {
                    JOptionPane.showMessageDialog(null, "Details found for this user...");
                    user.setId(resultSet.getString("id_number"));
                    user.setName(resultSet.getString("full_name"));
                    user.setEmail(resultSet.getString("email_address"));
                    user.setPhone_number(resultSet.getString("phone_number"));
                    user.setResident_address(resultSet.getString("resident_address"));
                    mFieldId.setEditable(false);

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

            }
        }
        return user;

    }

    //save to database.....
    private void saveUserDetails(User user) {
        try {
            PreparedStatement preparedStatement = mConnection.getConnection()
                    .prepareStatement("INSERT INTO users(id_number,full_name,email_address,phone_number,resident_address)VALUES(?,?,?,?,?)");

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone_number());
            preparedStatement.setString(5, user.getResident_address());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User details save successfully...\nYour id is " + user.getId());
            clearText();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System error " + e.getMessage());
        } finally {
            // mConnection.close;
        }


    }

    private boolean confirmBaseId(User user) {
        boolean flag = true;
        ResultSet resultSet = null;
        String id = user.getId();
        try {
            resultSet = mConnection.getStatement().executeQuery("select * from users where id_number ='" + id + "'");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

        }

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());
            }
            String userID;
            String name;
            String address;
            String email;
            String phone_number;
            try {


                email = resultSet.getString("email_address");
                phone_number = resultSet.getString("phone_number");
                if (email.equals(user.getEmail()) || phone_number.equals(user.getPhone_number())) {
                    flag = false;
                    JOptionPane.showMessageDialog(null, "User with id " + user.getId() + " already exist!!!");

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

            }
        }
        return flag;
    }

    private boolean confirmId(String id) {
        boolean flag = false;

        ResultSet resultSet = null;
        try {
            resultSet = mConnection.getStatement().executeQuery("select * from users where id_number ='" + id + "'");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

        }

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());
            }
            String userID;
            try {
                userID = resultSet.getString("id_number");
                if (userID.equals(mFieldId.getText())) {
                    flag = true;

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

            }
        }


        return flag;
    }

    private void clearText() {
        mFieldName.setText("");
        mFieldEmail.setText("");
        mFieldPhoneNumber.setText("");
        mFieldResidentAddress.setText("");
        mFieldId.setText("");
        mFieldId.setText(Utils.generateId());
    }

    private void deleteUser(String id) {
        String idFromDatabase = idFromDatabase(id);
        try {
            if (!idFromDatabase(id).equals("")) {
                PreparedStatement preparedStatement =
                        mConnection.getConnection().prepareStatement("Delete  from users where id_number ='" + id + "' ");
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "user deleted successfully...");
                clearText();

            } else {
                JOptionPane.showMessageDialog(null, "No data for specified user..\n1. enter phone number" +
                        "\n2.click search icon.\n3.click delete button to delete");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // JOptionPane.showOptionDialog(null,"are you sure, you want to delete  the user?","Delete User");

    }

    private void updateUser(User user) {
        String update1 = "Update users set full_name = '" + user.getName() + "' where id_number = '" + user.getId() + "'";
        String update2 = "Update users set email_address = '" + user.getEmail() + "' where id_number = '" + user.getId() + "'";
        String update3 = "Update users set phone_number = '" + user.getPhone_number() + "' where id_number = '" + user.getId() + "'";
        String update4 = "Update users set resident_address = '" + user.getResident_address() + "' where id_number = '" + user.getId() + "'";


        Statement st;
        try {
            st = mConnection.getConnection().createStatement();
            st.executeUpdate(update1);
            st.executeUpdate(update2);
            st.executeUpdate(update3);
            st.executeUpdate(update4);

            JOptionPane.showMessageDialog(null, "Update Finished!");
            st.close();
            clearText();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String idFromDatabase(String id) {
        String userID = "";
        ResultSet resultSet = null;
        try {
            resultSet = mConnection.getStatement().executeQuery("select * from users where id_number ='" + id + "'");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

        }

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());
            }

            try {
                userID = resultSet.getString("id_number");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "System Error " + e.getMessage());

            }
        }


        return userID;
    }

    private void disableAllComponents() {
        mJButtonExit.setVisible(false);
        mJButtonRead.setVisible(false);
        mJButtonDelete.setVisible(false);
        mJButtonUpdate.setVisible(false);
        mJButtonCheck.setVisible(false);
        mJButtonSave.setVisible(false);

        mLabelResidentAddress.setVisible(false);
        mLabelPhoneNumber.setVisible(false);
        mLabelEmail.setVisible(false);
        mLabelName.setVisible(false);
        mLabelId.setVisible(false);


        mFieldId.setVisible(false);
        mFieldResidentAddress.setVisible(false);
        mFieldEmail.setVisible(false);
        mFieldPhoneNumber.setVisible(false);
        mFieldName.setVisible(false);

        mFieldResidentAddress.setVisible(false);
        addressScrollPane.setVisible(false);


    }

    private void enableAllComponents() {
        mJButtonExit.setVisible(true);
        mJButtonRead.setVisible(true);
        mJButtonDelete.setVisible(true);
        mJButtonUpdate.setVisible(true);
        mJButtonCheck.setVisible(true);
        mJButtonSave.setVisible(true);

        mLabelResidentAddress.setVisible(true);
        mLabelPhoneNumber.setVisible(true);
        mLabelEmail.setVisible(true);
        mLabelName.setVisible(true);
        mLabelId.setVisible(true);


        mFieldId.setVisible(true);
        mFieldResidentAddress.setVisible(true);
        mFieldEmail.setVisible(true);
        mFieldPhoneNumber.setVisible(true);
        mFieldName.setVisible(true);

        mFieldResidentAddress.setVisible(true);
        addressScrollPane.setVisible(true);


    }

    private ArrayList<User> getInfo() {
        ArrayList<User> arrayList = new ArrayList<>();

        try {
            String query = "SELECT * FROM users";
            ResultSet resultSet = mConnection.getStatement().executeQuery(query);
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getString("id_number"));
                user.setName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email_address"));
                user.setPhone_number(resultSet.getString("phone_number"));
                user.setResident_address(resultSet.getString("resident_address"));

                arrayList.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return arrayList;
    }


    public static void main(String[] args) {
        UserView userView = new UserView();
        userView.setVisible(true);

    }
}