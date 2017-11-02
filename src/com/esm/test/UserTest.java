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
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;

public class UserTest extends BasicTest {

//    @Autowired
//    private CountryMapper countryMapper;

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception{
    	
    	TaobaoClient client = new DefaultTaobaoClient(Constants.getTbUrl(), Constants.getAppKey(), Constants.getAppSecret());
    	
    	TbkItemGetRequest req = new TbkItemGetRequest();
    	req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
    	req.setQ("女装");
    	req.setItemloc("杭州");
    	req.setSort("tk_rate_des");
    	req.setIsTmall(false);
    	req.setIsOverseas(false);
    	req.setStartPrice(10L);
    	req.setEndPrice(100L);
    	req.setStartTkRate(0L);
    	req.setEndTkRate(123L);
    	req.setPlatform(1L);
    	req.setPageNo(1L);
    	req.setPageSize(20L);
    	TbkItemGetResponse rsp = client.execute(req);
    	System.out.println(rsp.getBody());
    }
}
