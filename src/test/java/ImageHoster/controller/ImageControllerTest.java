/*
package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {
    protected MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private TagService tagService;

    //This test checks the controller logic to get all the images after the user is logged in the application and checks whether the logic returns the html file 'images.html'
    @Test
    public void getUserImages() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        this.mockMvc.perform(get("/images").session(session))
                .andExpect(view().name("images"))
                .andExpect(content().string(containsString("Welcome User. These are the images")));
    }


    //This test checks the controller logic when the logged in user sends the GET request to the server to get the details of a particular image and checks whether the logic returns the html file 'images/image.html'
    @Test
    public void showImage() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        Image image = new Image();
        image.setId(1);
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        image.setUser(user);

        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(get("/images/1/new").session(session))
                .andExpect(view().name("images/image"))
                .andExpect(content().string(containsString("Welcome User. This is the image")));

    }


    //This test checks the controller logic when the logged in user sends a GET request to the server to get the form to upload an image in the application and checks whether the logic returns the html file 'images/upload.html'
    @Test
    public void uploadImageWithGetRequest() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        this.mockMvc.perform(get("/images/upload").session(session))
                .andExpect(view().name("images/upload"))
                .andExpect(content().string(containsString("Upload New Image")));
    }


    //This test checks the controller logic when the logged in submits the image to be uploaded in the application and checks whether the logic returns the html file 'images.html'
    @Test
    public void uploadImageWithPostRequest() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        MockMultipartFile mockImage = new MockMultipartFile("file", "image.jpg", "image/jpeg", "some_image".getBytes());

        String tags = "dog,labrador";

        Image image = new Image();
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        this.mockMvc.perform(multipart("/images/upload")
                .file(mockImage)
                .param("tags", tags)
                .flashAttr("newImage", image)
                .session(session))
                .andExpect(redirectedUrl("/images"));
    }

    //This test checks the controller logic when the owner of the image sends the GET request to get the form to edit the image and checks whether the logic returns the html file 'images/edit.html'
    @Test
    public void editImageWithOwnerOfTheImage() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        Image image = new Image();
        image.setId(1);
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        image.setUser(user);

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dog");

        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        image.setTags(tags);

        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(get("/editImage")
                .param("imageId", "1")
                .session(session))
                .andExpect(view().name("images/edit"))
                .andExpect(content().string(containsString("Edit Image")));
    }


    //This test checks the controller logic when non owner of the image sends the GET request to get the form to edit the image and checks whether the Model type object contains the desired attribute with desired value
    @Test
    public void editImageWithNonOwnerOfTheImage() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        User user1 = new User();
        UserProfile userProfile1 = new UserProfile();
        userProfile.setId(2);
        userProfile.setEmailAddress("p@gmail.com");
        userProfile.setFullName("Prerna");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile1);
        user.setId(2);
        user.setUsername("Prerna");
        user.setPassword("password1@@");

        Image image = new Image();
        image.setId(1);
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        image.setUser(user1);


        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(get("/editImage")
                .param("imageId", "1")
                .session(session))
                .andExpect(model().attribute("editError", "Only the owner of the image can edit the image"));
    }

    //This test checks the controller logic when the owner of the image sends the DELETE request to delete the image and checks whether the logic returns the html file 'images.html'
    @Test
    public void deleteImageWithOwnerOfTheImage() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        Image image = new Image();
        image.setId(1);
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        image.setUser(user);

        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(delete("/deleteImage")
                .param("imageId", "1")
                .session(session))
                .andExpect(redirectedUrl("/images"));
    }


    //This test checks the controller logic when non owner of the image sends the DELETE request to delete the image and checks whether the Model type object contains the desired attribute with desired value
    @Test
    public void deleteImageWithNonOwnerOfTheImage() throws Exception {
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

        session = new MockHttpSession();
        session.setAttribute("loggeduser", user);

        User user1 = new User();
        UserProfile userProfile1 = new UserProfile();
        userProfile.setId(2);
        userProfile.setEmailAddress("p@gmail.com");
        userProfile.setFullName("Prerna");
        userProfile.setMobileNumber("9876543210");
        user.setProfile(userProfile1);
        user.setId(2);
        user.setUsername("Prerna");
        user.setPassword("password1@@");

        Image image = new Image();
        image.setId(1);
        image.setTitle("new");
        image.setDescription("This image is for testing purpose");
        image.setUser(user1);


        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(delete("/deleteImage")
                .param("imageId", "1")
                .session(session))
                .andExpect(model().attribute("deleteError", "Only the owner of the image can delete the image"));
    }
}

*/
