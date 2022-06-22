package com.example.application.views.list;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;

@PageTitle("start")
@Route(value = "/start")
public class StartView extends VerticalLayout{
	StartView(){
		setSizeFull();
		buttons();
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
		
		VerticalLayout layout = new VerticalLayout(startbutton, backbutton);
		layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(layout);
		
		}
}
