/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exception.NotFoundException;
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

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
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
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping
	@RequestMapping("/recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {		
		if (bindingResult.hasErrors()) {

			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});

			return RECIPE_RECIPEFORM_URL;
		}
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
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
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("{}", exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404NotFound");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
}
