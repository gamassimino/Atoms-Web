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

		// public static AtomProblem Variable.atoms;
		// public static MinMaxEngine<AtomProblem,AtomState> Variable.engine;
   //  before((request, response) -> {
   //  	AtomProblem Variable.atoms;
			// MinMaxEngine<AtomProblem,AtomState> Variable.engine;
   //  });


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

  post("/playAux", (request, response) -> {
   	Map<String, Object> attributes = new HashMap<>();
  	Integer dificulty = request.session().attribute("dificulty");
  	Variable.turn = Integer.parseInt(request.queryParams("player"));
  	
  	Variable.atoms  = new AtomProblem();
  	attributes.put("atom",Variable.atoms.initialState());
  	return new ModelAndView(attributes, "play.moustache");
	},
  	new MustacheTemplateEngine()
  );


	post("/play",(request,response) -> {
		Map<String, Object> attributes = new HashMap<>();
  	String row = request.queryParams("row");
  	String column =request.queryParams("column");
  	// AtomProblem Variable.atoms = (AtomProblem)request.queryParams("atom");
  	Integer dificulty = request.session().attribute("dificulty");
  	Variable.engine = new MinMaxEngine<AtomProblem,AtomState>(Variable.atoms,dificulty);
  	if (Variable.turn % 2==0)	
	  	Variable.atoms = new AtomProblem(Variable.engine.computeSuccessor(Variable.atoms.initialState().clone()));
  	else
  		Variable.atoms = new AtomProblem (Variable.atoms.putAnAtom(Variable.atoms.initialState(),(Integer.parseInt(row)),(Integer.parseInt(column)),new Atom(2)));
  	Variable.turn++;
  	attributes.put("atom",Variable.atoms.initialState());
  	return new ModelAndView(attributes,"play.moustache");
	},
		new MustacheTemplateEngine()
	);


  }
}