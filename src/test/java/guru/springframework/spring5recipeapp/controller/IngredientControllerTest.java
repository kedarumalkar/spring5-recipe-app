/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UomService;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

	@Mock
	private RecipeService recipeService;
	
	@Mock
	private UomService uomService;
	
	@Mock
	private IngredientService ingredientService;

	@InjectMocks
	private IngredientController ingredientController;

	MockMvc mockMvc;

	Recipe recipe;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		recipe = Recipe.builder().id(1L).build();

		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.controller.IngredientController#listIngredients(java.lang.String, org.springframework.ui.Model)}.
	 * @throws Exception 
	 */
	@Test
	void testListIngredients() throws Exception {
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/"+recipe.getId()+"/ingredients")).andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/list"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	void testshowIngredient() throws Exception {
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		
		mockMvc.perform(get("/recipe/"+recipe.getId()+"/ingredient/"+ingredientCommand.getId()+"/show"))
		.andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
	}

	@Test
	void testUpdateIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		
		Set<UnitOfMeasureCommand> uomSet = new HashSet<>();
		
		UnitOfMeasureCommand uom1 = new UnitOfMeasureCommand();
		uom1.setId(1L);
		uom1.setDescription("Each");
		
		UnitOfMeasureCommand uom2 = new UnitOfMeasureCommand();
		uom2.setId(2L);
		uom2.setDescription("Ounce");
		
		uomSet.add(uom1);
		uomSet.add(uom2);
		
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(uomService.findAllUoms()).thenReturn(uomSet);
		
		mockMvc.perform(get("/recipe/"+recipe.getId()+"/ingredient/"+ingredientCommand.getId()+"/update"))
		.andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	void testSaveOrUpdateIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		ingredientCommand.setRecipeId(1L);
		
		when(ingredientService.saveIngredient(any())).thenReturn(ingredientCommand);
		
		mockMvc.perform(get("/recipe/"+recipe.getId()+"/ingredient"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/"+ingredientCommand.getRecipeId()+"/ingredient/"+ingredientCommand.getId()+"/show"));
	}
	
	@Test
	void testCreateIngredient() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/"+recipe.getId()+"/ingredient/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
	}
	
	
	@Test
	void testDeleteIngredient() throws Exception {
		
		//doNothing().when(ingredientService).deleteIngredient(anyLong(), anyLong());
		
		mockMvc.perform(get("/recipe/2/ingredient/12/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/ingredients"));
		
		verify(ingredientService).deleteIngredient(2L, 12L);
	}
}
