package edu.poly.duan1_fpoly.models;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int id;
    private String tenSp;
    private int giaSp;
    private String hinhAnhSp;
    private String moTaSp;
    private int idLoaiSp;

    public SanPham() {
    }

    public SanPham(int id, String tenSp, int giaSp, String hinhAnhSp, String moTaSp, int idLoaiSp) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.hinhAnhSp = hinhAnhSp;
        this.moTaSp = moTaSp;
        this.idLoaiSp = idLoaiSp;
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

    public int getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(int giaSp) {
        this.giaSp = giaSp;
    }

    public String getHinhAnhSp() {
        return hinhAnhSp;
    }

    public void setHinhAnhSp(String hinhAnhSp) {
        this.hinhAnhSp = hinhAnhSp;
    }

    public String getMoTaSp() {
        return moTaSp;
    }

    public void setMoTaSp(String moTaSp) {
        this.moTaSp = moTaSp;
    }

    public int getIdLoaiSp() {
        return idLoaiSp;
    }

    public void setIdLoaiSp(int idLoaiSp) {
        this.idLoaiSp = idLoaiSp;
    }
}
