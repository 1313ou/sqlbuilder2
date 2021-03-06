CREATE TABLE IF NOT EXISTS ${sentences.table} (
    ${sentences.sentenceid} INTEGER NOT NULL,
    ${sentences.documentid} INTEGER DEFAULT NULL,
    ${sentences.corpusid} INTEGER DEFAULT NULL,
    ${sentences.paragno} INTEGER,
    ${sentences.sentno} INTEGER,
    ${sentences.text} TEXT,
    ${sentences.apos} INTEGER,
    ${sentences.noccurs} INTEGER DEFAULT 1
);
