-- password aaa
INSERT INTO users (id, username, email, password, confirmed, created) VALUES (1, 'Username1', 'user1@site.com', '$2a$10$oqEgKzT5PiR4xHez3AK74ebs5pRiZ.g67rERMY9zY1UKb8h/h4uNu', 1, now());
-- password bbb
INSERT INTO users (id, username, email, password, created) VALUES (2, 'Username2', 'user2@site.com', '$2a$10$n51atdv0cfU4y1vrA6Uybesbt1VksgHPS5pg.NUBrOQogkNmSJjhi', now());

INSERT INTO users_authorities (id, user_id, authority_id, created) VALUES (1, 1, 1, now());
INSERT INTO users_authorities (id, user_id, authority_id, created) VALUES (2, 2, 1, now());

INSERT INTO user_tokens (token, user_id, type_id, expiration_time, created) VALUES ('AZERTYUIOPQSDFGHJKLMWXCVBN123456', 1, 1, '2032-01-01 12:12:12', now());

INSERT INTO contacts (id, user_id, first_name, last_name, created) VALUES (1, 1, 'John', 'Smith', now());
INSERT INTO contacts (id, user_id, first_name, last_name, created) VALUES (2, 1, 'Annie', 'Bernard', now());
INSERT INTO contacts (id, user_id, first_name, last_name, created) VALUES (3, 2, 'Jean', 'Durand', now());

INSERT INTO phone_numbers (id, contact_id, type_id, number, created) VALUES (1, 1, 1, '212 123 4567', now());
INSERT INTO phone_numbers (id, contact_id, type_id, number, created) VALUES (2, 1, 2, '917 123 4567', now());
INSERT INTO phone_numbers (id, contact_id, type_id, number, created) VALUES (3, 2, 2, '818 123 4567', now());
INSERT INTO phone_numbers (id, contact_id, type_id, number, created) VALUES (4, 3, 1, '212 987 1234', now());