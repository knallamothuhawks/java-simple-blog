package cc.blog.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Create {
		@NotBlank
		@Size(min = 3)
		private String name;
		
		@NotBlank
		@Size(min = 3)
		private String password;
		
		@NotBlank
		@Size(min = 3)
		private String email;
		
		private MemberRoleType role;
	}
	
	@Data
	public static class Response {
		private Long id;
		private String name;
		private String email;
		private Date createdDate;
		private MemberRoleType role;
	}
}
