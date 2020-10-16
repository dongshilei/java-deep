package dong.xml;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/9 19:18
 **/
public class Xml2Json {

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><Resp><TotalAmt>362.78</TotalAmt><PayShdTryListAll><row><prcpAmt>0.0</prcpAmt><psRemPrcp>343.21</psRemPrcp><normInt>0.0</normInt><intRate>0.11</intRate><dueDt>2019-08-19</dueDt><instmAmt>0.0</instmAmt><perdNo>0</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>27.18</prcpAmt><psRemPrcp>316.03</psRemPrcp><normInt>1.78</normInt><intRate>0.11</intRate><dueDt>2019-09-05</dueDt><instmAmt>28.96</instmAmt><perdNo>1</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>27.43</prcpAmt><psRemPrcp>288.6</psRemPrcp><normInt>2.9</normInt><intRate>0.11</intRate><dueDt>2019-10-05</dueDt><instmAmt>30.33</instmAmt><perdNo>2</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>27.68</prcpAmt><psRemPrcp>260.92</psRemPrcp><normInt>2.65</normInt><intRate>0.11</intRate><dueDt>2019-11-05</dueDt><instmAmt>30.33</instmAmt><perdNo>3</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>27.94</prcpAmt><psRemPrcp>232.98</psRemPrcp><normInt>2.39</normInt><intRate>0.11</intRate><dueDt>2019-12-05</dueDt><instmAmt>30.33</instmAmt><perdNo>4</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>28.19</prcpAmt><psRemPrcp>204.79</psRemPrcp><normInt>2.14</normInt><intRate>0.11</intRate><dueDt>2020-01-05</dueDt><instmAmt>30.33</instmAmt><perdNo>5</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>28.45</prcpAmt><psRemPrcp>176.34</psRemPrcp><normInt>1.88</normInt><intRate>0.11</intRate><dueDt>2020-02-05</dueDt><instmAmt>30.33</instmAmt><perdNo>6</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>28.71</prcpAmt><psRemPrcp>147.63</psRemPrcp><normInt>1.62</normInt><intRate>0.11</intRate><dueDt>2020-03-05</dueDt><instmAmt>30.33</instmAmt><perdNo>7</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>28.98</prcpAmt><psRemPrcp>118.65</psRemPrcp><normInt>1.35</normInt><intRate>0.11</intRate><dueDt>2020-04-05</dueDt><instmAmt>30.33</instmAmt><perdNo>8</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>29.24</prcpAmt><psRemPrcp>89.41</psRemPrcp><normInt>1.09</normInt><intRate>0.11</intRate><dueDt>2020-05-05</dueDt><instmAmt>30.33</instmAmt><perdNo>9</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>29.51</prcpAmt><psRemPrcp>59.9</psRemPrcp><normInt>0.82</normInt><intRate>0.11</intRate><dueDt>2020-06-05</dueDt><instmAmt>30.33</instmAmt><perdNo>10</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>29.78</prcpAmt><psRemPrcp>30.12</psRemPrcp><normInt>0.55</normInt><intRate>0.11</intRate><dueDt>2020-07-05</dueDt><instmAmt>30.33</instmAmt><perdNo>11</perdNo><odIntRate>0.0</odIntRate></row><row><prcpAmt>30.12</prcpAmt><psRemPrcp>0.0</psRemPrcp><normInt>0.4</normInt><intRate>0.11</intRate><dueDt>2020-08-19</dueDt><instmAmt>30.52</instmAmt><perdNo>12</perdNo><odIntRate>0.0</odIntRate></row></PayShdTryListAll><errorCode>00000</errorCode><totalNormInt>19.57</totalNormInt><errorMsg></errorMsg></Resp><em>交易成功</em><flowNo>202006091631319820</flowNo><ec>0</ec></root>";
        XMLSerializer xmlSerializer = new XMLSerializer();
        String resutStr = xmlSerializer.read(xml).toString();
        JSONObject result = JSONObject.parseObject(resutStr);
        System.out.println(result.toJSONString());
    }

}
