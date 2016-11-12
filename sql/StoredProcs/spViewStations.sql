
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewStations$$
CREATE PROCEDURE spViewStations(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	s.id,
	s.address,
	s.stationname,
	s.createdby_employeeid,
	cru.username AS CreatedByEmployee_Username,
	s.updatedby_employeeid,
	upu.username AS UpdatedByEmployee_Username,
	COUNT(DISTINCT ls.id) AS lineStopsCount,
	COUNT(DISTINCT als.id) AS ActiveLineStopsCount


FROM stations s
	INNER JOIN clerks crc ON crc.employeeid = s.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = s.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN linestops ls ON ls.locatedstation_id = s.id
	LEFT JOIN linestops als ON als.locatedstation_id = s.id AND als.status = 1
GROUP BY
	s.id,
	s.address,
	s.stationname,
	s.createdby_employeeid,
	cru.username,
	s.updatedby_employeeid,
	upu.username

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN s.id
		WHEN in_orderby = 'name' THEN s.stationname
		WHEN in_orderby = 'address' THEN s.address
		WHEN in_orderby = 'lsCount' THEN COUNT(DISTINCT ls.id)
		WHEN in_orderby = 'lsCountActive' THEN COUNT(DISTINCT als.id)
		ELSE s.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
