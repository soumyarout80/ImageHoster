package ImageHoster.controller;

import ImageHoster.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    protected MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    //This test checks the controller logic when the user sends the GET request to get all images in the application and checks whether the logic returns the html file 'index.html'
    @Test
    public void getAllImages() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Image Hoster")));
    }
}