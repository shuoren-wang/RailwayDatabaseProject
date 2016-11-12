DELIMITER $$

DROP PROCEDURE IF EXISTS spPurchase$$
CREATE PROCEDURE spPurchase
(IN
in_forUserId INT,
in_fromStationId INT,
in_toStationId INT,
in_travelDate DATE,
in_SeatType VARCHAR(30),
in_LineId INT,
in_TrainNumber INT
)

proc_label:BEGIN

	DECLARE `_rollback` BOOL DEFAULT 0;
	DECLARE `_errMsg` VARCHAR(30);

	-- variables needed
	DECLARE `fromLineStopId` INT;
	DECLARE `toLineStopId` INT;
	DECLARE `fromLineStopTime` TIME;
	DECLARE `toLineStopTime` TIME;

	DECLARE `seatNo` INT;

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;


-- wrap in transaction to prevent partial error
START TRANSACTION;

-- get variables
SELECT ls.id, ls.arrivalTime
INTO fromLineStopID, fromLineStopTime
FROM linestops ls
WHERE ls.forline_id = in_lineid
	AND ls.locatedStation_id = in_fromStationId
	AND ls.status = 1;

SELECT ls.id, ls.arrivalTime
INTO toLineStopID, toLineStopTime
FROM linestops ls
WHERE ls.forline_id = in_lineid
	AND ls.locatedStation_id = in_toStationId
	AND ls.status = 1;


IF NULL IN (fromLineStopID, fromLineStopTime, toLineStopID, toLineStopTime) THEN
	SET `_rollback` = 1;
	SET `_errMsg` = 'Station Invalid';
END IF;

IF NOT EXISTS(SELECT NULL FROM passengers WHERE userID = in_forUserId) THEN
	SET `_rollback` = 1;
	SET `_errMsg` = 'User is not Passenger';
END IF;

IF (fromLineStopTime > toLineStopTime) THEN
	SET `_rollback` = 1;
	SET `_errMsg` = 'Station Invalid (2)';
END IF;


-- valid seatnumbers
DROP TEMPORARY TABLE IF EXISTS _ValidSeatNumbers;
CREATE TEMPORARY TABLE _ValidSeatNumbers AS (
	SELECT
		s.seatnumber
	FROM
		seats s
		INNER JOIN  trains t ON s.trainnumber = t.trainnumber
		INNER JOIN line l ON l.id = t.runsline_id
	WHERE
		s.class = in_seattype
		AND t.trainnumber=in_trainnumber
		AND l.id = in_lineid
		AND s.Active = 1
);


-- remove taken seats
DELETE
FROM
	_ValidSeatNumbers
WHERE seatnumber IN (
	SELECT t.forseat_number
	FROM tickets t
	INNER JOIN seats s ON s.seatnumber = t.forseat_number AND s.trainnumber = t.fortrain_number
	LEFT JOIN linestops fromls ON fromls.id = t.fromlinestop_id
	LEFT JOIN linestops tols ON tols.id = t.tolinestop_id
	WHERE
		t.returned = 0
		AND s.class = in_seattype
		AND fortrain_number = in_trainnumber
		AND departuredate = in_travelDate
		-- check if duration of trips overlap
		AND (
			fromls.arrivaltime < toLineStopTime
			AND tols.arrivaltime > fromLineStopTime
		)
);

IF NOT EXISTS (SELECT NULL FROM _ValidSeatNumbers) THEN
	SET `_rollback` = 1;
	SET `_errMsg` = 'No Seats Available';
END IF;

SET seatNo = (SELECT seatnumber FROM _ValidSeatNumbers LIMIT 1);

INSERT INTO `tickets`
	(
	`DepartureDate`,
	`FromLineStop_ID`,
	`ToLineStop_ID`,
	`Owner_ID`,
	`ForSeat_Number`,
	`ForTrain_Number`
	)
	VALUES
	(
	in_travelDate,
	fromLineStopId,
	toLineStopId,
	in_forUserId,
	seatNo,
	in_TrainNumber
	);




-- clean up temporary tables
DROP TEMPORARY TABLE IF EXISTS _ValidSeatNumbers;

IF `_rollback` THEN
	SELECT _errMsg AS 'Error';
	ROLLBACK;
ELSE
	COMMIT;
	SELECT LAST_INSERT_ID();
END IF;

END$$

DELIMITER ;
