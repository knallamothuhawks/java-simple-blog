package cc.blog.service;

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
		repository.save(post);
		return post.getId();
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
