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
@jakarta.persistence.Table(name = "m_menu")
@SQLDelete(sql = "UPDATE m_menu SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends AbstractEntity {

	@Id
	@Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "menu_name")
	private String name;
	@Column(name = "menu_prize")
	private int prize;
	@Column(name = "menu_pic")
	private String pic;
	@Column(name = "menu_desc")
	private String desc;
	@Column(name = "menu_close")
	private boolean close;

}
