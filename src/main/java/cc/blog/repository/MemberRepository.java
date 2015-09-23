package cc.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cc.blog.model.Member;

@Transactional
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
	
	public void update(final Member member) {
		em.merge(member);
	}

	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public void remove(final Long id) {
		em.remove(findOne(id));
	}
}
