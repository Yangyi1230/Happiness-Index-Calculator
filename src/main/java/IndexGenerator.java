import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created Date: 12/10/17
 */


public class IndexGenerator {

    static final int clusterNum = 2000;
    public Map<Integer, Double> indexMap = new HashMap<>();
    Map<Integer, MetaData> metaDataMap = new HashMap<>();
    Map<Integer, List<Record>> mapTwitter = new HashMap<>();
    Map<Integer, List<Record>> map311 = new HashMap<>();
    Map<Integer, List<Record>> mapCrime = new HashMap<>();
    Map<Integer, Double> valueTwitter = new HashMap<>();
    Map<Integer, Double> value311 = new HashMap<>();
    Map<Integer, Double> valueCrime = new HashMap<>();
    Map<String, List<String[]>> tupleMap = new HashMap<>();

    public IndexGenerator() {
        readFromFile();
    }

    public static void main(String args[]) {
        IndexGenerator indexGenerator = new IndexGenerator();
        indexGenerator.valueCompute();
        indexGenerator.exportToFile();
    }

    private void readFromFile() {
        try {
            File sourceFile = new File("result.csv");
            FileReader fileReader = new FileReader(sourceFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                Record record = new Record(fields[0], fields[1], fields[2], fields[3], fields[4]);
                if (record.getSourceId() == 0) {
                    if (!mapTwitter.containsKey(record.getClusterId()))
                        mapTwitter.put(record.getClusterId(), new ArrayList<>());
                    mapTwitter.get(record.getClusterId()).add(record);
                } else if (record.getSourceId() == 1) {
                    if (!map311.containsKey(record.getClusterId()))
                        map311.put(record.getClusterId(), new ArrayList<>());
                    record.setScore(-1 * record.getSocre());
                    map311.get(record.getClusterId()).add(record);
                } else {
                    if (!mapCrime.containsKey(record.getClusterId()))
                        mapCrime.put(record.getClusterId(), new ArrayList<>());
                    record.setScore(-1 * record.getSocre());
                    mapCrime.get(record.getClusterId()).add(record);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void exportToFile() {
        try {
            File targetFile = new File("output.csv");
            FileWriter fileWriter = new FileWriter(targetFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<Integer, MetaData> entry : metaDataMap.entrySet()) {
                MetaData data = entry.getValue();
                bufferedWriter.write(data.clusterId + "," + data.avgLatitude + "," + data.avgLongitude + "," + data.value + ","
                        + data.density + ","
                        + data.scoreTwitter + ","
                        + data.scoreCrime + "," +
                        +data.score311 + "," +
                        "\n");
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void valueCompute() {
        DescriptiveStatistics dsT = new DescriptiveStatistics();
        DescriptiveStatistics dsC = new DescriptiveStatistics();
        DescriptiveStatistics ds3 = new DescriptiveStatistics();
        for (int i = 0; i < clusterNum; i++) {
            if (mapTwitter.containsKey(i) && mapCrime.containsKey(i) && map311.containsKey(i)) {
                List<Record> listT = mapTwitter.get(i);
                List<Record> listC = mapCrime.get(i);
                List<Record> list3 = map311.get(i);
                double latitude = 0, longitude = 0;
                int size = list3.size() + listC.size() + listT.size();
                for (Record record : listT) {
                    latitude += record.getLatitude();
                    longitude += record.getLongitude();
                }
                for (Record record : listC) {
                    latitude += record.getLatitude();
                    longitude += record.getLongitude();
                }
                for (Record record : list3) {
                    latitude += record.getLatitude();
                    longitude += record.getLongitude();
                }

                MetaData metaData = new MetaData(latitude / size, longitude / size, size);
                metaDataMap.put(i, metaData);
                double avgT = 0, avgC = 0, avg3 = 0;
                for (Record record : listT) {
                    avgT += record.getSocre();
                }
                avgT = avgT / listT.size();
                avgC = -listC.size() / (double) listT.size();
                avg3 = -list3.size() / (double) listT.size();
                dsT.addValue(avgT);
                dsC.addValue(avgC);
                ds3.addValue(avg3);

                valueTwitter.put(i, avgT);
                valueCrime.put(i, avgC);
                value311.put(i, avg3);
            }
        }

        double svT = dsT.getStandardDeviation();
        double svC = dsC.getStandardDeviation();
        double sv3 = ds3.getStandardDeviation();

        System.out.println(svT + " " + svC + " " + sv3);


        for (Map.Entry<Integer, Double> entry : valueTwitter.entrySet()) {
            int i = entry.getKey();
            double normalT = valueTwitter.get(i) / svT;
            double normalC = valueCrime.get(i) / svC;
            double normal3 = value311.get(i) / sv3;
            System.out.println(i + " " + normalT + " " + normalC + " " + normal3);
            metaDataMap.get(i).setValue(normalT + normal3 + normalC);
            metaDataMap.get(i).scoreTwitter = normalT;
            metaDataMap.get(i).scoreCrime = normalC;
            metaDataMap.get(i).score311 = normal3;
            metaDataMap.get(i).clusterId = i;
            //indexMap.put(i, normal3 + normalC + normalT);
        }
    }
}
