package cc.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cc.blog.model.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void save(final Member member) {
		em.persist(member);
	}
	
	public Member findOne(final Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}
	
	public void remove(final Member member) {
		em.remove(member);
	}
}