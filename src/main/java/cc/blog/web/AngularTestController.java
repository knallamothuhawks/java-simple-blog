package cc.blog.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AngularTestController {

	@RequestMapping("/userSave")
	public Map<String,Object> userSave(@RequestParam Map<String, Object> user) {
		System.out.println(user);
		Map<String,Object> model = new HashMap<String,Object>();
	    model.put("content", "Hello World");
		return model;
	}
	
	@RequestMapping("/boardSave")
	public Map<String,Object> boardSave(@RequestParam Map<String, Object> board) {
		System.out.println(board);
		Map<String,Object> model = new HashMap<String,Object>();
	    model.put("content", "Hello World");
		return model;
	}
}
