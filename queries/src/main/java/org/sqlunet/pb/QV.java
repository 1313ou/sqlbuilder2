/*
 * Copyright (c) 2019. Bernard Bou <1313ou@gmail.com>.
 */

package org.sqlunet.pb;

import org.sqlbuilder.common.Lib;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PropBank provider
 *
 * @author <a href="mailto:1313ou@gmail.com">Bernard Bou</a>
 */
public class QV implements Function<String,String[]>
{
	//# instantiated at runtime
	static public final String URI_LAST = "#{uri_last}";

	@Override
	public String[] apply(String key)
	{
		final String last = URI_LAST;

		String table;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String sortOrder = null;

		switch (key)
		{
			// I T E M
			// the incoming URI was for a single item because this URI was for a single row, the _ID value part is present.
			// get the last path segment from the URI: this is the _ID value. then, append the value to the WHERE clause for the query

			case "PBROLESET":
				table = "${rolesets.table}";
				if (selection != null)
				{
					selection += " AND ";
				}
				else
				{
					selection = "";
				}
				selection += String.format("%s = ?", "${rolesets.rolesetid}");
				break;

			case "PBROLESETS":
				table = "${rolesets.table}";
				break;

			// J O I N S

			case "PBROLESETS_X_BY_ROLESET":
				groupBy = "${rolesets.rolesetid}";
				//$FALL-THROUGH$
				//noinspection fallthrough

			case "PBROLESETS_X":
				table = String.format("%s " + //
								"LEFT JOIN %s AS %s USING (%s) " + //
								"LEFT JOIN %s AS %s ON %s.%s = %s.%s", //
						"${rolesets.table}", //
						"${members.table}", "${as_members}", "${rolesets.rolesetid}", //
						"${words.table}", "${as_words}", "${as_members}", "${words.pbwordid}", "${as_words}", "${words.pbwordid}");
				break;

			case "WORDS_PBROLESETS":
				table = String.format("%s " + //
								"INNER JOIN %s USING (%s) " + //
								"INNER JOIN %s USING (%s)", //
						"${wnwords.table}", //
						"${words.table}", "${words.wordid}", //
						"${rolesets.table}", "${words.pbwordid}");
				break;

			case "PBROLESETS_PBROLES":
				table = String.format("%s " + //
								"INNER JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${rolesets.table}", //
						"${roles.table}", "${rolesets.rolesetid}", //
						"${funcs.table}", "${funcs.func}", //
						"${thetas.table}", "${thetas.theta}");
				sortOrder = "${roles.argtypeid}";
				break;

			case "PBROLESETS_PBEXAMPLES_BY_EXAMPLE":
				groupBy = String.format("%s.%s", "${as_examples}", "${examples.exampleid}");
				//$FALL-THROUGH$
				//noinspection fallthrough

			case "PBROLESETS_PBEXAMPLES":
				table = String.format("%s " + //
								"INNER JOIN %s AS %s USING (%s) " + //
								"LEFT JOIN %s AS %s USING (%s) " + //
								"LEFT JOIN %s AS %s USING (%s) " + //
								"LEFT JOIN %s AS %s ON (%s.%s = %s.%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s) " + //
								"LEFT JOIN %s USING (%s,%s) " + //
								"LEFT JOIN %s USING (%s)", //
						"${rolesets.table}", //
						"${examples.table}", "${as_examples}", "${rolesets.rolesetid}", //
						"${rels.table}", "${as_relations}", "${examples.exampleid}", //
						"${args.table}", "${as_args}", "${examples.exampleid}", //
						"${funcs.table}", "${as_funcs}", "${as_args}", "${funcs.func}", "${as_funcs}", "${funcs.func}", //
						"${aspects.table}", "${aspects.aspect}", //
						"${forms.table}", "${forms.form}", //
						"${tenses.table}", "${tenses.tense}", //
						"${voices.table}", "${voices.voice}", //
						"${persons.table}", "${persons.person}", //
						"${roles.table}", "${rolesets.rolesetid}", "${args.arg}", //
						"${thetas.table}", "${thetas.theta}");
				sortOrder = String.format("%s.%s,%s", "${as_examples}", "${examples.exampleid}", "${args.arg}");
				break;

			// L O O K U P

			case "LOOKUP_FTS_EXAMPLES":
				table = String.format("%s_%s_fts4", "${examples.table}", "${examples.text}");
				break;

			case "LOOKUP_FTS_EXAMPLES_X_BY_EXAMPLE":
				groupBy = "${examples.exampleid}";
				//$FALL-THROUGH$
				//noinspection fallthrough

			case "LOOKUP_FTS_EXAMPLES_X":
				table = String.format("%s_%s_fts4 " + //
								"LEFT JOIN %s USING (%s)", //
						"${examples.table}", "${examples.text}", //
						"${rolesets.table}", "${rolesets.rolesetid}");
				break;

			// S U G G E S T

			case "SUGGEST_WORDS":
			{
				table = "${words.table}";
				projection = new String[]{String.format("%s AS _id", "${words.pbwordid}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_TEXT_1", "${words.word}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_COLUMN_QUERY", "${words.word}")}; //
				selection = String.format("%s LIKE ? || '%%'", "${words.word}");
				selectionArgs = new String[]{last};
				break;
			}

			case "SUGGEST_FTS_WORDS":
			{
				table = String.format("%s_%s_fts4", "${words.table}", "${words.word}");
				projection = new String[]{String.format("%s AS _id", "${words.pbwordid}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_COLUMN_TEXT_1", "${words.word}"), //
						String.format("%s AS " + "SearchManager.SUGGEST_COLUMN_COLUMN_QUERY", "${words.word}")}; //
				selection = String.format("%s MATCH ?", "${words.word}"); //
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
		PBROLESET, PBROLESETS, PBROLESETS_X_BY_ROLESET, PBROLESETS_X, WORDS_PBROLESETS, PBROLESETS_PBROLES, PBROLESETS_PBEXAMPLES_BY_EXAMPLE, PBROLESETS_PBEXAMPLES, LOOKUP_FTS_EXAMPLES, LOOKUP_FTS_EXAMPLES_X_BY_EXAMPLE, LOOKUP_FTS_EXAMPLES_X, SUGGEST_WORDS, SUGGEST_FTS_WORDS,
	}
}
