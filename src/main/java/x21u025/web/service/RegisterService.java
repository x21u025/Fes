package x21u025.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x21u025.web.entity.Register;
import x21u025.web.repository.RegisterRepository;

@Service
public class RegisterService {

	@Autowired
	RegisterRepository registerRepository;

	public List<Register> getAll() {
		return registerRepository.findAll();
	}

	public void addRegister(Register register) {
		registerRepository.save(register);
	}
}
