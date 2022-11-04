-- requires: preset_add_1_company.sql
-- requires: preset_add_1_user.sql
INSERT INTO company_owner (company_id, user_ids_user_id, created_at)
VALUES (100001, '00000000-0000-0000-0000-000000105001', '2021-01-17 02:30:00');
