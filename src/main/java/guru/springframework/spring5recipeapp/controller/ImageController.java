/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.service.ImageService;
import guru.springframework.spring5recipeapp.service.RecipeService;

/**
 * @author this pc
 *
 */
@Controller
public class ImageController {
	
	private final ImageService imageService;	
	private final RecipeService recipeService;

	/**
	 * @param imageService
	 */
	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("recipe/{id}/image")
	public String getUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", 
				recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/imageuploadform";
	}
	
	
	@PostMapping("recipe/{id}/image")
	public String uploadImage(@PathVariable String id, Model model, 
			@RequestParam("imagefile") MultipartFile file) {
		model.addAttribute("recipe", 
				recipeService.findCommandById(Long.valueOf(id)));
		imageService.saveImageFile(Long.valueOf(id), file);
		return "redirect:/recipe/" + id + "/show";
	}

	@GetMapping("recipe/{id}/recipeImage")
	public void getImge(@PathVariable String id, HttpServletResponse response) throws IOException {
		RecipeCommand command = recipeService.findCommandById(Long.valueOf(id));
		
		Byte[] recipeImage = command.getImage();
		byte[] image = null;
		if (recipeImage != null) {
			image = new byte[recipeImage.length];
			int i = 0;
			for (Byte b : recipeImage) {
				image[i] = b;
				i++;
			}
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(image);
			IOUtils.copy(is, response.getOutputStream());
		} else {
			Resource resource = new ClassPathResource("./static/images/guacamole400x400WithX.jpg");
			image = Files.readAllBytes(Paths.get(resource.getFile().getPath()));
			
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(image);
			IOUtils.copy(is, response.getOutputStream());
		}
	}
}
