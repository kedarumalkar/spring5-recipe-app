/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author this pc
 *
 */
@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	/**
	 * @param recipeService
	 */
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String showDetails(@PathVariable String id, Model model) {
		Recipe recipe = recipeService.findById(Long.valueOf(id));
		
		if(recipe == null) {
			throw new RuntimeException("Recipe does not exist");
		}		
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/create")
	public String create(Model model) {		
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping("/recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);		
		return "redirect:/recipe/"+savedCommand.getId()+"/show";
	}
	
	@PostMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));	
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		recipeService.deleteById(Long.valueOf(id));
		
		log.debug("Deleting id: " + id);
		return "redirect:/";
	}
}
