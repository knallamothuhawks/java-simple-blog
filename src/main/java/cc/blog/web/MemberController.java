package cc.blog.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.service.MemberService;


@RestController
public class MemberController {

	private MemberService service;
	
	@Autowired
	public MemberController(MemberService service) {
		this.service = service;
	}
	
	@RequestMapping(value="/member", method=RequestMethod.POST)
	public ResponseEntity addMember(@RequestBody @Valid MemberDto.Create dto, BindingResult result) {
		
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Member newMember = service.addMember(dto);
		return new ResponseEntity<>(newMember, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/member", method=RequestMethod.PUT)
	public void updateMember(@RequestBody Member member) {
		service.updateMember(member);
	}
	
	@RequestMapping(value="/member/{memberId}", method=RequestMethod.GET)
	public Member viewMember(@PathVariable(value="memberId") Long memberId) {
		return service.findMemberById(memberId);
	}
	
	@RequestMapping(value="/member/{memberId}", method=RequestMethod.DELETE)
	public void removeMember(@PathVariable(value="memberId") Long memberId) {
		service.removeMemberById(memberId);
	}
	
	@RequestMapping(value="/members", method=RequestMethod.GET)
	public List<Member> listMembers() {
		return service.findAllMembers();
	}
}
