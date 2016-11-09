DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTrainTypes$$
CREATE PROCEDURE spViewTrainTypes(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	t.id,
	t.color,
	COUNT(tr.trainnumber) AS TrainsCount,
	SUM(tr.active) AS ActiveTrainsCount
FROM
	traintypes t
	LEFT JOIN trains tr ON tr.traintype_id = t.id
GROUP BY t.id, t.color

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN t.id
		WHEN in_orderby = 'color' THEN t.color
		WHEN in_orderby = 'tcount' THEN	COUNT(tr.trainnumber)
		WHEN in_orderby = 'tcountActive' THEN SUM(tr.active)
		ELSE t.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
