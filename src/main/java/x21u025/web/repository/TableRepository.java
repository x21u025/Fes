package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import x21u025.web.entity.Table;

@Repository
public interface TableRepository extends JpaRepository<Table, String>{

}
