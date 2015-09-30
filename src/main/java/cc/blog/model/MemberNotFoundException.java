package cc.blog.model;

public class MemberNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public MemberNotFoundException(Long id) {
		super("Member not found. memberId : " + id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
