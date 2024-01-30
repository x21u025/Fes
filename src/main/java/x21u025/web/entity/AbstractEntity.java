package x21u025.web.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Data;

/**
 * DB共通Entity
 */
@MappedSuperclass
@Data
public class AbstractEntity {

	/** 登録日時 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	/** 更新日時 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	/** バージョン */
	@Version
	@Column(name = "version")
	private int version;

	/** 削除フラグ */
	@Column(name = "delete_flag")
	private boolean deleteFlag;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersist() {
		// 登録日、更新日を設定
		Date date = new Date();
		createDate = date;
		updateDate = date;
		version = 1;
	}

	/**
	 * 更新前処理
	 */
	@PreUpdate
	public void preUpdate() {
		// 更新日を設定
		updateDate = new Date();
	}
}