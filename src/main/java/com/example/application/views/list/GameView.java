package com.example.application.views.list;

import com.example.application.data.entity.Player;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/game")
@PageTitle("Game | Maexchen")
public class GameView extends HorizontalLayout { 
    Grid<Player> grid = new Grid<>(Player.class); 
    TextField filterText = new TextField();
    CrmService service;

    public GameView(){
        addClassName("game-view");
        setSizeFull();
        configureGrid();
        
        add(getScoreboard(), configGameScreen());
    }
    
    //Das macht die Tabelle an sich
    private HorizontalLayout getScores() {
        HorizontalLayout scores = new HorizontalLayout(grid);
        scores.addClassNames("scoreboard");
        scores.setWidth("200px");
        scores.setHeightFull();
        return scores;
    }
    
    //Das macht die Filterung
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); 
        filterText.addValueChangeListener(e -> updateList()); 
        
        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    
    //Das macht die gesamte Seitenleiste (Filter+Tabelle)
    private VerticalLayout getScoreboard() {
    	VerticalLayout scoreboard = new VerticalLayout();
    	scoreboard.add(getToolbar(), getScores());
    	return scoreboard;
    }
    
    //Hier muss das Spiel an sich konfiguriert werden also der Screen
    private Image configGameScreen() {
    	Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        return img;
    }
    
    //Tabelle wird hier erstellt also die Tabellen
    private void configureGrid() {
        grid.addClassNames("player-grid");
        grid.setColumns("name", "score", "status"); 
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); 
    }
 
    //Filter für wenn man ganz viele Namen hat    
    private void updateList() { 
        grid.setItems(service.findAllPlayers(filterText.getValue()));
    }
    
}


