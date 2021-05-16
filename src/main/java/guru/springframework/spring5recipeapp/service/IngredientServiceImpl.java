/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author this pc
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    
	/**
	 * @param recipeRepository
	 * @param ingredientToIngredientCommand
	 */
	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	@Transactional
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(recipeOptional.isEmpty()) {
			log.error("Recipe not found for id {}", recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if(ingredientCommandOptional.isEmpty()) {
			log.error("No ingredient {} found in Recipe {}", ingredientId, recipeId);
		}
		
		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe not found with id {}", ingredientCommand.getRecipeId());
			return ingredientCommand;
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
		
		if(ingredientOptional.isPresent()) {
			Ingredient savedIngredient = ingredientOptional.get();
			
			savedIngredient.setAmount(ingredientCommand.getAmount());
			savedIngredient.setDescription(ingredientCommand.getDescription());
			savedIngredient.setUom(unitOfMeasureRepository.findById(
					ingredientCommand.getUom().getId()).orElseThrow(()-> new RuntimeException("UOM not found")));
			
		} else {
			Ingredient savedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
			savedIngredient.setRecipe(recipe);
			recipe.addIngredient(savedIngredient);
		}
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(savedIngredient -> savedIngredient.getId().equals(ingredientCommand.getId()))
				.findFirst();
		
		if(!savedIngredientOptional.isPresent()) {
			log.info("Adding a new ingredient ");
			// add new ingredient
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(savedIngredient -> savedIngredient.getDescription().equals(ingredientCommand.getDescription()))
					.filter(savedIngredient -> savedIngredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(savedIngredient -> savedIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
					.findFirst();
		}
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteIngredient(Long recipeId, Long ingredientId) {
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(recipeOptional.isEmpty()) {
			log.error("Recipe not found for id {}", recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
		
		if(ingredientOptional.isPresent()) {
			Ingredient ingredientToBeDeleted = ingredientOptional.get();	
			ingredientToBeDeleted.setRecipe(null);
			Set<Ingredient> recipeIngredients = recipe.getIngredients();
			
			recipeIngredients.remove(ingredientToBeDeleted);
			
			log.debug("successfully deleted ingredient with id {}", ingredientId);
			recipe.setIngredients(recipeIngredients);
			recipeRepository.save(recipe);
		} else {
			log.error("Ingredient {} does not exist!", ingredientId);
		}
		
	}
}
