package org.gen.screensharesdk;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class DeviceInfo implements Parcelable {
    private String ip;
    private int port;
    private String mac;
    private State state;

    public DeviceInfo() {
    }

    protected DeviceInfo(Parcel in) {
        ip = in.readString();
        port = in.readInt();
        mac = in.readString();
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel in) {
            return new DeviceInfo(in);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ip);
        dest.writeInt(port);
        dest.writeString(mac);
    }

    public enum State {
        UNCONN, CONNECTING, CONNECTED
    }

    public static class Builder {

        private DeviceInfo deviceInfo = new DeviceInfo();

        public Builder ip(String ip) {
            deviceInfo.ip = ip;
            return this;
        }

        public Builder port(int port) {
            deviceInfo.port = port;
            return this;
        }

        public Builder mac(String mac) {
            deviceInfo.mac = mac;
            return this;
        }

        public Builder state(State state) {
            deviceInfo.state = state;
            return this;
        }

        public DeviceInfo build() {
            return deviceInfo;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceInfo that = (DeviceInfo) o;
        return port == that.port &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(mac, that.mac) &&
                state == that.state;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ip, port, mac, state);
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", mac='" + mac + '\'' +
                ", state=" + state +
                '}';
    }
}
