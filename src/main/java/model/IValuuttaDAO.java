package model;

public interface IValuuttaDAO {
	boolean createValuutta(Valuutta valuutta);
	Valuutta readValuutta(String tunnus);
	Valuutta[] readValuutat();
	boolean updateValuutta(Valuutta valuutta);
	boolean deleteValuutta(String tunnus);

}
