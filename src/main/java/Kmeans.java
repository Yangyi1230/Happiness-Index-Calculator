import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;


public class Kmeans {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaKMeansExample")
                .getOrCreate();


        StructType schema = new StructType()
                .add("latitude", "double")
                .add("longtitude", "double");


        //TODO read and format data from local txt file
        Dataset<Row> dataset = spark.read().schema(schema).load("output.txt");
        // Trains a k-means model.
        //TODO understand the meaning of argument
        KMeans kmeans = new KMeans().setK(2).setSeed(1L);
        KMeansModel model = kmeans.fit(dataset);
        double WSSSE = model.computeCost(dataset);
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);
        Vector[] centers = model.clusterCenters();
        System.out.println("Cluster Centers: ");
        for (Vector center : centers) {
            System.out.println(center);
        }
        spark.stop();
    }
}
