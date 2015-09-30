package cc.blog.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberNotFoundException;
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
	
	public Member updateMember(final Member updateMember) throws MemberNotFoundException {
		if (updateMember == null || updateMember.getId() == null) {
			throw new MemberNotFoundException(updateMember.getId());
		}
		
		Member existMember = findMemberById(updateMember.getId());
		updateMember.setCreatedDate(existMember.getCreatedDate());
		return repository.save(updateMember);
	}
	
	public Member findMemberById(final Long memberId) throws MemberNotFoundException {
		Member member = repository.findOne(memberId);
		if (member == null) {
			throw new MemberNotFoundException(memberId);
		}
		
		return member;
	}
	
	public void removeMemberById(final Long memberId) {
		repository.delete(memberId);
	}
	
	public List<Member> findAllMembers() {
		return repository.findAll();
	}
}
