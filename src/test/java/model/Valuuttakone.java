package model;

public class Valuuttakone implements IValuuttakone {
	static ValuuttaAccessObject valuuttaDAO = new ValuuttaAccessObject();
	private Valuutta valuutat[] = valuuttaDAO.readValuutat();

	@Override
	public String[] getVaihtoehdot() {
		String palautus[] = new String[this.valuutat.length];
		int i = 0;
		for (Valuutta valuutta : this.valuutat) {
			palautus[i] = valuutta.getNimi();
			i++;
		}
		return palautus;
	}

	@Override
	public double muunna(int mistäIndeksi, int mihinIndeksi, double määrä) {
		double mistäKerroin = this.valuutat[mistäIndeksi].getVaihtokurssi();
		double mihinKerroin = this.valuutat[mihinIndeksi].getVaihtokurssi();
		määrä = määrä / mistäKerroin;
		määrä = määrä * mihinKerroin;
		return määrä;
	}

}
