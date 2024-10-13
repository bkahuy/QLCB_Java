/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectqlcb;

/**
 *
 * @author buikh
 */
public class Canbo {
    private String soTk, hoTen, gioiTinh, diaChi;
    private long Luong;

    public Canbo() {
    }

    public Canbo(String soTk, String hoTen, String gioiTinh, String diaChi, long Luong) {
        this.soTk = soTk;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.Luong = Luong;
    }

    public String getSoTk() {
        return soTk;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public long getLuong() {
        return Luong;
    }

    public void setSoTk(String soTk) {
        this.soTk = soTk;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setLuong(long Luong) {
        this.Luong = Luong;
    }
}
