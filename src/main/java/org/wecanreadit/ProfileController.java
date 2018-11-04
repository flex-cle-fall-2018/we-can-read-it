package org.wecanreadit;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/firstName")
    public Reader reader(@RequestParam(value="firstName", defaultValue="World") String name) {
        return new Reader(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
	

