package org.cg.rest;


import org.cg.Model.Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController
{
	
	@RequestMapping(value="/request", method=RequestMethod.POST)
	public Request addRequest(@RequestBody Request request)
	{
		return request;
	
	}
}
