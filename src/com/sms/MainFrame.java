package com.sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {
    private StudentDAO dao = new StudentDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    // input fields
    private JTextField tfId = new JTextField(5);
    private JTextField tfRoll = new JTextField(10);
    private JTextField tfFirst = new JTextField(12);
    private JTextField tfLast = new JTextField(12);
    private JTextField tfDob = new JTextField(10); // format yyyy-MM-dd
    private JComboBox<String> cbGender = new JComboBox<>(new String[]{"Male","Female","Other"});
    private JTextField tfEmail = new JTextField(15);
    private JTextField tfMobile = new JTextField(12);
    private JTextField tfAddress = new JTextField(20);

    private JTextField tfSearch = new JTextField(15);

    public MainFrame() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        initUI();
        loadTableData();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;
        addToGrid(inputPanel, new JLabel("ID:"), 0, y); addToGrid(inputPanel, tfId, 1, y++);
        tfId.setEditable(false);

        addToGrid(inputPanel, new JLabel("Roll No:"), 0, y); addToGrid(inputPanel, tfRoll, 1, y++);
        addToGrid(inputPanel, new JLabel("First Name:"), 0, y); addToGrid(inputPanel, tfFirst, 1, y++);
        addToGrid(inputPanel, new JLabel("Last Name:"), 0, y); addToGrid(inputPanel, tfLast, 1, y++);
        addToGrid(inputPanel, new JLabel("DOB (yyyy-MM-dd):"), 0, y); addToGrid(inputPanel, tfDob, 1, y++);
        addToGrid(inputPanel, new JLabel("Gender:"), 0, y); addToGrid(inputPanel, cbGender, 1, y++);
        addToGrid(inputPanel, new JLabel("Email:"), 0, y); addToGrid(inputPanel, tfEmail, 1, y++);
        addToGrid(inputPanel, new JLabel("Mobile:"), 0, y); addToGrid(inputPanel, tfMobile, 1, y++);
        addToGrid(inputPanel, new JLabel("Address:"), 0, y); addToGrid(inputPanel, tfAddress, 1, y++);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        btnPanel.add(btnAdd); btnPanel.add(btnUpdate); btnPanel.add(btnDelete); btnPanel.add(btnClear);

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());

        JPanel left = new JPanel(new BorderLayout());
        left.add(inputPanel, BorderLayout.NORTH);
        left.add(btnPanel, BorderLayout.SOUTH);

        // Right side - table and search
        JPanel right = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID","Roll No","First","Last","DOB","Gender","Email","Mobile","Address"}, 0) {
            public boolean isCellEditable(int row, int col){ return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sc = new JScrollPane(table);
        right.add(sc, BorderLayout.CENTER);

        JPanel topSearch = new JPanel();
        JButton btnSearch = new JButton("Search");
        JButton btnRefresh = new JButton("Refresh");
        topSearch.add(new JLabel("Search:"));
        topSearch.add(tfSearch);
        topSearch.add(btnSearch);
        topSearch.add(btnRefresh);
        right.add(topSearch, BorderLayout.NORTH);

        btnSearch.addActionListener(e -> search());
        btnRefresh.addActionListener(e -> loadTableData());

        // table click -> populate form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    tfId.setText(tableModel.getValueAt(r,0).toString());
                    tfRoll.setText((String)tableModel.getValueAt(r,1));
                    tfFirst.setText((String)tableModel.getValueAt(r,2));
                    tfLast.setText((String)tableModel.getValueAt(r,3));
                    tfDob.setText((tableModel.getValueAt(r,4) == null) ? "" : tableModel.getValueAt(r,4).toString());
                    cbGender.setSelectedItem(tableModel.getValueAt(r,5));
                    tfEmail.setText((String)tableModel.getValueAt(r,6));
                    tfMobile.setText((String)tableModel.getValueAt(r,7));
                    tfAddress.setText((String)tableModel.getValueAt(r,8));
                }
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(360);
        add(split, BorderLayout.CENTER);
    }

    private void addToGrid(JPanel p, Component c, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;
        if (c instanceof JTextField || c instanceof JComboBox) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }
        p.add(c, gbc);
    }

    private void addStudent() {
        try {
            Student s = readForm(false);
            dao.addStudent(s);
            JOptionPane.showMessageDialog(this, "Added successfully. ID: " + s.getId());
            loadTableData();
            clearForm();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void updateStudent() {
        try {
            Student s = readForm(true);
            dao.updateStudent(s);
            JOptionPane.showMessageDialog(this, "Updated successfully.");
            loadTableData();
            clearForm();
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void deleteStudent() {
        try {
            String idStr = tfId.getText();
            if (idStr == null || idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }
            int id = Integer.parseInt(idStr);
            int ok = JOptionPane.showConfirmDialog(this, "Delete selected student?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                dao.deleteStudent(id);
                loadTableData();
                clearForm();
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void search() {
        try {
            String q = tfSearch.getText().trim();
            List<Student> list = q.isEmpty() ? dao.findAll() : dao.searchByNameOrRoll(q);
            populateTable(list);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void loadTableData() {
        try {
            List<Student> list = dao.findAll();
            populateTable(list);
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void populateTable(List<Student> list) {
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getRollNo(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getDob() == null ? null : s.getDob().toString(),
                    s.getGender(),
                    s.getEmail(),
                    s.getMobile(),
                    s.getAddress()
            });
        }
    }

    private Student readForm(boolean forUpdate) {
        Student s = new Student();
        if (forUpdate) {
            String idStr = tfId.getText();
            if (idStr == null || idStr.isEmpty()) throw new IllegalArgumentException("ID is required for update");
            s.setId(Integer.parseInt(idStr));
        }
        s.setRollNo(tfRoll.getText().trim());
        s.setFirstName(tfFirst.getText().trim());
        s.setLastName(tfLast.getText().trim());
        String dobStr = tfDob.getText().trim();
        if (!dobStr.isEmpty()) s.setDob(LocalDate.parse(dobStr));
        s.setGender((String) cbGender.getSelectedItem());
        s.setEmail(tfEmail.getText().trim());
        s.setMobile(tfMobile.getText().trim());
        s.setAddress(tfAddress.getText().trim());
        return s;
    }

    private void clearForm() {
        tfId.setText("");
        tfRoll.setText("");
        tfFirst.setText("");
        tfLast.setText("");
        tfDob.setText("");
        cbGender.setSelectedIndex(0);
        tfEmail.setText("");
        tfMobile.setText("");
        tfAddress.setText("");
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
