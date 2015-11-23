package cc.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.blog.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByName(String name);

}
