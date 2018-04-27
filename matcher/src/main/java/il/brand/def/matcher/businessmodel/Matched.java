package il.brand.def.matcher.businessmodel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Matched {
	@Getter
	private final String query;
	@Getter @Setter
	private List<String> top;
	
	public Matched(String query) {
		this.query = query;
	}
	
}
