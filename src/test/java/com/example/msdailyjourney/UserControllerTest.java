package com.example.msdailyjourney;

import com.example.msdailyjourney.shared.GenericResponse;
import com.example.msdailyjourney.user.User;
import com.example.msdailyjourney.user.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    public static final String API_1_0_USERS = "/api/1.0/users";
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;

    @Before
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk() {
        User user = createdValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = createdValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        Assert.assertEquals(userRepository.count(),1);
    }
    @Test
    public void postUser_whenUserIsValid_receiveSuccessMessage() {
        User user = createdValidUser();
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        Assert.assertNotNull(response.getBody().getMessage());
    }
    @Test
    public void postUser_whenUserIsValid_passwordIsHasheedInDatabase() {
        User user = createdValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        List<User> response = userRepository.findAll();
        User inDB = response.get(0);
        Assert.assertNotEquals(inDB.getPassword(),user.getPassword());
    }

    private static User createdValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }
}