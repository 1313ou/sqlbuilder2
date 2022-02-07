package org.sqlbuilder.sn.objects;

import org.sqlbuilder.common.Insertable;
import org.sqlbuilder.common.ParseException;
import org.sqlbuilder.common.Utils;
import org.sqlbuilder2.legacy.Triplet;

import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;

public class Collocation implements Insertable
{
	public final long offset1;

	public final char pos1;

	public final String word1;

	public final long offset2;

	public final char pos2;

	public final String word2;

	public String sensekey1;

	public String sensekey2;

	public Collocation(long offset1, char pos1, String lemma1, long offset2, char pos2, String lemma2)
	{
		this.offset1 = offset1;
		this.pos1 = pos1;
		this.word1 = makeLemma(lemma1);
		this.offset2 = offset2;
		this.pos2 = pos2;
		this.word2 = makeLemma(lemma2);
	}

	public String getSensekey1()
	{
		return sensekey1;
	}

	public String getSensekey2()
	{
		return sensekey2;
	}

	public static Collocation parse(String line) throws ParseException
	{
		try
		{
			String[] fields = line.split("\\s");

			long synset1Id = Long.parseLong(fields[0].substring(0, fields[0].length() - 1));
			long synset2Id = Long.parseLong(fields[1].substring(0, fields[1].length() - 1));
			String lemma1 = fields[2];
			char pos1 = fields[3].charAt(0);
			String lemma2 = fields[4];
			char pos2 = fields[5].charAt(0);
			return new Collocation(synset1Id, pos1, lemma1, synset2Id, pos2, lemma2);
		}
		catch (Exception e)
		{
			throw new ParseException(e.getMessage());
		}
	}

	static String makeLemma(String word)
	{
		word = word.trim();
		return word.replace('_', ' ');
	}

	@Override
	public String dataRow()
	{
		//return String.format("('%s',%d,'%c',%d,%d, '%s',%d,'%c',%d,%d)", Utils.escape(word1), word1id, pos1, offset1, synset1id, Utils.escape(word2), word2id, pos2, offset2, synset2id);
		return String.format("%s,%s", Utils.nullableQuotedEscapedString(sensekey1), Utils.nullableQuotedEscapedString(sensekey2));
	}

	@Override
	public String comment()
	{
		return String.format("%s,%c,%d,%s,%c,%d", word1, pos1, offset1, word2, pos2, offset2);
	}

	public boolean resolve(final Function<Triplet<String, Character, Long>, String> skResolver)
	{
		String sk1 = skResolver.apply(new Triplet<>(word1, pos1, offset1));
		boolean resolved1 = sk1 != null;
		if (!resolved1)
		{
			System.err.printf("[RK] %s %c %d%n", word1, pos1, offset1);
		}
		if (resolved1)
		{
			sensekey1 = sk1;
		}
		String sk2 = skResolver.apply(new Triplet<>(word2, pos2, offset2));
		boolean resolved2 = sk2 != null;
		if (!resolved2)
		{
			System.err.printf("[RK] %s %c %d%n", word2, pos2, offset2);
		}
		if (resolved2)
		{
			sensekey2 = sk2;
		}
		return resolved1 && resolved2;
	}
}
