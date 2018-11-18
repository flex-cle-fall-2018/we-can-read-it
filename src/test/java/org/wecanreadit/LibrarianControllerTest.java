package org.wecanreadit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


public class LibrarianControllerTest {

	@InjectMocks
	private LibrarianController underTest;

	@Mock
	private Librarian librarian;
	
	@Mock
	private Librarian librarian2;
	
	@Mock
	private LibrarianRepository librarianRepo;
	
	@Mock
	private Model model;
	
	@Mock
	GroupBookRepository groupBookRepo;
	
	@Mock
	ReadingGroup readingGroup;
	
	@Mock
	ReadingGroupRepository readingGroupRepo;
	
	@Mock
	ReaderRepository readerRepo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldBeAbleToAddASingleLibrarianToModel() throws LibrarianNotFoundException {
		long librarianId = 1; 
		when(librarianRepo.findById(librarianId)).thenReturn(Optional.of(librarian));
		underTest.findOneLibrarian(librarianId, model);
		verify(model).addAttribute("librarian",librarian);
}
	@Test
	public void shouldBeAbleToAddAllLibrarians() {
		Collection<Librarian> allLibrarians = asList(librarian, librarian2);
		when(librarianRepo.findAll()).thenReturn(allLibrarians);
		underTest.findAllLibrarians(model);
		verify(model).addAttribute("librarians", allLibrarians);
	}
	
	
	

	
}