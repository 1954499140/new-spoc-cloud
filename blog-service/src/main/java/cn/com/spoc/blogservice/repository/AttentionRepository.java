package cn.com.spoc.blogservice.repository;

import cn.com.spoc.blogservice.dto.hot.AttentionInfoDTO;
import cn.com.spoc.blogservice.entity.AttentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttentionRepository extends JpaRepository<AttentionEntity, String> {

    @Query("SELECT a.attention FROM AttentionEntity a WHERE a.me = :me AND a.other = :other")
    Boolean getIsAttention(@Param("me") String me, @Param("other") String other);

    @Query("SELECT COUNT(a) FROM AttentionEntity a WHERE a.other = :other AND a.attention = true")
    int getAttentionNum(@Param("other") String other);

    @Query("SELECT a FROM AttentionEntity a WHERE a.me = :me AND a.other = :other")
    AttentionEntity getAttentionEntitiesByMe(@Param("me") String me, @Param("other") String other);

    @Query("SELECT a.other from AttentionEntity a WHERE a.me = :username ")
    List<String> getAttentionAuthor(@Param("username") String username);

    @Query("SELECT new cn.com.spoc.blogservice.dto.hot.AttentionInfoDTO(null, a.other) FROM AttentionEntity a WHERE a.me = :me AND a.attention = true")
    List<AttentionInfoDTO> getMyAttention(@Param("me") String me);

    @Query("SELECT new cn.com.spoc.blogservice.dto.hot.AttentionInfoDTO(null, a.me) FROM AttentionEntity a WHERE a.other = :other AND a.attention = true")
    List<AttentionInfoDTO> getAttentionMe(@Param("other") String other);
}
