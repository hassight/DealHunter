package fr.dealhunter.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopAchatScraper {

	public static void main(String[] args) {
		String url = "https://www.topachat.com/pages/detail2_cat_est_ordinateurs_puis_rubrique_est_wport_puis_ref_est_in20008483.html";
		scrapLaptopPage(url);
	}
	
	public static void scrapLaptopPage(String url) {
		try {
			Document doc = Jsoup.connect(url)
				     .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
				     .ignoreHttpErrors(true)
				     .ignoreContentType(true)
				     .timeout(10000)
				     .get();
			
			String name = doc.getElementsByAttributeValue("property", "og:title").attr("content").toString();
			name = name.substring(0, name.indexOf("|")).trim();
			String reference = doc.select("div > span").first().text();
			String price = doc.getElementsByAttributeValue("itemprop", "price").attr("content").toString() + "€";
			System.out.println("Nom : " + name);
			System.out.println(reference);
			System.out.println(price);
			
			String content, screenSize, screenResolution, cpu, dedicatedGpu, chipset, storage, ram, os, weight, gpu;
			content = screenSize = screenResolution = cpu = dedicatedGpu = chipset = storage = ram = os = weight = gpu = null;
			
			Elements characteristics = doc.select("div.caracLine");
			for (Element characteristic : characteristics) {
				content = characteristic.select("div.caracDesc").text();
				switch(characteristic.select("div.caracName").text()) {
				case "Processeur":
					cpu = content.substring(0, content.indexOf("Fréquence :")).trim();
					System.out.println(cpu);
					break;
				case "Chipset":
					chipset = content.trim();
					System.out.println(chipset);
					break;
				case "Carte graphique":
					if(!content.equals("Pas de carte graphique dédiée")) {
						dedicatedGpu = content.substring(0, content.indexOf("Mémoire :")).trim();
						System.out.println(dedicatedGpu);
					}
					break;
				case "Mémoire (RAM)":
					ram = content.substring(0, content.indexOf("Configuration :")).trim();
					System.out.println(ram);
					break;
				case "Stockage":
					storage = content.trim();
					System.out.println(storage);
					break;
				case "Ecran":
					screenSize = content.substring(0, content.indexOf("pouces")).trim();
					String resolutionInfos = content.substring(content.indexOf("Résolution : "), content.indexOf("Type de dalle :")).trim();
					screenResolution = resolutionInfos.substring(resolutionInfos.indexOf("("), resolutionInfos.indexOf(")") + 1).trim();
					System.out.println(screenSize);
					System.out.println(screenResolution);
					break;
				case "Système d'exploitation":
					os = content.trim();
					System.out.println(os);
					break;
				case "Poids":
					weight = content.trim();
					System.out.println(weight);
					break;
				}
			}
			
			gpu = dedicatedGpu != null ? dedicatedGpu : chipset;
			System.out.println(gpu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
