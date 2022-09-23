SELECT 'Setting up database aiweb...';

CREATE DATABASE IF NOT EXISTS aiweb;
USE aiweb;

-- Clear database
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS allAuthors;
DROP TABLE IF EXISTS authorToSubfieldsMap;
DROP TABLE IF EXISTS contributions;
DROP TABLE IF EXISTS institutions;
DROP TABLE IF EXISTS publications;
DROP TABLE IF EXISTS subFields;
DROP TABLE IF EXISTS aiKeywords;

CREATE TABLE IF NOT EXISTS admins(id int AUTO_INCREMENT, username varChar(255), hashedPassword varChar(255), PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS authors (id varChar(255), surname varChar(255), initials varChar(255), title varChar(255), institution varChar(255), rating Char, PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS allAuthors (id varChar(255), surname varChar(255), initials varChar(255), title varChar(255), institution varChar(255), rating Char, PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS authorToSubfieldsMap (authorId varChar(255), subfieldId varChar(255));
CREATE TABLE IF NOT EXISTS contributions (publicationId varChar(255), contributorId varChar(255), type Enum("MainAuthor", "CoAuthor"));
CREATE TABLE IF NOT EXISTS institutions (id varChar(255), name varChar(255), latitude numeric(10, 6), longitude numeric(10, 6));
CREATE TABLE IF NOT EXISTS publications (id varChar(255), title varChar(255), citationCount int, externalLink varChar(500), year varChar(255));
CREATE TABLE IF NOT EXISTS subFields (id varChar(255), name varChar(255));
CREATE TABLE IF NOT EXISTS aiKeywords (keyword varChar(255));

-- Populate keywords table
INSERT INTO aiKeywords (keyword) VALUES ("artificial intelligence");
INSERT INTO aiKeywords (keyword) VALUES ("machine learning");
INSERT INTO aiKeywords (keyword) VALUES ("neural networks");
INSERT INTO aiKeywords (keyword) VALUES ("cognitive computing");
INSERT INTO aiKeywords (keyword) VALUES ("natural language processing");
INSERT INTO aiKeywords (keyword) VALUES ("computer vision");
INSERT INTO aiKeywords (keyword) VALUES ("image processing");
INSERT INTO aiKeywords (keyword) VALUES ("pattern recognition");
INSERT INTO aiKeywords (keyword) VALUES ("deep learning");
INSERT INTO aiKeywords (keyword) VALUES ("deep reinforcement learning");
INSERT INTO aiKeywords (keyword) VALUES ("speech recognition");
INSERT INTO aiKeywords (keyword) VALUES ("brain-computer interfacing");
INSERT INTO aiKeywords (keyword) VALUES ("automatic speech recognition");

-- Populate institutions table
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_south_africa", "University of South Africa", 25.7677,28.1993 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("north-west_university", "North-West University", 26.6906, 27.0929);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_pretoria", "University of Pretoria", 25.7545, 28.2314);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("tshwane_university_of_technology", "Tshwane University of Technology", 25.7322, 28.1619);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_johannesburg", "University of Johannesburg", 26.1832, 27.9990);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_kwaZulu-Natal", "University of KwaZulu-Natal",29.8674 ,30.9807 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_free_state", "University of the Free State",29.1076 ,26.1925 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("cape_peninsula_university_of_technology", "Cape Peninsula University of Technology",33.9305 ,18.6391 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_witwatersrand", "University of the Witwatersrand",26.1929 ,28.0305 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_stellenbosch", "University of Stellenbosch",33.9328 ,18.8644 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_cape_town", "University of Cape Town", 33.9577, 18.4612);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("nelson_mandela_metropolitan_university", "Nelson Mandela Metropolitan University",34.0010 ,25.6715 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("walter_sisulu_university", "Walter Sisulu University",31.6036 ,28.7507 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("durban_university_of_technology", "Durban University of Technology",29.8536 ,31.0061);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_limpopo", "University of Limpopo",23.8888 ,29.7386 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("vaal_university_of_technology", "Vaal University of Technology",26.7115 ,27.8617 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_zululand", "University of Zululand",28.7576 ,32.0497 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_the_western_cape", "University of the Western Cape",33.9335 ,18.6280 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("central_university_of_technology", "Central University of Technology",29.1217 ,26.2128 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_fort_hare", "University of Fort Hare",32.7859 ,26.8459);
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_venda", "University of Venda",22.9761 ,30.4465 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("mangosuthu_university_of_technology", "Mangosuthu University of Technology",29.9698 ,30.9133 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("rhodes_university", "Rhodes University",33.3136 ,26.5163 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("sefako_makgatho_health_sciences_university", "Sefako Makgatho Health Sciences University",25.6192 ,28.0161 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("university_of_mpumalanga", "University of Mpumalanga",25.4371 ,30.9818 );
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("sol_plaatje_university", "Sol Plaatje University",28.7450 ,24.7662 );

SELECT 'Database setup complete!';
