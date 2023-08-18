insert into member (id, email, join_dtm, member_role, modify_dtm, nickname, password, picture) values (1L, 'inw2129@gmail.com', '20230817000000', 'USER', '20230817000000', '닥터페퍼', null, 'https://lh3.googleusercontent.com/a/AAcHTtf0CXww5ofev27IZJs17x9wbgXvEUOUy5K5uiucLwhlIyc=s96-c');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title) values (33L, 30, 'desc', 2, '사진 인증', 'tid3tle');
insert into challenge_card (id, assign_score, description, difficulty, final_goal, title) values (34L, 30, 'desc', 2, '사진 인증', 'titleb');
insert into challenge_card (id, assign_score, description, difficulty, final_goal, title) values (35L, 30, 'desc', 2, '사진 인증', 'titlse');

insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (1L, '15920101000000', 'key', 33L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (2L, '15920101000000', 'key', 34L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (3L, '15920101000000', 'key', 35L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (1L, 33L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (2L, 33L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (3L, 33L, '중간 목표3');

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (4L, 34L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (5L, 34L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (6L, 34L, '중간 목표3');

insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (7L, 35L, '중간 목표1');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (8L, 35L, '중간 목표2');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (9L, 35L, '중간 목표3');
