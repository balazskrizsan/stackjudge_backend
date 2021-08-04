TRUNCATE TABLE
    "address",
    "group",
    "review",
    "protected_review_log",
    "company" RESTART IDENTITY;

INSERT INTO company(id, name, company_size_id, it_size_id, logo_path, created_at, created_by)
VALUES (default, 'Google', 1, 5, 'company-logos/1.png', '2021-01-01 00:00:00', 1),
       (default, 'Samsung Electronics', 3, 4, 'company-logos/2.png', '2021-01-02 00:00:00', 2),
       (default, 'Samsung Electronics with a very very very very very long name', 4, 3, 'company-logos/3.png', '2021-01-03 00:00:00', 3),
       (default, 'Foxconn', 5, 2, 'company-logos/4.png', '2021-01-04 00:00:00', 4),
       (default, 'Apple', 4, 1, 'company-logos/5.png', '2021-01-05 00:00:00', 5),
       (default, 'Microsoft', 3, 2, 'company-logos/6.png', '2021-01-06 00:00:00', 6),
       (default, 'Huawei', 2, 3, 'company-logos/7.png', '2021-01-07 00:00:00', 7),
       (default, 'Dell Technologies', 1, 4, 'company-logos/8.png', '2021-01-08 00:00:00', 8),
       (default, 'Hitachi', 2, 5, 'company-logos/9.png', '2021-01-09 00:00:00', 9),
       (default, 'IBM', 3, 4, 'company-logos/10.png', '2021-01-10 00:00:00', 1),
       (default, 'Sony', 4, 3, 'company-logos/11.png', '2021-01-11 00:00:00', 11),
       (default, 'Intel', 5, 2, 'company-logos/12.png', '2021-01-12 00:00:00', 12),
       (default, 'Facebook', 4, 1, 'company-logos/13.png', '2021-01-13 00:00:00', 13),
       (default, 'Panasonic', 3, 2, 'company-logos/14.png', '2021-01-14 00:00:00', 14),
       (default, 'HP Inc.', 2, 3, 'company-logos/15.png', '2021-01-15 00:00:00', 15),
       (default, 'Tencent', 1, 4, 'company-logos/16.png', '2021-01-16 00:00:00', 16),
       (default, 'LG Electronics', 2, 5, 'company-logos/17.png', '2021-01-17 00:00:00', 17),
       (default, 'Cisco', 3, 4, 'company-logos/18.png', '2021-01-18 00:00:00', 18),
       (default, 'Lenovo', 4, 3, 'company-logos/19.png', '2021-01-19 00:00:00', 19),
       (default, 'JD.com', 5, 2, 'company-logos/20.png', '2021-01-20 00:00:00', 20);

INSERT INTO address(id, company_id, full_address, marker_lat, marker_lng, manual_marker_lat, manual_marker_lng,
                    created_at, created_by)
-- company: 1
VALUES (default, 1, '1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA', 37.4220452, -122.0846619, NULL, NULL, '2020-11-01 11:22:33', 1),
       (default, 1, '6 Pancras Square, London N1C 4AG, UK', 51.5332642,-0.1281919, NULL, NULL, '2020-11-02 11:22:33', 2),
       (default, 1, 'Tucholskystraße 2, 10117 Berlin, Germany', 52.5232521, 13.3901892, NULL, NULL, '2020-11-03 11:22:33', 3),
-- company: 2
       (default, 2, '129 Samseong-ro, Taejang-dong, Yeongtong-gu, Suwon-si, Gyeonggi-do, South Korea', 37.2570082, 127.0501493, NULL, NULL, '2020-11-01 11:22:33', 1),
-- company: 3
       (default, 3, '6 Pancras Square, London N1C 4AG, UK', 51.5332642,-0.1281919, NULL, NULL, '2020-11-02 11:22:33', 2),
       (default, 3, 'Tucholskystraße 2, 10117 Berlin, Germany', 52.5232521, 13.3901892, NULL, NULL, '2020-11-03 11:22:33', 3);

INSERT INTO "group" (id, company_id, address_id, parent_id, type_id, name, members_on_group_id, created_at, created_by)
-- company: 1
VALUES (default, 1, 1, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 1, 1, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 1, 2, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 2, 3, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 2, null, 1, 'Level 0 - Company', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 2, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 3, 6, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 3, 5, 2, 'Level 1 - Team', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 4, 8, 3, 'Level 2 - Stack', 1, '2020-01-01 01:01:01', 1),
       (default, 1, 4, 9, 4, 'Level 3 - Technology', 1, '2020-01-01 01:01:01', 1);

-- requires: preset_add_10_groups.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
-- group_id: 1
VALUES (default, 1, 1, 1, 'Review 1, Group 1, Review text test, short 111', '2021-01-16 01:50:01', 1),
       (default, 1, 2, 2, 'Review 2, Group 1, Review text test, short 222', '2021-01-16 01:50:01', 2),
-- group_id: 2
       (default, 2, 3, 3, 'Review 3, Group 2, Review text test, short 333', '2021-01-16 01:50:01', 1),
       (default, 2, 1, 4, 'Review 4, Group 2, Review text test, short 444', '2021-01-16 01:50:01', 2),
-- group_id: 3
       (default, 3, 2, 5, 'Review 5, Group 3, Review text test, long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long 555', '2021-01-16 01:50:01', 1),
       (default, 3, 3, 6, 'Review 6, Group 3, Review text test, short 666', '2021-01-16 01:50:01', 2),
-- group_id: 4
       (default, 4, 1, 7, 'Review 7, Group 4, Review text test, short 777', '2021-01-16 01:50:01', 1),
       (default, 4, 2, 8, 'Review 8, Group 4, Review text test, 888 \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line \n multi line  multi line  multi line 888', '2021-01-16 01:50:01', 2);

INSERT INTO stackjudge.public."users" (id, is_email_user, is_facebook_user, profile_picture_url, username, password, facebook_access_token, facebook_id)
VALUES (default, false, true, 'http://logo.com/image.jpg', 'Default User 1', 'QWEqwe123123', 'qwe123', '1'),
       (default, false, true, 'http://logo.com/image.jpg', 'Default User 2', 'asdASD123123', 'asd123', '2');
