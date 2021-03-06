/*
 * Copyright (c) 2019. Bernard Bou <1313ou@gmail.com>.
 */

package org.sqlunet.vn;

import org.sqlbuilder.common.Lib;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * VerbNet provider
 *
 * @author <a href="mailto:1313ou@gmail.com">Bernard Bou</a>
 */
public class QV implements Function<String,String[]>
{
	//# instantiated at runtime
	static public final String URI_LAST = "#{uri_last}";

	@Override
	public String[] apply(String keyname)
	{
		final String last = URI_LAST;

		String table;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;

		Key key = Key.valueOf(keyname);
		switch (key)
		{
			// I T E M
			// the incoming URI was for a single item because this URI was for a single row, the _ID value part is present.
			// get the last path segment from the URI: this is the _ID value. then, append the value to the WHERE clause for the query

			case WORDS:
				table = "${words.table}";
				break;

			case SENSES:
				table = "${senses.table}";
				break;

			case SYNSETS:
				table = "${wnsynsets.table}";
				break;

			case VNCLASS:
				table = "${classes.table}";
				if (selection != null)
				{
					selection += " AND ";
				}
				else
				{
					selection = "";
				}
				selection += "${classes.classid}" + " = " + "URI_PATH_SEGMENT";
				break;

			case VNCLASSES:
				table = "${classes.table}";
				break;

			case VNCLASSES_X_BY_VNCLASS:
				table = String.format("%s " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${classes.table}", //
						"${members_groupings.table}", "${classes.classid}", //
						"${groupings.table}", "${groupings.groupingid}"); //
				groupBy = "${classes.classid}";
				break;

			// J O I N S

			case WORDS_VNCLASSES:
				table = String.format("%s " + //
								"INNER JOIN %s USING (%s) " + //
								"INNER JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${wnwords.table}", //
						"${words.table}", "${wnwords.wordid}", //
						"${members_senses.table}", "${words.vnwordid}", //
						"${classes.table}", "${classes.classid}");
				break;

			case VNCLASSES_VNMEMBERS_X_BY_WORD:
				table = String.format("%s " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s, %s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${members_senses.table}", //
						"${words.table}", "${words.vnwordid}", //
						"${members_groupings.table}", "${members.classid}", "${members.vnwordid}", //
						"${groupings.table}", "${groupings.groupingid}", //
						"${wnsynsets.table}", "${wnsynsets.synsetid}");
				groupBy = "${words.vnwordid}";
				break;

			case VNCLASSES_VNROLES_X_BY_VNROLE:
				table = String.format("%s " + //
								"INNER JOIN %s USING (%s) " + //
								"INNER JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${classes.table}", //
						"${roles.table}", "${classes.classid}", //
						"${roletypes.table}", "${roletypes.roletypeid}", //
						"${restrs.table}", "${restrs.restrsid}");
				groupBy = "${roles.roleid}";
				break;

			case VNCLASSES_VNFRAMES_X_BY_VNFRAME:
				table = String.format("%s " + //
								"INNER JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${classes_frames.table}", //
						"${frames.table}", "${frames.frameid}", //
						"${framenames.table}", "${framenames.framenameid}", //
						"${framesubnames.table}", "${framesubnames.framesubnameid}", //
						"${syntaxes.table}", "${syntaxes.syntaxid}", //
						"${semantics.table}", "${semantics.semanticsid}", //
						"${frames_examples.table}", "${frames.frameid}", //
						"${examples.table}", "${examples.exampleid}");
				groupBy = "${frames.frameid}";
				break;

			// L O O K U P

			case LOOKUP_FTS_EXAMPLES:
				table = String.format("%s_%s_fts4", "${examples.table}", "${examples.example}");
				break;

			case LOOKUP_FTS_EXAMPLES_X_BY_EXAMPLE:
				groupBy = "${examples.exampleid}";
				//$FALL-THROUGH$
				//noinspection fallthrough
			case LOOKUP_FTS_EXAMPLES_X:
				table = String.format("%s_%s_fts4 " + //
								"LEFT JOIN %s USING (%s)", //
						"${examples.table}", "${examples.example}", //
						"${classes.table}", "${classes.classid}");
				break;

			// S U G G E S T

			case SUGGEST_WORDS:
			{
				table = "${words.table}";
				projection = new String[]{String.format("%s AS _id", "${words.vnwordid}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_TEXT_1", "${words.word}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_QUERY", "${words.word}")}; //
				selection = String.format("%s LIKE ? || '%%'", "${words.word}");
				selectionArgs = new String[]{last};
				break;
			}

			case SUGGEST_FTS_WORDS:
			{
				table = String.format("%s_%s_fts4", "${words.table}", "${words.word}");
				projection = new String[]{String.format("%s AS _id", "${words.vnwordid}"),//
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_TEXT_1", "${words.word}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_QUERY", "${words.word}")}; //
				selection = String.format("%s MATCH ?", "${words.word}");
				selectionArgs = new String[]{last + '*'};
				break;
			}

			default:
				return null;
		}
		return new String[]{ //
				Lib.quote(table), //
				projection == null ? null : "{" + Arrays.stream(projection).map(Lib::quote).collect(Collectors.joining(",")) + "}", //
				Lib.quote(selection), //
				selectionArgs == null ? null : "{" + Arrays.stream(selectionArgs).map(Lib::quote).collect(Collectors.joining(",")) + "}", //
				Lib.quote(groupBy)};
	}

	public enum Key
	{
		VNCLASS, VNCLASSES, VNCLASSES_X_BY_VNCLASS, WORDS_VNCLASSES, VNCLASSES_VNMEMBERS_X_BY_WORD, VNCLASSES_VNROLES_X_BY_VNROLE, VNCLASSES_VNFRAMES_X_BY_VNFRAME, LOOKUP_FTS_EXAMPLES, LOOKUP_FTS_EXAMPLES_X_BY_EXAMPLE, LOOKUP_FTS_EXAMPLES_X, SUGGEST_WORDS, SUGGEST_FTS_WORDS, WORDS, SENSES, SYNSETS,
	}
}