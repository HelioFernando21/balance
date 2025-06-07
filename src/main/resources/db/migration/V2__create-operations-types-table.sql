CREATE TABLE OperationsTypes (
    OperationType_ID SERIAL PRIMARY KEY,
    Description VARCHAR(100) NOT NULL,
    Created TIMESTAMP DEFAULT NOW()
);