package cc.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cc.blog.model.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void save(Member member) {
		
	}
}
