package dong.ognl;

import com.alibaba.fastjson.JSONObject;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

/**
 * @program: java-deep
 * @description 工具类：使用Ognl对象图导航语言,获取JSON多层嵌套数据
 * @author: DONGSHILEI
 * @create: 2020/5/20 16:26
 **/
public class OgnlUtil {
    private OgnlContext context;

    public OgnlUtil context(OgnlContext context) {
        this.context = context;
        return this;
    }

    public static OgnlUtil build() {
        return new OgnlUtil();
    }

    public OgnlUtil add(JSONObject source) {
        return this.add(null, source);
    }

    public OgnlUtil add(String key, JSONObject source) {
        if (this.context == null) {
            this.context = new OgnlContext();
        }
        if (key == null) {
            this.context.setRoot(source);
        } else {
            this.context.put(key, source);
        }
        return this;
    }

    public static Object getValue(OgnlContext context, String dsl) throws OgnlException {
        Object mapValue = Ognl.getValue(dsl, context, context.getRoot());
        return mapValue;
    }

    public Object getValue(String dsl) throws OgnlException {
        Object mapValue = Ognl.getValue(dsl, this.context, this.context.getRoot());
        return mapValue;
    }

    /**
     * 将json数据放到context中，并获取特定的值
     */
    @Test
    public void test1() {
        String jsonStr = "{\"body\":{\"accountInfo\":{\"loanBankCode\":\"0308\",\"repayBankProvince\":\"\",\"loanBankProvince\":\"\",\"loanPrivatePublicAccount\":\"01\",\"loanTrusteeType\":\"B134003\"},\"contactInfo\":[{\"relativeName\":\"王一\",\"relativeCompanyAddress\":\"来广营\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994747\"},{\"relativeName\":\"王二\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994848\"}],\"professionInfo\":{\"incomeLevelCode\":\"B20202\",\"professionCode\":\"F12326\",\"incomeSourceCode\":\"B20305\",\"duty\":\"B2910\",\"yearIncome\":48000,\"industryCode\":\"B1036\"}}}";
        JSONObject source = JSONObject.parseObject(jsonStr);
        OgnlContext context = new OgnlContext();
        // 将json数据放入context的map中，key值自定义
        OgnlUtil ognlUtil = OgnlUtil.build().context(context).add("source", source);
        try {
            //注意:dsl要带上map的自定义的key
            Object value = ognlUtil.getValue("#source.body.accountInfo.loanBankCode");
            System.out.println("body.accountInfo.loanBankCode:"+value);
            //获取JsonArray中的值
            Object value1 = ognlUtil.getValue("#source.body.contactInfo[0].relativeName");
            System.out.println("body.contactInfo[0].relativeName:"+value1);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取json中特定的值
     */
    @Test
    public void test2() {
        String jsonStr = "{\"body\":{\"accountInfo\":{\"loanBankCode\":\"0308\",\"repayBankProvince\":\"\",\"loanBankProvince\":\"\",\"loanPrivatePublicAccount\":\"01\",\"loanTrusteeType\":\"B134003\",\"repayAccountType\":\"AT02\",\"repayTrusteeType\":\"B134003\",\"loanBankName\":\"\",\"repayAccountOpenNo\":\"\",\"repayBankName\":\"\",\"repayAccountNo\":\"6225880127160006\",\"loanBankCity\":\"1000\",\"repayBankCity\":\"1000\",\"repayAccountPhone\":\"18612994579\",\"loanAccountNo\":\"6225880127160006\",\"repayAccountName\":\"陈建美\",\"repayBankCode\":\"0308\",\"loanAccountPhone\":\"18612994579\",\"loanAccountName\":\"陈建美\",\"loanAccountOpenNo\":\"\",\"loanAccountType\":\"AT01\",\"repayPrivatePublicAccount\":\"01\"},\"contactInfo\":[{\"relativeName\":\"王一\",\"relativeCompanyAddress\":\"来广营\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994747\"},{\"relativeName\":\"王二\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994848\"}],\"professionInfo\":{\"incomeLevelCode\":\"B20202\",\"professionCode\":\"F12326\",\"incomeSourceCode\":\"B20305\",\"duty\":\"B2910\",\"yearIncome\":48000,\"industryCode\":\"B1036\"},\"censusInfoInfo\":{\"validDateBegin\":\"2017-08-21\",\"address\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"authority\":\"丰宁满族自治县公安局\",\"localResid\":\"F14901\",\"validDateEnd\":\"2037-08-21\",\"validDateEndSpecial\":\"2037-08-21\",\"validDateSpecial\":\"20170821-20370821\"},\"transInfo\":{\"businessNo\":\"1212147561\",\"fundApplyNo\":\"1_20200511000000000002\",\"applyNo\":\"5011202005200249053590009285\",\"merchTransNo\":\"1175221219026000\"},\"imageInfo\":{\"imageFilePathAndName\":\"/home/xabk_jf/image/creditAuth/20200520/130826198709136320_04.pdf\",\"imageFileDate\":\"2020-05-20 14:48:50\",\"imageFileName\":\"130826198709136320_04.pdf\",\"imageFilePath\":\"/home/xabk_jf/image/creditAuth/20200520\"},\"contractInfoList\":[{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:32\",\"contractType\":\"F19402\",\"contractFileid\":\"fa9dc8f5-977e-4dd6-8fc1-0d543df2f630\",\"contractId\":\"256\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"CA个人数字证书申请表及授权委托书（消金）\",\"contractVersion\":\"20191206142640\"},{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:32\",\"contractType\":\"F19403\",\"contractFileid\":\"93b732fa-9aeb-4a42-8af1-647d77e28bfb\",\"contractId\":\"247\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"征信及信息披露授权书（消金）\",\"contractVersion\":\"20191209185115\"},{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:33\",\"contractType\":\"F19403\",\"contractFileid\":\"521d67a4-d86c-4757-b89c-b75482beaaaa\",\"contractId\":\"558\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"个人信用报告查询授权书(西安)\",\"contractVersion\":\"20200410143223\"}],\"workUnitInfo\":{\"companyCity\":\"110100\",\"companyPhone\":\"\",\"companyType\":\"B0908\",\"companyAddress\":\"来广营\",\"company\":\"玖富数科\",\"companyCounty\":\"110105\",\"companyProvince\":\"110000\"},\"recordLoanAttach\":[{\"attachType\":\"0\",\"attachName\":\"130826198709136320_01.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"1\",\"attachName\":\"130826198709136320_02.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"11001578\",\"attachName\":\"130826198709136320_01.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"11001579\",\"attachName\":\"130826198709136320_02.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"2\",\"attachName\":\"130826198709136320_03.jpg\",\"fileId\":\"/home/xabk_jf/image/faceCognition/20200520\"},{\"attachType\":\"558\",\"attachName\":\"130826198709136320_04.pdf\",\"fileId\":\"/home/xabk_jf/image/creditAuth/20200520\"}],\"orderAttachInfoList\":[{\"creator\":\"ROOT\",\"attachType\":\"11001578\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"正面.jpg\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28848,\"fileType\":\"jpg\",\"fileId\":\"2a1f341b-3710-4b50-b32b-54184659e633\"},{\"creator\":\"ROOT\",\"attachType\":\"11001579\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"反面.jpg\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28849,\"fileType\":\"jpg\",\"fileId\":\"ead7517b-a472-4ee6-91b0-55db2a1770f1\"},{\"creator\":\"ROOT\",\"attachType\":\"11001503\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"人脸照片\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28850,\"fileType\":\"jpg\",\"fileId\":\"cec71a98-6ed1-429d-ab69-ae1d64d14f1b\"}],\"custInfo\":{\"birthDay\":\"1987-09-13\",\"faceRecognitionEndDate\":\"2020-05-12 14:07:10\",\"certType\":\"01\",\"custNo\":\"1000001809\",\"nation\":\"满\",\"sex\":\"F\",\"educationCode\":\"B0305\",\"certId\":\"130826198709136320\",\"certAddress\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"customerName\":\"陈建美\",\"phoneNo\":\"18612994579\",\"marriageCode\":\"B0501\",\"custLabel\":\"WKYZ\",\"email\":\"18612994579@wo.cn\",\"age\":32},\"communicationInfo\":{\"liveCounty\":\"110105\",\"liveAddress\":\"北京市北京市朝阳区融新科技中心B座\",\"liveProvince\":\"110000\",\"liveCity\":\"110100\"},\"insureDB\":{\"insureNum\":\"\",\"insureName\":\"江西华章汉辰担保集团股份有限公司\",\"insureCode\":\"HanChenJF\",\"insureRate\":0.1292},\"accountInfoList\":[{\"accountBankCity\":\"1000\",\"accountBankProvince\":\"\",\"accountBankCode\":\"0308\",\"orderId\":\"1212147561\",\"accountName\":\"陈建美\",\"accountType\":\"AT02\",\"accAccount\":\"\",\"accountBankName\":\"\",\"assetProductId\":\"3006000002\",\"uumCustNo\":\"201612121525292277501559358\",\"accountPhone\":\"18612994579\",\"trusteeType\":\"B134003\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"merchNo\":\"30060\",\"instNo\":\"10450\",\"accountNo\":\"6225880127160006\",\"accAccountName\":\"\",\"accountCertId\":\"130826198709136320\",\"accountCardType\":\"01\",\"privatePublicAccount\":\"01\",\"accountPro\":\"\",\"accountOpenNo\":\"\",\"fundProductId\":\"1045000001\"},{\"accountBankCity\":\"1000\",\"accountBankProvince\":\"\",\"accountBankCode\":\"0308\",\"orderId\":\"1212147561\",\"accountName\":\"陈建美\",\"accountType\":\"AT01\",\"accAccount\":\"\",\"accountBankName\":\"\",\"assetProductId\":\"3006000002\",\"uumCustNo\":\"201612121525292277501559358\",\"accountPhone\":\"18612994579\",\"trusteeType\":\"B134003\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"merchNo\":\"30060\",\"instNo\":\"10450\",\"accountNo\":\"6225880127160006\",\"accAccountName\":\"\",\"accountCertId\":\"130826198709136320\",\"accountCardType\":\"01\",\"privatePublicAccount\":\"01\",\"accountPro\":\"\",\"accountOpenNo\":\"\",\"fundProductId\":\"1045000001\"}],\"orderAppendInfo\":{\"registeSourceName\":\"万卡H5\",\"businessChannelCode\":\"0\",\"saleSubChannel\":\"1002\",\"certAddress\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"applyTransNo\":\"3107cd81a9154cc1859373e1e28a1a26\",\"creditReport\":\"F18801\",\"customerType\":\"B20502\",\"communicateAddressCity\":\"北京市\",\"uumCustNo\":\"201612121525292277501559358\",\"communicateAddressProvince\":\"北京市\",\"yearIncome\":\"72000\",\"preChannelIds\":\"西安银行\",\"certValidate\":\"2037.08.21\",\"rechargeType\":\"B8010\",\"behaviorScore\":\"F\",\"otherLoanPlatformsInfo\":\"F2402\",\"withdrawalMode\":\"F1982\",\"ValidPeriod\":\"2017.08.21\",\"ipAddress\":\"10.172.226.97\",\"approveEndTime\":\"2020-05-20\",\"associationKey\":\"75221219186000\",\"isNewUser\":\"N8701\",\"validPeriod\":\"2017.08.21\",\"terminalType\":\"F8303\",\"otherLoanPlatformsUnpaid\":\"0.00\",\"verificationIdentity\":\"F18601\",\"issueAgency\":\"丰宁满族自治县公安局\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"registeSourceNo\":\"0\",\"communicateAddressDistrict\":\"朝阳区\",\"trustedPaymentFlag\":\"N8701\",\"businessChannelName\":\"万卡\",\"custLabel\":\"WKYZ\",\"creditAmt\":\"200000.00\"},\"applyInfo\":{\"repaymentMethod\":\"RM01\",\"deductionDate\":20,\"fundProvideProductId\":\"835\",\"purpose\":\"F1102\",\"saleSubChannel\":\"1002\",\"yearRate\":0.0900,\"loanTypeCode\":\"F1205\",\"score\":\"90\",\"instNo\":\"10450\",\"periods\":12,\"instCode\":\"3006000001\",\"firstRepayDay\":\"2020-06-20\",\"loanMethod\":\"B4001\",\"loanStartDate\":\"2020-05-20\",\"syntheticFundCost\":0.21,\"associationKey\":\"75221219186000\",\"applyAmt\":1200.00,\"borrowerType\":\"B154001\",\"saleChannel\":\"30060\",\"merchNo\":\"30060\",\"repayDateFlag\":\"RD02\",\"contractAmt\":1378.00,\"loanCode\":\"N8701\",\"applyDate\":\"2020-05-20 14:46:59\",\"riskGrade\":\"AA\",\"loanEndDate\":\"2021-05-20\",\"fundProductId\":\"1045000001\"}}}";
        JSONObject source = JSONObject.parseObject(jsonStr);
        try {
            String expression = "body.accountInfo.loanBankCode";
            Object value2 = Ognl.getValue(expression, new OgnlContext(), source);
            System.out.println(value2);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取json中特定的值
     */
    @Test
    public void test3() {
        String jsonStr = "{\"body\":{\"accountInfo\":{\"loanBankCode\":\"0308\",\"repayBankProvince\":\"\",\"loanBankProvince\":\"\",\"loanPrivatePublicAccount\":\"01\",\"loanTrusteeType\":\"B134003\",\"repayAccountType\":\"AT02\",\"repayTrusteeType\":\"B134003\",\"loanBankName\":\"\",\"repayAccountOpenNo\":\"\",\"repayBankName\":\"\",\"repayAccountNo\":\"6225880127160006\",\"loanBankCity\":\"1000\",\"repayBankCity\":\"1000\",\"repayAccountPhone\":\"18612994579\",\"loanAccountNo\":\"6225880127160006\",\"repayAccountName\":\"陈建美\",\"repayBankCode\":\"0308\",\"loanAccountPhone\":\"18612994579\",\"loanAccountName\":\"陈建美\",\"loanAccountOpenNo\":\"\",\"loanAccountType\":\"AT01\",\"repayPrivatePublicAccount\":\"01\"},\"contactInfo\":[{\"relativeName\":\"王一\",\"relativeCompanyAddress\":\"来广营\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994747\"},{\"relativeName\":\"王二\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994848\"}],\"professionInfo\":{\"incomeLevelCode\":\"B20202\",\"professionCode\":\"F12326\",\"incomeSourceCode\":\"B20305\",\"duty\":\"B2910\",\"yearIncome\":48000,\"industryCode\":\"B1036\"},\"censusInfoInfo\":{\"validDateBegin\":\"2017-08-21\",\"address\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"authority\":\"丰宁满族自治县公安局\",\"localResid\":\"F14901\",\"validDateEnd\":\"2037-08-21\",\"validDateEndSpecial\":\"2037-08-21\",\"validDateSpecial\":\"20170821-20370821\"},\"transInfo\":{\"businessNo\":\"1212147561\",\"fundApplyNo\":\"1_20200511000000000002\",\"applyNo\":\"5011202005200249053590009285\",\"merchTransNo\":\"1175221219026000\"},\"imageInfo\":{\"imageFilePathAndName\":\"/home/xabk_jf/image/creditAuth/20200520/130826198709136320_04.pdf\",\"imageFileDate\":\"2020-05-20 14:48:50\",\"imageFileName\":\"130826198709136320_04.pdf\",\"imageFilePath\":\"/home/xabk_jf/image/creditAuth/20200520\"},\"contractInfoList\":[{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:32\",\"contractType\":\"F19402\",\"contractFileid\":\"fa9dc8f5-977e-4dd6-8fc1-0d543df2f630\",\"contractId\":\"256\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"CA个人数字证书申请表及授权委托书（消金）\",\"contractVersion\":\"20191206142640\"},{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:32\",\"contractType\":\"F19403\",\"contractFileid\":\"93b732fa-9aeb-4a42-8af1-647d77e28bfb\",\"contractId\":\"247\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"征信及信息披露授权书（消金）\",\"contractVersion\":\"20191209185115\"},{\"contractDownType\":\"1\",\"node\":\"ND02\",\"contractInUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"createTime\":\"2020-05-20 14:48:33\",\"contractType\":\"F19403\",\"contractFileid\":\"521d67a4-d86c-4757-b89c-b75482beaaaa\",\"contractId\":\"558\",\"contractOutUrl\":\"http://47.93.168.209:7006/ossFile/download.do\",\"contractName\":\"个人信用报告查询授权书(西安)\",\"contractVersion\":\"20200410143223\"}],\"workUnitInfo\":{\"companyCity\":\"110100\",\"companyPhone\":\"\",\"companyType\":\"B0908\",\"companyAddress\":\"来广营\",\"company\":\"玖富数科\",\"companyCounty\":\"110105\",\"companyProvince\":\"110000\"},\"recordLoanAttach\":[{\"attachType\":\"0\",\"attachName\":\"130826198709136320_01.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"1\",\"attachName\":\"130826198709136320_02.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"11001578\",\"attachName\":\"130826198709136320_01.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"11001579\",\"attachName\":\"130826198709136320_02.jpg\",\"fileId\":\"/home/xabk_jf/image/cert/20200520\"},{\"attachType\":\"2\",\"attachName\":\"130826198709136320_03.jpg\",\"fileId\":\"/home/xabk_jf/image/faceCognition/20200520\"},{\"attachType\":\"558\",\"attachName\":\"130826198709136320_04.pdf\",\"fileId\":\"/home/xabk_jf/image/creditAuth/20200520\"}],\"orderAttachInfoList\":[{\"creator\":\"ROOT\",\"attachType\":\"11001578\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"正面.jpg\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28848,\"fileType\":\"jpg\",\"fileId\":\"2a1f341b-3710-4b50-b32b-54184659e633\"},{\"creator\":\"ROOT\",\"attachType\":\"11001579\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"反面.jpg\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28849,\"fileType\":\"jpg\",\"fileId\":\"ead7517b-a472-4ee6-91b0-55db2a1770f1\"},{\"creator\":\"ROOT\",\"attachType\":\"11001503\",\"createTime\":\"2020-05-20T14:48:28\",\"merchNo\":\"30060\",\"orderId\":\"1212147561\",\"attachUrl\":\"http://100.114.30.194:7006/ossFile/download.do\",\"attachName\":\"人脸照片\",\"updateTime\":\"2020-05-20T14:48:28\",\"id\":28850,\"fileType\":\"jpg\",\"fileId\":\"cec71a98-6ed1-429d-ab69-ae1d64d14f1b\"}],\"custInfo\":{\"birthDay\":\"1987-09-13\",\"faceRecognitionEndDate\":\"2020-05-12 14:07:10\",\"certType\":\"01\",\"custNo\":\"1000001809\",\"nation\":\"满\",\"sex\":\"F\",\"educationCode\":\"B0305\",\"certId\":\"130826198709136320\",\"certAddress\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"customerName\":\"陈建美\",\"phoneNo\":\"18612994579\",\"marriageCode\":\"B0501\",\"custLabel\":\"WKYZ\",\"email\":\"18612994579@wo.cn\",\"age\":32},\"communicationInfo\":{\"liveCounty\":\"110105\",\"liveAddress\":\"北京市北京市朝阳区融新科技中心B座\",\"liveProvince\":\"110000\",\"liveCity\":\"110100\"},\"insureDB\":{\"insureNum\":\"\",\"insureName\":\"江西华章汉辰担保集团股份有限公司\",\"insureCode\":\"HanChenJF\",\"insureRate\":0.1292},\"accountInfoList\":[{\"accountBankCity\":\"1000\",\"accountBankProvince\":\"\",\"accountBankCode\":\"0308\",\"orderId\":\"1212147561\",\"accountName\":\"陈建美\",\"accountType\":\"AT02\",\"accAccount\":\"\",\"accountBankName\":\"\",\"assetProductId\":\"3006000002\",\"uumCustNo\":\"201612121525292277501559358\",\"accountPhone\":\"18612994579\",\"trusteeType\":\"B134003\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"merchNo\":\"30060\",\"instNo\":\"10450\",\"accountNo\":\"6225880127160006\",\"accAccountName\":\"\",\"accountCertId\":\"130826198709136320\",\"accountCardType\":\"01\",\"privatePublicAccount\":\"01\",\"accountPro\":\"\",\"accountOpenNo\":\"\",\"fundProductId\":\"1045000001\"},{\"accountBankCity\":\"1000\",\"accountBankProvince\":\"\",\"accountBankCode\":\"0308\",\"orderId\":\"1212147561\",\"accountName\":\"陈建美\",\"accountType\":\"AT01\",\"accAccount\":\"\",\"accountBankName\":\"\",\"assetProductId\":\"3006000002\",\"uumCustNo\":\"201612121525292277501559358\",\"accountPhone\":\"18612994579\",\"trusteeType\":\"B134003\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"merchNo\":\"30060\",\"instNo\":\"10450\",\"accountNo\":\"6225880127160006\",\"accAccountName\":\"\",\"accountCertId\":\"130826198709136320\",\"accountCardType\":\"01\",\"privatePublicAccount\":\"01\",\"accountPro\":\"\",\"accountOpenNo\":\"\",\"fundProductId\":\"1045000001\"}],\"orderAppendInfo\":{\"registeSourceName\":\"万卡H5\",\"businessChannelCode\":\"0\",\"saleSubChannel\":\"1002\",\"certAddress\":\"河北省承德市丰宁满族自治县黄旗镇中心街45号\",\"applyTransNo\":\"3107cd81a9154cc1859373e1e28a1a26\",\"creditReport\":\"F18801\",\"customerType\":\"B20502\",\"communicateAddressCity\":\"北京市\",\"uumCustNo\":\"201612121525292277501559358\",\"communicateAddressProvince\":\"北京市\",\"yearIncome\":\"72000\",\"preChannelIds\":\"西安银行\",\"certValidate\":\"2037.08.21\",\"rechargeType\":\"B8010\",\"behaviorScore\":\"F\",\"otherLoanPlatformsInfo\":\"F2402\",\"withdrawalMode\":\"F1982\",\"ValidPeriod\":\"2017.08.21\",\"ipAddress\":\"10.172.226.97\",\"approveEndTime\":\"2020-05-20\",\"associationKey\":\"75221219186000\",\"isNewUser\":\"N8701\",\"validPeriod\":\"2017.08.21\",\"terminalType\":\"F8303\",\"otherLoanPlatformsUnpaid\":\"0.00\",\"verificationIdentity\":\"F18601\",\"issueAgency\":\"丰宁满族自治县公安局\",\"uumUserId\":\"e24f5c6c-6055-4d42-9890-b8db6da3ced0\",\"registeSourceNo\":\"0\",\"communicateAddressDistrict\":\"朝阳区\",\"trustedPaymentFlag\":\"N8701\",\"businessChannelName\":\"万卡\",\"custLabel\":\"WKYZ\",\"creditAmt\":\"200000.00\"},\"applyInfo\":{\"repaymentMethod\":\"RM01\",\"deductionDate\":20,\"fundProvideProductId\":\"835\",\"purpose\":\"F1102\",\"saleSubChannel\":\"1002\",\"yearRate\":0.0900,\"loanTypeCode\":\"F1205\",\"score\":\"90\",\"instNo\":\"10450\",\"periods\":12,\"instCode\":\"3006000001\",\"firstRepayDay\":\"2020-06-20\",\"loanMethod\":\"B4001\",\"loanStartDate\":\"2020-05-20\",\"syntheticFundCost\":0.21,\"associationKey\":\"75221219186000\",\"applyAmt\":1200.00,\"borrowerType\":\"B154001\",\"saleChannel\":\"30060\",\"merchNo\":\"30060\",\"repayDateFlag\":\"RD02\",\"contractAmt\":1378.00,\"loanCode\":\"N8701\",\"applyDate\":\"2020-05-20 14:46:59\",\"riskGrade\":\"AA\",\"loanEndDate\":\"2021-05-20\",\"fundProductId\":\"1045000001\"}}}";
        JSONObject source = JSONObject.parseObject(jsonStr);
        OgnlContext context = new OgnlContext();
        // 将json数据放入context的map中,默认放入root中
        OgnlUtil ognlUtil = OgnlUtil.build().context(context).add(source);
        try {
            Object value = ognlUtil.getValue("body.accountInfo.loanBankCode");
            System.out.println(value);
            //获取JsonArray中的值
            Object value1 = ognlUtil.getValue("body.contactInfo[0].relativeName");
            System.out.println(value1);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String jsonStr = "{\"body\":{\"accountInfo\":{\"loanBankCode\":\"0308\",\"repayBankProvince\":\"\",\"loanBankProvince\":\"\",\"loanPrivatePublicAccount\":\"01\",\"loanTrusteeType\":\"B134003\"},\"contactInfo\":[{\"relativeName\":\"王一\",\"relativeCompanyAddress\":\"来广营\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994747\"},{\"relativeName\":\"王二\",\"relativeCode\":\"F1004\",\"relativeMobile\":\"18612994848\"}],\"professionInfo\":{\"incomeLevelCode\":\"B20202\",\"professionCode\":\"F12326\",\"incomeSourceCode\":\"B20305\",\"duty\":\"B2910\",\"yearIncome\":48000,\"industryCode\":\"B1036\"}}}";
        JSONObject source = JSONObject.parseObject(jsonStr);
        try {
            //将json数据放到context中，并获取特定的值
            OgnlContext context = new OgnlContext();
            OgnlUtil ognlUtil = OgnlUtil.build().context(context).add("body", source);
            Object value = ognlUtil.getValue("#body.body.accountInfo.loanBankCode");
            System.out.println("#body.body.accountInfo.loanBankCode:"+value);
            // 获取JsonArray中值
            Object value1 = ognlUtil.getValue("#body.body.contactInfo[0].relativeName");
            System.out.println("#body.body.contactInfo[0].relativeName:"+value1);
            //获取的value值为空字符串，则正常打印空字符串
            Object value3 = ognlUtil.getValue("#body.body.accountInfo.repayBankProvince");
            System.out.println("#body.body.accountInfo.repayBankProvince:"+value3);
            //获取的json key不存在，则打印null
            Object value4 = ognlUtil.getValue("#body.body.accountInfo.repayBankProvince1");
            System.out.println("#body.body.accountInfo.repayBankProvince1:"+value4);
            //获取json对象
            String expression = "body.contactInfo";
            Object value2 = Ognl.getValue(expression, new OgnlContext(), source);
            System.out.println("body.contactInfo:"+value2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
