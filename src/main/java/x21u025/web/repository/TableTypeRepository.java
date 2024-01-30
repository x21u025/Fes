package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import x21u025.web.entity.TableType;

@Repository
public interface TableTypeRepository extends JpaRepository<TableType, String>{

	public TableType getById(int id);

}
