/**
 * 
 */
package guru.springframework.spring5recipeapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.model.Difficulty;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Notes;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

/**
 * @author this pc
 *
 */
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final RecipeRepository recipeRepository;

	/**
	 * @param categoryRepository
	 * @param unitOfMeasureRepository
	 * @param recipeRepository
	 */
	public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			RecipeRepository recipeRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.recipeRepository = recipeRepository;
	}

		
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		List<Recipe> recipes = new ArrayList<>();
		
		recipes.add(createPerfectGaucamoleRecipe());
		recipes.add(createSpicyGrilledChickenTacos());
		
		recipeRepository.saveAll(recipes);
	}

	/**
	 * 
	 */
	private Recipe createSpicyGrilledChickenTacos() {
		Recipe spicyGrilledChickenTacos = new Recipe();
		spicyGrilledChickenTacos.getCategories().add(categoryRepository.findByCategoryName("Mexican").get());
		spicyGrilledChickenTacos.setCookTime(15);
		spicyGrilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
		spicyGrilledChickenTacos.setDifficulty(Difficulty.HARD);
		spicyGrilledChickenTacos.setPrepTime(20);
		spicyGrilledChickenTacos.setServings(6);
		spicyGrilledChickenTacos.setSource("Simply Recipes");
		spicyGrilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		spicyGrilledChickenTacos.setDirections("1. Prepare a gas or charcoal grill for medium-high, direct heat.\n\n "
				+ "2. In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\r\n"
				+ "\r\n"
				+ "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n\n "
				+ "3. Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n\n "
				+ "4. Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\r\n"
				+ "\r\n"
				+ "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n\n "
				+ "5. Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
		
		Notes notes = new Notes();
		
		notes.setNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. "
				+ "(If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
	
		notes.setRecipe(spicyGrilledChickenTacos);
		spicyGrilledChickenTacos.setNotes(notes);
		
		Ingredient anchoChilliPowder = new Ingredient();
		anchoChilliPowder.setDescription("ancho chili powder");
		anchoChilliPowder.setAmount(new BigDecimal(2));
		anchoChilliPowder.setRecipe(spicyGrilledChickenTacos);
		anchoChilliPowder.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(anchoChilliPowder);
				
		Ingredient driedOregano = new Ingredient();
		driedOregano.setDescription("dried Oregano");
		driedOregano.setAmount(new BigDecimal(1));
		driedOregano.setRecipe(spicyGrilledChickenTacos);
		driedOregano.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(driedOregano);
		
		Ingredient driedCumin = new Ingredient();
		driedCumin.setDescription("dried Cumin");
		driedCumin.setAmount(new BigDecimal(1));
		driedCumin.setRecipe(spicyGrilledChickenTacos);
		driedCumin.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(driedCumin);
		
		Ingredient sugar = new Ingredient();
		sugar.setDescription("sugar");
		sugar.setAmount(new BigDecimal(1));
		sugar.setRecipe(spicyGrilledChickenTacos);
		sugar.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(sugar);
		
		Ingredient salt = new Ingredient();
		salt.setDescription("salt");
		salt.setAmount(new BigDecimal(0.5));
		salt.setRecipe(spicyGrilledChickenTacos);
		salt.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(salt);
		
		Ingredient cloveGarlic = new Ingredient();
		cloveGarlic.setDescription("clove garlic, finely chopped");
		cloveGarlic.setAmount(new BigDecimal(1));
		cloveGarlic.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(cloveGarlic);
		
		Ingredient orangeZest = new Ingredient();
		orangeZest.setDescription("finely grated orange zest");
		orangeZest.setAmount(new BigDecimal(1));
		orangeZest.setRecipe(spicyGrilledChickenTacos);
		orangeZest.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(orangeZest);
		
		Ingredient orangeJuice = new Ingredient();
		orangeJuice.setDescription("fresh-squeezed orange juice");
		orangeJuice.setAmount(new BigDecimal(3));
		orangeJuice.setRecipe(spicyGrilledChickenTacos);
		orangeJuice.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(orangeJuice);
		
		Ingredient oliveOil = new Ingredient();
		oliveOil.setDescription("Olive Oil");
		oliveOil.setAmount(new BigDecimal(2));
		oliveOil.setRecipe(spicyGrilledChickenTacos);
		oliveOil.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		spicyGrilledChickenTacos.getIngredients().add(oliveOil);
		
		Ingredient bonelessChickenThighs = new Ingredient();
		bonelessChickenThighs.setDescription("skinless, boneless chicken thighs");
		bonelessChickenThighs.setAmount(new BigDecimal(1.25));
		bonelessChickenThighs.setRecipe(spicyGrilledChickenTacos);
		bonelessChickenThighs.setUom(unitOfMeasureRepository.findByDescription("Pound").get());
		
		spicyGrilledChickenTacos.getIngredients().add(bonelessChickenThighs);
		
		Ingredient smallCornTortillas = new Ingredient();
		smallCornTortillas.setDescription("small corn tortillas");
		smallCornTortillas.setAmount(new BigDecimal(8));
		smallCornTortillas.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(smallCornTortillas);
		
		Ingredient babyArugula = new Ingredient();
		babyArugula.setDescription("packed baby arugula");
		babyArugula.setAmount(new BigDecimal(3));
		babyArugula.setRecipe(spicyGrilledChickenTacos);
		babyArugula.setUom(unitOfMeasureRepository.findByDescription("Ounce").get());
		
		spicyGrilledChickenTacos.getIngredients().add(babyArugula);
		
		Ingredient avocado = new Ingredient();
		avocado.setDescription("medium ripe avocados, sliced");
		avocado.setAmount(new BigDecimal(2));
		avocado.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(avocado);
		
		Ingredient radish = new Ingredient();
		radish.setDescription("radishes, thinly sliced");
		radish.setAmount(new BigDecimal(4));
		radish.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(radish);
		
		Ingredient cherryTomatoes = new Ingredient();
		cherryTomatoes.setDescription("cherry tomatoes, halved");
		cherryTomatoes.setAmount(new BigDecimal(0.2));
		cherryTomatoes.setRecipe(spicyGrilledChickenTacos);
		cherryTomatoes.setUom(unitOfMeasureRepository.findByDescription("Pint").get());
		
		spicyGrilledChickenTacos.getIngredients().add(cherryTomatoes);
		
		Ingredient redOnion = new Ingredient();
		redOnion.setDescription("red onion, thinly sliced");
		redOnion.setAmount(new BigDecimal(0.5));
		redOnion.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(redOnion);
		
		Ingredient cilantro = new Ingredient();
		cilantro.setDescription("Roughly chopped cilantro");
		cilantro.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(cilantro);
		
		Ingredient cream = new Ingredient();
		cream.setDescription("sour cream");
		cream.setAmount(new BigDecimal(0.5));
		cream.setRecipe(spicyGrilledChickenTacos);
		cream.setUom(unitOfMeasureRepository.findByDescription("Cup").get());
		
		spicyGrilledChickenTacos.getIngredients().add(cream);
		
		Ingredient milk = new Ingredient();
		milk.setDescription("milk");
		milk.setAmount(new BigDecimal(0.25));
		milk.setRecipe(spicyGrilledChickenTacos);
		milk.setUom(unitOfMeasureRepository.findByDescription("Cup").get());
		
		spicyGrilledChickenTacos.getIngredients().add(milk);
		
		Ingredient lime = new Ingredient();
		lime.setDescription("lime, cut into wedges");
		lime.setRecipe(spicyGrilledChickenTacos);
		
		spicyGrilledChickenTacos.getIngredients().add(lime);
		
		System.out.println("Spicy Grilled Chicken Tacos recipe created ...");
		return spicyGrilledChickenTacos;
	}

	/**
	 * 
	 */
	private Recipe createPerfectGaucamoleRecipe() {
		Recipe perfectGuacamole = new Recipe();
		
		perfectGuacamole.getCategories().add(categoryRepository.findByCategoryName("American").get());		
		perfectGuacamole.setCookTime(10);
		perfectGuacamole.setDescription("How to Make Perfect Guacamole");
		perfectGuacamole.setDifficulty(Difficulty.MODERATE);
		perfectGuacamole.setPrepTime(10);
		perfectGuacamole.setServings(4);
		perfectGuacamole.setSource("Simply Recipes");
		perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		
		perfectGuacamole.setDirections("1. Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon\n\n "
				+ "2. Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n\n "
				+ "3.Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
				+ "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
				+ "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
				+ "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n\n "
				+ "4. Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");
		
		Notes notes = new Notes();
		
		notes.setNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
		
		notes.setRecipe(perfectGuacamole);
		
		perfectGuacamole.setNotes(notes);
		
		Ingredient avocado = new Ingredient();
		avocado.setDescription("ripe avocados");
		avocado.setAmount(new BigDecimal(2));
		avocado.setRecipe(perfectGuacamole);
		
		perfectGuacamole.getIngredients().add(avocado);
						
		Ingredient salt = new Ingredient();
		salt.setDescription("salt, more to taste");
		salt.setAmount(new BigDecimal(0.4));
		salt.setRecipe(perfectGuacamole);
		salt.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
		
		perfectGuacamole.getIngredients().add(salt);

		Ingredient lemonJuice = new Ingredient();
		lemonJuice.setDescription("fresh lime juice or lemon juice");
		lemonJuice.setAmount(new BigDecimal(1));
		lemonJuice.setRecipe(perfectGuacamole);
		lemonJuice.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		perfectGuacamole.getIngredients().add(lemonJuice);
		
		Ingredient mincedRedOnion = new Ingredient();
		mincedRedOnion.setDescription("minced red onion or thinly sliced green onion");
		mincedRedOnion.setAmount(new BigDecimal(0.4));
		mincedRedOnion.setRecipe(perfectGuacamole);
		mincedRedOnion.setUom(unitOfMeasureRepository.findByDescription("Cup").get());
		
		perfectGuacamole.getIngredients().add(mincedRedOnion);
		
		Ingredient serranoChiles = new Ingredient();
		serranoChiles.setDescription("serrano chiles, stems and seeds removed, minced");
		serranoChiles.setAmount(new BigDecimal(2));
		serranoChiles.setRecipe(perfectGuacamole);
		
		perfectGuacamole.getIngredients().add(serranoChiles);
		
		Ingredient cilantro = new Ingredient();
		cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
		cilantro.setAmount(new BigDecimal(2));
		cilantro.setRecipe(perfectGuacamole);
		cilantro.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
		
		perfectGuacamole.getIngredients().add(cilantro);
		
		Ingredient blackPepper = new Ingredient();
		blackPepper.setDescription("freshly grated black pepper");
		blackPepper.setAmount(new BigDecimal(1));
		blackPepper.setRecipe(perfectGuacamole);
		blackPepper.setUom(unitOfMeasureRepository.findByDescription("Dash").get());
		
		perfectGuacamole.getIngredients().add(blackPepper);
		
		Ingredient ripeTomato = new Ingredient();
		ripeTomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
		ripeTomato.setAmount(new BigDecimal(0.5));
		ripeTomato.setRecipe(perfectGuacamole);
		
		perfectGuacamole.getIngredients().add(ripeTomato);
		
		Ingredient redRadishes = new Ingredient();
		redRadishes.setDescription("Red radishes or jicama, to garnish");
		redRadishes.setRecipe(perfectGuacamole);
		
		perfectGuacamole.getIngredients().add(redRadishes);
		
		Ingredient tortillaChips = new Ingredient();
		tortillaChips.setDescription("Tortilla chips, to serve");
		tortillaChips.setRecipe(perfectGuacamole);
		
		perfectGuacamole.getIngredients().add(tortillaChips);
		
		System.out.println("Perfect Guacamole recipe created ...");
		return perfectGuacamole;
	}

	
}
