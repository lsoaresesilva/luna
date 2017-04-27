package luna.luna.geolocation;

import android.location.Location;

/**
 * Created by macbookair on 20/04/17.
 */

class LocationWrapper {
    private double latitude;
    private double longitude;

    //-(void)updateLocationWrapper:(CLLocation*)location;
    public void updateLocationWrapper(Location location){
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
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
}
