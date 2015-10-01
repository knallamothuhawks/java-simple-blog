package cc.blog.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cc.blog.model.Member;
import cc.blog.model.MemberNotFoundException;
import cc.blog.model.Post;
import cc.blog.model.PostDto;
import cc.blog.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ModelMapper modelMapper;

	public Post addPost(final PostDto.Create dto) throws MemberNotFoundException {
		Member member = memberService.findMemberById(dto.getMemberId());
		Post post = modelMapper.map(dto, Post.class);
		final Date currentDate = new Date();
		post.setCreatedDate(currentDate);
		post.setLastModifiedDate(currentDate);
		post.setMember(member);
		return repository.save(post);
	}

	public Post updatePost(final PostDto.Update updateDto) {
		if (updateDto == null || updateDto.getId() == null) {
			throw new IllegalStateException("Invalid Post entity.");
		}

		Post existPost = findPostById(updateDto.getId());
		if (existPost == null) {
			throw new IllegalStateException("Post entity not found, postId: " + updateDto.getId());
		}

		Post updatePost = modelMapper.map(updateDto, Post.class);
		updatePost.setCreatedDate(existPost.getCreatedDate());
		updatePost.setLastModifiedDate(new Date());
		updatePost.setMember(existPost.getMember());
		
		return repository.save(updatePost);
	}

	public Post findPostById(final Long postId) {
		return repository.findOne(postId);
	}

	public void removePostById(final Long postId) {
		repository.delete(postId);
	}

	public Page<Post> findAllPosts(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
