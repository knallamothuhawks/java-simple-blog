package cc.blog.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostDto {

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Create {
		private String title;
		private String content;
		private Member member;
		private Set<String> tags;
	}
}
