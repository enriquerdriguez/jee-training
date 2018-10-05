/**
 * 
 */
package com.bmw.login.boundaries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bmw.login.entity.User;
import com.bmw.login.exceptions.UserNotFoundException;

/**
 * @author erodrmed
 *
 */
public class LoginServiceTest {

	@InjectMocks
	LoginService loginService;
	
	@Mock 
	EntityManager em;
	
	@Mock
	Query query;
	
	@Mock
	User user;
	
	@Before
	public void inicializaMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public final void createUsertest() {
		//Given
        User user = new User();
        user.setEmail("prueba@gmail.com");
        user.setPassword("password");
        //When
        (em).persist(user);
//        User createdUser = loginService.createUser(user);
        verify(em).persist(user);
        //Then
        assertNotNull(user);
//        assertNotEquals(0, user.getId());
//        assertEquals(user.getEmail(), createdUser.getEmail());
//        assertEquals(user.getPassword(), createdUser.getPassword());
	}
	
	@Test
	public final void getAllUsersEMPTY() {
		//Given
		List<User> expected= new LinkedList <>();
		
		when(em.createNamedQuery("User.getAll")).thenReturn(query);
		when(query.getResultList()).thenReturn(expected);

		//When
		List<User> real = loginService.getAll();
		//Then
		assertEquals(0, real.size());
		assertNotNull(real);
	}
	
	@Test
	public final void getAllUsersNULL() {
		//Given
		
		when(em.createNamedQuery("User.getAll")).thenReturn(query);
		when(query.getResultList()).thenReturn(null);

		//When
		List<User> real = loginService.getAll();
		//Then
		assertEquals(0, real.size());
		assertNotNull(real);
	}
	
	@Test
	public final void getAllUsersOK() {
		//Given
		List<User> expected= new LinkedList <>();
		expected.add(new User());
		
		when(em.createNamedQuery("User.getAll")).thenReturn(query);
		when(query.getResultList()).thenReturn(expected);

		//When
		List<User> real = loginService.getAll();
		//Then
		assertEquals(1, real.size());
		assertEquals(expected.size(), real.size());
		assertNotNull(real);
		
	}	
	
	@Test
	public final void testDeleteUserWhenUserIsOK() {
		
		User user = new User();
		when(em.find(Matchers.anyObject(), Matchers.anyLong())).thenReturn(user);

        doNothing().when(em).remove(user);
        loginService.deleteUser(22L);
        verify(em).remove(user);
        
	}	
	
	@Test(expected = UserNotFoundException.class)
	public final void testDeleteUserWhenUserIsNULL() {
		User user = new User();
		when(em.find(Matchers.anyObject(), Matchers.anyLong())).thenReturn(null);

        doNothing().when(em).remove(user);
        loginService.deleteUser(22L);
        verify(em).remove(null);
        
	}
	
	@Test
	public final void testGetUserOK() {
		User u = new User();
				
		when(em.find(Matchers.anyObject(), Matchers.anyLong())).thenReturn(u);
		User user = loginService.getUser(88L);
		
		assertEquals(user ,u);
		assertNotNull(user);
	}
	
	@Test(expected = UserNotFoundException.class)
	public final void testGetUserNULL() {
		User u = null;
				
		when(em.find(Matchers.anyObject(), Matchers.anyLong())).thenReturn(null);
		User user = loginService.getUser(88L);
		
		assertEquals(user ,u);
		verify(loginService.getUser(88L));
	}
	
	@Test
	public final void testGetUserByEmailOK() {
		User expect = new User();
		expect.setEmail("asdf");
		
		when(em.createNamedQuery("User.byEmail")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(expect);
		
		User user = loginService.getByEmail("asdasdf");
		
		assertEquals(user ,expect);
		assertNotNull(user);
		assertEquals(user.getEmail(), expect.getEmail());
	}
	
	@Test(expected = UserNotFoundException.class)
	public final void testGetUserByEmailNULL() {
		User expect = null;
		
		when(em.createNamedQuery("User.byEmail")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(null);
		User user = loginService.getByEmail("asdasdf");
		
		assertEquals(user ,expect);
	}	
		

}
