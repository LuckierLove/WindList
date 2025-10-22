-- 1. 创建自定义 ENUM 类型 (提高数据一致性)

-- 任务状态: 待处理, 进行中, 已完成, 已归档
CREATE
TYPE task_status AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'ARCHIVED');

-- 任务优先级: 低, 中, 高
CREATE
TYPE task_priority AS ENUM ('LOW', 'MEDIUM', 'HIGH');


-- 2. 创建一个自动更新 'updated_at' 字段的触发器函数
-- 这是一个很好的实践，MyBatisPlus 的自动填充功能也可以实现，但在数据库层面保证更可靠
CREATE OR
REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- 3. 创建核心表结构

-- 表 1: users (用户表)
-- 用于 Spring Security UserDetails
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

-- 为 users 表添加 updated_at 触发器
CREATE TRIGGER set_users_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();


-- 表 2: roles (角色表)
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- 例如 'ROLE_USER', 'ROLE_ADMIN'
);

-- 插入基础角色
INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');


-- 表 3: user_roles (用户-角色关联表)
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INT    NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


-- 表 4: todo_lists (待办事项清单)
CREATE TABLE todo_lists
(
    id BIGSERIAL PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 添加索引，用户查询自己的列表时会非常快
CREATE INDEX idx_todo_lists_user_id ON todo_lists (user_id);

-- 为 todo_lists 表添加 updated_at 触发器
CREATE TRIGGER set_todo_lists_timestamp
    BEFORE UPDATE
    ON todo_lists
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();


-- 表 5: todo_items (待办事项)
CREATE TABLE todo_items
(
    id BIGSERIAL PRIMARY KEY,
    list_id      BIGINT       NOT NULL REFERENCES todo_lists (id) ON DELETE CASCADE,

    title        VARCHAR(255) NOT NULL,
    content      TEXT,
    status task_status NOT NULL DEFAULT 'PENDING',
    priority task_priority NOT NULL DEFAULT 'MEDIUM',
    due_date     TIMESTAMP WITH TIME ZONE, -- 截止日期
    completed_at TIMESTAMP WITH TIME ZONE, -- 完成日期

    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 添加索引
CREATE INDEX idx_todo_items_list_id ON todo_items (list_id);
CREATE INDEX idx_todo_items_status ON todo_items (status);
CREATE INDEX idx_todo_items_due_date ON todo_items (due_date);

-- 为 todo_items 表添加 updated_at 触发器
CREATE TRIGGER set_todo_items_timestamp
    BEFORE UPDATE
    ON todo_items
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();


-- 表 6: sub_tasks (子任务)
CREATE TABLE sub_tasks
(
    id BIGSERIAL PRIMARY KEY,
    item_id    BIGINT       NOT NULL REFERENCES todo_items (id) ON DELETE CASCADE,
    title      VARCHAR(255) NOT NULL,
    completed  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 添加索引
CREATE INDEX idx_sub_tasks_item_id ON sub_tasks (item_id);

-- 为 sub_tasks 表添加 updated_at 触发器
CREATE TRIGGER set_sub_tasks_timestamp
    BEFORE UPDATE
    ON sub_tasks
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();


-- 表 7: tags (标签)
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

-- 添加索引
CREATE INDEX idx_tags_user_id ON tags (user_id);


-- 表 8: todo_item_tags (事项-标签 关联表)
CREATE TABLE todo_item_tags
(
    item_id BIGINT NOT NULL REFERENCES todo_items (id) ON DELETE CASCADE,
    tag_id  BIGINT NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
    PRIMARY KEY (item_id, tag_id)
);