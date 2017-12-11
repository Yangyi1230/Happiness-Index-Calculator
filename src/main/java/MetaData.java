/**
 * Author: Spikerman < mail4spikerman@gmail.com >
 * Created Date: 11/30/17
 */
public class MetaData {
    double avgLatitude;
    double avgLongitude;
    double value;
    int density;
    double scoreTwitter;
    double score311;
    double scoreCrime;
    int clusterId;

    public MetaData(double avgLatitude, double avgLongitude, double value, int density) {
        this.avgLatitude = avgLatitude;
        this.avgLongitude = avgLongitude;
        this.value = value;
        this.density = density;
    }

    public MetaData(double avgLatitude, double avgLongitude, int density) {
        this.avgLatitude = avgLatitude;
        this.avgLongitude = avgLongitude;
        this.density = density;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
