/*
package ImageHoster.controller;

import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    protected MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageService imageService;

    //This test checks the controller logic for user signup when user requests for a registration form and checks whether the logic returns the html file 'users/registration.html'
    @Test
    public void signupWithGetRequest() throws Exception {
        this.mockMvc.perform(get("/users/registration"))
                .andExpect(view().name("users/registration"))
                .andExpect(content().string(containsString("Please Register:")));
    }


    //This test checks the controller logic for user signup when user fills the form and send the POST request to the server but the password type is wrong and checks whether the Model type object contains the desired attribute with desired value
    @Test
    public void signupWithWrongPasswordType() throws Exception {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);
        userProfile.setEmailAddress("a@gmail.com");
        userProfile.setFullName("Abhi Mahajan");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile);
        user.setId(1);
        user.setUsername("Abhi");
        user.setPassword("password");


        this.mockMvc.perform(post("/users/registration")
                .flashAttr("user", user)
        )
                .andExpect(model().attribute("passwordTypeError", equalTo("Password must contain atleast 1 alphabet, 1 number & 1 special character")));
    }

    //This test checks the controller logic for user signup when user fills the form and send the POST request to the server with the correct password type and checks whether the logic returns the html file 'users/login.html'
    @Test
    public void signupWithCorrectPasswordType() throws Exception {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);
        userProfile.setEmailAddress("a@gmail.com");
        userProfile.setFullName("Abhi Mahajan");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile);
        user.setId(1);
        user.setUsername("Abhi");
        user.setPassword("password1@");


        this.mockMvc.perform(post("/users/registration")
                .flashAttr("user", user)
        )
                .andExpect(view().name("users/login"))
                .andExpect(content().string(containsString("Please Login:")));
    }

    //This test checks the controller logic for user signin when user requests for a signin form where he can enter the username and password and checks whether the logic returns the html file 'users/login.html'
    @Test
    public void signinWithGetRequest() throws Exception {
        this.mockMvc.perform(get("/users/login"))
                .andExpect(view().name("users/login"))
                .andExpect(content().string(containsString("Please Login:")));
    }


    //This test checks the controller logic for user signin when user enters the username and password that has not been registered and sends the POST request to the server and checks whether the logic returns the html file 'users/login.html'
    @Test
    public void signinWithWrongCredentials() throws Exception {
        User userSignin = new User();
        userSignin.setUsername("Abhi");
        userSignin.setPassword("password1@");

        Mockito.when(userService.login(Mockito.anyObject())).thenReturn(null);
        session = new MockHttpSession();


        this.mockMvc.perform(post("/users/login").session(session)
                .flashAttr("user", userSignin)
        )
                .andExpect(view().name("users/login"))
                .andExpect(content().string(containsString("Please Login:")));
    }


    //This test checks the controller logic for user signin when user enters the username and password that has been registered and sends the POST request to the server and checks whether the logic redirects to the request handling method with request mapping of type "/images"
    public void signinWithCorrectCredentials() throws Exception {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);
        userProfile.setEmailAddress("a@gmail.com");
        userProfile.setFullName("Abhi Mahajan");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile);
        user.setId(1);
        user.setUsername("Abhi");
        user.setPassword("password");

        User userSignin = new User();
        userSignin.setUsername("Abhi");
        userSignin.setPassword("password1@");

        Mockito.when(userService.login(Mockito.anyObject())).thenReturn(user);
        session = new MockHttpSession();


        this.mockMvc.perform(post("/users/login").session(session)
                .flashAttr("user", userSignin)
        )
                .andExpect(redirectedUrl("/images"));
    }


    //This test checks the controller logic for user signout where he sends the POST request to the server to invalidate the session and checks whether the logic returns the html file 'index.html'
    @Test
    public void logout() throws Exception {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1);
        userProfile.setEmailAddress("a@gmail.com");
        userProfile.setFullName("Abhi Mahajan");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile);
        user.setId(1);
        user.setUsername("Abhi");
        user.setPassword("password");

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        this.mockMvc.perform(post("/users/logout").session(session))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Image Hoster")));
    }
}
*/
