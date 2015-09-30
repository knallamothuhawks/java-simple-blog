package cc.blog.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.Post;
import cc.blog.model.PostDto;
import cc.blog.service.PostService;


@RestController
public class PostController {

	@Autowired
	private PostService service;
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	public ResponseEntity<?> addPost(@RequestBody @Valid PostDto.Create postDto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Post newPost = service.addPost(postDto);
		return new ResponseEntity<>(newPost, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/post", method=RequestMethod.PUT)
	public void updatePost(@RequestBody Post post) {
		service.updatePost(post);
	}
	
	@RequestMapping(value="/post/{postId}", method=RequestMethod.GET)
	public Post viewPost(@PathVariable(value="postId") Long postId) {
		return service.findPostById(postId);
	}
	
	@RequestMapping(value="/post/{postId}", method=RequestMethod.DELETE)
	public void removePost(@PathVariable(value="postId") Long postId) {
		service.removePostById(postId);
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.GET)
	public List<Post> listPosts() {
		return service.findAllPosts();
	}
}
