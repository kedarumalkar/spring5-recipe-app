/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import java.util.Set;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;

/**
 * @author this pc
 *
 */
public interface UomService {

	Set<UnitOfMeasureCommand> findAllUoms();
}
