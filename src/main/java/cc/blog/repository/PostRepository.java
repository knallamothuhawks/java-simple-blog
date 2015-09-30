package cc.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cc.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
