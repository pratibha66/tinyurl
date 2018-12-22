Create database tinyurl;
Use tinyurl;
Create table Users(userid VARCHAR (30) Primary Key, cname varchar(100),
                   createdon TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
                    ENGINE=INNODB;
Create table URL(userid VARCHAR (30), originalurl Varchar(200), shorturl Varchar(20),
                 createdon TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							Foreign key (userid) References Users(userid)
                            ON Delete cascade
                            ON Update cascade )
                            ENGINE=INNODB;