package com.lixiaomi.baselibapplication.bean;

import java.util.List;

/**
 * Description: 无限层json的Bean类设计<br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-11-27<br>
 * Time: 16:44<br>
 * UpdateDescription：<br>
 */
public class JsonTestBean {


    /**
     * code : 0
     * message : 成功
     * data : {"structureContactName":"石家庄_小米1","structureChildren":[{"structureContactName":"桥西_小米1","structureChildren":[{"structureContactName":"振头_小米1","structureChildren":[],"structureLevel":1,"structureName":"桥西区振头国土资源中心","structureParentName":"桥西区国土资源分局","structureId":"6","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222203","userLoginName":"qxxiaomi1","userName":"桥西_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222204","userLoginName":"qxxiaomi2","userName":"桥西_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222205","userLoginName":"qxxiaomi3","userName":"桥西_小米3"}],"structureEmail":"1111@163.com","structureContactPhone":"15222222209","structureParentId":"2","structureAddress":"桥西区新石北路与友谊大街交叉口","structureIsLast":1},{"structureContactName":"新石_小米1","structureChildren":[],"structureLevel":1,"structureName":"桥西区新石国土资源中心","structureParentName":"桥西区国土资源分局","structureId":"7","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222203","userLoginName":"qxxiaomi1","userName":"桥西_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222204","userLoginName":"qxxiaomi2","userName":"桥西_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222205","userLoginName":"qxxiaomi3","userName":"桥西_小米3"}],"structureEmail":"1112@qq.com","structureContactPhone":"15222222212","structureParentId":"2","structureAddress":"桥西区新石北路与友谊大街交叉口1","structureIsLast":1}],"structureLevel":1,"structureName":"桥西区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"2","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"111@163.com","structureContactPhone":"15222222203","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路111号","structureIsLast":0},{"structureContactName":"裕华_小米1","structureChildren":[],"structureLevel":1,"structureName":"裕华区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"3","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"112@163.com","structureContactPhone":"15222222206","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路112号","structureIsLast":0},{"structureContactName":"长安_小米1","structureChildren":[],"structureLevel":1,"structureName":"长安区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"4","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"113@163.com","structureContactPhone":"15222222207","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路113号","structureIsLast":0},{"structureContactName":"鹿泉_小米1","structureChildren":[],"structureLevel":1,"structureName":"鹿泉区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"5","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"114@163.com","structureContactPhone":"15222222208","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路114号","structureIsLast":0}],"structureLevel":1,"structureName":"石家庄市国土资源局","structureId":"1","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"110@163.com","structureContactPhone":"15222222200","structureParentId":"0","structureAddress":"石家庄市桥西区新石中路110号","structureIsLast":0}
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
         * structureContactName : 石家庄_小米1
         * structureChildren : [{"structureContactName":"桥西_小米1","structureChildren":[{"structureContactName":"振头_小米1","structureChildren":[],"structureLevel":1,"structureName":"桥西区振头国土资源中心","structureParentName":"桥西区国土资源分局","structureId":"6","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222203","userLoginName":"qxxiaomi1","userName":"桥西_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222204","userLoginName":"qxxiaomi2","userName":"桥西_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222205","userLoginName":"qxxiaomi3","userName":"桥西_小米3"}],"structureEmail":"1111@163.com","structureContactPhone":"15222222209","structureParentId":"2","structureAddress":"桥西区新石北路与友谊大街交叉口","structureIsLast":1},{"structureContactName":"新石_小米1","structureChildren":[],"structureLevel":1,"structureName":"桥西区新石国土资源中心","structureParentName":"桥西区国土资源分局","structureId":"7","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222203","userLoginName":"qxxiaomi1","userName":"桥西_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222204","userLoginName":"qxxiaomi2","userName":"桥西_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222205","userLoginName":"qxxiaomi3","userName":"桥西_小米3"}],"structureEmail":"1112@qq.com","structureContactPhone":"15222222212","structureParentId":"2","structureAddress":"桥西区新石北路与友谊大街交叉口1","structureIsLast":1}],"structureLevel":1,"structureName":"桥西区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"2","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"111@163.com","structureContactPhone":"15222222203","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路111号","structureIsLast":0},{"structureContactName":"裕华_小米1","structureChildren":[],"structureLevel":1,"structureName":"裕华区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"3","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"112@163.com","structureContactPhone":"15222222206","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路112号","structureIsLast":0},{"structureContactName":"长安_小米1","structureChildren":[],"structureLevel":1,"structureName":"长安区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"4","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"113@163.com","structureContactPhone":"15222222207","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路113号","structureIsLast":0},{"structureContactName":"鹿泉_小米1","structureChildren":[],"structureLevel":1,"structureName":"鹿泉区国土资源分局","structureParentName":"石家庄市国土资源局","structureId":"5","structurePeople":[{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}],"structureEmail":"114@163.com","structureContactPhone":"15222222208","structureParentId":"1","structureAddress":"石家庄市桥西区新石中路114号","structureIsLast":0}]
         * structureLevel : 1
         * structureName : 石家庄市国土资源局
         * structureId : 1
         * structurePeople : [{"userSex":1,"userState":1,"userPhone":"15222222200","userLoginName":"sjzxiaomi1","userName":"石家庄_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222201","userLoginName":"sjzxiaomi2","userName":"石家庄_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222202","userLoginName":"sjzxiaomi3","userName":"石家庄_小米3"}]
         * structureEmail : 110@163.com
         * structureContactPhone : 15222222200
         * structureParentId : 0
         * structureAddress : 石家庄市桥西区新石中路110号
         * structureIsLast : 0
         */

        private String structureContactName;
        private int structureLevel;
        private String structureName;
        private String structureId;
        private String structureEmail;
        private String structureContactPhone;
        private String structureParentId;
        private String structureAddress;
        private int structureIsLast;
        private List<StructureChildrenBean> structureChildren;
        private List<StructurePeopleBean> structurePeople;

        public String getStructureContactName() {
            return structureContactName;
        }

        public void setStructureContactName(String structureContactName) {
            this.structureContactName = structureContactName;
        }

        public int getStructureLevel() {
            return structureLevel;
        }

        public void setStructureLevel(int structureLevel) {
            this.structureLevel = structureLevel;
        }

        public String getStructureName() {
            return structureName;
        }

        public void setStructureName(String structureName) {
            this.structureName = structureName;
        }

        public String getStructureId() {
            return structureId;
        }

        public void setStructureId(String structureId) {
            this.structureId = structureId;
        }

        public String getStructureEmail() {
            return structureEmail;
        }

        public void setStructureEmail(String structureEmail) {
            this.structureEmail = structureEmail;
        }

        public String getStructureContactPhone() {
            return structureContactPhone;
        }

        public void setStructureContactPhone(String structureContactPhone) {
            this.structureContactPhone = structureContactPhone;
        }

        public String getStructureParentId() {
            return structureParentId;
        }

        public void setStructureParentId(String structureParentId) {
            this.structureParentId = structureParentId;
        }

        public String getStructureAddress() {
            return structureAddress;
        }

        public void setStructureAddress(String structureAddress) {
            this.structureAddress = structureAddress;
        }

        public int getStructureIsLast() {
            return structureIsLast;
        }

        public void setStructureIsLast(int structureIsLast) {
            this.structureIsLast = structureIsLast;
        }

        public List<StructureChildrenBean> getStructureChildren() {
            return structureChildren;
        }

        public void setStructureChildren(List<StructureChildrenBean> structureChildren) {
            this.structureChildren = structureChildren;
        }

        public List<StructurePeopleBean> getStructurePeople() {
            return structurePeople;
        }

        public void setStructurePeople(List<StructurePeopleBean> structurePeople) {
            this.structurePeople = structurePeople;
        }

        public static class StructureChildrenBean {
            /**
             * structureContactName : 振头_小米1
             * structureChildren : []
             * structureLevel : 1
             * structureName : 桥西区振头国土资源中心
             * structureParentName : 桥西区国土资源分局
             * structureId : 6
             * structurePeople : [{"userSex":1,"userState":1,"userPhone":"15222222203","userLoginName":"qxxiaomi1","userName":"桥西_小米1"},{"userSex":1,"userState":1,"userPhone":"15222222204","userLoginName":"qxxiaomi2","userName":"桥西_小米2"},{"userSex":1,"userState":1,"userPhone":"15222222205","userLoginName":"qxxiaomi3","userName":"桥西_小米3"}]
             * structureEmail : 1111@163.com
             * structureContactPhone : 15222222209
             * structureParentId : 2
             * structureAddress : 桥西区新石北路与友谊大街交叉口
             * structureIsLast : 1
             */

            private String structureContactName;
            private int structureLevel;
            private String structureName;
            private String structureParentName;
            private String structureId;
            private String structureEmail;
            private String structureContactPhone;
            private String structureParentId;
            private String structureAddress;
            private int structureIsLast;
            private List<StructureChildrenBean> structureChildren;
            private List<StructurePeopleBean> structurePeople;

            public String getStructureContactName() {
                return structureContactName;
            }

            public void setStructureContactName(String structureContactName) {
                this.structureContactName = structureContactName;
            }

            public int getStructureLevel() {
                return structureLevel;
            }

            public void setStructureLevel(int structureLevel) {
                this.structureLevel = structureLevel;
            }

            public String getStructureName() {
                return structureName;
            }

            public void setStructureName(String structureName) {
                this.structureName = structureName;
            }

            public String getStructureParentName() {
                return structureParentName;
            }

            public void setStructureParentName(String structureParentName) {
                this.structureParentName = structureParentName;
            }

            public String getStructureId() {
                return structureId;
            }

            public void setStructureId(String structureId) {
                this.structureId = structureId;
            }

            public String getStructureEmail() {
                return structureEmail;
            }

            public void setStructureEmail(String structureEmail) {
                this.structureEmail = structureEmail;
            }

            public String getStructureContactPhone() {
                return structureContactPhone;
            }

            public void setStructureContactPhone(String structureContactPhone) {
                this.structureContactPhone = structureContactPhone;
            }

            public String getStructureParentId() {
                return structureParentId;
            }

            public void setStructureParentId(String structureParentId) {
                this.structureParentId = structureParentId;
            }

            public String getStructureAddress() {
                return structureAddress;
            }

            public void setStructureAddress(String structureAddress) {
                this.structureAddress = structureAddress;
            }

            public int getStructureIsLast() {
                return structureIsLast;
            }

            public void setStructureIsLast(int structureIsLast) {
                this.structureIsLast = structureIsLast;
            }

            public List<StructureChildrenBean> getStructureChildren() {
                return structureChildren;
            }

            public void setStructureChildren(List<StructureChildrenBean> structureChildren) {
                this.structureChildren = structureChildren;
            }

            public List<StructurePeopleBean> getStructurePeople() {
                return structurePeople;
            }

            public void setStructurePeople(List<StructurePeopleBean> structurePeople) {
                this.structurePeople = structurePeople;
            }
        }

        public static class StructurePeopleBean {
            /**
             * userSex : 1
             * userState : 1
             * userPhone : 15222222200
             * userLoginName : sjzxiaomi1
             * userName : 石家庄_小米1
             */

            private int userSex;
            private int userState;
            private String userPhone;
            private String userLoginName;
            private String userName;

            public int getUserSex() {
                return userSex;
            }

            public void setUserSex(int userSex) {
                this.userSex = userSex;
            }

            public int getUserState() {
                return userState;
            }

            public void setUserState(int userState) {
                this.userState = userState;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getUserLoginName() {
                return userLoginName;
            }

            public void setUserLoginName(String userLoginName) {
                this.userLoginName = userLoginName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
