/* CREATE_TABLES.sql */
CREATE TABLE Users(
	UserID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	UserName VARCHAR(50) NOT NULL UNIQUE,
	PASSWORD VARCHAR(50) NOT NULL,
	Active BOOL NOT NULL DEFAULT 1
);

CREATE TABLE Passengers(
	PassengerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	PhoneNumber VARCHAR(10),
	UserID INT NOT NULL UNIQUE,
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Clerks(
	EmployeeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	EmpPosition VARCHAR(20) NOT NULL DEFAULT 'Employee',
	UserID INT NOT NULL UNIQUE,
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Stations(
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Address VARCHAR(200) NOT NULL UNIQUE,
	StationName VARCHAR(30) NOT NULL UNIQUE,
	CreatedBy_EmployeeID INT NOT NULL,
	UpdatedBy_EmployeeID INT,
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID),
	FOREIGN KEY (UpdatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);

CREATE TABLE Line(
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	LineName VARCHAR(50) NOT NULL,
	CreatedBy_EmployeeID INT NOT NULL,
	UpdatedBy_EmployeeID INT,
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID),
	FOREIGN KEY (UpdatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);

CREATE TABLE LineStops(
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ArrivalTime TIME NOT NULL,
	StopsForDuration TIME NOT NULL DEFAULT '00:05:00',
	STATUS BOOL NOT NULL DEFAULT 1,
	LocatedStation_ID INT NOT NULL,
	ForLine_ID INT NOT NULL,
	CreatedBy_EmployeeID INT NOT NULL,
	UpdatedBy_EmployeeID INT,
	FOREIGN KEY (LocatedStation_ID) REFERENCES Stations(ID),
	FOREIGN KEY (ForLine_ID ) REFERENCES Line(ID),
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID),
	FOREIGN KEY (UpdatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);


CREATE TABLE TrainTypes (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Color VARCHAR(30) NOT NULL DEFAULT 'White'
);

 CREATE TABLE Trains (
	TrainNumber INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Runs_M BOOL NOT NULL DEFAULT 0,
	Runs_T BOOL NOT NULL DEFAULT 0,
	Runs_W BOOL NOT NULL DEFAULT 0,
	Runs_TH BOOL NOT NULL DEFAULT 0,
	Runs_F BOOL NOT NULL DEFAULT 0,
	Runs_S BOOL NOT NULL DEFAULT 0,
	Runs_SU BOOL NOT NULL DEFAULT 0,
	TrainType_ID INT NOT NULL,
	RunsLine_ID INT NOT NULL,
	CreatedBy_EmployeeID INT NOT NULL,
	UpdatedBy_EmployeeID INT,
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (TrainType_ID) REFERENCES TrainTypes(ID),
	FOREIGN KEY (RunsLine_ID) REFERENCES Line(ID),
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID),
	FOREIGN KEY (UpdatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);

CREATE TABLE Seats (
	SeatNumber INT NOT NULL,
	TrainNumber INT NOT NULL,
	Class VARCHAR(30) NOT NULL DEFAULT 'Economy',
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (TrainNumber) REFERENCES Trains(TrainNumber),
	PRIMARY KEY (TrainNumber, SeatNumber)
);

CREATE TABLE Tickets (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DepartureDate DATE NOT NULL,
	FromLineStop_ID INT NOT NULL,
	ToLineStop_ID INT NOT NULL,
	Owner_ID INT NOT NULL,
	ForSeat_Number INT NOT NULL,
	ForTrain_Number INT NOT NULL,
	Returned BOOL NOT NULL DEFAULT 0,
	FOREIGN KEY (FromLineStop_ID) REFERENCES LineStops(ID),
	FOREIGN KEY (ToLineStop_ID) REFERENCES LineStops(ID),
	FOREIGN KEY (Owner_ID) REFERENCES Users(UserID),
	FOREIGN KEY (ForTrain_Number,ForSeat_Number) REFERENCES Seats(TrainNumber,SeatNumber)
);


/* TEST_DATA.sql */
-- Users
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('1', 'John Doe', 'testuser1', 'user1password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('2', 'David McFake', 'testuser2', 'user2password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('3', 'Donald Trump', 'testuser3', 'user3password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('4', 'Winnie Poo', 'testuser4', 'user4password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('5', 'Chicken Nugget', 'testuser5', 'user5password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('6', 'Hewrett Packard', 'testuser6', 'user6password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('7', 'Steven Jobs', 'testuser7', 'user7password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('8', 'Mike NoJobs', 'testuser8', 'user8password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('9', 'Foo Bar', 'testuser9', 'user9password');
INSERT INTO Users (`UserID`, `Name`, `UserName`, `Password`) VALUES ('10', 'My Name', 'testuser10', 'user10password');



-- Clerks
INSERT INTO Clerks (`EmployeeID`, `EmpPosition`, `UserID`) VALUES ('1', 'Manager', '1');
INSERT INTO Clerks (`EmployeeID`, `EmpPosition`, `UserID`) VALUES ('2', 'Intern', '2');
INSERT INTO Clerks (`EmployeeID`, `EmpPosition`, `UserID`) VALUES ('3', 'Clerk', '3');
INSERT INTO Clerks (`EmployeeID`, `EmpPosition`, `UserID`) VALUES ('4', 'Clerk', '4');
INSERT INTO Clerks (`EmployeeID`, `EmpPosition`, `UserID`) VALUES ('5', 'Clerk', '5');



-- Passengers
INSERT INTO Passengers(`PassengerID`, `PhoneNumber`, `UserID`)VALUES('1', '6041112222', '6');
INSERT INTO Passengers(`PassengerID`, `PhoneNumber`, `UserID`)VALUES('2', '6041234567', '7');
INSERT INTO Passengers(`PassengerID`, `PhoneNumber`, `UserID`)VALUES('3', '6041234567', '8');
INSERT INTO Passengers(`PassengerID`, `PhoneNumber`, `UserID`)VALUES('4', '7787787788', '9');
INSERT INTO Passengers(`PassengerID`, `PhoneNumber`, `UserID`)VALUES('5', '7789995555', '10');



-- Stations
INSERT INTO Stations (`ID`, `Address`, `StationName`, `CreatedBy_EmployeeID`)VALUES('1', '1234 Fake street, Surrey, BC, Canada', 'Surrey', '1');
INSERT INTO Stations (`ID`, `Address`, `StationName`, `CreatedBy_EmployeeID`)VALUES('2', '888 Real Street, Vancouver, BC, Canada', 'Vancouver', '2');
INSERT INTO Stations (`ID`, `Address`, `StationName`, `CreatedBy_EmployeeID`)VALUES('3', '945 No Ave, Calgary, AB, Canada', 'Calgary', '3');
INSERT INTO Stations (`ID`, `Address`, `StationName`, `CreatedBy_EmployeeID`)VALUES('4', 'some LA address', 'LA', '2');
INSERT INTO Stations (`ID`, `Address`, `StationName`, `CreatedBy_EmployeeID`)VALUES('5', '1 First Ave, London, UK', 'London', '1');


-- Line
INSERT INTO Line(`ID`, `LineName`, `CreatedBy_EmployeeID`)VALUES('1', 'Prime', '2');
INSERT INTO Line(`ID`, `LineName`, `CreatedBy_EmployeeID`)VALUES('2', 'Yellow', '3');
INSERT INTO Line(`ID`, `LineName`, `CreatedBy_EmployeeID`)VALUES('3', 'Express', '4');
INSERT INTO Line(`ID`, `LineName`, `CreatedBy_EmployeeID`)VALUES('4', 'Expo', '5');
INSERT INTO Line(`ID`, `LineName`, `CreatedBy_EmployeeID`)VALUES('5', 'Millenium', '5');


-- LineStops
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`, `CreatedBy_EmployeeID`)VALUES('1', '12:00:00', '1', '1', 1);
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`, `CreatedBy_EmployeeID`)VALUES('2', '13:00:00', '3', '1', 1);
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`, `CreatedBy_EmployeeID`)VALUES('3', '14:00:00', '5', '1', 1);
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`, `CreatedBy_EmployeeID`)VALUES('4', '18:00:00', '2', '1', 1);
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`, `CreatedBy_EmployeeID`)VALUES('5', '23:00:00', '4', '1', 1);



-- TrainTypes
INSERT INTO TrainTypes(`ID`, `Color`)VALUES(1, 'Red');
INSERT INTO TrainTypes(`ID`, `Color`)VALUES(2, 'Blue');
INSERT INTO TrainTypes(`ID`, `Color`)VALUES(3, 'White');
INSERT INTO TrainTypes(`ID`, `Color`)VALUES(4, 'Grey');
INSERT INTO TrainTypes(`ID`, `Color`)VALUES(5, 'Black');



-- Trains
INSERT INTO Trains(`TrainNumber`, `Runs_M`, `Runs_T`, `Runs_W`, `Runs_TH`, `Runs_F`, `Runs_S`, `Runs_SU`, `TrainType_ID`, `RunsLine_ID`, `CreatedBy_EmployeeID`)
VALUES('1', '1', '1', '1', '1', '1', '1', '1', '2', '1', '3');
INSERT INTO Trains(`TrainNumber`, `Runs_M`, `Runs_T`, `Runs_W`, `Runs_TH`, `Runs_F`, `Runs_S`, `Runs_SU`, `TrainType_ID`, `RunsLine_ID`, `CreatedBy_EmployeeID`)
VALUES('2', '1', '1', '1', '1', '1', '1', '0', '2', '1', '3');
INSERT INTO Trains(`TrainNumber`, `Runs_M`, `Runs_T`, `Runs_W`, `Runs_TH`, `Runs_F`, `Runs_S`, `Runs_SU`, `TrainType_ID`, `RunsLine_ID`, `CreatedBy_EmployeeID`)
VALUES('3', '1', '1', '1', '1', '0', '0', '0', '2', '1', '3');
INSERT INTO Trains(`TrainNumber`, `Runs_M`, `Runs_T`, `Runs_W`, `Runs_TH`, `Runs_F`, `Runs_S`, `Runs_SU`, `TrainType_ID`, `RunsLine_ID`, `CreatedBy_EmployeeID`)
VALUES('4', '1', '1', '1', '1', '0', '0', '0', '2', '1', '3');
INSERT INTO Trains(`TrainNumber`, `Runs_M`, `Runs_T`, `Runs_W`, `Runs_TH`, `Runs_F`, `Runs_S`, `Runs_SU`, `TrainType_ID`, `RunsLine_ID`, `CreatedBy_EmployeeID`)
VALUES('5', '1', '0', '0', '0', '0', '0', '0', '2', '1', '3');


-- Seats

INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('1', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('2', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('3', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('4', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('5', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('6', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('7', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('8', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('9', '1');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('1', '2');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('2', '2');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('3', '2');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('4', '2');
INSERT INTO Seats (`SeatNumber`, `TrainNumber`)VALUES('5', '2');



-- Tickets
INSERT INTO Tickets (`ID`, `DepartureDate`, `FromLineStop_ID`, `ToLineStop_ID`, `Owner_ID`, `ForSeat_Number`, `ForTrain_Number`)
VALUES('1', '2016-10-18', '1', '5', '1', '1', '1');
INSERT INTO Tickets (`ID`, `DepartureDate`, `FromLineStop_ID`, `ToLineStop_ID`, `Owner_ID`, `ForSeat_Number`, `ForTrain_Number`)
VALUES('2', '2016-10-18', '2', '5', '2', '2', '1');
INSERT INTO Tickets (`ID`, `DepartureDate`, `FromLineStop_ID`, `ToLineStop_ID`, `Owner_ID`, `ForSeat_Number`, `ForTrain_Number`)
VALUES('3', '2016-10-18', '1', '3', '3', '6', '1');
INSERT INTO Tickets (`ID`, `DepartureDate`, `FromLineStop_ID`, `ToLineStop_ID`, `Owner_ID`, `ForSeat_Number`, `ForTrain_Number`)
VALUES('4', '2016-10-17', '1', '4', '4', '1', '2');
INSERT INTO Tickets (`ID`, `DepartureDate`, `FromLineStop_ID`, `ToLineStop_ID`, `Owner_ID`, `ForSeat_Number`, `ForTrain_Number`)
VALUES('5', '2016-10-16', '1', '5', '1', '2', '2');


/* Views/ */
/* Views/vwAllLinesAndStations.sql/ */
DELIMITER $$

DROP VIEW IF EXISTS vwAllLinesAndStations$$
CREATE VIEW vwAllLinesAndStations AS

SELECT
	l.id AS lineId,
	l.linename,
	ls.id AS linestopId,
	ls.arrivalTime,
	ls.stopsforduration,
	s.id AS stationId,
	s.stationname,
	s.address
FROM
	line l
	INNER JOIN linestops ls ON l.id = ls.forline_id
	INNER JOIN stations s ON s.id = ls.locatedstation_id
WHERE
	ls.Status = 1
	AND s.Active = 1 $$

DELIMITER ;


/* StoredProcs/ */
/* StoredProcs/spChangePassword.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spChangePassword$$
CREATE PROCEDURE spChangePassword
(IN
in_userid VARCHAR(50),
in_password VARCHAR(50),
in_newpassword VARCHAR(50)
)

BEGIN

IF EXISTS (SELECT NULL FROM users WHERE userid = in_userid AND PASSWORD = in_password) THEN
	-- userid and password match
	UPDATE users SET PASSWORD = in_newpassword WHERE userid = in_userid;
	SELECT 1;
ELSE
	-- no such match
	SELECT 0;
END IF;

END$$

DELIMITER ;

/* StoredProcs/spClerkInfo.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spClerkInfo$$
CREATE PROCEDURE spClerkInfo(
IN
in_uid INT
)

BEGIN

SELECT
	u.userid,
	c.employeeid,
	u.username,
	u.name,
	c.empposition
FROM users u
	INNER JOIN clerks c ON u.userid = c.userid

WHERE u.userid = in_uid;

END$$

DELIMITER ;

/* StoredProcs/spCreateLine.sql/ */
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

/* StoredProcs/spCreateLineStop.sql/ */
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

/* StoredProcs/spCreateStation.sql/ */
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

/* StoredProcs/spCreateTrain.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateTrain$$


CREATE PROCEDURE spCreateTrain
(IN
in_employeeId INT,
in_runsM BOOL,
in_runsT BOOL,
in_runsW BOOL,
in_runsTH BOOL,
in_runsF BOOL,
in_runsS BOOL,
in_runsSU BOOL,
in_TrainTypeId INT,
in_LineId INT
)


BEGIN


INSERT INTO trains
(
	`Runs_M`,
	`Runs_T`,
	`Runs_W`,
	`Runs_TH`,
	`Runs_F`,
	`Runs_S`,
	`Runs_SU`,
	`TrainType_ID`,
	`RunsLine_ID`,
	`CreatedBy_EmployeeID`
)

SELECT
	in_runsM,
	in_runsT,
	in_runsW,
	in_runsTH,
	in_runsF,
	in_runsS,
	in_runsSU,
	in_trainTypeId,
	in_lineId,
	in_employeeid;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;

/* StoredProcs/spCreateTrainType.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateTrainType$$
CREATE PROCEDURE spCreateTrainType
(IN
in_color VARCHAR(30)
)


BEGIN

INSERT INTO `traintypes`
	(
	`Color`
	)

SELECT
	in_color;

SELECT LAST_INSERT_ID();

END$$

DELIMITER ;

/* StoredProcs/spGetLineTrainSeats_by_FromToStationDate.sql/ */
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

/* StoredProcs/spLogin.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spLogin$$
CREATE PROCEDURE spLogin
(IN
in_username VARCHAR(50),
in_password VARCHAR(50)
)


SELECT u.userid,
	CASE WHEN EXISTS(SELECT NULL FROM clerks c WHERE c.userid = u.userid) THEN 1

	ELSE 0
	END AS UserType
FROM users u
WHERE
	`username` = in_username AND `password` = in_password$$

DELIMITER ;

/* StoredProcs/spModifyLine.sql/ */

DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyLine$$
CREATE PROCEDURE spModifyLine
(IN
in_employeeId INT,
in_lineid INT,
in_lineName VARCHAR(50),
in_isActive BOOL
)


BEGIN


UPDATE line
SET
	`LineName` = in_linename,
	`UpdatedBy_EmployeeID` = in_employeeId,
	`Active` = in_isActive
WHERE
	`ID` = in_lineid;

SELECT 1;

END$$

DELIMITER ;

/* StoredProcs/spModifyLineStop.sql/ */

DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyLineStop$$
CREATE PROCEDURE spModifyLineStop
(IN
in_employeeid INT,
in_linestopid INT,
in_stopsforduration TIME,
in_status BOOL
)


BEGIN


UPDATE linestops
SET
	`StopsForDuration` = in_stopsforduration,
	`STATUS` = in_status,
	`UpdatedBy_EmployeeID` = in_employeeid

WHERE
	`ID` = in_linestopid ;


SELECT 1;

END$$

DELIMITER ;

/* StoredProcs/spModifyStation.sql/ */

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

/* StoredProcs/spModifyTrain.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyTrain$$
CREATE PROCEDURE spModifyTrain
(IN
in_trainNo INT,
in_employeeId INT,
in_Active BOOL
)


BEGIN


UPDATE trains
SET
	`Active` = in_Active,
	`UpdatedBy_EmployeeID` = in_employeeId

WHERE
	`TrainNumber` = in_trainno;

SELECT 1;

END$$

DELIMITER ;

/* StoredProcs/spModifyTrainType.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyTrainType$$
CREATE PROCEDURE spModifyTrainType
(IN
in_id INT,
in_color VARCHAR(30)
)


BEGIN


UPDATE traintypes
SET
	color = in_color

WHERE
	`ID` = in_id ;


SELECT 1;

END$$

DELIMITER ;

/* StoredProcs/spModifyUser.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spModifyUser$$

CREATE PROCEDURE spModifyUser
(IN
in_UserId INT,
in_UserName VARCHAR(50),
in_Name VARCHAR(50),
in_PhoneNumber VARCHAR(10),
in_Position VARCHAR(20)
)


BEGIN

DECLARE `_rollback` BOOL DEFAULT 0;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;

-- wrap in transaction to prevent partial error
START TRANSACTION;


UPDATE users SET username = in_username, `name` = in_name WHERE userid = in_userid;

IF EXISTS(SELECT NULL FROM clerks WHERE userid = in_userId) THEN
	UPDATE clerks SET empposition = in_position WHERE userid = in_userid;
ELSE
	UPDATE passengers SET phonenumber = in_phonenumber WHERE userid = in_userid;
END IF;


IF `_rollback` THEN
	ROLLBACK;
	SELECT 0;
ELSE
	COMMIT;

	SELECT
		u.userid,
		u.username,
		u.name,
		p.phonenumber,
		e.empposition
	FROM users u
	LEFT JOIN passengers p ON p.userid = u.userid
	LEFT JOIN clerks e ON e.userid = u.userid
	WHERE u.userid = in_userid;
END IF;


END$$

DELIMITER ;

/* StoredProcs/spPassengerInfo.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spPassengerInfo$$
CREATE PROCEDURE spPassengerInfo
(IN
in_userid INT
)

SELECT u.username, u.name, p.phonenumber
FROM users u INNER JOIN passengers p ON u.userid = p.userid
WHERE u.userid = in_userid$$

DELIMITER ;

/* StoredProcs/spPasswordRecovery.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spPasswordRecovery$$
CREATE PROCEDURE spPasswordRecovery(
IN
in_uname VARCHAR(50),
in_name VARCHAR(50),
in_phone VARCHAR(10)
)

BEGIN

SELECT
	u.password
FROM users u
	INNER JOIN passengers p ON u.userid = p.userid

WHERE
	u.username = in_uname
	AND u.name = in_name
	AND p.phonenumber = in_phone;

END$$

DELIMITER ;

/* StoredProcs/spPurchase.sql/ */
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
	AND ls.status = 1
LIMIT 1;

SELECT ls.id, ls.arrivalTime
INTO toLineStopID, toLineStopTime
FROM linestops ls
WHERE ls.forline_id = in_lineid
	AND ls.locatedStation_id = in_toStationId
	AND ls.status = 1
LIMIT 1;


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

/* StoredProcs/spRegister.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spRegister$$
CREATE PROCEDURE spRegister
(IN
in_username VARCHAR(50),
in_password VARCHAR(50),
in_name VARCHAR(50),
in_phoneno VARCHAR(10),
in_empposition VARCHAR(20),
in_usertype INT
)

proc_label:BEGIN

    DECLARE `_rollback` BOOL DEFAULT 0;
    DECLARE `uid` INT;

    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `_rollback` = 1;

-- wrap in transaction to prevent partial error
START TRANSACTION;

IF EXISTS (SELECT username FROM users WHERE username = in_username) THEN
  SET `_rollback` = 1;
  SELECT 'Username Exists';
	LEAVE proc_label;
END IF;

INSERT INTO `users`
	(
	`NAME`,
	`UserName`,
	`PASSWORD`
	)
	VALUES
	(
	in_name,
	in_username,
	in_password
	);

SELECT userId INTO uid FROM users WHERE username = in_username;

IF in_usertype = 0 THEN
	INSERT INTO `passengers`
		(
			`PhoneNumber`,
			`UserID`
		)
		SELECT
			in_phoneno,
			uid;
	SELECT uid;
ELSEIF in_usertype = 1 THEN

	INSERT INTO `clerks`
		(
		`EmpPosition`,
		`UserID`
		)
		SELECT
			in_empposition,
			uid;
	SELECT uid;
ELSE
	SELECT 0 AS error;
END IF;


IF `_rollback` THEN
	ROLLBACK;
  SELECT 'Username Exists';
ELSE
	COMMIT;
  SELECT userId FROM users WHERE username = in_username;
END IF;

END$$

DELIMITER ;

/* StoredProcs/spReturnTicket.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spReturnTicket$$
CREATE PROCEDURE spReturnTicket
(IN
in_requestingUserId INT,
in_ticketID INT
)

BEGIN

IF EXISTS
	(
	SELECT NULL FROM tickets WHERE owner_id = in_requestingUserId AND id = in_ticketID
	)OR EXISTS(
	SELECT NULL FROM clerks WHERE userId = in_requestingUserId
	)
THEN
	-- return ticket
	UPDATE tickets SET returned = 1 WHERE id = in_ticketID;
	SELECT 1;
ELSE
	-- no such match
	SELECT 0;
END IF;

END$$

DELIMITER ;

/* StoredProcs/spSearchPassengerInfo.sql/ */

DELIMITER $$

DROP PROCEDURE IF EXISTS spSearchPassengerInfo$$
CREATE PROCEDURE spSearchPassengerInfo(
IN
in_uid INT
)

BEGIN

SELECT
	u.userid,
	p.passengerid,
	u.username,
	u.name,
	p.phonenumber
FROM users u
	INNER JOIN passengers p ON u.userid = p.userid

WHERE u.userid = in_uid;

END$$

DELIMITER ;

/* StoredProcs/spShowAllLinesAndStops.sql/ */
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

/* StoredProcs/spViewLines.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewLines$$
CREATE PROCEDURE spViewLines(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	l.id,
	l.linename,
	l.active,
	l.createdby_employeeid,
	l.updatedby_employeeid,
	cru.name AS CreatedByEmployee_Name,
	upu.name AS UpdatedByEmployee_Name,
	COUNT(DISTINCT tr.trainnumber) AS TrainsCount,
	COUNT(DISTINCT atr.trainnumber) AS ActiveTrainsCount,
	COUNT(DISTINCT ls.id) AS LineStopsCount,
	COUNT(DISTINCT als.id) AS ActiveLineStopsCount
FROM
	line l
	INNER JOIN clerks crc ON crc.employeeid = l.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = l.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN trains tr ON tr.runsline_id = l.id
	LEFT JOIN trains atr ON atr.runsline_id = l.id AND atr.active
	LEFT JOIN linestops ls ON ls.forline_id = l.id
	LEFT JOIN linestops als ON als.forline_id = l.id AND als.status

GROUP BY
	l.id,
	l.linename,
	l.active,
	l.createdby_employeeid,
	l.updatedby_employeeid

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN l.id
		WHEN in_orderby = 'name' THEN l.linename
		WHEN in_orderby = 'tcount' THEN	COUNT(DISTINCT tr.trainnumber)
		WHEN in_orderby = 'tcountActive' THEN	COUNT(DISTINCT atr.trainnumber)
		WHEN in_orderby = 'lscount' THEN	COUNT(DISTINCT ls.id)
		WHEN in_orderby = 'lscountActive' THEN	COUNT(DISTINCT als.id)
		ELSE l.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;

/* StoredProcs/spViewLineStops.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewLineStops$$
CREATE PROCEDURE spViewLineStops(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	ls.id,
	ls.arrivaltime,
	ls.stopsforduration,
	ls.status,
	ls.locatedstation_id,
	s.stationname,
	ls.forline_id,
	l.linename,
	ls.createdby_employeeid,
	ls.updatedby_employeeid,
	cru.name AS CreatedByEmployee_Name,
	upu.name AS UpdatedByEmployee_Name
FROM 	linestops ls
	INNER JOIN stations s ON s.id = ls.locatedstation_id
	INNER JOIN line l ON l.id = ls.forline_id
	INNER JOIN clerks crc ON crc.employeeid = l.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = l.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN ls.id
		WHEN in_orderby = 'time' THEN ls.arrivaltime
		WHEN in_orderby = 'duration' THEN ls.stopsforduration
		WHEN in_orderby = 'stationname' THEN s.stationname
		WHEN in_orderby = 'linename' THEN l.linename
		WHEN in_orderby = 'stationid' THEN s.id
		WHEN in_orderby = 'lineid' THEN l.id
		ELSE ls.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;

/* StoredProcs/spViewPassengerInfo.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewPassengerInfo$$
CREATE PROCEDURE spViewPassengerInfo(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	u.userid,
	p.passengerid,
	u.username,
	u.name,
	p.phonenumber
FROM users u
	INNER JOIN passengers p ON u.userid = p.userid

ORDER BY(
	CASE
		WHEN in_orderby = 'uid' THEN u.userid
		WHEN in_orderby = 'pid' THEN p.passengerid
		WHEN in_orderby = 'uname' THEN u.username
		WHEN in_orderby = 'name' THEN u.name
		WHEN in_orderby = 'phone' THEN p.phonenumber
		ELSE u.userid
	END)

LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;

/* StoredProcs/spViewStations.sql/ */

DELIMITER $$

DROP PROCEDURE IF EXISTS spViewStations$$
CREATE PROCEDURE spViewStations(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	s.id,
	s.address,
	s.stationname,
	s.active,
	s.createdby_employeeid,
	cru.username AS CreatedByEmployee_Username,
	s.updatedby_employeeid,
	upu.username AS UpdatedByEmployee_Username,
	COUNT(DISTINCT ls.id) AS lineStopsCount,
	COUNT(DISTINCT als.id) AS ActiveLineStopsCount


FROM stations s
	INNER JOIN clerks crc ON crc.employeeid = s.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = s.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN linestops ls ON ls.locatedstation_id = s.id
	LEFT JOIN linestops als ON als.locatedstation_id = s.id AND als.status = 1

GROUP BY
	s.id,
	s.address,
	s.stationname,
	s.createdby_employeeid,
	cru.username,
	s.updatedby_employeeid,
	upu.username

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN s.id
		WHEN in_orderby = 'name' THEN s.stationname
		WHEN in_orderby = 'address' THEN s.address
		WHEN in_orderby = 'lsCount' THEN COUNT(DISTINCT ls.id)
		WHEN in_orderby = 'lsCountActive' THEN COUNT(DISTINCT als.id)
		ELSE s.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;

/* StoredProcs/spViewTickets.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTickets$$
CREATE PROCEDURE spViewTickets
(IN
in_forUserId INT
)


BEGIN


SELECT
t.owner_Id AS forUserId,
t.ID AS ticketId,
fls.locatedStation_id AS fromStationId,
tls.locatedStation_id AS toStationId,
t.departuredate AS travelDate,
s.Class,
fls.forline_id AS lineID,
t.fortrain_number AS TrainNumber

FROM
	tickets t
	INNER JOIN linestops fls ON fls.id = t.fromlinestop_id
	INNER JOIN linestops tls ON tls.id = t.toLineStop_id
	INNER JOIN seats s ON s.seatnumber = t.forseat_number AND s.trainnumber = t.fortrain_number
WHERE owner_id = in_forUserId;



END$$

DELIMITER ;

/* StoredProcs/spViewTrains.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTrains$$
CREATE PROCEDURE spViewTrains(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	t.trainnumber,
	t.runs_m,
	t.runs_t,
	t.runs_w,
	t.runs_th,
	t.runs_f,
	t.runs_s,
	t.runs_su,
	t.traintype_id,
	tt.color,
	t.runsline_id,
	l.linename,
	t.createdby_employeeid,
	cru.name AS CreatedByEmployee_Name,
	t.updatedby_employeeid,
	t.active,
	upu.name AS UpdatedByEmployee_Name,
	COUNT(DISTINCT s.seatnumber) AS seatNumberCount,
	COUNT(DISTINCT acs.seatnumber) AS seatNumberCountActive
FROM
	trains t
	INNER JOIN traintypes tt ON tt.id = t.traintype_id
	INNER JOIN line l ON l.id = t.runsline_id
	INNER JOIN clerks crc ON crc.employeeid = t.createdby_employeeid
	INNER JOIN users cru ON cru.userid = crc.userid
	LEFT JOIN clerks upc ON upc.employeeid = t.updatedby_employeeid
	LEFT JOIN users upu ON upu.userid = upc.userid
	LEFT JOIN seats s ON s.trainnumber = t.trainnumber
	LEFT JOIN seats acs ON acs.trainnumber = t.trainnumber AND acs.active
GROUP BY
	t.trainnumber,
	t.runs_m,
	t.runs_t,
	t.runs_w,
	t.runs_th,
	t.runs_f,
	t.runs_s,
	t.runs_su,
	t.traintype_id,
	tt.color,
	t.runsline_id,
	l.linename,
	t.createdby_employeeid,
	cru.name,
	t.updatedby_employeeid,
	t.active,
	upu.name

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN t.trainnumber
		WHEN in_orderby = 'color' THEN tt.color
		WHEN in_orderby = 'lid' THEN l.id
		WHEN in_orderby = 'ttid' THEN tt.id
		WHEN in_orderby = 'seatcount' THEN COUNT(DISTINCT s.seatnumber)
		WHEN in_orderby = 'seatcountactive' THEN COUNT(DISTINCT acs.seatnumber)
		ELSE t.trainnumber
	END)

LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;

/* StoredProcs/spViewTrainTypes.sql/ */
DELIMITER $$

DROP PROCEDURE IF EXISTS spViewTrainTypes$$
CREATE PROCEDURE spViewTrainTypes(
IN
in_orderby VARCHAR(20),
in_take INT,
in_offset INT
)

BEGIN

SELECT
	t.id,
	t.color,
	COUNT(tr.trainnumber) AS TrainsCount,
	SUM(tr.active) AS ActiveTrainsCount
FROM
	traintypes t
	LEFT JOIN trains tr ON tr.traintype_id = t.id
GROUP BY t.id, t.color

ORDER BY(
	CASE
		WHEN in_orderby = 'id' THEN t.id
		WHEN in_orderby = 'color' THEN t.color
		WHEN in_orderby = 'tcount' THEN	COUNT(tr.trainnumber)
		WHEN in_orderby = 'tcountActive' THEN SUM(tr.active)
		ELSE t.id
	END)
LIMIT in_take
OFFSET in_offset;

END$$

DELIMITER ;


