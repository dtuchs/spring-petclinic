package guru.qa;

import guru.qa.domain.Owner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;

public class OwnersManagerNG implements IOwnersManager {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(
		DataSourceProvider.INSTANCE.getDataSource()
	);

	@Override
	public int createOwner(Owner owner) {
		return new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(Map.of(
				"first_name", owner.getFirstName(),
				"last_name", owner.getLastName(),
				"address", owner.getAddress(),
				"city", owner.getCity(),
				"telephone", owner.getTelephone()
			)).intValue();
	}

	@Override
	public void deleteOwner(int id) {
		jdbcTemplate.update("DELETE FROM owners WHERE id = ?", id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		return null;
	}
}
