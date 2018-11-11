package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BookRestController {
	
	@Resource
	BookRepository bookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	
	
	
	
	
	
	
	

}
