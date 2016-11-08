DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateLine$$
CREATE PROCEDURE spCreateLine
(IN
in_employeeid INT,
in_linename VARCHAR(50),
in_active BOOL
)


BEGIN


INSERT INTO line
	(
	`LineName`,
	`CreatedBy_EmployeeID`,
	`Active`
	)
SELECT
	in_linename,
	in_employeeid,
	in_active;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;
