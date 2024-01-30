package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.dto.MenuListParam;
import x21u025.web.entity.Menu;
import x21u025.web.repository.MenuRepository;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;

	public List<Menu> getAll() {
		return menuRepository.findAll();
	}

	public Menu getById(int id) {
		return menuRepository.getById(id);
	}


	public void updateByBoardGameListParam(MenuListParam menuListParam) {
		ArrayList<Menu> list = new ArrayList<Menu>();
		for(Menu menu: menuListParam.getMenuList()) {
			Menu m = getById(menu.getId());
			if(m == null) {
				list.add(menu);
			} else {
				list.add(changeMenu(m, menu));
			}
		}
		menuRepository.saveAll(list);
	}


	private Menu changeMenu(Menu base, Menu edited) {
		base.setName(edited.getName());
		base.setPrize(edited.getPrize());
		base.setPic(edited.getPic());
		base.setDesc(edited.getDesc());
		base.setClose(edited.isClose());
		base.setDeleteFlag(edited.isDeleteFlag());

		return base;
	}

}
