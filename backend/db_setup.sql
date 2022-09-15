CREATE DATABASE IF NOT EXISTS aiweb;
USE aiweb;

CREATE TABLE IF NOT EXISTS admins(id int AUTO_INCREMENT, username varChar(255), hashedPassword varChar(255), PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS authors (id varChar(255), surname varChar(255), initials varChar(255), title varChar(255), institution varChar(255), rating Char, PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS authorToSubfieldsMap (authorId varChar(255), subfieldId varChar(255));
CREATE TABLE IF NOT EXISTS contributions (publicationId varChar(255), contributorId varChar(255), type Enum("MainAuthor", "CoAuthor"));
CREATE TABLE IF NOT EXISTS institutions (id varChar(255), name varChar(255), latitude Decimal(8, 8), longitude Decimal(8, 8));
CREATE TABLE IF NOT EXISTS publications (id varChar(255), title varChar(255), citationCount int, externalLink varChar(500), year varChar(255));
CREATE TABLE IF NOT EXISTS subFields (id varChar(255), name varChar(255));

-- Populate institutions table
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_south_africa", "University of South Africa", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("north-west_university", "North-West University", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_pretoria", "University of Pretoria", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("tshwane_university_of_technology", "Tshwane University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_johannesburg", "University of Johannesburg", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_kwaZulu-Natal", "University of KwaZulu-Natal", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_free_state", "University of the Free State", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("cape_peninsula_university_of_technology", "Cape Peninsula University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_witwatersrand", "University of the Witwatersrand", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_stellenbosch", "University of Stellenbosch", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_cape_town", "University of Cape Town", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("nelson_mandela_metropolitan_university", "Nelson Mandela Metropolitan University", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("walter_sisulu_university", "Walter Sisulu University", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("durban_university_of_technology", "Durban University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_limpopo", "University of Limpopo", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("vaal_university_of_technology", "Vaal University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_zululand", "University of Zululand", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_western_cape", "University of the Western Cape", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("central_university_of_technology", "Central University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_fort_hare", "University of Fort Hare", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_venda", "University of Venda", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("mangosuthu_university_of_technology", "Mangosuthu University of Technology", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("rhodes_university", "Rhodes University", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("sefako_makgatho_health_sciences_university", "Sefako Makgatho Health Sciences University", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_mpumalanga", "University of Mpumalanga", 0.000, 0.000);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("sol_plaatje_university", "Sol Plaatje University", 0.000, 0.000);

INSERT INTO admins (username, hashedPassword) VALUES ("admin", "4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2");
