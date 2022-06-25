package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	YelpDao dao;
	Graph<Review,DefaultWeightedEdge> grafo;
	Map<String, Review> idMapReviews;
	private List<Review> migliore;
	
	public Model()
	{
		dao = new YelpDao();
		idMapReviews = new HashMap<>();
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
		
		Graphs.addAllVertices(grafo, dao.getReviewsVERTICI(b, idMapReviews));
		
		List<Archi> archi = dao.getArchi(b);
		
		for(Archi a:archi)
		{
			if(a.getD1().isBefore(a.getD2()))
			{
				int peso = (int) ChronoUnit.DAYS.between(a.getD1(), a.getD2());
				if(peso>0)
				{
					System.out.println(this.idMapReviews.size());
					Graphs.addEdgeWithVertices(grafo, idMapReviews.get(a.getR1()), idMapReviews.get(a.getR2()), peso);
				}
			}
		}
		
		return "Grafo creato: " + grafo.vertexSet().size() + " vertici, " + grafo.edgeSet().size() + " archi";
	}
	
	public List<String> getUscenti()
	{
		List<String> listaUscentiMax = new ArrayList<>();
		int uscentiMax = 0;
		for(Review r: grafo.vertexSet())
		{
			int uscenti = Graphs.successorListOf(grafo, r).size();
			if(uscenti > uscentiMax)
			{
				uscentiMax = uscenti;
			}
		}
		
		for(Review r: grafo.vertexSet())
		{
			int uscenti = Graphs.successorListOf(grafo, r).size();
			if(uscenti == uscentiMax)
			{
				listaUscentiMax.add(r.getReviewId() + "             # ARCHI USCENTI: " + uscentiMax);
			}
		}
		
		return listaUscentiMax;
	}
	
	public List<Review> calcolaPercorso()
	{
		migliore = new LinkedList<Review>();
		List<Review> daTornare = new LinkedList<Review>();
		
		
		for(Review r1:grafo.vertexSet())
		{
			List<Review> parziale = new LinkedList<>();
			parziale.add(r1);
			cercaRicorsiva(parziale);
			
			if(daTornare.size()<migliore.size())
			{
				daTornare = new LinkedList<>(migliore);
			}
		}
		
		return daTornare;
	}

	private void cercaRicorsiva(List<Review> parziale) {
		 
				//condizione di terminazione
				if(parziale.size() > migliore.size())//la strada piú lunga é la migliore
				{
					migliore = new LinkedList<>(parziale);
				}
				
					
					for(Review r:Graphs.successorListOf(this.grafo, parziale.get(parziale.size()-1))) //scorro sui vicini dell'ultimo nodo sulla lista
					{
						if(!parziale.contains(r))
						{
							if(r.getStars()>= parziale.get(parziale.size()-1).getStars())
							{
								parziale.add(r);
								cercaRicorsiva(parziale);
								parziale.remove(parziale.size()-1);
							}
						
						}	
					}

				
	}

	
}
