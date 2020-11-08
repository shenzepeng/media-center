//package kxg.sso.mediacenter.provider.dubbo.service;
//
//import kxg.sso.mediacenter.service.DemoService;
//import org.apache.dubbo.rpc.RpcContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DemoServiceImpl implements DemoService {
//    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
//
//    @Override
//    public String sayHello(String name) {
//        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
//        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
//    }
//
//}
//
