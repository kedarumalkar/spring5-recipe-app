/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exception.NotFoundException;
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

		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void showDetails() throws Exception {
		when(recipeService.findById(1L)).thenReturn(recipe);

		mockMvc.perform(get("/recipe/" + 1L+"/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show"));
	}
	
	@Test
	public void showDetails_NotFoundException() throws Exception {
		when(recipeService.findById(1L)).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/" + 1L + "/show")).andExpect(status().isNotFound())
				.andExpect(view().name("404NotFound"));
	}
	
	@Test
	public void showDetails_BadRequestException() throws Exception {
		mockMvc.perform(get("/recipe/one/show")).andExpect(status().isBadRequest())
				.andExpect(view().name("400BadRequest"));
	}

	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/create")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//	                .param("id", "")
//	                .param("description", "some string")
		).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/"+command.getId()+"/show"));
	}

	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		mockMvc.perform(get("/recipe/1/update")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetDeleteView() throws Exception {
		mockMvc.perform(get("/recipe/1/delete")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

       // when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }
}
