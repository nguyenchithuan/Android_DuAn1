package edu.poly.duan1.model;

public class LoaiSp {
    private int id;
    private String tenLoaiSp;
    private String hinhLoaiSp;

    public LoaiSp() {
    }

    public LoaiSp(int id, String tenLoaiSp, String hinhLoaiSp) {
        this.id = id;
        this.tenLoaiSp = tenLoaiSp;
        this.hinhLoaiSp = hinhLoaiSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSp() {
        return tenLoaiSp;
    }

    public void setTenLoaiSp(String tenLoaiSp) {
        this.tenLoaiSp = tenLoaiSp;
    }

    public String getHinhLoaiSp() {
        return hinhLoaiSp;
    }

    public void setHinhLoaiSp(String hinhLoaiSp) {
        this.hinhLoaiSp = hinhLoaiSp;
    }
}
