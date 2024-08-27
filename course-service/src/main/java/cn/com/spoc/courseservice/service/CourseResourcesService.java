package cn.com.spoc.courseservice.service;

import cn.com.spoc.courseservice.entity.CourseResourcesEntity;
import cn.com.spoc.courseservice.repository.CourseResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class CourseResourcesService {
    @Value("${application.file.directory}")
    private String uploadFilesDirectory;
    @Autowired
    private CourseResourcesRepository courseResourcesRepository;

    public boolean storeFile(MultipartFile file, String username, String courseId, String fileId) {
        try {
            Files.createDirectories(Paths.get(uploadFilesDirectory));
            var fileName = file.getOriginalFilename();
            var fullName = fileId + "-" + fileName;
            var targetLocation = Paths.get(uploadFilesDirectory).resolve(fullName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            var type = Files.probeContentType(targetLocation.normalize());
            var resource = new CourseResourcesEntity(fileId, fileName, username, courseId, type);
            courseResourcesRepository.save(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean revert(String fileId, String username) {
        var title = courseResourcesRepository.findTitleByFileIdAndUsername(fileId, username);
        if (title == null) {
            return false;
        }
        var fullName = fileId + "-" + title;
        try {
            var filePath = Paths.get(uploadFilesDirectory).resolve(fullName).normalize();
            Files.deleteIfExists(filePath);
            courseResourcesRepository.deleteById(fileId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public ResponseEntity<Resource> download(String fileId) {
        var title = courseResourcesRepository.findTitleByFileId(fileId);
        var fullName = fileId + "-" + title;
        try {
            var filePath = Paths.get(uploadFilesDirectory).resolve(fullName).normalize();
            var resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<CourseResourcesEntity> getCourseResources(String courseId) {
        return courseResourcesRepository.findCourseResourcesEntitiesByCourseId(courseId);
    }

    public String getCourseIdByResourceId(String resourceId) {
        return courseResourcesRepository.findCourseIdByResourceId(resourceId);
    }

    public boolean deleteResource(String resourceId) {
        var title = courseResourcesRepository.findTitleByFileId(resourceId);
        if (title == null) {
            return false;
        }
        var fullName = resourceId + "-" + title;
        try {
            var filePath = Paths.get(uploadFilesDirectory).resolve(fullName).normalize();
            Files.deleteIfExists(filePath);
            courseResourcesRepository.deleteById(resourceId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
