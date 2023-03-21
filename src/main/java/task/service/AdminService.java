package task.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import task.dto.DiscountDTO;
import task.dto.NotificationResponseDTO;
import task.dto.StoryResponseDTO;
import task.entity.*;
import task.entity.enums.Role;
import task.exceptions.*;
import task.repository.*;
import task.security.TokenCreator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {

    private UserRepository userRepository;
    private GoodRepository goodRepository;
    private DiscountRepository discountRepository;
    private OrganizationRepository organizationRepository;
    private NotificationRepository notificationRepository;
    private EstimationRepository estimationRepository;
    private StoryRepository storyRepository;
    private TokenCreator creator;
    private EstimationService estimationService;

    public AdminService (UserRepository userRepository,
                        GoodRepository goodRepository,
                        DiscountRepository discountRepository,
                        OrganizationRepository organizationRepository,
                        NotificationRepository notificationRepository,
                        EstimationRepository estimationRepository,
                        StoryRepository storyRepository,
                        TokenCreator creator,
                        EstimationService estimationService) {
        this.userRepository = userRepository;
        this.goodRepository = goodRepository;
        this.discountRepository = discountRepository;
        this.organizationRepository = organizationRepository;
        this.notificationRepository = notificationRepository;
        this.estimationRepository = estimationRepository;
        this.storyRepository = storyRepository;
        this.creator = creator;
        this.estimationService = estimationService;
    }

    public ResponseEntity<String> changeDescription (String token, int good_id, String description) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (goodRepository.existsById(good_id)) {
                goodRepository.descriptionChange(good_id,description);
            } else {
                throw new GoodNotFoundException("Good with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("Description of the goo with the id "
                + good_id + " successfully changed", HttpStatus.OK);
    }

    public ResponseEntity<String> changeKeywords (String token, int good_id, String keywords) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (goodRepository.existsById(good_id)) {
                goodRepository.keywordsChange(good_id,keywords);
            } else {
                throw new GoodNotFoundException("Good with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("Keywords of the good with the id "
                + good_id + " successfully changed", HttpStatus.OK);
    }

    public ResponseEntity<String> changePrice (String token, int good_id, double price) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (goodRepository.existsById(good_id)) {
                goodRepository.priceChange(good_id,price);
            } else {
                throw new GoodNotFoundException("Good with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("Price of the good with the id "
                + good_id + " successfully changed", HttpStatus.OK);
    }

    public ResponseEntity<String> changeFeatures (String token, int good_id, String features) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (goodRepository.existsById(good_id)) {
                goodRepository.featuresChange(good_id,features);
            } else {
                throw new GoodNotFoundException("Good with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("Price of the good with the id "
                + good_id + " successfully changed", HttpStatus.OK);
    }

    public ResponseEntity<String> createDiscounts (String token, DiscountDTO discountDTO) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            Discount discount = new Discount();
            discount.setPercent(discountDTO.getPercent());
            discount.setStart_date(discountDTO.getStart_date());
            discount.setEnd_date(discountDTO.getEnd_date());
            discountRepository.save(discount);
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("New discount created",HttpStatus.OK);
    }

    public ResponseEntity<String> addGoodToDiscount (String token, int discount_id, int good_id) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (discountRepository.existsById(discount_id)) {
                Discount discount = discountRepository.findById(discount_id);
                List<Good> goods = discount.getGoods();
                if (goods.isEmpty()) {
                    goods = new ArrayList<>();
                }
                goods.add(goodRepository.findGoodById(good_id));
                discount.setGoods(goods);
            } else {
                throw new NoDiscountException("Discount with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("You added discount to good's price",HttpStatus.OK);
    }

    public List<StoryResponseDTO> getStoryOfUser(String token, int user_id) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        List<Story> list; List<StoryResponseDTO> result = new ArrayList<>();
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (userRepository.existsById(user_id)) {
                User user_guest = userRepository.findUserById(user_id);
                list = storyRepository.findAllByUser(user_guest);
                for (int i = 0; i < list.size(); i++) {
                    Story story = list.get(i);
                    StoryResponseDTO responseDTO = new StoryResponseDTO(story.getUser().getId(),
                            story.getGood().getId(), story.getDate());
                    result.add(responseDTO);
                }
            } else {
                throw new UsernameNotFoundException("User with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return result;
    }

    public ResponseEntity<String> addBalanceOfUser(String token, int user_id, double money) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (userRepository.existsById(user_id)) {
                userRepository.addBalance(user_id, money);
            } else {
                throw new UsernameNotFoundException("User with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("You added money to balance of user",HttpStatus.OK);
    }

    public ResponseEntity<String> sendNotification(String token, int user_id, NotificationResponseDTO notificationDTO) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (userRepository.existsById(user_id)) {
                User user_guest = new User();
                String text = notificationDTO.getText();
                String header = notificationDTO.getHeader();
                Notification notification = new Notification(header,text,user_guest);
                notificationRepository.save(notification);
            } else {
                throw new UsernameNotFoundException("User with such id doesn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("You send notification to user ",HttpStatus.OK);
    }

    public ResponseEntity<String> changeOrganisationStatus(String token, int organization_id, String status) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (organizationRepository.existsById(organization_id)) {
                organizationRepository.changeStatus(organization_id, status);
            } else {
                throw new OrganizationNotFoundException("Organization with id didn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("You changed status of organization",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteOrganization (String token, int organization_id) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (user.getRole().equals(String.valueOf(Role.ADMIN))) {
            if (organizationRepository.existsById(organization_id)) {
                organizationRepository.deleteById(organization_id);
            } else {
                throw new OrganizationNotFoundException("Organization with id didn't exist");
            }
        } else {
            throw new NotAllowedException("You have no permission for that");
        }
        return new ResponseEntity<>("Organization successfully deleted",HttpStatus.OK);
    }

    private User getUser(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        final String name = creator.getNameFromToken(token);
        return userRepository.findUserByName(name)
                .orElseThrow(() -> new UnauthorizedUserException("Unauthorized user"));
    }
}
