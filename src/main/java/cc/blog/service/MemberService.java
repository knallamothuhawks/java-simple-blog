package cc.blog.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberNotFoundException;
import cc.blog.model.MemberRoleType;
import cc.blog.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MemberService {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Member addMember(final MemberDto.Create dto) {
		Member member = modelMapper.map(dto, Member.class);
		member.setCreatedDate(new Date());
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
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
		updateMember.setPassword(passwordEncoder.encode(updateMember.getPassword()));
		
		return repository.save(updateMember);
	}

	public Member findMemberById(final Long memberId) throws MemberNotFoundException {
		Member member = repository.findOne(memberId);
		if (member == null) {
			log.error("Member entity not found. memberId: {}", memberId);
			throw new MemberNotFoundException(memberId);
		}

		return member;
	}

	public void removeMemberById(final Long memberId) {
		repository.delete(memberId);
	}

	public Page<Member> findAllMembers(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
