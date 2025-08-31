package ro.oauth2.oauth2ex.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RestController
@RequestMapping("/")
public class HomeControler {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> home(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok("{\"message\": \"Welcome to the OAuth2 Example Application!\"}");
    }

    @GetMapping("/user")
    public ResponseEntity<String> GetUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/oauth2/authorization/github")); // 👈 relative or absolute URL
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        for (String attr : principal.getAttributes().keySet()) {
            System.out.println("Attribute: " + attr + " = " + principal.getAttribute(attr));
        }
        return ResponseEntity.ok("{\"name\": \"" + principal.getAttribute("login") + " with ID:" + principal
                .getAttribute("id") + "\"}");
    }

}
