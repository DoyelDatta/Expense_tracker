package com.dao;

import org.hibernate.query.Query;
import org.hibernate.*;
import com.entity.User;
import java.util.List;

import javax.servlet.ServletRequest;

public class UserDao {

	private SessionFactory factory = null;
	private Session session = null;
	private Transaction tx = null;

	public UserDao(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	public boolean saveuser(User user) {
		boolean f = false;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			session.save(user);
			tx.commit();
			f = true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				f = false;
				e.printStackTrace();
			}
		}
		return f;
	}
	 public User login(String email, String password) {
	        User u = null;
	        try {
	            session = factory.openSession();
	            Query<User> q = session.createQuery("from User where email = :em and password = :ps", User.class);
	            q.setParameter("em", email);
	            q.setParameter("ps", password);
	            List<User> results = q.getResultList();
	            
	            if (!results.isEmpty()) {
	                u = results.get(0);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	        return u;
	}
}