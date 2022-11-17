insert into TEAM(`name`, `code`) values('서울 FC', 'S');
insert into TEAM(`name`, `code`) values('강원 FC', 'G');
insert into TEAM(`name`, `code`) values('충북 FC', 'CB');
insert into TEAM(`name`, `code`) values('충남 FC', 'CN');
insert into TEAM(`name`, `code`) values('경남 FC', 'GN');
insert into TEAM(`name`, `code`) values('경북 FC', 'GB');
insert into TEAM(`name`, `code`) values('전북 FC', 'JB');
insert into TEAM(`name`, `code`) values('전남 FC', 'JN');

insert into _USER(`login_id`, `login_pw`, `name`, `imgUrl`, `team_id`, `role`,`age`) values('player','{bcrypt}$2a$10$d3QK7jlUd8NI6EdHmILzfejSoF.XSlmT/eRdb9zaXuM8YuWmTdzSO','test', 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png', 1,'PLAYER',25);
insert into Player(`position`,`user_id`) values('FW',1);

insert into _USER(`login_id`, `login_pw`, `name`, `imgUrl`, `team_id`, `role`,`age`) values('director','{bcrypt}$2a$10$d3QK7jlUd8NI6EdHmILzfejSoF.XSlmT/eRdb9zaXuM8YuWmTdzSO','test', 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png', 1,'DIRECTOR',25);
insert into Director(`user_id`) values(2);

insert into Diagnose(`title`, `deleted`) values('경기력', 'N');
insert into Diagnose(`title`, `deleted`) values('기능자신감', 'N');
insert into Diagnose(`title`, `deleted`) values('부상',  'N');
insert into Diagnose(`title`, `deleted`) values('슬럼프', 'N');
insert into Diagnose(`title`, `deleted`) values('진로미래', 'N');
insert into Diagnose(`title`, `deleted`) values('관계',  'N');
insert into Diagnose(`title`, `deleted`) values('학업',  'N');
insert into Diagnose(`title`, `deleted`) values('주전경쟁', 'N');
insert into Diagnose(`title`, `deleted`) values('훈련',  'N');
insert into Diagnose(`title`, `deleted`) values('부담감', 'N');
insert into Diagnose(`title`, `deleted`) values('사생활규제', 'N');

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 전 스트레스 원인 체크 해주세요', 0, '경기전-스트레스', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 전 불안감에 대해 체크해주세요', 1,'경기전-불안감', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('어웨이 경기장 환경에 대해 스트레스 체크해주세요', 4,'어웨이 경기장 환경', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 전 훈련 집중도에 대해 체크해주세요', 2, '경기전-집중도', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 전 자신감에 대한 스트레스 체크해주세요', 3, '경기전-자신감', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 전 경기 부담감에 대해 체크해주세요', 0, '경기전-부담감', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 중 스트레스 원인 체크 해주세요', 0, '경기중-스트레스', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 중 실수에 대한 두려움에 대해 체크해주세요', 1, '경기중-실수', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 중 부상에 대한 걱정에 대해 체크해주세요', 4,'경기중-부상', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values('경기 중 자신감 하락에 대해 체크해주세요', 2, '경기중-자신감', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 중 심판에 의한 스트레스에 대해 체크해주세요', 3, '경기중-심판', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 중 감독/코치 지시에 의한 스트레스에 대해 체크', 0,'경기중-감독/코치', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 중 실력발휘에 대한 스트레스에 대해 체크해주세요', 0, '경기중-실력발휘', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 후 스트레스 원인 체크 해주세요.', 1, '경기후-스트레스', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 패배에 대한 스트레스 체크해주세요.', 4, '경기 패배', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기 패배 원인제공에 대한 스트레스 체크해주세요.', 2, '패배 원인제공', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '다른 선수로 인한 경기 패배 스트레스 체크해주세요.', 3, '다른 선수로 인한 패배', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경기력 불만족에 대한 스트레스 체크해주세요.', 0, '경기력 불만족', 'N', 1);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '실력 부족에 대한 스트레스 체크해주세요. ', 0, '실력 부족', 'N', 1);


insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '기능 자신감 스트레스 원인 체크 해주세요', 1, '기능 자신감', 'N', 2);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '기술에 대한 스트레스 체크해주세요', 1, '기술', 'N', 2);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '체력에 대한 스트레스 체크해주세요', 1, '체력', 'N', 2);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '피지컬(키)에 대한 스트레스 체크해주세요', 2, '피지컬(키)', 'N', 2);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '피지컬(체형)에 대한 스트레스 체크해주세요', 2, '피지컬(체형)', 'N', 2);


insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '부상 스트레스 원인 체크해주세요.', 1,'부상-스트레스', 'N', 3);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '부상에 대한 스트레스 체크해주세요.', 2, '부상', 'N', 3);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '부상으로 인한 경기불참에 대한 스트레스 체크해주세요.', 2, '경기 불참', 'N', 3);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '부상 후 재활치료에 대한 스트레스 체크해주세요.', 0, '부상 후 재활', 'N', 3);


insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '슬럼프 스트레스 원인 체크해주세요', 0, '슬럼프-스트레스', 'N', 4);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '슬럼프 극복 어려움에 대해 체크해주세요.', 0, '슬럼프 극복 어려움', 'N', 4);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '어려움을 이야기 할 사람에 대해 체크해주세요.', 0,'슬럼프-사람', 'N', 4);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '진로 스트레스 원인 체크 해주세요.', 0, '진로', 'N', 5);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '진학에 대한 스트레스 체크해주세요.', 0, '진학', 'N', 5);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '미래에 대한 불안 스트레스 체크해주세요.', 0, '미래 불안', 'N', 5);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '선수로서 실패에 대한 불안 스트레스 체크해주세요.', 0, '선수로서 실패 불안', 'N', 5);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '관계 스트레스 원인 체크 해주세요.', 0, '관계-스트레스', 'N', 6);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '부모님과의 관계 스트레스 체크해주세요', 0, '부모님', 'N', 6);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '동료와의 관계 스트레스 체크해주세요', 0, '동료', 'N', 6);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '선배와의 관계 스트레스 체크해주세요', 0, '선배', 'N', 6);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '지도자와의 관계 스트레스 체크해주세요', 0, '지도자', 'N', 6);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '이성과의 관계 스트레스 체크해주세요.', 0, '이성', 'N', 6);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '훈련 스트레스 원인 체크 해주세요.', 0, '훈련-스트레스', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '흥미에 대한 스트레스 체크해주세요.', 0, '흥미', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '규칙적인 훈련 대한 불안 스트레스 체크해주세요.', 0, '규칙적 훈련', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '훈련 중 부상에 스트레스 체크해주세요.', 0, '훈련 중 부상', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '훈련에서 오는 경쟁에 대한 스트레스 체크해주세요.', 0, '훈련 중 경쟁', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '훈련에서 실수에 대한 스트레스 체크해주세요.', 0, '훈련 중 실수', 'N', 7);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '훈련량에 대한 스트레스 체크해주세요.', 0, '훈련량', 'N', 7);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '학업 스트레스 원인(중분류) 체크 해주세요.', 0, '학업-스트레스', 'N', 8);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '수업 흥미 스트레스 강도 체크', 0, '수업 흥미', 'N', 8);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '공부 방법 스트레스 강도 체크', 0, '공부 방법', 'N', 8);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '학업 성적 스트레스 강도 체크', 0, '학업 성적', 'N', 8);


insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '경쟁 스트레스 원인 체크해주세요', 0, '경쟁-스트레스', 'N', 9);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '실력 한계에 대한 스트레스 체크해주세요', 0, '실력 한계', 'N', 9);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '주전 경쟁에 대한 스트레스 체크해주세요', 0, '주전 경쟁', 'N', 9);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '가족 기대감에 대한 스트레스 체크해주세요.', 0, '가족 기대감', 'N', 10);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '지도자의 기대감에 대한 스트레스 체크해주세요.', 0,'지도자 기대감',  'N', 10);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '주위 기대감(동료, 친척 등)에 대한 스트레스 체크', 0, '주위 기대감', 'N', 10);

insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '사생활 규제에 대한 스트레스 원인 체크해주세요.', 0, '사생활 규제-스트레스', 'N', 11);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '자유시간 규제에 대한 스트레스 체크해주세요', 0, '자유시간 규제', 'N', 11);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '단체생활 스트레스에 대해 체크해주세요', 0, '단체 생활', 'N', 11);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '사생활 구속에 대한 스트레스 체크해주세요', 0, '사생활 구속', 'N', 11);
insert into Question(`context`, `weight`, `keyword` , `deleted`, `diagnose_id`) values( '똑같은 패턴 생활에 대한 스트레스 체크해주세요.', 0, '반복된 생활 패턴', 'N', 11);





