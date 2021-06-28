package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public List<String> getVertici(Integer calorie){
		List<String> result = new LinkedList<>();
		result = this.dao.getVertici(calorie);
		Collections.sort(result);
		return result;
	}
	
	public List<Adiacenza> getArchi(Integer calorie){
		return this.dao.getArchi(calorie);
	}
	
	public String creaGrafo(Integer calorie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		String result ="Grafo creato con successo. \n";
		Graphs.addAllVertices(this.grafo, this.getVertici(calorie));
		result += "#VERTICI: " +this.grafo.vertexSet().size() +"\n";
		for (Adiacenza a : this.getArchi(calorie)) {
			if(this.grafo.containsVertex(a.getP1()) && this.grafo.containsVertex(a.getP2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
		result += "#ARCHI: " +this.grafo.edgeSet().size() +"\n";
		
		
		return result;
	}
	
	public String getCorrelazioni(String porzione) {
		String result = "";
		
		for (DefaultWeightedEdge d : grafo.edgesOf(porzione)) {
			if (grafo.getEdgeSource(d).equals(porzione)) {
				result += grafo.getEdgeTarget(d) +" " +(int)(grafo.getEdgeWeight(d)) +"\n";
			} else if (grafo.getEdgeTarget(d).equals(porzione)) {
				result += grafo.getEdgeSource(d) +" " +(int)(grafo.getEdgeWeight(d)) +"\n";
			}
		}
		
		
		return result;
	}
	
	
}
