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
  password
) VALUES (
  'f47ac10b-58cc-4372-a567-0e02b2c3d479',
  NOW(),
  'guest@email.com',
  'Guest',
  'User',
  NOW(),
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

-- create tags
INSERT INTO tag (id, name, created_at, updated_at)
VALUES
  ('41b849a7-6555-4b41-93b1-bd55838f2acf', 'personal', NOW(), NOW()),
  ('ca7efbf0-4c57-454e-ad25-76409330b3b0', 'work', NOW(), NOW()),
  ('607456e4-8ce1-46c8-a2fc-c5e3981e6c60', 'education', NOW(), NOW()),
  ('eb809258-1e0a-4bd4-82d0-290d15871a24', 'family', NOW(), NOW()),
  ('9ba2bcc7-9a4d-4c24-b9ec-5b76aa70f1a2', 'training', NOW(), NOW()),
  ('18a1ef7a-584e-4920-b115-0aaf41077372', 'finance',  NOW(), NOW());

-- create test institution
INSERT INTO institution (
  id,
  name,
  street,
  building_number,
  postbox,
  city,
  postal_code,
  country_code,
  updated_at,
  created_at
) VALUES (
  '89f85bdb-e82e-4d8e-bda9-2f1af1d7bc23',
  'Test Institution',
  'Turnhoutsebaan',
  '100',
  '1',
  'Antwerp',
  '2000',
  'BE',
  NOW(),
  NOW()
);