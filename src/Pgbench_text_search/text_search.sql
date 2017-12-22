CREATE TABLE test.text_table(
  id serial NOT NULL,
  content text,
  title character varying,
  ts_vec tsvector,
  CONSTRAINT text_table_pkey PRIMARY KEY (id)
);

CREATE INDEX text_idx
  ON test.text_table
  USING gin
  (to_tsvector('russian'::regconfig, content));

BEGIN;
select id, title from test.text_table
where ts_vec @@ to_tsquery('russian', 
(select string_agg(name, ' & ') from
	(select name from test.words
		order by random()
		limit (select trunc(random()*10))) r_w));
END;
