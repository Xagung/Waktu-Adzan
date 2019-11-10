package com.azan;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import static com.azan.Constants.KAABA_LATITUDE;
import static com.azan.Constants.KAABA_LONGITUDE;
import static com.azan.Constants.TOTAL_DEGREES;
import static com.azan.util.MathUtil.atan2Deg;
import static com.azan.util.MathUtil.cosDeg;
import static com.azan.util.MathUtil.sinDeg;
import static com.azan.util.MathUtil.tanDeg;


public class QiblaUtils {
    /**
     * Return qibla direction in degrees from the north (clock-wise).
     *
     * @param latitude  latitude in degrees
     * @param longitude longitude in degreesl
     * @return 0 means north, 90 means east, 270 means west, etc
     */
    public static double qibla(double latitude, double longitude) {
        double degrees = atan2Deg(sinDeg(KAABA_LONGITUDE - longitude),
            cosDeg(latitude) * tanDeg(KAABA_LATITUDE)
                - sinDeg(latitude) * cosDeg(KAABA_LONGITUDE - longitude));
        return degrees >= 0 ? degrees : degrees + TOTAL_DEGREES;
    }
    
    public static Double HitungJarak(double latitude, double longitude) {
		Location lokasiA = new Location("lokasi_a");
		lokasiA.setLatitude(KAABA_LATITUDE);
		lokasiA.setLongitude(KAABA_LONGITUDE);

		Location lokasiB = new Location("lokasi_b");
		lokasiB.setLatitude(latitude);
		lokasiB.setLongitude(longitude);

		Double distance = (double) lokasiA.distanceTo(lokasiB);
		return distance;
	}

	public static String direction(LatLng latlng1, LatLng latlng2) {
		String direction = "Unknown";
		double heading = SphericalUtil.computeHeading(latlng1, latlng2);
		Direction arah = new Direction();
		arah.toLeft = new String[]{"Utara", "Barat Laut", "Barat", "Barat Daya", "Selatan"};
		arah.toRight = new String[]{"Utara", "Timur Laut", "Timur", "Tenggara", "Selatan"};
		return arah.get(heading);
	}

        public class Direction{
		private double delta = 360/16;
		private double[] degree = new double[]{0, 45, 90, 135, 180};
		public String[] toLeft;
		public String[] toRight;
		public boolean isValid(){
			return degree.length == toLeft.length && degree.length == toRight.length;
  		}
  		public String get(double heading){
		String dir = "Tidak diketahui";
		for(int i = 0; i < degree.length ; i++)
			if(antara(Math.abs(heading), i)){
				dir = (heading >= 0? toRight : toLeft)[i];
				break;
	  		}
		return dir;
		}
		private boolean antara(double target, int no){
			return target >= degree[no] - delta && target <= degree[no] + delta;
  		}
	}
}
