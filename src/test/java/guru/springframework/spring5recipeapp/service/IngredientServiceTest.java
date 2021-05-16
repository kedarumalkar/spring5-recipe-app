/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.model.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
    	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	
	IngredientCommandToIngredient ingredientCommandToIngredient;
		
	IngredientServiceImpl ingredientService;
	
	//init converters
    public IngredientServiceTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {		
		MockitoAnnotations.openMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, 
				ingredientCommandToIngredient, unitOfMeasureRepository);
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.service.IngredientServiceImpl#findByRecipeIdAndIngredientId(java.lang.Long, java.lang.Long)}.
	 */
	@Test
	void testFindByRecipeIdAndIngredientId() {
		// given
		Recipe recipe = Recipe.builder().id(1L).build();
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
					
		// when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		// then
		assertNotNull(ingredientCommand);
		assertEquals(3L, ingredientCommand.getId());
		assertEquals(1L, ingredientCommand.getRecipeId());
	}
	
	@Test
	void testSaveIngredient() {
		// given
		Recipe recipe = Recipe.builder().id(1L).build();
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(1L);
		uomCommand.setDescription("Each");
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(1L);
		uom.setDescription("Each");
		
		Optional<UnitOfMeasure> uomOptional = Optional.of(uom);
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		ingredientCommand.setRecipeId(1L);
		ingredientCommand.setAmount(BigDecimal.valueOf(2.0));
		ingredientCommand.setDescription("chocos");
		ingredientCommand.setUom(uomCommand);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		// when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(unitOfMeasureRepository.findById(anyLong())).thenReturn(uomOptional);
		when(recipeRepository.save(any())).thenReturn(recipe);
		
		IngredientCommand foundIngredient = ingredientService.saveIngredient(ingredientCommand);
		
		// then
		assertNotNull(foundIngredient);
		assertEquals(1L, foundIngredient.getId());
		assertEquals("chocos", ingredientCommand.getDescription());
	}
	
	@Test
	void testSaveNewIngredient() {
		// given
		Recipe recipe = Recipe.builder().id(1L).build();
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(1L);
		uomCommand.setDescription("Each");
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(1L);
		uom.setDescription("Each");
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(4L);
		ingredientCommand.setRecipeId(1L);
		ingredientCommand.setAmount(BigDecimal.valueOf(2.0));
		ingredientCommand.setDescription("chocos");
		ingredientCommand.setUom(uomCommand);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		// when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(recipe);
		
		IngredientCommand newIngredient = ingredientService.saveIngredient(ingredientCommand);
		
		// then
		assertNotNull(newIngredient);
		assertEquals(4L, newIngredient.getId());
		assertEquals("chocos", ingredientCommand.getDescription());
	}
	
	@Test
	void testDeleteIngredient() {
		// given
		Recipe recipe = Recipe.builder().id(1L).build();
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		ingredient1.setRecipe(recipe);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		ingredient2.setRecipe(recipe);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		ingredient3.setRecipe(recipe);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		// when		
		ingredientService.deleteIngredient(1L, 3L);
		
		// then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any());
	}
}
