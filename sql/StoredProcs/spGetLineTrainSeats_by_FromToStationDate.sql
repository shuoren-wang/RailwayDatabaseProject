DELIMITER $$

DROP PROCEDURE IF EXISTS spGetLineTrainSeats_by_FromToStationDate$$
CREATE PROCEDURE spGetLineTrainSeats_by_FromToStationDate
(IN
fromStationId INT,
toStationId INT,
travelDate DATE
)


BEGIN

DECLARE `_dayOfWeek` INT DEFAULT DAYOFWEEK(travelDate);


-- lines connecting fromStationId to ToStationId
DROP TEMPORARY TABLE IF EXISTS _ValidLines;
CREATE TEMPORARY TABLE _ValidLines AS (
	SELECT
		l.id AS LineID,
		tr.trainnumber,
		fls.id AS FromLineStopId,
		tls.id AS ToLineStopID,
		fls.arrivaltime AS DepartureTime,
		tls.arrivaltime AS ArrivalTime
	FROM
		linestops fls
		INNER JOIN linestops tls ON tls.forline_id = fls.forline_id
		INNER JOIN line l ON l.id = fls.forline_id
		INNER JOIN trains tr ON tr.runsline_id = l.id
	WHERE
		fls.locatedStation_ID = fromStationId
		AND tls.locatedStation_ID = toStationId
		AND fls.arrivaltime < tls.arrivaltime
);

DROP TEMPORARY TABLE IF EXISTS _ValidLinesOuter;
CREATE TEMPORARY TABLE _ValidLinesOuter AS (
	SELECT * FROM _ValidLines
);

SELECT
	travelDate AS `Date`,
	_dayOfWeek AS `DayOfWeek`,
	l.LineName,
	vl.LineID,
	vl.TrainNumber,
	totalCounts.Class,
	CASE WHEN soldCounts.SoldTicketsCount IS NULL THEN totalCounts.TotalSeats
	ELSE
		CASE WHEN totalCounts.TotalSeats - soldCounts.SoldTicketsCount < 0 THEN 0
		ELSE totalCounts.TotalSeats - soldCounts.SoldTicketsCount END
	END AS AvailableSeats,
	vl.DepartureTime,
	vl.ArrivalTime
FROM
	_ValidLinesOuter vl
	INNER JOIN (
		SELECT trainnumber, COUNT(seatnumber) AS TotalSeats, Class
		FROM seats
		WHERE active = 1
		GROUP BY trainnumber, Class
	) totalCounts ON vl.TrainNumber = totalCounts.TrainNumber
	INNER JOIN line l ON l.id = vl.lineid
	INNER JOIN trains tr ON tr.trainnumber = vl.trainnumber

	LEFT JOIN (
		SELECT
			soldTickets.TrainNumber,
			soldTickets.Class,
			COUNT(soldTickets.seatNo) AS SoldTicketsCount
		FROM (
			SELECT
				t.forseat_number AS seatNo,
				vl.trainnumber AS TrainNumber,
				s.Class
			FROM tickets t
				INNER JOIN _ValidLines vl ON vl.trainnumber = t.fortrain_number
				INNER JOIN seats s ON s.seatnumber = t.forseat_number AND s.trainnumber = t.fortrain_number
				LEFT JOIN linestops fromls ON fromls.id = t.fromlinestop_id
				LEFT JOIN linestops tols ON tols.id = t.tolinestop_id
			WHERE
				t.returned = 0
				AND t.departuredate = travelDate
				-- check if duration of trips overlap
				AND (
					fromls.arrivaltime < vl.ArrivalTime
					AND tols.arrivaltime > vl.DepartureTime
				)
		) soldTickets
	GROUP BY soldTickets.Class, soldTickets.TrainNumber
	) soldCounts ON soldCounts.trainnumber = vl.trainnumber AND soldCounts.Class = totalCounts.Class

WHERE
	/* check train runs on given date */
	(_dayOfWeek=1 AND tr.runs_SU=1) /*given date is sunday and train runs on sunday*/
	OR (_dayOfWeek=2 AND tr.runs_M=1)
	OR (_dayOfWeek=3 AND tr.runs_T=1)
	OR (_dayOfWeek=4 AND tr.runs_W=1)
	OR (_dayOfWeek=5 AND tr.runs_TH=1)
	OR (_dayOfWeek=6 AND tr.runs_F=1)
	OR (_dayOfWeek=7 AND tr.runs_S=1)

;

DROP TEMPORARY TABLE IF EXISTS _ValidLines;
DROP TEMPORARY TABLE IF EXISTS _ValidLinesOuter;


END$$

DELIMITER ;
