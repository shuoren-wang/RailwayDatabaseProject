
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyStation$$
CREATE PROCEDURE spModifyStation
(IN
in_stationId INT,
in_employeeid INT,
in_address VARCHAR(20),
in_stationname VARCHAR(30),
in_isActive BOOL
)


BEGIN


UPDATE stations
SET
	`Address` = in_address,
	`StationName` = in_stationname,
	`UpdatedBy_EmployeeID` = in_employeeid,
	`Active` = in_isactive

WHERE
	`ID` = in_stationid ;


SELECT 1;

END$$

DELIMITER ;
