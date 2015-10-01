package cc.blog.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.MemberNotFoundException;
import cc.blog.model.Post;
import cc.blog.model.PostDto;
import cc.blog.service.PostService;

@RestController
public class PostController {

	@Autowired
	private PostService service;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<?> addPost(@RequestBody @Valid PostDto.Create postDto, BindingResult result)
			throws MemberNotFoundException {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Post newPost = service.addPost(postDto);
		return new ResponseEntity<>(newPost, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/post", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePost(@RequestBody @Valid PostDto.Update dto) {
		Post updatePost = service.updatePost(dto);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}

	@RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Post viewPost(@PathVariable(value = "postId") Long postId) {
		return service.findPostById(postId);
	}

	@RequestMapping(value = "/post/{postId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removePost(@PathVariable(value = "postId") Long postId) {
		service.removePostById(postId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public PageImpl<PostDto.Response> listPosts(Pageable pageable) {
		Page<Post> page = service.findAllPosts(pageable);
		List<PostDto.Response> posts = page.getContent().stream()
				.map(post -> modelMapper.map(post, PostDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(posts, pageable, page.getTotalElements());
	}
}
