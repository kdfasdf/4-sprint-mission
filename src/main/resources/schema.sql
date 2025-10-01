CREATE TABLE binary_contents (
     id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     file_name VARCHAR(255) NOT NULL,
     size BIGINT NOT NULL,
     content_type VARCHAR(100) NOT NULL,
     bytes BYTEA NOT NULL
);

CREATE TABLE channels (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    name VARCHAR(100),
    description VARCHAR(500),
    type VARCHAR(10) NOT NULL CHECK (type IN ('PUBLIC', 'PRIVATE'))
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(20) NOT NULL,
    profile_id UUID REFERENCES binary_contents(id) ON DELETE SET NULL
);

CREATE TABLE user_statuses (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    last_active_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE read_statuses (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
    last_read_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(user_id, channel_id)
);

CREATE TABLE messages (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    content TEXT,
    channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
    author_id UUID REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE message_attachments (
    message_id UUID REFERENCES messages(id) ON DELETE CASCADE,
    attachment_id UUID REFERENCES binary_contents(id) ON DELETE CASCADE,
    PRIMARY KEY (message_id, attachment_id)
);
