CREATE TABLE IF NOT EXISTS ${annosets.table} (
${annosets.annosetid} INTEGER NOT NULL,
${annosets.sentenceid} INTEGER NOT NULL,
${annosets.frameid} INTEGER DEFAULT NULL,
${annosets.luid} INTEGER DEFAULT NULL,
${annosets.cxnid} INTEGER DEFAULT NULL
);
