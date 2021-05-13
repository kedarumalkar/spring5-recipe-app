/**
 * 
 */
package guru.springframework.spring5recipeapp.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author this pc
 *
 */
@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String categoryName;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Recipe> recipes;
}
