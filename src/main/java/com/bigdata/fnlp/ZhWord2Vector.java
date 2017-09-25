package com.bigdata.fnlp;

import org.canova.api.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Administrator on 2017/9/25.
 * Author：charlesXu
 * file: ${Name}.scala
 * time: 2017/09/25
 */
public class ZhWord2Vector {
    private static Logger logger = LoggerFactory.getLogger(ZhWord2Vector.class);

    public static void main(String[] args) throws IOException {
        String filePath = new ClassPathResource("text/tlbb_t.txt").getFile().getAbsolutePath();
        logger.info("Load & Vectorize Sentence....");

        SentenceIterator iterator = new BasicLineIterator(filePath);

        logger.info("Building Model....");
        Word2Vec vec = new Word2Vec.Builder()
                                    .minWordFrequency(5)
                                    .iterations(1)
                                    .layerSize(100)
                                    .seed(42)
                                    .windowSize(5)
                                    .iterate(iterator)
                                    .build();
        logger.info("Fitting Word2Vec model.....");
        vec.fit();

        logger.info("Writing word vector to text file...");

        //Write word vectors
        WordVectorSerializer.writeWordVectors(vec, "tlbb_vectors.txt");
        WordVectorSerializer.writeFullModel(vec, "tlbb_model.txt");
        String[] names = {"萧峰","乔峰","段誉","虚竹","王语嫣","阿紫","阿朱","木婉清"};
        logger.info("Closest Words:");

        for (String name: names){
            System.out.println(name + ">>>>>");
            Collection<String> lst = vec.wordsNearest(name, 10);
            System.out.println(lst);
        }

    }
}
