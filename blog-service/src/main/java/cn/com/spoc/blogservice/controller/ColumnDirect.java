package cn.com.spoc.blogservice.controller;

import cn.com.spoc.blogservice.entity.ColumnEntity;
import cn.com.spoc.blogservice.service.ColumnService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/column-direct")
public class ColumnDirect {
    @Autowired
    private ColumnService columnService;
    @Value("${application.column-cover.base-url}")
    private String columnCoverBaseUrl;
    @Value("${application.column-cover.default-url}")
    private String columnCoverDefaultUrl;

    @PostMapping("/get-all-columns")
    public ResponseEntity<List<String>> getAllColumns() {
        List<String> columns = columnService.getAllColumn();
        if (columns == null) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(columns);
    }

    @PostMapping("/get-title-num")
    public ResponseEntity<Integer> getTitleNum(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        var columnEntity = columnService.getColumn(name);
        if (columnEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        int num = columnService.getTitleNum(name);
        return ResponseEntity.ok(num);
    }

    @PostMapping("/create-column")
    public ResponseEntity<String> createColumn(@RequestParam("ColumnName") String columnName, @RequestParam("file") MultipartFile file) {
        try {
            byte[] blogCover = file.getBytes();
            return ResponseEntity.ok(columnService.saveColumn(columnName, blogCover));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File Get Failed");
        }
    }

    @GetMapping("/column-cover/{columnName}")
    public void getBlogCover(@PathVariable String columnName, HttpServletResponse response) {
        try {
            var cover = columnService.getColumn(columnName).getCover();
            if (cover == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setContentType("image/jpeg");
                response.setContentLength(cover.length);
                response.getOutputStream().write(cover);
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/get-column-cover-url")
    public ResponseEntity<Map<String, String>> getColumnCoverUrl(@RequestBody Map<String, String> request) {
        String columnName = request.get("columnName");
        ColumnEntity columnEntity = columnService.getColumn(columnName);
        if (columnEntity.getCover() == null) {
            return ResponseEntity.ok(Map.of("url", columnCoverDefaultUrl));
        }
        return ResponseEntity.ok(Map.of("url", columnCoverBaseUrl + columnName));
    }
}
