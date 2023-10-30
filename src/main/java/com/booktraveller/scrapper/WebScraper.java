package com.booktraveller.scrapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class WebScraper {
	public static void main(String[] args) {

		String url = "https://www.goodreads.com/list/show/6.Best_Books_of_the_20th_Century";

		try {

			Document document = Jsoup.connect(url).get();
			Elements books = document.select("tr[itemscope][itemtype=\"http://schema.org/Book\"]");
			System.out.println("books");
//			System.out.println(books2);
				for (Element e : books) {
					// on récupère le titre
					String title = e.select(".bookTitle").text();
					// on récupère l'auteur
					String author = e.select(".authorName").text();
					
					// on récupère le titre
			        Element imgElement = e.select("img.bookCover").first(); // Sélectionnez l'élément img
			        String imgurl = "";
			        
			        // on récupère le titre
			        Element aElement = e.select("a.bookTitle").first();
			        String linkURL = "";

			        
			        if (imgElement != null) {
			            imgurl = imgElement.attr("src"); // Récupérez l'URL de l'image
			        }
			        
			        if (aElement != null) {
			            linkURL = aElement.attr("href");
			        }
			        
					System.out.println(title+" : "+author);
					System.out.println(imgurl);
					System.out.println("lien : "+linkURL);
					

					
					// ON VA SCRAPPER SUR LES PAGES INDIVIDUELLES
					String idurl = "https://www.goodreads.com"+linkURL;
					System.out.println("url : "+idurl);
					try {
					Document document2 = Jsoup.connect(idurl).get();
				    Element dateElement = document2.select("p[data-testid=publicationInfo]").first();
				    String description = document2.select(".BookPageMetadataSection__description").text();
		            Element script = document2.select("script#__NEXT_DATA__").first();


				    
				    if (dateElement != null) {
				        String datebook = dateElement.text();
				        System.out.println("Date  : " + datebook);
				    } else {
				        System.out.println("Élément non trouvé");
				    }
				    


				    //--------------------------------------//
				    
				    if (script != null) {
		                // Extraire le contenu JSON du script
		                String jsonText = script.data();
		                System.out.println("Contenu JSON : " + jsonText);

				    }
				    
				    
				    
				    
				    
					
					System.out.println("-------------------");
					
					} catch (IOException f) {
						// TODO Auto-generated catch block
						f.printStackTrace();
					}
				}		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	

}