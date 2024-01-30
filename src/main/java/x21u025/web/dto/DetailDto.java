package x21u025.web.dto;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import x21u025.web.entity.Menu;

@Data
public class DetailDto {

	private int id;
	private Map<Menu, Integer> detailMap;
	private int total;
	private Date createDate;

}
