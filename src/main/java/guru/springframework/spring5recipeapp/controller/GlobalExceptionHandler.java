/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author this pc
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("{}", exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400BadRequest");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
}
