package task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.dto.DiscountDTO;
import task.dto.NotificationResponseDTO;
import task.dto.StoryResponseDTO;
import task.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService service;

    public AdminController (AdminService service) {
        this.service = service;
    }

    @PutMapping("/good/description")
    public ResponseEntity<String> changeDescription (@RequestHeader("token") String token,
                                                     @RequestParam ("good_id") int good_id,
                                                     @RequestParam ("description") String description) {
        return service.changeDescription(token,good_id,description);
    }

    @PutMapping("/good/keywords")
    public ResponseEntity<String> changeKeywords (@RequestHeader("token") String token,
                                                     @RequestParam ("good_id") int good_id,
                                                     @RequestParam ("keywords") String keywords) {
        return service.changeKeywords(token,good_id,keywords);
    }

    @PutMapping("/good/price")
    public ResponseEntity<String> changePrice (@RequestHeader("token") String token,
                                               @RequestParam("good_id") int good_id,
                                               @RequestParam ("price") double price) {
        return service.changePrice(token,good_id,price);
    }

    @PutMapping("/good/features")
    public ResponseEntity<String> changeFeatures (@RequestHeader("token") String token,
                                               @RequestParam("good_id") int good_id,
                                               @RequestParam ("features") String features) {
        return service.changeFeatures(token,good_id,features);
    }

    @PostMapping("/discount")
    public ResponseEntity<String> createDiscounts (@RequestHeader("token") String token,
                                                   @RequestBody DiscountDTO discountDTO) {
        return service.createDiscounts(token,discountDTO);
    }

    @PutMapping("/discount/good")
    public ResponseEntity<String> addGoodToDiscount (@RequestHeader("token") String token,
                                                  @RequestParam("discount_id") int discount_id,
                                                  @RequestParam ("good_id") int good_id) {
        return service.addGoodToDiscount(token,discount_id,good_id);
    }

    @GetMapping("/user/story")
    public List<StoryResponseDTO> getStoryOfUser(@RequestHeader("token")String token,
                                                 @RequestParam("user_id") int user_id) {
        return service.getStoryOfUser(token,user_id);
    }

    @PutMapping("/user/balance")
    public ResponseEntity<String> addBalanceOfUser(@RequestHeader("token") String token,
                                                   @RequestParam("user_id") int user_id,
                                                   @RequestParam("money") double money) {
        return service.addBalanceOfUser(token,user_id,money);
    }

    @PostMapping("/notification")
    public ResponseEntity<String> sendNotification(@RequestHeader("token") String token,
                                                   @RequestParam("user_id") int user_id,
                                                   @RequestBody NotificationResponseDTO notificationDTO) {
        return service.sendNotification(token,user_id,notificationDTO);
    }

    @PutMapping("/organization")
    public ResponseEntity<String> changeOrganisationStatus(@RequestHeader("token") String token,
                                                            @RequestParam("organization_id") int organization_id,
                                                            @RequestParam("status")String status) {
        return service.changeOrganisationStatus(token,organization_id,status);
    }

    @DeleteMapping("/organization")
    public ResponseEntity<String> deleteOrganization (@RequestHeader("token")String token,
                                                       @RequestParam("organization_id") int organization_id) {
        return service.deleteOrganization(token,organization_id);
    }
}
