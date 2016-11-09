DELIMITER $$

DROP PROCEDURE IF EXISTS spViewLines$$
CREATE PROCEDURE spViewLines(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	l.id,
	l.linename,
	l.active,
	l.createdby_employeeid,
	l.updatedby_employeeid,
	cru.name AS CreatedByEmployee_UserName,
	upu.name AS UpdatedByEmployee_UserName,
	COUNT(DISTINCT tr.trainnumber) AS TrainsCount,
	COUNT(DISTINCT atr.trainnumber) AS ActiveTrainsCount,
	COUNT(DISTINCT ls.id) AS LineStopsCount,
	COUNT(DISTINCT als.id) AS ActiveLineStopsCount
FROM
	line l
	INNER JOIN clerks crc ON crc.employeeid = l.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = l.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN trains tr ON tr.runsline_id = l.id
	LEFT JOIN trains atr ON atr.runsline_id = l.id AND atr.active
	LEFT JOIN linestops ls ON ls.forline_id = l.id
	LEFT JOIN linestops als ON als.forline_id = l.id AND als.status

GROUP BY
	l.id,
	l.linename,
	l.active,
	l.createdby_employeeid,
	l.updatedby_employeeid

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN l.id
		WHEN in_orderby = 'name' THEN l.linename
		WHEN in_orderby = 'tcount' THEN	COUNT(DISTINCT tr.trainnumber)
		WHEN in_orderby = 'tcountActive' THEN	COUNT(DISTINCT atr.trainnumber)
		WHEN in_orderby = 'lscount' THEN	COUNT(DISTINCT ls.id)
		WHEN in_orderby = 'lscountActive' THEN	COUNT(DISTINCT als.id)
		ELSE l.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
