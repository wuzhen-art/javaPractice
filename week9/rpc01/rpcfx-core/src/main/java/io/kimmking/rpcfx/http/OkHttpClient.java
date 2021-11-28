package io.kimmking.rpcfx.http;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 */
public class OkHttpClient extends HttpClient {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    @Override
    public RpcfxResponse post(RpcfxRequest req, String url) {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = null;
        try {
            respJson = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            RpcfxResponse rpcfxResponse = new RpcfxResponse();
            rpcfxResponse.setResult(null);
            rpcfxResponse.setStatus(false);
            rpcfxResponse.setException(e);
            return rpcfxResponse;
        }
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
}
