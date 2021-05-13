/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.RecipeService;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

	@Mock
	private RecipeService recipeService;
	
	@InjectMocks
	private RecipeController recipeController;
	
	MockMvc mockMvc;
	
	Recipe recipe;
	
	@BeforeEach
	public void setup() {
		recipe = Recipe.builder().id(1L).build();
		
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}
	
	@Test
	public void showDetails() throws Exception {
		when(recipeService.findById(1L)).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/show/"+1L))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"));
	}
}
