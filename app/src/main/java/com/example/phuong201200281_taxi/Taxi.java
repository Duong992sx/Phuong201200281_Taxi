package com.example.phuong201200281_taxi;

import java.io.Serializable;

public class Taxi implements Comparable<Taxi>, Serializable {
    private int id;
    private String soXe;
    private double quangDuong;
    private double donGia;
    private double khuyenMai;

    public Taxi() {
    }

    public Taxi(int id, String soXe, double quangDuong, double donGia, double khuyenMai) {
        this.id = id;
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.khuyenMai = khuyenMai;
    }
      public Taxi( String soXe, double quangDuong, double donGia, double khuyenMai) {
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.khuyenMai = khuyenMai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoXe() {
        return soXe;
    }

    public void setSoXe(String soXe) {
        this.soXe = soXe;
    }

    public double getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(double quangDuong) {
        this.quangDuong = quangDuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public double getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    @Override
    public int compareTo(Taxi o) {
        return this.soXe.compareTo(o.getSoXe());
    }
}
