DROP DATABASE IF EXISTS projectdb;
CREATE DATABASE projectdb DEFAULT CHARSET=utf8mb4;
USE projectdb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    uname VARCHAR(50) UNIQUE NOT NULL,
    upasshash VARCHAR(64) NOT NULL
);

CREATE TABLE topics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic_id INT NOT NULL,
    user_id INT NOT NULL,
    msg TEXT,
    date_sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (topic_id) REFERENCES topics(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users(uname, upasshash) VALUES
('user1', SHA2('user1', 256)),
('admin', SHA2('admin', 256));

INSERT INTO topics(name, description) VALUES
('General', 'General discussion topic'),
('Tech', 'Technology and innovation');

INSERT INTO messages(topic_id, user_id, msg) VALUES
(1, 1, 'Hello world!'),
(2, 2, 'Tech is awesome!');


SELECT uname, upasshash FROM users;