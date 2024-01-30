package x21u025.web.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import x21u025.web.entity.YNChart;

@Data
public class YNChartListParam {

	private List<YNChartDto> ynList;

	public void setYnList(List<YNChart> ynChartList) {
		ynList = new ArrayList<YNChartDto>();

		for(YNChart yn : ynChartList) {
			YNChartDto dto = new YNChartDto();
			dto.setId(yn.getId());
			dto.setText(yn.getText());
			dto.setWeight(yn.getWeight());
			dto.setSort(yn.getSort());
			dto.setDeleteFlag(yn.isDeleteFlag());

			List<Integer> list = new ArrayList<Integer>();
			try {
				JsonNode node = new ObjectMapper().readTree(yn.getBgId());
				for(JsonNode n : node) {
					list.add(n.asInt());
				}
			} catch(Exception e) {}
			dto.setBgId(list.stream().mapToInt(Integer::intValue).toArray());
			ynList.add(dto);
		}
	}

	public List<YNChart> getYNChartList() {
		List<YNChart> list = new ArrayList<YNChart>();

		for(YNChartDto dto : getYnList()) {
			YNChart yn = new YNChart();
			yn.setId(dto.getId());
			yn.setText(dto.getText());
			yn.setWeight(dto.getWeight());
			yn.setBgId(Arrays.toString(dto.getBgId()));
			yn.setSort(dto.getSort());
			yn.setDeleteFlag(dto.isDeleteFlag());

			list.add(yn);
		}

		return list;
	}

}
