package org.sqlbuilder.fn;

import org.sqlbuilder.common.*;
import org.sqlbuilder.fn.joins.*;
import org.sqlbuilder.fn.objects.*;
import org.sqlbuilder.fn.types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;

public class Inserter
{
	protected final Names names;

	protected File outDir;

	protected boolean resolve = false;

	public Inserter(final Properties conf)
	{
		this.names = new Names("fn");
		this.outDir = new File(conf.getProperty("fn_outdir", "sql/data"));
		if (!this.outDir.exists())
		{
			this.outDir.mkdirs();
		}
	}

	public void insert() throws FileNotFoundException
	{
		insertSemTypes();
		insertFrames();
		insertLexUnitsAndText();
	}

	public void insertSemTypes() throws FileNotFoundException
	{
		Progress.tracePending("set", "semtype");
		Insert.insert(SemType.SET, SemType.COMPARATOR, new File(outDir, names.file("semtypes")), names.table("semtypes"), names.columns("semtypes"));
		SemType.SET.clear();
		Progress.traceDone(null);

		Progress.tracePending("set", "semtype_super");
		Insert.insert(SemType_SemTypeSuper.SET, SemType_SemTypeSuper.COMPARATOR, new File(outDir, names.file("semtypes_supers")), names.table("semtypes_supers"), names.columns("semtypes_supers"));
		SemType_SemTypeSuper.SET.clear();
		Progress.traceDone(null);
	}

	public void insertFrames() throws FileNotFoundException
	{
		Progress.tracePending("set", "frame");
		Insert.insert(Frame.SET, Comparator.comparing(Frame::getID), new File(outDir, names.file("frames")), names.table("frames"), names.columns("frames"));
		Frame.SET.clear();
		Progress.traceDone(null);

		try (@ProvidesIdTo(type = FrameRelation.class) var ignored = FrameRelation.COLLECTOR.open())
		{
			Progress.tracePending("collector", "frame_relations");
			Insert.insertStringMap(FrameRelation.COLLECTOR, new File(outDir, names.file("framerelations")), names.table("framerelations"), names.columns("framerelations"));
			Progress.traceDone(null);

			Progress.tracePending("set", "frame_related");
			Insert.insert(Frame_FrameRelated.SET, Frame_FrameRelated.COMPARATOR, new File(outDir, names.file("frames_related")), names.table("frames_related"), names.columns("frames_related"));
			Frame_FrameRelated.SET.clear();
			Progress.traceDone(null);
		}

		Progress.tracePending("set", "fe_required");
		Insert.insert(FE_FERequired.SET, FE_FERequired.COMPARATOR, new File(outDir, names.file("fes_required")), names.table("fes_required"), names.columns("fes_required"));
		FE_FERequired.SET.clear();
		Progress.traceDone(null);

		Progress.tracePending("set", "fe_excluded");
		Insert.insert(FE_FEExcluded.SET, FE_FEExcluded.COMPARATOR, new File(outDir, names.file("fes_excluded")), names.table("fes_excluded"), names.columns("fes_excluded"));
		FE_FEExcluded.SET.clear();
		Progress.traceDone(null);

		Progress.tracePending("set", "frame_semtype");
		Insert.insert(Frame_SemType.SET, Frame_SemType.COMPARATOR, new File(outDir, names.file("frames_semtypes")), names.table("frames_semtypes"), names.columns("frames_semtypes"));
		Frame_SemType.SET.clear();
		Progress.traceDone(null);
	}

	public void insertLexUnitsAndText() throws FileNotFoundException
	{
		try (@ProvidesIdTo(type = GfType.class) var ignored11 = GfType.COLLECTOR.open(); //
		     @ProvidesIdTo(type = PtType.class) var ignored12 = PtType.COLLECTOR.open(); //
		     @ProvidesIdTo(type = LayerType.class) var ignored13 = LayerType.COLLECTOR.open(); //
		     @ProvidesIdTo(type = LabelType.class) var ignored14 = LabelType.COLLECTOR.open())
		{
			Progress.tracePending("collectors", "pos,coretype,labelitype");
			Insert.insert(Values.Pos.MAP, new File(outDir, names.file("poses")), names.table("poses"), names.columns("poses"));
			Insert.insert(Values.CoreType.MAP, new File(outDir, names.file("coretypes")), names.table("coretypes"), names.columns("coretypes"));
			Insert.insert(Values.LabelIType.MAP, new File(outDir, names.file("labelitypes")), names.table("labelitypes"), names.columns("labelitypes"));
			Progress.traceDone(null);

			Progress.tracePending("collector", "gf");
			Insert.insertStringMap(GfType.COLLECTOR, new File(outDir, names.file("gftypes")), names.table("gftypes"), names.columns("gftypes"));
			Progress.traceDone(null);

			Progress.tracePending("collector", "pt");
			Insert.insertStringMap(PtType.COLLECTOR, new File(outDir, names.file("pttypes")), names.table("pttypes"), names.columns("pttypes"));
			Progress.traceDone(null);

			Progress.tracePending("collector", "layertypes");
			Insert.insertStringMap(LayerType.COLLECTOR, new File(outDir, names.file("layertypes")), names.table("layertypes"), names.columns("layertypes"));
			Progress.traceDone(null);

			Progress.tracePending("collector", "labeltypes");
			Insert.insertStringMap(LabelType.COLLECTOR, new File(outDir, names.file("labeltypes")), names.table("labeltypes"), names.columns("labeltypes"));
			Progress.traceDone(null);

			Progress.tracePending("set", "annoset");
			Insert.insert(AnnotationSet.SET, AnnotationSet.COMPARATOR, new File(outDir, names.file("annosets")), names.table("annosets"), names.columns("annosets"));
			AnnotationSet.SET.clear();
			Progress.traceDone(null);

			Progress.tracePending("set", "cxns");
			Insert.insert(Cxns.SET, Cxns.COMPARATOR, new File(outDir, names.file("cxns")), names.table("cxns"), names.columns("cxns"));
			Cxns.SET.clear();
			Progress.traceDone(null);

			Progress.tracePending("set", "corpus");
			Insert.insert(Corpus.SET, Corpus.COMPARATOR, new File(outDir, names.file("corpuses")), names.table("corpuses"), names.columns("corpuses"));
			Corpus.SET.clear();
			Progress.traceDone(null);

			Progress.tracePending("set", "doc");
			Insert.insert(Doc.SET, Doc.COMPARATOR, new File(outDir, names.file("documents")), names.table("documents"), names.columns("documents"));
			Doc.SET.clear();
			Progress.traceDone(null);

			try (@ProvidesIdTo(type = Layer.class) var ignored20 = Layer.COLLECTOR.open())
			{
				Progress.tracePending("collector", "layer");
				Insert.insert(Layer.COLLECTOR, new File(outDir, names.file("layers")), names.table("layers"), names.columns("layers"), false);
				Progress.traceDone(null);

				Progress.tracePending("set", "label");
				Insert.insertFragmented(Label.SET, Label.COMPARATOR, new File(outDir, names.file("labels")), names.table("labels"), names.columns("labels"));
				Label.SET.clear();
				Progress.traceDone(null);
			}

			try (@ProvidesIdTo(type = FeType.class) var ignored21 = FeType.COLLECTOR.open())
			{
				Progress.tracePending("collector", "fetype");
				Insert.insertStringMap(FeType.COLLECTOR, new File(outDir, names.file("fetypes")), names.table("fetypes"), names.columns("fetypes"));
				Progress.traceDone(null);

				Progress.tracePending("set", "lexunit");
				Insert.insert(LexUnit.SET, LexUnit.COMPARATOR, new File(outDir, names.file("lexunits")), names.table("lexunits"), names.columns("lexunits"));
				LexUnit.SET.clear();
				Progress.traceDone(null);

				Progress.tracePending("set", "lexunit_semtype");
				Insert.insert(LexUnit_SemType.SET, LexUnit_SemType.COMPARATOR, new File(outDir, names.file("lexunits_semtypes")), names.table("lexunits_semtypes"), names.columns("lexunits_semtypes"));
				LexUnit_SemType.SET.clear();
				Progress.traceDone(null);

				Progress.tracePending("set", "fe_semtype");
				Insert.insert(FE_SemType.SET, FE_SemType.COMPARATOR, new File(outDir, names.file("fes_semtypes")), names.table("fes_semtypes"), names.columns("fes_semtypes"));
				FE_SemType.SET.clear();
				Progress.traceDone(null);

				try (@ProvidesIdTo(type = Word.class) var ignored30 = Word.COLLECTOR.open())
				{
					Progress.tracePending("set", "lexeme");
					Insert.insertAndIncrement(Lexeme.SET, Lexeme.COMPARATOR, new File(outDir, names.file("lexemes")), names.table("lexemes"), names.columns("lexemes"));
					Lexeme.SET.clear();
					Progress.traceDone(null);

					FE.BY_FETYPEID_AND_FRAMEID = makeFEByFETypeIdAndFrameIdMap();
					try
					{
						Progress.tracePending("set", "fe");
						Insert.insert(FE.SET, FE.COMPARATOR, new File(outDir, names.file("fes")), names.table("fes"), names.columns("fes"));
						FE.SET.clear();
						Progress.traceDone(null);

						try (@ProvidesIdTo(type = FERealization.class) var ignored40 = FERealization.LIST.open(); // vu_fer
						     @ProvidesIdTo(type = FEGroupRealization.class) var ignored41 = FEGroupRealization.LIST.open())
						{
							Progress.tracePending("collector", "fer");
							Insert.insert(FERealization.LIST, new File(outDir, names.file("ferealizations")), names.table("ferealizations"), names.columns("ferealizations"));
							Progress.traceDone(null);

							Progress.tracePending("collector", "fegr");
							Insert.insert(FEGroupRealization.LIST, new File(outDir, names.file("fegrouprealizations")), names.table("fegrouprealizations"), names.columns("fegrouprealizations"));
							Progress.traceDone(null);

							Progress.tracePending("set", "fe_fegr");
							Insert.insert(FE_FEGroupRealization.SET, FE_FEGroupRealization.COMPARATOR, new File(outDir, names.file("fes_fegrouprealizations")), names.table("fes_fegrouprealizations"), names.columns("fes_fegrouprealizations"));
							FE_FEGroupRealization.SET.clear();
							Progress.traceDone(null);

							try (@ProvidesIdTo(type = FEGroupPattern.class) var ignored44 = FEGroupPattern.LIST.open(); //
							     @ProvidesIdTo(type = ValenceUnit.class) var ignored43 = ValenceUnit.COLLECTOR.open())
							{
								Progress.tracePending("set", "fe_fegr");
								Insert.insert(FEPattern.SET, FEPattern.COMPARATOR, new File(outDir, names.file("ferealizations_valenceunits")), names.table("ferealizations_valenceunits"), names.columns("ferealizations_valenceunits"));
								FEPattern.SET.clear();
								Progress.traceDone(null);

								Progress.tracePending("collector", "valenceunit");
								Insert.insert(ValenceUnit.COLLECTOR, new File(outDir, names.file("valenceunits")), names.table("valenceunits"), names.columns("valenceunits"));
								Progress.traceDone(null);

								Progress.tracePending("collector", "grouppattern");
								Insert.insert(FEGroupPattern.LIST, new File(outDir, names.file("grouppatterns")), names.table("grouppatterns"), names.columns("grouppatterns"));
								Progress.traceDone(null);

								Progress.tracePending("collector", "grouppattern_annoset");
								Insert.insert(FEGroupPattern_AnnoSet.SET, null, new File(outDir, names.file("grouppatterns_annosets")), names.table("grouppatterns_annosets"), names.columns("grouppatterns_annosets"));
								FEGroupPattern_AnnoSet.SET.clear();
								Progress.traceDone(null);

								Progress.tracePending("collector", "grouppattern_pattern");
								Insert.insert(FEGroupPattern_FEPattern.SET, null, new File(outDir, names.file("grouppatterns_patterns")), names.table("grouppatterns_patterns"), names.columns("grouppatterns_patterns"));
								FEGroupPattern_FEPattern.SET.clear();
								Progress.traceDone(null);

								Progress.tracePending("collector", "valenceunit_annoset");
								Insert.insert(ValenceUnit_AnnoSet.SET, null, new File(outDir, names.file("valenceunits_annosets")), names.table("valenceunits_annosets"), names.columns("valenceunits_annosets"));
								ValenceUnit_AnnoSet.SET.clear();
								Progress.traceDone(null);
							}
						}

						try (@ProvidesIdTo(type = Governor.class) var ignored42 = Governor.COLLECTOR.open())
						{
							Progress.tracePending("collector", "governor");
							Insert.insert(Governor.COLLECTOR, new File(outDir, names.file("governors")), names.table("governors"), names.columns("governors"));
							Progress.traceDone(null);

							Progress.tracePending("set", "lexunit_governor");
							Insert.insert(LexUnit_Governor.SET, LexUnit_Governor.COMPARATOR, new File(outDir, names.file("lexunits_governors")), names.table("lexunits_governors"), names.columns("lexunits_governors"));
							LexUnit_Governor.SET.clear();
							Progress.traceDone(null);

							Progress.tracePending("set", "governor_annoset");
							Insert.insert(Governor_AnnoSet.SET, Governor_AnnoSet.COMPARATOR, new File(outDir, names.file("governors_annosets")), names.table("governors_annosets"), names.columns("governors_annosets"));
							Governor_AnnoSet.SET.clear();
							Progress.traceDone(null);
						}

						Progress.tracePending("set", "sentence");
						Insert.insert(Sentence.SET, Sentence.COMPARATOR, new File(outDir, names.file("sentences")), names.table("sentences"), names.columns("sentences"));
						Sentence.SET.clear();
						Progress.traceDone(null);

						try (@ProvidesIdTo(type = SubCorpus.class) var ignored50 = SubCorpus.COLLECTOR.open())
						{
							Progress.tracePending("set", "subcorpus_sentence");
							Insert.insert(SubCorpus.COLLECTOR, new File(outDir, names.file("subcorpuses")), names.table("subcorpuses"), names.columns("subcorpuses"));
							Progress.traceDone(null);

							Progress.tracePending("set", "subcorpus_sentence");
							Insert.insert(SubCorpus_Sentence.SET, SubCorpus_Sentence.COMPARATOR, new File(outDir, names.file("subcorpuses_sentences")), names.table("subcorpuses_sentences"), names.columns("subcorpuses_sentences"));
							SubCorpus_Sentence.SET.clear();
							Progress.traceDone(null);
						}
					}
					finally
					{
						FE.BY_FETYPEID_AND_FRAMEID = null;
					}

					// R E S O L V A B L E
					insertWords();
				}
			}
		}
	}

	protected void insertWords() throws FileNotFoundException
	{
		Progress.tracePending("collector", "word");
		Insert.insert(Word.COLLECTOR, new File(outDir, names.file("words")), names.table("words"), names.columns("words"));
		Progress.traceDone(null);
	}

	@RequiresIdFrom(type = FeType.class)
	private Map<Pair<Integer, Integer>, FE> makeFEByFETypeIdAndFrameIdMap()
	{
		return FE.SET.stream() //
				.map(fe -> new SimpleEntry<>(new Pair<>(FeType.getIntId(fe.getName()), fe.getFrameID()), fe)) //
				.collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	}
}
