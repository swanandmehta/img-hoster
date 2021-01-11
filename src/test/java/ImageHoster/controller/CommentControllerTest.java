
package ImageHoster.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.swing.text.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageController.class)
public class CommentControllerTest {
    protected MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;
    
    @MockBean
    private TagService tagService;

    //This test checks controller logic for comment and checks whether the controller logic redirects to the request handling method with request mapping of type "/images/{imageId}/{title}"
    @Test
    public void createComment() throws Exception {
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

        Mockito.when(imageService.getImage(Mockito.anyInt())).thenReturn(image);

        this.mockMvc.perform(post("/image/1/comments")
                .param("comment", "This comment is for testing purpose")
                .session(session))
                .andExpect(view().name("images/image"));
    }
}

