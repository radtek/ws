package com.cplatform.mall.back.utils;

import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cplatform.gift_card_interface.GiftCardClient;
import com.cplatform.gift_card_interface.bean.BaseResponse;
import com.cplatform.gift_card_interface.bean.CardOperateRequest;
import com.cplatform.gift_card_interface.bean.GetCardNumberRequest;

/**
 * 
 * Title.礼品卡功能高阳接口<br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2013-9-18 下午01:43:18
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * Author: macl@c-platform.com
 * <p>
 * Version: 1.0
 * <p>
 */
@Service
public class GiftCardSyncInterface {
	private static Logger log = Logger.getLogger(GiftCardSyncInterface.class);

	@Autowired
	private AppConfig config;

	@Autowired
	private LogUtils logUtils;

	private void init() {
		try {
			// 需要完成AppConfig方法
			GiftCardClient.init(config.getGiftCardSyncIp(), config
					.getGiftCardSyncPort(), config.getGiftCardSyncPlatCode(),
					config.getGiftCardSyncMd5Key(), config
							.getGiftCardSyncDesKey());
		} catch (Exception e) {
			logUtils.logAdd("初始化礼品卡高阳接口类失败", e.getMessage());
			log.error("初始化礼品卡高阳接口类失败：" + e.getMessage());
		}

	}

	/**
	 * 高阳制卡接口
	 * 
	 * @throws Exception
	 */
	public String makeCard(String orderNo, int quantity) {
		String msg = "制卡失败！";
		init();
		GetCardNumberRequest request = new GetCardNumberRequest();
		request.setOrderno(orderNo);
		request.setQuantity(quantity);
		BaseResponse response = null;
		try {
			response = GiftCardClient.getInstance().sendRequest(request, 10000);
			if (null != response) {
				if ("SUCCESS".equals(response.getRetcode().toUpperCase())) {
					msg = "制卡成功！";
					logUtils.logAdd("礼品卡制卡", "礼品卡制卡成功:orderNo:" + orderNo
							+ ",quantity" + quantity);
				}
			} else {
				logUtils.logAdd("礼品卡制卡", "礼品卡制卡:接口没有返回数据");
			}
		} catch (Exception e) {
			log.error("礼品卡制卡接口失败：" + e.getMessage());
			logUtils.logAdd("礼品卡制卡", "礼品卡制卡接口失败：" + e.getMessage());
		}
		return msg;
	}

	public String activeCard(String serialNo) throws Exception {
		String msg = "0"; 
		init();
		CardOperateRequest request = new CardOperateRequest();
		request.setCardno(serialNo);
		request.setType("0");
		request.setOrderno(serialNo);
		request.setContent("");

		BaseResponse response = null;
		try {
			response = GiftCardClient.getInstance().sendRequest(request, 10000);
//			 System.out.println("retCode:"+response.getRetcode());
//			 System.out.println("msg:"+response.getMessage());
			 if (null != response) {
					if ("SUCCESS".equals(response.getRetcode().toUpperCase())) {
						msg = "1";
						log.info("监控程序调用礼品卡激活成功:serialNo:" + serialNo);
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("监控程序调用礼品卡调用礼品卡激活接口失败：：" + e.getMessage());
			throw new Exception("监控程序调用礼品卡调用礼品卡激活接口失败："+e.getMessage());
		}
		 return msg;
	}
}