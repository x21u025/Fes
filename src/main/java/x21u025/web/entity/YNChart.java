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
@jakarta.persistence.Table(name = "m_ynchart")
@SQLDelete(sql = "UPDATE m_ynchart SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YNChart extends AbstractEntity {

	@Id
	@Column(name = "yn_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "yn_text")
	private String text;
	@Column(name = "yn_weight")
	private int weight;
	@Column(name = "bg_id")
	private String bgId;
	@Column(name = "yn_sort")
	private int sort;

}
