package guru.qa;

import guru.qa.domain.Owner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OwnersManager implements IOwnersManager {

	private DataSource ds = DataSourceProvider.INSTANCE.getDataSource();

	@Override
	public int createOwner(Owner owner) {
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
				 "INSERT INTO owners (first_name, last_name, address, city, telephone)\n" +
					 "VALUES (?, ?, ?, ?, ?)",
				 Statement.RETURN_GENERATED_KEYS
			 )) {
			ps.setString(1, owner.getFirstName());
			ps.setString(2, owner.getLastName());
			ps.setString(3, owner.getAddress());
			ps.setString(4, owner.getCity());
			ps.setString(5, owner.getTelephone());
			ps.executeUpdate();

			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void deleteOwner(int id) {
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
				 "DELETE FROM owners WHERE id = ?"
			 )) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		List<Owner> result = new ArrayList<>();

		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
				 "SELECT * FROM owners WHERE last_name = ?"
			 )) {
			ps.setString(1, lastName);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				result.add(Owner.builder()
					.firstName(resultSet.getString("first_name"))
					.lastName(resultSet.getString("last_name"))
					.address(resultSet.getString("address"))
					.city(resultSet.getString("city"))
					.telephone(resultSet.getString("telephone"))
					.build());
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
