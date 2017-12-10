import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created Date: 11/30/17
 */
public class Reader {
    Map<String, List<String[]>> tupleMap = new TreeMap<>();
    Map<String, MetaData> metaDataMap = new TreeMap<>();


    public static void main(String args[]) {
        Reader reader = new Reader();
        reader.read();
        reader.compute();
        reader.exportToFIle();
        System.out.println("");
    }

    public void read() {
        try {
            File sourceFile = new File("part");
            FileReader fileReader = new FileReader(sourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\\[|\\]", "");
                String[] tuple = line.split(",");
                if (!tupleMap.containsKey(tuple[2])) {
                    tupleMap.put(tuple[2], new ArrayList<>());
                }
                tupleMap.get(tuple[2]).add(tuple);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportToFIle() {
        try {
            File targetFile = new File("output.csv");
            FileWriter fileWriter = new FileWriter(targetFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, MetaData> entry : metaDataMap.entrySet()) {
                MetaData data = entry.getValue();
                bufferedWriter.write(data.avgLatitude + "," + data.avgLongitude + "," + data.weight + "\n");
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compute() {

        for (Map.Entry<String, List<String[]>> entry : tupleMap.entrySet()) {
            String key = entry.getKey();
            List<String[]> tuples = entry.getValue();
            int size = tuples.size();
            double longitude = 0, latitude = 0;
            for (String[] tuple : tuples) {
                latitude += Double.parseDouble(tuple[0]);
                longitude += Double.parseDouble(tuple[1]);
            }

            MetaData metaData = new MetaData(latitude / size, longitude / size, size);
            metaDataMap.put(key, metaData);
        }
    }

}