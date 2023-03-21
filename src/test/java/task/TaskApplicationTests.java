package task;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import task.controller.AdminController;
import task.controller.UserController;
import task.dto.UserAuthenticationDTO;
import task.dto.UserRegistrationDTO;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    private static final String WRONG_NAME = "wrongUser";
    private static final String RIGHT_NAME = "Alexey";
    private static final String PASSWORD = "0000";
    private static final String ENDPOINT_LOGIN = "/login";
    private static final String ENDPOINT_REGISTER = "/register";
    private static UserAuthenticationDTO VALID_REQUEST;
    private static UserAuthenticationDTO INVALID_REQUEST;
    private static final Gson gson = new Gson();

    @BeforeAll
    public static void beforeAll() {
        VALID_REQUEST = new UserAuthenticationDTO(RIGHT_NAME, PASSWORD);
        INVALID_REQUEST = new UserAuthenticationDTO(WRONG_NAME, PASSWORD);
    }

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post(ENDPOINT_LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(VALID_REQUEST))).andExpect(status().isOk());
    }

    @Test
    void testLoginFail() throws Exception {
        mockMvc.perform(post(ENDPOINT_LOGIN).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(INVALID_REQUEST))).andExpect(status().isUnauthorized());
    }

    @Test
    void testRegistrationSuccess() throws Exception {
        mockMvc.perform(post(ENDPOINT_REGISTER).contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(INVALID_REQUEST))).andExpect(status().isOk());
    }

    @Test
    void AdminControllerTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertNotNull(webApplicationContext.getBean(AdminController.class));
    }

    @Test
    void UserControllerTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertNotNull(webApplicationContext.getBean(UserController.class));
    }
}
