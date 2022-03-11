package provider;

public class WnQueries {

static public class WORDS {
	static public final String TABLE = "`words`";
}

static public class WORD {
	static public final String TABLE = "`words`";
}

static public class SENSES {
	static public final String TABLE = "`senses`";
}

static public class SENSE {
	static public final String TABLE = "`senses`";
}

static public class SYNSETS {
	static public final String TABLE = "`synsets`";
}

static public class SYNSET {
	static public final String TABLE = "`synsets`";
}

static public class SEMRELATIONS {
	static public final String TABLE = "synsets_synsets";
}

static public class LEXRELATIONS {
	static public final String TABLE = "`senses_senses`";
}

static public class RELATIONS {
	static public final String TABLE = "`relations`";
}

static public class POSES {
	static public final String TABLE = "`poses`";
}

static public class DOMAINS {
	static public final String TABLE = "`domains`";
}

static public class ADJPOSITIONS {
	static public final String TABLE = "`adjpositions`";
}

static public class SAMPLES {
	static public final String TABLE = "`samples`";
}

static public class DICT {
	static public final String TABLE = "URI_PATH_SEGMENT";
}

static public class WORDS_SENSES_SYNSETS {
	static public final String TABLE = "`words` AS w LEFT JOIN `senses` AS s USING (`wordid`) LEFT JOIN `synsets` AS y USING (`synsetid`)";
}

static public class WORDS_SENSES_CASEDWORDS_SYNSETS {
	static public final String TABLE = "`words` AS w LEFT JOIN `senses` AS s USING (`wordid`) LEFT JOIN `casedwords` AS c USING (`wordid`,`casedwordid`) LEFT JOIN `synsets` AS y USING (`synsetid`)";
}

static public class WORDS_SENSES_CASEDWORDS_SYNSETS_POSTYPES_LEXDOMAINS {
	static public final String TABLE = "`words` AS w LEFT JOIN `senses` AS s USING (`wordid`) LEFT JOIN `casedwords` AS c USING (`wordid`,`casedwordid`) LEFT JOIN `synsets` AS y USING (`synsetid`) LEFT JOIN `poses` AS p USING (`posid`) LEFT JOIN `domains` AS m USING (`domainid`)";
}

static public class SENSES_WORDS {
	static public final String TABLE = "`senses` AS s LEFT JOIN `words` AS w USING (`wordid`)";
}

static public class SENSES_WORDS_BY_SYNSET {
	static public final String TABLE = "`senses` AS s LEFT JOIN `words` AS w USING (`wordid`)";
	static public final String[] PROJECTION = {"*","GROUP_CONCAT(`words`.`word`, ', ' ) AS `members`"};
	static public final String GROUPBY = "`synsetid`";
}

static public class SENSES_SYNSETS_POSES_DOMAINS {
	static public final String TABLE = "`senses` AS s INNER JOIN `synsets` AS y USING (`synsetid`) LEFT JOIN `poses` AS p USING (`posid`) LEFT JOIN `domains` AS m USING (`domainid`)";
}

static public class SYNSETS_POSES_DOMAINS {
	static public final String TABLE = "`synsets` AS y LEFT JOIN `poses` AS p USING (`posid`) LEFT JOIN `domains` AS m USING (`domainid`)";
}

static public class BASERELATIONS_SENSES_WORDS_X_BY_SYNSET {
	static public final String TABLE = "( MAKEQUERY ) AS r INNER JOIN `relations` USING (`relationid`) INNER JOIN `synsets` AS d ON r.``word2id`` = d.`synsetid` LEFT JOIN `senses` ON d.`synsetid` = `senses`.`synsetid` LEFT JOIN `words` AS w USING (`wordid`) LEFT JOIN `words` AS t ON r.``word2id`` = t.`wordid`";
	static public final String GROUPBY = "`d_synsetid`,x,`relation`,`relationid`,`d_wordid`,`#d_word`";
}

static public class SEMRELATIONS_SYNSETS {
	static public final String TABLE = "`synsets_synsets` AS r INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid`";
}

static public class SEMRELATIONS_SYNSETS_X {
	static public final String TABLE = "`synsets_synsets` AS r INNER JOIN `relations` USING (`relationid`) INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid` ";
}

static public class SEMRELATIONS_SYNSETS_WORDS_X_BY_SYNSET {
	static public final String TABLE = "`synsets_synsets` AS r INNER JOIN `relations` USING (`relationid`) INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid` LEFT JOIN `senses` ON d.`synsetid` = `senses`.`synsetid` LEFT JOIN `words` USING (`wordid`)";
	static public final String[] PROJECTION = {"*","GROUP_CONCAT(`words`.`word`, ', ' ) AS `members2`"};
	static public final String GROUPBY = "d.`synsetid`";
}

static public class LEXRELATIONS_SENSES {
	static public final String TABLE = "`senses_senses` AS r INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid` INNER JOIN `words` AS w ON r.`word2id` = w.`wordid`";
}

static public class LEXRELATIONS_SENSES_X {
	static public final String TABLE = "`senses_senses` AS r INNER JOIN `relations` USING (`relationid`) INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid` INNER JOIN `words` AS w ON r.`word2id` = w.`wordid` ";
}

static public class LEXRELATIONS_SENSES_WORDS_X_BY_SYNSET {
	static public final String TABLE = "`senses_senses` AS r INNER JOIN `relations` USING (`relationid`) INNER JOIN `synsets` AS d ON r.`synset2id` = d.`synsetid` INNER JOIN `words` AS w ON r.`word2id` = w.`wordid` LEFT JOIN `senses` AS s ON d.`synsetid` = s.`synsetid` LEFT JOIN `words` AS t USING (`wordid`)";
	static public final String[] PROJECTION = {"*","GROUP_CONCAT(DISTINCT t.`word`) AS `members2`"};
	static public final String GROUPBY = "d.`synsetid`";
}

static public class SENSES_VFRAMES {
	static public final String TABLE = "`senses_vframes` LEFT JOIN `vframes` USING (`frameid`)";
}

static public class SENSES_VTEMPLATES {
	static public final String TABLE = "`senses_vtemplates` LEFT JOIN `vtemplates` USING (`templateid`)";
}

static public class SENSES_ADJPOSITIONS {
	static public final String TABLE = "`senses_adjpositions` LEFT JOIN `adjpositions` USING (`positionid`)";
}

static public class LEXES_MORPHS {
	static public final String TABLE = "`lexes_morphs` LEFT JOIN `morphs` USING (`morphid`)";
}

static public class WORDS_LEXES_MORPHS {
	static public final String TABLE = "`words` LEFT JOIN `lexes_morphs` USING (`wordid`) LEFT JOIN `morphs` USING (`morphid`)";
}

static public class WORDS_LEXES_MORPHS_BY_WORD {
	static public final String TABLE = "`words` LEFT JOIN `lexes_morphs` USING (`wordid`) LEFT JOIN `morphs` USING (`morphid`)";
	static public final String GROUPBY = "`wordid`";
}

static public class LOOKUP_FTS_WORDS {
	static public final String TABLE = "`words`_`word`_fts4";
}

static public class LOOKUP_FTS_DEFINITIONS {
	static public final String TABLE = "`synsets`_`definition`_fts4";
}

static public class LOOKUP_FTS_SAMPLES {
	static public final String TABLE = "`samples`_`sample`_fts4";
}

static public class SUGGEST_WORDS {
	static public final String TABLE = "`words`";
	static public final String[] PROJECTION = {"`wordid` AS _id","`word` AS SearchManager.SUGGEST_COLUMN_TEXT_1","`word` AS SearchManager.SUGGEST_COLUMN_QUERY"};
	static public final String SELECTION = "`word` LIKE ? || '%'";
	static public final String[] ARGS = {"`#{uri_last}`"};
}

static public class SUGGEST_FTS_WORDS {
	static public final String TABLE = "`words`_`word`_fts4";
	static public final String[] PROJECTION = {"`wordid` AS _id","`word` AS SearchManager.SUGGEST_COLUMN_TEXT_1","`word` AS SearchManager.SUGGEST_COLUMN_QUERY"};
	static public final String SELECTION = "`word` MATCH ?";
	static public final String[] ARGS = {"`#{uri_last}`*"};
}

static public class SUGGEST_FTS_DEFINITIONS {
	static public final String TABLE = "`synsets`_`definition`_fts4";
	static public final String[] PROJECTION = {"`synsetid` AS _id","`definition` AS SearchManager.SUGGEST_COLUMN_TEXT_1","`definition` AS SearchManager.SUGGEST_COLUMN_QUERY"};
	static public final String SELECTION = "`definition` MATCH ?";
	static public final String[] ARGS = {"`#{uri_last}`*"};
}

static public class SUGGEST_FTS_SAMPLES {
	static public final String TABLE = "`samples`_`sample`_fts4";
	static public final String[] PROJECTION = {"`sampleid` AS _id","`sample` AS SearchManager.SUGGEST_COLUMN_TEXT_1","`sample` AS SearchManager.SUGGEST_COLUMN_QUERY"};
	static public final String SELECTION = "`sample` MATCH ?";
	static public final String[] ARGS = {"`#{uri_last}`*"};
}

}