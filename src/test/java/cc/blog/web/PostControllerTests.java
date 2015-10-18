package cc.blog.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberRoleType;
import cc.blog.model.Post;
import cc.blog.model.PostDto;
import cc.blog.service.MemberService;
import cc.blog.service.PostService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@Transactional
public class PostControllerTests {
	
	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	PostService postService;
	
	@Autowired
	MemberService memberService;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testCreatePost() throws Exception {
		
		ResultActions result = mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPostDto())));
		
		result.andDo(print());
		result.andExpect(status().isCreated());
	}
	
	@Test
	public void testGetPost() throws Exception {
		Post post = postService.addPost(getPostDto());
		ResultActions result = mockMvc.perform(get("/post/" + post.getId()));
		result.andDo(print());
		result.andExpect(status().isOk());
	}
	
	@Test
	public void testDeletePost() throws Exception {
		Post post = postService.addPost(getPostDto());
		ResultActions result = mockMvc.perform(delete("/post/" + post.getId()));
		result.andDo(print());
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdatePost() throws Exception {
		Post oldPost = postService.addPost(getPostDto());
		oldPost.getTags().clear();
		oldPost.getTags().add("new_tag_1");
		Set<String> newTag = oldPost.getTags();
		PostDto.Update dto = new PostDto.Update(oldPost.getId(), "update-title", "update-content", newTag);
		
		ResultActions result = mockMvc.perform(put("/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
		
		result.andDo(print());
		result.andExpect(status().isOk());
	}
	
	@Test
	public void testGetPosts() throws Exception {
		IntStream.range(1, 20).forEach(i -> {
			try {
				postService.addPost(getPostDto());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		ResultActions result = mockMvc.perform(get("/posts"));
		result.andDo(print());
		result.andExpect(status().isOk());
	}
	
	private PostDto.Create getPostDto() {
		MemberDto.Create memberDto = new MemberDto.Create("user1", "pass1", "email2@mail.com", MemberRoleType.GENERAL);
		Member member = memberService.addMember(memberDto);
		Set<String> tags = new HashSet<String>();
		tags.add("tag1");
		tags.add("user-tag-2");
		
		return new PostDto.Create("title", "content", member.getId(), tags);
	}
}
