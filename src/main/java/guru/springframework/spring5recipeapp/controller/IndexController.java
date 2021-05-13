/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author this pc
 *
 */
@Slf4j
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
		log.debug("index page");
		model.addAttribute("recipes", recipeService.getAllRecipes());
		return "index";
	}
	
}
