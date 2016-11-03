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
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);

CREATE TABLE Line(
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	LineName VARCHAR(50) NOT NULL,
	CreatedBy_EmployeeID INT NOT NULL,
	Active BOOL NOT NULL DEFAULT 1,
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
);

CREATE TABLE LineStops(
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ArrivalTime TIME NOT NULL,
	StopsForDuration TIME NOT NULL DEFAULT '00:05:00',
	STATUS BOOL NOT NULL DEFAULT 1,
	LocatedStation_ID INT NOT NULL,
	ForLine_ID INT NOT NULL,
	FOREIGN KEY (LocatedStation_ID) REFERENCES Stations(ID),
	FOREIGN KEY (ForLine_ID ) REFERENCES Line(ID)
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
	FOREIGN KEY (TrainType_ID) REFERENCES TrainTypes(ID),
	FOREIGN KEY (RunsLine_ID) REFERENCES Line(ID),
	FOREIGN KEY (CreatedBy_EmployeeID) REFERENCES Clerks(EmployeeID)
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