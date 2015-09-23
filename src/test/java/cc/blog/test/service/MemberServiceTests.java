package cc.blog.test.service;

import static org.junit.Assert.*;

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
import cc.blog.model.MemberRoleType;
import cc.blog.service.MemberService;
import cc.blog.test.config.DataSourceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
@Transactional
public class MemberServiceTests {

	@Autowired
	private MemberService service;

	private LongFunction<Member> dummyMember = (number) -> {
		String name = "test-name-" + number;
		String email = name + "@email.com";
		String password = name + "!pass";

		return new Member(null, name, email, password, null, null);
	};

	@Test
	public void testSaveAndFind() {
		final int numberMembers = 10;

		IntStream.range(0, numberMembers).mapToObj(number -> dummyMember.apply(number))
				.forEach(member -> service.addMember(member));

		Assert.assertEquals(numberMembers, service.findAllMembers().size());
	}

	@Test
	public void testUpdate() {
		final Long targetId = service.addMember(dummyMember.apply(1));
		
		Member tempUpdateMember = new Member();
		tempUpdateMember.setId(targetId);
		tempUpdateMember.setEmail("update_email@emgila.com");
		tempUpdateMember.setName("update-name");
		tempUpdateMember.setPassword("update-pass@pass");
		tempUpdateMember.setRole(MemberRoleType.ADMIN);
		service.updateMember(tempUpdateMember);
		
		Member newMember = service.findMemberById(targetId);
		assertEquals(tempUpdateMember, newMember);
	}
	
	@Test
	public void testRemove() {
		final Long targetId = service.addMember(dummyMember.apply(1));
		service.removeMemberById(targetId);
		assertNull(service.findMemberById(targetId));
	}
}
