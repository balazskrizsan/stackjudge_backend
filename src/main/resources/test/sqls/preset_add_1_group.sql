-- requires: preset_add_1_company
-- requires: preset_add_1_address
INSERT INTO "group" (id, company_id, parent_id, address_id, type_id, name, members_on_group_id, created_at, created_by)
VALUES (101001, 100001, null, 102001, 2, 'QQQ', 1, '2020-01-01 01:01:01', 1);
