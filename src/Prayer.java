/// <h1>Project Title: Prayer Tracker</h1>
/// @Start Date  19.December.2024
/// @author Afra, Munesha, Saba, Bakhtawar
public class Prayer {

    public class Prayer {
        private String prayerName;
        private boolean isPrayed;
        private String date;

        // Constructor
        public Prayer(String prayerName, boolean isPrayed, String date) {
            this.prayerName = prayerName;
            this.isPrayed = isPrayed;
            this.date = date;
        }

        // Getters and Setters
        public String getPrayerName() {
            return prayerName;
        }

        public void setPrayerName(String prayerName) {
            this.prayerName = prayerName;
        }

        public boolean isPrayed() {
            return isPrayed;
        }

        public void setPrayed(boolean prayed) {
            isPrayed = prayed;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Prayer: " + prayerName + ", Prayed: " + isPrayed + ", Date: " + date;
        }
    }

}
