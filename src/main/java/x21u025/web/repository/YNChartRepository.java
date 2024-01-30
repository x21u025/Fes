package x21u025.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import x21u025.web.entity.YNChart;

public interface YNChartRepository extends JpaRepository<YNChart, String> {

	public YNChart findById(int id);

}
