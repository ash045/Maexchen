package com.example.application.views.list;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


import com.example.application.data.entity.Player;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

@PageTitle("start")
@Route(value = "/start")
public class StartView extends VerticalLayout{
	Grid<Player> grid = new Grid<>(Player.class); 
	TextField addname = new TextField();
	List Playerlist = new ArrayList<>();
	
	StartView(){
		setSizeFull();
		buttons();
		configureGrid();
		//updateScores();
	}
	

	private void buttons() {
		Button startbutton = new Button("Start");
		
		//Hier ist Platz für einen Link zu einer anderen Route
		startbutton.addClickListener(e ->
	    	startbutton.getUI().ifPresent(ui ->
	           ui.navigate("/game"))
		);
		
		Button backbutton = new Button("Back");
		
		//Hier ist Platz für einen Link zu einer anderen Route
		backbutton.addClickListener(e ->
    		backbutton.getUI().ifPresent(ui ->
    			ui.navigate(""))
		);
		
		VerticalLayout layout = new VerticalLayout(getNameboard(),startbutton, backbutton);
		layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(layout);
		
		}

		private VerticalLayout getNameboard(){
			VerticalLayout nameboard = new VerticalLayout();
			nameboard.add(getToolbar(), getScores());
			nameboard.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
			return nameboard;
		}

		private void configureGrid() {
			grid.addClassNames("player-grid");
			grid.setColumns("name","score","status"); 
			grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
		}

		private HorizontalLayout getToolbar() {
			HorizontalLayout toolbar = new HorizontalLayout();
			addname.setPlaceholder("Add Names...");         	 //Filter
        	addname.setClearButtonVisible(true);
        	addname.setValueChangeMode(ValueChangeMode.LAZY); 
        	//addname.addValueChangeListener(e -> updateList()); 
        
        	toolbar.addClassName("toolbar");
        	toolbar.add(addname);
			toolbar.addClassName("addname");
			
			Button addPlayerButton = new Button("Add Player"); 		// Button muss noch angepasst werden um neue Spieler zu erzeugen
			addPlayerButton.addClickListener(event -> updatePlayerlist());
			//addPlayerButton.getUI().ifPresent(ui -> ui.navigate("/game")));       */      
			toolbar.add(addPlayerButton);
			
			return toolbar;
		}

		private void updatePlayerlist(){
			Player newPlayer = new Player(addname.getValue());
			System.out.println(addname.getValue());

			Playerlist.add(newPlayer);
			updateScores();
		}

		private void updateScores() {
			grid.setItems(Playerlist);
		}

		private HorizontalLayout getScores() {
			HorizontalLayout scores = new HorizontalLayout(grid);
			scores.addClassNames("scoreboard");
			scores.setWidth("40%");  //will "auto" nur mal ausprobieren ansonsten sieht 40% bei mir gut aus
			scores.setHeightFull();
			return scores;
		}
}
