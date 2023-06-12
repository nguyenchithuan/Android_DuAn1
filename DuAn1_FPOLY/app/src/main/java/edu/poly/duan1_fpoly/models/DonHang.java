package edu.poly.duan1_fpoly.models;

import java.util.ArrayList;

public class DonHang {
    private int id;
    private int iduser;
    private String diaChi;
    private String soDienThoai;
    private int soluong;
    private long tongTien;
    private ArrayList<Item> list;

    public DonHang() {
    }

    public DonHang(int id, int iduser, String diaChi, String soDienThoai, int soluong, long tongTien, ArrayList<Item> list) {
        this.id = id;
        this.iduser = iduser;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.soluong = soluong;
        this.tongTien = tongTien;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public ArrayList<Item> getList() {
        return list;
    }

    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
