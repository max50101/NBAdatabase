database.sql
CREATE TABLE team(
id BigSerial PRIMARY KEY NOT NULL,
name VARCHAR(100) NOT NULL,
city Varchar(100) NOT NULL,
CountOfCmampions int,
season_place int,
play_off_place int
);
CREATE TABLE player
(
id BigSerial PRIMARY KEY,
team_id BigInt,
player_position VARCHAR(100) NOT NULL UNIQUE CHECK(player_position IN('PF','PG','SF','C','SG')),
name VARCHAR (50) NOT NULL,
age INT NOT NULL CHECK( age>15 and age<=38),
nationality VARCHAR (20) NOT NULL,
height VARCHAR (10) NOT NULL,
weight INT NOT NULL
);

CREATE TABLE player_stats(
player_id BigINT,
value VARCHAR(20),
progress_id BigINT
);

CREATE TABLE progress(
id BigSerial not null PRIMARY KEY,
name varchar(100) not null,
unit varchar(50)
);
CREATE TABLE contract_sponsor_player(
contract_sum int,
name varchar(100) not null PRIMARY KEY,
player_id BigInt
);
CREATE TABLE manager(
id BigSerial not null PRIMARY KEY,
name varchar(100) not null,
team_id BigInt
);
CREATE TABLE coach(
id BigSerial not null PRIMARY KEY,
name varchar(100) not null,
team_id BigInt,
speciality varchar(100) not null
);
CREATE TABLE contract_sponsor_team(
contract_sum int,
name varchar(100) not null PRIMARY KEY,
team_id BigInt
);
ALTER TABLE player_stats ADD PRIMARY KEY (player_id,progress_id);
ALTER TABLE manager 
	ADD CONSTRAINT managerstoteam FOREIGN KEY (team_id)
					REFERENCES team(id);
ALTER TABLE coach 
	ADD CONSTRAINT coachtoteam FOREIGN KEY (team_id)
					REFERENCES team(id);
ALTER TABLE contract_sponsor_team
	ADD CONSTRAINT contractsponsorteam FOREIGN KEY (team_id)
					REFERENCES team (id);
ALTER TABLE contract_sponsor_player
	ADD CONSTRAINT contractsponsorplayer FOREIGN KEY (player_id)
					REFERENCES player (id);
ALTER TABLE player_stats 
	ADD CONSTRAINT player_statstoplayer FOREIGN KEY (player_id)
					REFERENCES player(id),
	ADD CONSTRAINT player_statstoprogess FOREIGN KEY (progress_id)
					REFERENCES progress(id);

----INSERT INTO TEAM PROCEDURE
CREATE PROCEDURE Teaminstertupdatev     (_id BigInt,_Name varchar(100),_city varchar(100),_CountOfChampions int,
_season_place int,_play_off_place int)
LANGUAGE SQL
AS $$
INSERT INTO team
			(name ,city ,CountOfCmampions ,season_place ,play_off_place )
			VALUES
			(_Name,_city,_CountOfChampions,_season_place,_play_off_place )

$$;

--INSERT INTO COACH PROCEDURE
CREATE PROCEDURE insertCoach ( _id BigInt,_Name varchar(100),_speciality varchar(100))
LANGUAGE SQL
AS $$
INSERT INTO coach
			(name ,team_id ,speciality )
			VALUES
			(_Name,_id,_speciality )

$$;
--Insert INTO CONTRACT_SPONSOR_TEAM
CREATE PROCEDURE sponsorteaminsert     ( _id BigInt,_Name varchar(100),_contractsum int)
LANGUAGE SQL
AS $$
INSERT INTO contract_sponsor_team
			(name ,team_id , contract_sum)
			VALUES
			(_Name,_id,_contractsum )

$$;
-INSERT INTO CONTRACT_SPONSOW_PLAYER
CREATE PROCEDURE sponsorplayerinsert  ( _id BigInt,_Name varchar(100),_contractsum int)
LANGUAGE SQL
AS $$
INSERT INTO contract_sponsor_player
			(name ,player_id , contract_sum)
			VALUES
			(_Name,_id,_contractsum )

$$;

--INSERT INTO PLAYER
CREATE PROCEDURE playerInsert     (_id BigInt,_age int,_nationality varchar(20),_playerposition varchar(100),
_height varchar(10),_weight int,_Name varchar(100))
LANGUAGE SQL
AS $$
INSERT INTO player
			(name,age,nationality ,team_id,player_position , weight,height)
			VALUES
			(_Name,_age,_nationality,_id,_playerposition,_weight,_height )
$$;

--INSERT INTO PLAYER_STATS
CREATE PROCEDURE playerstatsInsert  (_player_id BigInt,   _value varchar(20),_progressid BigInt)
LANGUAGE SQL
AS $$
INSERT INTO player_stats
			(player_id,value,progress_id)
			VALUES
			(_player_id,_value,_progressid )
$$;


--INSERT INTO PROGRESS
CREATE PROCEDURE progressInsert  (_name varchar(100), _unit varchar(50))
LANGUAGE SQL
AS $$
INSERT INTO progress
			(name,unit)
			VALUES
			(_name,_unit )
$$;
CREATE TABLE tmp_Team(
n serial not null,
id int PRIMARY KEY NOT NULL,
name VARCHAR(100) NOT NULL,
city Varchar(100) NOT NULL,
CountOfCmampions int,
season_place int,
play_off_place int);


CREATE TABLE tmp_player(
n serial not null,
id int PRIMARY KEY,
team_id BigInt,
player_position	 VARCHAR(100) NOT NULL UNIQUE CHECK(player_position IN('PF','PG','SF','C','SG')),
name VARCHAR (50) NOT NULL,
age INT NOT NULL CHECK( age>15 and age<=38),
nationality VARCHAR (20) NOT NULL,
height VARCHAR (10) NOT NULL,
weight INT NOT NULL
);
CREATE TABLE tmp_player_stats(
n serial not null,
player_id BigINT,
value VARCHAR(20),
progress_id BigINT
);
CREATE TABLE tmp_progress(
n serial not null,
id int not null PRIMARY KEY,
name varchar(100) not null,
unit varchar(50)
);
CREATE TABLE tmp_contract_sponsor_player(
n serial not null,
contract_sum int,
name varchar(100) not null PRIMARY KEY,
player_id BigInt
);
CREATE TABLE tmp_manager(
n serial not null,
id int not null PRIMARY KEY,
name varchar(100) not null,
team_id BigInt
);
CREATE TABLE tmp_coach(
n serial not null,
id int not null PRIMARY KEY,
name varchar(100) not null,
team_id BigInt,
speciality varchar(100) not null
);
CREATE TABLE tmp_contract_sponsor_team(
n serial not null,
contract_sum int,
name varchar(100) not null PRIMARY KEY,
team_id BigInt
);
CREATE TABLE story_help(
id serial not null,
kto varchar,
kto_id integer,
gde varchar,
gde_id integer,
chto varchar,
cogda timestamptz,
Constraint story_help_pk Primary Key(id)
)
--TRIGERS WE ARE DOING 3 types for each tabel, insert, delete, update
create function team_c() returns trigger as $team_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Team',new.ID,'insert',now());
insert into tmp_team(id,name,city,CountOfChampions,season_place,play_off_place) 
values(new.id,new.name,new.city,new.CountOfCmampions,new.season_place,new.play_off_place);
end if;
return new;
end;
$team_c$ Language plpgsql;
CREATE TRIGGER team_c BEFORE INSERT ON team
FOR EACH ROW EXECUTE PROCEDURE team_c();

create function team_u() returns trigger as $team_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Team',new.ID,'update',now());
insert into tmp_team(id,name,city,CountOfChampions,season_place,play_off_place) 
values(old.id,old.name,old.city,old.CountOfCmampions,old.season_place,old.play_off_place);
end if;
return new;
end;
$team_u$ Language plpgsql;
CREATE TRIGGER team_u BEFORE UPDATE ON team
FOR EACH ROW EXECUTE PROCEDURE team_u();

create function team_d() returns trigger as $team_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Team',old.ID,'delete',now());
insert into tmp_team(id,name,city,CountOfChampions,season_place,play_off_place) 
values(old.id,old.name,old.city,old.CountOfCmampions,old.season_place,old.play_off_place);
end if;
return null;
end;
$team_d$ Language plpgsql;
CREATE TRIGGER team_d AFTER DELETE ON team
FOR EACH ROW EXECUTE PROCEDURE team_d();

create function player_c() returns trigger as $player_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player',new.ID,'insert',now());
insert into tmp_player(id,team_id,name,player_position,age,nationality,height,weight) 
values(new.id,new.team_id,new.name,new.player_position,new.age,new.nationality,new.height,new.weight);
end if;
return new;
end;
$player_c$ Language plpgsql;
CREATE TRIGGER player_c BEFORE INSERT ON player
FOR EACH ROW EXECUTE PROCEDURE player_c();


create function player_u() returns trigger as $player_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player',new.ID,'update',now());
insert into tmp_player(id,team_id,name,player_position,age,nationality,height,weight) 
values(old.id,old.team_id,old.name,old.player_position,old,age,old.nationality,old.height,old.weight);
end if;
return new;
end;
$player_u$ Language plpgsql;
CREATE TRIGGER player_u BEFORE UPDATE ON player
FOR EACH ROW EXECUTE PROCEDURE player_u();

create function player_d() returns trigger as $player_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player',old.ID,'delete',now());
insert into tmp_player(id,team_id,name,player_position,age,nationality,height,weight) 
values(old.id,old.team_id,old.name,old.player_position,old.age,old.nationality,old.height,old.weight);
end if;
return null;
end;
$player_d$ Language plpgsql;
CREATE TRIGGER player_d AFTER DELETE ON player
FOR EACH ROW EXECUTE PROCEDURE player_d();

create function player_stats_c() returns trigger as $player_stats_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player_stats',new.ID,'insert',now());
insert into tmp_player_stats(player_id,value,progress_id) 
values(new.player_id,new.value,new.progress_id);
end if;
return new;
end;
$player_stats_c$ Language plpgsql;
CREATE TRIGGER player_stats_c BEFORE INSERT ON player_stats
FOR EACH ROW EXECUTE PROCEDURE player_stats_c();

create function player_stats_u() returns trigger as $player_stats_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player_stats',new.ID,'update',now());
insert into tmp_player_stats(player_id,value,progress_id) 
values(old.player_id,old.value,old.progress_id);
end if;
return new
end;
$player_stats_u$ Language plpgsql;
CREATE TRIGGER player_stats_u BEFORE update ON player_stats
FOR EACH ROW EXECUTE PROCEDURE player_stats_u();

create function player_stats_d() returns trigger as $player_stats_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'Player_stats',old.ID,'delete',now());
insert into tmp_player_stats(player_id,value,progress_id) 
values(old.player_id,old.value,old.progress_id);
end if;
return null;
end;
$player_stats_d$ Language plpgsql;
CREATE TRIGGER player_stats_d AFTER DELETE ON player_stats
FOR EACH ROW EXECUTE PROCEDURE player_stats_d();

create function progress_c() returns trigger as $progress_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'progress',new.ID,'insert',now());
insert into tmp_progress(id,name,unit) 
values(new.id,new.name,new.unit);
end if;
return new;
end;
$progress_c$ Language plpgsql;
CREATE TRIGGER progress_c BEFORE INSERT ON progress
FOR EACH ROW EXECUTE PROCEDURE progress_c();

create function progress_u() returns trigger as $progress_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'progress',new.ID,'update',now());
insert into tmp_progress(id,name,unit) 
values(old.id,old.name,old.unit);
end if;
return new;
end;
$progress_u$ Language plpgsql;
CREATE TRIGGER progress_u BEFORE UPDATE ON progress
FOR EACH ROW EXECUTE PROCEDURE progress_u();

create function progress_d() returns trigger as $progress_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'progress',old.ID,'delete',now());
insert into tmp_progress(id,name,unit) 
values(old.id,old.name,old.unit);
end if;
return null;
end;
$progress_d$ Language plpgsql;
CREATE TRIGGER progress_d AFTER DELETE ON progress
FOR EACH ROW EXECUTE PROCEDURE progress_d();

create function contract_sponsor_player_c() returns trigger as $contract_sponsor_player_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_player',new.ID,'insert',now());
insert into tmp_contract_sponsor_player(id,name,player_id,contract_sum) 
values(new.id,new.name,new.player_id,new.contract_sum);
end if;
return new;
end;
$contract_sponsor_player_c$ Language plpgsql;
CREATE TRIGGER contract_sponsor_player_c BEFORE INSERT		 ON contract_sponsor_player
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_player_c();

create function contract_sponsor_player_u() returns trigger as $contract_sponsor_player_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_player',new.ID,'update',now());
insert into tmp_contract_sponsor_player(id,name,player_id,contract_sum) 
values(old.id,old.name,old.player_id,old.contract_sum);
end if;
return new;
end;
$contract_sponsor_player_u$ Language plpgsql;
CREATE TRIGGER contract_sponsor_player_u BEFORE UPDATE	 ON contract_sponsor_player
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_player_u();

create or replace function contract_sponsor_player_d() returns trigger as $contract_sponsor_player_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_player',old.ID,'delete',now());
insert into tmp_contract_sponsor_player(id,name,player_id,contract_sum) 
values(old.id,old.name,old.player_id,old.contract_sum);
end if;
return null;
end;
$contract_sponsor_player_d$ Language plpgsql;
CREATE TRIGGER contract_sponsor_player_d AFTER DELETE	 ON contract_sponsor_player
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_player_d();


create function contract_sponsor_team_c() returns trigger as $contract_sponsor_team_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_team',new.ID,'insert',now());
insert into tmp_contract_sponsor_team(id,name,team_id,contract_sum) 
values(new.id,new.name,new.team_id,new.contract_sum);
end if;
return new;
end;
$contract_sponsor_team_c$ Language plpgsql;
CREATE TRIGGER contract_sponsor_team_c BEFORE Insert ON contract_sponsor_team
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_team_c();

create function contract_sponsor_team_u() returns trigger as $contract_sponsor_team_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_team',new.ID,'update',now());
insert into tmp_contract_sponsor_team(id,name,team_id,contract_sum) 
values(old.id,old.name,old.team_id,old.contract_sum);
end if;
return new;
end;
$contract_sponsor_team_u$ Language plpgsql;
CREATE TRIGGER contract_sponsor_team_u BEFORE UPDATE ON contract_sponsor_team
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_team_u();

create or replace function contract_sponsor_team_d() returns trigger as $contract_sponsor_team_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'contract_sponsor_team',old.ID,'delete',now());
insert into tmp_contract_sponsor_team(id,name,team_id,contract_sum) 
values(old.id,old.name,old.team_id,old.contract_sum);
end if;
return null;
end;
$contract_sponsor_team_d$ Language plpgsql;
CREATE TRIGGER contract_sponsor_team_d AFTER DELETE	 ON contract_sponsor_team
FOR EACH ROW EXECUTE PROCEDURE contract_sponsor_team_d();

create or replace function coach_c() returns trigger as $coach_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'coach',new.ID,'insert',now());
insert into tmp_coach(id,name,speciality,team_id) 
values(new.id,new.name,new.speciality,new.team_id);
end if;
return new;
end;
$coach_c$ Language plpgsql;
CREATE TRIGGER coach_c BEFORE INSERT	 ON coach
FOR EACH ROW EXECUTE PROCEDURE coach_c();

create or replace function coach_u() returns trigger as $coach_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'coach',new.ID,'update',now());
insert into tmp_coach(id,name,speciality,team_id) 
values(old.id,old.name,old.speciality,old.team_id);
end if;
return new;
end;
$coach_u$ Language plpgsql;
CREATE TRIGGER coach_u BEFORE UPDATE	 ON coach
FOR EACH ROW EXECUTE PROCEDURE coach_u();

create or replace function coach_d() returns trigger as $coach_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'coach',old.ID,'delete',now());
insert into tmp_coach(id,name,speciality,team_id) 
values(old.id,old.name,old.speciality,old.team_id);
end if;
return null;
end;
$coach_d$ Language plpgsql;
CREATE TRIGGER coach_d AFTER DELETE	 ON coach
FOR EACH ROW EXECUTE PROCEDURE coach_d();


create or replace function manager_c() returns trigger as $manager_c$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'manager',new.ID,'insert',now());
insert into tmp_manager(id,name,team_id) 
values(new.id,new.name,new.team_id);
end if;
return new;
end;
$manager_c$ Language plpgsql;


create or replace function manager_u() returns trigger as $manager_u$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'manager',new.ID,'update',now());
insert into tmp_manager(id,name,team_id) 
values(old.id,old.name,old.team_id);
end if;
return new;
end;
$manager_u$ Language plpgsql;

create or replace function manager_d() returns trigger as $manager_d$
begin
if ((select Bol from kld where Polzovatel='admin')=true) THEN
insert into story_help (kto,kto_id,gde,gde_id,chto,cogda) values('admin',42,'manager',old.ID,'delete',now());
insert into tmp_manager(id,name,team_id) 
values(old.id,old.name,old.team_id);
end if;
return null;
end;
$manager_d$ Language plpgsql;



create or replace function undo() returns trigger as $get_back$
DECLARE
	ii varchar;
	iii integer;
	w integer;
	ww integer;
	h integer;
	hh integer;
begin
update kld set Bol=false where Polzovatel='admin';
ii = old.gde;
iii = old.gde_id;
if(ii='Team') then
w=(select max(n) from tmp_team where id=iii);
ww=(select id from story_help ORDER BY cogda LIMIT 1);
if(old.chto='insert') then
delete from team where id=old.gde_id;
delete from tmp_team where n=w;
end if;
if(old.chto='update') then
update team set
name=(select Name from tmp_team where tmp_team.n=w),
city=(select city from tmp_team where tmp_team.n=w),
countofchampions=(select countofchampions from tmp_team where tmp_team.n=w),
season_place=(select season_place from tmp_team where tmp_team.n=w),	
play_off_place=(select play_off_place from tmp_team where tmp_team.n=w)
where team.id=old.gde_id;
delete from tmp_team where n=w;
end if;
if(old.chto='delete') then
h=(select ID from tmp_team where tmp_team.n=w);
INSERT INTO team
(id,name ,city ,CountOfCmampions ,season_place ,play_off_place ) VALUES 
((select id from tmp_team where tmp_team.n=w),(select name from tmp_team where tmp_team.n=w),(select city from tmp_team where tmp_team.n=w),
(select countofchampions from tmp_team where tmp_team.n=w),
(select season_place from tmp_team where tmp_team.n=w),(select play_off_place from tmp_team where tmp_team.n=w));
delete from tmp_team where n=w;
end if;

 delete from tmp_team where tmp_team.n=w;
 end if;
 update kld set Bol=true where Polzovatel='admin';
RETURN NULL;
end;

if(ii='Player') then
w = (select max(n) from tmp_player where ID=iii);
ww=(select id from story_help
ORDER BY cogda DESC
  LIMIT 1);
if(old.chto='insert') then
delete from player where ID=old.gde_id;
delete from tmp_player where n=w;
end if;
if(old.chto='update') then
update Player set team_id=(select team_id from tmp_player where tmp_player.n=w),
player_position=(select player_position from tmp_player where tmp_player.n=w),
age=(select age from tmp_player where tmp_player.n=w),
nationality=(select nationality from tmp_player where tmp_player.n=w),
height=(select height from tmp_player where tmp_player.n=w),
weight=(select weight from tmp_player where tmp_Автор.n=w)
where player.id=old.gde_id;
delete from tmp_Автор where n=w;
end if;
if(old.chto='delete') then
h=(select ID from tmp_player where tmp_player.n=w);
insert into Player (ID, team_id,player_position,age,nationality,height,weight) values ((select ID from tmp_player where tmp_player.n=w),
(select team_id from tmp_player where tmp_player.n=w),
(select player_position from tmp_player where tmp_player.n=w),
(select age from tmp_player where tmp_player.n=w),
(select nationality from tmp_player where tmp_player.n=w),
(select height from tmp_player where tmp_player.n=w),
(select weight from tmp_player where tmp_player.n=w);
delete from tmp_player where n=w;
end if;
delete from tmp_player where tmp_player.n=w;
end if;

-- if(ii='Player_stats') then
-- w = (select max(n) from tmp_player_stats where ID=iii);
-- ww=(select id from story_help
-- ORDER BY cogda DESC
--   LIMIT 1);
-- if(old.chto='insert') then
-- delete from player_stats where ID=old.gde_id;
-- delete from tmp_player_stats where n=w;
-- end if;
-- if(old.chto='update') then
-- update player_stats set player_id=(select player_id from tmp_player_stats where tmp_player_stats.n=w),
-- value=(select value from tmp_player_stats where tmp_player_stats.n=w),
-- progress_id=(select progress_id from tmp_player_stats where tmp_player_stats.n=w)
-- where player_stats.id=old.gde_id;
-- delete from tmp_player_stats where n=w;
-- end if;
-- if(old.chto='delete') then
-- h=(select ID from tmp_player_stats where tmp_player.n=w);
-- insert into player_stats (player_id,value,progress_id) values ((select player_id from tmp_player_stats where tmp_player_stats.n=w),
-- (select value from tmp_player_stats where tmp_player_stats.n=w),
-- (select progress_id from tmp_player_stats where tmp_player_stats.n=w),
-- ;
-- delete from tmp_player_stats where n=w;
-- end if;
-- delete from tmp_player_stats where tmp_player_stats.n=w;
-- end if;

-- if(ii='progress') then
-- w = (select max(n) from tmp_progress where ID=iii);
-- ww=(select id from story_help
-- ORDER BY cogda DESC
--   LIMIT 1);
-- if(old.chto='insert') then
-- delete from progress where ID=old.gde_id;
-- delete from tmp_progress where n=w;
-- end if;
-- if(old.chto='update') then
-- update progress set progress_id=(select progress_id from tmp_progress where tmp_progress.n=w),
-- name=(select name from tmp_progress where tmp_progress.n=w),
-- unit=(select unit from tmp_progress where tmp_progress.n=w)
-- where progress.id=old.gde_id;
-- delete from tmp_progress where n=w;
-- end if;
-- if(old.chto='delete') then
-- h=(select ID from tmp_progress where tmp_progress.n=w);
-- insert into progress (id,name,unit) values ((select id from tmp_progress where tmp_progress.n=w),
-- (select name from tmp_progress where tmp_progress.n=w),
-- (select unit from tmp_progress where tmp_progress.n=w),
-- ;
-- delete from tmp_progress where n=w;
-- end if;
-- delete from tmp_progress where tmp_progress.n=w;
-- end if;

-- if(ii='contract_sponsor_player') then
-- w = (select max(n) from tmp_contract_sponsor_player where ID=iii);
-- ww=(select id from story_help
-- ORDER BY cogda DESC
--   LIMIT 1);
-- if(old.chto='insert') then
-- delete from contract_sponsor_player where ID=old.gde_id;
-- delete from tmp_contract_sponsor_player where n=w;
-- end if;
-- if(old.chto='update') then
-- update contract_sponsor_player set player_id=(select player_id from contract_sponsor_player where tmp_contract_sponsor_player.n=w),
-- name=(select name from tmp_contract_sponsor_player where tmp_contract_sponsor_player.n=w),
-- contract_sum=(select contract_sum from tmp_contract_sponsor_player where tmp_contract_sponsor_player.n=w)
-- where contract_sponsor_player.id=old.gde_id;
-- delete from tmp_progress where n=w;
-- end if;
-- if(old.chto='delete') then
-- h=(select ID from tmp_progress where tmp_progress.n=w);
-- insert into progress (id,name,unit) values ((select id from tmp_progress where tmp_progress.n=w),
-- (select name from tmp_progress where tmp_progress.n=w),
-- (select unit from tmp_progress where tmp_progress.n=w),
-- ;
-- delete from tmp_progress where n=w;
-- end if;
-- delete from tmp_progress where tmp_progress.n=w;
-- end if;
$get_back$ LANGUAGE plpgsql;
CREATE TRIGGER get_back AFTER DELETE ON story_help
FOR EACH ROW EXECUTE PROCEDURE undo();
 

