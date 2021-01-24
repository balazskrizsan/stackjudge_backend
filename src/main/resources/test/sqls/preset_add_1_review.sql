-- requires: preset_add_1_review.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
VALUES (102001, 101001, 1, 2, 'long review text', '2021-01-16 01:50:01', 123);
