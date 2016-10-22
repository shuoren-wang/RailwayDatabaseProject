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
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`)VALUES('1', '12:00:00', '1', '1');
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`)VALUES('2', '13:00:00', '3', '1');
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`)VALUES('3', '14:00:00', '5', '1');
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`)VALUES('4', '18:00:00', '2', '1');
INSERT INTO LineStops(`ID`, `ArrivalTime`, `LocatedStation_ID`, `ForLine_ID`)VALUES('5', '23:00:00', '4', '1');



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
