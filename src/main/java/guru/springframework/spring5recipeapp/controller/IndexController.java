/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author this pc
 *
 */
@Controller
public class IndexController {

	@RequestMapping({"", "/", "/index"})
	public String getIndex() {
		return "index";
	}
}
