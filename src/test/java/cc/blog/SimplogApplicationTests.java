package cc.blog;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cc.blog.model.Member;
import cc.blog.model.MemberRoleType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimplogApplication.class)
@WebAppConfiguration
public class SimplogApplicationTests {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;
	
	@Before
	public void setup() {
		emf = Persistence.createEntityManagerFactory("blog");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	@After
	public void tearDown() {
		em.close();
		emf.close();
	}
	
	@Test
	public void contextLoads() {
		try {
			tx.begin();

			Date currentDate = Calendar.getInstance().getTime();
			
			IntStream.range(1, 10)
					 .mapToObj((idx) -> {
						 String name = "test_name_" + idx;
						 String password = name + "!pass";
						 String email = name + "@email.com";
						 
						 return new Member(null, name, email, password, currentDate, MemberRoleType.ADMIN);
					 }).forEach((member) -> em.persist(member));
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
}
