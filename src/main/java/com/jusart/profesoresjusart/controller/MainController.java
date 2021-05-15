package com.jusart.profesoresjusart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Deveria existir un controlador por cada entidad
//Este controlador manejar /

@Controller
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		String response = "Bienvenido a <a href='http://jusart.com'>jusart.com</a>";
		return response;
	}
}
