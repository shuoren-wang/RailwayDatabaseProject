DELIMITER $$

DROP PROCEDURE IF EXISTS spViewLineStops$$
CREATE PROCEDURE spViewLineStops(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	ls.id,
	ls.arrivaltime,
	ls.stopsforduration,
	ls.status,
	ls.locatedstation_id,
	s.stationname,
	ls.forline_id,
	l.linename,
	ls.createdby_employeeid,
	ls.updatedby_employeeid,
	cru.name AS CreatedByEmployee_UserName,
	upu.name AS UpdatedByEmployee_UserName
FROM 	linestops ls
	INNER JOIN stations s ON s.id = ls.locatedstation_id
	INNER JOIN line l ON l.id = ls.forline_id
	INNER JOIN clerks crc ON crc.employeeid = l.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = l.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN ls.id
		WHEN in_orderby = 'time' THEN ls.arrivaltime
		WHEN in_orderby = 'duration' THEN ls.stopsforduration
		WHEN in_orderby = 'stationname' THEN s.stationname
		WHEN in_orderby = 'linename' THEN l.linename
		WHEN in_orderby = 'stationid' THEN s.id
		WHEN in_orderby = 'lineid' THEN l.id
		ELSE ls.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;
