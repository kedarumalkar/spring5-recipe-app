/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.service.RecipeService;

/**
 * @author this pc
 *
 */
@Controller
public class IndexController {
	
	private final RecipeService recipeService;

	/**
	 * @param recipeService
	 */
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
	public String getIndex(Model model) {
		model.addAttribute("recipes", recipeService.getAllRecipes());
		return "recipes";
	}
	
}
