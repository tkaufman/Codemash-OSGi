DROP TABLE IF EXISTS entries, raffles;

CREATE TABLE raffles (
  id INT NOT NULL auto_increment,
  name VARCHAR(50) NOT NULL,
  number_of_winners INT(2) NOT NULL,
  started DATE NOT NULL,
  PRIMARY KEY  (id)
) TYPE=innodb;

CREATE TABLE entries (
  id INT NOT NULL auto_increment,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  created DATE NOT NULL,
  raffle_id INT,
  KEY (raffle_id),
  FOREIGN KEY (raffle_id) REFERENCES raffles (id) ON DELETE CASCADE,
  PRIMARY KEY (id)
) TYPE=innodb;
