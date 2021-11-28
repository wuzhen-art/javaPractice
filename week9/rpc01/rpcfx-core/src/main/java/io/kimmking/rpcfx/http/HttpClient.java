package io.kimmking.rpcfx.http;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;

/**
 */
public abstract class HttpClient {


    public abstract RpcfxResponse post(RpcfxRequest req, String url);

}
