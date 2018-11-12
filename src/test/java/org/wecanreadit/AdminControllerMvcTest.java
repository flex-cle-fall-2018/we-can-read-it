package org.wecanreadit;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerMvcTest {
	
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private GroupRepository groupRepo;
	
	@MockBean
	private ReadingGroupRepository readingGroupRepo;
	
	@MockBean
	private ReaderRepository readerRepo;
	
	@MockBean
	private LibrarianRepository librarianRepo;
	
	
	final Cookie adminRoleCookie = new Cookie("role", "admin");
	final Cookie readerRoleCookie = new Cookie("role", "reader");
	byte[] mockImageData = new byte[10 * 1024];

	
	@Before
	public void setup() {
		new Random().nextBytes(mockImageData);
		
	}
	
	@Test
	public void shouldRouteToAdminPanelIfAdmin() throws Exception {
		mvc.perform(get("/admin").cookie(adminRoleCookie))
		.andExpect(view().name(is("admin")));
		
	}
	
	@Test
	public void shouldBeOKForAdminPanelIfAdmin() throws Exception {
		mvc.perform(get("/admin").cookie(adminRoleCookie))
		.andExpect(status().isOk()); // 200
	}
	
	@Test
	public void shouldRedirectToLoginFromAdminPanelIfNotAdmin() throws Exception {
		mvc.perform(get("/admin")).andExpect(status().isFound()); // 302
		mvc.perform(get("/admin").cookie(readerRoleCookie)).andExpect(status().isFound()); // 302
	}

}
