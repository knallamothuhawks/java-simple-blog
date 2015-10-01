package cc.blog.web;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
public class MiscTests {

	@Autowired
	ModelMapper modelMapper;
	
	@Test
	public void test() {
		assertNotNull(modelMapper);
		TestUserDto dto = new TestUserDto(1L, "yoon", 23L);
		TestUser t = modelMapper.map(dto, TestUser.class);
		System.out.println(t);
	}
	
	
	@Data
	private static class TestUser {
		private Long id;
		private String name;
		private String email;
		private String pass;
		private TestTeam team;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class TestUserDto {
		private Long id;
		private String name;
		private Long teamnum;
	}
	
	@Data
	private static class TestTeam {
		private Long id;
		private String name;
	}
}
