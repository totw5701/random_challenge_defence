-- default member
insert into member (id, email, join_dtm, member_role, modify_dtm, nickname, password, picture, experience) values (1L, 'inw2129@gmail.com', '20230817000000', 'USER', '20230817000000', '닥터페퍼', null, 'https://lh3.googleusercontent.com/a/AAcHTtf0CXww5ofev27IZJs17x9wbgXvEUOUy5K5uiucLwhlIyc=s96-c', 1);


-- challengeCardCategory
insert into challenge_card_category (id, title, description) values (1L, '괴짜', '엉뚱한 챌린지에 도전');
insert into challenge_card_category (id, title, description) values (2L, '운동', '운동 챌린지에 도전');
insert into challenge_card_category (id, title, description) values (3L, '마음 챙김', '마음 챙김에 도전');
insert into challenge_card_category (id, title, description) values (4L, '문화 생활', '문화 생활 즐기기에 도전');
insert into challenge_card_category (id, title, description) values (5L, '사람 챙김', '주변 사람 챙기기에 도전');
insert into challenge_card_category (id, title, description) values (6L, '여행', '여행에 도전');
insert into challenge_card_category (id, title, description) values (7L, '자기 개발', '자기개발에 도전');


-- challengeCard
insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (1L, 100, '오늘 하루 엉뚱한 괴짜가 되어봅시다. 양말의 앞 뒤 혹운 좌우를 바꾸어 신고 출근하세요.', 1, '거꾸로 신은 양말 인증하기', '양말 거꾸로 신고 출근하기', 1, 1L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (1L, '15920101000000', 'key', 1L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (1L, 1L, '양말 고르기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (2L, 1L, '양말 거꾸로 신기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (3L, 1L, '출근하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (2L, 40, '지구를 박차고 앞으로 나아가봅시다. 밖으로 나가 힘차게 달려보세요.', 4, '달리기 인증 사진 찍기', '달리기 하고오기', 6, 2L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (2L, '15920101000000', 'key', 2L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (4L, 2L, '운동복 입기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (5L, 2L, '달리기 할 수 있는 장소로 이동하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (6L, 2L, '숨이 차도록 5분 이상 달리기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (3L, 90, '중력을 거슬러볼까요? 걱정 근심 모두 종이비행기에 담아 저 멀리 날려버리세요.', 1, '종이비행기 사진 인증하기', '종이비행기 접어 날리기', 1, 3L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (3L, '15920101000000', 'key', 3L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (7L, 3L, '종이비행기 접기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (8L, 3L, '종이비행기 날리기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (9L, 3L, '쓰레기 정리하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (4L, 90, '시인이 되어볼까요? 내 진심을 담아 오글거리는 시를 작성해보세요.', 2, '종이에 적은 시 인증하기', '짧은 시 쓰기', 1, 3L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (4L, '15920101000000', 'key', 4L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (10L, 4L, '종이와 펜 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (11L, 4L, '시 주제 정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (12L, 4L, '시 내용 구상하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (13L, 4L, '시 쓰기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (5L, 60, '한국인은 밥심! 소중한 사람에게 즐거운 식사를 대접해봅시다.', 5, '주변 사람에게 식사를 대접하세요', '식사 대접하기', 5, 5L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (5L, '15920101000000', 'key', 5L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (14L, 5L, '소중한 사람을 한명 떠올리기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (15L, 5L, '대접할 음식 정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (16L, 5L, '직접 만든 음식 혹은 식당에서 식사 대접하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (6L, 80, '할리우드 배우가 되어볼까요? 로멘틱한 영어 문구를 암송해보세요.', 2, '간드러지는 멋진 로멘틱 영어 문구를 외워보세요.', '로멘틱한 영어 문구 외우기', 2, 3L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (6L, '15920101000000', 'key', 6L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (17L, 6L, '로멘틱한 영어 문장 찾기, 혹은 한국어 문장을 번역하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (18L, 6L, '영어 문장 암기하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (19L, 6L, '앞에 사랑하는 연인이 있다고 상상하며 멋지게 암송하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (7L, 80, '깔깔 유머집? 깔깔 유머집보단 재미있는 개그를 외워봅시다.', 2, '언젠가 써먹을 수 있는 개그를 외워보세요.', '재미있는 유머 외워두기', 2, 4L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (7L, '15920101000000', 'key', 7L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (20L, 7L, '육성으로 터진 웃긴 이야기 찾아보기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (21L, 7L, '일단 한바탕 웃기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (22L, 7L, '개그 암기하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (8L, 90, '인사? 어렵지 않아요. 버스 기사님께 반갑게 먼저 인사해봅시다.', 2, '버스 기사님께 먼저 인사해보세요.', '버스 기사님께 먼저 인사하기', 2, 3L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (8L, '15920101000000', 'key', 8L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (23L, 8L, '버스 탈 일 만들기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (24L, 8L, '버스를 타고 먼저 인사하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (25L, 8L, '인사를 받아주지 않더라도 뿌듯해 하기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (9L, 100, '나라가 허락한 마약. 크게 한바탕 웃어봅시다.', 1, '큰소리로 한바탕 웃어보세요.', '크게 웃기', 1, 3L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (9L, '15920101000000', 'key', 9L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (26L, 9L, '아무 이유 없이 한바탕 웃기');

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id) values (10L, 50, '한국이 생각보다 예뻐요. 내가 살고있는 지역으로 여행을 다녀옵시다.', 8, '지역 여행지 방문 인증하기', '근처 여행 다녀오기', 15, 6L);
insert into File (id, create_dtm, key, challenge_card_id, url, member_id) values (10L, '15920101000000', 'key', 10L, 'https://i.namu.wiki/i/5BMg9O67g2ocAweObDeXri9iimKFDbUvFzppS79WAsFhHXrKAeJ7J0nr5lnHGukryqK6YPHPnTytJvRvo4N8LEqhBc6wWTZF8uUueRT2-WaOtTlIRmij0dHUWscWx07kQdJrFUF22qN2qw932vvhmw.webp', 1L);
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (27L, 10L, '대중교통 혹은 자동차로 1시간 내로 갈 수 있는 지역 선정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (28L, 10L, '지역내 유명한 관광지 찾아보기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (29L, 10L, '배낭을 메고 여행지 방문하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (30L, 10L, '여행지 관람하기');








