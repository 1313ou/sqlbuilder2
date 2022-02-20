package org.sqlbuilder.bnc;

import org.sqlbuilder.bnc.objects.BNCExtendedRecord;
import org.sqlbuilder.bnc.objects.BNCRecord;
import org.sqlbuilder.common.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.function.BiConsumer;

public class BNCUpdatingProcessor extends BNCResolvingProcessor
{
	public BNCUpdatingProcessor(final Properties conf) throws IOException, ClassNotFoundException
	{
		super(conf);
	}

	@Override
	public void run() throws IOException
	{
		// main
		String bNCMain = conf.getProperty("bnc_main", "bnc.txt");
		try (PrintStream ps = new PrintStream(new FileOutputStream(new File(outDir, names.updateFile("bncs"))), true, StandardCharsets.UTF_8))
		{
			processBNCFile(ps, new File(bncHome, bNCMain), (record, i) -> updateRow(ps, names.table("bncs"), i, record, names.column("bncs.wordid"), names.column("bncs.word")));
		}

		// subfiles
		String bNCSpWr = conf.getProperty("bnc_spwr", "bnc-spoken-written.txt");
		try (PrintStream ps = new PrintStream(new FileOutputStream(new File(outDir, names.updateFile("spwrs"))), true, StandardCharsets.UTF_8))
		{
			processBNCSubFile(ps, new File(bncHome, bNCSpWr), (record, i) -> updateRow(ps, names.table("spwrs"), i, record, names.column("spwrs.wordid"), names.column("spwrs.word")));
		}

		String bNCConvTask = conf.getProperty("bnc_convtask", "bnc-conv-task.txt");
		try (PrintStream ps = new PrintStream(new FileOutputStream(new File(outDir, names.updateFile("convtasks"))), true, StandardCharsets.UTF_8))
		{
			processBNCSubFile(ps, new File(bncHome, bNCConvTask), (record, i) -> updateRow(ps, names.table("convtasks"), i, record, names.column("convtasks.wordid"), names.column("convtasks.word")));
		}

		String bNCImagInf = conf.getProperty("bnc_imaginf", "bnc-imag-inf.txt");
		try (PrintStream ps = new PrintStream(new FileOutputStream(new File(outDir, names.updateFile("imaginfs"))), true, StandardCharsets.UTF_8))
		{
			processBNCSubFile(ps, new File(bncHome, bNCImagInf), (record, i) -> updateRow(ps, names.table("imaginfs"), i, record, names.column("imaginfs.wordid"), names.column("imaginfs.word")));
		}
	}

	private void processBNCFile(final PrintStream ps, final File file, final BiConsumer<BNCRecord, Integer> consumer) throws IOException
	{
		ps.printf("-- %s %s%n", file.getName(), this.serFile);
		process(file, BNCRecord::parse, consumer);
	}

	private void processBNCSubFile(final PrintStream ps, final File file, final BiConsumer<BNCRecord, Integer> consumer) throws IOException
	{
		ps.printf("-- %s %s%n", file.getName(), this.serFile);
		process(file, BNCExtendedRecord::parse, consumer);
	}

	private void updateRow(final PrintStream ps, final String table, final int index, final BNCRecord bncRecord, final String... columns)
	{
		Integer wordid = wordResolver.apply(bncRecord.word);
		if (wordid != null)
		{
			var setClause = String.format("%s=%d", columns[0], wordid);
			var whereClause = String.format("%s=%s", columns[1], Utils.quote(Utils.escape(bncRecord.word)));
			ps.printf("UPDATE %s SET %s WHERE %s; -- %d%n", table, setClause, whereClause, index + 1);
		}
	}
}
