UPDATE ${sentences.table} AS fk LEFT JOIN ${documents.table} AS pk ON fk.${sentences.documentid} = pk.${documents.documentid}SET fk.${sentences.documentid} = NULLWHERE fk.${sentences.documentid} IS NOT NULL AND pk.${documents.documentid} IS NULL;
