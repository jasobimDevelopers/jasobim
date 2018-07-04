package com.my.spring.wechat;


import java.util.List;

import com.my.spring.wechat.models.Topic;
import com.my.spring.wechat.util.WechatUtil;


public class WechatSpider extends WechatUtil {

    /**
     * <pre>
     *  new WechatSpider("123").getPageDocs(1); 获取第一页的的全部文章
     * </pre>
     * 
     * @param id
     *            微信公共号的openId
     */
    public WechatSpider(String id) {
        super.setId(id);
        //super.excute();
    }


    public static void main(String[] args) {
        WechatSpider spider = new WechatSpider("gh_c886b7f1ee09");//嘉实集团有限公司
        String listUrl = spider.getListUrl();
        System.out.println(listUrl);
        	System.out.println(listUrl);
			Topic topic = spider.getTopicByUrl(listUrl);
			System.out.println(topic.getContent());
    }
    public void testGet(){
    	WechatSpider spider = new WechatSpider("gh_c886b7f1ee09");//嘉实集团有限公司
        String listUrl = spider.getListUrl();
        System.out.println(listUrl);
        	System.out.println(listUrl);
			Topic topic = spider.getTopicByUrl(listUrl);
			System.out.println(topic.getContent());
    }

}
