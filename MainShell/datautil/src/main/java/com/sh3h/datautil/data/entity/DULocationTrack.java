package com.sh3h.datautil.data.entity;

/**
 * Created by dengzhimin on 2016/6/1.
 */
public class DULocationTrack {
    public static final int UPLOAD_SUCCESS = 1;
    public static final int UPLOAD_FAIL = 0;

    private int userId;
    private Location location;

    public DULocationTrack() {
    }

    public DULocationTrack(int userId, Location location){
        this.userId = userId;
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public class Location {

        private int userId;
        private String deviceId;
        private String locationType;
        private double longitude;
        private double latitude;
        private float radius;
        private double altitude;
        private float direction;
        private float speed;
        private long time;
        private String extend;

        public Location() {
        }

        public Location(int userId,
                        String deviceId,
                        String locationType,
                        float longitude,
                        float latitude,
                        float radius,
                        float altitude,
                        float direction,
                        float speed,
                        long time,
                        String extend) {
            this.userId = userId;
            this.deviceId = deviceId;
            this.locationType = locationType;
            this.longitude = longitude;
            this.latitude = latitude;
            this.radius = radius;
            this.altitude = altitude;
            this.direction = direction;
            this.speed = speed;
            this.time = time;
            this.extend = extend;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public double getAltitude() {
            return altitude;
        }

        public void setAltitude(double altitude) {
            this.altitude = altitude;
        }

        public float getDirection() {
            return direction;
        }

        public void setDirection(float direction) {
            this.direction = direction;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }
    }
}
