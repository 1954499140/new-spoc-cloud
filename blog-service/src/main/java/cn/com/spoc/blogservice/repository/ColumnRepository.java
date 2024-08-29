package cn.com.spoc.blogservice.repository;

import cn.com.spoc.blogservice.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, String> {
    @Query("SELECT c FROM ColumnEntity c WHERE c.columnName = :columnName")
    ColumnEntity findByColumnName(@Param("columnName") String columnName);

    @Query("SELECT c.columnName from ColumnEntity c")
    List<String> getAllColumns();
}
