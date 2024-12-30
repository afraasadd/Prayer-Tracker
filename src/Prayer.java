/**
 * <h3>Purpose of this class:</h3>
 * store a blueprint of each prayer
 */
public class Prayer {
    private String prayerName;
    private String status;
    private String date;
    private Integer khushuRating;
    private String notes;

    public Prayer(String prayerName, String status, String date, Integer khushuRating, String notes) {
        this.prayerName = prayerName;
        this.status = status;
        this.date = date;
        this.khushuRating = khushuRating;
        this.notes = notes;
    }

    // Getters and setters
    public String getPrayerName() { return prayerName; }
    public void setPrayerName(String prayerName) { this.prayerName = prayerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Integer getKhushuRating() { return khushuRating; }
    public void setKhushuRating(Integer khushuRating) { this.khushuRating = khushuRating; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Prayer{" +
                "prayerName='" + prayerName + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", khushuRating=" + khushuRating +
                ", notes='" + notes + '\'' +
                '}';
    }
}
