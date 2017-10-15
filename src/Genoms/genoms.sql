WITH 
G1L2 as (select k_sh from test.genoms where file_id = 1 AND char_length(k_sh) = 2),
G2L2 as (select k_sh from test.genoms where file_id = 2 AND char_length(k_sh) = 2),
G1L5 as (select k_sh from test.genoms where file_id = 1 AND char_length(k_sh) = 5),
G2L5 as (select k_sh from test.genoms where file_id = 2 AND char_length(k_sh) = 5),
G1L9 as (select k_sh from test.genoms where file_id = 1 AND char_length(k_sh) = 9),
G2L9 as (select k_sh from test.genoms where file_id = 2 AND char_length(k_sh) = 9)

select
(select count(*) from 
	(select * from
		(select * from G1L2) t1
		INTERSECT 
		(select * from G2L2)
	) t2
)::numeric / 
(select count(*) from 
	(select * from
		(select * from G1L2) t3
		UNION 
		(select * from G2L2)
	) t4
)::numeric as k_shingle_2,
(select count(*) from 
	(select * from
		(select * from G1L5) t1
		INTERSECT 
		(select * from G2L5)
	) t2
)::numeric / 
(select count(*) from 
	(select * from
		(select * from G1L5) t3
		UNION 
		(select * from G2L5)
	) t4
)::numeric as k_shingle_5,
(select count(*) from 
	(select * from
		(select * from G1L9) t1
		INTERSECT 
		(select * from G2L9)
	) t2
)::numeric / 
(select count(*) from 
	(select * from
		(select * from G1L9) t3
		UNION 
		(select * from G2L9)
	) t4
)::numeric as k_shingle_9