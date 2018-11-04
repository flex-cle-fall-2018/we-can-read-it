package org.wecanreadit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)

@WebMvcTest(LibrarianController.class)
public class LibrarianControllerMvcTest {

	@Mock
	private Librarian librarian;

	@Mock
	private Librarian librarian2;

	@MockBean
	private LibrarianRepository librarianRepo;

	@Resource
	private MockMvc mvc;

	@Test
	public void shouldBeOkForAllLibrarians() throws Exception {
		mvc.perform(get("/show-librarians")).andExpect(status().isOk());
	}

	@Test
	public void shouldRouteToAllLibrarians() throws Exception {
		mvc.perform(get("/show-librarians")).andExpect(view().name(is("librarians")));
	}

	@Test
	public void shouldBeOkForSingleLibrarian() throws Exception {
		long librarianId = 1;
		when(librarianRepo.findById(librarianId)).thenReturn(Optional.of(librarian));
		mvc.perform(get("/librarian?id=1")).andExpect(status().isOk());
	}
	@Test
	public void shouldPutAllLibrariansIntoModel() throws Exception {
		Collection<Librarian> allLibrarians = asList(librarian, librarian2);
		when(librarianRepo.findAll()).thenReturn(allLibrarians);
		mvc.perform(get("/show-librarians")).andExpect(model().attribute("librarians", is(allLibrarians)));
	}

	@Test
	public void shouldRouteToSingleLibrarian() throws Exception {
		long librarianId = 1;
		when(librarianRepo.findById(librarianId)).thenReturn(Optional.of(librarian));
		mvc.perform(get("/librarian?id=1")).andExpect(view().name(is("librarian")));
	}

	@Test
	public void shouldPutALibrarianIntoModel() throws Exception {
		when(librarianRepo.findById(1L)).thenReturn(Optional.of(librarian));	
		mvc.perform(get("/librarian?id=1")).andExpect(model().attribute("librarian",is(librarian)));
	}

}
