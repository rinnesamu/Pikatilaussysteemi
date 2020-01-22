package controller;
import view.*;
import model.*;

public class ValuuttakoneenOhjain implements IValuuttakoneenOhjain {
	private IValuuttakoneenGUI valuuttakoneGUI;
	private IValuuttakone valuuttakone;
	
	public ValuuttakoneenOhjain(IValuuttakoneenGUI valuuttakoneGUI, IValuuttakone valuuttakone) {
		this.valuuttakoneGUI = valuuttakoneGUI;
		this.valuuttakone = valuuttakone;
	}
	

	@Override
	public void muunnos() {
		double uusiMäärä = this.valuuttakone.muunna(valuuttakoneGUI.getLahtoindeksi(), valuuttakoneGUI.getKohdeIndeksi(), valuuttakoneGUI.getMäärä());
		valuuttakoneGUI.setTulos(uusiMäärä);

	}

	@Override
	public String[] getValuutat() {
		return this.valuuttakone.getVaihtoehdot();
	}

}
