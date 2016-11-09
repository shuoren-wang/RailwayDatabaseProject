DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateLineStop$$
CREATE PROCEDURE spCreateLineStop
(IN
in_employeeID INT,
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
	`ForLine_ID`,
	`CreatedBy_EmployeeID`
	)
SELECT
	in_arrivaltime,
	in_stopsforduration,
	in_status,
	in_locatedstationid,
	in_forlineid,
	in_employeeID;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
