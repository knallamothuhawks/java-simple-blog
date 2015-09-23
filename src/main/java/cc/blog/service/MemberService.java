package cc.blog.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.blog.model.Member;
import cc.blog.model.MemberRoleType;
import cc.blog.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository repository;
	
	public Long addMember(final Member member) {
		member.setCreatedDate(Calendar.getInstance().getTime());
		if (member.getRole() == null) {
			member.setRole(MemberRoleType.GENERAL);
		}
		
		repository.save(member);
		return member.getId();
	}
	
	public void updateMember(final Member updateMember) {
		if (updateMember == null || updateMember.getId() == null) {
			throw new IllegalStateException("Invalid Member entity.");
		}
		
		Member existMember = findMemberById(updateMember.getId());
		if (existMember == null) {
			throw new IllegalStateException("Member entity not found, memberId: " + updateMember.getId());
		}
		
		updateMember.setCreatedDate(existMember.getCreatedDate());
		repository.update(updateMember);
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
