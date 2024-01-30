package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import x21u025.web.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

	public Menu getById(int id);

}
