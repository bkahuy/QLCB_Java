/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectqlcb;
import java.sql.*;
/**
 *
 * @author buikh
 */
public class QLCB {
    private static Connection cn;
//    kết nối sql
    public static void getCon(){
        try {
            cn = DriverManager.getConnection("jdbc:sqlserver://BKAHUYYYYY;database=QLCB;user=sa;password=1;trustServerCertificate=true;");
            System.out.println("pass to load database");
        } catch (SQLException e) {
            System.out.println("failed to load database" + e.getMessage());
        }
    }
//    lấy tất cả dữ liệu trong database
    public static ResultSet getAllData(){
        try {
            Statement st = cn.createStatement();
            return st.executeQuery("select * from tbCanbo");
        } catch (SQLException e) {
            System.out.println("Failed "+ e.getMessage());
            return null;
        }
    }
//    lấy dữ liệu theo số tk
    public static ResultSet getData(String SoTk){
        try {
            PreparedStatement pst = cn.prepareStatement("select * from tbCanbo where SoTK='" + SoTk + "'");
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed " + e.getMessage());
            return null;
        }
    }
//    them
    public static boolean insertCB(Canbo cb){
        try {
            if(!checkAccount(cb.getSoTk())){
                PreparedStatement pst = cn.prepareStatement("insert into tbCanbo (SoTK, Hoten, GT, Diachi, Luong) values (?, ?, ?, ?, ?)");
                pst.setString(1, cb.getSoTk());
                pst.setString(2, cb.getHoTen());
                pst.setString(3, cb.getGioiTinh());
                pst.setString(4, cb.getDiaChi());
                pst.setLong(5, cb.getLuong());
                int res = pst.executeUpdate();
                return res > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return false;
    }
//    Sua
    public static boolean editCB(String Sotk, Canbo cb){
        try {
            PreparedStatement pst = cn.prepareStatement("update tbCanbo set Hoten = N'" + cb.getHoTen() + "', GT = N'" + cb.getGioiTinh() + "', Diachi = N'" + cb.getDiaChi() + "', Luong = '" + cb.getLuong() + "' where SoTK = '" + Sotk + "'");
            int res = pst.executeUpdate();
            return res>0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
//    Xoa
    public static boolean deleteCB(String Sotk){
        try {
            PreparedStatement pst = cn.prepareStatement("delete from tbCanbo where SoTK = '" + Sotk + "'");
            int res = pst.executeUpdate();
            return res>0;
        } catch (SQLException e) {
            System.out.println("Error: "+ e.getMessage());
            return false;
        }
    }
//    Validate sotk
    public static boolean checkAccount(String stk){
        try {
            PreparedStatement pst = cn.prepareStatement("select * from tbCanbo where SoTK = ?");
            pst.setString(1, stk);
            ResultSet result = pst.executeQuery();
            return result.next();
        } catch (SQLException e) {
            System.out.println("Error checking STK " + e.getMessage());
            return false;
        }
    }
    
}
