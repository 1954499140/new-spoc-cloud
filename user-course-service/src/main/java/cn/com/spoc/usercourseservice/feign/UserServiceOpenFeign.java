package cn.com.spoc.usercourseservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "user-service")
public interface UserServiceOpenFeign {
    @PostMapping("/user/check-token")
    ResponseEntity<Map<String, String>> checkToken(@RequestBody Map<String, String> request);

    @PostMapping("/user/get-username-from-token")
    ResponseEntity<Map<String, String>> getUsernameFromToken(@RequestBody Map<String, String> request);

    @PostMapping("/user/get-identity-from-token")
    ResponseEntity<Map<String, String>> getIdentityFromToken(@RequestBody Map<String, String> request);

    default boolean tokenInvalid(String token) {
        var response = checkToken(Map.of("token", token));
        var body = response.getBody();
        if (body == null) {
            return true;
        }
        var result = body.get("result");
        return !"true".equals(result);
    }

    default String getUsernameFromToken(String token) {
        var response = getUsernameFromToken(Map.of("token", token));
        var body = response.getBody();
        if (body == null) {
            return null;
        }
        return body.get("username");
    }

    default String getIdentityFromToken(String token) {
        var response = getIdentityFromToken(Map.of("token", token));
        var body = response.getBody();
        if (body == null) {
            return null;
        }
        return body.get("identity");
    }
}
