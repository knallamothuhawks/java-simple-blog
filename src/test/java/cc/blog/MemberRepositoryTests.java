package cc.blog;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cc.blog.model.Member;
import cc.blog.model.MemberRoleType;
import cc.blog.repository.MemberRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository repository;

	@Test
	public void testSave() {
		Date currentDate = Calendar.getInstance().getTime();
		IntStream.range(1, 10).mapToObj(i -> {
			String name = "test_name_" + i;
			String email = name + "@email.com";
			String password = name + "!pass";

			return new Member(null, name, email, password, currentDate, MemberRoleType.ADMIN);
		}).forEach(member -> repository.save(member));

	}

}
