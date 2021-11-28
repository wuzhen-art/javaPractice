package io.kimmking.rpcfx.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 吴振
 * @since 2021/11/28 下午8:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RemoteServiceRegister.class)
public @interface RemoteStitchService {
    String[] basePackages();
}
