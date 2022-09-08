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
INSERT INTO subFields (subField) VALUES ("Deep learning");
INSERT INTO subFields (subField) VALUES ("Artificial Intelligence");

CREATE TABLE IF NOT EXISTS authorSubfieldsMap (authorID varChar(255), subFieldID int);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Daramola", 1);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Bagula", 7);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 7 );
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 2 );
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Atemkeng Teufack", 6 );
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 2);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 4);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aldrich", 5);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 2);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 3);
INSERT INTO authorSubfieldsMap (authorID, subFieldID) VALUES ("Aharonson", 1);


CREATE TABLE IF NOT EXISTS publications (ID varChar(255), title varChar(255), citationCount int, externalLink varChar(500), publicationDate varChar(255));
INSERT INTO publications (ID, title, citationCount, externalLink, publicationDate) VALUES ("Aharonson", "The automatic recognition of emotions in speech", 71, "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=MlXzlYQAAAAJ&citation_for_view=MlXzlYQAAAAJ:3fE2CSJIrl8C", "2011");
INSERT INTO publications (ID, title, citationCount, externalLink, publicationDate) VALUES ("Aldrich", "ANN-DT: an algorithm for extraction of decision trees from artificial neural networks", 176, "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=iLJjvVYAAAAJ&citation_for_view=iLJjvVYAAAAJ:d1gkVwhDpl0C", "1999");
INSERT INTO publications (ID, title, citationCount, externalLink, publicationDate) VALUES ("Atemkeng Teufack", "NenUFAR: Instrument description and science case", 35, "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=kO4LdnkAAAAJ&citation_for_view=kO4LdnkAAAAJ:YsMSGLbcyi4C", "2015");
INSERT INTO publications (ID, title, citationCount, externalLink, publicationDate) VALUES ("Bagula", "Online traffic engineering: the least interference optimization algorithm", 76, "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=7RjFNjIAAAAJ&citation_for_view=7RjFNjIAAAAJ:ZHo1McVdvXMC", "2004");
INSERT INTO publications (ID, title, citationCount, externalLink, publicationDate) VALUES ("Daramola", "Pattern-based security requirements specification using ontologies and boilerplates", 37, "https://scholar.google.com/citations?view_op=view_citation&hl=en&user=B2uyXAUAAAAJ&citation_for_view=B2uyXAUAAAAJ:YOwf2qJgpHMC", "2012");

CREATE TABLE IF NOT EXISTS institutions (ID int AUTO_INCREMENT, institutionName varChar(255), latitute int, longitute int, PRIMARY KEY(ID));


