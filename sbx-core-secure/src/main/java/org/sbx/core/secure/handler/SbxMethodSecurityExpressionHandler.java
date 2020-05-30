package org.sbx.core.secure.handler;

import org.aopalliance.intercept.MethodInvocation;
import org.sbx.core.secure.expression.SbxSecurityExpressionRoot;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * <p>SbxMethodSecurityExpressionHandler class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
@Configuration
public class SbxMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    public SbxMethodSecurityExpressionHandler(){
        super();
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation){
        SbxSecurityExpressionRoot root = new SbxSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        return root;
    }

}
