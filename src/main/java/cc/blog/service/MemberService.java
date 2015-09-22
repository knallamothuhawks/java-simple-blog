package cc.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.blog.model.Member;
import cc.blog.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository repository;
	
	public Long addMember(final Member member) {
		repository.save(member);
		return member.getId();
	}
	
	public Member findMemberById(final Long memberId) {
		return repository.findOne(memberId);
	}
	
	public void removeMemberById(final Long memberId) {
		repository.remove(memberId);
	}
	
	public List<Member> findAllMembers() {
		return repository.findAll();
	}
}
