-- +goose Up
-- +goose StatementBegin
SELECT 'up SQL query';
-- +goose StatementEnd

-- 1. Создание ENUM-типов
CREATE TYPE booking_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED');
CREATE TYPE payment_status AS ENUM ('PENDING', 'SUCCESSFUL', 'FAILED');

-- 2. Таблица ролей
CREATE TABLE roles (
    id            SERIAL PRIMARY KEY,
    role_name     VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Таблица пользователей
CREATE TABLE users (
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(100) NOT NULL UNIQUE,
    full_name      VARCHAR(200) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    birth_date     DATE NOT NULL,
    phone          VARCHAR(20),
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    role_id        INTEGER NOT NULL REFERENCES roles(id) ON DELETE RESTRICT
);

-- 4. Таблица квест-комнат
CREATE TABLE escape_rooms (
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(200) NOT NULL,
    description       TEXT,
    difficulty_level  VARCHAR(50),
    max_participants  INTEGER NOT NULL CHECK (max_participants > 0),
    price             NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

-- 5. Таблица тайм-слотов
CREATE TABLE time_slots (
    id               SERIAL PRIMARY KEY,
    escape_room_id   INTEGER NOT NULL REFERENCES escape_rooms(id) ON DELETE CASCADE,
    start_time       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_time         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_available     BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_timeslot CHECK (end_time > start_time)
);

-- 6. Таблица бронирований
CREATE TABLE bookings (
    booking_id   SERIAL PRIMARY KEY,
    user_id      INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    time_slot_id INTEGER NOT NULL REFERENCES time_slots(id) ON DELETE CASCADE,
    booking_time TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    status       booking_status NOT NULL DEFAULT 'PENDING'
);

-- 7. Таблица оплат
CREATE TABLE payments (
    payment_id     SERIAL PRIMARY KEY,
    booking_id     INTEGER NOT NULL REFERENCES bookings(booking_id) ON DELETE CASCADE,
    amount         NUMERIC(10,2) NOT NULL CHECK (amount >= 0),
    payment_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    payment_method VARCHAR(100),
    status         payment_status NOT NULL DEFAULT 'PENDING'
);

-- 8. Таблица логов
CREATE TABLE logs (
    log_id     SERIAL PRIMARY KEY,
    user_id    INTEGER REFERENCES users(id) ON DELETE SET NULL,
    action     VARCHAR(200) NOT NULL,
    timestamp  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    details    TEXT
);

-- 9. Таблица бэкапов
CREATE TABLE backups (
    file_name  VARCHAR(255) PRIMARY KEY,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE
);
-- +goose Down
-- +goose StatementBegin
SELECT 'down SQL query';
-- +goose StatementEnd
