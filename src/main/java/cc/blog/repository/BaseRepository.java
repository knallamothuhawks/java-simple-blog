package cc.blog.repository;

import java.util.List;

public interface BaseRepository<E, K> {

	public void save(final E entity);
	
	public E findOne(final K id);
	
	public List<E> findAll();
	
	public void remove(final K id);
}
