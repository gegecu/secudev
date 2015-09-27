package utility;

import java.util.List;
import java.util.Map;

import model.Constant;

public class QueryBuilder {
	public static String buildQuery(List<String> filter, List<String> condition, List<String> dateOption) {
		
		String query = " WHERE `post`.`post` LIKE ? ";
		int j = 0;
		
		if(filter != null && condition != null) {
			for(int i = 0; i < filter.size(); i++) {
				if(filter.get(i).equals("Username")) {
					query += condition.get(i) + " `post`.`" + filter.get(i) + "` = ? ";
				}
				else if (filter.get(i).equals("Post")) {
					query += condition.get(i) + " `post`.`" + filter.get(i) + "` LIKE ? ";
				}
				else if(filter.get(i).equals("Postdate")) {
					if(dateOption != null) {
						if(dateOption.get(j).equals("Between")) {
							query += condition.get(i) + " `post`.`" + filter.get(i) + "` BETWEEN ? " + "AND ? ";
						}
						else {
							query += condition.get(i) + " `post`.`" + filter.get(i) + "` " + dateOption.get(j) + " ? ";
						}
					j++;
					}
				}
			}
		}
		
		return query;
	}
	
}
