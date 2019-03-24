CREATE TABLE IF NOT EXISTS siteuser(
 userid serial PRIMARY KEY,
 username VARCHAR(32) NOT NULL,
 password VARCHAR(32) NOT NULL,
 email VARCHAR(64) NOT NULL,
 banned BOOLEAN NOT NULL,
 permission VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS post(
 postid serial PRIMARY KEY,
 userid INTEGER NOT NULL REFERENCES siteuser(userid),
 parentid INTEGER NOT NULL REFERENCES post(postid),
 title VARCHAR(32) NOT NULL,
 text VARCHAR(64) NOT NULL,
 posttype VARCHAR(64) NOT NULL,
 creationdate TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS tag(
 tagid serial PRIMARY KEY,
 title VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS post_tag(
 posttagid serial PRIMARY KEY,
 postid INTEGER REFERENCES post(postid) ON DELETE CASCADE ON UPDATE CASCADE,
 tagid INTEGER REFERENCES tag(tagid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS vote(
 voteid serial PRIMARY KEY,
 userid INTEGER NOT NULL REFERENCES siteuser(userid),
 postid INTEGER NOT NULL REFERENCES post(postid),
 upvote BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX posttag_postid_tagid_uindex ON post_tag(postid, tagid);




