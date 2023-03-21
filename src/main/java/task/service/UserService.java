package task.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import task.dto.GoodDTO;
import task.dto.NotificationResponseDTO;
import task.dto.ReviewResponseDTO;
import task.dto.StoryResponseDTO;
import task.entity.*;
import task.entity.enums.Status;
import task.exceptions.GoodNotFoundException;
import task.exceptions.OrganizationNotFoundException;
import task.exceptions.StoryNotFoundException;
import task.exceptions.UnauthorizedUserException;
import task.repository.*;
import task.security.TokenCreator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private GoodRepository goodRepository;
    private OrganizationRepository organizationRepository;
    private NotificationRepository notificationRepository;
    private EstimationRepository estimationRepository;
    private StoryRepository storyRepository;
    private TokenCreator creator;
    private EstimationService estimationService;

    public UserService (UserRepository userRepository,
                        GoodRepository goodRepository,
                        OrganizationRepository organizationRepository,
                        NotificationRepository notificationRepository,
                        EstimationRepository estimationRepository,
                        StoryRepository storyRepository,
                        TokenCreator creator,
                        EstimationService estimationService) {
        this.userRepository = userRepository;
        this.goodRepository = goodRepository;
        this.organizationRepository = organizationRepository;
        this.notificationRepository = notificationRepository;
        this.estimationRepository = estimationRepository;
        this.storyRepository = storyRepository;
        this.creator = creator;
        this.estimationService = estimationService;
    }

    public ResponseEntity<String> buyGood (String token, int good_id, int amount) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        int user_id = user.getId();

        Good good;
        if (goodRepository.existsById(good_id)) {
            good = goodRepository.findGoodById(good_id);
        } else {
            throw new GoodNotFoundException("Good with the id " + good_id + " doesn't exist");
        }

        if (good.getAmount() < amount) {
            throw new GoodNotFoundException("Not enough goods of the type " + good_id + " in the store");
        } else {
            goodRepository.goodBought(good_id,amount);
            int organization_id = good.getOrganization().getId();
            double percent = 1;
            if (good.getDiscount() != null) {
                Discount discount = good.getDiscount();
                percent = discount.getPercent();
            }
            double money = amount * good.getPrice() * percent;
            organizationRepository.gainMoney(organization_id, money);

            Story story = new Story(user,good);
            storyRepository.save(story);

            userRepository.payForGood(user_id, money);
        }
        return new ResponseEntity<>("Successfully bought", HttpStatus.OK);
    }

    public ResponseEntity<String> setEstimationMark (String token, int good_id, double mark) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        Good good;
        if (goodRepository.existsById(good_id)) {
            good = goodRepository.findGoodById(good_id);
        } else {
            throw new GoodNotFoundException("Good with such id doesn't exist");
        }
        if (!storyRepository.existsByGoodAndUser(good,user)) {
            throw new StoryNotFoundException("You didn't buy it");
        } else {
            Estimation estimation = new Estimation();
            estimation.setGood(good);
            estimation.setUser(user);
            estimation.setMark(mark);
            estimationRepository.save(estimation);

            double total_mark = estimationService.evaluation(good);
            goodRepository.changeEstimation(good_id,total_mark);
            return new ResponseEntity<>("You estimated good with id " + good_id,HttpStatus.OK);
        }
    }

    public List<ReviewResponseDTO> getReviews (String token, int good_id) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        Good good;
        if (goodRepository.existsById(good_id)) {
            good = goodRepository.findGoodById(good_id);
        } else {
            throw new GoodNotFoundException("Good with such id doesn't exist");
        }
        return estimationService.getReviews(good);
    }

    public ResponseEntity<String> setReview (String token, int good_id, String text) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        Good good;
        if (goodRepository.existsById(good_id)) {
            good = goodRepository.findGoodById(good_id);
        } else {
            throw new GoodNotFoundException("Good with such id doesn't exist");
        }

        Estimation estimation = estimationRepository.findByGoodAndUser(good,user);
        int id = estimation.getId();
        estimationRepository.changeEstimation(id,text);
        return new ResponseEntity<>("Successfully set review", HttpStatus.OK);
    }

    public List<StoryResponseDTO> getStory (String token) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        List<Story> list = storyRepository.findAllByUser(user);
        List<StoryResponseDTO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Story story = list.get(i);
            StoryResponseDTO responseDTO = new StoryResponseDTO(story.getUser().getId(),
                    story.getGood().getId(),
                    story.getDate());
            result.add(responseDTO);
        }
        return result;
    }

    public ResponseEntity<String> returnGood (String token, int good_id, int amount) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }

        Good good;
        if (goodRepository.existsById(good_id)) {
            good = goodRepository.findGoodById(good_id);
        } else {
            throw new GoodNotFoundException("Good with such id doesn't exist");
        }
        Story story = new Story();
        if (!storyRepository.existsByGoodAndUser(good,user)) {
            throw new StoryNotFoundException("You didn't buy that good");
        } else {
            story = storyRepository.findByGoodAndUser(good,user);
        }
        Date date = new Date();
        int diffInDays = (int)((date.getTime() - story.getDate().getTime()) / (1000 * 60 * 60 * 24));
        if (diffInDays > 1) {
            throw new StoryNotFoundException("The time you can return good is expired");
        } else {
            goodRepository.goodReturn(good_id,amount);
            int organization_id = good.getOrganization().getId();
            double percent = 1;
            if (good.getDiscount() != null) {
                Discount discount = good.getDiscount();
                percent = discount.getPercent();
            }
            double money = amount * good.getPrice() / percent;
            organizationRepository.returnMoney(organization_id, money);

            userRepository.returnForGood(good_id, money);
        }
        return new ResponseEntity<>("Successfully returned", HttpStatus.OK);
    }

    public List<NotificationResponseDTO> getNotifications (String token) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        List<NotificationResponseDTO> result = new ArrayList<>();
        List<Notification> list = notificationRepository.findAllByUser(user);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Notification notification = list.get(i);
                NotificationResponseDTO dto = new NotificationResponseDTO();
                dto.setHeader(notification.getHeader());
                dto.setText(notification.getText());
                result.add(dto);
            }
        }
        return result;
    }

    public ResponseEntity<String> goodOffer (String token, int organization_id, GoodDTO goodDTO) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (organizationRepository.existsByIdAndUser(organization_id,user)) {
            Organization organization = organizationRepository.findById(organization_id);
            if (organization.getStatus().equals(String.valueOf(Status.ACTIVE))) {
                Good good = new Good();
                good.setName(goodDTO.getName());
                good.setPrice(goodDTO.getPrice());
                good.setAmount(goodDTO.getAmount());
                good.setOrganization(organization);
                goodRepository.save(good);
            } else {
                throw new OrganizationNotFoundException("Organization is not active");
            }
        } else {
            throw new OrganizationNotFoundException("Organization doesn't exist");
        }
        return new ResponseEntity<>("The good is added", HttpStatus.OK);
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
