package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Controller
public class ReaderProgressRecordController {
	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;

	@Resource
	ReaderRepository readerRepo;

	@RequestMapping("/readerProgressRecord")
	public String findOneReaderProgressRecord(@RequestParam(required = true) long id, Model model)
			throws ReaderProgressRecordNotFoundException {
		boolean isOwner = false;
		Optional<ReaderProgressRecord> readerProgressRecord = readerProgressRecordRepo.findById(id);
		
		Reader profileOwner = readerProgressRecord.get().getReader();
		
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder
		        .getRequestAttributes())
				.getRequest();
	    	
	    	Cookie readerIdCookie = WebUtils.getCookie(request, "readerId");
	    
	    	
	    	if (readerIdCookie != null) {
	    		Long readerId = new Long(readerIdCookie.getValue());
	    		Reader readerLoggedIn = readerRepo.findById(readerId).get();
	    		if(readerLoggedIn == profileOwner) {
	    			isOwner = true;
	    		}
	    	}
	
		if (readerProgressRecord.isPresent()) {
			
			model.addAttribute("isOwner", isOwner);	
			model.addAttribute("readerProgressRecord", readerProgressRecord.get());
			model.addAttribute("reader", profileOwner);
			return "readerProgressRecord";
		}
		throw new ReaderProgressRecordNotFoundException();

	}

}
