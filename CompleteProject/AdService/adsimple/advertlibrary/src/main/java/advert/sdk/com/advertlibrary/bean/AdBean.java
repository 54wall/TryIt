package advert.sdk.com.advertlibrary.bean;

import java.util.List;

public class AdBean {
    /**
     * {
     * <p>
     * "interval": 4,
     * "version": "0.1",
     * "adsBannerList": [
     * {
     * "name": "banner_0.jpg"
     * },
     * {
     * "name": "banner_1.jpg"
     * },
     * {
     * "name": "banner_2.jpg"
     * }
     * ],
     * "adsFullList": [
     * {
     * "name": "full_0.jpg"
     * },
     * {
     * "name": "full_1.jpg"
     * },
     * {
     * "name": "full_2.jpg"
     * },
     * {
     * "name": "full_3.jpg"
     * }
     * ]
     * }
     */
    private int interval;
    private String version;
    private List<AdsBannerListBean> adsBannerList;
    private List<AdsFullListBean> adsFullList;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<AdsBannerListBean> getAdsBannerList() {
        return adsBannerList;
    }

    public void setAdsBannerList(List<AdsBannerListBean> adsBannerList) {
        this.adsBannerList = adsBannerList;
    }

    public List<AdsFullListBean> getAdsFullList() {
        return adsFullList;
    }

    public void setAdsFullList(List<AdsFullListBean> adsFullList) {
        this.adsFullList = adsFullList;
    }

    public static class AdsBannerListBean {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class AdsFullListBean {
        /**
         * name : food_0.jpg
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
