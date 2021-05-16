/**
 * 
 */
package guru.springframework.spring5recipeapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

/**
 * @author this pc
 *
 */
@ExtendWith(MockitoExtension.class)
class UomServiceTest {

	@Mock
	UnitOfMeasureRepository uomRepository;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	UomService uomService;
	
	public UomServiceTest() {
		this.unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		uomService = new UomServiceImpl(uomRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	/**
	 * Test method for {@link guru.springframework.spring5recipeapp.service.UomServiceImpl#findAllUoms()}.
	 */
	@Test
	void testFindAllUoms() {
		
		// given
		Set<UnitOfMeasure> uomSet = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		uom1.setDescription("Each");
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		uom2.setDescription("Ounce");
		
		uomSet.add(uom1);
		uomSet.add(uom2);
		
		// when
		when(uomRepository.findAll()).thenReturn(uomSet);
		
		Set<UnitOfMeasureCommand> uomCommands = uomService.findAllUoms();
	
		// then
		assertNotNull(uomCommands);
		assertEquals(2, uomCommands.size());
	}

}
