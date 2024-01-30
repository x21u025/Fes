package x21u025.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@jakarta.persistence.Table(name = "m_microsoft")
@Getter
public class Microsoft {

	@Id
	@Column(name = "ms_email")
	private String email;

	@Column(name = "ms_name")
	private String name;

	@Column(name = "ms_group")
	private String group;

	@Column(name = "ms_employee")
	private boolean employee;



}
