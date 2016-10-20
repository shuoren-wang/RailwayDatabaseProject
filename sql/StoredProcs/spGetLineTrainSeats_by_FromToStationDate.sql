CREATE PROCEDURE spGetLineTrainSeats_by_FromToStationDate
(IN
fromStationId INT,
toStationId INT,
travelDate DATE
)

SELECT
	lt.`Date`,
	lt.`DayOfWeek`,
	lt.LineName,
	lt.TrainNumber,
	s.Class,
	s.TotalSeats,
	s.SoldTicketsCount,
	s.AvailableSeats
FROM
(
SELECT
	travelDate AS `Date`,
	DAYOFWEEK(travelDate) AS `DayOfWeek`,
	l.linename,
	t.trainNumber
FROM
	trains t
	INNER JOIN line l ON t.runsline_id = l.id
WHERE
	runsLine_ID IN
	(
	/*returns LineIds of lines that contain both fromStation and toStation*/
	SELECT
		lineId
	FROM
		vwAllLinesAndStations ls
	WHERE
		stationId = fromStationId /*from station*/
		AND
		lineid IN (SELECT lineId FROM
			vwAllLinesAndStations
			WHERE stationId = toStationId /*to station*/
			AND arrivalTime > ls.arrivalTime /*confirms toStation arrives later than fromStation*/
		)
	)
	AND ( /* check train runs on given date */
	(DAYOFWEEK(travelDate)=1 AND t.runs_SU=1) /*given date is sunday and train runs on sunday*/
	OR (DAYOFWEEK(travelDate)=2 AND t.runs_M=1)
	OR (DAYOFWEEK(travelDate)=3 AND t.runs_T=1)
	OR (DAYOFWEEK(travelDate)=4 AND t.runs_W=1)
	OR (DAYOFWEEK(travelDate)=5 AND t.runs_TH=1)
	OR (DAYOFWEEK(travelDate)=6 AND t.runs_F=1)
	OR (DAYOFWEEK(travelDate)=7 AND t.runs_S=1))
)lt
INNER JOIN
(
	SELECT travelDate AS `Date`,
	ts.TrainNumber,
	ts.class,
	CASE WHEN a.SoldTicketsCount IS NULL THEN 0
	ELSE a.SoldTicketsCount END AS SoldTicketsCount
	,
	ts.TotalSeats,
	ts.TotalSeats -
	CASE WHEN a.SoldTicketsCount IS NULL THEN 0
	ELSE a.SoldTicketsCount END AS AvailableSeats
	FROM
		(SELECT
		departuredate, fortrain_number AS trainnumber, s.class, COUNT(id) AS SoldTicketsCount

			FROM tickets t
			INNER JOIN seats s ON s.seatnumber = t.forseat_number AND s.trainnumber = t.fortrain_number
			WHERE t.departureDate = @travelDate

		GROUP BY t.fortrain_number, s.class) a
	RIGHT JOIN
		(SELECT s.trainnumber, s.class, COUNT(s.seatnumber) AS TotalSeats FROM seats s
		GROUP BY s.trainnumber, s.class) ts ON ts.trainnumber = a.trainnumber AND ts.class=a.class
)s
ON lt.trainnumber = s.trainnumber
