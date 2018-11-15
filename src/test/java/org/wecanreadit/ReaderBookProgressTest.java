package org.wecanreadit;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ReaderBookProgressTest {
	
	@Resource
	ReaderBookProgressRepository readerBookRepo;

	
	@Test
	 public void testFindById(){
	     Optional<ReaderBookProgress> byId = readerBookRepo.findById(1L);
	     assertTrue(byId != null);
	 }
}
