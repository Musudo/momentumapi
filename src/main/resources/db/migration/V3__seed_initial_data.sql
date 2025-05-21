--------------------
-- Seed initial data
--------------------

-- create test user
INSERT INTO app_user (
  id,
  created_at,
  email,
  first_name,
  last_name,
  updated_at,
  roles,
  password
) VALUES (
  'f47ac10b-58cc-4372-a567-0e02b2c3d479',
  NOW(),
  'guest@email.com',
  'Guest',
  'User',
  NOW(),
  ARRAY['ROLE_USER'],
  '$2y$10$0gq2Xcb80yYJyFzwyAuuM.o4dhBbVYQ4qYfFH7U5LSKeQHzjDhAEK'
);

-- create role for test user
INSERT INTO user_role (
  user_id,
  role
) VALUES (
  'f47ac10b-58cc-4372-a567-0e02b2c3d479',
  'ROLE_USER'
);