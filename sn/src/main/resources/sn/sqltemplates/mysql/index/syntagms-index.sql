ALTER TABLE `sn_syntagms` ADD CONSTRAINT `pk_@{syntagms.table}` PRIMARY KEY (${syntagms.syntagmid});
ALTER TABLE `sn_syntagms` ADD KEY `k_@{syntagms.table}_@{syntagms.synset1id}_@{syntagms.word1id}` (${syntagms.synset1id},${syntagms.word1id});
ALTER TABLE `sn_syntagms` ADD KEY `k_@{syntagms.table}_@{syntagms.synset2id}_@{syntagms.word2id}` (${syntagms.synset2id},${syntagms.word2id});
ALTER TABLE `sn_syntagms` ADD KEY `k_@{syntagms.table}_@{syntagms.sensekey1}` (${syntagms.sensekey1});
ALTER TABLE `sn_syntagms` ADD KEY `k_@{syntagms.table}_@{syntagms.sensekey2}` (${syntagms.sensekey2});
