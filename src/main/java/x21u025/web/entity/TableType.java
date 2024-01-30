package x21u025.web.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@jakarta.persistence.Table(name = "m_table_type")
@SQLDelete(sql = "UPDATE m_table_type SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@NoArgsConstructor
public class TableType extends AbstractEntity {

	@Id
	@Column(name = "ty_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ty_name")
	private String name;
	@Column(name = "ty_color")
	private String color;

}
