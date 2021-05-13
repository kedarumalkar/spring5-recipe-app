/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import java.util.Set;

import guru.springframework.spring5recipeapp.model.Recipe;

/**
 * @author this pc
 *
 */
public interface RecipeService {

	Set<Recipe> getAllRecipes();
	
	Recipe findById(Long id);	
}
