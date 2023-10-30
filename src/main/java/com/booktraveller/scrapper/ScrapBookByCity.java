package com.booktraveller.scrapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.booktraveller.entity.Book;
import com.booktraveller.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ScrapBookByCity {
	
	
	public static void main(String[] args) throws InterruptedException {
		Pattern pattern = Pattern.compile(".*?(\\d+)$");

		String[] listlink = {
				"https://www.goodreads.com/places/71-afghanistan", "https://www.goodreads.com/places/31-algeria", "https://www.goodreads.com/places/222-angola", "https://www.goodreads.com/places/214-argentina", "https://www.goodreads.com/places/393-armenia", "https://www.goodreads.com/places/22-australia", "https://www.goodreads.com/places/263-azerbaijan", "https://www.goodreads.com/places/179-bahamas", "https://www.goodreads.com/places/801-bahrain", "https://www.goodreads.com/places/120-bangladesh", "https://www.goodreads.com/places/453-barbados", "https://www.goodreads.com/places/292-belize", "https://www.goodreads.com/places/136-benin", "https://www.goodreads.com/places/621-bhutan", "https://www.goodreads.com/places/9859-bolivia", "https://www.goodreads.com/list/show/16793.Books_Set_in_Botswana", "https://www.goodreads.com/places/18-brazil", "https://www.goodreads.com/shelf/show/brunei", "https://www.goodreads.com/shelf/show/burkina-faso", "https://www.goodreads.com/places/41-cambodia", "https://www.goodreads.com/places/124-cameroon", "https://www.goodreads.com/places/5-canada", "https://www.goodreads.com/places/443-chad", "https://www.goodreads.com/places/141-chile", "https://www.goodreads.com/places/45-china", "https://www.goodreads.com/places/80-colombia", "https://www.goodreads.com/places/4240-congo", "https://www.goodreads.com/places/262-costa-rica", "https://www.goodreads.com/places/500393-cote-d-ivoire", "https://www.goodreads.com/places/47-cuba", "https://www.goodreads.com/places/577-djibouti", "https://www.goodreads.com/places/493-dominica", "https://www.goodreads.com/places/39-ecuador", "https://www.goodreads.com/places/17-egypt", "https://www.goodreads.com/places/268-el-salvador", "https://www.goodreads.com/places/13515-equatorial-guinea", "https://www.goodreads.com/places/12710-eritrea", "https://www.goodreads.com/places/338-ethiopia", "https://www.goodreads.com/places/681-gabon", "https://www.goodreads.com/places/825-gambia", "https://www.goodreads.com/places/223-georgia-eurasian-country", "https://www.goodreads.com/places/173-ghana", "https://www.goodreads.com/places/217-guatemala", "https://www.goodreads.com/places/511489-guinea-bissau", "https://www.goodreads.com/places/67-haiti", "https://www.goodreads.com/places/2188-honduras", "https://www.goodreads.com/places/140-hong-kong", "https://www.goodreads.com/places/1-india", "https://www.goodreads.com/places/61-indonesia", "https://www.goodreads.com/places/3-iran-islamic-republic-of", "https://www.goodreads.com/places/77-iraq", "https://www.goodreads.com/places/98-israel", "https://www.goodreads.com/places/5713-jamaica", "https://www.goodreads.com/places/25-japan", "https://www.goodreads.com/places/238-jordan?page=1", "https://www.goodreads.com/places/483-kazakhstan", "https://www.goodreads.com/places/20-kenya", "https://www.goodreads.com/places/4086-kuwait-city", "https://www.goodreads.com/places/118-kyrgyzstan", "https://www.goodreads.com/places/3811-laos", "https://www.goodreads.com/places/174-lebanon", "https://www.goodreads.com/places/197-lesotho", "https://www.goodreads.com/places/365-liberia", "https://www.goodreads.com/places/7633-libya", "https://www.goodreads.com/places/3670-macao", "https://www.goodreads.com/places/93-madagascar", "https://www.goodreads.com/places/288-malawi", "https://www.goodreads.com/places/511628-malaysia", "https://www.goodreads.com/places/151-mali", "https://www.goodreads.com/places/2793-mauritania", "https://www.goodreads.com/places/257-mauritius", "https://www.goodreads.com/places/82-mexico", "https://www.goodreads.com/places/166-mongolia", "https://www.goodreads.com/places/84-morocco", "https://www.goodreads.com/places/420-mozambique", "https://www.goodreads.com/places/340-myanmar", "https://www.goodreads.com/places/52-namibia", "https://www.goodreads.com/places/244-nepal", "https://www.goodreads.com/places/511653-nicaragua", "https://www.goodreads.com/places/734-niger", "https://www.goodreads.com/places/127-nigeria", "https://www.goodreads.com/places/8227-north-korea", "https://www.goodreads.com/places/7045-oman", "https://www.goodreads.com/places/107-pakistan?container=myspace", "https://www.goodreads.com/places/131-panama", "https://www.goodreads.com/places/276-paraguay", "https://www.goodreads.com/places/4491-peru", "https://www.goodreads.com/places/68-philippines", "https://www.goodreads.com/places/375-qatar", "https://www.goodreads.com/places/200-rwanda", "https://www.goodreads.com/places/279-saudi-arabia", "https://www.goodreads.com/places/6491-sierra-leone", "https://www.goodreads.com/places/111-singapore", "https://www.goodreads.com/places/387-somalia", "https://www.goodreads.com/places/143-south-africa", "https://www.goodreads.com/places/8841-south-korea", "https://www.goodreads.com/places/8850-south-sudan", "https://www.goodreads.com/places/11-spain", "https://www.goodreads.com/places/175-sri-lanka?page=1", "https://www.goodreads.com/places/5943-palestine", "https://www.goodreads.com/places/354-sudan", "https://www.goodreads.com/places/8024-suriname", "https://www.goodreads.com/places/5160-syria", "https://www.goodreads.com/places/211-taiwan", "https://www.goodreads.com/places/538-tajikistan", "https://www.goodreads.com/places/495304-tanzania", "https://www.goodreads.com/places/69-thailand", "https://www.goodreads.com/places/12379-togo", "https://www.goodreads.com/places/477-trinidad-and-tobago", "https://www.goodreads.com/places/271-tunisia", "https://www.goodreads.com/places/49-turkey", "https://www.goodreads.com/places/300-turkmenistan", "https://www.goodreads.com/places/133-uganda", "https://www.goodreads.com/places/384-united-arab-emirates", "https://www.goodreads.com/places/622-uruguay", "https://www.goodreads.com/places/92-uzbekistan", "https://www.goodreads.com/places/161-venezuela", "https://www.goodreads.com/places/62-vietnam", "https://www.goodreads.com/places/494-yemen", "https://www.goodreads.com/places/162-zambia", "https://www.goodreads.com/places/105-zimbabwe"

//				"https://www.goodreads.com/places/255-albania",
//				"https://www.goodreads.com/places/8655-andorra",
//				"https://www.goodreads.com/places/43-austria",
//				"https://www.goodreads.com/places/580-belarus",
//				"https://www.goodreads.com/places/114-belgium",
//				"https://www.goodreads.com/places/8696-bosnia",
//				"https://www.goodreads.com/places/137-bulgaria",
//				"https://www.goodreads.com/places/96-croatia",
//				"https://www.goodreads.com/places/117-cyprus",
//				"https://www.goodreads.com/places/502889-czech-republic",
//				"https://www.goodreads.com/places/72-denmark",
//				"https://www.goodreads.com/places/853-england",
//				"https://www.goodreads.com/places/188-estonia",
//				"https://www.goodreads.com/places/57-finland",
//				"https://www.goodreads.com/places/26-france",
//				"https://www.goodreads.com/places/16-germany",
//				"https://www.goodreads.com/places/14-greece",
//				"https://www.goodreads.com/places/40-hungary",
//				"https://www.goodreads.com/places/123-iceland",
//				"https://www.goodreads.com/places/6-ireland",
//				"https://www.goodreads.com/places/12-italy",
//				"https://www.goodreads.com/places/4079-kosovo",
//				"https://www.goodreads.com/places/520-latvia",
//				"https://www.goodreads.com/places/8588-liechtenstein",
//				"https://www.goodreads.com/places/283-lithuania",
//				"https://www.goodreads.com/places/181-luxembourg",
//				"https://www.goodreads.com/places/246-malta",
//				"https://www.goodreads.com/places/516-moldova",
//				"https://www.goodreads.com/places/596-monaco",
//				"https://www.goodreads.com/places/35-netherlands",
//				"https://www.goodreads.com/places/508016-north-macedonia",
//				"https://www.goodreads.com/places/19-norway",
//				"https://www.goodreads.com/places/87-poland",
//				"https://www.goodreads.com/places/53-portugal",
//				"https://www.goodreads.com/places/56-romania",
//				"https://www.goodreads.com/places/2199-russia",
//				"https://www.goodreads.com/places/510528-san-marino",
//				"https://www.goodreads.com/places/875-scotland",
//				"https://www.goodreads.com/places/210-serbia",
//				"https://www.goodreads.com/places/64-slovakia",
//				"https://www.goodreads.com/places/287-slovenia",
//				"https://www.goodreads.com/places/26-france",
//				"https://www.goodreads.com/places/48-sweden",
//				"https://www.goodreads.com/places/37-switzerland",
//				"https://www.goodreads.com/places/76-ukraine",
//				"https://www.goodreads.com/places/4-united-kingdom",
//				"https://www.goodreads.com/places/2534-vatican-city"
				};
		
		

		for (int  i=0; i<listlink.length; i++) {
			

		
		String url = listlink[i];
		String city="";
		String author="";
		String title="";
		String utf8StringDescription="";
		String imgUrl="";
		String year= "";

		try {

			Document cityPage = Jsoup.connect(url).get();
			city = cityPage.select("h1").text().toLowerCase().replaceAll(" ","_");;
			// on récupère le nom de la ville
			System.out.println(city);
			
			// on va chercher tous les livres concernés par la class .bookTitle
			Elements books = cityPage.select(".bookTitle");

				for (Element book : books) {
			        Element aElement = book.select("a.bookTitle").first();
			        String linkURL = "";
	        
			        // on récupère les liens vers chaque livre
			        if (aElement != null) {
			            linkURL = aElement.attr("href");
			        }
			        
			        
			        // pour chacun d'entre eux, on va aller chercher les caractéristique
			        
			        String bookUrl = "https://www.goodreads.com"+linkURL;
					System.out.println("url : "+bookUrl);
					try {
						// on atteint la page du livre
						Document bookPage = Jsoup.connect(bookUrl).get();
					
						// on cherche les éléments qui nous intéressent
						
						// Titre
						title = bookPage.select(".Text__title1").text();
						System.out.println(title);
					    
						//auteur
						author = bookPage.select("span.ContributorLink__name").first().text();
						System.out.println(author);
						
						
						//description
						String description = bookPage.select(".BookPageMetadataSection__description").text();
						System.out.println(description);
						
						// on transforme en UTF8
										
						utf8StringDescription = StringEscapeUtils.escapeJava(description);
				        System.out.println(utf8StringDescription);							
						
						//image couverture
						Element imgElement = bookPage.select("img.ResponsiveImage").first();

						if (imgElement != null) {
				        	imgUrl = imgElement.attr("src"); 
				        	System.out.println("Lien image :"+imgUrl);
				        }else {
					        System.out.println("Image non trouvée");
					    }
				        
						//date de l'ouvrage
						Element dateElement = bookPage.select("p[data-testid=publicationInfo]").first();
					    if (dateElement != null) {
					        String datebook = dateElement.text();
					        Matcher matcher = pattern.matcher(datebook);
					        if (matcher.find()) {
					            String number = matcher.group(1);
					            year =number;
					        } else {
					            System.out.println("Aucun nombre trouvé à la fin de la chaîne.");
					        }
					        
					        
					        
//					        year = datebook.substring(datebook.length()-4);
					        System.out.println("Date  : " + year);
					    } else {
					        System.out.println("Date non trouvée");
					    }
					    

					    //enregistrement en base de données
					    if (year!=null&&city!=null&&author!=null&&title!=null&&imgUrl!=null&&utf8StringDescription!=null) {
					    	Book newBook = new Book();
					    	newBook.setAuthor(author);
					    	newBook.setCountries(city);
					    	newBook.setDate( Integer. valueOf(year));
					    	newBook.setDescription(utf8StringDescription);
					    	newBook.setImgurl(imgUrl);
					    	newBook.setTitle(title);
					    	

					    	
					    	// On va transformer ce book en JSON
					    	// Convertissez l'objet Book en JSON
				            ObjectMapper objectMapper = new ObjectMapper();
				            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				            String jsonBook = objectMapper.writeValueAsString(newBook);
				            System.out.println(jsonBook);
					    	
					    	// on va ensuite effectuer un POST sur localhost
					    	try {


					            // URL de la ressource que vous souhaitez atteindre avec la requête POST
					            String posturl = "http://localhost:8080/books";
					            
					            // Créer un objet URL à partir de l'URL de la ressource
					            URL obj = new URL(posturl);
					            
					            // Ouvrir une connexion HttpURLConnection
					            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					    		con.setRequestProperty("Content-Type", "application/json");
					            
					            // Spécifier la méthode de la requête (POST)
					            con.setRequestMethod("POST");
					            
					            // Activer l'envoi de données dans la requête
					            con.setDoOutput(true);
					            

					            
					            
					            // Obtenez un flux de sortie pour écrire les données dans la requête
					            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					            wr.writeBytes(jsonBook);
					            wr.flush();
					            wr.close();
					            
					            // Obtenez la réponse de la requête
					            int responseCode = con.getResponseCode();
					            System.out.println("Code de réponse : " + responseCode);
					            
					            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					            String inputLine;
					            StringBuilder response = new StringBuilder();
					            
					            while ((inputLine = in.readLine()) != null) {
					                response.append(inputLine);
					            }
					            in.close();
					            
					            // Affichez la réponse de la requête
					            System.out.println("Réponse : " + response.toString());
							    Thread.sleep(1000);
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
					    	
					    	


					    	
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


}
