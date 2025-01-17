package entity;

public class ThiSinh {
    private String maThiSinh;
    private String hoTen;
    private String lop;
    private String diaChi;
    private String soDienThoai;
    private String Gmail;
    private String gioiTinh;
    private String ngaySinh;

    public ThiSinh(String maThiSinh, String hoTen,String ngaySinh ,String gioiTinh , String lop,String soDienThoai,  String Gmail , String diaChi ) {
        this.maThiSinh = maThiSinh;
        this.hoTen = hoTen;
        this.lop = lop;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.Gmail = Gmail;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    
    public String getMaThiSinh() {
        return maThiSinh;
    }

    public void setMaThiSinh(String maThiSinh) {
        this.maThiSinh = maThiSinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
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

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String Gmail) {
        this.Gmail = Gmail;
    }
}

