package t5750.springboot2keycloak.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/heroes")
public class HeroController {
	private static List<Hero> someHeroes = new ArrayList<>(4);
	static {
		someHeroes.add(new Hero(1, "Ken"));
		someHeroes.add(new Hero(2, "Yannick"));
		someHeroes.add(new Hero(3, "Pieter"));
	}

	@GetMapping
	public List<Hero> heroes() {
		return someHeroes;
	}

	@GetMapping("/{id}")
	public Hero hero(@PathVariable("id") String id) {
		return someHeroes.stream()
				.filter(h -> Integer.toString(h.getId()).equals(id))
				.findFirst().orElse(null);
	}
}

class Hero {
	private final int id;
	private final String name;

	public Hero(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}