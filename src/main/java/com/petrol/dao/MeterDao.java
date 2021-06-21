package com.petrol.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petrol.model.DiselCal;
import com.petrol.model.Meter;
import com.petrol.model.PetrolCal;

@Repository
public class MeterDao {
	private SessionFactory sessionFactory;
	@Autowired
	EntityManager entityManager;
	public List<Meter> getMeterOpenClose(String date) {
		Session session1 = getSession();
		List<Meter> list= session1.createQuery("from Meter where date= '"+date+"'").list();
		session1.close();
		return list;
				
	}
	public void saveMeterDetails(Meter meter) {
		
		Session session1 = getSession();
		Transaction tx = session1.beginTransaction();
		session1.saveOrUpdate(meter);
		tx.commit();
		session1.close();
				
	}
public int updateMeterDetails(Meter meter) {
		
		Session session1 = getSession();
		Transaction tx = session1.beginTransaction();
		Query query = session1.createQuery("update Meter set mClose = '" +meter.getmClose()+"'"+
				" where date = '"+meter.getDate()+"'"+ "AND mid = '"+meter.getMid()+"'");
		int result = query.executeUpdate();
		tx.commit();
		session1.close();
		return result;		
	}
	public void savePetlCalDetails(PetrolCal ptCal) {
		Session session1 = getSession();
		Transaction tx = session1.beginTransaction();
		session1.saveOrUpdate(ptCal);
		tx.commit();
		session1.close();
				
	}
	public void saveDsCalDetails(DiselCal dsCal) {
		Session session1 = getSession();
		Transaction tx = session1.beginTransaction();
		session1.saveOrUpdate(dsCal);
		tx.commit();
		session1.close();
				
	}
	public Session getSession(){
		Session session = entityManager.unwrap(Session.class);
	    sessionFactory = session.getSessionFactory(); 
		Session session1 = sessionFactory.openSession();
		return session1;
	}
	
	
}
