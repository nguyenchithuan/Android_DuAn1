package edu.poly.duan1_fpoly.models;

public class Item { // sản phẩm của đơn hàng
    // không cần hiển thị đơn hàng lên không lấy iddonhang
    private int idsp;
    private String tensp;
    private String hinhanh;
    private int soLuong; // có thêm số lượng mua hàng
    private int giaSp;

    public Item() {
    }

    public Item(int idsp, String tensp, String hinhanh, int soLuong, int giaSp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.soLuong = soLuong;
        this.giaSp = giaSp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }
}
