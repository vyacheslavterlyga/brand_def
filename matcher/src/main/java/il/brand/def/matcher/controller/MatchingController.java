package il.brand.def.matcher.controller;

import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import il.brand.def.matcher.businessmodel.InsertResult;
import il.brand.def.matcher.businessmodel.Matched;
import il.brand.def.matcher.persistance.EntityRepository;
import il.brand.def.matcher.persistance.backendmodel.Entity;

@Controller
public class MatchingController {

	private final Random random = new Random();
	
	@Value("${matched.result.size:3}")
	private int matchedResultSize;

	@Autowired
	private EntityRepository<Entity> repository;

	@RequestMapping("/")
	public String root() {
		return "index";
	}

	@PostMapping("/insert/{word}")
	public ResponseEntity<?> insert(@PathVariable(value = "word") final String word) {
		InsertResult result = new InsertResult();
		result.setSuccess(repository.save(new Entity(word, getCode(word))));
		result.setErr(random.nextInt());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/match/{word}")
	public ResponseEntity<?> match(@PathVariable(value = "word") final String word) {
		Matched matched = new Matched(word);
		matched.setTop(repository.findMatched(getCode(word), matchedResultSize).stream().map(x -> x.getName())
				.collect(Collectors.toList()));
		return ResponseEntity.ok(matched);
	}

	private int getCode(String word) {
		int code = 0;
		for (char ch : word.toCharArray())
			if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
				code += (byte) ch;
		return code;
	}

}
