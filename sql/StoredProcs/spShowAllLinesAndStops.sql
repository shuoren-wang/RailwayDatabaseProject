DELIMITER $$

DROP PROCEDURE IF EXISTS spShowAllLinesAndStops$$
CREATE PROCEDURE spShowAllLinesAndStops()
SELECT
	l.linename,
	ls.ArrivalTime,
	ADDTIME(ls.arrivaltime, ls.stopsforduration) AS DepartureTime,
	s.StationName,
	s.Address AS StationAddress

FROM
	line l
	INNER JOIN linestops ls ON l.id=ls.forline_id
	INNER JOIN stations s ON ls.locatedstation_id = s.id
WHERE
	STATUS=1
ORDER BY
	Arrivaltime ASC$$

DELIMITER ;
