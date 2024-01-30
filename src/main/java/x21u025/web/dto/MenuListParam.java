package x21u025.web.dto;

import java.util.List;

import lombok.Data;
import x21u025.web.entity.Menu;

@Data
public class MenuListParam {

	private List<Menu> menuList;

}
