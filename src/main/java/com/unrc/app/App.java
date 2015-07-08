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
	  	//===================================NEW CODE==========================================================
	  	String position = request.session().attribute("position");
	  	char[] positionArray = position.toCharArray();
	  	int i;
	  	int j;
	  	if (position.length()==2){//first number is a row and second is a column
	  		i = positionArray[0];
	  		j = positionArray[1];
	  		atoms = new AtomProblem (atoms.putAnAtom(atoms.initialState(),(i-1),(j-1),new Atom(player)));
	  	}
	  	else{//first pair of number is a row and the other number is a column
	  		i = 10;
	  		j = positionArray[3];
	  		atoms = new AtomProblem (atoms.putAnAtom(atoms.initialState(),(i-1),(j-1),new Atom(player)));
	  	}
	  	//====================================================================================================
	  	MinMaxEngine<AtomProblem,AtomState> engine = new MinMaxEngine<AtomProblem,AtomState>(atoms,dificulty);

	  	atoms = new AtomProblem(engine.computeSuccessor(atoms.initialState().clone()));
	  	attributes.put("atom",atoms.initialState());
      return new ModelAndView(attributes, "play.moustache");
		},
      new MustacheTemplateEngine()
	  );

  }
}