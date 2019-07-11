package com.lixiaomi.baselibapplication.bean;

/**
 * 类描述：登陆接口
 * 作  者：Admin or 李小米
 * 时  间：2017/10/11
 * 修改备注：
 */
public class LoginBean {


    /**
     * code : 0
     * message : 成功
     * data : {"appVersion":{"Description":"asdasd","isMust":0,"VersionName":"1.0.0","VersionNumber":2,"url":"download"},"userNickName":"admin","token":"z7GYOsiVIYPDhAGG4mGQ"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appVersion : {"Description":"asdasd","isMust":0,"VersionName":"1.0.0","VersionNumber":2,"url":"download"}
         * userNickName : admin
         * token : z7GYOsiVIYPDhAGG4mGQ
         */

        private AppVersionBean appVersion;
        private String userNickName;
        private String token;

        public AppVersionBean getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(AppVersionBean appVersion) {
            this.appVersion = appVersion;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class AppVersionBean {
            /**
             * Description : asdasd
             * isMust : 0
             * VersionName : 1.0.0
             * VersionNumber : 2
             * url : download
             */

            private String Description;
            private int isMust;
            private String VersionName;
            private int VersionNumber;
            private String url;

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public int getIsMust() {
                return isMust;
            }

            public void setIsMust(int isMust) {
                this.isMust = isMust;
            }

            public String getVersionName() {
                return VersionName;
            }

            public void setVersionName(String VersionName) {
                this.VersionName = VersionName;
            }

            public int getVersionNumber() {
                return VersionNumber;
            }

            public void setVersionNumber(int VersionNumber) {
                this.VersionNumber = VersionNumber;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
