package org.forum.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://geek.csdn.net/").get();
			Elements root_ele = doc.getElementById("blog_list_wrap").getElementsByClass("tracking-ad");
			for(Element e : root_ele){
				System.out.println(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
