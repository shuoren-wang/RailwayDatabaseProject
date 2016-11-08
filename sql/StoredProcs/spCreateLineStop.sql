DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateLineStop$$
CREATE PROCEDURE spCreateLineStop
(IN
in_arrivaltime TIME,
in_stopsforduration TIME,
in_status BOOL,
in_locatedstationid INT,
in_forlineid INT
)


BEGIN


INSERT INTO linestops
	(
	`ArrivalTime`,
	`StopsForDuration`,
	`STATUS`,
	`LocatedStation_ID`,
	`ForLine_ID`
	)
SELECT
	in_arrivaltime,
	in_stopsforduration,
	in_status,
	in_locatedstationid,
	in_forlineid;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
