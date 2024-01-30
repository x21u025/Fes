package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortRepository extends JpaRepository<x21u025.web.entity.Short, String> {

	public x21u025.web.entity.Short getById(int id);

	public x21u025.web.entity.Short getByShortUrl(String shortUrl);

}
