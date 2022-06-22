package com.example.application.views.list;

import com.example.application.data.entity.Player;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
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
    //TextField labelScoreboard = new TextField("Scoreboard");
    Label labelScoreboard = new Label("Scoreboard");
    TextField numPlayersField = new TextField();
    TextField numRoundsField = new TextField();
    TextField pointLimitField = new TextField();
    
    Integer numPlayers = 0;
    Integer numRounds;
    Integer pointLimit;
    
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
    
    //Das macht die Informationen unten, das muss noch angepasst werden, damit eben nicht alles immer angezeigt wird sondenr nur abhängig des GameModes
    private VerticalLayout getLabel() {
    	VerticalLayout label = new VerticalLayout();
    	numPlayersField.setReadOnly(true);
//    	numPlayers = service.countplayer(); Hier müssen Werte drin sein, sonst gibt es einen Fehler, das sollte man catchen
    	numPlayersField.setValue(numPlayers + " Players");
    	
        numRoundsField.setReadOnly(true);
        numRoundsField.setValue(numRounds + "Rounds left");
        
        pointLimitField.setReadOnly(true);
        pointLimitField.setValue(pointLimit + "Point limit");
        
        label.add(numPlayersField, numRoundsField, pointLimitField);
    	
    	return label;
    }
    
    //Das macht die gesamte Seitenleiste (Filter+Tabelle)
    private VerticalLayout getScoreboard() {
    	VerticalLayout scoreboard = new VerticalLayout();
    	scoreboard.add(labelScoreboard, getToolbar(), getScores(), getLabel());
    	return scoreboard;
    }
    
    //Hier muss das Spiel an sich konfiguriert werden also der Screen, man sollte evtl ein schärferes Bild nehmen aber worst case mäßig gehts
    private Image configGameScreen() {
    	Image img = new Image("images/tisch.jpg","Tisch");
        img.setSizeFull();
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


