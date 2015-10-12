package cc.blog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {

	public UserDetailsImpl(Member member) {
		super(member.getName(), member.getPassword(), authorities(member));
	}

	private static Collection<? extends GrantedAuthority> authorities(Member member) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (member.getRole() == MemberRoleType.ADMIN) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}
}
