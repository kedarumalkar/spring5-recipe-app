/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.exception.NotFoundException;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@InjectMocks
	RecipeServiceImpl recipeService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.service.RecipeServiceImpl#getAllRecipes()}.
	 */
	@Test
	void testGetAllRecipes() {
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(Recipe.builder().id(1L).build());
		recipes.add(Recipe.builder().id(2L).build());
		
		when(recipeRepository.findAll()).thenReturn(recipes);
		
		Set<Recipe> recipeSet = recipeService.getAllRecipes();
		
		verify(recipeRepository).findAll();
		assertNotNull(recipeSet);
		assertEquals(2, recipeSet.size());
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.service.RecipeServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	void testFindById() {

		Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().id(1L).build());
		
		when(recipeRepository.findById(1L)).thenReturn(recipeOptional);
		
		Recipe recipe = recipeService.findById(1L);
		
		verify(recipeRepository).findById(1L);
		assertNotNull(recipe);
	}
	
	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.service.RecipeServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	void testFindCommandById() {

		Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull(commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
	}

	@Test
	void testDeleteById() {
		
		//given
		Long idToDelete = Long.valueOf(1L);
		
		//when
		recipeService.deleteById(idToDelete);
		
		//then
		verify(recipeRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void testRecipeNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        
        Exception exception = assertThrows(NotFoundException.class, () -> {
        	recipeService.findById(1L);
        });
        
        assertEquals("Could not find a Recipe with ID : 1", exception.getMessage());
	}
}
