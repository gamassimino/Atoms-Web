package com.unrc.app;

import static spark.Spark.*;
//import com.unrc.app.User;
// import org.javalite.activejdbc.Base;
import java.util.Scanner;
import java.util.*;
import java.lang.Object;

import spark.ModelAndView;
import spark.TemplateEngine;


public class App{

  public static void main(String[] args) {
    externalStaticFileLocation("./media");

	  get("/main", (request, response) -> {
	      return new ModelAndView(null, "main.moustache");
		},
	      new MustacheTemplateEngine()
	  );

	  get("/dificulty", (request, response) -> {
	      return new ModelAndView(null, "dificulty.moustache");
		},
	      new MustacheTemplateEngine()
	  );

	  get("/player", (request, response) -> {
	      return new ModelAndView(null, "player.moustache");
		},
	      new MustacheTemplateEngine()
	  );

	  get("/play", (request, response) -> {
	      return new ModelAndView(null, "play.moustache");
		},
	      new MustacheTemplateEngine()
	  );

  }
}