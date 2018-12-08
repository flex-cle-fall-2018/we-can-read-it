package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Service
public class AuthService {
	
    @Resource
    ReaderRepository readerRepo;
    
    @Resource
    LibrarianRepository librarianRepo;
    
    public Optional<Reader> getReaderIdentity() {
    	
    	HttpServletRequest request =
			((ServletRequestAttributes) RequestContextHolder
	        .getRequestAttributes())
			.getRequest();
    	
    	Cookie userIdCookie = WebUtils.getCookie(request, "readerId");
    	
    	if (userIdCookie == null) {
    		return Optional.ofNullable(null);
    	}
    	
    	Long userId = new Long(userIdCookie.getValue());
    	
    	// Get user from database
    	return readerRepo.findById(userId);
    }
    
    public Optional<Librarian> getLibrarianIdentity() {
    	
    	HttpServletRequest request =
			((ServletRequestAttributes) RequestContextHolder
	        .getRequestAttributes())
			.getRequest();
    	
    	Cookie userIdCookie = WebUtils.getCookie(request, "LibrarianId");
    	
    	if (userIdCookie == null) {
    		return Optional.ofNullable(null);
    	}
    	
    	Long userId = new Long(userIdCookie.getValue());
    	
    	// Get user from database
    	return librarianRepo.findById(userId);
    }

}