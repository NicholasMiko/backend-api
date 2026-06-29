CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    status VARCHAR(50),
    priority VARCHAR(50),
    due_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id BIGINT,

    CONSTRAINT fk_task_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);