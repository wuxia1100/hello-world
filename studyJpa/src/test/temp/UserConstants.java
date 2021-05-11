package temp;

public class UserConstants {
    public static final String SYS_CODE = "um.USER";


    /**
     * 消息来源 0-系统
     */
    public static final String MNSLIST_SOURCE_SYSTEM = "0";
    /**
     * 消息来源 1-人工
     */
    public static final String MNSLIST_SOURCE_MANUAL = "1";

    /**
     * Saas服务到期提醒默认时间
     */
    public static final Integer SAAS_REMIND_TIME_DEFAULT = 7;

    /**
     * 缴费产品默认名称
     */
    public static final String PRODUCTNAME_FEE_SAAS = "维金SaaS";


    /**
     * 产品缴费到期提醒 业务代码
     */
    public static final String MNSCONFIG_BUSTYPE_CODE_FEE = "10016";
    /**
     * 产品缴费到期提醒 业务名称
     */
    public static final String MNSCONFIG_BUSTYPE_NAME_FEE = "产品缴费到期提醒";

    /**
     * 用户实名有效期到期提醒 业务代码
     */
    public static final String MNSCONFIG_BUSTYPE_CODE_LOSE = "00013";
    /**
     * 用户实名有效期到期提醒 业务名称
     */
    public static final String MNSCONFIG_BUSTYPE_NAME_LOSE = "用户实名有效期到期提醒";


    /**
     * 用户状态 0-未实名
     */
    public static final String USERINFO_DATASTATE_0 = "0";
    /**
     * 用户状态 1-已实名
     */
    public static final String USERINFO_DATASTATE_1 = "1";

    /**
     * 用户缴费状态 0-未缴费
     */
    public static final String USERINFO_FEESTATUS_0 = "0";
    /**
     * 用户缴费状态 1-已缴费
     */
    public static final String USERINFO_FEESTATUS_1 = "1";


    public static final String YQB_TENANT_CODE = "00000000";
    /**
     * 1:默认收货地址
     */
    public static final String ADDRESS_DEFAULT_0 = "0";
    public static final String ADDRESS_DEFAULT_1 = "1";

    /**
     * 团队与角色展示
     *
     * @author ron
     */
    public static enum RoleTeamEnum {
        FZJL("副总经理", "0001008"),  //副总经理
        XSZJ("案场团队", "0001006"),  //销售总监
        XXZJ("拓展团队", "0001007"),  //行销总监
        XSJL("案场团队", "0001004"),  //销售经理
        XXJL("拓展团队", "0001002");  //行销经理

        private RoleTeamEnum(String roleName, String roleCode) {
            setRoleName(roleName);
            setRoleCode(roleCode);
        }

        private RoleTeamEnum() {

        }

        private String roleName;
        private String roleCode;

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }
    }

    /**
     * 默认分组
     */
    public static final String DEFAULT_GROUP_NAME = "我的团队";
    public static final String DEFAULT_RELATION_TYPE = "1";
    public static final String GROUP_RELATION_TYPE = "2";

    /**
     * 团队成语初始化时使用
     *
     * @author ron
     */
    public static enum RoleEnum {
        /**
         * 副总经理
         */
        FZJL("副总经理", "0001008", "teamLeader"),  //副总经理
        /**
         * 销售总监
         */
        XSZJ("销售总监", "0001006", "teamLeader"),  //销售总监
        /**
         * 鉴定
         */
        JD("鉴定", "0001005", "normal"),  //鉴定
        /**
         * 销售经理
         */
        XSJL("销售经理", "0001004", "teamLeader"),  //销售经理
        /**
         * 置业顾问
         */
        ZYGW("置业顾问", "0001003", "normal"),  //置业顾问
        /**
         * 行销总监
         */
        XXZJ("行销总监", "0001007", "teamLeader"),  //行销总监
        /**
         * 行销经理
         */
        XXJL("行销经理", "0001002", "teamLeader"),  //行销经理
        /**
         * 行销主管
         */
        XXZG("行销主管", "0001001", "normal"),  //行销主管
        /**
         * 行销专员
         */
        XXZY("行销专员", "0001001", "normal"),  //副总经理
        /**
         * 拓展主管
         */
        TZZG("拓展主管", "0001001", "normal");  //副总经理

        private String roleName;
        private String roleCode;
        private String manager;

        private RoleEnum(String roleName, String roleCode, String manager) {
            setRoleName(roleName);
            setRoleCode(roleCode);
            setManager(manager);
        }

        private RoleEnum() {

        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

    }

    /**
     * 租户
     */
    public static final String PARAMS_NO[] = {"0", "UmUserinfo", "userinfoCode", "00000000"};

    public static enum TenantEnum {
        MDK("麦迪康", "2020072700000001");  //副总经理

        private String tenantName;
        private String tenantCode;

        TenantEnum(String tenantName, String tenantCode) {
            this.tenantName = tenantName;
            this.tenantCode = tenantCode;
        }

        TenantEnum() {
        }

        public String getTenantName() {
            return tenantName;
        }

        public void setTenantName(String tenantName) {
            this.tenantName = tenantName;
        }

        public String getTenantCode() {
            return tenantCode;
        }

        public void setTenantCode(String tenantCode) {
            this.tenantCode = tenantCode;
        }
    }


}
