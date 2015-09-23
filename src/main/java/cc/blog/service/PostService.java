package cc.blog.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.blog.model.Post;
import cc.blog.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	public Long addPost(final Post post) {
		final Date currentDate = Calendar.getInstance().getTime();
		post.setCreatedDate(currentDate);
		post.setLastModifiedDate(currentDate);
		repository.save(post);
		return post.getId();
	}
	
	public void updatePost(final Post updatePost) {
		if (updatePost == null || updatePost.getId() == null) {
			throw new IllegalStateException("Invalid Post entity.");
		}
		
		Post existPost = findPostById(updatePost.getId());
		if (existPost == null) {
			throw new IllegalStateException("Post entity not found, postId: " + updatePost.getId());
		}
		
		updatePost.setCreatedDate(existPost.getCreatedDate());
		updatePost.setLastModifiedDate(Calendar.getInstance().getTime());
		
		repository.update(updatePost);
	}
	
	public Post findPostById(final Long postId) {
		return repository.findOne(postId);
	}
	
	public void removePostById(final Long postId) {
		repository.remove(postId);
	}
	
	public List<Post> findAllPosts() {
		return repository.findAll();
	}
}
