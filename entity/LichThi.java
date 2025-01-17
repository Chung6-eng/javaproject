package entity;

import java.util.Date;

public class LichThi {
    private String maLichThi;
    private String maMonThi;
    private String phongthi;
    private Date ngayThi; 

    public LichThi(String maLichThi, String maMonThi, String phongthi, Date ngayThi) {
        this.maLichThi = maLichThi;
        this.maMonThi = maMonThi;
        this.phongthi = phongthi;
        this.ngayThi = ngayThi;
    }

    public String getMaLichThi() {
        return maLichThi;
    }

    public void setMaLichThi(String maLichThi) {
        this.maLichThi = maLichThi;
    }

    public String getMaMonThi() {
        return maMonThi;
    }

    public void setMaMonThi(String maMonThi) {
        this.maMonThi = maMonThi;
    }

    public String getPhongthi() {
        return phongthi;
    }

    public void setPhongthi(String phongthi) {
        this.phongthi = phongthi;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    @Override
    public String toString() {
        return "Mã lịch thi: " + maLichThi + ", Mã môn thi: " + maMonThi + ", Phòng thi: " + phongthi + ", Ngày thi: " + ngayThi;
    }
}

