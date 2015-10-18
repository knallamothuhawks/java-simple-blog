package cc.blog.model;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cc.blog.web.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostDto {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Create {
		@NotBlank
		@Size(min = 1)
		private String title;
		@NotBlank
		@Size(min = 1)
		private String content;
		@NotNull
		@Min(value = 1)
		private Long memberId;
		private Set<String> tags;
	}

	@Data
	public static class Response {
		private Long id;
		private String title;
		private String content;
		private Date createdDate;
		private MemberDto.Response member;
		private Set<String> tags;

		@JsonSerialize(using = CustomDateSerializer.class)
		public Date getCreatedDate() {
			return createdDate;
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Update {
		private Long id;
		private String title;
		private String content;
		private Set<String> tags;
	}
}
