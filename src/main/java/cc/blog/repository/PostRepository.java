package cc.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cc.blog.model.Post;

@Transactional
@Repository
public class PostRepository {

	@PersistenceContext
	private EntityManager em;

	public void save(Post post) {
		em.persist(post);
	}

	public Post findOne(Long id) {
		return em.find(Post.class, id);
	}
	
	public void update(final Post post) {
		em.merge(post);
	}

	public List<Post> findAll() {
		return em.createQuery("select p from Post p", Post.class).getResultList();
	}

	public void remove(Long id) {
		em.remove(findOne(id));
	}

}
