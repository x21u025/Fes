package x21u025.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import x21u025.web.entity.Microsoft;
import x21u025.web.repository.MicrosoftRepository;

@Service
public class MicrosoftService {

	@Autowired
	MicrosoftRepository microsoftRepository;

	public List<Microsoft> getAll() {
		return microsoftRepository.findAll();
	}

	public Microsoft getByEmail(String email) {
		return microsoftRepository.getReferenceById(email);
	}

	public boolean isEmployeeByEmail(String email) {
		try {
			return getByEmail(email).isEmployee();
		} catch (EntityNotFoundException e) {
			return false;
		}
	}
}
