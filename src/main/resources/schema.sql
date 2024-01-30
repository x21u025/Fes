--DROP TABLE IF EXISTS m_table;
--DROP TABLE IF EXISTS m_table_type;
--DROP TABLE IF EXISTS m_boardgame;
--DROP TABLE IF EXISTS m_menu;
--DROP TABLE IF EXISTS t_register;
--DROP TABLE IF EXISTS m_microsoft;
--DROP TABLE IF EXISTS m_ynchart;
--DROP TABLE IF EXISTS m_short;

CREATE TABLE IF NOT EXISTS m_boardgame (
  bg_id INT NOT NULL AUTO_INCREMENT,
  bg_name LONGTEXT NOT NULL,
  bg_en_name LONGTEXT NOT NULL,
  bg_pic LONGTEXT DEFAULT NULL COMMENT 'url',
  bg_desc LONGTEXT NOT NULL,
  bg_color LONGTEXT NOT NULL DEFAULT '#FFFFFF',
  bg_player_low INT NOT NULL DEFAULT 0,
  bg_player_high INT NOT NULL DEFAULT 0,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (bg_id)
);

CREATE TABLE IF NOT EXISTS m_menu (
  menu_id INT AUTO_INCREMENT PRIMARY KEY,
  menu_name LONGTEXT NOT NULL,
  menu_prize INT NOT NULL,
  menu_pic LONGTEXT NOT NULL COMMENT 'url',
  menu_desc LONGTEXT NOT NULL,
  menu_close BOOLEAN NOT NULL DEFAULT FALSE,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS m_table_type (
  ty_id INT NOT NULL AUTO_INCREMENT,
  ty_name CLOB NOT NULL,
  ty_color CLOB NOT NULL DEFAULT '#FFFFFF',
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (ty_id)
);

CREATE TABLE IF NOT EXISTS m_table (
  table_row INT NOT NULL,
  table_column INT NOT NULL,
  ty_id INT NOT NULL,
  table_dx INT NOT NULL DEFAULT 0,
  table_dy INT NOT NULL DEFAULT 0,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (table_row, table_column),
  INDEX idx_m_table_m_table_type (ty_id),
  FOREIGN KEY (ty_id) REFERENCES m_table_type (ty_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS t_register (
  reg_id INT NOT NULL AUTO_INCREMENT,
  reg_cart CLOB NOT NULL,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (reg_id),
  CHECK (reg_cart IS JSON)
);

CREATE TABLE IF NOT EXISTS m_microsoft (
  ms_email VARCHAR(50) NOT NULL DEFAULT '',
  ms_name CLOB DEFAULT NULL,
  ms_group CLOB DEFAULT NULL,
  ms_employee BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (ms_email)
);

CREATE TABLE IF NOT EXISTS m_ynchart (
  yn_id INT NOT NULL AUTO_INCREMENT,
  yn_text CLOB NOT NULL,
  yn_weight INT NOT NULL DEFAULT 0,
  bg_id CLOB DEFAULT '[]' NOT NULL,
  yn_sort INT NOT NULL DEFAULT 0,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (yn_id)
);

CREATE TABLE IF NOT EXISTS m_short (
  s_id INT NOT NULL AUTO_INCREMENT,
  s_short VARCHAR(50) NOT NULL DEFAULT '',
  s_path CLOB DEFAULT NULL,
  create_date TIMESTAMP NOT NULL,
  update_date TIMESTAMP NOT NULL,
  version INT NOT NULL,
  delete_flag BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (s_id)
);
