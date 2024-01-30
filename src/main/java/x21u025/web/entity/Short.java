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
@jakarta.persistence.Table(name = "m_short")
@SQLDelete(sql = "UPDATE m_short SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Short extends AbstractEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "s_id")
	private int id;

	@Column(name = "s_short")
	private String shortUrl;

	@Column(name = "s_path")
	private String path;

}
