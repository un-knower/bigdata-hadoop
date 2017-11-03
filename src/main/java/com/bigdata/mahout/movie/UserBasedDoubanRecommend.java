package com.bigdata.mahout.movie;

import com.google.common.io.Closeables;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.iterator.FileLineIterator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 * Author：charlesXu
 * file: UserBasedDoubanRecommend
 * desc: 基于物品的协同过滤算法
 * time: 2017/11/03
 */
public class UserBasedDoubanRecommend {

    public static void main(String[] args) throws IOException, TasteException {
        String base = "E:\\adx\\";
        File file = new File(base + "user_movies.csv");
        DoubanFileDataModel model = new DoubanFileDataModel(file);

        //计算相似度
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        Recommender cachingRecommender = new CachingRecommender(recommender);

        //Evaluate
        RMSRecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = dataModel -> cachingRecommender;
        double score = evaluator.evaluate(recommenderBuilder, null, model, 0.95, 0.95);
        System.out.print("Score=" + score);

        Map<Long, String> movies = getMovies(base);
        for (long userID = 0; userID < 100; userID++){
            String userName = model.userIdAndNameMapping.get(userID);
            List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userID, 2);
            System.out.print("为用户" + userName + "推荐电影:");
            for (RecommendedItem recommendedItem : recommendedItems){
                System.out.print(recommendedItem.getItemID() + "," + movies.get(recommendedItem.getItemID()) + " ");
            }
            System.out.println();
        }

        PrintWriter writer = new PrintWriter(base + "result.csv", "UTF-8");
        for (long userID = 0; userID < model.userIdAndNameMapping.size(); userID++){
            String userName = model.userIdAndNameMapping.get(userID);
            List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userID, 5);
            if (recommendedItems.size() > 0){
                String line = userName + ",";
                for (RecommendedItem recommendedItem: recommendedItems){
                    line += recommendedItem.getItemID() + ":" + movies.get(recommendedItem.getItemID() + ",");
                }
                if (line.endsWith(",")){
                    line = line.substring(0, line.length() - 1);
                }
                writer.println(line);
            }
        }
        writer.close();
    }

    private static Map<Long,String> getMovies(String base) {
        Map<Long, String> movies = new HashMap<>();
        try {
            File file = new File(base + "hot_movies.csv");
            FileLineIterator iterator = new FileLineIterator(file,false);
            String line = iterator.next();
            while (line.isEmpty()){
                String[] m = line.split(",");
                movies.put(Long.parseLong(m[0]), m[2]);
                line = iterator.next();
            }
            Closeables.close(iterator, true);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
