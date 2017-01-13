package com.jsqix.yunpos.app.bean.hb;

/**
 * Created by dongqing on 2016/12/19.
 */

public class LoginSmsCodeResult {

    /**
     * TCA_FLG : 0
     * RSP_ID : SUC
     * MBL_NO : 13915506121
     * RSP_EX_TIME : 90
     * TXN_CD : PTS0011803
     * RSP_PAG : /common/jsp/json.jsp
     * GWA : {"CUR_AC_DT":"20161219","TX_TYP":"","MBL_NO":"13915506121","LAST_AC_DT_FLG":"D","TRM_ID":"","LOG":{"MSG_ID":"OPTSURM1URMOLN100000000354408","STE":"[Transaction:[null](TID000000000000098520), If(TID000000000000098540), If(TID000000000000098541), Exec:[CMM:GetMobilNoSegment](TID000000000000098543)]","REG_ID":"URMOLN_104","END_TM":"1482119236985","NOD_ID":"URMOLN","TX_CD":"PTS0011803","SVR_NM":"OPTSURM1"},"CNL_TX_CD":"","OPR_ID":"","MSG_DAT":"","TX_CD":"PTS0011803","OLD_JRN_NO":"","JRN_SEQ":"1","MSG_TYP":"N","MSG_CD":"URM00000","LAST_AC_DT2_FLG":"D","MSG_INF":"","VIEWCODE":"json","DOMAIN":"www.cmpay.com","AC_DT":"20161219","BUS_TYP":"NMOP","PRD_CD":"","UENV":{"IP":"221.224.198.226"},"LAST_AC_DT":"20161218","TX_DT":"20161219","WIN_SW_TM":"000000","VER_NO":"V1.0.0","WIN_MOD":"1","CUR_AC_DT_FLG":"D","WIN_STS":"N","TX_TM":"114716","NEXT_AC_DT":"20161220","SYS_CNL":"CMPAY","NEXT_AC_DT_FLG":"D","TX_AMT":"","AP_CD":"","WIN_DT2":"20161220","TXN_MOD":"O","WIN_BEG_TM":"230000","WIN_DT1":"20161219","JRN_NO":"201612199354710980","BUS_CNL":"CMPAY","WIN_END_TM":"001000","LAST_AC_DT2":"20161217"}
     */

    private String TCA_FLG;
    private String RSP_ID;
    private String MBL_NO;
    private String RSP_EX_TIME;
    private String TXN_CD;
    private String RSP_PAG;
    private GWABean GWA;

    public String getTCA_FLG() {
        return TCA_FLG;
    }

    public void setTCA_FLG(String TCA_FLG) {
        this.TCA_FLG = TCA_FLG;
    }

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

    public String getRSP_EX_TIME() {
        return RSP_EX_TIME;
    }

    public void setRSP_EX_TIME(String RSP_EX_TIME) {
        this.RSP_EX_TIME = RSP_EX_TIME;
    }

    public String getTXN_CD() {
        return TXN_CD;
    }

    public void setTXN_CD(String TXN_CD) {
        this.TXN_CD = TXN_CD;
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

    public static class GWABean {
        /**
         * CUR_AC_DT : 20161219
         * TX_TYP :
         * MBL_NO : 13915506121
         * LAST_AC_DT_FLG : D
         * TRM_ID :
         * LOG : {"MSG_ID":"OPTSURM1URMOLN100000000354408","STE":"[Transaction:[null](TID000000000000098520), If(TID000000000000098540), If(TID000000000000098541), Exec:[CMM:GetMobilNoSegment](TID000000000000098543)]","REG_ID":"URMOLN_104","END_TM":"1482119236985","NOD_ID":"URMOLN","TX_CD":"PTS0011803","SVR_NM":"OPTSURM1"}
         * CNL_TX_CD :
         * OPR_ID :
         * MSG_DAT :
         * TX_CD : PTS0011803
         * OLD_JRN_NO :
         * JRN_SEQ : 1
         * MSG_TYP : N
         * MSG_CD : URM00000
         * LAST_AC_DT2_FLG : D
         * MSG_INF :
         * VIEWCODE : json
         * DOMAIN : www.cmpay.com
         * AC_DT : 20161219
         * BUS_TYP : NMOP
         * PRD_CD :
         * UENV : {"IP":"221.224.198.226"}
         * LAST_AC_DT : 20161218
         * TX_DT : 20161219
         * WIN_SW_TM : 000000
         * VER_NO : V1.0.0
         * WIN_MOD : 1
         * CUR_AC_DT_FLG : D
         * WIN_STS : N
         * TX_TM : 114716
         * NEXT_AC_DT : 20161220
         * SYS_CNL : CMPAY
         * NEXT_AC_DT_FLG : D
         * TX_AMT :
         * AP_CD :
         * WIN_DT2 : 20161220
         * TXN_MOD : O
         * WIN_BEG_TM : 230000
         * WIN_DT1 : 20161219
         * JRN_NO : 201612199354710980
         * BUS_CNL : CMPAY
         * WIN_END_TM : 001000
         * LAST_AC_DT2 : 20161217
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
        private String MSG_INF;
        private String VIEWCODE;
        private String DOMAIN;
        private String AC_DT;
        private String BUS_TYP;
        private String PRD_CD;
        private UENVBean UENV;
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

        public String getMSG_INF() {
            return MSG_INF;
        }

        public void setMSG_INF(String MSG_INF) {
            this.MSG_INF = MSG_INF;
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

        public UENVBean getUENV() {
            return UENV;
        }

        public void setUENV(UENVBean UENV) {
            this.UENV = UENV;
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
             * MSG_ID : OPTSURM1URMOLN100000000354408
             * STE : [Transaction:[null](TID000000000000098520), If(TID000000000000098540), If(TID000000000000098541), Exec:[CMM:GetMobilNoSegment](TID000000000000098543)]
             * REG_ID : URMOLN_104
             * END_TM : 1482119236985
             * NOD_ID : URMOLN
             * TX_CD : PTS0011803
             * SVR_NM : OPTSURM1
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

        public static class UENVBean {
            /**
             * IP : 221.224.198.226
             */

            private String IP;

            public String getIP() {
                return IP;
            }

            public void setIP(String IP) {
                this.IP = IP;
            }
        }
    }
}
