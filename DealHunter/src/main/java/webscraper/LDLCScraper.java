package webscraper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LDLCScraper {

  public static void main(String[] args) {
	  ArrayList<String> tabLinks = new ArrayList<String>();
	  int nbpage = getNumberpages("https://www.ldlc.com/informatique/ordinateur-portable/pc-portable/c4265/");
	  System.out.println(nbpage);
    try {
    	
    	String pageRacine = null;
    	
    	  for (int index = 1; index < nbpage; index++) {
    		  
    		  if(index == 1) {
    			  
    		     pageRacine = "https://www.ldlc.com/informatique/ordinateur-portable/pc-portable/c4265/";

    		  }else {
    			  
    		     pageRacine = "https://www.ldlc.com/informatique/ordinateur-portable/pc-portable/c4265/"+"page"+index+"/";

    		  }
    		
    	
    	

    	String name = null;
    	String screenSize = null;
    	String screen_resolution = null;
    	String cpu = null;
    	String gpu = null;
    	String ram = null;
    	String storage = null;
    	String os = null;
    	String wheight = null;
    	String price = null;
    	String url = null;
    	String source = null;
    	String newline = System.getProperty("line.separator");
    	
      Document docHome = Jsoup.connect(pageRacine).get();
      Elements links = docHome.select("[href*=/fiche/]");
      for (Element element : links) {
		    	
    	  String link = element.attr("abs:href");
    	   if(!tabLinks.contains(link)) {
          	  tabLinks.add(link);
          System.out.println(link);
          Document doc = Jsoup.connect(link).get();
          String url2 = doc.baseUri();
       
          Elements divPrice = doc.select("div.price > div");
          Elements table = doc.select("div.specsTech table");
          Elements rows = table.select("tr");
          Elements cols = table.select("td");
          //System.out.println(url2);     
          String price2 = (String) divPrice.text().subSequence(0,7);
          String price3 = price2.replace(" ", "");
          String price4 = price3.replace("€", ".").concat("€");
          //System.out.println(price4);
      for (int i = 1; i < cols.size() ; i++) { 
          Element col = cols.get(i);
          //System.out.println(col.text());
         source = "LDLC";
         name = cols.get(2).text();
         cpu = cols.get(24).text();
         gpu = cols.get(44).text();
         ram = cols.get(31).text();
         os = cols.get(9).text();
         price = price4;
         url = url2;
          
	     if (col.text().contains("kg") && !col.text().contains("W/")) {
	    	 wheight = col.text();
	     }
	     
	     if (col.text().contains("pouces")) {
	    	 screenSize = col.text();
	     }
	     
	     if (col.text().contains("pixels")) {
	    	 screen_resolution = col.text();
	     }
	     
	     if (col.text().contains("Go")) {
	    	 storage = col.text();
	     }
          
      }
          
          try {
        	  
              FileWriter myWriter = new FileWriter("filename184.txt",true);
      	      BufferedWriter out = new BufferedWriter(myWriter);
      	      
              out.write("source :"+source+newline+"name :"+name+newline+"url :"+url+newline+"Screen size :"+
              screenSize+newline+"Screen resolution :"+screen_resolution+newline+"Processeur :"+cpu
              +newline+"Carte graphique :"+gpu+newline+"ram :"+ram+newline+"stoackage :"+storage+newline
              +"systeme d'exploitation :"+os+newline+"poids :"+wheight+newline+"prix :"+price+newline+pageRacine+newline);
              out.write("###########################################################"+newline);
              out.close();
              //System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
      }
      
    	   }
    	  }
    } catch (IOException e) {
    e.printStackTrace();
    }
    

    }
  
  	public static int getNumberpages(String link) {
  		int totalPage = 0;
  		try {
  			
  			Document doc = Jsoup.connect(link).get();
  			Elements element = doc.select("div.listing-product ul") ;
  			Element linkk = element.last();
  			String totale = linkk.getElementsByTag("a").text().substring(19, 21);		
  		    totalPage = Integer.parseInt(totale);
  		    //System.out.println(totalPage);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
  	
  	return totalPage;
  }
  
  }
