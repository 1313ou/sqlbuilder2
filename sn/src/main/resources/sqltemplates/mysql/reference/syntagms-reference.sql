-- ALTER TABLE ${syntagms.table} ADD CONSTRAINT fk_${syntagms.table}_${syntagms.word1id} FOREIGN KEY (${syntagms.word1id}) REFERENCES ${wnwords.table} (${wnwords.wordid});
-- ALTER TABLE ${syntagms.table} ADD CONSTRAINT fk_${syntagms.table}_${syntagms.word2id} FOREIGN KEY (${syntagms.word2id}) REFERENCES ${wnwords.table} (${wnwords.wordid});
-- ALTER TABLE ${syntagms.table} ADD CONSTRAINT fk_${syntagms.table}_${syntagms.synset1id} FOREIGN KEY (${syntagms.synset1id}) REFERENCES ${wnsynsets.table} (${wnsynsets.synsetid});
-- ALTER TABLE ${syntagms.table} ADD CONSTRAINT fk_${syntagms.table}_${syntagms.synset2id} FOREIGN KEY (${syntagms.synset2id}) REFERENCES ${wnsynsets.table} (${wnsynsets.synsetid});