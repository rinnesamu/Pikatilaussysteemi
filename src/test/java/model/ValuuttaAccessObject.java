package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@SuppressWarnings("null")

public class ValuuttaAccessObject implements IValuuttaDAO {

	private SessionFactory istuntotehdas = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	
	public ValuuttaAccessObject() {
		try {
			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}catch(Exception e) {
			System.out.println("Istuntotehtaan luonti epäonnistui");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	@Override
	public boolean createValuutta(Valuutta valuutta) {
		
		Transaction transaktio = null;
		try(Session istunto = istuntotehdas.openSession()){
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(valuutta);
			transaktio.commit();
			
			
		} catch(Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			return false;
		}
		return true;
	}

	@Override
	public Valuutta readValuutta(String tunnus) {
		Valuutta valuutta = null;
		Transaction transaktio = null;
		try(Session istunto = istuntotehdas.openSession()){
			transaktio = istunto.beginTransaction();
			valuutta = istunto.get(Valuutta.class, tunnus);
			transaktio.commit();
			
		} catch(Exception e) {
			if (transaktio != null) transaktio.rollback();
		}
		// TODO Auto-generated method stub
		return valuutta;
	}

	@Override
	public Valuutta[] readValuutat() {
		List<Valuutta> valuutat;
		Transaction transaktio = null;
		try(Session istunto = istuntotehdas.openSession()){
			transaktio = istunto.beginTransaction();

			valuutat = istunto.createQuery("from Valuutta").getResultList();
			transaktio.commit();
			
			
		} catch(Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		Valuutta[] returnValuutat = new Valuutta[valuutat.size()];
		return  (Valuutta[])valuutat.toArray(returnValuutat);
	}

	@Override
	public boolean updateValuutta(Valuutta valuutta) {
		Transaction transaktio = null;
		try(Session istunto = istuntotehdas.openSession()){
			transaktio = istunto.beginTransaction();

			
			istunto.update(valuutta);
			transaktio.commit();
			
			
		} catch(Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteValuutta(String tunnus) {
		if (readValuutta(tunnus) == null) {
			return false;
		}
		Transaction transaktio = null;
		try(Session istunto = istuntotehdas.openSession()){
			transaktio = istunto.beginTransaction();
			
			istunto.delete(readValuutta(tunnus));
			transaktio.commit();
			
			
		} catch(Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			return false;
		}
		return true;
	}
	
}
