package cc.blog.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cc.blog.model.Member;
import cc.blog.model.MemberDto;
import cc.blog.model.MemberDto.Response;
import cc.blog.model.MemberNotFoundException;
import cc.blog.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	private MemberService service;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public ResponseEntity<?> addMember(@RequestBody @Valid MemberDto.Create dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
		}

		Member newMember = service.addMember(dto);
		return new ResponseEntity<>(modelMapper.map(newMember, MemberDto.Response.class), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/member", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateMember(@RequestBody Member member) throws MemberNotFoundException {
		Member updateMember = service.updateMember(member);
		return new ResponseEntity<>(modelMapper.map(updateMember, MemberDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public MemberDto.Response viewMember(@PathVariable(value = "memberId") Long memberId) throws MemberNotFoundException {
		return modelMapper.map(service.findMemberById(memberId), MemberDto.Response.class);
	}

	@RequestMapping(value = "/member/{memberId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMember(@PathVariable(value = "memberId") Long memberId) {
		service.removeMemberById(memberId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/members", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<MemberDto.Response> getMembers(Pageable pageable) {
		Page<Member> page = service.findAllMembers(pageable);
		List<MemberDto.Response> members = page.getContent().stream()
				.map(member -> modelMapper.map(member, MemberDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(members, pageable, page.getTotalElements());
	}

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleAccountNotFoundException(MemberNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("[" + e.getId() + "]에 해당하는 계정이 없습니다.");
		errorResponse.setCode("member.not.found.exception");
		return errorResponse;
	}
}
