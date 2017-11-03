package com.bigdata.mahout.movie;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/3.
 * Author：charlesXu
 * file: ${Name}.scala
 * time: 2017/11/03
 */
public class ItemBasedDoubanRecommend {

    public static void main(String[] args) throws IOException, TasteException {
        String base = "E:\\adx\\";
        File file = new File(base + "user_movies.csv");
        DoubanFileDataModel model = new DoubanFileDataModel(file);

        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(recommender, 5);
        int numSimilarities = batch.computeItemSimilarities(Runtime.getRuntime().availableProcessors(), 1,
                                      new FileSimilarItemsWriter(new File(base + "item_result.csv")));
        System.out.println("Computed" + numSimilarities + "similarities for" + model.getNumItems()
                                        + "items" + "and saved them to file" + base + "item_result.csv");

    }
}
