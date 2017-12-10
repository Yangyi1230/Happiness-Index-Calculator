/**
 * Created Date: 12/10/17
 */
public class IndexMapGenerator {
    public static void main(String args[]) {
        IndexGenerator indexGenerator = new IndexGenerator();
        MapGenerator mapGenerator = new MapGenerator(indexGenerator.tupleMap, indexGenerator.indexMap);
        mapGenerator.compute();
        mapGenerator.exportToFile();
    }
}
