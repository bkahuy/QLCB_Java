package projectqlcb;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import projectqlcb.Canbo;
import projectqlcb.QLCB;
import java.sql.*;

/**
 *
 * @author buikh
 */
public class GuiInsertCB extends JFrame implements MouseListener, ActionListener {

    private JTextField tfsotk;
    private JTextField tften;
    private JRadioButton rbNam;
    private JRadioButton rbNu;
    private JTextField tfdiachi;
    private JTextField tfluong;
    private JTable tb;
    private DefaultTableModel dfModel;
    private JButton btAdd;
    private JButton btEdit;
    private JButton btDelete;
    private JButton btSearch;

    public GuiInsertCB() {
        setTitle("CHUONG TRINH QUAN LY CAN BO");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BuildGui();
        loadData(dfModel);
    }

    private void BuildGui() {
        JPanel pnLeft = new JPanel();
        pnLeft.setLayout(new BoxLayout(pnLeft, BoxLayout.Y_AXIS));
        pnLeft.setBorder(new EmptyBorder(20, 20, 20, 20));

//        thong tin can bo 
        JLabel lbInf = new JLabel("Thong tin can bo", JLabel.CENTER);
        lbInf.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnLeft.add(lbInf);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 20)));

//        so tai khoan
        JLabel lbsotk = new JLabel("So tai khoan: ");
        lbsotk.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfsotk = new JTextField();
        pnLeft.add(lbsotk);
        pnLeft.add(tfsotk);
        tfsotk.setMaximumSize(new Dimension(300, 30));
//        ho ten
        JLabel lbName = new JLabel("Họ và tên: ");
        lbName.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnLeft.add(lbName);
        tften = new JTextField();
        tften.setMaximumSize(new Dimension(300, 30));
        pnLeft.add(tften);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));
//        gioi tinh
        JPanel pnGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnGender.setMaximumSize(new Dimension(300, 40));
        ButtonGroup bgGender = new ButtonGroup();
        pnGender.add(new JLabel("Giới tính: "));
        rbNam = new JRadioButton("Nam");
        rbNu = new JRadioButton("Nữ");
        bgGender.add(rbNam);
        bgGender.add(rbNu);
        pnGender.add(rbNam);
        pnGender.add(rbNu);
        pnLeft.add(pnGender);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Địa chỉ
        JLabel lbAddress = new JLabel("Địa chỉ: ");
        lbAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnLeft.add(lbAddress);
        tfdiachi = new JTextField();
        tfdiachi.setMaximumSize(new Dimension(300, 30));
        pnLeft.add(tfdiachi);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 10)));

        // Lương
        JLabel lbSalary = new JLabel("Lương: ");
        lbSalary.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnLeft.add(lbSalary);
        tfluong = new JTextField();
        tfluong.setMaximumSize(new Dimension(300, 30));
        pnLeft.add(tfluong);
        pnLeft.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JPanel pnLeftBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btAdd = new JButton("Thêm");
        btEdit = new JButton("Sửa");
        btDelete = new JButton("Xóa");
        btSearch = new JButton("Tìm kiếm");
        pnLeftBottom.add(btAdd);
        pnLeftBottom.add(btEdit);
        pnLeftBottom.add(btDelete);
        pnLeftBottom.add(btSearch);
        pnLeft.add(pnLeftBottom);

        // Table
        JPanel pnRight = new JPanel(new GridLayout(1, 1));
        String[] headers = {"Số tài khoản", "Họ tên", "Giới tính", "Địa chỉ", "Lương"};
        dfModel = new DefaultTableModel(headers, 0);
        tb = new JTable(dfModel);
        pnRight.add(new JScrollPane(tb));

        this.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight));

        //Bắt sự kiện kích chọn trong bảng
        tb.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selectedRow = tb.getSelectedRow();
                if (selectedRow != -1) {
                    tfsotk.setText(tb.getValueAt(selectedRow, 0).toString());
                    tften.setText(tb.getValueAt(selectedRow, 1).toString());
                    String gender = tb.getValueAt(selectedRow, 2).toString();
                    if (gender.equals("Nam")) {
                        rbNam.setSelected(true);  // Tích chọn radio button cho nam
                        rbNu.setSelected(false);   // Bỏ chọn radio button cho nữ nếu có
                    } else if (gender.equals("Nữ")) {
                        rbNu.setSelected(true);    // Tích chọn radio button cho nữ
                        rbNam.setSelected(false);   // Bỏ chọn radio button cho nam nếu có
                    }
                    tfdiachi.setText(tb.getValueAt(selectedRow, 3).toString());
                    tfluong.setText(tb.getValueAt(selectedRow, 4).toString());
                }

            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        });
        //Bắt sự kiện thêm mới
        btAdd.addActionListener((var e) -> {
            String accountNumber = tfsotk.getText().trim();
            String name = tften.getText().trim();
            String gender = "";

            if (rbNam.isSelected()) {
                gender = rbNam.getText();
            } else if (rbNu.isSelected()) {
                gender = rbNu.getText();
            }

            String address = tfdiachi.getText().trim();
            int salary = 0;
            boolean isValid = true;

            try {
                salary = Integer.parseInt(tfluong.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Nhập lương không hợp lệ!" + ex.getMessage());
                isValid = false;
            }

            if (accountNumber.isEmpty() || name.isEmpty() || !isValid) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin cán bộ!");
            } else {
                if (QLCB.checkAccount(accountNumber)) {
                    JOptionPane.showMessageDialog(null, "Số tài khoản đã tồn tại!");
                } else {
                    boolean res = QLCB.insertCB(new Canbo(accountNumber, name, gender, address, salary));
                    if (res) {
                        loadData(dfModel);
                        JOptionPane.showMessageDialog(null, "Thêm cán bộ thành công!");
                        tfsotk.setText("");
                        tften.setText("");
                        bgGender.clearSelection();
                        tfdiachi.setText("");
                        tfluong.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm cán bộ thất bại!");
                    }
                }
            }
        });

        //Bắt sự kiện btn sửa
        btEdit.addActionListener(e -> {
            String sotk = tfsotk.getText().trim();
            String ten = tften.getText().trim();
            String gioitinh = "";

            if (rbNam.isSelected()) {
                gioitinh = rbNam.getText();
            } else if (rbNu.isSelected()) {
                gioitinh = rbNu.getText();
            }

            String diachi = tfdiachi.getText().trim();
            int luong = 0;
            boolean isValid = true;

            ResultSet resAccountNummber = QLCB.getData(sotk);

            try {
                if (resAccountNummber != null && resAccountNummber.next()) {
                    try {
                        luong = Integer.parseInt(tfluong.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Nhập lương không hợp lệ!\n" + ex.getMessage());
                        isValid = false;
                    }

                    if (sotk.isEmpty() || ten.isEmpty() || !isValid) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập lại thông tin cán bộ!");
                    } else {
                        boolean res = QLCB.editCB(sotk, new Canbo(sotk, ten, gioitinh, diachi, luong));
                        if (res) {
                            loadData(dfModel);
                            JOptionPane.showMessageDialog(null, "Sửa cán bộ thành công!");
                            tfsotk.setText("");
                            tften.setText("");
                            bgGender.clearSelection();
                            tfdiachi.setText("");
                            tfluong.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Không tồn tại mã cán bộ: " + sotk);
                }
            } catch (HeadlessException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error");
            }
        });
//        bắt sự kiện xóa
        btDelete.addActionListener((e) -> {
            String sotk = tfsotk.getText().trim();
            if (sotk.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Chon mot can bo de xoa");
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "ban co chac chan muon xoa?", "xac nhan", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean res = QLCB.deleteCB(sotk);
                    if (res) {
                        loadData(dfModel);
                        JOptionPane.showMessageDialog(null, "xoa thanh cong");
                        tften.setText("");
                        tfsotk.setText("");
                        bgGender.clearSelection();
                        tfdiachi.setText("");
                        tfluong.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "xoa that bai!");
                    }
                }
            }
        });
    }

    private void loadData(DefaultTableModel dfModel) {
        try {
            ResultSet res = QLCB.getAllData();
            dfModel.setRowCount(0);
            dfModel.fireTableDataChanged();
            if (res != null) {
                while (res.next()) {
                    dfModel.addRow(new String[]{
                        res.getString("SoTK"),
                        res.getString("Hoten"),
                        res.getString("GT"),
                        res.getString("Diachi"),
                        res.getString("Luong")
                    });
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        QLCB.getCon();
        SwingUtilities.invokeLater(() -> {
            new GuiInsertCB().setVisible(true);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
