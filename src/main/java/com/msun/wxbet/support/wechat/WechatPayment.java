/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.support.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author zxc Sep 19, 2016 2:45:12 PM
 */
@Service
public class WechatPayment {

    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${appId}")
    private String appId;
    @Value("${mchId}")
    private String mchId;
    @Value("${paymentApiKey}")
    private String paymentApiKey;

    /**
     * 支付JS的参数生成
     * 
     * @param prepayId
     * @return
     * @throws IOException
     */
    public Map<String, String> initPaymentAttribute(String prepayId) throws IOException {
        Map<String, String> map = new HashMap<>();
        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = UUID.randomUUID().toString().replace("-", "");

        map.put("appId", appId);
        map.put("timeStamp", String.valueOf(timestamp));
        map.put("nonceStr", noncestr);
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", "MD5");

        String sign = generateSign(map);
        map.put("paySign", sign);
        return map;
    }

    /**
     * 调用微信统一下单API https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * 
     * @param totalMoney 总金额(单位:分)
     * @return prepay_id
     */
    public String createWechatOrder(String productName, String orderId, long totalMoney, String remoteIp, String openId)
                                                                                                                        throws ParserConfigurationException,
                                                                                                                        TransformerException,
                                                                                                                        IOException,
                                                                                                                        WechatException,
                                                                                                                        SAXException {

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String notifyUrl = baseUrl + "/wechatpaymentnotify";
        String nonceStr = UUID.randomUUID().toString().replace("-", "");

        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("body", productName);
        map.put("out_trade_no", orderId);
        map.put("total_fee", String.valueOf(totalMoney));
        map.put("spbill_create_ip", remoteIp);
        map.put("notify_url", notifyUrl);
        map.put("openid", openId);
        map.put("trade_type", "JSAPI");
        String xml = generateXml(map);

        String result = HttpUtil.post(url, xml);

        Document document = convertXml(result, true);
        if ("SUCCESS".equals(getElementTextValue(document, "return_code"))
            && "SUCCESS".equals(getElementTextValue(document, "result_code"))) {
            return getElementTextValue(document, "prepay_id");
        } else {
            throw new WechatException("支付接口返回异常,return_code或result_code不为SUCCESS: " + result);
        }
    }

    /**
     * 企业付款 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * 
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws WechatException
     * @throws SAXException
     */
    public void withdraw(String orderId, String openId, int money, String ip) throws TransformerException,
                                                                             ParserConfigurationException, IOException,
                                                                             WechatException, SAXException {
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        String nonceStr = UUID.randomUUID().toString().replace("-", "");

        Map<String, String> map = new HashMap<>();
        map.put("mch_appid", appId);
        map.put("mchid", mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", orderId);
        map.put("openid", openId);
        map.put("check_name", "NO_CHECK");
        map.put("amount", String.valueOf(money));
        map.put("desc", "提现");
        map.put("spbill_create_ip", ip);
        String xml = generateXml(map);

        String result = HttpUtil.post(url, xml);

        Document document = convertXml(result, false);
        if (!"SUCCESS".equals(getElementTextValue(document, "return_code"))
            || !"SUCCESS".equals(getElementTextValue(document, "result_code"))) {
            throw new WechatException("企业付款接口返回异常,return_code或result_code不为SUCCESS: " + result);
        }
    }

    /**
     * 处理支付成功回调通知
     * 
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws WechatException
     * @throws IOException
     */
    public String paymentNotify(String xml) throws ParserConfigurationException, SAXException, WechatException,
                                           IOException {
        Document document = convertXml(xml, true);
        if ("SUCCESS".equals(getElementTextValue(document, "return_code"))
            && "SUCCESS".equals(getElementTextValue(document, "result_code"))) {
            return getElementTextValue(document, "out_trade_no");
        } else {
            throw new WechatException("支付接口返回异常,return_code或result_code不为SUCCESS: " + xml);
        }
    }

    /**
     * 获取XML中的一项元素的值
     * 
     * @param document
     * @param key
     * @return
     */
    private String getElementTextValue(Document document, String key) {
        Element rootElement = document.getDocumentElement();
        return rootElement.getElementsByTagName(key).item(0).getTextContent();
    }

    /**
     * 将XML转换成Document,同时校验sign
     * 
     * @param xml XML字符串
     * @param checkSign 是否需要校验xml中的sign
     * @return
     */
    private Document convertXml(String xml, boolean checkSign) throws ParserConfigurationException, IOException,
                                                              SAXException, WechatException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));

        Element rootElement = document.getDocumentElement();
        NodeList nodeList = rootElement.getChildNodes();

        String sign = null;
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String key = element.getTagName();
                String value = element.getTextContent();
                if (key.equals("sign")) {
                    sign = value;
                } else {
                    map.put(key, value);
                }
            }
        }

        if (checkSign && !generateSign(map).equals(sign)) {
            throw new WechatException("支付接口返回结果sign错误: " + xml);
        }
        return document;
    }

    /**
     * 按规则生成XML,并添加sign
     * 
     * @param map
     * @return
     */
    public String generateXml(Map<String, String> map) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Element rootElement = document.createElement("xml");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Element childElement = document.createElement(entry.getKey());
            childElement.appendChild(document.createTextNode(entry.getValue()));
            rootElement.appendChild(childElement);
        }

        String sign = generateSign(map);
        Element childElement = document.createElement("sign");
        childElement.appendChild(document.createTextNode(sign));
        rootElement.appendChild(childElement);

        document.appendChild(rootElement);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        String xml = writer.toString();

        return xml;
    }

    /**
     * 根据签名生成算法生成sign https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
     * 
     * @param map
     * @return
     */
    private String generateSign(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append('&');
        }
        builder.append("key=");
        builder.append(paymentApiKey);

        String sign = DigestUtils.md5Hex(builder.toString()).toUpperCase();
        return sign;
    }
}
