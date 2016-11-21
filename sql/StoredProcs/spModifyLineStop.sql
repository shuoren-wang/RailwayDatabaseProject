
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyLineStop$$
CREATE PROCEDURE spModifyLineStop
(IN
in_employeeid INT,
in_linestopid INT,
in_arrivaltime TIME,
in_stopsforduration TIME,
in_status BOOL,
in_locatedstationid INT,
in_forlineid INT
)


BEGIN


UPDATE linestops
SET
	`ArrivalTime` = in_arrivaltime,
	`StopsForDuration` = in_stopsforduration,
	`STATUS` = in_status,
	`LocatedStation_ID` = in_locatedstationid,
	`ForLine_ID` = in_forlineid,
	`UpdatedBy_EmployeeID` = in_employeeid

WHERE
	`ID` = in_linestopid ;


SELECT 1;

END$$

DELIMITER ;
