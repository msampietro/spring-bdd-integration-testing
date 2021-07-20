insert into film_genre (id, name)
values (1, 'Action film'),
       (2, 'Adventure film'),
       (3, 'Animated film'),
       (4, 'Comedy film'),
       (5, 'Drama'),
       (6, 'Fantasy film'),
       (7, 'Horror film'),
       (8, 'Science fiction film'),
       (9, 'Thriller film'),
       (10, 'Western')
on conflict do nothing;

insert into film_crew_job (id, name)
values (1, 'Director'),
       (2, 'Producer'),
       (3, 'Executive Producer'),
       (4, 'Principal Cast'),
       (5, 'Casting Director'),
       (6, 'AD Department'),
       (7, 'Art Department'),
       (8, 'Camera Department'),
       (9, 'Set Dresser'),
       (10, 'Prop Master')
on conflict do nothing;