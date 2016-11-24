package com.jsqix.yunpos.app.bean.hb;

/**
 * Created by dongqing on 2016/11/18.
 */

public class PlaceOrderSuccessResult {

    /**
     * RSP_ID : SUC1
     * MBL_NO :
     * MKM_USER_ACT : {"EVENT_FLG":"2","MERC_ID":"888009974090330","PROD_PRICE":"20","PROD_NO":"20160516152834","MBL_NO":"","PROD_NM":"20元天猫购物券","USR_NO":"","MERC_NM":"积分时代","JRN_NO":"201603100764220651"}
     * PAY_URL : http://ipos.10086.cn/wap/index.xhtml?PAY_TYP_FLG=1&SESSIONID=20161118201603100764220651
     * RSP_PAG : /common/jsp/json.jsp
     * GWA : {"CUR_AC_DT":"20160310","TX_TYP":"02","MBL_NO":"","LAST_AC_DT_FLG":"D","TRM_ID":"","LOG":{"MSG_ID":"OMKMWEB1MKMWEB033000000695601","STE":"[Application:[MKM](TID000000000000003012), Transaction:[MWB0101034](TID000000000000005819), Exec:[PUB:GWASetMsg](TID000000000000005842)]","REG_ID":"MKMWEB34","END_TM":"1479454547278","NOD_ID":"MKMWEB","TX_CD":"MWB0101034","SVR_NM":"OMKMWEB1"},"CNL_TX_CD":"","OPR_ID":"","MSG_DAT":"","TX_CD":"MWB0101034","OLD_JRN_NO":"","JRN_SEQ":"1","MSG_TYP":"N","MSG_CD":"RPM00000","LAST_AC_DT2_FLG":"D","VIEWCODE":"json","DOMAIN":"www.cmpay.com","AC_DT":"20160310","BUS_TYP":"0010","PRD_CD":"","LAST_AC_DT":"20310708","TX_DT":"20161118","WIN_SW_TM":"000000","VER_NO":"V1.0.0","WIN_MOD":"1","CUR_AC_DT_FLG":"D","WIN_STS":"B","TX_TM":"153546","NEXT_AC_DT":"20310801","SYS_CNL":"IPS","NEXT_AC_DT_FLG":"D","TX_AMT":"","AP_CD":"","WIN_DT2":"20310801","TXN_MOD":"O","WIN_BEG_TM":"080000","WIN_DT1":"20310731","JRN_NO":"201603100764220651","BUS_CNL":"WAP","WIN_END_TM":"020000","LAST_AC_DT2":"20310707"}
     */

    private String RSP_ID;
    private String MBL_NO;
    private MKMUSERACTBean MKM_USER_ACT;
    private String PAY_URL;
    private String RSP_PAG;
    private GWABean GWA;

    public String getRSP_ID() {
        return RSP_ID;
    }

    public void setRSP_ID(String RSP_ID) {
        this.RSP_ID = RSP_ID;
    }

    public String getMBL_NO() {
        return MBL_NO;
    }

    public void setMBL_NO(String MBL_NO) {
        this.MBL_NO = MBL_NO;
    }

    public MKMUSERACTBean getMKM_USER_ACT() {
        return MKM_USER_ACT;
    }

    public void setMKM_USER_ACT(MKMUSERACTBean MKM_USER_ACT) {
        this.MKM_USER_ACT = MKM_USER_ACT;
    }

    public String getPAY_URL() {
        return PAY_URL;
    }

    public void setPAY_URL(String PAY_URL) {
        this.PAY_URL = PAY_URL;
    }

    public String getRSP_PAG() {
        return RSP_PAG;
    }

    public void setRSP_PAG(String RSP_PAG) {
        this.RSP_PAG = RSP_PAG;
    }

    public GWABean getGWA() {
        return GWA;
    }

    public void setGWA(GWABean GWA) {
        this.GWA = GWA;
    }

    public static class MKMUSERACTBean {
        /**
         * EVENT_FLG : 2
         * MERC_ID : 888009974090330
         * PROD_PRICE : 20
         * PROD_NO : 20160516152834
         * MBL_NO :
         * PROD_NM : 20元天猫购物券
         * USR_NO :
         * MERC_NM : 积分时代
         * JRN_NO : 201603100764220651
         */

        private String EVENT_FLG;
        private String MERC_ID;
        private String PROD_PRICE;
        private String PROD_NO;
        private String MBL_NO;
        private String PROD_NM;
        private String USR_NO;
        private String MERC_NM;
        private String JRN_NO;

        public String getEVENT_FLG() {
            return EVENT_FLG;
        }

        public void setEVENT_FLG(String EVENT_FLG) {
            this.EVENT_FLG = EVENT_FLG;
        }

        public String getMERC_ID() {
            return MERC_ID;
        }

        public void setMERC_ID(String MERC_ID) {
            this.MERC_ID = MERC_ID;
        }

        public String getPROD_PRICE() {
            return PROD_PRICE;
        }

        public void setPROD_PRICE(String PROD_PRICE) {
            this.PROD_PRICE = PROD_PRICE;
        }

        public String getPROD_NO() {
            return PROD_NO;
        }

        public void setPROD_NO(String PROD_NO) {
            this.PROD_NO = PROD_NO;
        }

        public String getMBL_NO() {
            return MBL_NO;
        }

        public void setMBL_NO(String MBL_NO) {
            this.MBL_NO = MBL_NO;
        }

        public String getPROD_NM() {
            return PROD_NM;
        }

        public void setPROD_NM(String PROD_NM) {
            this.PROD_NM = PROD_NM;
        }

        public String getUSR_NO() {
            return USR_NO;
        }

        public void setUSR_NO(String USR_NO) {
            this.USR_NO = USR_NO;
        }

        public String getMERC_NM() {
            return MERC_NM;
        }

        public void setMERC_NM(String MERC_NM) {
            this.MERC_NM = MERC_NM;
        }

        public String getJRN_NO() {
            return JRN_NO;
        }

        public void setJRN_NO(String JRN_NO) {
            this.JRN_NO = JRN_NO;
        }
    }

    public static class GWABean {
        /**
         * CUR_AC_DT : 20160310
         * TX_TYP : 02
         * MBL_NO :
         * LAST_AC_DT_FLG : D
         * TRM_ID :
         * LOG : {"MSG_ID":"OMKMWEB1MKMWEB033000000695601","STE":"[Application:[MKM](TID000000000000003012), Transaction:[MWB0101034](TID000000000000005819), Exec:[PUB:GWASetMsg](TID000000000000005842)]","REG_ID":"MKMWEB34","END_TM":"1479454547278","NOD_ID":"MKMWEB","TX_CD":"MWB0101034","SVR_NM":"OMKMWEB1"}
         * CNL_TX_CD :
         * OPR_ID :
         * MSG_DAT :
         * TX_CD : MWB0101034
         * OLD_JRN_NO :
         * JRN_SEQ : 1
         * MSG_TYP : N
         * MSG_CD : RPM00000
         * LAST_AC_DT2_FLG : D
         * VIEWCODE : json
         * DOMAIN : www.cmpay.com
         * AC_DT : 20160310
         * BUS_TYP : 0010
         * PRD_CD :
         * LAST_AC_DT : 20310708
         * TX_DT : 20161118
         * WIN_SW_TM : 000000
         * VER_NO : V1.0.0
         * WIN_MOD : 1
         * CUR_AC_DT_FLG : D
         * WIN_STS : B
         * TX_TM : 153546
         * NEXT_AC_DT : 20310801
         * SYS_CNL : IPS
         * NEXT_AC_DT_FLG : D
         * TX_AMT :
         * AP_CD :
         * WIN_DT2 : 20310801
         * TXN_MOD : O
         * WIN_BEG_TM : 080000
         * WIN_DT1 : 20310731
         * JRN_NO : 201603100764220651
         * BUS_CNL : WAP
         * WIN_END_TM : 020000
         * LAST_AC_DT2 : 20310707
         */

        private String CUR_AC_DT;
        private String TX_TYP;
        private String MBL_NO;
        private String LAST_AC_DT_FLG;
        private String TRM_ID;
        private LOGBean LOG;
        private String CNL_TX_CD;
        private String OPR_ID;
        private String MSG_DAT;
        private String TX_CD;
        private String OLD_JRN_NO;
        private String JRN_SEQ;
        private String MSG_TYP;
        private String MSG_CD;
        private String LAST_AC_DT2_FLG;
        private String VIEWCODE;
        private String DOMAIN;
        private String AC_DT;
        private String BUS_TYP;
        private String PRD_CD;
        private String LAST_AC_DT;
        private String TX_DT;
        private String WIN_SW_TM;
        private String VER_NO;
        private String WIN_MOD;
        private String CUR_AC_DT_FLG;
        private String WIN_STS;
        private String TX_TM;
        private String NEXT_AC_DT;
        private String SYS_CNL;
        private String NEXT_AC_DT_FLG;
        private String TX_AMT;
        private String AP_CD;
        private String WIN_DT2;
        private String TXN_MOD;
        private String WIN_BEG_TM;
        private String WIN_DT1;
        private String JRN_NO;
        private String BUS_CNL;
        private String WIN_END_TM;
        private String LAST_AC_DT2;

        public String getCUR_AC_DT() {
            return CUR_AC_DT;
        }

        public void setCUR_AC_DT(String CUR_AC_DT) {
            this.CUR_AC_DT = CUR_AC_DT;
        }

        public String getTX_TYP() {
            return TX_TYP;
        }

        public void setTX_TYP(String TX_TYP) {
            this.TX_TYP = TX_TYP;
        }

        public String getMBL_NO() {
            return MBL_NO;
        }

        public void setMBL_NO(String MBL_NO) {
            this.MBL_NO = MBL_NO;
        }

        public String getLAST_AC_DT_FLG() {
            return LAST_AC_DT_FLG;
        }

        public void setLAST_AC_DT_FLG(String LAST_AC_DT_FLG) {
            this.LAST_AC_DT_FLG = LAST_AC_DT_FLG;
        }

        public String getTRM_ID() {
            return TRM_ID;
        }

        public void setTRM_ID(String TRM_ID) {
            this.TRM_ID = TRM_ID;
        }

        public LOGBean getLOG() {
            return LOG;
        }

        public void setLOG(LOGBean LOG) {
            this.LOG = LOG;
        }

        public String getCNL_TX_CD() {
            return CNL_TX_CD;
        }

        public void setCNL_TX_CD(String CNL_TX_CD) {
            this.CNL_TX_CD = CNL_TX_CD;
        }

        public String getOPR_ID() {
            return OPR_ID;
        }

        public void setOPR_ID(String OPR_ID) {
            this.OPR_ID = OPR_ID;
        }

        public String getMSG_DAT() {
            return MSG_DAT;
        }

        public void setMSG_DAT(String MSG_DAT) {
            this.MSG_DAT = MSG_DAT;
        }

        public String getTX_CD() {
            return TX_CD;
        }

        public void setTX_CD(String TX_CD) {
            this.TX_CD = TX_CD;
        }

        public String getOLD_JRN_NO() {
            return OLD_JRN_NO;
        }

        public void setOLD_JRN_NO(String OLD_JRN_NO) {
            this.OLD_JRN_NO = OLD_JRN_NO;
        }

        public String getJRN_SEQ() {
            return JRN_SEQ;
        }

        public void setJRN_SEQ(String JRN_SEQ) {
            this.JRN_SEQ = JRN_SEQ;
        }

        public String getMSG_TYP() {
            return MSG_TYP;
        }

        public void setMSG_TYP(String MSG_TYP) {
            this.MSG_TYP = MSG_TYP;
        }

        public String getMSG_CD() {
            return MSG_CD;
        }

        public void setMSG_CD(String MSG_CD) {
            this.MSG_CD = MSG_CD;
        }

        public String getLAST_AC_DT2_FLG() {
            return LAST_AC_DT2_FLG;
        }

        public void setLAST_AC_DT2_FLG(String LAST_AC_DT2_FLG) {
            this.LAST_AC_DT2_FLG = LAST_AC_DT2_FLG;
        }

        public String getVIEWCODE() {
            return VIEWCODE;
        }

        public void setVIEWCODE(String VIEWCODE) {
            this.VIEWCODE = VIEWCODE;
        }

        public String getDOMAIN() {
            return DOMAIN;
        }

        public void setDOMAIN(String DOMAIN) {
            this.DOMAIN = DOMAIN;
        }

        public String getAC_DT() {
            return AC_DT;
        }

        public void setAC_DT(String AC_DT) {
            this.AC_DT = AC_DT;
        }

        public String getBUS_TYP() {
            return BUS_TYP;
        }

        public void setBUS_TYP(String BUS_TYP) {
            this.BUS_TYP = BUS_TYP;
        }

        public String getPRD_CD() {
            return PRD_CD;
        }

        public void setPRD_CD(String PRD_CD) {
            this.PRD_CD = PRD_CD;
        }

        public String getLAST_AC_DT() {
            return LAST_AC_DT;
        }

        public void setLAST_AC_DT(String LAST_AC_DT) {
            this.LAST_AC_DT = LAST_AC_DT;
        }

        public String getTX_DT() {
            return TX_DT;
        }

        public void setTX_DT(String TX_DT) {
            this.TX_DT = TX_DT;
        }

        public String getWIN_SW_TM() {
            return WIN_SW_TM;
        }

        public void setWIN_SW_TM(String WIN_SW_TM) {
            this.WIN_SW_TM = WIN_SW_TM;
        }

        public String getVER_NO() {
            return VER_NO;
        }

        public void setVER_NO(String VER_NO) {
            this.VER_NO = VER_NO;
        }

        public String getWIN_MOD() {
            return WIN_MOD;
        }

        public void setWIN_MOD(String WIN_MOD) {
            this.WIN_MOD = WIN_MOD;
        }

        public String getCUR_AC_DT_FLG() {
            return CUR_AC_DT_FLG;
        }

        public void setCUR_AC_DT_FLG(String CUR_AC_DT_FLG) {
            this.CUR_AC_DT_FLG = CUR_AC_DT_FLG;
        }

        public String getWIN_STS() {
            return WIN_STS;
        }

        public void setWIN_STS(String WIN_STS) {
            this.WIN_STS = WIN_STS;
        }

        public String getTX_TM() {
            return TX_TM;
        }

        public void setTX_TM(String TX_TM) {
            this.TX_TM = TX_TM;
        }

        public String getNEXT_AC_DT() {
            return NEXT_AC_DT;
        }

        public void setNEXT_AC_DT(String NEXT_AC_DT) {
            this.NEXT_AC_DT = NEXT_AC_DT;
        }

        public String getSYS_CNL() {
            return SYS_CNL;
        }

        public void setSYS_CNL(String SYS_CNL) {
            this.SYS_CNL = SYS_CNL;
        }

        public String getNEXT_AC_DT_FLG() {
            return NEXT_AC_DT_FLG;
        }

        public void setNEXT_AC_DT_FLG(String NEXT_AC_DT_FLG) {
            this.NEXT_AC_DT_FLG = NEXT_AC_DT_FLG;
        }

        public String getTX_AMT() {
            return TX_AMT;
        }

        public void setTX_AMT(String TX_AMT) {
            this.TX_AMT = TX_AMT;
        }

        public String getAP_CD() {
            return AP_CD;
        }

        public void setAP_CD(String AP_CD) {
            this.AP_CD = AP_CD;
        }

        public String getWIN_DT2() {
            return WIN_DT2;
        }

        public void setWIN_DT2(String WIN_DT2) {
            this.WIN_DT2 = WIN_DT2;
        }

        public String getTXN_MOD() {
            return TXN_MOD;
        }

        public void setTXN_MOD(String TXN_MOD) {
            this.TXN_MOD = TXN_MOD;
        }

        public String getWIN_BEG_TM() {
            return WIN_BEG_TM;
        }

        public void setWIN_BEG_TM(String WIN_BEG_TM) {
            this.WIN_BEG_TM = WIN_BEG_TM;
        }

        public String getWIN_DT1() {
            return WIN_DT1;
        }

        public void setWIN_DT1(String WIN_DT1) {
            this.WIN_DT1 = WIN_DT1;
        }

        public String getJRN_NO() {
            return JRN_NO;
        }

        public void setJRN_NO(String JRN_NO) {
            this.JRN_NO = JRN_NO;
        }

        public String getBUS_CNL() {
            return BUS_CNL;
        }

        public void setBUS_CNL(String BUS_CNL) {
            this.BUS_CNL = BUS_CNL;
        }

        public String getWIN_END_TM() {
            return WIN_END_TM;
        }

        public void setWIN_END_TM(String WIN_END_TM) {
            this.WIN_END_TM = WIN_END_TM;
        }

        public String getLAST_AC_DT2() {
            return LAST_AC_DT2;
        }

        public void setLAST_AC_DT2(String LAST_AC_DT2) {
            this.LAST_AC_DT2 = LAST_AC_DT2;
        }

        public static class LOGBean {
            /**
             * MSG_ID : OMKMWEB1MKMWEB033000000695601
             * STE : [Application:[MKM](TID000000000000003012), Transaction:[MWB0101034](TID000000000000005819), Exec:[PUB:GWASetMsg](TID000000000000005842)]
             * REG_ID : MKMWEB34
             * END_TM : 1479454547278
             * NOD_ID : MKMWEB
             * TX_CD : MWB0101034
             * SVR_NM : OMKMWEB1
             */

            private String MSG_ID;
            private String STE;
            private String REG_ID;
            private String END_TM;
            private String NOD_ID;
            private String TX_CD;
            private String SVR_NM;

            public String getMSG_ID() {
                return MSG_ID;
            }

            public void setMSG_ID(String MSG_ID) {
                this.MSG_ID = MSG_ID;
            }

            public String getSTE() {
                return STE;
            }

            public void setSTE(String STE) {
                this.STE = STE;
            }

            public String getREG_ID() {
                return REG_ID;
            }

            public void setREG_ID(String REG_ID) {
                this.REG_ID = REG_ID;
            }

            public String getEND_TM() {
                return END_TM;
            }

            public void setEND_TM(String END_TM) {
                this.END_TM = END_TM;
            }

            public String getNOD_ID() {
                return NOD_ID;
            }

            public void setNOD_ID(String NOD_ID) {
                this.NOD_ID = NOD_ID;
            }

            public String getTX_CD() {
                return TX_CD;
            }

            public void setTX_CD(String TX_CD) {
                this.TX_CD = TX_CD;
            }

            public String getSVR_NM() {
                return SVR_NM;
            }

            public void setSVR_NM(String SVR_NM) {
                this.SVR_NM = SVR_NM;
            }
        }
    }
}
