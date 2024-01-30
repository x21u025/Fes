package x21u025.web.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@jakarta.persistence.Table(name = "t_register")
@SQLDelete(sql = "UPDATE t_register SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register extends AbstractEntity {

	@Id
	@Column(name = "reg_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "reg_cart")
	private String cart;

}
