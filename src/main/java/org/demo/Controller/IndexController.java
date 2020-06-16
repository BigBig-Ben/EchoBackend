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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	
	//{"xx":[{"a":a, "b":b },{"a":a, "b":b }...]
	@RequestMapping("test1")
	public JSONObject test1()
	{
		//System.out.println("1===");
		JSONObject obj=new JSONObject();
		JSONArray array=new JSONArray();
		for(int i=0; i<3; i++)
		{
			JSONObject json=new JSONObject();
			json.put("1st", "001");
			json.put("2nd", "002");
			json.put("3rd", "003");
			array.add(json);
		}
		//System.out.println("2===");
		obj.put("examples", array);
		//System.out.println(obj.toString());
		return obj;
	}
	
	@RequestMapping("queryTest")
	public String testForQuery()
	{
		System.out.println("===>"+userService.findById(1));
		List<User> user = userService.test(1);
		return "query ok"+user.toString();
	}
}
