package org.safebusiness.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.safebusiness.Article;
import org.safebusiness.api.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@Autowired
	ArticleRepository articleRepo;
	
	@GetMapping("safebusiness/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("safebusiness")
	public String baseUrl() {
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/newArticle")
	public String newArticle(Model model) {
		model.addAttribute("article", new Article());
		return "addArticle";
	}
	
	@PostMapping("addArticle")
	public String addArticle(@Valid Article article) {
		articleRepo.save(article);
		return "redirect:safebusiness/index";
	}
	
	private List<Article> parseArticleString(String val) {
		List<Article> ret = new ArrayList<>();
		String[] stringIds = val.split(",");

		for (String id : stringIds) {
			// Lookup the Article
			try {
				if (articleRepo.findById(Integer.parseInt(id)).isPresent()) {
					ret.add(articleRepo.findById(Integer.parseInt(id)).get());	
				}
			} catch(NumberFormatException ex) {
				// Ignore..
			}
		}
		return ret;
	}
}
