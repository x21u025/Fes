package x21u025.web.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.dto.YNChartListParam;
import x21u025.web.entity.YNChart;
import x21u025.web.repository.YNChartRepository;

@Service
public class YNChartService {

	@Autowired
	private YNChartRepository ynChartRepository;

	public List<YNChart> getAll() {
		return ynChartRepository.findAll();
	}

	public YNChart getById(int id) {
		return ynChartRepository.findById(id);
	}

	public void updateByYNChartListParam(YNChartListParam ynChartListParam) {
		ArrayList<YNChart> list = new ArrayList<YNChart>();
		for(YNChart ynChart : ynChartListParam.getYNChartList()) {
			YNChart yn = getById(ynChart.getId());
			if(yn == null) {
				list.add(ynChart);
			} else {
				list.add(changeYNChart(yn, ynChart));
			}
		}

		list.sort(Comparator.comparingInt(YNChart::getSort).thenComparingInt(YNChart::getId));
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSort(i + 1);
		}

		ynChartRepository.saveAll(list);
	}


	private YNChart changeYNChart(YNChart base, YNChart edited) {
		base.setText(edited.getText());
		base.setWeight(edited.getWeight());
		base.setBgId(edited.getBgId());
		base.setSort(edited.getSort());
		base.setDeleteFlag(edited.isDeleteFlag());
		return base;
	}



}
