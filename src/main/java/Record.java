/**
 * Created Date: 12/10/17
 */
public class Record {
    private int sourceId;
    private int clusterId;
    private double latitude;
    private double longitude;
    private double socre;

    public Record(String sourceId, String clusterId, String latitude, String longitude, String socre) {
        this.sourceId = Integer.valueOf(sourceId);
        this.clusterId = Integer.valueOf(clusterId);
        this.latitude = Double.valueOf(latitude);
        this.longitude = Double.valueOf(longitude);
        this.socre = Double.valueOf(socre);
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSocre() {
        return socre;
    }

    public void setScore(double socre) {
        this.socre = socre;
    }

    public String toString() {
        return sourceId + " " + clusterId + " " + latitude + " " + longitude;
    }
}
