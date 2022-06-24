package it.polito.tdp.yelp.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	YelpDao dao;
	Graph<Review,DefaultWeightedEdge> grafo;
	
	public Model()
	{
		dao = new YelpDao();
	}
	
	public List<String> getCity()
	{
		return dao.getCity();
	}
	
	public List<Business> getBusiness(String c)
	{
		return dao.getBusiness(c);
	}
	
	public String creaGrafo(Business b)
	{
		grafo = new SimpleDirectedWeightedGraph<Review,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getReviewsVERTICI(b));
		
		return "Grafo creato: " + grafo.vertexSet().size() + " vertici, ";
	}
	
}
