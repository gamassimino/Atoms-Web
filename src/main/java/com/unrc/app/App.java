package com.unrc.app;

import static spark.Spark.*;
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

		post("/dificulty", (request, response) -> {
      return new ModelAndView(null, "dificulty.moustache");
		},
      new MustacheTemplateEngine()
	  );

	  post("/player", (request, response) -> {
	  	request.session(true);
	  	Integer dificulty = Integer.parseInt(request.queryParams("dificulty"));
	  	request.session().attribute("dificulty",dificulty);
      return new ModelAndView(null, "player.moustache");
		},
      new MustacheTemplateEngine()
	  );

	  post("/play", (request, response) -> {
	   	Map<String, Object> attributes = new HashMap<>();
	  	Integer dificulty = request.session().attribute("dificulty");
	  	Integer player = Integer.parseInt(request.queryParams("player"));
	  	
	  	AtomProblem atoms = new AtomProblem();
	  	MinMaxEngine<AtomProblem,AtomState> engine = new MinMaxEngine<AtomProblem,AtomState>(atoms,dificulty);

	  	atoms = new AtomProblem(engine.computeSuccessor(atoms.initialState().clone()));
	  	attributes.put("atom",atoms.initialState());
      return new ModelAndView(attributes, "play.moustache");
		},
      new MustacheTemplateEngine()
	  );

  }
}