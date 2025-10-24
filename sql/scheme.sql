
CREATE OR
REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    username                VARCHAR(50)  NOT NULL UNIQUE,
    email                   VARCHAR(100) NOT NULL UNIQUE,
    password                VARCHAR(255) NOT NULL, -- 存储 BCrypt/SCrypt 哈希后的密码
    nickname                VARCHAR(50),
    avatar_url              VARCHAR(255),

    -- Spring Security UserDetails 字段
    enabled                 BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,

    created_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE TRIGGER set_users_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();



CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- 例如 'ROLE_USER', 'ROLE_ADMIN'
);


INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');



CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INT    NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);



CREATE TABLE todo_lists
(
    id BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX idx_todo_lists_user_id ON todo_lists (user_id);

CREATE TRIGGER set_todo_lists_timestamp
    BEFORE UPDATE
    ON todo_lists
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();



CREATE TABLE todo_items
(
    id BIGSERIAL PRIMARY KEY,
    list_id      BIGINT       NOT NULL REFERENCES todo_lists (id) ON DELETE CASCADE,

    title        VARCHAR(255) NOT NULL,
    content      TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    due_date     TIMESTAMP WITH TIME ZONE, -- 截止日期
    completed_at TIMESTAMP WITH TIME ZONE, -- 完成日期

    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX idx_todo_items_list_id ON todo_items (list_id);
CREATE INDEX idx_todo_items_status ON todo_items (status);
CREATE INDEX idx_todo_items_due_date ON todo_items (due_date);


CREATE TRIGGER set_todo_items_timestamp
    BEFORE UPDATE
    ON todo_items
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();



CREATE TABLE sub_tasks
(
    id BIGSERIAL PRIMARY KEY,
    item_id    BIGINT       NOT NULL REFERENCES todo_items (id) ON DELETE CASCADE,
    title      VARCHAR(255) NOT NULL,
    completed  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX idx_sub_tasks_item_id ON sub_tasks (item_id);


CREATE TRIGGER set_sub_tasks_timestamp
    BEFORE UPDATE
    ON sub_tasks
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();



CREATE TABLE tags
(
    id BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       VARCHAR(50) NOT NULL,
    color      VARCHAR(7) DEFAULT '#FFFFFF', -- 标签颜色, e.g., '#FF0000'

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- 确保同一个用户不能有同名标签
    UNIQUE (user_id, name)
);


CREATE INDEX idx_tags_user_id ON tags (user_id);



CREATE TABLE todo_item_tags
(
    item_id BIGINT NOT NULL REFERENCES todo_items (id) ON DELETE CASCADE,
    tag_id  BIGINT NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, tag_id)
);