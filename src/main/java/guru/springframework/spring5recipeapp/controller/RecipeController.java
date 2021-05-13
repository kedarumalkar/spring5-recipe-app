/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.RecipeService;

/**
 * @author this pc
 *
 */
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	/**
	 * @param recipeService
	 */
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/show/{id}")
	public String showDetails(@PathVariable String id, Model model) {
		Recipe recipe = recipeService.findById(Long.valueOf(id));
		
		if(recipe == null) {
			throw new RuntimeException("Recipe does not exist");
		}		
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}
}
