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

-- create test institution
INSERT INTO institution (
  id,
  created_at,
  updated_at,
  name,
  street,
  building_number,
  city,
  country_code,
  postbox,
  postal_code
) VALUES (
  'ae7d91f0-8abc-440f-b1f8-a2cf61f9ba26',
  NOW(),
  NOW(),
  'TestInstitution',
  'Turnhoutsebaan',
  '100',
  'Antwerpen',
  'BE',
  '1',
  '2000'
);

-- create tags
INSERT INTO tag (
  id,
  created_at,
  name,
  updated_at
) VALUES
(
  'e3169e0d-ccf3-4acf-b32a-3e96a08dacd4',
  NOW(),
  'personal',
  NOW()
),
(
  '18e0ead5-01ac-4c7d-9805-05e3efcdda37',
  NOW(),
  'work',
  NOW()
),
(
  '57d3ae4a-6801-4df9-90bb-9dc251572555',
  NOW(),
  'finance',
  NOW()
),
(
  '67c831e6-ad13-4d33-9187-2346e172af49',
  NOW(),
  'training',
  NOW()
),
 (
   'da372978-3271-4152-9bc3-22975148e9b6',
   NOW(),
   'family',
   NOW()
 ),
(
  '7e8ff6b8-ab39-4014-a4f5-4a3e66088b79',
  NOW(),
  'education',
  NOW()
);