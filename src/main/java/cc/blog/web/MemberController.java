package cc.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.Member;
import cc.blog.service.MemberService;


@RestController
public class MemberController {

	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/add/member", method=RequestMethod.POST)
	public Long addMember(Member member) {
		return service.addMember(member);
	}
	
	@RequestMapping(value="/view/member/id/{memberId}", method=RequestMethod.GET)
	public Member viewMember(@PathVariable(value="memberId") Long memberId) {
		return service.findMemberById(memberId);
	}
	
	@RequestMapping(value="/remove/member/id/{memberId}")
	public void removeMember(@PathVariable(value="memberId") Long memberId) {
		service.removeMemberById(memberId);
	}
	
	@RequestMapping(value="/list/members")
	public List<Member> listMembers() {
		return service.findAllMembers();
	}
}
