ALTER TABLE users ADD CONSTRAINT users_fk0 FOREIGN KEY ("role_id") REFERENCES roles("id");
