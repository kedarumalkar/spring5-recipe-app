/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
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
public class IndexControllerTest {

	@Mock
	private RecipeService recipeService;
	
	@InjectMocks
	private IndexController indexController;
	
	MockMvc mockMvc;
	
	Set<Recipe> recipeSet;
	
	@BeforeEach
	public void setup() {
		recipeSet = new HashSet<>();		
		recipeSet.add(Recipe.builder().description("my recipe").build());
		recipeSet.add(Recipe.builder().id(1L).build());
		
		mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
	}

	@Test
	public void testMvcMock() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}
	

	@Test
	public void getIndex() throws Exception {
		when(recipeService.getAllRecipes()).thenReturn(recipeSet);
		
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attribute("recipes", Matchers.hasSize(2)));
	}
}
