package utils;

import model.User;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final static int COLUMN_COUNT = 9;
    private List<User> mUsers;
    private final static String[] COLUMNS = { "id_number", "full_name", "email_address",
            "phone_number", "resident_address", "state", "age", "gender", "salary"};


    public UserTableModel(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public int getRowCount() {
        return mUsers.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User student = mUsers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return student.getId();
            case 1:
                return student.getName();
            case 2:
                return student.getEmail();
            case 3:
                return student.getPhone_number();
            case 4:
                return student.getResident_address();
            case 5:
                return student.getState();
            case 6:
                return student.getAge();
            case 7:
                return student.getGender();
            case 8:
                return student.getSalary();

        }
        return null;
    }


}