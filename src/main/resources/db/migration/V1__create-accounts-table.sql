CREATE TABLE Accounts (
    Account_ID SERIAL PRIMARY KEY,
    Document_Number VARCHAR(11) NOT NULL,
    Created TIMESTAMP DEFAULT NOW()
);