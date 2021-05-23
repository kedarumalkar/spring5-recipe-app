/**
 * 
 */
package guru.springframework.spring5recipeapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.service.ImageService;
import guru.springframework.spring5recipeapp.service.RecipeService;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
	
	@Mock
	private ImageService imageService;	
	
	@Mock
	private RecipeService recipeService;
	
	@InjectMocks
	private ImageController imageController;

	
	MockMvc mockMvc;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.controller.ImageController#getUploadForm(java.lang.String, org.springframework.ui.Model)}.
	 * @throws Exception 
	 */
	@Test
	void testGetUploadForm() throws Exception {
		 //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.controller.ImageController#uploadImage(java.lang.String, org.springframework.ui.Model, org.springframework.web.multipart.MultipartFile)}.
	 * @throws Exception 
	 */
	@Test
	void testUploadImage() throws Exception {
		MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}

	@Test
	void testGetImage() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		String s = "some fake content";
		Byte[] image = new Byte[s.getBytes().length];
		
		int i = 0;
		
		for(byte b : s.getBytes()) {
			image[i++] = b;
		}
		
		command.setImage(image);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
											.andExpect(status().isOk())
											.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(responseBytes.length, s.getBytes().length);
	}
}
