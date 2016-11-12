DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateStation$$
CREATE PROCEDURE spCreateStation
(IN
in_employeeid INT,
in_address VARCHAR(200),
in_stationName VARCHAR(30),
in_active BOOL
)


BEGIN

INSERT INTO stations
	(
	`Address`,
	`StationName`,
	`CreatedBy_EmployeeID`,
	`Active`
	)

SELECT
	in_address,
	in_stationname,
	in_employeeid,
	in_active;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
