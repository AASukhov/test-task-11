package task.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.dto.*;
import task.service.OrganizationService;
import task.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private OrganizationService organizationService;

    public UserController (UserService userService, OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @PutMapping("/good/buy")
    public ResponseEntity<String> buyGood (@RequestHeader("token") String token,
                                           @RequestParam("good_id") int good_id,
                                           @RequestParam("amount") int amount) {
        return userService.buyGood(token,good_id,amount);
    }

    @PutMapping("/good/return")
    public ResponseEntity<String> returnGood (@RequestHeader("token") String token,
                                               @RequestParam("good_id") int good_id,
                                               @RequestParam("amount") int amount) {
        return userService.returnGood(token,good_id,amount);
    }

    @PutMapping("/good/estimate")
    public ResponseEntity<String> setEstimationMark (@RequestHeader("token") String token,
                                           @RequestParam("good_id") int good_id,
                                           @RequestParam("mark") double mark) {
        return userService.setEstimationMark(token,good_id,mark);
    }

    @PostMapping("/good/offer")
    public ResponseEntity<String> goodOffer (@RequestHeader("token") String token,
                                              @RequestParam("organization_id") int organization_id,
                                              @RequestBody GoodDTO goodDTO) {
        return userService.goodOffer(token,organization_id,goodDTO);
    }

    @GetMapping("/good/list")
    public List<GoodResponseDTO> getAllOrganizationGoods(@RequestHeader("token") String token,
                                                         @RequestParam("organization_id") int organization_id) {
        return organizationService.getAllOrganizationGoods(token,organization_id);
    }

    @GetMapping("/review/list")
    public List<ReviewResponseDTO> getReviews (@RequestHeader("token") String token,
                                               @RequestParam("good_id") int good_id) {
        return userService.getReviews(token,good_id);
    }

    @PostMapping("/review")
    public ResponseEntity<String> setReview (@RequestHeader("token") String token,
                                              @RequestParam("good_id") int good_id,
                                              @RequestParam("text") String text) {
        return userService.setReview(token,good_id,text);
    }

    @GetMapping("/story/list")
    public List<StoryResponseDTO> getStory (@RequestHeader("token") String token) {
        return userService.getStory(token);
    }

    @GetMapping("/notifications")
    public List<NotificationResponseDTO> getNotifications (@RequestHeader("token") String token) {
        return userService.getNotifications(token);
    }

    @PostMapping("/organization/register")
    public ResponseEntity<String> organizationRegister (@RequestHeader("token") String token,
                                                        @RequestBody OrganizationDTO organizationDTO) {
        return organizationService.organizationRegister(token,organizationDTO);
    }

    @PutMapping("/organization/name")
    public ResponseEntity<String> changeName (@RequestHeader("token") String token,
                                              @RequestParam("organization_id") int organization_id,
                                              @RequestParam("text") String name) {
        return organizationService.changeName(token,organization_id,name);
    }
}
