package cc.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cc.blog.model.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	public void save(Member member) {
		if (member.getId() == null) {
			this.em.persist(member);
		} else {
			this.em.merge(member);
		}
	}
}
