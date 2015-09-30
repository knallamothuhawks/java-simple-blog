package cc.blog.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberRoleType;
import cc.blog.repository.MemberRepository;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Member addMember(final MemberDto.Create dto) {
		Member member = modelMapper.map(dto, Member.class);
		member.setCreatedDate(new Date());
		if (member.getRole() == null) {
			member.setRole(MemberRoleType.GENERAL);
		}
		
		return repository.save(member);
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
		repository.save(updateMember);
	}
	
	public Member findMemberById(final Long memberId) {
		return repository.findOne(memberId);
	}
	
	public void removeMemberById(final Long memberId) {
		repository.delete(memberId);
	}
	
	public List<Member> findAllMembers() {
		return repository.findAll();
	}
}
