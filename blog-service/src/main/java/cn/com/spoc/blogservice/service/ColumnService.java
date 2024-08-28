package cn.com.spoc.blogservice.service;

import cn.com.spoc.blogservice.entity.ColumnEntity;
import cn.com.spoc.blogservice.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnService {
    @Autowired
    private ColumnRepository columnRepository;

    public List<String> getAllColumn() {
        return columnRepository.getAllColumns();
    }

    public int getTitleNum(String name) {
        return columnRepository.getReferenceById(name).getBlogEntityLists().size();
    }

    public String saveColumn(String columnName, byte[] file) {
        var columnEntity = new ColumnEntity(columnName, file);
        if (columnRepository.findByColumnName(columnName) != null) {
            return "Column Already Exist";
        }
        columnRepository.save(columnEntity);
        return "Create Column Success";
    }

    public ColumnEntity getColumn(String columnName) {
        return columnRepository.findByColumnName(columnName);
    }
}
