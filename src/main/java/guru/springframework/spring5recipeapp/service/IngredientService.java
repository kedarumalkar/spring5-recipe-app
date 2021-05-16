/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;

/**
 * @author this pc
 *
 */
public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredient(IngredientCommand ingredientCommand);
	
	void deleteIngredient(Long recipeId, Long ingredientId);
}
