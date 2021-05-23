/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author this pc
 *
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;
	
	/**
	 * @param recipeRepository
	 */
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Transactional
	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		Recipe recipe = recipeRepository.findById(recipeId).get();
		try {
			Byte[] byteObject = new Byte[file.getBytes().length];			
			int i = 0;			
			for (Byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
			recipe.setImage(byteObject);			
			recipeRepository.save(recipe);	
			log.info("Image received successfully");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}
