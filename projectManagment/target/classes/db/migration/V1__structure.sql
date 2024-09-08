CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(255)
);

CREATE TABLE team (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    project_id BIGINT,
    manager_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE app_user (
                          id SERIAL PRIMARY KEY,
                          username VARCHAR(64) UNIQUE NOT NULL,
                          password VARCHAR(64) NOT NULL,
                          email VARCHAR(64) UNIQUE NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          role VARCHAR(64) NOT NULL,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          team_id BIGINT, -- Полето за връзка с екипа
                          CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team(id) -- Външен ключ към таблицата team
);

CREATE TABLE sprint (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(64) UNIQUE NOT NULL,
                        duration_in_weeks INT NOT NULL,
                        sprint_state VARCHAR(20) NOT NULL,
                        start_date TIMESTAMP
);

CREATE TABLE task (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(64) UNIQUE NOT NULL,
                      description TEXT NOT NULL,
                      comment TEXT,
                      task_type VARCHAR(20) NOT NULL,
                      task_state VARCHAR(20) NOT NULL,
                      user_id BIGINT,
                      FOREIGN KEY (user_id) REFERENCES app_user(id),
                      sprint_id BIGINT,
                      FOREIGN KEY (sprint_id) REFERENCES sprint(id)
);

CREATE TABLE user_team (
    user_team_id SERIAL PRIMARY KEY,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    team_id BIGINT,
    FOREIGN KEY (team_id) REFERENCES team(id),
    role VARCHAR(50)
);
