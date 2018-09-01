package medicallab.model.specification;

import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import medicallab.model.User;
import medicallab.model.User_;

public class UserSpecifications {

	public static Specification<User> firstnameConcatLastnameStartingWiths(String searchText) { 
		return (root, query, builder) -> {
			Expression<String> firstnameConcatLastnameExpression;
			Expression<String> firstnameExpression = builder.upper(root.get(User_.firstname));
			Expression<String> lastnameExpression  = builder.upper(root.get(User_.lastname));
			
			firstnameConcatLastnameExpression = builder.concat(builder.concat(firstnameExpression, " "), 
															   lastnameExpression);
			return builder.like(firstnameConcatLastnameExpression, searchText.toUpperCase() + "%");
		};
	}
}