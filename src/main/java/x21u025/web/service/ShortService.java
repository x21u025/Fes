package x21u025.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.dto.ShortListParam;
import x21u025.web.entity.Short;
import x21u025.web.repository.ShortRepository;

@Service
public class ShortService {

	@Autowired
	ShortRepository shortRepository;

	public List<Short> getAll() {
		return shortRepository.findAll();
	}

	public Short getById(int id) {
		return shortRepository.getById(id);
	}

	public Short getByUrl(String shortUrl) {
		return shortRepository.getByShortUrl(shortUrl);
	}


	public void updateByShortListParam(ShortListParam shortListParam) {
		ArrayList<Short> list = new ArrayList<Short>();
		for(Short shortUrl: shortListParam.getShortList()) {
			Short s = getById(shortUrl.getId());
			if(s == null) {
				list.add(shortUrl);
			} else {
				list.add(changeMenu(s, shortUrl));
			}
		}
		shortRepository.saveAll(list);
	}


	private Short changeMenu(Short base, Short edited) {
		base.setShortUrl(edited.getShortUrl());
		base.setPath(edited.getPath());

		return base;
	}
}
