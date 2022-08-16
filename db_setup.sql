CREATE TABLE IF NOT EXISTS authors (Surname varChar(255) NOT NULL, Initials varChar(255), Title varChar(255), Institution varChar(255), Rating Char, PRIMARY KEY(Surname));
INSERT INTO authors (Surname, Initials, Title, Institution, Rating) VALUES ("Aharonson", "V", "Prof", "University of the Witwatersrand", "C" );
INSERT INTO authors (Surname, Initials, Title, Institution, Rating) VALUES ("Aldrich", "C", "Prof", "Stellenbosch University", "B" );
INSERT INTO authors (Surname, Initials, Title, Institution, Rating) VALUES ("Atemkeng Teufack", "M", "Dr", "Rhodes University", "Y" );
INSERT INTO authors (Surname, Initials, Title, Institution, Rating) VALUES ("Bagula", "BA", "Prof", "University of the Western Cape", "C" );
INSERT INTO authors (Surname, Initials, Title, Institution, Rating) VALUES ("Daramola", "JO", "Prof", "Cape Peninsula University of Technology", "C" );

CREATE TABLE IF NOT EXISTS subFields (ID int AUTO_INCREMENT, subField varChar(255), PRIMARY KEY(ID));
INSERT INTO subFields (subField) VALUES ("Applied Artificial Intelligence");
INSERT INTO subFields (subField) VALUES ("Machine Learning");
INSERT INTO subFields (subField) VALUES ("Speech Processing");
INSERT INTO subFields (subField) VALUES ("Artificial neural networks");
INSERT INTO subFields (subField) VALUES ("Machine Vision");
INSERT INTO subFields (subField) VALUES ("Deep learning")
INSERT INTO subFields (subField) VALUES ("Artificial Intelligence")

CREATE TABLE IF NOT EXISTS authorSubfieldsMap (authorID varChar(255), subFieldID int);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Daramola", 1)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Bagula", 7)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 7 )
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 2 )
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 6 )
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 2)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 4)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 5)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 2)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 3)
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 1)
