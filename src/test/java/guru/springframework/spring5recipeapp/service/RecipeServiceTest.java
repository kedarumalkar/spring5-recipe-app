/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	
	@InjectMocks
	RecipeServiceImpl recipeServiceImpl;
	
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
		
		Set<Recipe> recipeSet = recipeServiceImpl.getAllRecipes();
		
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
		
		Recipe recipe = recipeServiceImpl.findById(1L);
		
		verify(recipeRepository).findById(1L);
		assertNotNull(recipe);
	}

}
