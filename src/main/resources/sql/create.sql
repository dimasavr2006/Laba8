BEGIN;

CREATE TABLE IF NOT EXISTS collection (
    element_id INTEGER ,
    owner_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS hb (
    id SERIAL PRIMARY KEY ,
    name TEXT NOT NULL ,
    coords_id INTEGER NOT NULL ,
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE ,
    real_hero BOOLEAN NOT NULL ,
    has_toothpick BOOLEAN NOT NULL ,
    impact_speed BIGINT ,
    soundtrack_name TEXT NOT NULL ,
    weapon_type TEXT NOT NULL ,
    mood TEXT ,
    car_id INTEGER
);

CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY ,
    login TEXT NOT NULL UNIQUE ,
    password TEXT
);

CREATE TABLE IF NOT EXISTS coords (
    id_coord SERIAL PRIMARY KEY ,
    x BIGINT NOT NULL ,
    y BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS cars (
    id_car SERIAL PRIMARY KEY ,
    name_car TEXT NOT NULL ,
    cool BOOLEAN NOT NULL
);

ALTER TABLE collection
    ADD FOREIGN KEY (element_id) REFERENCES hb(id)
        ON UPDATE CASCADE ON DELETE CASCADE ;

ALTER TABLE collection
    ADD FOREIGN KEY (owner_id) REFERENCES users(user_id)
        ON UPDATE CASCADE ON DELETE CASCADE ;

ALTER TABLE hb
    ADD FOREIGN KEY (coords_id) REFERENCES coords(id_coord)
        ON UPDATE CASCADE ON DELETE CASCADE ;

ALTER TABLE hb
    ADD FOREIGN KEY (car_id) REFERENCES cars(id_car)
        ON UPDATE CASCADE ON DELETE CASCADE ;

ALTER TABLE hb
    ADD CONSTRAINT name_not_empty
        CHECK (name <> '');

END ;