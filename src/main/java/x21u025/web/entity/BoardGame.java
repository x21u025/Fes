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
@jakarta.persistence.Table(name = "m_boardgame")
@SQLDelete(sql = "UPDATE m_boardgame SET delete_flag = true WHERE id=?")
@Where(clause = "delete_flag=false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGame extends AbstractEntity {

	@Id
	@Column(name = "bg_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "bg_name")
	private String name;
	@Column(name = "bg_en_name")
	private String enName;
	@Column(name = "bg_pic")
	private String pic;
	@Column(name = "bg_desc")
	private String desc;
	@Column(name = "bg_player_low")
	private int player_low;
	@Column(name = "bg_player_high")
	private int player_high;
	@Column(name = "bg_color")
	private String color;

	public String getPlayer() {
		return player_low + " ~ " + player_high;
	}

}
