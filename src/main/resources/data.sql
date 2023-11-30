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

-- memberPersonality
insert into member_personality (title, description) values
('술러버', '술을 좋아하는 사람들.'),
('익스트림러버', '익스트림 활동과 스릴을 추구하는 사람들.'),
('괴짜', '평범한 것에서 벗어난 독특한 활동을 선호하는 사람들.'),
('운동매니아', '운동과 건강한 생활을 추구하는 사람들.'),
('야외활동가', '밖에서 하는 활동을 좋아하는 사람들.'),
('집돌이', '집에서의 여유를 즐기는 사람들.'),
('감성이묻은', '감성적인/낭만적인 경험을 좋아하는 사람들.'),
('일상탈출', '일상에서의 변화와 새로움을 원하는 사람들.'),
('직장인', '일과 직장생활에 관련된 챌린지를 원하는 사람들.'),
('뷰티앤스타일러', '외모와 스타일에 관심이 많은 사람들.'),
('사교성풍부', '사람들과의 교류와 대화를 좋아하는 사람들.'),
('예술가', '예술과 문화에 대한 관심이 높은 사람들.'),
('음악가', '음악을 사랑하고 다양한 장르에 관심이 있는 사람들.'),
('여행가', '여행과 새로운 경험을 좋아하는 사람들.'),
('독서가', '책과 문학을 좋아하는 사람들.'),
('춤꾼', '무용과 춤에 관심이 많은 사람들.'),
('요리사', '요리와 음식에 관심이 많은 사람들.'),
('게이머', '게임과 가상 세계에 푹 빠진 사람들.'),
('자연보호자', '환경 보호와 지속 가능한 생활을 추구하는 사람들.'),
('손재주꾼', '직접 무언가를 만들어보는 것을 좋아하는 사람들.'),
('스릴러버', '스릴 넘치는 활동과 경험을 추구하는 사람들.'),
('공부좋아요', '지식을 습득하는 경험을 좋아하는 사람들.'),
('동물좋아요', '애완동물과 그들과의 시간을 좋아하는 사람들.');

-- challengeCard
insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (1L, 100, '오늘 하루 엉뚱한 괴짜가 되어봅시다. 양말의 앞 뒤 혹운 좌우를 바꾸어 신고 출근하세요.', 1, '거꾸로 신은 양말 인증하기', '양말 거꾸로 신고 출근하기', 1, 1L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (1L, 1L, '양말 고르기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (2L, 1L, '양말 거꾸로 신기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (3L, 1L, '출근하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (1L, 1L, 3L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (2L, 1L, 7L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (3L, 1L, 8L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (4L, 1L, 9L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (2L, 40, '지구를 박차고 앞으로 나아가봅시다. 밖으로 나가 힘차게 달려보세요.', 4, '달리기 인증 사진 찍기', '달리기 하고오기', 6, 2L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (4L, 2L, '운동복 입기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (5L, 2L, '달리기 할 수 있는 장소로 이동하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (6L, 2L, '숨이 차도록 5분 이상 달리기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (5L, 2L, 5L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (6L, 2L, 4L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (3L, 90, '중력을 거슬러볼까요? 걱정 근심 모두 종이비행기에 담아 저 멀리 날려버리세요.', 1, '종이비행기 사진 인증하기', '종이비행기 접어 날리기', 1, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (7L, 3L, '종이비행기 접기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (8L, 3L, '종이비행기 날리기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (9L, 3L, '쓰레기 정리하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (7L, 3L, 6L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (8L, 3L, 7L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (9L, 3L, 8L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (10L, 3L, 12L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (11L, 3L, 20L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (4L, 90, '시인이 되어볼까요? 내 진심을 담아 오글거리는 시를 작성해보세요.', 2, '종이에 적은 시 인증하기', '짧은 시 쓰기', 1, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (10L, 4L, '종이와 펜 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (11L, 4L, '시 주제 정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (12L, 4L, '시 내용 구상하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (13L, 4L, '시 쓰기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (12L, 4L, 6L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (13L, 4L, 7L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (14L, 4L, 8L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (15L, 4L, 12L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (16L, 4L, 15L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (17L, 4L, 22L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (5L, 60, '한국인은 밥심! 소중한 사람에게 즐거운 식사를 대접해봅시다.', 5, '대접한 메뉴 인증하기', '식사 대접하기', 5, 5L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (14L, 5L, '소중한 사람을 한명 떠올리기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (15L, 5L, '대접할 음식 정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (16L, 5L, '직접 만든 음식 혹은 식당에서 식사 대접하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (18L, 5L, 11L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (19L, 5L, 17L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (20L, 5L, 20L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (6L, 80, '할리우드 배우가 되어볼까요? 로멘틱한 영어 문구를 암송해보세요.', 2, '간드러지는 멋진 로멘틱 영어 문구를 외워보세요.', '로멘틱한 영어 문구 외우기', 2, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (17L, 6L, '로멘틱한 영어 문장 찾기, 혹은 한국어 문장을 번역하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (18L, 6L, '영어 문장 암기하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (19L, 6L, '앞에 사랑하는 연인이 있다고 상상하며 멋지게 암송하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (21L, 6L, 6L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (22L, 6L, 7L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (23L, 6L, 11L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (24L, 6L, 15L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (25L, 6L, 22L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (7L, 80, '깔깔 유머집? 깔깔 유머집보단 재미있는 개그를 외워봅시다.', 2, '언젠가 써먹을 수 있는 개그를 외워보세요.', '재미있는 유머 외워두기', 2, 4L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (20L, 7L, '육성으로 터진 웃긴 이야기 찾아보기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (21L, 7L, '일단 한바탕 웃기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (22L, 7L, '개그 암기하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (26L, 7L, 3L);
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (27L, 7L, 6L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (8L, 90, '인사? 어렵지 않아요. 버스 기사님께 반갑게 먼저 인사해봅시다.', 2, '버스 기사님께 먼저 인사하기.', '버스 기사님께 먼저 인사하기', 2, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (23L, 8L, '버스 탈 일 만들기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (24L, 8L, '버스를 타고 먼저 인사하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (25L, 8L, '인사를 받아주지 않더라도 뿌듯해 하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (28L, 8L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (9L, 100, '나라가 허락한 마약. 크게 한바탕 웃어봅시다.', 1, '큰소리로 한바탕 웃어보세요.', '크게 웃기', 1, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (26L, 9L, '아무 이유 없이 한바탕 웃기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (29L, 9L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (10L, 50, '한국이 생각보다 예뻐요. 내가 살고있는 지역으로 여행을 다녀옵시다.', 8, '지역 여행지 방문 인증하기', '근처 여행 다녀오기', 15, 6L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (27L, 10L, '대중교통 혹은 자동차로 1시간 내로 갈 수 있는 지역 선정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (28L, 10L, '지역내 유명한 관광지 찾아보기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (29L, 10L, '배낭을 메고 여행지 방문하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (30L, 10L, '여행지 관람하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (30L, 10L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (11L, 50, '아름다운 음악을 들으며 마음을 진정시켜 봅시다.', 2, '좋아하는 노래 듣고 감정 기록하기', '음악 감상', 1, 4L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (31L, 11L, '편안한 장소 찾기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (32L, 11L, '좋아하는 노래 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (33L, 11L, '노래 듣고 감정 기록하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (31L, 11L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (12L, 70, '당신의 철학은 무엇인가요? 하루 10분 독서로 마음의 양식을 채워봅시다.', 3, '책에서 인상 깊은 구절 찾기', '독서의 시간', 3, 4L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (34L, 12L, '읽고 싶은 책 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (35L, 12L, '10분 독서하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (36L, 12L, '인상 깊은 구절 기록하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (32L, 12L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (13L, 50, '자연을 느껴봅시다. 근처 공원이나 숲으로 걷는 여행을 떠나보세요.', 4, '공원이나 숲에서 찍은 사진 인증하기', '자연 속 걷기', 3, 6L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (37L, 13L, '가고 싶은 공원이나 숲 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (38L, 13L, '날씨 체크하고 준비물 챙기기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (39L, 13L, '자연 속에서 마음 편히 걷기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (33L, 13L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (14L, 50, '음악의 힘을 느껴보세요. 좋아하는 노래를 크게 틀고 몸을 흔들어봅시다.', 1, '음악 듣는 모습을 인증하기', '음악과 함께 댄스 타임', 2, 3L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (40L, 14L, '가장 기분을 좋게 해주는 노래 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (41L, 14L, '볼륨을 최대로 올리기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (42L, 14L, '자유롭게 몸을 흔들면서 음악 느끼기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (34L, 14L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (15L, 60, '백종원이 되어봅시다. 간단한 요리로 배를 채워보세요.', 3, '요리 사진 인증하기', '간단한 요리 도전', 4, 6L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (43L, 15L, '요리할 재료 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (44L, 15L, '요리 레시피 찾기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (45L, 15L, '요리 시작하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (35L, 15L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (16L, 40, '갓생의 기본을 시작해볼까요?.하루 일과를 기록하며 생활 패턴을 알아보세요.', 2, '일과 기록 인증하기', '하루 일과 기록하기', 1, 7L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (46L, 16L, '일과 기록 노트 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (47L, 16L, '아침부터 저녁까지 일과 기록하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (48L, 16L, '기록한 일과를 되돌아보기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (36L, 16L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (17L, 70, '운동의 힘을 느껴봅시다. 짧은 시간의 스트레칭으로 몸을 움켜보세요.', 1, '스트레칭 인증하기', '짧은 시간의 스트레칭', 2, 2L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (49L, 17L, '스트레칭 운동 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (50L, 17L, '안전한 곳에서 스트레칭 시작하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (51L, 17L, '스트레칭 후 몸의 상태 기록하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (37L, 17L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (18L, 80, '사진의 힘을 느껴봅시다. 주변 풍경을 담아보는 시간을 가져보세요.', 4, '풍경 사진 인증하기', '풍경 사진 촬영하기', 5, 6L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (52L, 18L, '사진을 찍을 장소 선정하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (53L, 18L, '카메라 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (54L, 18L, '주변 풍경 촬영하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (38L, 18L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (19L, 60, '아침을 특별하게 시작해보세요. 아침에 일어나자마자 물 한잔 마시기', 1, '아침 물 한잔 인증하기', '아침의 시작, 물 한잔', 1, 7L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (55L, 19L, '물병 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (56L, 19L, '아침에 물 한잔 마시기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (39L, 19L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (20L, 80, '스케치북을 들고 나가서 자연을 그려봅시다.', 5, '자연을 그려서 인증하기', '자연 스케치하기', 4, 4L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (57L, 20L, '스케치북과 필기구 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (58L, 20L, '그리고 싶은 장소 찾아가기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (59L, 20L, '자연을 관찰하고 그려보기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (40L, 20L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (21L, 90, '일기를 통해 하루를 되돌아봅시다.', 3, '일기 작성 후 인증하기', '일기 쓰기', 2, 7L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (60L, 21L, '일기장 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (61L, 21L, '하루의 기억을 되돌아보기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (62L, 21L, '감정과 생각을 일기에 적어보기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (41L, 21L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (22L, 100, '다른 나라의 음식을 직접 만들어 봅시다.', 6, '외국 음식 만들기 후 인증하기', '세계 음식 도전', 5, 4L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (63L, 22L, '만들고 싶은 음식 레시피 찾기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (64L, 22L, '재료 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (65L, 22L, '음식 만들어보기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (42L, 22L, 1L);

insert into challenge_card (id, assign_score, description, difficulty, final_goal, title, experience, challenge_card_category_id, create_dtm) values (23L, 70, '운동을 통해 활력을 불어넣어봅시다.', 4, '운동 후 셀카 인증하기', '활력의 원천, 운동하기', 3, 2L, '20231010120000');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (66L, 23L, '운동할 장소 선택하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (67L, 23L, '운동복과 운동화 준비하기');
insert into challenge_card_sub_goal (id, challenge_card_id, sub_goal) values (68L, 23L, '30분 이상 운동하기');
insert into challenge_card_member_personality(id, challenge_card_id, member_personality_id) values (43L, 23L, 1L);

