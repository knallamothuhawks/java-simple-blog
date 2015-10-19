package cc.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cc.blog.model.Member;
import cc.blog.model.UserDetailsImpl;
import cc.blog.repository.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Member member = memberRepository.findByName(name);
		if (member == null) {
			throw new UsernameNotFoundException(name);
		}
		return new UserDetailsImpl(member);
	}

}
