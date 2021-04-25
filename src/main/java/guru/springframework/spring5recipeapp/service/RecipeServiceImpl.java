/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

/**
 * @author this pc
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;

	/**
	 * @param recipeRepository
	 */
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getAllRecipes() {		
		Set<Recipe> recipes = new HashSet<>();		
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}
}
