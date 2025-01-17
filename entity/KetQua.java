package entity;

public class KetQua {
    private String maKetQua;
    private String maThiSinh;
    private String maMonThi;
    private float diem;
    private int lanThi;

    public KetQua(String maKetQua, String maThiSinh, String maMonThi, float diem, int lanThi) {
        this.maKetQua = maKetQua;
        this.setMaThiSinh(maThiSinh);
        this.setMaMonThi(maMonThi);
        this.setDiem(diem);
        this.setLanThi(lanThi);
    }

    public String getMaKetQua() {
        return maKetQua;
    }

    public void setMaKetQua(String maKetQua) {
        this.maKetQua = maKetQua;
    }

	public String getMaThiSinh() {
		return maThiSinh;
	}

	public void setMaThiSinh(String maThiSinh) {
		this.maThiSinh = maThiSinh;
	}

	public String getMaMonThi() {
		return maMonThi;
	}

	public void setMaMonThi(String maMonThi) {
		this.maMonThi = maMonThi;
	}

	public float getDiem() {
		return diem;
	}

	public void setDiem(float diem) {
		this.diem = diem;
	}

	public int getLanThi() {
		return lanThi;
	}

	public void setLanThi(int lanThi) {
		this.lanThi = lanThi;
	}

}
