package com.ucsp.tres_raya.client;

import com.ucsp.tres_raya.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.CheckBox;

import java.util.ArrayList;
import java.util.List;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tres_en_raya implements EntryPoint {
	boolean x,ini = true;
	int[][] tablero = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
	Button b;
	public void onModuleLoad() {
		
		 final CheckBox ck = new CheckBox("empieza X");
		 ck.getElement().setId("ckTurnoX");
		 RootPanel.get("CheckBoxContainer").add(ck);
		 b = new Button("Comenzar");
		 b.getElement().setId("btbEmpezar");
		 b.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(ini)
				{
				 
			     try
			     {
			     
				  x = ck.isChecked();
				  b.setEnabled(false);
				  cargarTabla();
				  ini = false;
			     }
			     catch(Exception e)
			     {
			       Window.alert("err empezar click");	 
			     }
				}
				
				
				
			}
		});
		 RootPanel.get("CkButtonContainer").add(b);
	}
	void cargarTabla()
	{
		try{
		String token;
		for(int i = 0;i<3;++i)
	     for(int j = 0;j<3;++j)
	     {
	      token = String.valueOf(i)+String.valueOf(j);
	      Button btn = new Button();
	      //"boton"+token
	      btn.getElement().setId("boton"+token);
	      btn.getElement().setClassName("michi_button");
	      btn.addClickHandler(new ClickHandler(){@Override
	      public void onClick(ClickEvent event) {
	    	 
	    	 Button sender = (Button) event.getSource();
	    	 String tkn = sender.getElement().getId().substring(5); 
	    	 try
	    	 {
	    	 
	          int i,j;
	          
	    	  i = Integer.parseInt(tkn.substring(0,1));
	    	  j = Integer.parseInt(tkn.substring(1));
	    	  
	    	  Panel celda = RootPanel.get("celda"+tkn);
	    	  celda.clear();
	    	  celda.add(new HTML((x?"x":"o")));
	    	  tablero[i][j] = (x?1:0);
	    	  int res = resultado();
	    	  if(res == 1)
	    	  {
	    	   Window.alert("Ganaste");
	    	   iniTablero();
	    	   b.setEnabled(true);
	    	   ini = true;
	    	   return;
	    	  }
	    	  else if(res == 2)
	    	  {
	    		Window.alert("Empate");
	    		iniTablero();
		    	b.setEnabled(true);
		    	ini = true;
		    	return;
	    	  }
	    	  x = !x;
	    	 }
	    	 catch(Exception e)
	    	 {
	    		 Window.alert("error");
	    	 }
	    }});
	      RootPanel rp = RootPanel.get("celda"+token);
	      rp.clear();
	      rp.getElement().getStyle().setBackgroundColor("white");
	      rp.add(btn);
	     }
		}
		catch(Exception ex)
		{
			Window.alert("err cargar");
		}
	}
    void iniTablero()
    {
      for(int i = 0;i<3;++i)
    	for(int j = 0;j<3;++j)
    	  tablero[i][j] = -1;
    }
	int resultado()
	{
	   int acum = 0,acum2 = 0,acum3 = 0,acum4 = 0;
	   ArrayList<String> tokens1 = new ArrayList<String>(),tokens2 = new ArrayList<String>();
	   ArrayList<String> tokens3 = new ArrayList<String>(),tokens4 = new ArrayList<String>();
	   int flgMenosUnos = 2;
	   for(int i = 0;i<3;++i)
	   {
		 acum = acum2 = 0;
		 //+++++++++++++++++++++++
		 for(int j = 0;j<3;++j)
		 {
		   if(tablero[i][j] != -1)
		   { 
			 acum+=tablero[i][j];
			 tokens1.add((""+i)+j);
		   }
		   else
		   {	
			 flgMenosUnos = 0;
			 acum += 4;
		   }
		   if(tablero[j][i] != -1)
		   {	  
			  acum2+=tablero[j][i];
			  tokens2.add((""+j)+i);
		   }
		   else
		   {
			 acum2 += 4;
			 flgMenosUnos = 0;
		   }
		 }
		 if(acum == 3 || acum == 0)
		 { 
		   //Window.alert("acum:"+acum);
		   for(String p:tokens1)
		    RootPanel.get("celda"+p).getElement().getStyle().setBackgroundColor("yellow"); 
		   return 1;
		 }
		 else if(acum2 == 3 || acum2 == 0)
		 {
			//Window.alert("acum2:"+acum2);
		   for(String p:tokens2)
		     RootPanel.get("celda"+p).getElement().getStyle().setBackgroundColor("yellow");
			return 1;	 
		 }
		 else
		 {
			tokens1.clear();
			tokens2.clear();
		 }
		 if(tablero[i][i] == -1)
	      acum3 += 4;
		 else
		 { 
		  acum3 += tablero[i][i];
		  tokens3.add((""+i)+i);
		 }
		 if(tablero[i][2-i] == -1)
		  acum4 += 4;
	     else
	     { 
	      acum4 += tablero[i][2-i];
	      tokens4.add((""+i)+(2-i));
	     }
		 
	   }
	   //---------------------
	   if(acum4 == 3 || acum4 == 0)
	   {
		 for(String p:tokens4)
		   RootPanel.get("celda"+p).getElement().getStyle().setBackgroundColor("yellow");
		 return 1;
	   }
	   else if(acum3 == 3 || acum3 == 0)
	   { 
		 for(String p:tokens3)
		   RootPanel.get("celda"+p).getElement().getStyle().setBackgroundColor("yellow");
		 return 1; 
	   }
	   return 0+flgMenosUnos;	
	}
	
}
