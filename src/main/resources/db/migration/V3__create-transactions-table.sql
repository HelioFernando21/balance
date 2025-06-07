CREATE TABLE Transactions (
    Transaction_ID SERIAL PRIMARY KEY,
    Account_ID INTEGER NOT NULL,
    OperationType_ID INTEGER NOT NULL,
    Amount NUMERIC(15, 2) NOT NULL,
    EventDate TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID) ON DELETE CASCADE,
    FOREIGN KEY (OperationType_ID) REFERENCES OperationsTypes(OperationType_ID) ON DELETE CASCADE
);