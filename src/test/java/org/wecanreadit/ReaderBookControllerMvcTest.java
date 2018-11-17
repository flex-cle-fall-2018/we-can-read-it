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

@WebMvcTest(ReaderProgressRecordController.class)
public class ReaderBookControllerMvcTest {

		@Mock
		private ReaderProgressRecord readerProgress;

		@Mock
		private ReaderProgressRecord readerProgress2;
		
		@Mock
		private Reader reader;
		
		@Mock
		private GroupBook groupBook;

		@MockBean
		private ReaderProgressRecordRepository readerProgressRecordRepo;
		
		@MockBean
		private ReaderRepository readerRepo;
		
		@MockBean
		private GroupBookRepository groupBookRepo;

		@Resource
		private MockMvc mvc;


		@Test
		public void shouldBeOkForSingleReaderProgressRecord() throws Exception {
			long readerBookId = 1;
			when(readerProgressRecordRepo.findById(readerBookId)).thenReturn(Optional.of(readerProgress));
			when(readerProgress.getGroupBook()).thenReturn(groupBook);
			when(readerProgress.getReader()).thenReturn(reader);
			mvc.perform(get("/readerProgressRecord?id=1")).andExpect(status().isOk());
		}

		@Test 
		public void shouldRouteToSingleReaderProgressRecord() throws Exception {
			long readerProgressId = 1;
			when(readerProgressRecordRepo.findById(readerProgressId)).thenReturn(Optional.of(readerProgress));
			when(readerProgress.getGroupBook()).thenReturn(groupBook);
			when(readerProgress.getReader()).thenReturn(reader);
			mvc.perform(get("/readerProgressRecord?id=1")).andExpect(view().name(is("readerProgressRecord")));
		}

		@Test
		public void shouldPutAReaderProgressRecordIntoModel() throws Exception {
			when(readerProgressRecordRepo.findById(1L)).thenReturn(Optional.of(readerProgress));
			when(readerProgress.getGroupBook()).thenReturn(groupBook);
			when(readerProgress.getReader()).thenReturn(reader);
			mvc.perform(get("/readerProgressRecord?id=1")).andExpect(model().attribute("readerProgressRecord",is(readerProgress)));
		}
		
		

}

