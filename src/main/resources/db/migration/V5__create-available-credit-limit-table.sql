CREATE TABLE AvailableCreditLimits (
    Account_ID INTEGER NOT NULL PRIMARY KEY,
    Available_Credit_Limit NUMERIC(15, 2) NOT NULL,
    FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID) ON DELETE CASCADE
);