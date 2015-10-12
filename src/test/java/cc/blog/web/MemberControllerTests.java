package cc.blog.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import cc.blog.config.WebSecurityConfig;
import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberRoleType;
import cc.blog.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestConfig.class, WebSecurityConfig.class})
@WebAppConfiguration
@Transactional
public class MemberControllerTests {
	@Autowired
	WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MemberService memberService;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;

	MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(springSecurityFilterChain)
				.build();
	}

	@Test
	public void testCreateMember() throws Exception {
		MemberDto.Create createDto = new MemberDto.Create("username", "password!", "email@email.com");

		ResultActions result = mockMvc.perform(post("/member")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));
		
		result.andDo(print());
		result.andExpect(status().isCreated());
		
		MemberDto.Create brokenDto = new MemberDto.Create("user1", "p", "meil");
		result = mockMvc.perform(post("/member").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(brokenDto)));
		
		result.andDo(print());
		result.andExpect(status().isBadRequest());
	}
	
	@Test
	public void tesgtFindMember() throws Exception {
		MemberDto.Create createDto = new MemberDto.Create();
		createDto.setEmail("email@eml.com");
		createDto.setName("uname");
		createDto.setPassword("password1");
		memberService.addMember(createDto);
		
		
		
		Member member = memberService.addMember(new MemberDto.Create("username", "password!", "email@email.com"));
		ResultActions result = mockMvc.perform(get("/member/" + member.getId())
				.with(httpBasic(createDto.getName(), createDto.getPassword())));
		
		result.andDo(print());
		result.andExpect(status().isOk());
		
		ResultActions errorResult = mockMvc.perform(get("/member/" + 0L));
		errorResult.andDo(print());
		errorResult.andExpect(status().isBadRequest());
		errorResult.andExpect(jsonPath("$.code", is("member.not.found.exception")));
	}
	
	@Test
	public void testDeleteMember() throws Exception {
		Member member = memberService.addMember(new MemberDto.Create("username", "password!", "email@email.com"));
		ResultActions result = mockMvc.perform(delete("/member/" + member.getId())
				);
		
		result.andDo(print());
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdateMember() throws Exception {
		Member member = memberService.addMember(new MemberDto.Create("username", "password!", "email@email.com"));
		Member updateMember = new Member(member.getId(), "update-name", "update-mail@mail.com", "update-pass", null, MemberRoleType.ADMIN);
		
		ResultActions result = mockMvc.perform(put("/member")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateMember)));
		
		result.andDo(print());
		result.andExpect(status().isOk());
	}
	
	@Test
	public void testGetMemberPage() throws Exception {
		ResultActions result = mockMvc.perform(get("/members"));
		result.andDo(print());
		result.andExpect(status().isOk());
	}
}
