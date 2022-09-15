CREATE TABLE IF NOT EXISTS admins(id int AUTO_INCREMENT, username, hashedPassword);
CREATE TABLE IF NOT EXISTS authors (id varChar(255), surname varChar(255), initials varChar(255), title varChar(255), institution varChar(255), rating Char, PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS authorToSubfieldsMap (authorId varChar(255), subfieldId varChar(255));
CREATE TABLE IF NOT EXISTS contributions (publicationId varChar(255), contributorId varChar(255), type Enum("MainAuthor", "CoAuthor"));
CREATE TABLE IF NOT EXISTS institutions (id varChar(255), name varChar(255), latitude Decimal(8, 8), longitude Decimal(8, 8));
CREATE TABLE IF NOT EXISTS publications (id varChar(255), title varChar(255), citationCount int, externalLink varChar(500), year varChar(255));
CREATE TABLE IF NOT EXISTS subFields (id varChar(255), name varChar(255));

-- Populate institutions table
INSERT INTO institutions (id, name, latitude, longitude) VALUES ("Aharonson", "V", "Prof", "University of the Witwatersrand", "C" );
