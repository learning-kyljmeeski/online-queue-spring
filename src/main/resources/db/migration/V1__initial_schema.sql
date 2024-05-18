-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255)
);

-- Create queues table
CREATE TABLE queues (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT DEFAULT 10 NOT NULL,
    user_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create people table
CREATE TABLE people (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    queue_id BIGINT,
    CONSTRAINT fk_queue FOREIGN KEY (queue_id) REFERENCES queues(id)
);
