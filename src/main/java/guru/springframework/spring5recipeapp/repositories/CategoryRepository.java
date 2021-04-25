/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring5recipeapp.model.Category;

/**
 * @author this pc
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);
}
