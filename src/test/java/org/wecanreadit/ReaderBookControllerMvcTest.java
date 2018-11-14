package org.wecanreadit;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

@WebMvcTest(ReaderBookController.class)
public class ReaderBookControllerMvcTest {

		@Mock
		private ReaderBook readerBook;

		@Mock
		private ReaderBook readerBook2;
		
		@Mock
		private Reader reader;

		@MockBean
		private ReaderBookRepository readerBookRepo;
		
		@MockBean
		private ReaderRepository readerRepo;

		@Resource
		private MockMvc mvc;


		@Test
		public void shouldBeOkForSingleReaderBook() throws Exception {
			long readerBookId = 1;
			when(readerBookRepo.findById(readerBookId)).thenReturn(Optional.of(readerBook));
			mvc.perform(get("/readerBook?id=1")).andExpect(status().isOk());
		}

		@Test
		public void shouldRouteToSingleReaderBook() throws Exception {
			long readerBookId = 1;
			when(readerBookRepo.findById(readerBookId)).thenReturn(Optional.of(readerBook));
			mvc.perform(get("/readerBook?id=1")).andExpect(view().name(is("readerBook")));
		}

		@Test
		public void shouldPutAReaderBookIntoModel() throws Exception {
			when(readerBookRepo.findById(1L)).thenReturn(Optional.of(readerBook));	
			mvc.perform(get("/readerBook?id=1")).andExpect(model().attribute("readerBook",is(readerBook)));
		}
		
		

}

