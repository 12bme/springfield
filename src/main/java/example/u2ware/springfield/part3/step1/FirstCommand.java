package example.u2ware.springfield.part3.step1;

import lombok.Getter;
import lombok.Setter;

import com.u2ware.springfield.config.Springfield;
import com.u2ware.springfield.config.Springfield.Strategy;
import com.u2ware.springfield.repository.QueryMethod;

import example.u2ware.springfield.part1.step2.JpaBean;

@Springfield(
	strategy=Strategy.JPA,
	entity=JpaBean.class,
	topLevelMapping="/part3/step11"
)
@QueryMethod("findByName")
public class FirstCommand {

	private @Getter @Setter String name;
}