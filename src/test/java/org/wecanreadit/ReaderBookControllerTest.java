package org.wecanreadit;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class ReaderBookControllerTest {
	@InjectMocks
	private ReaderBookController underTest;

	@Mock
	private ReaderBook readerBook;
	
	@Mock
	private ReaderBook readerBook2;
	
	@Mock
	private ReaderBookRepository readerBookRepo;
	
	@Mock
	private Model model;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldBeAbleToAddASingleReaderBookToModel() throws ReaderBookNotFoundException {
		long readerBookId = 1; 
		when(readerBookRepo.findById(readerBookId)).thenReturn(Optional.of(readerBook));
		underTest.findOneReaderBook(readerBookId, model);
		verify(model).addAttribute("readerBook", readerBook);
}

}
