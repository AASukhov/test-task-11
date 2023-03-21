package task.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import task.dto.GoodResponseDTO;
import task.dto.OrganizationDTO;
import task.entity.Good;
import task.entity.Organization;
import task.entity.User;
import task.entity.enums.Status;
import task.exceptions.OrganizationNotFoundException;
import task.exceptions.UnauthorizedUserException;
import task.repository.GoodRepository;
import task.repository.OrganizationRepository;
import task.repository.UserRepository;
import task.security.TokenCreator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganizationService {

    private TokenCreator creator;
    private UserRepository userRepository;
    private OrganizationRepository organizationRepository;
    private GoodRepository goodRepository;

    public OrganizationService (TokenCreator creator,
                                UserRepository userRepository,
                                OrganizationRepository organizationRepository,
                                GoodRepository goodRepository) {
        this.creator = creator;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.goodRepository = goodRepository;
    }

    public ResponseEntity<String> organizationRegister (String token, OrganizationDTO organizationDTO) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        Organization organization = new Organization();
        String name = organizationDTO.getName();
        organization.setName(name);
        organization.setDescription(organizationDTO.getDescription());
        organization.setLogo(organizationDTO.getLogo());
        organization.setUser(user);
        organization.setStatus(String.valueOf(Status.AWAIT_FOR_APPROVAL));
        if (organizationRepository.existsByName(name)) {
            organizationRepository.save(organization);
        } else {
            throw new OrganizationNotFoundException("Organization with the name is existing");
        }
        return new ResponseEntity<>("You registered organization with the name'"
                + organizationDTO.getName() + "'.", HttpStatus.OK);
    }

    public ResponseEntity<String> changeName (String token, int organization_id, String name) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (organizationRepository.existsById(organization_id)) {
            organizationRepository.updateName(organization_id,name);
        } else {
            throw new OrganizationNotFoundException("Organization with such name is not registered");
        }
        return new ResponseEntity<>("Name of the organization with id "+ organization_id +" successfully modified", HttpStatus.OK);
    }

    public List<GoodResponseDTO> getAllOrganizationGoods(String token, int organization_id) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        List<Good> list; List<GoodResponseDTO> result = new ArrayList<>();
        if (organizationRepository.existsByIdAndUser(organization_id,user)) {
            Organization organization = organizationRepository.findById(organization_id);
            list = goodRepository.findAllByOrganization(organization);
            for (int i = 0; i < list.size(); i++) {
                Good good = list.get(i);
                GoodResponseDTO responseDTO = new GoodResponseDTO(good.getName(),good.getPrice(),good.getAmount());
                result.add(responseDTO);
            }
        } else {
            throw new OrganizationNotFoundException("This organization doesn't exist or user is not an owner");
        }
        return result;
    }

    public List<Organization> getAllUserOrganizations(String token) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        return organizationRepository.findAllByUser(user);
    }

    public ResponseEntity<String> changeLogo (String token, int id, String logo) {
        User user = getUser(token);
        if (user == null) {
            throw new UnauthorizedUserException("Unauthorized user");
        }
        if (organizationRepository.existsById(id)) {
            organizationRepository.updateLogo(id,logo);
        } else {
            throw new OrganizationNotFoundException("Organization with such name is not registered");
        }
        return new ResponseEntity<>("Logo of the organization with id "+id+" successfully modified",
                HttpStatus.OK);
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
