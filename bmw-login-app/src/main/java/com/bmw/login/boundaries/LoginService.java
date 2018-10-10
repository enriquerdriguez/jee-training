package com.bmw.login.boundaries;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bmw.login.entity.User;
import com.bmw.login.exceptions.AuthNotFoundException;
import com.bmw.login.exceptions.UserNotFoundException;
import com.bmw.login.security.PasswordEncoded;

//@Named
@Stateless
public class LoginService {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Creates user and persist to DataBase
	 * @param user
	 * @return created User
	 */
	@PasswordEncoded
	public User createUser(User user) {
		
		this.em.persist(user);
		this.em.flush();
		this.em.refresh(user);
		return user;
	}
	
	/**
	 * Removes user from DataBase
	 * @param user
	 * @return Deleted User
	 */
	public User deleteUser(long id) {
		User removed = this.getUser(id); 
		this.em.remove(removed);
		return removed;
	}
	
	/**
	 * @param id
	 * @return User with id received
	 */
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public User getUser(long id) {
		User user = this.em.find(User.class, id);
		if(user == null) {
			throw new UserNotFoundException("User Not Found");
		}
		return user;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public User getByEmail(String email) {
		Query query = this.em.createNamedQuery("User.byEmail");
		query.setParameter("email", email);
		User user = (User) query.getSingleResult();
		if(user == null) {
			throw new UserNotFoundException("User Not Found");
		}
		return user;
	}
	
	/**
	 * @return List will all Users
	 */
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public List<User> getAll(){
		Query query = this.em.createNamedQuery("User.getAll");
		List <User> users = (List<User>) query.getResultList();
		if(users == null || users.isEmpty()) {
			return new LinkedList <>();
		}
		return users;
	}
	
	/**
	 * @param user
	 * @return Updated User
	 */
	@PasswordEncoded
	public User updateUser(User user) {
		User update = this.em.find(User.class, user.getId());
		update.updateUser(user);
		return user;
	}
	
	@PasswordEncoded
	public User validate(User user) {
		Query query = em.createNamedQuery("User.validation");
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		User userValidated = (User) query.getSingleResult();
		if(userValidated == null) {
			throw new AuthNotFoundException("User not authorized");
		}
		return userValidated;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
	public byte[] getUserSalt(String email) {
			Query query = this.em.createNamedQuery("User.SaltbyEmail");
			query.setParameter("email", email);
			try {
	            return (byte[]) query.getSingleResult();
	        }catch (NoResultException ex){
	            return null;
	        }
	}
	
}
