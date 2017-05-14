package ru.noties.demoui.options;

// Control the RSSI display
public class Network {

    // `show` to show icon, any other value to hide
    private Boolean airplane;

    // 	Sets MCS state to fully connected (true, false)
    private Boolean fully;

    // `show` to show icon, any other value to hide
    private Boolean wifi;

    // Sets wifi level (null or 0-4)
    private Integer wifiLevel;

    // `show` to show icon, any other value to hide
    private Boolean mobile;

    // Values: `1x`, `3g`, `4g`, `e`, `g`, `h`, `lte`, `roam`, any other value to hide
    private String mobileDatatype;

    // Sets mobile signal strength level (null or 0-4)
    private Integer mobileLevel;

    // Sets mobile signal icon to carrier network change UX when disconnected (`show` to show icon, any other value to hide)
    private Boolean carriernetworkchange;

    // Sets the number of sims (1-8)
    private Integer sims;

    // `show` to show icon, any other value to hide
    private Boolean nosim;

    public Boolean airplane() {
        return airplane;
    }


    public Network airplane(Boolean airplane) {
        this.airplane = airplane;
        return this;
    }

    public Boolean fully() {
        return fully;
    }

    public Network fully(Boolean fully) {
        this.fully = fully;
        return this;
    }

    public Boolean wifi() {
        return wifi;
    }

    public Network wifi(Boolean wifi) {
        this.wifi = wifi;
        return this;
    }

    public Integer wifiLevel() {
        return wifiLevel;
    }

    public Network wifiLevel(Integer wifiLevel) {
        this.wifiLevel = wifiLevel;
        return this;
    }

    public Boolean mobile() {
        return mobile;
    }

    public Network mobile(Boolean mobile) {
        this.mobile = mobile;
        return this;
    }

    public String mobileDatatype() {
        return mobileDatatype;
    }

    public Network mobileDatatype(String mobileDatatype) {
        this.mobileDatatype = mobileDatatype;
        return this;
    }

    public Integer mobileLevel() {
        return mobileLevel;
    }

    public Network mobileLevel(Integer mobileLevel) {
        this.mobileLevel = mobileLevel;
        return this;
    }

    public Boolean carriernetworkchange() {
        return carriernetworkchange;
    }

    public Network carriernetworkchange(Boolean carriernetworkchange) {
        this.carriernetworkchange = carriernetworkchange;
        return this;
    }

    public Integer sims() {
        return sims;
    }

    public Network sims(Integer sims) {
        this.sims = sims;
        return this;
    }

    public Boolean nosim() {
        return nosim;
    }

    public Network nosim(Boolean nosim) {
        this.nosim = nosim;
        return this;
    }

    @Override
    public String toString() {
        return "Network{" +
                "airplane=" + airplane +
                ", fully=" + fully +
                ", wifi=" + wifi +
                ", wifiLevel=" + wifiLevel +
                ", mobile=" + mobile +
                ", mobileDatatype='" + mobileDatatype + '\'' +
                ", mobileLevel=" + mobileLevel +
                ", carriernetworkchange=" + carriernetworkchange +
                ", sims=" + sims +
                ", nosim=" + nosim +
                '}';
    }
}
