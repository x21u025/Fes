package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.dto.TableTypeListParam;
import x21u025.web.entity.TableType;
import x21u025.web.repository.TableTypeRepository;

@Service
public class TableTypeService {

	@Autowired
	TableTypeRepository tableTypeRepository;

	public List<TableType> getAll() {
		return tableTypeRepository.findAll();
	}

	public TableType getById(int id) {
		return tableTypeRepository.getById(id);
	}


	public void updateByBoardGameListParam(TableTypeListParam tableTypeListParam) {
		ArrayList<TableType> list = new ArrayList<TableType>();
		for(TableType tableType: tableTypeListParam.getTableTypeList()) {
			TableType ty = getById(tableType.getId());
			if(ty == null) {
				list.add(tableType);
			} else {
				list.add(changeTableType(ty, tableType));
			}
		}
		tableTypeRepository.saveAll(list);
	}


	private TableType changeTableType(TableType base, TableType edited) {
		base.setName(edited.getName());
		base.setColor(edited.getColor());
		base.setDeleteFlag(edited.isDeleteFlag());

		return base;
	}

}
