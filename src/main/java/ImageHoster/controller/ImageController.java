package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private TagService tagService;

    //This method displays all the images in the user home page after successful login
    @RequestMapping("images")
    public String getUserImages(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "images";
    }

    //This method is called when the details of the specific image with corresponding title are to be displayed
    //The logic is to get the image from the databse with corresponding title. After getting the image from the database the details are shown
    //First receive the dynamic parameter in the incoming request URL in a string variable 'title' and also the Model type object
    //Call the getImageByTitle() method in the business logic to fetch all the details of that image
    //Add the image in the Model type object with 'image' as the key
    //Return 'images/image.html' file

    //Also now you need to add the tags of an image in the Model type object
    //Here a list of tags is added in the Model type object
    //this list is then sent to 'images/image.html' file and the tags are displayed
    @RequestMapping("/images/{title}")
    public String showImage(@PathVariable("title") String title, Model model) {
        Image image = imageService.getImageByTitle(title);
        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        return "images/image";
    }

    //This controller method is called when the request pattern is of type 'images/upload'
    //The method returns 'images/upload.html' file
    @RequestMapping("/images/upload")
    public String newImage() {
        return "images/upload";
    }

    //This controller method is called when the request pattern is of type 'images/upload' and also the incoming request is of POST type
    //The method receives all the details of the image to be stored in the database, and now the image will be sent to the business logic to be persisted in the database
    //After you get the imageFile, set the user of the image by getting the logged in user from the Http Session
    //Convert the image to Base64 format and store it as a string in the 'imageFile' attribute
    //Set the date on which the image is posted
    //After storing the image, this method directs to the logged in user homepage displaying all the images

    //Get the 'tags' request parameter using @RequestParam annotation which is just a string of all the tags
    //Store all the tags in the database and make a list of all the tags using the findOrCreateTags() method
    //set the tags attribute of the image as a list of all the tags returned by the findOrCreateTags() method
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
    public String createImage(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags, Image newImage, HttpSession session) throws IOException {

        User user = (User) session.getAttribute("loggeduser");
        newImage.setUser(user);
        String uploadedImageData = convertUploadedFileToBase64(file);
        newImage.setImageFile(uploadedImageData);

        List<Tag> imageTags = findOrCreateTags(tags);
        newImage.setTags(imageTags);
        newImage.setDate(new Date());
        imageService.uploadImage(newImage);
        return "redirect:/images";
    }

    //This controller method is called when the request pattern is of type 'editImage'
    //This method fetches the image with the corresponding id from the database and adds it to the model with the key as 'image'
    //The method then returns 'images/edit.html' file wherein you fill all the updated details of the image

    //The method first needs to convert the list of all the tags to a string containing all the tags separated by a comma and then add this string in a Model type object
    //This string is then displayed by 'edit.html' file as previous tags of an image
    @RequestMapping(value = "/editImage")
    public String editImage(@RequestParam("imageId") Integer imageId, Model model) {
        Image image = imageService.getImage(imageId);

        String tags = convertTagsToString(image.getTags());
        model.addAttribute("image", image);
        model.addAttribute("tags", tags);
        return "images/edit";
    }

    //This controller method is called when the request pattern is of type 'images/edit' and also the incoming request is of PUT type
    //The method receives the imageFile, imageId, updated image, along with the Http Session
    //The method adds the new imageFile to the updated image if user updates the imageFile and adds the previous imageFile to the new updated image if user does not choose to update the imageFile
    //Set an id of the new updated image
    //Set the user using Http Session
    //Set the date on which the image is posted
    //Call the updateImage() method in the business logic to update the image
    //Direct to the same page showing the details of that particular updated image

    //The method also receives tags parameter which is a string of all the tags separated by a comma using the annotation @RequestParam
    //The method converts the string to a list of all the tags using findOrCreateTags() method and sets the tags attribute of an image as a list of all the tags
    @RequestMapping(value = "/editImage", method = RequestMethod.PUT)
    public String editImageSubmit(@RequestParam("file") MultipartFile file, @RequestParam("imageId") Integer imageId, @RequestParam("tags") String tags, Image updatedImage, HttpSession session) throws IOException {

        Image image = imageService.getImage(imageId);
        String updatedImageData = convertUploadedFileToBase64(file);
        List<Tag> imageTags = findOrCreateTags(tags);

        if (updatedImageData.isEmpty())
            updatedImage.setImageFile(image.getImageFile());
        else {
            updatedImage.setImageFile(updatedImageData);
        }

        updatedImage.setId(imageId);
        User user = (User) session.getAttribute("loggeduser");
        updatedImage.setUser(user);
        updatedImage.setTags(imageTags);
        updatedImage.setDate(new Date());

        imageService.updateImage(updatedImage);
        return "redirect:/images/" + updatedImage.getTitle();
    }


    //This controller method is called when the request pattern is of type 'deleteImage' and also the incoming request is of DELETE type
    //The method calls the deleteImage() method in the business logic passing the id of the image to be deleted
    //Looks for a controller method with request mapping of type '/images'
    @RequestMapping(value = "/deleteImage", method = RequestMethod.DELETE)
    public String deleteImageSubmit(@RequestParam(name = "imageId") Integer imageId) {
        imageService.deleteImage(imageId);
        return "redirect:/images";
    }


    //This method converts the image to Base64 format
    private String convertUploadedFileToBase64(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }

    //findOrCreateTags() method has been implemented, which returns the list of tags after converting the ‘tags’ string to a list of all the tags and also stores the tags in the database if they do not exist in the database. Observe the method and complete the code where required for this method.
    //Try to get the tag from the database using getTagByName() method. If tag is returned, you need not to store that tag in the database, and if null is returned, you need to first store that tag in the database and then the tag is added to a list
    //After adding all tags to a list, the list is returned
    private List<Tag> findOrCreateTags(String tagNames) {
        StringTokenizer st = new StringTokenizer(tagNames, ",");
        List<Tag> tags = new ArrayList<Tag>();

        while (st.hasMoreTokens()) {
            String tagName = st.nextToken().trim();
            Tag tag = tagService.getTagByName(tagName);

            if (tag == null) {
                Tag newTag = new Tag(tagName);
                tag = tagService.createTag(newTag);
            }
            tags.add(tag);
        }
        return tags;
    }


    //The method receives the list of all tags
    //Converts the list of all tags to a single string containing all the tags separated by a comma
    //Returns the string
    private String convertTagsToString(List<Tag> tags) {
        StringBuilder tagString = new StringBuilder();

        for (int i = 0; i <= tags.size() - 2; i++) {
            tagString.append(tags.get(i).getName()).append(",");
        }

        Tag lastTag = tags.get(tags.size() - 1);
        tagString.append(lastTag.getName());

        return tagString.toString();
    }
}
