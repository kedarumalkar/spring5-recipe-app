/**
 * 
 */
package guru.springframework.spring5recipeapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author this pc
 *
 */
@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String notes;
	
	@OneToOne
	private Recipe recipe;
}
