package model;
import javax.persistence.*;

/**
 * @author Samu Rinne
 */
 
@Entity
@Table(name = "valuutat")
public class Valuutta {
	@Id
	@Column
	private String tunnus;
	@Column
	private String nimi;
	@Column
	private double vaihtokurssi;
	
	public Valuutta() {};
	
	public Valuutta(String tunnus, double vaihtokurssi, String nimi) {
		this.nimi = nimi;
		this.vaihtokurssi = vaihtokurssi;
		this.tunnus = tunnus;
		
	}

	public String getTunnus() {
		return tunnus;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public double getVaihtokurssi() {
		return vaihtokurssi;
	}

	public void setVaihtokurssi(double vaihtokurssi) {
		this.vaihtokurssi = vaihtokurssi;
	}
	
	@Override
	public String toString() {
		return this.tunnus + ": " + this.vaihtokurssi;
	}
	
}
