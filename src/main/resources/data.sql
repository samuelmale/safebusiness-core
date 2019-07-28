-- Create Admin User
-- INSERT IGNORE INTO user (id, username, password) VALUES (1, 'admin', 'Admin123');
-- H2 DB doesn't support `INSERT IGNORE`

-- HACKY WORK AROUND
REPLACE INTO user (id, username, password) VALUES (1, 'admin', 'Admin123');