package il.brand.def.matcher.persistance.backendmodel;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Entity {
	private String name; 		// unique entity ID
	private int code;			// should be indexed in DB
	@Setter
	private Date createdAt;

	public Entity(String name, int code) {
		this.name = name;
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Entity))
			return false;
		Entity objEntity = (Entity) obj;
		return this.getName().equals(objEntity.getName());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
