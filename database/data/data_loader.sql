insert into album(album_id,album_name,release_date,album_cover)
	values ('1','1989','2014-10-27','Taylor_Swift_-_1989.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('2','Red','2012-10-22','Taylor_Swift_-_Red.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('3','Let It Bleed','1969-12-05','Rolling_Stones_-_Let_It_Bleed.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('4','News of the World','1977-10-28','Queen_-_News_Of_The_World.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('5','The Works','1984-02-27','Queen_-_The_Works.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('6','1000 Forms of Fear','2014-07-04','Sia_-_1000_Forms_of_Fear.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('7','21','2011-01-19','Adele_-_21.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('8','25','2015-11-20','Adele_-_25.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('9','What a Time to Be Alive','2015-09-20','Drake_Future_-_What_a_Time_to_Be_Alive.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('10','Nothing Was the Same','2013-09-23','Drake_-_Nothing_Was_the_Same.png');
insert into album(album_id,album_name,release_date,album_cover)
	values ('11','Under the Mistletoe','2011-11-01','Justin_Bieber_-_Under_the_Mistletoe.png');
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (1,'Bad Blood','Taylor_Swift_-_Bad_Blood.txt','Taylor_Swift_-_Bad_Blood.mp3',211,1,8);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (2,'Blank Space','Taylor_Swift_-_Blank_Space.txt','Taylor_Swift_-_Blank_Space.mp3',231,1,2);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (3,'Treacherous','Taylor_Swift_-_Treacherous.txt','Taylor_Swift_-_Treacherous.mp3',241,2,3);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (4,'Gimme Shelter','Rolling_Stones_-_Gimme_Shelter.txt','Rolling_Stones_-_Gimme_Shelter.mp3',277,3,1);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (5,'We Will Rock You','Queen_-_We_Will_Rock_You.txt','Queen_-_We_Will_Rock_You.mp3',122,4,1);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (6,'One and Only','Adele_-_One_and_Only.txt','Adele_-_One_and_Only.mp3',348,7,9);
insert into general_song(song_id,track_name,lyrics,audio_sample,length_seconds,album_id,track_number)
	values (7,'The Christmas Song (Chestnuts Roasting on an Open Fire)','Justin_Bieber_-_The_Christmas_Song.txt','Justin_Bieber_-_The_Christmas_Song.mp3',215,11,3);
insert into single_song(song_id,release_date,cover_art)
	values (1,'2015-05-17','Taylor_Swift_-_Bad_Blood.png');
insert into single_song(song_id,release_date,cover_art)
	values (2,'2014-11-10','Taylor_Swift_-_Blank_Space.png');
insert into single_song(song_id,release_date,cover_art)
	values (4,'1969-12-05',NULL);
insert into single_song(song_id,release_date,cover_art)
	values (5,'1977-10-07','Queen_-_We_Will_Rock_You.png');
insert into artist(artist_name)
	values ('Taylor Swift');
insert into artist(artist_name)
	values ('The Rolling Stones');
insert into artist(artist_name)
	values ('Queen');
insert into artist(artist_name)
	values ('Sia');
insert into artist(artist_name)
	values ('Adele');
insert into artist(artist_name)
	values ('Drake');
insert into artist(artist_name)
	values ('Future');
insert into artist(artist_name)
	values ('Usher');
insert into artist(artist_name)
	values ('Justin Bieber');
insert into genre(genre_name)
	values ('pop');
insert into genre(genre_name)
	values ('hip hop');
insert into genre(genre_name)
	values ('electropop');
insert into genre(genre_name)
	values ('dance-pop');
insert into genre(genre_name)
	values ('synthpop');
insert into genre(genre_name)
	values ('hard rock');
insert into genre(genre_name)
	values ('blues');
insert into genre(genre_name)
	values ('country blues');
insert into genre(genre_name)
	values ('arena rock');
insert into genre(genre_name)
	values ('rock');
insert into genre(genre_name)
	values ('soul');
insert into genre(genre_name)
	values ('R&B');
insert into genre(genre_name)
	values ('trap');
insert into genre(genre_name)
	values ('pop rock');
insert into genre(genre_name)
	values ('holiday');
insert into genre(genre_name)
	values ('teen pop');
insert into music_format(format_name)
	values ('mp3');
insert into music_format(format_name)
	values ('audio cd');
insert into music_format(format_name)
	values ('vinyl');
insert into music_format(format_name)
	values ('cassette');
insert into store(store_name)
	values ('Amazon');
insert into store(store_name)
	values ('Barnes & Noble');
insert into store(store_name)
	values ('Walmart');
insert into artists_of_songs(song_id,artist_name)
	values (1,'Taylor Swift');
insert into artists_of_songs(song_id,artist_name)
	values (2,'Taylor Swift');
insert into artists_of_songs(song_id,artist_name)
	values (3,'Taylor Swift');
insert into artists_of_songs(song_id,artist_name)
	values (4,'The Rolling Stones');
insert into artists_of_songs(song_id,artist_name)
	values (5,'Queen');
insert into artists_of_songs(song_id,artist_name)
	values (6,'Adele');
insert into featured_artists_of_songs(song_id,featured_artist_name)
	values (7,'Usher');
insert into genre_of_song(song_id,genre_name)
	values (1,'pop');
insert into genre_of_song(song_id,genre_name)
	values (1,'hip hop');
insert into genre_of_song(song_id,genre_name)
	values (2,'electropop');
insert into genre_of_song(song_id,genre_name)
	values (3,'pop');
insert into genre_of_song(song_id,genre_name)
	values (4,'hard rock');
insert into genre_of_song(song_id,genre_name)
	values (5,'arena rock');
insert into genre_of_song(song_id,genre_name)
	values (6,'pop rock');
insert into genre_of_song(song_id,genre_name)
	values (7, 'holiday');
insert into artists_of_albums(album_id,artist_name)
	values (1,'Taylor Swift');
insert into artists_of_albums(album_id,artist_name)
	values (2,'Taylor Swift');
insert into artists_of_albums(album_id,artist_name)
	values (3,'The Rolling Stones');
insert into artists_of_albums(album_id,artist_name)
	values (4,'Queen');
insert into artists_of_albums(album_id,artist_name)
	values (5,'Queen');
insert into artists_of_albums(album_id,artist_name)
	values (6,'Sia');
insert into artists_of_albums(album_id,artist_name)
	values (7,'Adele');
insert into artists_of_albums(album_id,artist_name)
	values (8,'Adele');
insert into artists_of_albums(album_id,artist_name)
	values (9,'Drake');
insert into artists_of_albums(album_id,artist_name)
	values (9,'Future');
insert into artists_of_albums(album_id,artist_name)
	values (10,'Drake');
insert into artists_of_albums(album_id,artist_name)
	values (11,'Justin Bieber');
insert into genre_of_album(album_id,genre_name)
	values (1,'pop');
insert into genre_of_album(album_id,genre_name)
	values (1,'dance-pop');
insert into genre_of_album(album_id,genre_name)
	values (1,'synthpop');
insert into genre_of_album(album_id,genre_name)
	values (2,'pop');
insert into genre_of_album(album_id,genre_name)
	values (3,'hard rock');
insert into genre_of_album(album_id,genre_name)
	values (3,'blues');
insert into genre_of_album(album_id,genre_name)
	values (3,'country blues');
insert into genre_of_album(album_id,genre_name)
	values (4,'arena rock');
insert into genre_of_album(album_id,genre_name)
	values (4,'rock');
insert into genre_of_album(album_id,genre_name)
	values (5,'rock');
insert into genre_of_album(album_id,genre_name)
	values (6,'electropop');
insert into genre_of_album(album_id,genre_name)
	values (7,'soul');
insert into genre_of_album(album_id,genre_name)
	values (7,'pop');
insert into genre_of_album(album_id,genre_name)
	values (7,'R&B');
insert into genre_of_album(album_id,genre_name)
	values (8,'soul');
insert into genre_of_album(album_id,genre_name)
	values (9,'hip hop');
insert into genre_of_album(album_id,genre_name)
	values (9,'trap');
insert into genre_of_album(album_id,genre_name)
	values (10,'hip hop');
insert into genre_of_album(album_id,genre_name)
	values (11,'teen pop');
insert into genre_of_album(album_id,genre_name)
	values (11,'R&B');
insert into genre_of_album(album_id,genre_name)
	values (11,'holiday');
insert into format_of_album(distribution_id,album_id,format_name)
	values (1,1,'mp3');
insert into format_of_album(distribution_id,album_id,format_name)
	values (2,1,'audio cd');
insert into format_of_album(distribution_id,album_id,format_name)
	values (3,1,'vinyl');
insert into format_of_album(distribution_id,album_id,format_name)
	values (4,2,'mp3');
insert into format_of_album(distribution_id,album_id,format_name)
	values (5,2,'audio cd');
