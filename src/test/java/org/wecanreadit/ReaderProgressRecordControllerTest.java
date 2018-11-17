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

public class ReaderProgressRecordControllerTest {
	@InjectMocks
	private ReaderProgressRecordController underTest;

	@Mock
	private ReaderProgressRecord readerProgress;
	
	@Mock
	private ReaderProgressRecord readerProgress2;
	
	@Mock
	private ReaderProgressRecordRepository readerProgressRepo;
	
	@Mock
	private Model model;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldBeAbleToAddASingleReaderBookToModel() throws ReaderProgressRecordNotFoundException {
		long readerProgressId = 1; 
		when(readerProgressRepo.findById(readerProgressId)).thenReturn(Optional.of(readerProgress));
		underTest.findOneReaderProgressRecord(readerProgressId, model);
		verify(model).addAttribute("readerProgressRecord", readerProgress);
}

}
