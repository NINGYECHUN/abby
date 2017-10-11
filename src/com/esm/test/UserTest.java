/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.esm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.esm.service.UserService;
import com.esm.util.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

public class UserTest extends BasicTest {

//    @Autowired
//    private CountryMapper countryMapper;

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception{
    	
    	TaobaoClient client = new DefaultTaobaoClient(Constants.getTbUrl(), Constants.getAppKey(), Constants.getAppSecret());

    	/*TbkRebateOrderGetRequest req = new TbkRebateOrderGetRequest();
    	req.setFields("tb_trade_parent_id,tb_trade_id,num_iid,item_title,item_num,price,pay_price,seller_nick,seller_shop_title,commission,commission_rate,unid,create_time,earning_time");
    	req.setStartTime(StringUtils.parseDateTime("2015-03-05 13:52:08"));
    	req.setSpan(600L);
    	req.setPageNo(1L);
    	req.setPageSize(20L);
    	TbkRebateOrderGetResponse rsp = client.execute(req);
    	System.out.println(rsp.getBody());*/
    	
    	/*TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest",Constants.getAppKey(), Constants.getAppSecret());
    	TbkShopGetRequest req = new TbkShopGetRequest();
    	req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
    	req.setQ("女装");
    	req.setSort("commission_rate_des");
    	req.setIsTmall(false);
    	req.setStartCredit(1L);
    	req.setEndCredit(20L);
    	req.setStartCommissionRate(2000L);
    	req.setEndCommissionRate(123L);
    	req.setStartTotalAction(1L);
    	req.setEndTotalAction(100L);
    	req.setStartAuctionCount(123L);
    	req.setEndAuctionCount(200L);
    	req.setPlatform(1L);
    	req.setPageNo(1L);
    	req.setPageSize(20L);
    	TbkShopGetResponse rsp = client.execute(req);
    	System.out.println(rsp.getBody());*/
    }
}
