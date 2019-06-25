package com.itheima;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;
import java.io.IOException;

public class HelloWorld {
    @Test
    public void testlue() throws IOException {
        Directory directory = FSDirectory.open(new File("E:\\lue").toPath());
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(new IKAnalyzer()));
        File file = new File("D:\\java资料\\lucene\\资料\\searchsource");
        File[] files = file.listFiles();
        for (File f : files) {
            String name = f.getName();
            String path = f.getPath();
            String context = FileUtils.readFileToString(f, "UTF-8");
            long size = FileUtils.sizeOf(f);
            Field fileName = new TextField("name", name, Field.Store.YES);
            Field filepath = new TextField("path", path, Field.Store.YES);
            Field filecontext = new TextField("context", context, Field.Store.YES);
            Field fileSize = new TextField("fileSize", size+"", Field.Store.YES);

            Document document = new Document();
            document.add(fileName);
            document.add(filepath);
            document.add(filecontext);
            document.add(fileSize);
            indexWriter.addDocument(document);
        }

        indexWriter.close();
    }
    @Test
    public void testTokenStream() throws IOException {
        Analyzer analyzer = new IKAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("test", "The Spring Framework provides a comprehensive programming and configuration model.");
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.println("start->"+offsetAttribute.startOffset());
            System.out.println(charTermAttribute);
            System.out.println("end->"+offsetAttribute.endOffset());
        }
        tokenStream.close();

    }
}
