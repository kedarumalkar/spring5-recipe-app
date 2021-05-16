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

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UomService;

/**
 * @author this pc
 *
 */
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	
	private final IngredientService ingredientService;
	
	private final UomService uomService;

	/**
	 * @param recipeService
	 */
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UomService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", 
				recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/"+Long.valueOf(recipeId)+"/ingredients";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/new")
	public String createIngredient(@PathVariable String recipeId, Model model) {
		
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		ingredientCommand.setUom(new UnitOfMeasureCommand());
				
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", uomService.findAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", uomService.findAllUoms());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {		
		IngredientCommand savedCommand = ingredientService.saveIngredient(command);
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
}
