DELIMITER $$

DROP VIEW IF EXISTS vwAllLinesAndStations$$
CREATE VIEW vwAllLinesAndStations AS

SELECT
	l.id AS lineId,
	l.linename,
	ls.id AS linestopId,
	ls.arrivalTime,
	ls.stopsforduration,
	s.id AS stationId,
	s.stationname,
	s.address
FROM
	line l
	INNER JOIN linestops ls ON l.id = ls.forline_id
	INNER JOIN stations s ON s.id = ls.locatedstation_id$$

DELIMITER ;
