package edu.poly.duan1.model;

public class GioHang {
    private int id;
    private String tenSp;
    private long giaSp;
    private String hinhSp;
    private int soLuong;

    public GioHang() {

    }

    public GioHang(int id, String tenSp, long giaSp, String hinhSp, int soLuong) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.hinhSp = hinhSp;
        this.soLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public long getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(long giaSp) {
        this.giaSp = giaSp;
    }

    public String getHinhSp() {
        return hinhSp;
    }

    public void setHinhSp(String hinhSp) {
        this.hinhSp = hinhSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
