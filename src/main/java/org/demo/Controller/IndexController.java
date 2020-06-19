package org.demo.Controller;

import java.util.List;

import org.demo.Entity.*;
import org.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/*
 * for test
 * 
 */
@RestController
public class IndexController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/")
	public String welcome()
	{
		return "WELCOME this is index";
	}
	@RequestMapping(value="/test/{key}={value}")
	public String testForData(@PathVariable("key")String key, @PathVariable("value") String value)
	{
		return "your input content is " + key + " values " + value;
	}

}
