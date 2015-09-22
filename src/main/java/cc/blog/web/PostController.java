package cc.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.Post;
import cc.blog.service.PostService;


@RestController
public class PostController {

	@Autowired
	private PostService service;
	
	@RequestMapping(value="/add/post", method=RequestMethod.POST)
	public Long addPost(Post psot) {
		return service.addPost(psot);
	}
	
	@RequestMapping(value="/view/post/id/{postId}", method=RequestMethod.GET)
	public Post viewPost(@PathVariable(value="postId") Long postId) {
		return service.findPostById(postId);
	}
	
	@RequestMapping(value="/remove/post/id/{postId}")
	public void removePost(@PathVariable(value="postId") Long postId) {
		service.removePostById(postId);
	}
	
	@RequestMapping(value="/list/posts")
	public List<Post> listPosts() {
		return service.findAllPosts();
	}
}
