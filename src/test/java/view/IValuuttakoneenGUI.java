package view;
/**
 * @author silas
 */

public interface IValuuttakoneenGUI {
	public abstract int getLahtoindeksi();
	public abstract int getKohdeIndeksi();
	public abstract double getMäärä();
	public abstract void setTulos(double määrä);
}
