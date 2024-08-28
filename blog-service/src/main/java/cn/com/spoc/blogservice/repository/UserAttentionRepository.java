package cn.com.spoc.blogservice.repository;

import cn.com.spoc.blogservice.entity.UserAttentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttentionRepository extends JpaRepository<UserAttentionEntity, Integer> {
}
