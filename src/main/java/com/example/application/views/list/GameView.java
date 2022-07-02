package com.example.application.views.list;

import com.example.application.data.entity.Player;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Random;

import javax.websocket.Decoder.Text;



@Route(value = "/game")
@PageTitle("Game | Maexchen")

public class GameView extends HorizontalLayout { 
    Grid<Player> grid = new Grid<>(Player.class); 
    TextField filterText = new TextField();
    //TextField labelScoreboard = new TextField("Scoreboard");
    Label labelScoreboard = new Label("Scoreboard");
    Label numPlayersField = new Label();
    Label numRoundsField = new Label();
    Label pointLimitField = new Label();
    
    Integer numPlayers;
    Integer numRounds;
    Integer pointLimit;
    
    CrmService service;
    Integer zufallszahl; 
    
	HorizontalLayout dicesAndScore = new HorizontalLayout();

    

    public GameView(){
        addClassName("game-view");
        setSizeFull();
        configureGrid();
        //this.getElement().getStyle().set("background-image","url('images/tisch.jpg')");
        configFields();
        add(getScoreboard(), configGameScreen());
    }
    
    
    //Das macht die komplette linke Seite
    private HorizontalLayout getScores() {
        HorizontalLayout scores = new HorizontalLayout(grid);
        scores.addClassNames("scoreboard");
        scores.setWidth("auto");  //will "auto" nur mal ausprobieren ansonsten sieht 40% bei mir gut aus
        scores.setHeightFull();
        return scores;
    }
    
    //Das macht die Filterung "und den Add Button"
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); 
        filterText.addValueChangeListener(e -> updateList()); 
        
        HorizontalLayout toolbar = new HorizontalLayout();
        
        toolbar.addClassName("toolbar");
        toolbar.add(filterText);

        Button addPlayerButton = new Button("Add Player");
        toolbar.add(addPlayerButton);


        return toolbar;
    }
    
    //Hier kann man die Variablen dann setzen
    private void configFields() {
    	try{
    		numPlayers = (int) service.countplayer();
    	}
    	catch(Exception e) {
    		numPlayers = 0;
    	}
    }
    
    
    
    //Das macht die Informationen unten, das muss noch angepasst werden, damit eben nicht alles immer angezeigt wird sondenr nur abhängig des GameModes
    private VerticalLayout getLabel() {
    	VerticalLayout label = new VerticalLayout();
    	
    	numPlayersField.setText(numPlayers + " Players");
        numRoundsField.setText(numRounds + " Rounds left");
        pointLimitField.setText(pointLimit + " Point limit");
  
        label.add(numPlayersField, numRoundsField, pointLimitField);  	
    	return label;
    }
    
    //Das macht die gesamte Seitenleiste (Filter+Tabelle)
    private VerticalLayout getScoreboard() {
    	VerticalLayout scoreboard = new VerticalLayout();
    	scoreboard.add(labelScoreboard, getToolbar(), getScores(), getLabel());
    	return scoreboard;
    }
    
    ///Falls keine Datenbank dann das hier ändern:
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
    
    
    
    ////Wird grade bearbeitet
    //Hier wird das Bild geholt für den Tisch
    private Image tableimage() {
    	Image img = new Image("images/tisch.jpg","Tisch");
    	img.setSizeFull();
        return img;
    }
    
    //Hier wird der GameScreen konfiguriert, grade nur das Bild
    private VerticalLayout configGameScreen() {
    	
    	VerticalLayout table = new VerticalLayout();
    	table.add(tableimage());
    	table.add(buttonDice());
    	table.setSizeFull();
        return table;
    }
    
    
    //Erzeugt den Knopf für das Würfeln   --> hier können dann noch glauben/nicht glauebn und so hhin
    private HorizontalLayout buttonDice() {
    	HorizontalLayout dices = new HorizontalLayout();
    	
    	Icon dice = new Icon(VaadinIcon.GAMEPAD);
    	Button rollDiceB = new Button("Roll the dices...", dice);
    	rollDiceB.addClickListener(rollDices ->
		dices.add(configDicesAndScore()));
    	
    	dices.add(rollDiceB);

    	
    	return dices;
    }
    
    private HorizontalLayout configDicesAndScore() {
    	dicesAndScore.add(photo(), submitScoreF());
    	return dicesAndScore;
    }
    
    
    private HorizontalLayout submitScoreF () {

    	HorizontalLayout ownScore = new HorizontalLayout();
    	NumberField myScore = new NumberField("Enter score..");
    	Button enterScoreB = new Button("Submit");
    	    	
    	enterScoreB.addClickListener(scoreSubmitted ->
    			dicesAndScore.removeAll());
    	
    	ownScore.add(myScore, enterScoreB);
    	return ownScore;	
    }
    
    
    //Hier wird das Würfelnummerbild zufallig ausgewählt
    private HorizontalLayout photo() {
    	HorizontalLayout wuerfel = new HorizontalLayout();
    	zufallszahl();
    	
    	Image photo1 = new Image("images/wuerfel"+zufallszahl+".png", ""+zufallszahl+"");
    	photo1.setHeight("40px");
    	photo1.setWidth("40px");
    	
    	zufallszahl();
    	Image photo2 = new Image("images/wuerfel"+zufallszahl+".png", ""+zufallszahl+"");
    	photo2.setHeight("40px");
    	photo2.setWidth("40px");
    	
    	wuerfel.add(photo1, photo2);
    	return wuerfel;
    }
    
    //Erzeugt eine 0 <Zufallsnummer <= 6, um ein Bild (würfel) zufällig auszusuchen
    private Integer zufallszahl() {
    	int min = 1;
		int max = 6;

		Random random = new Random();

		zufallszahl = random.nextInt(max + min) + min;
    	return zufallszahl;
    }

}
