package x21u025.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import x21u025.web.entity.Menu;
import x21u025.web.entity.Register;
import x21u025.web.service.MenuService;

@Data
public class DetailListParam {

	private List<DetailDto> detailList;

	public void setDetailList(List<Register> registerList, MenuService menuService) {
		detailList = new ArrayList<DetailDto>();

		for(Register r : registerList) {
			DetailDto detailDto = new DetailDto();
			detailDto.setId(r.getId());
			detailDto.setCreateDate(r.getCreateDate());

			HashMap<Menu, Integer> map = new HashMap<Menu, Integer>();
			int total = 0;
			try {
				JsonNode node = new ObjectMapper().readTree(r.getCart());
				Iterator<String> it = node.fieldNames();
				while(it.hasNext()) {
					int id = Integer.parseInt(it.next());
					Menu menu = menuService.getById(id);
					int count = node.get(id + "").asInt();
					map.put(menu, count);
					total += count * menu.getPrize();
				}
			} catch(Exception e) {}

			detailDto.setDetailMap(map);
			detailDto.setTotal(total);
			detailList.add(detailDto);
		}
	}

}
