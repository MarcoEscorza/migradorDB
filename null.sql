

CREATE TABLE actor(
	actor_id SMALLINT (5),
	first_name VARCHAR(45),
	last_name VARCHAR(45),
	last_update TIMESTAMP
)
ENGINE=FEDERATED
CONNECTION='mysql://newuser:root@localhost/sakila/actor';

CREATE TABLE address(
	address_id SMALLINT (5),
	address VARCHAR(50),
	address2 VARCHAR(50),
	district VARCHAR(20),
	city_id SMALLINT (5),
	postal_code VARCHAR(10),
	phone VARCHAR(20),
	location GEOMETRY(0),
	last_update TIMESTAMP
)
ENGINE=FEDERATED
CONNECTION='mysql://newuser:root@localhost/sakila/address';

CREATE TABLE city(
	city_id SMALLINT (5),
	city VARCHAR(50),
	country_id SMALLINT (5),
	last_update TIMESTAMP
)
ENGINE=FEDERATED
CONNECTION='mysql://newuser:root@localhost/sakila/city' 