package task.service;

import org.springframework.stereotype.Service;
import task.dto.ReviewResponseDTO;
import task.entity.Estimation;
import task.entity.Good;
import task.repository.EstimationRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EstimationService {

    private EstimationRepository repository;

    public EstimationService (EstimationRepository repository) {
        this.repository = repository;
    }

    public double evaluation (Good good) {
        List<Estimation> list = repository.findAllByGood(good);
        int counter = 1;
        double sum = 0;
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                sum = sum + list.get(i).getMark();
                counter++;
            }
            counter = counter - 1;
        }
        return sum/counter;
    }

    public List<ReviewResponseDTO> getReviews (Good good) {
        List<Estimation> list = repository.findAllByGood(good);
        List<ReviewResponseDTO> result = new ArrayList<>();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                ReviewResponseDTO review = new ReviewResponseDTO(list.get(i).getReview());
                result.add(review);
            }
        }
        return result;
    }
}
