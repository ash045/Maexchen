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

import antlr.debug.Event;
import oshi.hardware.CentralProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.websocket.Decoder.Text;



@Route(value = "/game")
@PageTitle("Game | Maexchen")

public class GameView extends HorizontalLayout { 
    Grid<Player> grid = new Grid<>(Player.class); 
    TextField newPlayerName = new TextField();
    HorizontalLayout ownScore = new HorizontalLayout();
    NumberField myScoreField = new NumberField("Enter score..");
    Button enterScoreB = new Button("Submit");  
    //TextField labelScoreboard = new TextField("Scoreboard");
    Label labelScoreboard = new Label("Scoreboard");
    Label numPlayersField = new Label();
    Label numRoundsField = new Label();
    Label pointLimitField = new Label();
    
    Integer numPlayers;
    Integer numRounds;
    Integer pointLimit;
    List Playerlist = new ArrayList<>();
    double myScore = 0;
    Integer zufallszahlVar1 = 0;
    Integer zufallszahlVar2 = 0;

    int CurrentPlayer =0;
    int temp2 = 0;
    
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
        updateScores();
        updateLabel();
    }

    private HorizontalLayout trustButtons(){
        HorizontalLayout trust = new HorizontalLayout();
        Button trustbutton = new Button("Trust");
        Button doubtbutton = new Button("Doubt");
        trust.add(trustbutton);
        trustbutton.addClickListener(click -> scoreRechnungTrust());
        trust.add(doubtbutton);
        doubtbutton.addClickListener(click2 -> scoreRechnungDoubt());

        return trust;
        
    }
    
    
    private void updateScores() {
        grid.setItems(Playerlist);
    }


    //Das macht die Tabelle
    private HorizontalLayout getScores() {
        HorizontalLayout scores = new HorizontalLayout(grid);
        scores.addClassNames("scoreboard");
        scores.setWidth("40%");  //will "auto" nur mal ausprobieren ansonsten sieht 40% bei mir gut aus
        scores.setHeightFull();
        return scores;
    }
    
    //Das macht die Filterung und den Add Button
    private HorizontalLayout getToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();

        newPlayerName.setPlaceholder("Add Player...");          //Filter
        newPlayerName.setClearButtonVisible(true);
        newPlayerName.setValueChangeMode(ValueChangeMode.LAZY); 
        
        toolbar.addClassName("toolbar");
        toolbar.add(newPlayerName);

        Button addPlayerButton = new Button("Add Player");  
        addPlayerButton.addClickListener(event -> updatePlayerlist());            //Button
        toolbar.add(addPlayerButton);

        return toolbar;
    }

    private void updatePlayerlist(){
        Player newPlayer = new Player(newPlayerName.getValue());
        System.out.println(newPlayerName.getValue());

        Playerlist.add(newPlayer);
        updateScores();
        updateLabel();
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
    	
    	numPlayersField.setText(Playerlist.size() + " Players");
        numRoundsField.setText(numRounds + " Rounds left");
        pointLimitField.setText(pointLimit + " Point limit");
  
        label.add(numPlayersField, numRoundsField, pointLimitField);  	
    	return label;
    }

    private void updateLabel() {
    	
    	numPlayersField.setText(Playerlist.size() + " Players");
        numRoundsField.setText(numRounds + " Rounds left");
        pointLimitField.setText(pointLimit + " Point limit");

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
    
    ////Wird grade bearbeitet
    //Hier wird das Bild geholt für den Tisch
    /*private Image tableimage() {
    	Image img = new Image("images/tisch.jpg","Tisch");
    	img.setSizeFull();
        return img;
    }*/
    
    //Hier wird der GameScreen konfiguriert, grade nur das Bild
    private VerticalLayout configGameScreen() {
    	
    	VerticalLayout table = new VerticalLayout();
    	//table.add(tableimage());
    	table.add(buttonDice());
        table.add(trustButtons());
    	table.setSizeFull();
        return table;
    }
    
    
    //Erzeugt den Knopf für das Würfeln   --> hier können dann noch glauben/nicht glauebn und so hhin
    private HorizontalLayout buttonDice() {
    	HorizontalLayout dices = new HorizontalLayout();
    	
    	Icon dice = new Icon(VaadinIcon.GAMEPAD);
    	Button rollDiceButton = new Button("Roll the dice...", dice);
    	rollDiceButton.addClickListener(rollDices ->
		dices.add(configDicesAndScore()));
    	
    	dices.add(rollDiceButton);

    	
    	return dices;
    }
    
    private HorizontalLayout configDicesAndScore() {
    	dicesAndScore.add(photo(), submitScoreF());
    	return dicesAndScore;
    }
    
    public HorizontalLayout submitScoreF () {

    	enterScoreB.addClickListener(scoreSubmitted -> submitButtonFunctionality());
    	
    	ownScore.add(myScoreField, enterScoreB);
    	return ownScore;	
    }

    public void submitButtonFunctionality(){
        Double temp = myScoreField.getValue();
        int temp2 = temp.intValue();
    	((Player) Playerlist.get(CurrentPlayer)).setEnteredscore(translateEnteredzahl(temp2)); 
        myScoreField.clear();
        dicesAndScore.removeAll();
        
    }

    //Hier wird das Würfelnummerbild zufallig ausgewählt
    private HorizontalLayout photo() {
    	HorizontalLayout wuerfel = new HorizontalLayout();
    	zufallszahl();
    	zufallszahlVar1 = zufallszahl;
    	Image photo1 = new Image("images/wuerfel"+zufallszahl+".png", ""+zufallszahl+"");
    	photo1.setHeight("40px");
    	photo1.setWidth("40px");
    	
    	zufallszahl();
        zufallszahlVar2 = zufallszahl;
    	Image photo2 = new Image("images/wuerfel"+zufallszahl+".png", ""+zufallszahl+"");
    	photo2.setHeight("40px");
    	photo2.setWidth("40px");
    	
        ((Player) Playerlist.get(CurrentPlayer)).setRandomscore(translateZufallszahl(zufallszahlVar1, zufallszahlVar2));
    	wuerfel.add(photo1, photo2);
    	return wuerfel;
    }
    
    //Erzeugt eine 0 <Zufallsnummer <= 6, um ein Bild (würfel) zufällig auszusuchen
    public Integer zufallszahl() {
    	int min = 1;
		int max = 6;

		Random random = new Random();

		zufallszahl = random.nextInt(max) + min;
    	return zufallszahl;
    }

    public void scoreRechnungDoubt() {
        //if doubt gedrückt
        if (translateEnteredzahl(temp2) != ((Player) Playerlist.get(CurrentPlayer)).getRandomscore()) {
            ((Player) Playerlist.get(CurrentPlayer+1)).setScore(((Player) Playerlist.get(CurrentPlayer+1)).getScore()-1);
            updateScores();
            CurrentPlayer = CurrentPlayer + 1;
        }
        else{
            ((Player) Playerlist.get(CurrentPlayer)).setScore(((Player) Playerlist.get(CurrentPlayer)).getScore()-1);
            updateScores();
            CurrentPlayer = CurrentPlayer + 1;
        }
    }

    public void scoreRechnungTrust(){
        // if trust gedrückt
        buttonDice();
        if (((Player) Playerlist.get(CurrentPlayer)).getEnteredscore() > ((Player) Playerlist.get(CurrentPlayer+1)).getRandomscore()) {
            ((Player) Playerlist.get(CurrentPlayer+1)).setScore(((Player) Playerlist.get(CurrentPlayer+1)).getScore()-1);
            updateScores();
            CurrentPlayer = CurrentPlayer + 1;
            
        }
        else{
            ((Player) Playerlist.get(CurrentPlayer)).setScore(((Player) Playerlist.get(CurrentPlayer)).getScore()-1);
            updateScores();
            CurrentPlayer = CurrentPlayer + 1;
        }
    }

    private Integer translateZufallszahl(Integer z1, Integer z2){
        Integer zufallsScore = 0;
        if (z1>z2){
            if (z1 == 3){
                if (z2 == 1 ){
                    zufallsScore = 0;
                } else {
                    zufallsScore = 1;
                }
            } else if (z1 == 4 ){
                if (z2 == 1){
                    zufallsScore = 2;
                } else if (z2 == 2) {
                    zufallsScore = 3;
                } else {
                    zufallsScore = 4;
                }
            } else if (z1 == 5){
                if (z2 == 1 ){
                    zufallsScore = 5;
                } else if (z2 == 2) {
                    zufallsScore = 6;
                } else if (z2 == 3) {
                    zufallsScore = 7;
                } else {
                    zufallsScore = 8;
                }
            } else if (z1 == 6) {
                if (z2 == 1 ){
                    zufallsScore = 9;
                } else if (z2 == 2) {
                    zufallsScore = 10;
                } else if (z2 == 3) {
                    zufallsScore = 11;
                } else if(z2 ==4) {
                    zufallsScore = 12;
                } else {
                    zufallsScore = 13;
                }
            } else {
                zufallsScore = 20;
            }
        } else if (z2>z1){
            int temp = 0;
            temp = z1;
            z1 = z2;
            z2 = temp;
            if (z1 == 3){
                if (z2 == 1 ){
                    zufallsScore = 0;
                } else {
                    zufallsScore = 1;
                }
            } else if (z1 == 4 ){
                if (z2 == 1){
                    zufallsScore = 2;
                } else if (z2 == 2) {
                    zufallsScore = 3;
                } else {
                    zufallsScore = 4;
                }
            } else if (z1 == 5){
                if (z2 == 1 ){
                    zufallsScore = 5;
                } else if (z2 == 2) {
                    zufallsScore = 6;
                } else if (z2 == 3) {
                    zufallsScore = 7;
                } else {
                    zufallsScore = 8;
                }
            } else if (z1 == 6) {
                if (z2 == 1 ){
                    zufallsScore = 9;
                } else if (z2 == 2) {
                    zufallsScore = 10;
                } else if (z2 == 3) {
                    zufallsScore = 11;
                } else if(z2 ==4) {
                    zufallsScore = 12;
                } else {
                    zufallsScore = 13;
                }
            } else {
                zufallsScore = 20;
            }

        } else {
            zufallsScore = z1 + 13;
        }
        return zufallsScore;
    }
    private Integer translateEnteredzahl(int temp2){
        Integer enteredScore = 0;
            if (temp2 == 31){
                    enteredScore = 0;
                }
            else if (temp2 == 32){
                    enteredScore = 1;
                } 
            else if (temp2 == 41) {
                    enteredScore = 2;
            }
            else if (temp2 == 42){
                    enteredScore = 3;
                } 
            else if (temp2 == 43){
                    enteredScore = 4;
                } 
            else if (temp2 == 51) {
                    enteredScore = 5;
            }
            else if (temp2 == 52){
                    enteredScore = 6;
                    } 
            else if (temp2 == 53) {
                    enteredScore = 7;
                }
            else if (temp2 == 54){
                    enteredScore = 8;
                    } 
            else if (temp2 == 61) {
                    enteredScore = 9;
                    }
            else if (temp2 == 62){
                    enteredScore = 10;
                    } 
            else if (temp2 == 63) {
                    enteredScore = 11;
                    }
            else if (temp2 == 64){
                    enteredScore = 12;
                    } 
            else if (temp2 == 65) {
                    enteredScore = 13;
                    }
            else if (temp2 == 11){
                    enteredScore = 14;
                    } 
            else if (temp2 == 22) {
                    enteredScore = 15;
                    }
            else if (temp2 == 33){
                    enteredScore = 16;
                    } 
            else if (temp2 == 44) {
                     enteredScore = 17;
                    }
            else if (temp2 == 55){
                    enteredScore = 18;
                    } 
            else if (temp2 == 66) {
                    enteredScore = 19;
                    }
            else if (temp2 == 21) {
                        enteredScore = 20;
                        }
        return enteredScore;
}
}
