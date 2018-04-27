package il.brand.def.matcher.persistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import il.brand.def.matcher.persistance.backendmodel.Entity;

@Repository
public class EntityRepository<T extends Entity> {
	private final Set<T> data = new HashSet<>(); // check entity primary key
	private final Map<Integer, List<T>> codeIndex = new HashMap<>(); // simulate DB indexing

	public boolean save(T entity) {
		boolean added = data.add(entity);
		if (added) {
			entity.setCreatedAt(new Date());
			codeIndex.computeIfAbsent(entity.getCode(), code -> new ArrayList<>()).add(entity);
		}
		return added;
	}
	
/*
 * I've interpreted the closest words by word's score as words with exactly the same value of score OR nearest possible.
 * For example  {a, b, a1, b1, a2}:a => {a1,a2,a}, {a,b,c,d,e,f,g}:d =>{d,e,c}
 * 
 DB(MySQL Syntax) for selection from DB:
 
  SELECT *
  FROM Entity 
  ORDER BY ABS(code - :code)
  LIMIT :count
 
*/
	public List<Entity> findMatched(final int code, int count) {
		List<Integer> sortedKeys = new ArrayList<Integer>(codeIndex.keySet());
		Collections.sort(sortedKeys, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Math.abs(code - o1) - Math.abs(code - o2);
			}
		});
		List<Entity> result = new ArrayList<>(count);
		Iterator<Integer> iteratorCode = sortedKeys.iterator();
		while (result.size() < count && iteratorCode.hasNext()) {
			Iterator<T> iteratorEntity = codeIndex.get(iteratorCode.next()).iterator();
			while(result.size() < count && iteratorEntity.hasNext()) {
				result.add(iteratorEntity.next());
			}
		}
		return result;
	}

}
