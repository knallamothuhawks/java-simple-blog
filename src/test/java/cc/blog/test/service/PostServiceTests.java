package cc.blog.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.function.LongFunction;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cc.blog.model.Member;
import cc.blog.model.Post;
import cc.blog.service.MemberService;
import cc.blog.service.PostService;
import cc.blog.test.config.DataSourceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
@Transactional
public class PostServiceTests {

	@Autowired
	private PostService postService;
	@Autowired
	private MemberService memberService;

	private LongFunction<Post> dummyPost = (number) -> {
		Long memberId = memberService.addMember(new Member());
		Member member = memberService.findMemberById(memberId);
		Post dummyPost = new Post();
		dummyPost.setContent("test-contents-" + number);
		dummyPost.setTitle("test-post-title-" + number);
		dummyPost.setMember(member);
		dummyPost.getTags().add("tag1");
		dummyPost.getTags().add("tag2341");
		dummyPost.getTags().add("tag1566");
		
		return dummyPost;
	};

	@Test
	public void testSaveAndFind() {
		final int numberPosts = 10;

		IntStream.range(0, numberPosts).mapToObj(number -> dummyPost.apply(number))
				.forEach(post -> postService.addPost(post));

		Assert.assertEquals(numberPosts, postService.findAllPosts().size());
	}

	@Test
	public void testUpdate() {
		final Long targetId = postService.addPost(dummyPost.apply(1));
		
		Long memberId = memberService.addMember(new Member());
		
		Post tempUpdatePost = dummyPost.apply(targetId);
		tempUpdatePost.setId(targetId);
		tempUpdatePost.setMember(memberService.findMemberById(memberId));
		postService.updatePost(tempUpdatePost);
		
		Post newPost = postService.findPostById(targetId);
		assertEquals(tempUpdatePost, newPost);
	}
	
	@Test
	public void testRemove() {
		final Long targetId = postService.addPost(dummyPost.apply(1));
		postService.removePostById(targetId);
		assertNull(postService.findPostById(targetId));
	}
}
