package entity;

public class MonThi {
    private String maMH;
    private String tenMH;
    private String loaiMonThi;

    public MonThi(String maMH, String tenMH, String loaiMonThi) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.loaiMonThi = loaiMonThi;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getLoaiMonThi() {
        return loaiMonThi;
    }

    public void setLoaiMonThi(String loaiMonThi) {
        this.loaiMonThi = loaiMonThi;
    }
}
