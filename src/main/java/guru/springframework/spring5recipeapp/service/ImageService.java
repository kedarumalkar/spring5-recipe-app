/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author this pc
 *
 */
public interface ImageService {
	 void saveImageFile(Long recipeId, MultipartFile file);
}
